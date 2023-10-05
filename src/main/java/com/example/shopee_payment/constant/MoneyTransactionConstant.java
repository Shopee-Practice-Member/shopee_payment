package com.example.shopee_payment.constant;

public class MoneyTransactionConstant {

    public enum TransactionStatus {
        PENDING("Pending"),
        IN_PROGRESS("In progress"),
        PAID("Paid"),

        SUCCESSFUL("Successful"),

        FAILED("Failed");
        public final String label;

        private TransactionStatus(String label) {
            this.label = label;
        }
    }

    public enum BillType {

        DEPOSIT("Deposit"),

        WITHDRAW("With Draw");
        public final String label;

        BillType(String label) {
            this.label = label;
        }
    }


    public enum TargetType {

        PAY_FOR_BILL("Pay for bill"),
        WALLET("Wallet");

        public final String label;

        TargetType(String label) {
            this.label = label;
        }
    }

    public static final String NAME_SERVICE = "Shopee Payment Service";

}
