package com.kerosene.common.domain;

import com.kerosene.common.service.AddressDerivationService.DerivedAddress;

/** Existing Shared derivation contract required by AddressDerivationService. */
public interface AddressDerivationPort {
    String deriveAddress(Long walletId, String passphraseHash);

    DerivedAddress deriveAddressDetailsFromXpub(String xpub, int index, boolean change);
}
