package com.example.contactsdemo.Pojo;

import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.io.Serializable;

import static com.example.contactsdemo.Utils.Utility.chknull;

@Entity(tableName="contacts_table", indices=@Index(value={"phoneNumber"}, unique=true))
public class Contacts implements Serializable{
 @PrimaryKey(autoGenerate=true)
 private int id;
 private String name;
 private String phoneNumber;
 private String photo;
 private int favFlag=0;
 private int deleteFlag=0;

 public Contacts(){
 }

 public Contacts(String name, String phoneNumber, String photo, int favFlag, int deleteFlag){
  this.name=name;
  this.phoneNumber=phoneNumber;
  this.photo=photo;
  this.favFlag=favFlag;
  this.deleteFlag=deleteFlag;
 }

 public String getName(){
  return chknull(name, "NA");
 }

 public void setName(String name){
  this.name=chknull(name, "NA");
 }

 public String getPhoneNumber(){
  return chknull(phoneNumber, "999999999");
 }

 public void setPhoneNumber(String phoneNumber){
  this.phoneNumber=chknull(phoneNumber, "9999999999");
 }

 public String getPhoto(){
  return chknull(photo, "");
 }

 public void setPhoto(String photo){
  this.photo=chknull(photo, "");
 }

 public int getId(){
  return id;
 }

 public void setId(int id){
  this.id=id;
 }

 public int getFavFlag(){
  return favFlag;
 }

 public void setFavFlag(int favFlag){
  this.favFlag=favFlag;
 }

 public int getDeleteFlag(){
  return deleteFlag;
 }

 public void setDeleteFlag(int deleteFlag){
  this.deleteFlag=deleteFlag;
 }

 @Override
 public boolean equals(@Nullable Object obj){
  if(obj instanceof Contacts) return ((Contacts)obj).getPhoneNumber().equals(phoneNumber);
  else
   return false;
 }
}
