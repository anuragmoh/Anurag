package com.example.contactsdemo.Activity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.ContactsContract;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import com.example.contactsdemo.Database.ContactsDao;
import com.example.contactsdemo.Database.ViewModel;
import com.example.contactsdemo.Pojo.Contacts;
import com.example.contactsdemo.R;
import com.example.contactsdemo.Utils.Utility;
import com.example.contactsdemo.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class HomePageActivity extends AppCompatActivity{
 private static int size=0;
 ActivityMainBinding binding;
 private ViewModel viewmodel;
 private ProgressDialog dialog;

 @RequiresApi(api=Build.VERSION_CODES.O)
 @Override
 protected void onCreate(Bundle savedInstanceState){
  super.onCreate(savedInstanceState);
  binding=DataBindingUtil.setContentView(this, R.layout.activity_main);
  viewmodel=ViewModelProviders.of(this).get(ViewModel.class);
  binding.buttonContacts.setOnClickListener(v->{
   if(ContextCompat.checkSelfPermission(HomePageActivity.this, Manifest.permission.READ_CONTACTS)==PackageManager.PERMISSION_DENIED){
    ActivityCompat.requestPermissions(HomePageActivity.this, new String[]{Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS}, 101);
   }else{
    if(viewmodel.getRepository().getDao().getCount()>0){
     startActivity(new Intent(HomePageActivity.this, ViewContactsActivity.class).putExtra("flag", "0"));
    }else{
     dialog=new ProgressDialog(HomePageActivity.this);
     dialog.setMessage(getString(R.string.loading));
     dialog.show();
     final Handler handler=new Handler(Looper.getMainLooper());
     handler.postDelayed(new Runnable(){
      @Override
      public void run(){
       getContactList();
      }
     }, 2000);
    }
   }
  });
  binding.buttonDeleted.setOnClickListener(v->{
   startActivity(new Intent(HomePageActivity.this, ViewContactsActivity.class).putExtra("flag", "1"));
  });
  binding.buttonFavourites.setOnClickListener(v->{
   startActivity(new Intent(HomePageActivity.this, ViewContactsActivity.class).putExtra("flag", "2"));
  });

 }

 @Override
 public void onBackPressed(){
  super.onBackPressed();
  finishAffinity();
 }

 private void getContactList(){
  ArrayList<Contacts> contactsArrayList=new ArrayList<>();
  ContentResolver cr=getContentResolver();
  Cursor cur=cr.query(ContactsContract.Contacts.CONTENT_URI,
    null, null, null, null);
  if((cur!=null?cur.getCount():0)>0){
   while(cur!=null&&cur.moveToNext()){
    String id=cur.getString(
      cur.getColumnIndex(ContactsContract.Contacts._ID));
    String name=cur.getString(cur.getColumnIndex(
      ContactsContract.Contacts.DISPLAY_NAME));
    String contactID=cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
    Uri my_contact_Uri=Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_URI, String.valueOf(contactID));
    if(cur.getInt(cur.getColumnIndex(
      ContactsContract.Contacts.HAS_PHONE_NUMBER))>0){
     Cursor pCur=cr.query(
       ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
       null,
       ContactsContract.CommonDataKinds.Phone.CONTACT_ID+" = ?",
       new String[]{id}, null);
     while(pCur.moveToNext()){
      String phoneNo=pCur.getString(pCur.getColumnIndex(
        ContactsContract.CommonDataKinds.Phone.NUMBER));
      if(Utility.chknull(name, "").length()>0){
       Contacts contacts=new Contacts();
       contacts.setName(name);
       contacts.setPhoneNumber(phoneNo);
       contacts.setPhoto(String.valueOf(my_contact_Uri));
       contactsArrayList.add(contacts);
      }
     }
     pCur.close();
    }
   }
  }
  if(cur!=null){
   cur.close();
   setContactInfo(contactsArrayList);
  }
 }

 public void setContactInfo(List<Contacts> contactsList){
  if(viewmodel.getRepository().getDao().getCount()<=0){
   size=contactsList.size();
   for(Contacts contacts: contactsList){
    new InsertCourseAsyncTask(this,viewmodel.getRepository().getDao(),dialog).execute(contacts);
   }
  }
 }

 private static class InsertCourseAsyncTask extends AsyncTask<Contacts, Void, Void>{
  private ContactsDao dao;
  HomePageActivity homePageActivity;
  AlertDialog dialog;
  private InsertCourseAsyncTask(HomePageActivity homePageActivity, ContactsDao dao, AlertDialog dialog){
   this.dao=dao;
   this.homePageActivity=homePageActivity;
   this.dialog=dialog;
  }
  @Override
  protected Void doInBackground(Contacts... model){
   dao.insert(model[0]);
   size--;
   return null;
  }
  @Override
  protected void onPostExecute(Void unused){
   super.onPostExecute(unused);
   if(size==0){
    dialog.dismiss();
    homePageActivity.startActivity(new Intent(homePageActivity, ViewContactsActivity.class).putExtra("flag", "0"));
   }
  }
 }
}