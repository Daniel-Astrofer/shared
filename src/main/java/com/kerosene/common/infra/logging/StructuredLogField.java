package com.kerosene.common.infra.logging;

/**
 * Stable structured log field names used across runtime, access and audit logs.
 *
 * <p>Values must be safe for operators and log sinks: never place request bodies,
 * credentials, tokens, invoices, macaroons, private keys or raw provider payloads
 * in these fields.
 */
public final class StructuredLogField {

    /** Distributed trace identifier. */
    public static final String TRACE_ID = "traceId";
    /** Request or operation correlation identifier. */
    public static final String CORRELATION_ID = "correlationId";
    /** Stable event name or event category. */
    public static final String EVENT = "event";
    /** Subsystem or bounded context that emitted the record. */
    public static final String DOMAIN = "domain";
    /** Named operation within the emitting subsystem. */
    public static final String OPERATION = "operation";
    /** Sanitized, operator-readable event summary. */
    public static final String MESSAGE = "safeMessage";
    /** Stable machine-readable error code, when present. */
    public static final String ERROR_CODE = "errorCode";
    /** Exception class name; exception messages are intentionally excluded. */
    public static final String EXCEPTION_TYPE = "exceptionType";
    /** HTTP request method. */
    public static final String HTTP_METHOD = "http.method";
    /** Request URL path without query-string data. */
    public static final String URL_PATH = "url.path";
    /** HTTP response status code. */
    public static final String HTTP_STATUS_CODE = "http.status_code";
    /** Operation duration in milliseconds. */
    public static final String DURATION_MS = "duration_ms";
    /** Client network address, which must be masked before logging. */
    public static final String CLIENT_IP = "client.ip";
    /** Request body size in bytes, not the body contents. */
    public static final String REQUEST_BYTES = "req.bytes";
    /** Response body size in bytes, not the body contents. */
    public static final String RESPONSE_BYTES = "res.bytes";

    /** Prevents construction of this constants-only catalog. */
    private StructuredLogField() {
    }
}
