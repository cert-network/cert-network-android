package com.dcen.wallet.dellet.core

import com.dcen.wallet.dellet.core.bip32.ExtendedPrivateKey
import com.dcen.wallet.dellet.core.bip32.Path
import com.dcen.wallet.dellet.core.bip39.*
import com.dcen.wallet.dellet.core.bip39.validation.InvalidChecksumException
import com.dcen.wallet.dellet.core.bip39.validation.InvalidWordCountException
import com.dcen.wallet.dellet.core.bip39.validation.UnexpectedWhiteSpaceException
import com.dcen.wallet.dellet.core.bip39.validation.WordNotFoundException
import com.dcen.wallet.dellet.core.bip39.wordlists.English
import com.dcen.wallet.dellet.core.bip44.AddressIndex
import com.dcen.wallet.dellet.core.bip44.BIP44
import java.security.SecureRandom


/**
 * Created by「 The Khaeng 」on 03 Jul 2018 :)
 */
class WalletAccountManager {

    fun generateMnemonicKey(wordList: WordList): CharSequence {
        val entropy = ByteArray(Words.FIFTEEN.byteLength())
        SecureRandom().nextBytes(entropy)
        return MnemonicGenerator
                .createMnemonic(wordList, entropy)
    }

    fun validateMnemonic(mnemonic: String): Boolean {
        try {
            MnemonicValidator
                    .ofWordList(English.INSTANCE)
                    .validate(mnemonic)
        } catch (e: UnexpectedWhiteSpaceException) {
            return false
        } catch (e: InvalidWordCountException) {
            return false
        } catch (e: InvalidChecksumException) {
            return false
        } catch (e: WordNotFoundException) {
            return false
        }
        return true
    }

    fun createDerivePath(coinType: Int, addressIndex: Int): AddressIndex {
        return BIP44.m()
                .purpose44()
                .coinType(coinType)
                .account(0)
                .external()
                .address(addressIndex)
    }

    fun getPrivateKey(path: Path, mnemonic: String): ExtendedPrivateKey? {
        if (!validateMnemonic(mnemonic)) return null
        val seed = SeedCalculator().calculateSeed(mnemonic, "")
        return ExtendedPrivateKey
                .fromSeed(seed)
                .derive(path)
    }

    fun getPrivateKey(path: String, mnemonic: String): ExtendedPrivateKey? {
        if (!validateMnemonic(mnemonic)) return null
        val seed = SeedCalculator().calculateSeed(mnemonic, "")
        return ExtendedPrivateKey
                .fromSeed(seed)
                .derive(path)
    }

}