package com.empresa.chamados;

import android.app.Application;
import android.util.Log;
import com.parse.Parse;

public class ChamadosApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        try {
            Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("PXp6UKZNvVJ9aJ6qprcUHfczc4wnrWlzkJPVg6Ye")
                .clientKey("BF4bMQVJya1BCPVdK1lp6nA4R529WzV4sunr21Ze")
                .server("https://parseapi.back4app.com")
                .build()
            );
        } catch (Exception e) {
            Log.e("ChamadosApp", "Erro ao inicializar Parse", e);
        }
    }
}
