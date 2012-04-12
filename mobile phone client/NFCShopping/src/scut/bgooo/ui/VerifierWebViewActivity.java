package scut.bgooo.ui;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import scut.bgooo.ui.R;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class VerifierWebViewActivity extends Activity {

	private WebView mWebView;
	private String mOauthPin = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.webview);
		mWebView = (WebView) findViewById(R.id.webView1);
		Intent intent = getIntent();
		Bundle bundle = intent.getBundleExtra("URL");
		String URL = bundle.getString("URL");

		// ʹwebview������cookie��session
		CookieSyncManager.createInstance(this);
		CookieSyncManager.getInstance().startSync();
		CookieManager.getInstance().removeSessionCookie();
		mWebView.clearCache(true);
		mWebView.clearHistory();
		// �ܺõ�һ�δ���
		mWebView.getSettings().setJavaScriptEnabled(true);
		mWebView.getSettings().setSupportZoom(true);
		mWebView.getSettings().setBuiltInZoomControls(true);
		mWebView.loadUrl(URL);
	}

}
