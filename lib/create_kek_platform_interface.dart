import 'package:plugin_platform_interface/plugin_platform_interface.dart';

import 'create_kek_method_channel.dart';

abstract class CreateKekPlatform extends PlatformInterface {
  /// Constructs a CreateKekPlatform.
  CreateKekPlatform() : super(token: _token);

  static final Object _token = Object();

  static CreateKekPlatform _instance = MethodChannelCreateKek();

  /// The default instance of [CreateKekPlatform] to use.
  ///
  /// Defaults to [MethodChannelCreateKek].
  static CreateKekPlatform get instance => _instance;

  /// Platform-specific implementations should set this with their own
  /// platform-specific class that extends [CreateKekPlatform] when
  /// they register themselves.
  static set instance(CreateKekPlatform instance) {
    PlatformInterface.verifyToken(instance, _token);
    _instance = instance;
  }

  Future<String?> getPlatformVersion() {
    throw UnimplementedError('platformVersion() has not been implemented.');
  }
}
