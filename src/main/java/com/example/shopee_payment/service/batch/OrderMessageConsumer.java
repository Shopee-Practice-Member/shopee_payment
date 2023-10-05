package com.example.shopee_payment.service.batch;

import com.example.shopee_payment.dto.request.OrderMessageRequestDto;
import com.example.shopee_payment.dto.request.PaidOrderMessageRequestDto;
import com.example.shopee_payment.helper.JsonConverter;
import com.example.shopee_payment.service.KafkaService;
import com.example.shopee_payment.service.MoneyTransactionService;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.stereotype.Component;

import static com.example.shopee_payment.constant.KafkaConstant.ORDER_TO_PAYMENT_REQUEST_TOPIC;
import static com.example.shopee_payment.constant.KafkaConstant.PAYMENT_TO_ORDER_RESPONSE_TOPIC;
import static com.example.shopee_payment.constant.MoneyTransactionConstant.NAME_SERVICE;

@Component
@Log4j2
public class OrderMessageConsumer {

    @Autowired
    MoneyTransactionService moneyTransactionService;

    @Autowired
    KafkaService kafkaService;

    public static final String TOPIC = ORDER_TO_PAYMENT_REQUEST_TOPIC;
    private static final String PARTITIONS = "#{@finder.partitions('" + TOPIC + "')}";


    @KafkaListener(topics = TOPIC, topicPartitions = @TopicPartition(topic = TOPIC,
            partitions = PARTITIONS))
    public void receive(ConsumerRecord<String, String> consumerRecord) {
        log.info("Received payload: '{}'", consumerRecord.toString());
        OrderMessageRequestDto orderMessage = JsonConverter.readValue(consumerRecord.value(), OrderMessageRequestDto.class);
        var transactionId = moneyTransactionService.payForBill(orderMessage);
        var transaction = moneyTransactionService.getTransactionById(transactionId);
        var paidOrderMessageDto = PaidOrderMessageRequestDto.builder()
                .id(orderMessage.getId())
                .price(orderMessage.getPrice())
                .nameService(NAME_SERVICE)
                .message(transaction.getMessage())
                .transactionStatus(transaction.getTransactionStatus().label)
                .build();
        var paidOrderMessage = JsonConverter.writeValueAsString(paidOrderMessageDto);

        kafkaService.sendMessage(PAYMENT_TO_ORDER_RESPONSE_TOPIC, paidOrderMessage);
    }

}
