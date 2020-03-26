import 'package:flutter/material.dart';
import 'package:flutter_agentweb/flutter_agentweb.dart';

void main() => runApp(MyApp());

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  String _platformVersion = 'Unknown';

  @override
  void initState() {
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body: Center(
          child: GestureDetector(
            onTap: () {
              FlutterAgentweb.open(
                url:
                    'https://cvd.xiaoduoai.com/c/index.html?src=5072&key=ltssfsdcbraopjotflieccyatvrjllhwguaexoxrifgyoapgxhlkldcgnnfqusvr&channel_id=10013&removeTopBar=true',
              );
            },
            child: Text('Running on: $_platformVersion\n'),
          ),
        ),
      ),
    );
  }
}
