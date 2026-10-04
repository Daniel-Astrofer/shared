package com.kerosene.common.domain;

import com.kerosene.common.service.AddressDerivationService.DerivedAddress;

/** Domain port for deterministic address derivation. */
public interface AddressDerivationPort {
    /** Derives the wallet's deterministic deposit address from its ID and key hash.
     * @param walletId persistent wallet identifier used as derivation input
     * @param passphraseHash password hash used as additional deterministic entropy
     * @return network-valid address string
     */
    String deriveAddress(Long walletId, String passphraseHash);

    /** Derives address and public-key metadata from an extended public key.
     * @param xpub account or branch extended public key
     * @param index non-hardened child address index
     * @param change whether to derive from the change branch rather than receive branch
     * @return address, public key, index, and branch metadata
     */
    DerivedAddress deriveAddressDetailsFromXpub(String xpub, int index, boolean change);
}
