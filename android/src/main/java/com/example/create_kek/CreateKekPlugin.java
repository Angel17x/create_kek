package com.example.create_kek;

import static com.example.create_kek.GenerateKEK.kekWithKcv;

import androidx.annotation.NonNull;

import org.apache.commons.codec.DecoderException;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;

/** CreateKekPlugin */
public class CreateKekPlugin implements FlutterPlugin, MethodCallHandler {
  /// The MethodChannel that will the communication between Flutter and native Android
  ///
  /// This local reference serves to register the plugin with the Flutter Engine and unregister it
  /// when the Flutter Engine is detached from the Activity
  private MethodChannel channel;

  @Override
  public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
    channel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), "create_kek");
    channel.setMethodCallHandler(this);
  }

  public String getKek(@NonNull MethodCall call, @NonNull Result result) throws DecoderException, NoSuchPaddingException, IllegalBlockSizeException, UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeySpecException, BadPaddingException, InvalidKeyException, NoSuchProviderException {
    String rsa = call.argument("rsa");
    int keyLength = call.argument("key_length") != null ? call.argument("key_length") : 16;
    if(rsa != null){
      String kekWithKcv = kekWithKcv(rsa, keyLength);
      result.success(kekWithKcv);
    }else{
      result.error("01", "rsa is null", null);
    }

    return "";
  }

  @Override
  public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
    switch (call.method){
      case "getPlatformVersion":
        result.success("Android " + android.os.Build.VERSION.RELEASE);
      break;
      case "getPlatformKEK":
        try {
          getKek(call, result);
        } catch (DecoderException e) {
          result.success(null);
        } catch (NoSuchPaddingException e) {
          result.success(null);
        } catch (IllegalBlockSizeException e) {
          result.success(null);
        } catch (UnsupportedEncodingException e) {
          result.success(null);
        } catch (NoSuchAlgorithmException e) {
          result.success(null);
        } catch (InvalidKeySpecException e) {
          result.success(null);
        } catch (BadPaddingException e) {
          result.success(null);
        } catch (InvalidKeyException e) {
          result.success(null);
        } catch (NoSuchProviderException e) {
          result.success(null);
        }
        break;
      case "getDefaultMessage":
        result.success("This message is to greet you, Hello, I'm a developer");
      break;
      default:
        result.notImplemented();
    }
  }

  @Override
  public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
    channel.setMethodCallHandler(null);
  }
}
