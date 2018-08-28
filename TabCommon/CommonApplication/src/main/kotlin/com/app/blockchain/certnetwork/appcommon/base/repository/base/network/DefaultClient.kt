package com.app.blockchain.certnetwork.appcommon.base.repository.base.network

import com.app.blockchain.certnetwork.common.BuildConfig
import com.lib.nextwork.engine.NextworkClient
import okhttp3.OkHttpClient
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

/**
 * Created by「 The Khaeng 」on 29 Sep 2017 :)
 */

object DefaultClient : NextworkClient() {


    override
    fun getDefaultTimeoutMillis(): Long {
        return 20000
    }


    override
    fun onConfigBuilder(builder: OkHttpClient.Builder?) {
        super.onConfigBuilder(builder)
        if (BuildConfig.DEBUG) {
            try {
                val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
                    override
                    fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {
                    }

                    override
                    fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {
                    }

                    override
                    fun getAcceptedIssuers(): Array<X509Certificate> {
                        return arrayOf()
                    }
                })

                // Install the all-trusting trust manager
                val sslContext = SSLContext.getInstance("SSL")
                sslContext.init(null, trustAllCerts, java.security.SecureRandom())

                // Create an ssl socket factory with our all-trusting manager
                val sslSocketFactory = sslContext.socketFactory

                builder?.sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
                builder?.hostnameVerifier { _, _ -> true }
            } catch (e: Exception) {
                throw RuntimeException(e)
            }
        }
    }



}
