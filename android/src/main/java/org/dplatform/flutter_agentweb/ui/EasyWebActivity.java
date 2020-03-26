package org.dplatform.flutter_agentweb.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.dplatform.flutter_agentweb.R;


public class EasyWebActivity extends BaseAgentWebActivity {
    private TextView mTitleTextView;


    @Override
    protected void registerJockeyEvents() {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        mTitleTextView = findViewById(R.id.toolbar_title);
        findViewById(R.id.toolbar_back).setOnClickListener(v -> onBackPressed());

        //url带全屏参数单独处理
        String url = getUrl();
        if (null != url && url.contains("fullscreen")) {
            boolean isFullScreen = Uri.parse(url).getBooleanQueryParameter("fullscreen", false);
            findViewById(R.id.rl_toolbar).setVisibility(isFullScreen ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public void onBackPressed() {
        if (mAgentWeb != null && mAgentWeb.handleKeyEvent(KeyEvent.KEYCODE_BACK, null)) {
            //
        } else {
            super.onBackPressed();
        }
    }

    @NonNull
    @Override
    protected ViewGroup getAgentWebParent() {
        return (ViewGroup) this.findViewById(R.id.container);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (mAgentWeb != null && mAgentWeb.handleKeyEvent(keyCode, event)) {
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected int getIndicatorColor() {
        return Color.parseColor("#0091FF");
    }

    @Override
    protected void setTitle(WebView view, String title) {
        super.setTitle(view, title);
        if (!TextUtils.isEmpty(title)) {
            if (title.length() > 10) {
                title = title.substring(0, 10).concat("...");
            }
        }
        mTitleTextView.setText(title);
    }

    @Override
    protected int getIndicatorHeight() {
        return 3;
    }

    @Nullable
    @Override
    protected String getUrl() {
        return getIntent().getStringExtra("url");
    }


    public static void start(Activity activity, String url, String title) {
        Intent intent = new Intent(activity, EasyWebActivity.class);
        intent.putExtra("url", url);
        intent.putExtra("title", title);
        activity.startActivityForResult(intent, 400);
    }
}
