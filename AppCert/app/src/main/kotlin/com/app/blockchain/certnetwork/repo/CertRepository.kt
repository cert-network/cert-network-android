package com.app.blockchain.certnetwork.repo

import android.arch.lifecycle.LiveData
import com.app.blockchain.certnetwork.appcommon.base.repository.base.DefaultNetworkBoundResource
import com.app.blockchain.certnetwork.appcommon.base.repository.base.DefaultNetworkCacheBoundResource
import com.app.blockchain.certnetwork.appcommon.base.repository.base.DefaultRepository
import com.app.blockchain.certnetwork.appcommon.base.repository.base.database.realm.CertDatabase
import com.app.blockchain.certnetwork.appcommon.base.repository.base.model.AppResponse
import com.app.blockchain.certnetwork.common.extension.notnull
import com.app.blockchain.certnetwork.repo.network.CertApiManager
import com.app.blockchain.certnetwork.repo.network.model.reponse.AddCertResponse
import com.app.blockchain.certnetwork.repo.network.model.reponse.AddUserResponse
import com.app.blockchain.certnetwork.repo.network.model.reponse.CertListResponse
import com.app.blockchain.certnetwork.repo.network.trigger.AddCertTrigger
import com.app.blockchain.certnetwork.repo.network.trigger.AddUserTrigger
import com.app.blockchain.certnetwork.repo.network.trigger.GetCertListTrigger
import com.app.blockchain.certnetwork.repo.model.Account
import com.app.blockchain.certnetwork.repo.model.Certificate
import com.app.blockchain.certnetwork.repo.model.CertificateList
import com.lib.nextwork.engine.AppExecutors

/**
 * Created by「 The Khaeng 」on 12 Oct 2017 :)
 */

class CertRepository private constructor(
        appExecutors: AppExecutors
) : DefaultRepository(appExecutors) {

    companion object {
        @Volatile
        private var INSTANCE: CertRepository? = null

        const val REPO_REQUEST_CERT_LIST = "repo_request_cert_list"
        const val REPO_REQUEST_ADD_CERT = "repo_request_add_cert"
        const val REPO_REQUEST_ADD_USER = "repo_request_add_user"
        fun getInstance(appExecutors: AppExecutors = AppExecutors.getInstance()): CertRepository =
                INSTANCE ?: synchronized(this) {
                    INSTANCE ?: CertRepository(appExecutors).also { INSTANCE = it }
                }
    }

    fun saveAccount(account: Account) {
        database.saveModel(account)
    }

    fun loadAccount(): Account? = database.loadModel(CertDatabase.ACCOUNT_ID, Account::class.java)


    private val serviceManager: CertApiManager = CertApiManager.INSTANCE

    fun getCertList(trigger: GetCertListTrigger) = object : DefaultNetworkCacheBoundResource<CertificateList, CertListResponse>(appExecutors) {
        override
        fun saveCallResult(item: CertificateList) {
            database.saveModel(item)
        }

        override
        fun shouldFetch(oldData: CertificateList?): Boolean {
            return trigger.isForceFetch || oldData == null || oldData.shouldFetch()
        }

        override
        fun loadFromDb(): LiveData<CertificateList> {
            return database.loadModelAsLiveData(CertDatabase.CERT_ID, CertificateList::class.java)
        }

        override
        fun createCall(): LiveData<AppResponse<CertListResponse>> {
            return serviceManager.getCertList(trigger.address)
        }

        override
        fun convertToResultType(oldData: CertificateList?, response: CertListResponse?): CertificateList {
            return CertificateList(certList = response?.data?.map {
                Certificate(
                        certId = it.certId.notnull(),
                        certName = it.certName.notnull(),
                        category = it.category.notnull(),
                        desc = it.desc.notnull(),
                        issueDate = it.issueDate.notnull(),
                        expiredDate = it.expiredDate.notnull(),
                        approveBy = it.approveBy.notnull(),
                        isApprove = it.isApprove.notnull()
                )
            }?.toMutableList() ?: mutableListOf() )
        }

        override
        fun setPrefixLog(): String {
            return REPO_REQUEST_CERT_LIST
        }

        override
        fun setPayloadBack(): GetCertListTrigger? {
            return trigger
        }

        override
        fun setLoadCacheBeforeFetch(): Boolean {
            return true
        }

    }.asLiveData()


    fun addCert(trigger: AddCertTrigger) = object : DefaultNetworkBoundResource<Boolean, AddCertResponse>(appExecutors) {

        override
        fun createCall(): LiveData<AppResponse<AddCertResponse>> {
            return serviceManager.addCert(trigger.addCertBody)
        }

        override
        fun convertToResultType(response: AddCertResponse?): Boolean {
            return response?.isSuccess() ?: false
        }

        override
        fun setPrefixLog(): String {
            return REPO_REQUEST_ADD_CERT
        }

        override
        fun setPayloadBack(): AddCertTrigger? {
            return trigger
        }


    }.asLiveData()

    fun addUser(trigger: AddUserTrigger) = object : DefaultNetworkBoundResource<Boolean, AddUserResponse>(appExecutors) {

        override
        fun createCall(): LiveData<AppResponse<AddUserResponse>> {
            return serviceManager.addUser(trigger.addUserBody)
        }

        override
        fun convertToResultType(response: AddUserResponse?): Boolean {
            return response?.isSuccess() ?: false
        }

        override
        fun setPrefixLog(): String {
            return REPO_REQUEST_ADD_USER
        }

        override
        fun setPayloadBack(): AddUserTrigger? {
            return trigger
        }


    }.asLiveData()

}
