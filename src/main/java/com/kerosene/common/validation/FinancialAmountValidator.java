package com.kerosene.common.validation;

import java.math.BigDecimal;

/** Applies shared precision, positivity, and supply-limit rules to BTC values. */
public final class FinancialAmountValidator {

    /** Number of decimal places supported by BTC amounts (one satoshi precision). */
    public static final int BTC_SCALE = 8;
    /** Maximum Bitcoin supply, used as an upper bound for an individual BTC value. */
    public static final BigDecimal MAX_BTC_AMOUNT = new BigDecimal("21000000.00000000");

    /** Prevents construction of this static validation utility. */
    private FinancialAmountValidator() {
    }

    /** Requires a non-null, strictly positive BTC amount within supported precision.
     * @param amount amount to validate
     * @param fieldName field label included in validation errors
     * @throws IllegalArgumentException if null, non-positive, over-precision, or over the supply bound
     */
    public static void requirePositiveBtc(BigDecimal amount, String fieldName) {
        if (amount == null) {
            throw new IllegalArgumentException(fieldName + " is required.");
        }
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(fieldName + " must be greater than zero.");
        }
        requireBtcPrecision(amount, fieldName);
    }

    /** Requires a non-null BTC adjustment whose magnitude is non-zero and representable.
     * @param amount signed adjustment to validate
     * @param fieldName field label included in validation errors
     * @throws IllegalArgumentException if null, zero, over-precision, or over the supply bound
     */
    public static void requireNonZeroBtcDelta(BigDecimal amount, String fieldName) {
        if (amount == null) {
            throw new IllegalArgumentException(fieldName + " is required.");
        }
        if (amount.compareTo(BigDecimal.ZERO) == 0) {
            throw new IllegalArgumentException(fieldName + " must not be zero.");
        }
        requireBtcPrecision(amount.abs(), fieldName);
    }

    /** Ensures an amount uses at most one-satoshi precision and stays within the supply bound.
     * @param amount absolute amount to validate
     * @param fieldName field label included in validation errors
     * @throws IllegalArgumentException if decimal scale or magnitude exceeds the supported limit
     */
    public static void requireBtcPrecision(BigDecimal amount, String fieldName) {
        if (amount.scale() > BTC_SCALE) {
            throw new IllegalArgumentException(fieldName + " supports at most 8 decimal places.");
        }
        if (amount.abs().compareTo(MAX_BTC_AMOUNT) > 0) {
            throw new IllegalArgumentException(fieldName + " exceeds the maximum supported BTC amount.");
        }
    }
}
