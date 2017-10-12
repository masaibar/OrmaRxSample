package com.masaibar.ormarxjavasample;

import android.app.Application;
import android.content.Context;

import com.facebook.stetho.Stetho;


public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ThreadUtil.setup();
        initStetho(this);
    }

    private void initStetho(Context context) {
        Stetho.initialize(
                Stetho.newInitializerBuilder(context)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(context))
                        .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(context))
                        .build()
        );
    }
}
