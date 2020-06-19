package org.dplatform.flutter_agentweb.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.jockeyjs.JockeyCallback;
import com.jockeyjs.JockeyHandler;

import org.dplatform.flutter_agentweb.R;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Map;


public class EasyWebActivity extends BaseAgentWebActivity {
    private TextView mTitleTextView;


    @Override
    protected void registerJockeyEvents() {
        parseJsBundle();
    }

    private void parseJsBundle() {
        String jsBundleString = getIntent().getStringExtra("jsBundle");
        if (null != jsBundleString) {
            try {
                JSONArray jsonArray = new JSONArray(jsBundleString);
                System.out.println("=========== js array =====" + jsonArray);
                JSONObject jsBundle;
                for (int i = 0; i < jsonArray.length(); i++) {
                    jsBundle = jsonArray.getJSONObject(0);
                    final String type = jsBundle.optString("type");
                    final Object params = jsBundle.opt("payload");
                    if (null != type) {
                        System.out.println("======== js ========" + jsBundle);
                        onJs(type, new JockeyHandler() {
                            @Override
                            protected void doPerform(Map<Object, Object> payload) {
                                //
                                sendJs(type, params, new JockeyCallback() {
                                    @Override
                                    public void call() {
                                        //
                                    }
                                });
                            }
                        });
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setStatusBarFullTransparent();
        setStatusBarLightMode(true);
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


    public static void start(Activity activity, String url, String title, String jsBundle) {
        Intent intent = new Intent(activity, EasyWebActivity.class);
        intent.putExtra("url", url);
        intent.putExtra("title", title);
        intent.putExtra("jsBundle", jsBundle);
        activity.startActivityForResult(intent, 400);
    }

    /**
     * 全透状态栏
     */
    protected void setStatusBarFullTransparent() {
        if (Build.VERSION.SDK_INT >= 21) {//21表示5.0
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= 19) {//19表示4.4
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //虚拟键盘也透明
            //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    public void setStatusBarLightMode(boolean isLightMode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Window window = getWindow();
            int option = window.getDecorView().getSystemUiVisibility();
            if (isLightMode) {
                option |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            } else {
                option &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            }
            window.getDecorView().setSystemUiVisibility(option);
        }
    }
}
