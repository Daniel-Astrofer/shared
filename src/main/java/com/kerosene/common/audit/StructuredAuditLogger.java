package com.kerosene.common.audit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import com.kerosene.common.infra.logging.LogDomain;
import com.kerosene.common.infra.logging.StructuredLogEvent;

import java.util.Map;
import java.util.UUID;

/**
 * Emits immutable domain audit notifications to the AUDIT log stream.
 *
 * <p>Callers must pass only sanitized metadata. This logger deliberately records
 * row hashes and identifiers, never raw request bodies, credentials, invoices,
 * macaroons, private keys, tokens or provider payloads.
 */
@Component
public class StructuredAuditLogger {

    private static final Logger log = LoggerFactory.getLogger(StructuredAuditLogger.class);

    /** Creates the logger component; the SLF4J logger is bound to this class. */
    public StructuredAuditLogger() {
    }

    /** Emits the immutable metadata associated with a persisted audit record.
     * @param eventType stable audit event category
     * @param sequenceNumber monotonic sequence assigned to the record
     * @param auditId unique audit record identifier
     * @param transactionId related transaction identifier, when applicable
     * @param walletId related wallet identifier, when applicable
     * @param fromStatus prior domain status, when the event represents a transition
     * @param toStatus resulting domain status, when the event represents a transition
     * @param payloadHash digest of the canonical event payload
     * @param eventHash digest linking this record into the audit chain
     * @param metadata additional sanitized, non-secret event attributes
     */
    public void persisted(
            AuditEventType eventType,
            Long sequenceNumber,
            UUID auditId,
            UUID transactionId,
            UUID walletId,
            String fromStatus,
            String toStatus,
            String payloadHash,
            String eventHash,
            Map<String, ?> metadata) {
        StructuredLogEvent event = StructuredLogEvent.of(
                eventType.name(),
                "audit",
                "persist",
                "Audit event persisted")
                .field("audit.sequence", sequenceNumber)
                .field("audit.id", auditId)
                .field("transactionId", transactionId)
                .field("walletId", walletId)
                .field("fromStatus", fromStatus)
                .field("toStatus", toStatus)
                .field("payloadHash", payloadHash)
                .field("eventHash", eventHash);

        if (metadata != null) {
            metadata.forEach((key, value) -> event.field("metadata." + key, value));
        }

        log.info(LogDomain.AUDIT, "audit.event.persisted", event.arguments());
    }
}
