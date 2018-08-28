package com.app.blockchain.certnetwork.example.database;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class ExampleRealmObject extends RealmObject{
    public static final String KEY_ID = "objectId";
    public static final String KEY_CLASS = "objectClass";
    public static final String KEY_JSON = "jsonDataResult";

    @PrimaryKey
    String objectId;
    String objectClass;
    String jsonDataResult;

    public ExampleRealmObject(){
    }


    public ExampleRealmObject( String objectId,
                               String objectClass,
                               String json ){
        this.objectId = objectId;
        this.objectClass = objectClass;
        this.jsonDataResult = json;
    }

    public String getObjectId(){
        return objectId;
    }

    public String getObject(){
        return objectClass;
    }

    public String getJson(){
        return jsonDataResult;
    }

    @Override
    public String toString(){
        return "ExampleRealmObject{" +
                "objectId='" + objectId + '\'' +
                ", objectClass='" + objectClass + '\'' +
                ", jsonDataResult='" + jsonDataResult + '\'' +
                '}';
    }
}
