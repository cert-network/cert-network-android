package com.app.blockchain.certnetwork.appcommon.base.repository.base.database.realm.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class DefaultRealmObject extends RealmObject{
    public static final String FIELD_ID_NAME = "objectId";

    @PrimaryKey
    private String objectId;
    private String jsonDataResult;

    public DefaultRealmObject(){
    }


    public DefaultRealmObject(String objectId,
                              String json ){
        this.objectId = objectId;
        this.jsonDataResult = json;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public void setJsonDataResult(String jsonDataResult) {
        this.jsonDataResult = jsonDataResult;
    }

    public String getObjectId(){
        return objectId;
    }


    public String getJson(){
        return jsonDataResult;
    }

    public String getJsonDataResult() {
        return jsonDataResult;
    }
}
