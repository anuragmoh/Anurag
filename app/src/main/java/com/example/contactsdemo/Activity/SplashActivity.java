package com.example.contactsdemo.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;

import com.example.contactsdemo.R;

public class SplashActivity extends AppCompatActivity{

 @Override
 protected void onCreate(Bundle savedInstanceState){
  super.onCreate(savedInstanceState);
  setContentView(R.layout.activity_splash);
  final Handler handler=new Handler(Looper.getMainLooper());
  handler.postDelayed(()->startActivity(new Intent(SplashActivity.this, HomePageActivity.class)), 3000);

 }
}