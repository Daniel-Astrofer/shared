package com.kerosene.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.time.LocalDateTime;

/**
 * Standard success/error envelope shared by service API endpoints.
 *
 * <p>Null properties are omitted from JSON so error responses do not include
 * an irrelevant data field and successful responses do not include an error code.
 *
 * @param <T> type of the optional endpoint-specific data payload
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder(alphabetic = true)
public class ApiResponse<T> {

    /** Whether the operation completed successfully. */
    private boolean success;
    /** Human-readable result or failure summary. */
    private String message;
    /** Endpoint-specific response payload, omitted when absent. */
    private T data;
    /** Stable machine-readable failure code, omitted on success. */
    private String errorCode;
    /** Request trace identifier used to correlate the response with logs. */
    private String traceId;
    /** Local response construction time. */
    private LocalDateTime timestamp;

    /** Creates an empty response envelope and initializes its timestamp. */
    public ApiResponse() {
        this.timestamp = LocalDateTime.now();
    }

    /** Creates a response envelope and initializes its timestamp.
     * @param success whether the operation succeeded
     * @param message human-readable outcome text
     * @param data endpoint-specific payload, if any
     * @param errorCode stable error identifier, if this is a failure response
     */
    public ApiResponse(boolean success, String message, T data, String errorCode) {
        this.success = success;
        this.message = message;
        this.data = data;
        this.errorCode = errorCode;
        this.timestamp = LocalDateTime.now();
    }

    /** Creates a successful response with a payload.
     * @param message human-readable success text
     * @param data endpoint-specific result
     * @param <T> payload type
     * @return success envelope with the supplied payload
     */
    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(true, message, data, null);
    }

    /** Creates a successful response without a payload.
     * @param message human-readable success text
     * @param <T> payload type expected by the endpoint
     * @return success envelope with no data value
     */
    public static <T> ApiResponse<T> success(String message) {
        return new ApiResponse<>(true, message, null, null);
    }

    /** Creates a failure response without a payload.
     * @param message human-readable failure text
     * @param errorCode stable machine-readable failure code
     * @param <T> payload type expected by the endpoint
     * @return failure envelope with no data value
     */
    public static <T> ApiResponse<T> error(String message, String errorCode) {
        return new ApiResponse<>(false, message, null, errorCode);
    }

    /** Creates a failure response with optional diagnostic data.
     * @param message human-readable failure text
     * @param errorCode stable machine-readable failure code
     * @param data failure-specific payload
     * @param <T> payload type
     * @return failure envelope containing the supplied data
     */
    public static <T> ApiResponse<T> error(String message, String errorCode, T data) {
        return new ApiResponse<>(false, message, data, errorCode);
    }

    /** Creates a failure response and attaches its request trace identifier.
     * @param message human-readable failure text
     * @param errorCode stable machine-readable failure code
     * @param data failure-specific payload
     * @param traceId trace ID used by server logs for correlation
     * @param <T> payload type
     * @return failure envelope with trace ID attached
     */
    public static <T> ApiResponse<T> error(String message, String errorCode, T data, String traceId) {
        ApiResponse<T> response = new ApiResponse<>(false, message, data, errorCode);
        response.setTraceId(traceId);
        return response;
    }

    /** Returns whether the operation succeeded.
     * @return {@code true} for a successful response
     */
    public boolean isSuccess() {
        return success;
    }

    /** Updates the success flag before the response is serialized.
     * @param success whether the operation succeeded
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }

    /** Returns the human-readable outcome text.
     * @return outcome text
     */
    public String getMessage() {
        return message;
    }

    /** Updates the human-readable outcome text.
     * @param message outcome text
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /** Returns the endpoint-specific payload.
     * @return payload, or {@code null} when absent
     */
    public T getData() {
        return data;
    }

    /** Replaces the endpoint-specific payload.
     * @param data response payload
     */
    public void setData(T data) {
        this.data = data;
    }

    /** Returns the stable machine-readable error code.
     * @return error code, or {@code null} on success
     */
    public String getErrorCode() {
        return errorCode;
    }

    /** Sets the stable machine-readable error code.
     * @param errorCode error identifier or {@code null} when not applicable
     */
    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    /** Returns the request trace identifier.
     * @return trace ID, or {@code null} when unavailable
     */
    public String getTraceId() {
        return traceId;
    }

    /** Sets the request trace identifier used for log correlation.
     * @param traceId trace identifier
     */
    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    /** Returns the response construction timestamp.
     * @return response timestamp
     */
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    /** Overrides the response timestamp, primarily for framework serialization hooks.
     * @param timestamp timestamp to serialize
     */
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
