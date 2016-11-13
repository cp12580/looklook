package com.aaron.utils;

import android.content.Context;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Administrator on 2016/10/7.
 */
public class Util {
    public static void T(Context context, String toastMsg){
        Toast.makeText(context,toastMsg,Toast.LENGTH_SHORT).show();
    }
    public static void L(String log){
        Log.d("Aaron",log);
    }
    public static boolean isNetConn(Context c){
        ConnectivityManager cm = (ConnectivityManager) c.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        return info != null && info.isAvailable();
    }
    public static float dpToPx(float dp, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return px;
    }
}
