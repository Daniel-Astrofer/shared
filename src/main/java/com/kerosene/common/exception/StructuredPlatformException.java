package com.kerosene.common.exception;

import org.springframework.http.HttpStatus;

/** Runtime failure carrying an HTTP status, public error code, and structured response data. */
public class StructuredPlatformException extends RuntimeException {

    /** HTTP status used when this exception is rendered as an API response. */
    private final HttpStatus status;
    /** Stable public error identifier returned to API clients. */
    private final String errorCode;
    /** Optional structured payload with field or operation-specific context. */
    private final Object data;

    /** Creates a structured API failure.
     * @param message safe human-readable explanation
     * @param status HTTP status to expose
     * @param errorCode stable public error identifier
     * @param data optional structured details for the client response
     */
    public StructuredPlatformException(String message, HttpStatus status, String errorCode, Object data) {
        super(message);
        this.status = status;
        this.errorCode = errorCode;
        this.data = data;
    }

    /** Returns the HTTP status selected for this failure.
     * @return response status
     */
    public HttpStatus getStatus() {
        return status;
    }

    /** Returns the public error identifier.
     * @return stable error code
     */
    public String getErrorCode() {
        return errorCode;
    }

    /** Returns optional response details associated with this failure.
     * @return structured error data, or {@code null} when no details were supplied
     */
    public Object getData() {
        return data;
    }
}
