package com.app.blockchain.certnetwork.module.main

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import com.app.blockchain.certnetwork.R
import com.app.blockchain.certnetwork.appcommon.base.mvvm.AppMvvmRecyclerViewActivity
import com.app.blockchain.certnetwork.appcommon.base.repository.base.model.AppResource
import com.app.blockchain.certnetwork.appcommon.snackbar.showSnackbarError
import com.app.blockchain.certnetwork.common.base.mvvm.layer1View.holder.SpaceHolder
import com.app.blockchain.certnetwork.common.base.mvvm.layer1View.holder.SpaceItem
import com.app.blockchain.certnetwork.common.base.view.holder.base.BaseItem
import com.app.blockchain.certnetwork.common.base.view.holder.base.BaseViewHolder
import com.app.blockchain.certnetwork.module.cert.add.AddCertActivity
import com.app.blockchain.certnetwork.module.cert.detail.CertDetailActivity
import com.app.blockchain.certnetwork.module.cert.detail.CertDetailActivity.Companion.KEY_CERT
import com.app.blockchain.certnetwork.module.main.adapter.holder.CertCardHolder
import com.app.blockchain.certnetwork.module.main.adapter.holder.EmptyCertHolder
import com.app.blockchain.certnetwork.module.main.adapter.holder.ProfileHolder
import com.app.blockchain.certnetwork.module.main.adapter.item.CertCardItem
import com.app.blockchain.certnetwork.module.main.adapter.item.ProfileItem
import com.app.blockchain.certnetwork.module.main.adapter.operator.CertItemCreator
import com.app.blockchain.certnetwork.repo.model.CertificateList
import com.lib.nextwork.engine.model.NetworkStatus
import kotlinx.android.synthetic.main.activity_main.main_pull_refresh as pullRefresh
import kotlinx.android.synthetic.main.activity_main.main_recycler_view as rvCert
import kotlinx.android.synthetic.main.activity_main.main_toolbar_ic_add_cert as btnAdd


/**
 * Created by「 The Khaeng 」on 08 Oct 2017 :)
 */

class MainActivity : AppMvvmRecyclerViewActivity<BaseViewHolder<*>>() {

    companion object {
    }

    private lateinit var viewModel: MainViewModel

    override
    fun onSetupViewModel(savedInstanceState: Bundle?) {
        super.onSetupViewModel(savedInstanceState)
        viewModel = getViewModel(MainViewModel::class.java).apply {
            triggerCertList.observeData(this@MainActivity, onObserverCertList)
        }
    }

    override
    fun setupLoadmoreLayout(): Int = R.layout.holder_transaction_loadmore

    override
    fun setupLayoutView(): Int = R.layout.activity_main

    override
    fun setupRecyclerView(): RecyclerView? = rvCert

    override
    fun registerItemList(): MutableList<BaseItem> = viewModel.certItemList


    override
    fun onStart() {
        super.onStart()
        viewModel.subscribeBus(this@MainActivity, observer)
    }


    override
    fun onInitialize(savedInstanceState: Bundle?) {
        super.onInitialize(savedInstanceState)
        viewModel.requestCertList()
    }


    override
    fun onSetupView(savedInstanceState: Bundle?) {
        super.onSetupView(savedInstanceState)
        viewModel.initCertItem()
        notifyDataSetChanged()

        btnAdd.setOnClickListener {
            openActivity(AddCertActivity::class.java)
        }

        pullRefresh.setOnRefreshListener {
            viewModel.requestCertList()
        }
    }


    override
    fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        return when (viewType) {
            CertItemCreator.TYPE_PROFILE_ITEM -> ProfileHolder(parent).apply {
            }
            CertItemCreator.TYPE_CERT_ITEM -> CertCardHolder(parent).apply {
                clickListener = onClickCertListener
            }
            CertItemCreator.TYPE_EMPTY_CERT -> EmptyCertHolder(parent).apply {
            }
            SpaceItem.TYPE_SPACE -> SpaceHolder(parent).apply {
            }
            else -> super.onCreateViewHolder(parent, viewType)
        }
    }

    override
    fun onBindViewHolder(itemList: MutableList<*>, holder: BaseViewHolder<*>, pos: Int) {
        super.onBindViewHolder(itemList, holder, pos)
        val i = viewModel.certItemList[pos]
        when {
            getItemViewType(pos) == CertItemCreator.TYPE_PROFILE_ITEM -> {
                val viewHolder = holder as ProfileHolder
                val item = i as ProfileItem
                viewHolder.onBind(item)
            }
            getItemViewType(pos) == CertItemCreator.TYPE_CERT_ITEM -> {
                val viewHolder = holder as CertCardHolder
                val item = i as CertCardItem
                viewHolder.onBind(item)
            }
        }
    }

    private val onClickCertListener: BaseViewHolder.OnClickListener =
            object : BaseViewHolder.OnClickListener {
                override
                fun onClick(view: View?, position: Int) {
                    openActivity(CertDetailActivity::class.java,
                            data = bundleOf(
                                    KEY_CERT to viewModel.certItemList[position]
                            ))
                }
            }


    private val onObserverCertList: Observer<AppResource<CertificateList>> =
            Observer { resource: AppResource<CertificateList>? ->
                when {
                    resource?.status == NetworkStatus.SUCCESS -> {
                        pullRefresh.isRefreshing = false
                        adapter.showLoadmore(false)
                        val certList = resource.data?.certList
                        if (resource.isCached || certList?.isNotEmpty() == true) {
                            val newItemList = viewModel.createItemList(certList)
                            notifyDataDataWithDiffUtil(viewModel.certItemList, newItemList)
                            viewModel.certItemList = newItemList
                        } else {
                            val newItemList = viewModel.createEmptyItemList()
                            notifyDataDataWithDiffUtil(viewModel.certItemList, newItemList)
                            viewModel.certItemList = newItemList
                        }
                    }
                    resource?.status == NetworkStatus.LOADING_FROM_NETWORK -> {
                        adapter.showLoadmore(true)
                    }
                    resource?.status == NetworkStatus.ERROR -> {
                        adapter.showLoadmore(false)
                        showSnackbarError(getString(R.string.alert_service_unavailable))
                        pullRefresh.isRefreshing = false
                    }
                }
            }


    private var observer: Observer<Any> = Observer { event ->
        when (event) {
        }
    }


}

