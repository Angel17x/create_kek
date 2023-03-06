import 'package:create_kek/model/kek.dart';
import 'package:create_kek_example/rsa.dart';
import 'package:flutter/material.dart';
import 'dart:async';

import 'package:flutter/services.dart';
import 'package:create_kek/create_kek.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatefulWidget {
  const MyApp({super.key});

  @override
  State<MyApp> createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  String _platformVersion = 'Unknown';
  String _platformMessage = 'Unknown';
  String _serial = 'Unknown';
  Kek _platformGenerateKEK = Kek(key: null, kcv: null);
  final _createKekPlugin = CreateKek();

  @override
  void initState() {
    super.initState();
    initPlatformState();
  }

  // Platform messages are asynchronous, so we initialize in an async method.
  Future<void> initPlatformState() async {
    String platformVersion;
    String platformMessage;
    Kek platformGenerateKEK;
    String serial;
    // Platform messages may fail, so we use a try/catch PlatformException.
    // We also handle the message potentially returning null.
    try {
      platformVersion =
          await _createKekPlugin.getPlatformVersion() ?? 'Unknown platform version';
      platformMessage = await _createKekPlugin.getMessageKEK() ?? 'Unknown platform message';
      platformGenerateKEK = await _createKekPlugin.getPlatformKEK(rsa);
      serial = await _createKekPlugin.getSerial() ?? 'Unknown serial';

    } on PlatformException {
      platformVersion = 'Failed to get platform version.';
      platformMessage = 'Failed to get platform message.';
      platformGenerateKEK = Kek(key: null, kcv: null);
      serial = 'Unknown serial';
    }

    // If the widget was removed from the tree while the asynchronous platform
    // message was in flight, we want to discard the reply rather than calling
    // setState to update our non-existent appearance.
    if (!mounted) return;

    setState(() {
      _platformVersion = platformVersion;
      _platformMessage = platformMessage;
      _platformGenerateKEK = platformGenerateKEK;
      _serial = serial;
    });
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body: Column(
          children: [
            Center(
              child: Text('Running on: $_platformVersion\n'),
            ),
            Center(
              child: Text('Message on: $_platformMessage\n'),
            ),
            Center(
              child: Text('kek on: ${_platformGenerateKEK.key}\n'),
            ),
            Center(
              child: Text('kcv on: ${_platformGenerateKEK.kcv}\n'),
            ),
            Center(
              child: Text('serial: ${_serial}\n'),
            ),
          ],
        ),
      ),
    );
  }
}
