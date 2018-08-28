package com.lib.nextwork.engine.model;

/**
 * Created by「 The Khaeng 」on 12 Oct 2017 :)
 */

public abstract class NextworkModel{

    private String databaseId;

    public NextworkModel( String id ){
        this.databaseId = id;
    }

    public boolean shouldFetch(){
        return false;
    }

    public String getDatabaseId(){
        return databaseId;
    }

    public void setDatabaseId(String id ){
        this.databaseId = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NextworkModel that = (NextworkModel) o;

        return getDatabaseId() != null ? getDatabaseId().equals(that.getDatabaseId()) : that.getDatabaseId() == null;
    }

    @Override
    public int hashCode() {
        return getDatabaseId() != null ? getDatabaseId().hashCode() : 0;
    }
}
