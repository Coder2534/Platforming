package com.android.platforming.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.platforming.R;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.cookie.Cookie;

import java.util.ArrayList;
import java.util.List;

public class WebViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        Intent intent = getIntent();
        String workName = intent.getStringExtra("workName");

        if(workName.equals("homepage")){
            setListener();
            loadWeb("http://school.gyo6.net/geumohs");
        }
        else if(workName.equals("riroschool")){
            String riroschoolPackage = "com.rirosoft.riroschool";
            try{
                startActivity(getPackageManager().getLaunchIntentForPackage(riroschoolPackage));
                finish();
            }catch (Exception e){
                setListener();
                loadWeb("http://geumo.riroschool.kr/");
            }
        }
        else if(workName.equals("self-diagnosis")){
            String self_diagnosisPackage = "kr.go.eduro.hcs";
            try{
                startActivity(getPackageManager().getLaunchIntentForPackage(self_diagnosisPackage));
                finish();
            }catch (Exception e){
                setListener();
                loadWeb("http://hcs.eduro.go.kr");
            }
        }
    }

    private void setListener(){
        ImageButton imageButton = findViewById(R.id.btn_webview_back);
        imageButton.setOnClickListener(v -> finish());
    }

    WebView webView;
    private void loadWeb(String url){
        webView = findViewById(R.id.wv_webview);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_USER);

        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            CookieSyncManager.createInstance(this);
        }
        else {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.setAcceptCookie(true);
            cookieManager.setAcceptThirdPartyCookies(webView, true);
        }

        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if(Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP){
                    CookieSyncManager.getInstance().sync();
                } else{
                    CookieManager.getInstance().flush();
                }
            }
        });

        webView.loadUrl(url);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            CookieSyncManager.getInstance().stopSync();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            CookieSyncManager.getInstance().startSync();
        }
    }

    @Override
    public void onBackPressed() {
        if(webView.canGoBack()) {
            webView.goBack();
        }
        else{
            super.onBackPressed();
        }
    }
}