package com.kerosene.common.exception;

/** Signals that a transfer or payment targets the authenticated sender itself. */
public class FinancialSelfPaymentException extends RuntimeException {

    /** Creates the self-payment rejection with its standard user-facing explanation. */
    public FinancialSelfPaymentException() {
        super("You cannot pay or send funds to yourself.");
    }
}
