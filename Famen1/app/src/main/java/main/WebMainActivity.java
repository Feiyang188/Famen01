package main;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.GeolocationPermissions;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.famen1.feiyangkeji.R;

import base.BaseActivity;
import base.Loading;
import butterknife.BindView;
import butterknife.ButterKnife;

public class WebMainActivity extends BaseActivity {

    @BindView(R.id.webview)
    WebView webview;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_main);
        ButterKnife.bind(this);


        Loading.showDialog(this);

        webview.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);//滚动条设置
        webview.getSettings().setBuiltInZoomControls(false);//开启可缩放模式
        webview.getSettings().setJavaScriptEnabled(true); // 支持js
        webview.getSettings().setGeolocationEnabled(true);//开启webview定位
        webview.getSettings().setDatabaseEnabled(true);//开启数据库
        webview.getSettings().setGeolocationDatabasePath(getApplicationContext().getDir("database", Context.MODE_PRIVATE).getPath());
        webview.getSettings().setDomStorageEnabled(true);//开启DomStorage缓存
        webview.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);// 设置缓存


        webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);//对应参数view
                return true;// true表示此事件在此处被处理，不需要再广播
            }

            @Override
            public void onReceivedSslError(WebView view,
                                           SslErrorHandler handler, SslError error) {

                handler.proceed();  //接受所有证书
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                // 有页面跳转时被回调
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                // 页面跳转结束后被回调
            }

            @Override
            public void onReceivedError(WebView view, int errorCode,
                                        String description, String failingUrl) {

            }


        });


        webview.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onReceivedTitle(WebView view, String title) {//此方法可获得网页标题
                super.onReceivedTitle(view, title);
                //Log.i("xml",title);//title就是网页标题
            }

            /**
             * 处理JavaScript Alert事件
             */
            @Override
            public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
                // 用Android组件替换
                new AlertDialog.Builder(WebMainActivity.this).setTitle("JS提示").setMessage(message).setPositiveButton(android.R.string.ok, new AlertDialog.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        result.confirm();
                    }
                }).setCancelable(false).create().show();
                return true;
            }

            /**处理定位的相关，否则WebView不会开启定位功能，类似百度地图这样的就没法定位*/
            @Override
            public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
                callback.invoke(origin, true, false);              //这个必须有
                super.onGeolocationPermissionsShowPrompt(origin, callback);
            }


            /**打开网页的进度,newProgress就是*/
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress == 100) {
                    Loading.dismiss();
                }
            }
        });

        webview.loadUrl("http://116.62.200.11/Mobile/");//读取网络的html
    }


}
