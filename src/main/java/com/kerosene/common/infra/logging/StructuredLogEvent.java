package com.kerosene.common.infra.logging;

import net.logstash.logback.argument.StructuredArgument;

import java.util.ArrayList;
import java.util.List;

import static net.logstash.logback.argument.StructuredArguments.kv;

/**
 * Small builder for safe structured log fields.
 *
 * <p>The builder sanitizes string values as a last line of defense. Callers are
 * still responsible for passing only metadata, never bodies or raw secrets.
 */
public final class StructuredLogEvent {

    private final List<StructuredArgument> fields = new ArrayList<>();

    /** Builds the initial set of standard event fields, sanitizing string values.
     * @param event stable event name
     * @param domain subsystem that emitted the event
     * @param operation operation being recorded
     * @param safeMessage concise message safe for log storage
     */
    private StructuredLogEvent(String event, String domain, String operation, String safeMessage) {
        field(StructuredLogField.EVENT, event);
        field(StructuredLogField.DOMAIN, domain);
        field(StructuredLogField.OPERATION, operation);
        field(StructuredLogField.MESSAGE, safeMessage);
    }

    /** Starts a structured event populated with the standard metadata fields.
     * @param event stable event name
     * @param domain subsystem that emitted the event
     * @param operation operation being recorded
     * @param safeMessage concise message safe for log storage
     * @return a new event builder
     */
    public static StructuredLogEvent of(String event, String domain, String operation, String safeMessage) {
        return new StructuredLogEvent(event, domain, operation, safeMessage);
    }

    /** Adds a non-null field after sanitizing string values.
     * @param name structured field key
     * @param value field value; strings pass through financial payload sanitization
     * @return this builder for chained field additions
     */
    public StructuredLogEvent field(String name, Object value) {
        if (name != null && value != null) {
            fields.add(kv(name, sanitize(value)));
        }
        return this;
    }

    /** Adds the exception class name without adding its potentially sensitive message.
     * @param throwable exception to describe; null is ignored
     * @return this builder for chained additions
     */
    public StructuredLogEvent exception(Throwable throwable) {
        if (throwable != null) {
            field(StructuredLogField.EXCEPTION_TYPE, throwable.getClass().getSimpleName());
        }
        return this;
    }

    /** Exposes accumulated structured arguments for the logging framework.
     * @return snapshot array accepted by the structured logger API
     */
    public Object[] arguments() {
        return fields.toArray();
    }

    /** Sanitizes strings while leaving typed structured values unchanged.
     * @param value value to add to the structured logger
     * @return sanitized string or the original non-string value
     */
    private Object sanitize(Object value) {
        if (value instanceof String text) {
            return LogSanitizer.sanitizeFinancialPayload(text);
        }
        return value;
    }
}
