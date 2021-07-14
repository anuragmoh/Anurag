package com.example.contactsdemo.Adapter;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.contactsdemo.Activity.ViewContactsActivity;
import com.example.contactsdemo.Database.ContactsDao;
import com.example.contactsdemo.Database.ViewModel;
import com.example.contactsdemo.Pojo.Contacts;
import com.example.contactsdemo.R;
import com.example.contactsdemo.Utils.Utility;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;

import static com.example.contactsdemo.Utils.Utility.chkNull;
import static com.example.contactsdemo.Utils.Utility.chknull;

public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ViewHolder>{

 ViewModel viewModel;
 String flag;
 ContactsDao dao;
 private ArrayList<Contacts> contactsArrayList;
 private ViewContactsActivity context;

 public ContactListAdapter(ArrayList<Contacts> contactsArrayList, ViewContactsActivity context, ViewModel viewModel, String flag){
  this.contactsArrayList=contactsArrayList;
  this.context=context;
  this.viewModel=viewModel;
  this.flag=flag;
  dao=viewModel.getRepository().getDao();
 }

 @NonNull
 @Override
 public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, final int i){
  View view=LayoutInflater.from(context).inflate(R.layout.contacts_list_item, viewGroup, false);
  return new ViewHolder(view);
 }

 @RequiresApi(api=Build.VERSION_CODES.M)
 @Override
 public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i){
  Contacts contacts=contactsArrayList.get(i);
  viewHolder.contactName.setText(chkNull(contacts.getName(), "NA"));
  viewHolder.contactNumber.setText(chkNull(contacts.getPhoneNumber(), "NA"));
  viewHolder.textView.setBackgroundColor(Color.BLUE);
  viewHolder.textView.setTextColor(Color.WHITE);
  viewHolder.textView.setText(chknull(contacts.getName().substring(0, 1), "NA"));
  viewHolder.buttonDelete.setVisibility(flag.equals("1")?View.VISIBLE:flag.equals("2")?View.GONE:View.VISIBLE);
  viewHolder.buttonFavourite.setVisibility(flag.equals("2")?View.VISIBLE:flag.equals("0")?View.VISIBLE:View.GONE);
  viewHolder.buttonFavourite.setText(flag.equals("2")?"Remove from favourites":contacts.getFavFlag()==1?context.getString(R.string.mark_as_favourite):"Favourite");
  viewHolder.buttonDelete.setText(flag.equals("1")||contacts.getDeleteFlag()==1?context.getString(R.string.restore):"Delete");
  viewHolder.buttonDelete.setBackgroundColor(context.getColor(flag.equals("1")?R.color.green2:R.color.red));
  viewHolder.buttonFavourite.setBackgroundColor(context.getColor(R.color.blue));

  Utility.showLog(String.valueOf(viewHolder.imageTextView.getDrawable()==null));
  Utility.showLog(contacts.getPhoto());
  viewHolder.buttonDelete.setOnClickListener(v->{
   String msg=flag.equals("1")?"Restore":"Delete";
   AlertDialog.Builder alertbox=new AlertDialog.Builder(v.getRootView().getContext());
   alertbox.setMessage("Are you sure you want to "+msg.toLowerCase()+" this Contact?");
   alertbox.setTitle(msg+" Contact");
   alertbox.setPositiveButton(android.R.string.yes, (dialog, which)->{
    contacts.setDeleteFlag(contacts.getDeleteFlag()==1?0:1);
    dao.updateDel(contacts.getPhoneNumber(), contacts.getDeleteFlag());
    contactsArrayList.remove(contacts);
    if(contactsArrayList.size()==0)
     context.empty();
    notifyDataSetChanged();
   })
     .setNegativeButton(android.R.string.no, (dialog, which)->dialog.dismiss());
   alertbox.show();

  });
  if(flag.equals("0"))
   viewHolder.relative.setOnClickListener(v->{
    AlertDialog.Builder alertbox=new AlertDialog.Builder(v.getRootView().getContext());
    alertbox.setTitle("Select Action");
    alertbox.setSingleChoiceItems(new CharSequence[]{"Call", "SMS"}, -1, new DialogInterface.OnClickListener(){
     @Override
     public void onClick(DialogInterface dialog, int which){
      if(which==0){
       Intent intent=new Intent(Intent.ACTION_DIAL);
       intent.setData(Uri.parse("tel:"+Uri.encode(contacts.getPhoneNumber())));
       context.startActivity(intent);
      }else if(which==1){
       Intent intent=new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:"+Uri.encode(contacts.getPhoneNumber())));
       context.startActivity(intent);
      }
      dialog.dismiss();
     }
    });
    alertbox.setNegativeButton("Cancel", null);
    alertbox.show();
   });

  viewHolder.buttonFavourite.setOnClickListener(v->{
   contacts.setFavFlag(contacts.getFavFlag()==1?0:1);
   dao.updateFav(contacts.getPhoneNumber(), contacts.getFavFlag());
   if(flag.equals("2"))
    contactsArrayList.remove(contacts);
   else
    contactsArrayList.set(i, contacts);
   if(contactsArrayList.size()==0)
    context.empty();
   notifyDataSetChanged();
  });
  Picasso.with(context).load(contacts.getPhoto()).into(new Target(){
   @Override
   public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from){
    viewHolder.imageTextView.setImageBitmap(bitmap);
    viewHolder.textView.setVisibility(View.GONE);
    viewHolder.imageTextView.setVisibility(View.VISIBLE);
   }

   @Override
   public void onBitmapFailed(Drawable errorDrawable){
    viewHolder.textView.setVisibility(View.VISIBLE);
    viewHolder.imageTextView.setVisibility(View.INVISIBLE);
   }

   @Override
   public void onPrepareLoad(Drawable placeHolderDrawable){

   }
  });

 }

 @Override
 public int getItemCount(){
  return contactsArrayList.size();
 }

 public class ViewHolder extends RecyclerView.ViewHolder{
  private ImageView imageTextView;
  private Button buttonFavourite, buttonDelete;
  private TextView contactName, contactNumber, textView;
  private RelativeLayout relative;

  public ViewHolder(@NonNull View itemView){
   super(itemView);
   contactName=itemView.findViewById(R.id.contactName);
   contactNumber=itemView.findViewById(R.id.contactNumber);
   imageTextView=itemView.findViewById(R.id.imageView);
   buttonFavourite=itemView.findViewById(R.id.buttonFavourite);
   buttonDelete=itemView.findViewById(R.id.buttonDelete);
   relative=itemView.findViewById(R.id.relative);
   textView=itemView.findViewById(R.id.textView);

  }
 }
}
