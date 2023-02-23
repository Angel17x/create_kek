import 'package:flutter_test/flutter_test.dart';
import 'package:create_kek/create_kek.dart';
import 'package:create_kek/create_kek_platform_interface.dart';
import 'package:create_kek/create_kek_method_channel.dart';
import 'package:plugin_platform_interface/plugin_platform_interface.dart';

class MockCreateKekPlatform
    with MockPlatformInterfaceMixin
    implements CreateKekPlatform {

  @override
  Future<String?> getPlatformVersion() => Future.value('42');
}

void main() {
  final CreateKekPlatform initialPlatform = CreateKekPlatform.instance;

  test('$MethodChannelCreateKek is the default instance', () {
    expect(initialPlatform, isInstanceOf<MethodChannelCreateKek>());
  });

  test('getPlatformVersion', () async {
    CreateKek createKekPlugin = CreateKek();
    MockCreateKekPlatform fakePlatform = MockCreateKekPlatform();
    CreateKekPlatform.instance = fakePlatform;

    expect(await createKekPlugin.getPlatformVersion(), '42');
  });
}
