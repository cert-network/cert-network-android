package com.dcen.wallet.dellet.core.suite

import com.dcen.wallet.dellet.core.bip39.suite.BIP39TestSuite
import com.dcen.wallet.dellet.core.sign.suite.SignTestSuite
import org.junit.runner.RunWith
import org.junit.runners.Suite

/**
 * Created by「 The Khaeng 」on 1/13/2017 AD :)
 */

@RunWith(Suite::class)
@Suite.SuiteClasses(
        SignTestSuite::class,
        BIP39TestSuite::class )
class AllTestSuite
