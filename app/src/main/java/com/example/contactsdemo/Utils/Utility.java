package com.example.contactsdemo.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.contactsdemo.BuildConfig;
import com.example.contactsdemo.Pojo.Contacts;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Utility{
 public static final boolean isDebugAPK=BuildConfig.DEBUG&&BuildConfig.BUILD_TYPE.trim().equalsIgnoreCase("debug");



 public static boolean isNonEmptyList(List<?> list){
  return list!=null&&list.size()>0;
 }
 public static void setContactInfoList(Context context, List<Contacts> contactsList){
  SharedPreferences preferences=context.getSharedPreferences("Contacts Demo", Context.MODE_PRIVATE);
  SharedPreferences.Editor editor=preferences.edit();
  Gson gson=new Gson();
  String json="";
  if(contactsList!=null&&contactsList.size()>0)
   json=gson.toJson(contactsList);
  editor.putString("contactList", json);
  editor.commit();
 }

 public static void setContactInfoDeleteList(Context context, List<Contacts> contactsList){
  SharedPreferences preferences=context.getSharedPreferences("Contacts Demo", Context.MODE_PRIVATE);
  SharedPreferences.Editor editor=preferences.edit();
  Gson gson=new Gson();
  String json="";
  if(contactsList!=null&&contactsList.size()>0)
   json=gson.toJson(contactsList);
  editor.putString("deleteContactList", json);
  editor.commit();
 }
 public static List<Contacts> getDeleteContactInfoList(Context context){
  SharedPreferences prefs=context.getSharedPreferences("Contacts Demo", Context.MODE_PRIVATE);
  Gson gson=new Gson();
  String json=prefs.getString("deleteContactList", "");
  Type type=new TypeToken<ArrayList<Contacts>>(){
  }.getType();
  return json.length()>0&&!json.equalsIgnoreCase("")?gson.fromJson(json, type):new ArrayList<>();
 }

 public static List<Contacts> getContactInfoList(Context context){
  SharedPreferences prefs=context.getSharedPreferences("Contacts Demo", Context.MODE_PRIVATE);
  Gson gson=new Gson();
  String json=prefs.getString("contactList", "");
  Type type=new TypeToken<ArrayList<Contacts>>(){
  }.getType();
  return json.length()>0&&!json.equalsIgnoreCase("")?gson.fromJson(json, type):new ArrayList<>();
 }

 public static void showLog(String message){
  if(isDebugAPK)
   Log.w("showLog", message);
 }

 public static boolean chkNull(String str){
  return str!=null&&str.trim().length()>0&&!str.trim().equalsIgnoreCase("null");
 }

 public static String chkNull(String str, String def){
  return str!=null&&str.trim().length()>0&&!str.trim().equalsIgnoreCase("null")?str:def;
 }
 public static Long chknull(Long ll, Long def){
  return ll!=null?ll:def;
 }

 public static Double chknull(Double dd, Double def){
  return dd!=null?dd:def;
 }

 public static Float chknull(Float ff, Float def){
  return ff!=null?ff:def;
 }

 public static Integer chknull(Integer ii, Integer def){
  return ii!=null?ii:def;
 }

 public static Boolean chknull(Boolean bb, Boolean def){
  return bb!=null?bb:def;
 }

 public static String chknull(String str, String def){
  return (str!=null&&str.trim().length()>0&&!str.equalsIgnoreCase("null"))?str:def;
 }

}
