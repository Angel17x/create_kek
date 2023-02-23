import 'package:flutter/services.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:create_kek/create_kek_method_channel.dart';

void main() {
  MethodChannelCreateKek platform = MethodChannelCreateKek();
  const MethodChannel channel = MethodChannel('create_kek');

  TestWidgetsFlutterBinding.ensureInitialized();

  setUp(() {
    channel.setMockMethodCallHandler((MethodCall methodCall) async {
      return '42';
    });
  });

  tearDown(() {
    channel.setMockMethodCallHandler(null);
  });

  test('getPlatformVersion', () async {
    expect(await platform.getPlatformVersion(), '42');
  });
}
