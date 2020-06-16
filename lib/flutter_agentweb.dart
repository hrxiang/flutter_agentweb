import 'dart:convert';

import 'package:flutter/services.dart';

class FlutterAgentweb {
  static const MethodChannel _channel = const MethodChannel('flutter_agentweb');

  static void open({
    String url,
    String title,
    JsBundle jsBundle,
  }) {
    _channel.invokeMethod(
      "open",
      {"url": url, "title": title, "jsBundle": jsonEncode(jsBundle)},
    ).then((val) {});
  }
}

class JsBundle {
  final String type;
  final Map<String, dynamic> payload;

  JsBundle({this.type, this.payload}) : assert(null != type);
}
