package com.kerosene.common.exception;

/** Signals that an external financial rail is unavailable for the requested operation. */
public class FinancialProviderUnavailableException extends KeroseneException {

    /** Stable public error code mapped to this provider availability failure. */
    public static final String ERROR_CODE = "ERR_KFE_RAIL_PROVIDER_UNAVAILABLE";

    /** Creates the exception with a descriptive message and the standard provider error code.
     * @param message safe explanation of the unavailable provider condition
     */
    public FinancialProviderUnavailableException(String message) {
        super(message, ERROR_CODE);
    }

    /** Creates the exception while retaining the provider failure that caused it.
     * @param message safe explanation of the unavailable provider condition
     * @param cause original transport or provider exception
     */
    public FinancialProviderUnavailableException(String message, Throwable cause) {
        super(message, cause, ERROR_CODE);
    }
}
