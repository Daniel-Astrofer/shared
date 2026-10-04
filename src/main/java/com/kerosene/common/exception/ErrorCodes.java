package com.kerosene.common.exception;

/**
 * Standardized Error Codes for the Kerosene Platform.
 * Used by the Flutter frontend to identify specific error states.
 */
public final class ErrorCodes {

    /** Prevents construction of this constants-only catalog. */
    private ErrorCodes() {}

    // Authentication Errors
    /** Authentication failed because the requested account name is already registered. */
    public static final String AUTH_USER_ALREADY_EXISTS = "AUTH_001";
    /** Authentication request omitted the username. */
    public static final String AUTH_USERNAME_NULL = "AUTH_002";
    /** Authentication request omitted the passphrase. */
    public static final String AUTH_PASSPHRASE_NULL = "AUTH_003";
    /** Username contains a character outside the accepted policy. */
    public static final String AUTH_INVALID_USERNAME_CHAR = "AUTH_004";
    /** Username or passphrase exceeds its configured character limit. */
    public static final String AUTH_CHARACTER_LIMIT = "AUTH_005";
    /** No account matched the supplied user identifier. */
    public static final String AUTH_USER_NOT_FOUND = "AUTH_006";
    /** Supplied passphrase has an invalid format or policy. */
    public static final String AUTH_INVALID_PASSPHRASE = "AUTH_007";
    /** One-time authenticator code did not verify. */
    public static final String AUTH_INCORRECT_TOTP = "AUTH_008";
    /** Username/password pair could not be authenticated. */
    public static final String AUTH_INVALID_CREDENTIALS = "AUTH_009";
    /** Login was attempted from a device that is not recognized or trusted. */
    public static final String AUTH_UNRECOGNIZED_DEVICE = "AUTH_010";
    /** The submitted time-based one-time password is outside its accepted time window. */
    public static final String AUTH_TOTP_TIMEOUT = "AUTH_011";
    /** Passkey ceremony is missing or has an invalid challenge. */
    public static final String AUTH_PASSKEY_CHALLENGE = "AUTH_012";
    /** The authenticated session has expired and must be renewed. */
    public static final String AUTH_SESSION_EXPIRED = "AUTH_013";
    /** A passkey must be linked to the account before it can be used. */
    public static final String AUTH_PASSKEY_LINK_REQUIRED = "AUTH_014";
    /** Passkey assertion signature or challenge verification failed. */
    public static final String AUTH_PASSKEY_ASSERTION_FAILED = "AUTH_015";
    /** Passkey assertion counter indicates a replay. */
    public static final String AUTH_PASSKEY_REPLAY = "AUTH_016";
    /** Requested passkey credential is not registered to the account. */
    public static final String AUTH_PASSKEY_CREDENTIAL_NOT_FOUND = "AUTH_017";
    /** The app PIN has not been configured for this account or device. */
    public static final String AUTH_APP_PIN_NOT_CONFIGURED = "AUTH_018";
    /** The supplied app PIN is incorrect or malformed. */
    public static final String AUTH_APP_PIN_INVALID = "AUTH_019";
    /** PIN verification is temporarily blocked after repeated failures. */
    public static final String AUTH_APP_PIN_LOCKED = "AUTH_020";
    /** The request requires a device identity bound to the account. */
    public static final String AUTH_APP_PIN_DEVICE_REQUIRED = "AUTH_021";
    /** The request origin does not match the registered passkey origin. */
    public static final String AUTH_PASSKEY_INVALID_ORIGIN = "AUTH_022";
    /** Strong transactional authentication is required before this operation. */
    public static final String AUTH_TRANSACTIONAL_AUTH_REQUIRED = "AUTH_023";
    /** Device already bound to another account; client must confirm unlink. */
    public static final String AUTH_DEVICE_ALREADY_BOUND = "AUTH_024";
    /**
     * Soft-lock after repeated signature-counter replay failures on the same credential.
     * Not the same as AUTH_016 (single replay rejection).
     */
    public static final String AUTH_DEVICE_CRED_REPLAY_LOCKED = "AUTH_025";

