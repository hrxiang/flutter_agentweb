import 'dart:convert';

import 'package:flutter/services.dart';

class FlutterAgentweb {
  static const MethodChannel _channel = const MethodChannel('flutter_agentweb');

  static Future<T> open<T>({
    String url,
    String title,
    List<JsBundle> jsBundle,
  }) {
    return _channel.invokeMethod<T>(
      "open",
      {"url": url, "title": title, "jsBundle": jsonEncode(jsBundle)},
    );
  }
}

class JsBundle {
  final String type;
  final Map<String, dynamic> payload;

  JsBundle({this.type, this.payload}) : assert(null != type);

  /// jsonDecode(jsonStr) 方法中会调用实体类的这个方法。如果实体类中没有这个方法，会报错。
  Map toJson() {
    Map map = Map();
    map["type"] = type;
    map["payload"] = payload;
    return map;
  }
}
