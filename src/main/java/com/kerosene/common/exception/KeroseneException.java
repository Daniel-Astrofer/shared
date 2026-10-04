package com.kerosene.common.exception;

/** Base for platform exceptions that carry a stable client-facing error code. */
public abstract class KeroseneException extends RuntimeException {

    /** Stable machine-readable code associated with this failure. */
    private final String errorCode;

    /** Creates a coded platform failure without an underlying cause.
     * @param message human-readable explanation
     * @param errorCode stable client-facing error identifier
     */
    public KeroseneException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    /** Creates a coded platform failure and retains its originating cause.
     * @param message human-readable explanation
     * @param cause lower-level failure that caused this exception
     * @param errorCode stable client-facing error identifier
     */
    public KeroseneException(String message, Throwable cause, String errorCode) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    /** Returns the stable error identifier for API mapping.
     * @return client-facing error code
     */
    public String getErrorCode() {
        return errorCode;
    }
}