    // Semantic aliases (device credential naming — same wire values where applicable)
    /** Device-credential alias for {@link #AUTH_PASSKEY_CHALLENGE}. */
    public static final String AUTH_DEVICE_CRED_CHALLENGE = AUTH_PASSKEY_CHALLENGE;
    /** Device-credential alias for {@link #AUTH_PASSKEY_ASSERTION_FAILED}. */
    public static final String AUTH_DEVICE_CRED_ASSERTION = AUTH_PASSKEY_ASSERTION_FAILED;
    /** Device-credential alias for {@link #AUTH_PASSKEY_REPLAY}. */
    public static final String AUTH_DEVICE_CRED_REPLAY = AUTH_PASSKEY_REPLAY;
    /** Device-credential alias for {@link #AUTH_PASSKEY_CREDENTIAL_NOT_FOUND}. */
    public static final String AUTH_DEVICE_CRED_NOT_FOUND = AUTH_PASSKEY_CREDENTIAL_NOT_FOUND;
    /** Device-credential alias for {@link #AUTH_TRANSACTIONAL_AUTH_REQUIRED}. */
    public static final String AUTH_DEVICE_CRED_REQUIRED = AUTH_TRANSACTIONAL_AUTH_REQUIRED;

    /** Generic authentication failure without a more specific public code. */
    public static final String AUTH_GENERIC = "AUTH_099";

    // Ledger Errors
    /** The requested ledger account or ledger resource does not exist. */
    public static final String LEDGER_NOT_FOUND = "LEDGER_001";
    /** The receiving ledger account could not be found. */
    public static final String LEDGER_RECEIVER_NOT_FOUND = "LEDGER_002";
    /** Creation was rejected because the ledger resource already exists. */
    public static final String LEDGER_ALREADY_EXISTS = "LEDGER_003";
    /** The account balance is insufficient to complete the requested debit. */
    public static final String LEDGER_INSUFFICIENT_BALANCE = "LEDGER_004";
    /** The requested ledger operation is invalid for the current state or inputs. */
    public static final String LEDGER_INVALID_OPERATION = "LEDGER_005";
    /** The requested payment record does not exist. */
    public static final String LEDGER_PAYMENT_NOT_FOUND = "LEDGER_006";
    /** The payment request is no longer valid because its expiry time passed. */
    public static final String LEDGER_PAYMENT_EXPIRED = "LEDGER_007";
    /** The payment has already been settled and cannot be paid again. */
    public static final String LEDGER_PAYMENT_ALREADY_PAID = "LEDGER_008";
    /** A payment from an account to itself is not allowed. */
    public static final String LEDGER_PAYMENT_SELF_PAY = "LEDGER_009";
    /**
     * Lightning outbound targeted a custodial invoice owned by this platform.
     * Clients must settle via INTERNAL ledger + paymentRequestPublicId instead of LND self-pay.
     */
    public static final String LEDGER_PLATFORM_LIGHTNING_DENIED = "LEDGER_010";
    /** Generic ledger failure without a more specific public code. */
    public static final String LEDGER_GENERIC = "LEDGER_099";

    // Wallet Errors
    /** A wallet with the requested name already exists. */
    public static final String WALLET_NAME_EXISTS = "WALLET_001";
    /** Requested wallet does not exist or is not visible to the caller. */
    public static final String WALLET_NOT_FOUND = "WALLET_002";
    /** Generic wallet failure without a more specific public code. */
    public static final String WALLET_GENERIC = "WALLET_099";

    // Hydra / Quorum Errors
    /** A quorum of signing or coordination nodes did not respond in time. */
    public static final String HYDRA_QUORUM_TIMEOUT = "HYDRA_001";
    /** Vault could not persist or retrieve protected signing state. */
    public static final String VAULT_STORAGE_ERROR = "VAULT_001";
    /** Generic key-recovery-service business failure. */
    public static final String KRS_BUSINESS_ERROR = "KRS_099";

    // System / Infrastructure Errors
    /** One or more request arguments are malformed or inconsistent. */
    public static final String SYS_INVALID_ARGUMENTS = "SYS_001";
    /** The HTTP method is unsupported for the requested endpoint. */
    public static final String SYS_METHOD_NOT_ALLOWED = "SYS_002";
    /** The requested system resource or route was not found. */
    public static final String SYS_NOT_FOUND = "SYS_404";
    /** An unexpected internal system error prevented completion. */
    public static final String SYS_INTERNAL_ERROR = "SYS_500";
}
