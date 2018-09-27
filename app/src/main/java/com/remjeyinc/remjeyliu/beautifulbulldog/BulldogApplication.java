package com.remjeyinc.remjeyliu.beautifulbulldog;

import android.app.Application;

import io.realm.Realm;

public class BulldogApplication extends Application {
    @Override
    public void onCreate(){
        super.onCreate();
        Realm.init(this);
    }
}
