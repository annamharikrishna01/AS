package com.hk.Components;

import android.util.Base64;

/**
 * Created by Hari on 11/25/13.
 */
public class Encrypt {

    public String BasicBase64(String pass) {
        String ret="Basic "+ Base64.encodeToString(pass.getBytes(), Base64.URL_SAFE | Base64.NO_WRAP);
        return ret;
    }
}
