package com.example.credit_card_sample_app;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import org.greenrobot.eventbus.Subscribe;

import wendu.dsbridge.DWebView;

public class MainActivity extends AppCompatActivity {

    DWebView webView;
    ProgressBar pBar;
    private boolean isLoadedFirst = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webView = findViewById(R.id.flutter_webview);
        pBar = findViewById(R.id.progress_spinner);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String weburl) {
                if(isLoadedFirst) {
                    isLoadedFirst =false;
                    pBar.setVisibility(View.INVISIBLE);
                    webView.setVisibility(View.VISIBLE);
                }
            }
        });
        webView.setWebChromeClient(new WebChromeClient() {

        });
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setVerticalScrollBarEnabled(true);
        webView.setHorizontalScrollBarEnabled(true);
        webView.addJavascriptObject(new CCWebAppInterface(this), "credit_card");
        webView.loadUrl("https://focused-stonebraker-065db8.netlify.app/#/");

    }

    @Subscribe
    public void onRequestTypeSelected(RequestTypeSelectedEvent event) {
        if (event.requestType.equals(RequestType.SEND_PARTNER_APPLICATION_DATA)) {
            sendPartnerApplicationData();
        }
    }

    // send partner app data to flutter module through javascript channel
    private void sendPartnerApplicationData() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            webView.evaluateJavascript("javascript:partnerApplicationData('" + CCConfigProperties.TOKEN +
                    "','" + CCConfigProperties.DEVICE_TYPE + "')");
        }
    }
}