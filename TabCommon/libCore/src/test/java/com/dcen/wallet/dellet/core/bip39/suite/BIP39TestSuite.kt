package com.dcen.wallet.dellet.core.bip39.suite

import com.dcen.wallet.dellet.core.bip39.*
import com.dcen.wallet.dellet.core.bip39.wordlists.EnglishListContentTests
import com.dcen.wallet.dellet.core.bip39.wordlists.FrenchListContentTests
import com.dcen.wallet.dellet.core.bip39.wordlists.SpanishListContentTests
import org.junit.runner.RunWith
import org.junit.runners.Suite

/**
 * Created by「 The Khaeng 」on 1/13/2017 AD :)
 */

@RunWith(Suite::class)
@Suite.SuiteClasses(
        EnglishListContentTests::class,
        FrenchListContentTests::class,
        SpanishListContentTests::class,
        ByteUtilTests::class,
        CharSequenceSplitterTests::class,
        EnumValueValueOfCodeCoverageTests::class,
        MnemonicGenerationTests::class,
        MnemonicGenerationWordCountTests::class,
        MnemonicValidationTests::class,
        SeedCalculationFromWordListTests::class,
        SeedCalculationTests::class,
        ValidationExceptionMessagesTests::class,
        WordListMapNormalizationTests::class )
class BIP39TestSuite
