package org.dplatform.flutter_agentweb;

import android.app.Activity;
import android.content.Intent;

import org.dplatform.flutter_agentweb.ui.EasyWebActivity;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.PluginRegistry;

import static io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import static io.flutter.plugin.common.MethodChannel.Result;

public class FlutterAgentwebPlugin implements MethodCallHandler, PluginRegistry.ActivityResultListener {
    private Activity activity;

    private FlutterAgentwebPlugin(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onMethodCall(MethodCall methodCall, Result result) {
        if ("open".equals(methodCall.method)) {
            String url = methodCall.argument("url");
            String title = methodCall.argument("title");
            EasyWebActivity.start(activity, url, title);
        }
    }

    public static void registerWith(PluginRegistry.Registrar registrar) {
        MethodChannel channel = new MethodChannel(registrar.messenger(), "flutter_agentweb");
        channel.setMethodCallHandler(new FlutterAgentwebPlugin(registrar.activity()));
    }

    @Override
    public boolean onActivityResult(int i, int i1, Intent intent) {
        return false;
    }
}
