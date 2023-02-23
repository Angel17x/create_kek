import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';

import 'create_kek_platform_interface.dart';

/// An implementation of [CreateKekPlatform] that uses method channels.
class MethodChannelCreateKek extends CreateKekPlatform {
  /// The method channel used to interact with the native platform.
  @visibleForTesting
  final methodChannel = const MethodChannel('create_kek');

  @override
  Future<String?> getPlatformVersion() async {
    final version = await methodChannel.invokeMethod<String>('getPlatformVersion');
    return version;
  }

  @override
  Future<String?> getPlatformMessage() async {
    final message = await methodChannel.invokeMethod<String>('getDefaultMessage');
    return message;
  }
  @override
  Future<String?> getPlatformKEK(String rsa) async {
    final kek = await methodChannel.invokeMethod<String>('getPlatformKEK', {"rsa": rsa, "key_length": 16});
    return kek;
  }
}
