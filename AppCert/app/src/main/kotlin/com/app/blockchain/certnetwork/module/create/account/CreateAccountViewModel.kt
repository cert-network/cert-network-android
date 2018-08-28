package com.app.blockchain.certnetwork.module.create.account


import com.app.blockchain.certnetwork.appcommon.base.mvvm.AppSharedViewModel
import com.app.blockchain.certnetwork.appcommon.base.repository.base.model.AppResource
import com.app.blockchain.certnetwork.repo.network.model.request.AddUserBody
import com.app.blockchain.certnetwork.repo.network.trigger.AddUserTrigger
import com.app.blockchain.certnetwork.repo.CertRepository
import com.app.blockchain.certnetwork.repo.model.Account
import com.dcen.wallet.dellet.core.WalletAccountManager
import com.dcen.wallet.dellet.core.bip39.wordlists.English
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType
import com.lib.nextwork.engine.steam.TriggerObjectDataStream

class CreateAccountViewModel(
        var account: Account = Account(),
        val walletManager: WalletAccountManager = WalletAccountManager(),
        var repo: CertRepository = CertRepository.getInstance()
) : AppSharedViewModel() {

    val triggerAddUser =
            TriggerObjectDataStream<AddUserTrigger, AppResource<Boolean>>()
                    .initSwitchMap {
                        repo.addUser(it)
                    }

    fun createEthAddress() {
        val mnemonic = walletManager.generateMnemonicKey(English.INSTANCE).toString()
        val path = walletManager.createDerivePath(BIP44CoinType.ETH, 0)

        val privateKey = walletManager.getPrivateKey(path.toString(), mnemonic)
//        privateKey?.key.toString()
//        privateKey?.neuter()?.address
        privateKey?.apply {
            //NOTE: You don't send private key though internet this case is for hackathon only :)
//            account.privateKey = "your-private-key"
//            account.address = "your-address"
            repo.saveAccount(account)
        }
    }

    fun createAccount() {
        triggerAddUser.trigger(
                AddUserTrigger(
                        AddUserBody(
                                name = account.name,
                                passport = "AA00000000",
                                private = account.privateKey,
                                address = account.address
                        )
                ))
    }

}
