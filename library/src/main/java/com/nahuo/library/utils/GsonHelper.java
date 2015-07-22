package com.nahuo.library.utils;


import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.text.MessageFormat;

/**
 * Created by ZZB on 2015/6/4 11:20
 */
public class GsonHelper {

    private static final String TAG = "GsonHelper";
    private static Gson sGson;

    private static Gson getGson() {
        if (sGson == null) {
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.excludeFieldsWithoutExposeAnnotation();
            sGson = gsonBuilder.create();
        }
        return sGson;
    }


    /**
     * 将对象转换为Json
     * @param srcObj 转换的对象
     * @return Json字符串
     */
    public static String objectToJson(Object srcObj) {
        String strJson = "";
        try {
            strJson = getGson().toJson(srcObj);
        } catch (Exception ex) {
            Log.e(TAG, MessageFormat.format("{0}->{1}方法发生异常：{2}", TAG,
                    "objectToJson", ex.getMessage()));
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
        return strJson;
    }


    /**
     * 将Json格式的字符串转换为对象
     * @param json Json格式字符串
     * @param clazz 对象类型
     * @param <T> 要转换的类型
     * @return 转换的对象
     */
    public static <T> T jsonToObject(String json, Class<T> clazz) {
        try {
            T t = getGson().fromJson(json, clazz);
            return t;
        } catch (Exception ex) {
            Log.e(TAG, MessageFormat.format("{0}->{1}方法发生异常：{2}", TAG,
                    "jsonToObject", "json:" + json + "\n" + ex.getMessage()));
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }


    /**
     * 将Json格式的字符串转换为泛型集合
     * @param json Json格式字符串
     * @param token TypeToken
     * @param <T> 要转换的类型
     * @return 转换的对象
     */
    public static <T> T jsonToObject(String json, TypeToken<T> token) {
        try {
            T t = getGson().fromJson(json, token.getType());
            return t;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

}

