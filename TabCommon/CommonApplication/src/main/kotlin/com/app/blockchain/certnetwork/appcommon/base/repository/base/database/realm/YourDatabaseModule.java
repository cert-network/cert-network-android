package com.app.blockchain.certnetwork.appcommon.base.repository.base.database.realm;


import com.app.blockchain.certnetwork.appcommon.base.repository.base.database.realm.model.DefaultRealmObject;

import io.realm.annotations.RealmModule;

/**
 * Created by「 The Khaeng 」on 31 Aug 2017 :)
 */

@RealmModule( library = true,
              classes = {
                      DefaultRealmObject.class
              } )
public class YourDatabaseModule {
}
