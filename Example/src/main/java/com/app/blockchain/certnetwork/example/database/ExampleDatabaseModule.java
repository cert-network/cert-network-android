package com.app.blockchain.certnetwork.example.database;


import io.realm.annotations.RealmModule;

/**
 * Created by「 The Khaeng 」on 31 Aug 2017 :)
 */

@RealmModule( library = true,
              classes = {
                      ExampleRealmObject.class
              } )
public class ExampleDatabaseModule{
}
