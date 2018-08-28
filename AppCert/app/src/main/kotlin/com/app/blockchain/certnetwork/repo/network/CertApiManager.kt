package com.app.blockchain.certnetwork.repo.network

import android.arch.lifecycle.LiveData
import com.app.blockchain.certnetwork.appcommon.URL
import com.app.blockchain.certnetwork.appcommon.base.repository.base.ConvertResponseToAppResponse
import com.app.blockchain.certnetwork.appcommon.base.repository.base.model.AppResponse
import com.app.blockchain.certnetwork.appcommon.constant.GET_WITH_BASE_PREFIX
import com.app.blockchain.certnetwork.appcommon.constant.POST_WITH_BASE_PREFIX
import com.app.blockchain.certnetwork.repo.network.api.CertApiService
import com.app.blockchain.certnetwork.repo.network.api.CertUrl
import com.app.blockchain.certnetwork.repo.network.model.reponse.AddCertResponse
import com.app.blockchain.certnetwork.repo.network.model.reponse.AddUserResponse
import com.app.blockchain.certnetwork.repo.network.model.reponse.CertListResponse
import com.app.blockchain.certnetwork.repo.network.model.request.AddCertBody
import com.app.blockchain.certnetwork.repo.network.model.request.AddUserBody
import com.lib.nextwork.engine.NextworkLiveDataConverter
import com.lib.nextwork.engine.model.ResultChecker
import com.lib.nextwork.operator.NextworkLogError
import io.reactivex.Single
import retrofit2.Response

/**
 * Created by「 The Khaeng 」on 12 Oct 2017 :)
 */

class CertApiManager {

    private object Holder {
        val INSTANCE = CertApiManager()
    }

    companion object {
        val INSTANCE: CertApiManager by lazy { Holder.INSTANCE }
    }


    fun getCertList(address: String): LiveData<AppResponse<CertListResponse>> {
        return NextworkLiveDataConverter.convert(
                getCertListSingle(address)
                        .compose(ConvertResponseToAppResponse<Response<CertListResponse>, AppResponse<CertListResponse>>()))
    }

    fun addCert(body: AddCertBody): LiveData<AppResponse<AddCertResponse>> {
        return NextworkLiveDataConverter.convert(
                addCertSingle(body)
                        .compose(ConvertResponseToAppResponse<Response<AddCertResponse>, AppResponse<AddCertResponse>>()))
    }

    fun addUser(body: AddUserBody): LiveData<AppResponse<AddUserResponse>> {
        return NextworkLiveDataConverter.convert(
                addUserSingle(body)
                        .compose(ConvertResponseToAppResponse<Response<AddUserResponse>, AppResponse<AddUserResponse>>()))
    }

    private fun getCertListSingle(address: String): Single<Response<CertListResponse>> {
        return CertApiService.newInstance
                .createApi(URL.BASE)
                .getCertList(address)
                .compose(ResultChecker<CertListResponse>())
                .onErrorResumeNext(NextworkLogError(GET_WITH_BASE_PREFIX + CertUrl.GET_CERT))
    }

    private fun addCertSingle(body: AddCertBody): Single<Response<AddCertResponse>> {
        return CertApiService.newInstance
                .createApi(URL.BASE)
                .addCert(body)
                .compose(ResultChecker<AddCertResponse>())
                .onErrorResumeNext(NextworkLogError(POST_WITH_BASE_PREFIX + CertUrl.ADD_CERT))
    }


    private fun addUserSingle(body: AddUserBody): Single<Response<AddUserResponse>> {
        return CertApiService.newInstance
                .createApi(URL.BASE)
                .addUser(body)
                .compose(ResultChecker<AddUserResponse>())
                .onErrorResumeNext(NextworkLogError(POST_WITH_BASE_PREFIX + CertUrl.ADD_USER))
    }


}
