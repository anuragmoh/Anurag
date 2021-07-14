package com.example.contactsdemo.Database;
import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.contactsdemo.Pojo.Contacts;

import java.util.List;

public class ViewModel extends AndroidViewModel {

 // creating a new variable for course repository.
 private ContactsRepository repository;

 // below line is to create a variable for live
 // data where all the courses are present.
 private LiveData<List<Contacts>> allCourses;

 // constructor for our view modal.
 public ViewModel(@NonNull Application application) {
  super(application);
  repository = new ContactsRepository(application);
 }

 public ContactsRepository getRepository(){
  return repository;
 }

 // below method is use to insert the data to our repository.
 public void insert(Contacts model) {
  repository.insert(model);
 }

 // below line is to update data in our repository.
 public void update(Contacts contacts) {
  repository.update(contacts);
 }

 // below line is to delete the data in our repository.
 public void delete(Contacts contacts) {
  repository.delete(contacts);
 }

 // below method is to delete all the courses in our list.
 public void deleteAllDashImages() {
  repository.deleteAllCourses();
 }

 // below method is to get all the courses in our list.
 public LiveData<List<Contacts>> getAllContactsInfo() {
  allCourses = repository.getAllContacts();
  return allCourses;
 }

 public LiveData<List<Contacts>> getAllFavouriteContacts() {
  allCourses = repository.getAllFavouriteContacts();
  return allCourses;
 }

 public LiveData<List<Contacts>> getAllDeletedContacts() {
  allCourses = repository.getAllDeletedContacts();
  return allCourses;
 }
}
