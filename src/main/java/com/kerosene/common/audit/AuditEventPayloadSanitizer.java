package com.kerosene.common.audit;

import com.kerosene.common.infra.logging.LogSanitizer;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

/**
 * Converts audit payloads into bounded, log-safe metadata before hashing or
 * structured logging.
 */
public final class AuditEventPayloadSanitizer {

    /** Maximum number of metadata entries retained per audit payload. */
    private static final int MAX_KEYS = 32;
    /** Maximum serialized or sanitized scalar length retained per field. */
    private static final int MAX_VALUE_LENGTH = 256;
    /** Replacement used when an audit key is identified as sensitive. */
    private static final String MASKED = "[MASKED]";

    /** Prevents construction of this static sanitization utility. */
    private AuditEventPayloadSanitizer() {
    }

    /** Bounds and redacts payload metadata before it is logged or hashed.
     * @param payload caller-supplied audit metadata; may be {@code null}
     * @return immutable sanitized map with at most 32 safe entries
     */
    public static Map<String, Object> sanitize(Map<String, ?> payload) {
        if (payload == null || payload.isEmpty()) {
            return Map.of();
        }

        Map<String, Object> sanitized = new LinkedHashMap<>();
        for (Map.Entry<String, ?> entry : payload.entrySet()) {
            if (entry.getKey() == null || sanitized.size() >= MAX_KEYS) {
                continue;
            }
            String key = safeKey(entry.getKey());
            sanitized.put(key, sanitizeValue(key, entry.getValue()));
        }
        return Collections.unmodifiableMap(new LinkedHashMap<>(sanitized));
    }

    /** Redacts sensitive scalars and replaces nested containers with size summaries.
     * @param key already normalized metadata key
     * @param value original metadata value
     * @return safe scalar, bounded string, size summary, or null
     */
    private static Object sanitizeValue(String key, Object value) {
        if (value == null) {
            return null;
        }
        if (isSensitiveKey(key)) {
            return MASKED;
        }
        if (value instanceof Number || value instanceof Boolean || value instanceof UUID || value instanceof Enum<?>) {
            return value;
        }
        if (value instanceof Collection<?> collection) {
            return "collection(size=" + collection.size() + ")";
        }
        if (value instanceof Map<?, ?> map) {
            return "map(size=" + map.size() + ")";
        }
        return limit(LogSanitizer.sanitizeFinancialPayload(String.valueOf(value)));
    }

    /** Restricts metadata keys to a bounded set of log-safe characters.
     * @param key original metadata key
     * @return sanitized key of at most 64 characters
     */
    private static String safeKey(String key) {
        String sanitized = key.replaceAll("[^A-Za-z0-9_.-]", "_");
        if (sanitized.length() > 64) {
            return sanitized.substring(0, 64);
        }
        return sanitized;
    }

    /** Detects keys that could contain secrets, raw payloads, or payment material.
     * @param key sanitized candidate key
     * @return {@code true} when the key must be replaced with the masking marker
     */
    private static boolean isSensitiveKey(String key) {
        String normalized = key.toLowerCase(Locale.ROOT).replace("-", "").replace("_", "").replace(".", "");
        return LogSanitizer.isSensitiveKey(normalized)
                || normalized.contains("payload")
                || normalized.contains("body")
                || normalized.contains("credential")
                || normalized.contains("privatekey")
                || normalized.contains("macaroon")
                || normalized.contains("invoice");
    }

    /** Truncates a sanitized scalar to the maximum retained value length.
     * @param value sanitized text, possibly null
     * @return unchanged value within the limit, otherwise its bounded prefix
     */
    private static String limit(String value) {
        if (value == null || value.length() <= MAX_VALUE_LENGTH) {
            return value;
        }
        return value.substring(0, MAX_VALUE_LENGTH);
    }
}
