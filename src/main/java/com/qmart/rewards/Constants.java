package com.qmart.rewards;

public class Constants {

    public static enum ORDER_STATUS {
        SUCCESS, CANCEL, IN_PROGRESS
    }

    public static enum PAYMENT_TYPE {
        CREDIT_CARD, DEBIT_CARD, CHECK, CASH
    }

    public static enum TRANSACTION_STATUS {
        COMPLETE, REFUND, DISPUTE
    }
}
