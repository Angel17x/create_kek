import Flutter
import UIKit

public class CreateKekPlugin: NSObject, FlutterPlugin {
  public static func register(with registrar: FlutterPluginRegistrar) {
    let channel = FlutterMethodChannel(name: "create_kek", binaryMessenger: registrar.messenger())
    let instance = CreateKekPlugin()
    registrar.addMethodCallDelegate(instance, channel: channel)
  }

  public func handle(_ call: FlutterMethodCall, result: @escaping FlutterResult) {
    switch call.method {
        case 'getPlatformVersion':
            result("iOS " + UIDevice.current.systemVersion)
        case 'getDefaultMessage':
            result("iOS message from invoke_method")
        default:
            result("method not implemented")
    }
  }
}
