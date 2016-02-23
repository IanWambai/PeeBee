package com.toe.firstcustoma.classes;

import com.orm.SugarApp;
import com.toe.firstcustoma.utils.ParseUtils;

/**
 * Created by Wednesday on 12/16/2015.
 */
public class Application extends SugarApp {

    private static Application mInstance;

    @Override
    public void onCreate() {
        super.onCreate();

        ParseUtils.registerParse(this);
    }

    public static synchronized Application getInstance() {
        return mInstance;
    }
}
