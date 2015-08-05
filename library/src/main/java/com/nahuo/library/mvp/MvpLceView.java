package com.nahuo.library.mvp;

/**
 * Created by ZZB on 2015/6/4 9:47
 */
public interface MvpLceView extends MvpView {


    public void showLoading(boolean show, RequestData requestData);

    public void onRequestError(RequestError requestError);
//    public void hideLoading(RequestData data);

//    public void showContent();

//    public void onLoadSuccess(RequestError error);

//    public void onLoadFailed(ResponseData data);
}
