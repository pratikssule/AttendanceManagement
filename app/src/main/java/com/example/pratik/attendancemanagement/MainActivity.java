package com.example.pratik.attendancemanagement;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.CookieManager;
import android.webkit.DownloadListener;
import android.webkit.URLUtil;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import java.io.File;

import im.delight.android.webview.AdvancedWebView;

import static android.R.attr.autoUrlDetect;
import static android.R.attr.mimeType;

public class MainActivity extends AppCompatActivity {

    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //mWebView = (AdvancedWebView) findViewById(R.id.webview);
        mWebView = (WebView) findViewById(R.id.webview);
        //mWebView.setListener(this, this);
        // mWebView.loadUrl("http://awseriously.com/vivek/attendance/client/login");

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }
        });
        mWebView.setWebChromeClient(new WebChromeClient());

        mWebView.setWebViewClient(new MyWebViewClient());

        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setDomStorageEnabled(true);
        //mWebView.loadUrl("http://awseriously.com/vivek/attendance/client/login");
        //mWebView.addJavascriptInterface();
        mWebView.loadUrl("http://192.168.1.10/web/attendance/");


//        final String baseUrl = "http://awseriously.com/vivek/attendance/client/login";
//        mWebView.loadHtml("<html>...</html>", baseUrl);


//        mWebView.setDownloadListener(new DownloadListener() {
//            @Override
//            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
//                Log.d("MainActivity",url);
//                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
//
//                request.setMimeType(mimetype);
//                //------------------------COOKIE!!------------------------
//                //String cookies = CookieManager.getInstance().getCookie(url);
//                //request.addRequestHeader("cookie", cookies);
//                //------------------------COOKIE!!------------------------
//                request.addRequestHeader("User-Agent", userAgent);
//                request.setDescription("Downloading file...");
//                request.setTitle(URLUtil.guessFileName(url, contentDisposition, mimetype));
//                request.allowScanningByMediaScanner();
//                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
//                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, URLUtil.guessFileName(url, contentDisposition, mimetype));
//
//                DownloadManager dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
//                dm.enqueue(request);
//                Toast.makeText(getApplicationContext(), "Downloading File", Toast.LENGTH_LONG).show();
//            }
//        });


        mWebView.setDownloadListener(new DownloadListener() {

            public void onDownloadStart(String url, String userAgent,
                                        String contentDisposition, String mimetype,
                                        long contentLength) {

                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
                request.setMimeType(mimetype);
                request.allowScanningByMediaScanner();

                request.setNotificationVisibility(
                        DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

                request.setDestinationInExternalPublicDir(
                        Environment.DIRECTORY_DOWNLOADS,    //Download folder
                        "download");                        //Name of file


                DownloadManager dm = (DownloadManager) getSystemService(
                        DOWNLOAD_SERVICE);

                dm.enqueue(request);
                Toast.makeText(getApplicationContext(), "Downloading File", Toast.LENGTH_LONG).show();

            }
        });

    }

    public class MyWebViewClient extends WebViewClient {

//        private Context context;
//
//        public MyWebViewClient(Context context) {
//            this.context = context;
//        }

        //@SuppressLint("NewApi")
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url.contains(".jpg")) {

                DownloadManager mdDownloadManager = (DownloadManager)
                        getSystemService(DOWNLOAD_SERVICE);
                DownloadManager.Request request = new DownloadManager.Request(
                        Uri.parse(url));
                File destinationFile = new File(
                        Environment.getExternalStorageDirectory(),
                        getFileName(url));
                request.setDescription("Downloading ...");
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                request.setDestinationUri(Uri.fromFile(destinationFile));
                mdDownloadManager.enqueue(request);


                return true;
            }
            return super.shouldOverrideUrlLoading(view, url);
        }

        public String getFileName(String url) {
            String filenameWithoutExtension = "";
            filenameWithoutExtension = String.valueOf(System.currentTimeMillis()
                    + ".jpg");
            return filenameWithoutExtension;
        }

    }
}



