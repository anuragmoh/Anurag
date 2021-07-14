package com.example.contactsdemo.Activity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.contactsdemo.Adapter.ContactListAdapter;
import com.example.contactsdemo.Database.ContactsDao;
import com.example.contactsdemo.Database.ViewModel;
import com.example.contactsdemo.Pojo.Contacts;
import com.example.contactsdemo.R;
import com.example.contactsdemo.databinding.ActivityViewContactsBinding;

import java.util.ArrayList;

public class ViewContactsActivity extends AppCompatActivity{
 ActivityViewContactsBinding binding;
 ViewModel viewModel;
 ArrayList<Contacts> contacts;
 String flag=null;

 @RequiresApi(api=Build.VERSION_CODES.LOLLIPOP)
 @Override
 protected void onCreate(Bundle savedInstanceState){
  super.onCreate(savedInstanceState);
  binding=DataBindingUtil.setContentView(this, R.layout.activity_view_contacts);
  viewModel=ViewModelProviders.of(this).get(ViewModel.class);
  if(getIntent()!=null)
   flag=getIntent().getExtras().getString("flag");
  setTitle(flag.equals("1")?"Deleted Contacts":flag.equals("2")?"Favourites":"Contacts");
  ContactsDao dao=viewModel.getRepository().getDao();
  contacts=(ArrayList<Contacts>)(flag.equals("2")?dao.getAllFavouriteContacts1(1):flag.equals("1")?dao.getAllDeletedContacts1(1):dao.getAllContacts1(0));
  empty();
  final ContactListAdapter adapter=new ContactListAdapter(contacts, this, viewModel, flag);
  binding.recyclerView.setHasFixedSize(true);
  binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
  binding.recyclerView.setAdapter(adapter);
  binding.buttonRestoreAll.setOnClickListener(v->{
   dao.restoreAll();
   contacts.clear();
   adapter.notifyDataSetChanged();
   empty();
  });
 }

 public void empty(){
  binding.empty.setVisibility(contacts==null||contacts.size()==0?View.VISIBLE:View.GONE);
  binding.recyclerView.setVisibility(contacts!=null&&contacts.size()>0?View.VISIBLE:View.GONE);
  binding.buttonRestoreAll.setVisibility(flag.equals("1")&&contacts!=null&&contacts.size()>0?View.VISIBLE:View.GONE);
 }

 @Override
 public void onBackPressed(){
  super.onBackPressed();
 }
}