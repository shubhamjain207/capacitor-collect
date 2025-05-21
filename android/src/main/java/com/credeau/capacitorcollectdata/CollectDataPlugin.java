package com.credeau.capacitorcollectdata;

import android.util.Log;

public class CollectDataPlugin {

    public String echo(String value) {
        Log.i("Echo", value);
        return value;
    }
}
