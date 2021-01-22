package com.example.credit_card_sample_app;

import android.content.Context;
import android.util.Log;
import android.webkit.JavascriptInterface;

import org.greenrobot.eventbus.EventBus;

/**
 * contains methods annotated with @JavascriptInterface for communication from native to flutter
 */
public class CCWebAppInterface {
    Context context;

    public CCWebAppInterface(Context context) {
        this.context = context;
    }

    @JavascriptInterface
    public void onCreditCardLoginSuccess(Object msg) {

    }

    @JavascriptInterface
    public void onCreditCardLoginFailure(Object msg) {

    }

    @JavascriptInterface
    public void isNetworkAvailable(Object msg) {
        EventBus.getDefault().post(new RequestTypeSelectedEvent(RequestType.SEND_NETWORK_AVAILABILITY_STATUS));
    }

    @JavascriptInterface
    public void sendSeamlessLoginRequest(Object msg) {
        Log.d("CreditCard", "Login Request");
        EventBus.getDefault().post(new RequestTypeSelectedEvent(RequestType.SEND_PARTNER_APPLICATION_DATA));
    }
}

