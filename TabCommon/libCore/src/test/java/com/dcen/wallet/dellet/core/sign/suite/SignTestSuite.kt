package com.dcen.wallet.dellet.core.sign.suite

import com.dcen.wallet.dellet.core.sign.RlpDecoderTest
import com.dcen.wallet.dellet.core.sign.RlpEncoderTest
import com.dcen.wallet.dellet.core.sign.core.SignTest
import com.dcen.wallet.dellet.core.sign.etheruem.EthereumSignerTest
import org.junit.runner.RunWith
import org.junit.runners.Suite

/**
 * Created by「 The Khaeng 」on 1/13/2017 AD :)
 */

@RunWith(Suite::class)
@Suite.SuiteClasses(
        SignTest::class,
        EthereumSignerTest::class,
        RlpDecoderTest::class,
        RlpEncoderTest::class )
class SignTestSuite
