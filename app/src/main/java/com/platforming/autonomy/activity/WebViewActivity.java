package com.platforming.autonomy.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.WindowManager;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;

import com.platforming.autonomy.InitApplication;
import com.platforming.autonomy.clazz.FirestoreManager;
import com.platforming.autonomy.interfaze.ListenerInterface;
import com.android.autonomy.R;
import com.platforming.autonomy.clazz.User;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class WebViewActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        InitApplication initApplication = ((InitApplication)getApplication());
        switch (initApplication.getAppliedTheme()){
            case 0:setTheme(R.style.WhiteTheme);break;
            case 1:setTheme(R.style.PinkTheme);break;
            case 2:setTheme(R.style.BuleTheme);break;
            case 3:setTheme(R.style.GreenTheme);break;
            case 4:setTheme(R.style.BlackTheme);break;
        }
        switch (initApplication.getAppliedFont()){
            case 0:setTheme(R.style.pretendardFont);break;
            case 1:setTheme(R.style.snowFont);break;
            case 2:setTheme(R.style.bmeFont);break;
            case 3:setTheme(R.style.establishreFont);break;
            case 4:setTheme(R.style.eulyoo1945Font);break;
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        Intent intent = getIntent();
        String tag = intent.getStringExtra("tag");

        if(Objects.equals(tag, "HOMEPAGE")){
            setListener();
            loadWeb("http://school.gyo6.net/geumohs");
        }
        else if(Objects.equals(tag, "RIROSCHOOL")){
            try{
                startActivity(getPackageManager().getLaunchIntentForPackage("com.rirosoft.riroschool"));
                finish();
            }catch (Exception e){
                setListener();
                loadWeb("http://geumo.riroschool.kr/");
            }
        }
        else if(Objects.equals(tag, "SELFDIAGNOSIS")){
            if(User.user.getDailyTasks().get(1) < 1){
                List<Long> dailyTasks = new LinkedList<>(User.user.getDailyTasks());
                dailyTasks.set(1, 1L);
                FirestoreManager firestoreManager = new FirestoreManager();
                firestoreManager.updateUserData(new HashMap<String, Object>() {{
                    put("point_receipt", User.user.getPoint_receipt() + 10);
                    put("dailyTasks", dailyTasks);
                }}, new ListenerInterface() {
                    @Override
                    public void onSuccess() {
                        User.user.addPoint_receipt(10);
                        User.user.getDailyTasks().set(1, 1L);
                    }
                });
            }

            try{
                startActivity(getPackageManager().getLaunchIntentForPackage("kr.go.eduro.hcs"));
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