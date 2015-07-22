package com.nahuo.library.utils;

import android.content.Context;
import android.text.TextUtils;

import java.text.MessageFormat;

public class ImageUrlExtends {


    /**
     * 获取exif地址，此地址可获取图片信息
     * @param url 原图地址
     * @return exif地址
     */
    public static String getExifUrl(String url){
        String fullUrl = getImageUrl(url);
        if(fullUrl.contains("img4") || fullUrl.contains("upyun")){
            fullUrl = fullUrl + "!exif";
        }else{
            fullUrl = "";
        }
        return fullUrl;
    }



    /**
     * 获取图片略缩图地址
     * @param source 原图片路径
     * @param type 缩略图大小，从1-24
     * @return 图片略缩图地址
     */
    public static String getImageUrl(String source, int type) {

        if (TextUtils.isEmpty(source))
            return "";

        if (type < 1) {
            type = 1;
        } else if (type > 24) {
            type = 24;
        }

        if (source.startsWith("/u")) {
            return MessageFormat.format("http://img4.nahuo.com{0}!{1}", source, convertImgRule(type, 0));
        } else if (source.contains("/shop/logo/uid/")) {
            return MessageFormat.format("{0}/{1}", source, convertImgRule(type, 4));
        } else if (source.startsWith("upyun:")) {
            String[] datas = source.split(":");
            if (datas == null || datas.length != 3)
                return source;

            if (datas[1].equals("item-img") || datas[1].equals("banwo-img-server")) {
                return MessageFormat.format("http://{0}.b0.upaiyun.com{1}!{2}", datas[1], datas[2],
                        convertImgRule(type, 1));
            } else {
                return MessageFormat.format("http://{0}.b0.upaiyun.com{1}!{2}", datas[1], datas[2],
                        convertImgRule(type, 0));
            }
        } else if (source.startsWith("http://nahuo-img-server.b0.upaiyun.com/")) {
            return MessageFormat.format("{0}!{1}", source, convertImgRule(type, 0));
        } else if (source.startsWith("http://image10.nahuo.com")) {
            return MessageFormat.format("{0}/{1}.jpg?iscut=false", source, convertImgRule(type, 3));
        } else if (source.startsWith("http:")) {
            return source;
        } else {
            return MessageFormat.format("http://img3.nahuo.com/{0}{1}.jpg", source, convertImgRule(type, 2));
        }
    }


    /**
     * 根据图片指定后缀，转换成需求的缩略图后缀
     * @param size a的大小，1-24
     * @param type 类型，0为thum模式，1为a模式 2为-width-height模式 3为width/height模式  4为size模式
     * @return 缩略图后缀
     */
    public static String convertImgRule(int size, int type) {

        if (type == 0 || type == 2 || type == 3 || type == 4) {
            String pxSize = "50";
            switch (size) {
                case 1:
                    pxSize = "50";
                    break;
                case 2:
                    pxSize = "80";
                    break;
                case 3:
                    pxSize = "112";
                    break;
                case 4:
                    pxSize = "125";
                    break;
                case 5:
                    pxSize = "140";
                    break;
                case 6:
                    pxSize = "160";
                    break;
                case 7:
                case 8:
                    pxSize = "200";
                    break;
                case 9:
                case 10:
                    pxSize = "220";
                    break;
                case 11:
                case 12:
                case 13:
                case 14:
                    pxSize = "320";
                    break;
                case 15:
                case 16:
                case 17:
                    pxSize = "500";
                    break;
                case 18:
                    pxSize = "600";
                    break;
                case 19:
                case 20:
                    pxSize = "800";
                    break;
                case 21:
                case 22:
                case 23:
                case 24:
                    pxSize = "1000";
                    break;
                default:
                    pxSize = "1000";
                    break;
            }
            if (type == 0) {
                return "thum." + pxSize;
            } else if (type == 3) {
                return pxSize + "/" + pxSize;
            } else if (type == 4) {
                return pxSize;
            } else {
                return "-" + pxSize + "-" + pxSize;
            }
        } else {
            return "a" + size;
        }
    }


    /**
     * 获取图片的原图地址
     * @param source 原图片路径
     * @return 图片的原图地址
     */
    public static String getImageUrl(String source) {

        if (TextUtils.isEmpty(source))
            return "";

        if (source.startsWith("http:")) {
            return source;
        } else if (source.startsWith("/u")) {
            return MessageFormat.format("http://img4.nahuo.com{0}", source);
        } else if (source.startsWith("upyun:")) {
            String[] datas = source.split(":");
            if (datas == null || datas.length != 3)
                return source;
            return MessageFormat.format("http://{0}.b0.upaiyun.com{1}", datas[1], datas[2]);
        } else {
            return MessageFormat.format("http://img3.nahuo.com/{0}.jpg", source);
        }
    }
}
