package util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

public class SharedPreferenceUtil {


    /**
     * 是否是登录状态
     */
    public static boolean isLogin(Context context) {

        @SuppressLint("WrongConstant")
        SharedPreferences sp = context.getSharedPreferences("user", Context.MODE_APPEND);


        return sp.getBoolean("isLogin", false);
    }


    /**
     * 保存登录状态
     */
    public static void saveLoginInfo(Context context, JSONObject jsonObject) {
        SharedPreferences.Editor editor = context.getSharedPreferences("user", Context.MODE_PRIVATE).edit();

        editor.putBoolean("isLogin", true);

        Iterator<String> iterator = jsonObject.keys();

        while (iterator.hasNext()) {
            String key = iterator.next();
            try {
                editor.putString(key, jsonObject.getString(key));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        editor.commit();

    }


    /**
     * 是不是第一次使用app
     */
    public static boolean isFirst(Context context) {
        boolean isFirst = true;
        SharedPreferences sp = context.getSharedPreferences("user", Context.MODE_PRIVATE);
        isFirst = sp.getBoolean("isFirst", true);
        return isFirst;
    }

    public static void setIsFirst(Context context, boolean flag) {
        boolean isFirst = true;
        SharedPreferences.Editor editor = context.getSharedPreferences("user", Context.MODE_PRIVATE).edit();
        editor.putBoolean("isFirst",flag);
        editor.commit();
    }


    public static void test(Context c) {
        SharedPreferences sp = c.getSharedPreferences("user", Context.MODE_PRIVATE);
        LogUtil.Log(sp.getAll().keySet().toString());
        LogUtil.Log(sp.getAll().values().toString());
    }


}
