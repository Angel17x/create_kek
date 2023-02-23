
import 'package:create_kek/create_kek_method_channel.dart';
import 'package:create_kek/model/kek.dart';

import 'create_kek_platform_interface.dart';

class CreateKek {
  Future<String?> getPlatformVersion() {
    return CreateKekPlatform.instance.getPlatformVersion();
  }
  Future<String?> getMessageKEK(){
    return MethodChannelCreateKek().getPlatformMessage();
  }
  Future<Kek> getPlatformKEK(String rsa) async{
    String? kek = await MethodChannelCreateKek().getPlatformKEK(rsa);
    if(kek != null){
      List<String> result = kek.split("|");
      return Kek(key: result[0], kcv: result[1]);
    }else{
      return Kek(key: null, kcv: null);
    }
  }
}
