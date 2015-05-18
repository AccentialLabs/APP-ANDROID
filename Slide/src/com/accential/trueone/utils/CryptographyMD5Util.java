package com.accential.trueone.utils;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
/**
 * Classe responsavel por receber e criptografar senha para MD5
 * @author Matheus Odilon - accentialbrasil
 *
 */
public class CryptographyMD5Util {

 public static String encrypt(String password){  
  String senhaCriptografada = "";

  MessageDigest md;

  try {
   md = MessageDigest.getInstance("MD5");
   md.update(password.getBytes("ISO-8859-1"));

   BigInteger hash = new BigInteger(1, md.digest());

   senhaCriptografada = hash.toString(16);

   String zeros = "";

   if(senhaCriptografada.length() < 32) {
    for(int i = 0; i < (32 - senhaCriptografada.length()); i++) {
     zeros += "0";
    }
   }

   senhaCriptografada = zeros + senhaCriptografada;
  } catch(NoSuchAlgorithmException e) {
   e.printStackTrace();
  } catch(UnsupportedEncodingException e) {
   e.printStackTrace();
  }
  return senhaCriptografada;
 }
}