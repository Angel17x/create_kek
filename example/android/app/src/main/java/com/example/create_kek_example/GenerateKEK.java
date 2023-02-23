package com.example.create_kek_example;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class GenerateKEK {


    // @RequiresApi(api = Build.VERSION_CODES.O)
    public static String getKcv(String key) throws NoSuchPaddingException, NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, DecoderException {
        final var publicBytes = Hex.decodeHex(key.toCharArray());
        final var secretKey = new SecretKeySpec(publicBytes, "DESede");
        //final var secretKey = new SecretKeySpec(publicBytes, "TripleDES");
        final var cipher = Cipher.getInstance("DESede/CBC/NoPadding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        return Hex.encodeHexString(cipher.doFinal(new byte[8])).substring(0, 6).toUpperCase();

    }


    public static SecretKeySpec get3DESKey(int keyLength) {
//        final var id = RandomID.generateIdAlphanumeric(16);
        final var id = RandomID.generateIdAlphanumeric(keyLength);
//        log.info("secretKey TRIPLE DES: " + id);
        final var secretKey = id.getBytes();
        return new SecretKeySpec(secretKey, "TripleDES");
    }

    public static String getKekFormat(String kek) {

        return "0002E2709F1B6394ACFA5DBB0DC97997" +
                "3955B855E1457D2466CDAB79B1EE9F04" +
                "163CBFCB2911B02FF3BFEA593D4826B4" +
                "368EDD09F84C1591465B89F17A67EBB5" +
                "BF74799D2EF0CAB0BB2457CA2315084A" +
                "58D8C8E2F50970FCD2C74D6FF7CCD73E" +
                "692A310A5E1C8664ADF3D5FE448FB2C4" +
                "57DE7370ABC22F59ED57284CB4CE8449" +
                "3F78C61B3ED59C1185A3E10BD69F83F6" +
                "8FD2FDF6F5CD648C82A7E83B96663550" +
                "3A874EBC6D6CA34876E6DC38813D80C3" +
                "13F391A3056C7A091234C667DFC2A07A" +
                "7311C9BCAEDC9003CC0F0FE33FC7D8D4" +
                "0A581B351D0664EBF71F67C4190862DB" +
                "9F00301C0410" + kek + "04080000000000000000";

    }


    public static String encryptByRSA(String str, String rsa) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException, UnsupportedEncodingException, DecoderException, DecoderException {

        final var exponent = 65537L;
        final var publicBytes = Hex.decodeHex(rsa.toCharArray());
        final var strBytes = Hex.decodeHex(str.toCharArray());
        final var modulus = new BigInteger(1, publicBytes);
        final var publicExponent = BigInteger.valueOf(exponent);
        final var keySpec = new RSAPublicKeySpec(modulus, publicExponent);
        final var keyFactory = KeyFactory.getInstance("RSA");
        final var pubKey = keyFactory.generatePublic(keySpec);
        final var cipher = Cipher.getInstance("RSA/ECB/NoPadding");
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);
        final var rsaEncrypt = cipher.doFinal(strBytes);
        return Hex.encodeHexString(rsaEncrypt);

    }

    public static String decryptionBy3DES(byte[] encryptedMessageBytes, SecretKeySpec key) throws NoSuchPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, InvalidKeyException {
        final var decryptCipher = Cipher.getInstance("TripleDES/CBC/PKCS5Padding");
        final var iv = new byte[8];
        final var ivSpec = new IvParameterSpec(iv);
        decryptCipher.init(Cipher.DECRYPT_MODE, key, ivSpec);

        final var decryptedMessageBytes = decryptCipher.doFinal(encryptedMessageBytes);

        return new String(decryptedMessageBytes);
    }

    public static String kekWithKcv(String rsa, int keyLength) throws DecoderException, NoSuchPaddingException, IllegalBlockSizeException, UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeySpecException, BadPaddingException, InvalidKeyException, NoSuchProviderException {
        final var tripleDes = GenerateKEK.get3DESKey(keyLength);
        final var tripleDesString = Hex.encodeHexString(tripleDes.getEncoded());
        final var kek = GenerateKEK.encryptByRSA(GenerateKEK.getKekFormat(tripleDesString), rsa);
        final var kcv = GenerateKEK.getKcv(tripleDesString);

        return kek + "|" + kcv;
    }
}
