import 'package:flutter/services.dart';

class FlutterAgentweb {
  static const MethodChannel _channel = const MethodChannel('flutter_agentweb');

  static void open({String url, String title}) {
    _channel.invokeMethod(
      "open",
      {"url": url, "title": title},
    ).then((val) {});
  }
}
