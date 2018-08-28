package com.app.blockchain.certnetwork.repo.network.api

import com.app.blockchain.certnetwork.repo.network.model.reponse.AddCertResponse
import com.app.blockchain.certnetwork.repo.network.model.reponse.AddUserResponse
import com.app.blockchain.certnetwork.repo.network.model.reponse.CertListResponse
import com.app.blockchain.certnetwork.repo.network.model.request.AddCertBody
import com.app.blockchain.certnetwork.repo.network.model.request.AddUserBody
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

/**
 * Created by「 The Khaeng 」on 29 Sep 2017 :)
 */
interface CertApi {

    @GET(CertUrl.GET_CERT)
    fun getCertList(@Path("address") address: String): Single<Response<CertListResponse>>

    @POST(CertUrl.ADD_CERT)
    fun addCert(@Body body: AddCertBody): Single<Response<AddCertResponse>>

    @POST(CertUrl.ADD_USER)
    fun addUser(@Body body: AddUserBody): Single<Response<AddUserResponse>>
}