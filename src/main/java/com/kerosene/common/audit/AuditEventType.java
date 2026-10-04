package com.kerosene.common.audit;

import java.util.Arrays;
import java.util.Locale;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Stable domain audit taxonomy for security, operator and financial events.
 *
 * <p>Audit event names are persisted and consumed by incident-response tooling, so
 * changes must be additive. Unknown names are rejected instead of being truncated
 * or normalized into a different event.
 */
public enum AuditEventType {
    /** Records a successful user authentication. */
    AUTH_LOGIN_SUCCEEDED,
    /** Records a rejected or failed user authentication attempt. */
    AUTH_LOGIN_FAILED,
    /** Records a user ending an authenticated session. */
    AUTH_LOGOUT,
    /** Records revocation of a JWT-backed session. */
    JWT_SESSION_REVOKED,
    /** Records submission of an administrator access request. */
    ADMIN_ACCESS_REQUESTED,
    /** Records approval of an administrator access request. */
    ADMIN_ACCESS_APPROVED,
    /** Records rejection of an administrator access request. */
    ADMIN_ACCESS_REJECTED,
    /** Records redemption of an approved administrator access grant. */
    ADMIN_ACCESS_REDEEMED,
    /** Records regeneration of account recovery codes. */
    BACKUP_CODES_REGENERATED,
    /** Records creation of a KFE wallet. */
    KFE_WALLET_CREATED,
    /** Records acceptance of a transaction intent for processing. */
    KFE_TRANSACTION_INTENT,
    /** Records entry of a transaction into validation. */
    KFE_TRANSACTION_VALIDATING,
    /** Records synchronization with the transaction quorum. */
    KFE_TRANSACTION_QUORUM_SYNC,
    /** Records acquisition of the transaction execution lock. */
    KFE_TRANSACTION_LOCKED,
    /** Records the start of transaction execution. */
    KFE_TRANSACTION_EXECUTING,
    /** Records submission of a transaction to its execution network. */
    KFE_TRANSACTION_SUBMITTED,
    /** Records a conflict detected for a repeated idempotency key. */
    KFE_IDEMPOTENCY_CONFLICT,
    /** Records successful dispatch of an audit or domain outbox event. */
    KFE_OUTBOX_DISPATCHED,
    /** Records a retry scheduled for an outbox event. */
    KFE_OUTBOX_RETRY,
    /** Records successful settlement of a transaction. */
    KFE_SETTLEMENT_COMPLETED,
    /** Records failed settlement of a transaction. */
    KFE_SETTLEMENT_FAILED,
    /** Binary settlement gate outcome: map of V_* flags 0/1 + reasons (forensic). */
    KFE_SETTLEMENT_GATE,
    /** Channel lifecycle binary decision (open/rebal/close/ppm). */
    KFE_CHANNEL_DECISION,
    /** Records crediting of an inbound transfer. */
    KFE_INBOUND_CREDITED,
    /** Records rejection of an inbound transfer already processed. */
    KFE_INBOUND_DUPLICATE_REJECTED,
    /** Records successful validation of a vault attestation. */
    VAULT_ATTESTATION_SUCCEEDED,
    /** Records failed validation of a vault attestation. */
    VAULT_ATTESTATION_FAILED,
    /** Records rejection of a multiparty computation signing request. */
    MPC_SIGN_REJECTED,
    /** Records rejection of an unsupported MPC signing mode. */
    MPC_UNSUPPORTED_MODE_REJECTED,

    /** Records archival of a wallet. */
    KFE_WALLET_ARCHIVED,
    /** Records rotation of a wallet deposit address. */
    KFE_WALLET_ADDRESS_ROTATED,
    /** Records an operator or system balance adjustment. */
    KFE_WALLET_BALANCE_ADJUSTED,
    /** Records creation of a payment request. */
    KFE_PAYMENT_REQUEST_CREATED,
    /** Records observation of an incoming payment request transfer. */
    KFE_PAYMENT_REQUEST_OBSERVED,
    /** Records payment of a payment request. */
    KFE_PAYMENT_REQUEST_PAID,
    /** Records expiration of a payment request. */
    KFE_PAYMENT_REQUEST_EXPIRED,
    /** Records settlement of a transaction. */
    KFE_TRANSACTION_SETTLED,
    /** Records failure of a transaction. */
    KFE_TRANSACTION_FAILED,
    /** Records dispatch of an execution request. */
    KFE_EXECUTION_DISPATCHED,
    /** Records an execution failure eligible for retry. */
    KFE_EXECUTION_RETRYABLE_FAILURE,
    /** Records an execution failure that will not be retried. */
    KFE_EXECUTION_FINAL_FAILURE,
    /** Records a transaction that needs manual or automated reconciliation. */
    KFE_TRANSACTION_REQUIRES_RECONCILIATION,
    /** Records settlement of an inbound transfer. */
    KFE_INBOUND_SETTLED,
    /** Records settlement of a Kerosene fee. */
    KFE_KEROSENE_FEE_SETTLED,
    /** Records cancellation of a payment request. */
    KFE_PAYMENT_REQUEST_CANCELLED,
    /** Records hiding a payment request from its normal user view. */
    KFE_PAYMENT_REQUEST_HIDDEN,
    /** Records creation of a partially signed Bitcoin transaction. */
    KFE_PSBT_CREATED,
    /** Records signing of a partially signed Bitcoin transaction. */
    KFE_PSBT_SIGNED,
    /** Records rejection of a partially signed Bitcoin transaction. */
    KFE_PSBT_REJECTED,
    /** Records creation of a PSBT workflow. */
    KFE_PSBT_WORKFLOW_CREATED,
    /** Records signing within a PSBT workflow. */
    KFE_PSBT_WORKFLOW_SIGNED,
    /** Records broadcast of a transaction from a PSBT workflow. */
    KFE_PSBT_WORKFLOW_BROADCAST,
    /** Records broadcast of an on-chain outbound transaction. */
    KFE_ONCHAIN_OUTBOUND_BROADCAST,
    /** Records creation of a cold-wallet PSBT. */
    KFE_COLD_WALLET_PSBT_CREATED,
    /** Records a wallet creation failure. */
    KFE_WALLET_CREATE_FAILED,
    /** Records restoration of a wallet's prior operational status. */
    KFE_WALLET_STATUS_RESTORED,
    /** Records an update to wallet metadata or configuration. */
    KFE_WALLET_UPDATED;

    private static final int MAX_NAME_LENGTH = 96;
    private static final Map<String, AuditEventType> BY_NAME = Arrays.stream(values())
            .collect(Collectors.toUnmodifiableMap(AuditEventType::name, Function.identity()));

    /** Resolves an input name to a known stable audit event identifier.
     *
     * @param value event name; surrounding whitespace and letter case are ignored
     * @return the matching event type
     * @throws IllegalArgumentException if the name is blank, too long, or unknown
     */
    public static AuditEventType requireKnown(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Audit event type is required");
        }
        String normalized = value.trim().toUpperCase(Locale.ROOT);
        AuditEventType eventType = BY_NAME.get(normalized);
        if (eventType == null) {
            throw new IllegalArgumentException("Unknown audit event type");
        }
        if (normalized.length() > MAX_NAME_LENGTH) {
            throw new IllegalArgumentException("Audit event type is too long");
        }
        return eventType;
    }
}
