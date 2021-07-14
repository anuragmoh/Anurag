package com.example.contactsdemo.Database;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.contactsdemo.Pojo.Contacts;

import java.util.List;

public class ContactsRepository{
 private ContactsDao dao;
 private LiveData<List<Contacts>> allCourses;

 public ContactsDao getDao(){
  return dao;
 }

 // creating a constructor for our variables
 // and passing the variables to it.
 public ContactsRepository(Application application) {
  ContactsDatabase database = ContactsDatabase.getInstance(application);
  dao = database.Dao();
 }

 // creating a method to insert the data to our database.
 public void insert(Contacts model) {
  new InsertCourseAsyncTask(dao).execute(model);
 }

 // creating a method to update data in database.
 public void update(Contacts contacts) {
  new UpdateCourseAsyncTask(dao).execute(contacts);
 }

 // creating a method to delete the data in our database.
 public void delete(Contacts contacts) {
  new DeleteCourseAsyncTask(dao).execute(contacts);
 }

 // below is the method to delete all the courses.
 public void deleteAllCourses() {
  new DeleteAllCoursesAsyncTask(dao).execute();
 }

 // below method is to read all the courses.
 public LiveData<List<Contacts>> getAllContacts() {
  allCourses = dao.getAllContacts(0);
  return allCourses;
 }

 public LiveData<List<Contacts>> getAllFavouriteContacts() {
  allCourses = dao.getAllFavouriteContacts(1);
  return allCourses;
 }

 public LiveData<List<Contacts>> getAllDeletedContacts() {
  allCourses = dao.getAllDeletedContacts(1);
  return allCourses;
 }

 // we are creating a async task method to insert new course.
 private static class InsertCourseAsyncTask extends AsyncTask<Contacts, Void, Void> {
  private ContactsDao dao;

  private InsertCourseAsyncTask(ContactsDao dao) {
   this.dao = dao;
  }

  @Override
  protected Void doInBackground(Contacts... model) {
   dao.insert(model[0]);
   return null;
  }
 }

 // we are creating a async task method to update our course.
 private static class UpdateCourseAsyncTask extends AsyncTask<Contacts, Void, Void> {
  private ContactsDao dao;

  private UpdateCourseAsyncTask(ContactsDao dao) {
   this.dao = dao;
  }

  @Override
  protected Void doInBackground(Contacts... contacts) {
   // below line is use to update
   // our modal in dao.
   dao.update(contacts[0]);
   return null;
  }
 }

 // we are creating a async task method to delete course.
 private static class DeleteCourseAsyncTask extends AsyncTask<Contacts, Void, Void> {
  private ContactsDao dao;

  private DeleteCourseAsyncTask(ContactsDao dao) {
   this.dao = dao;
  }

  @Override
  protected Void doInBackground(Contacts... models) {
   // below line is use to delete
   // our course modal in dao.
   dao.delete(models[0].getPhoneNumber());
   return null;
  }
 }

 // we are creating a async task method to delete all courses.
 private static class DeleteAllCoursesAsyncTask extends AsyncTask<Void, Void, Void>{
  private ContactsDao dao;
  private DeleteAllCoursesAsyncTask(ContactsDao dao) {
   this.dao = dao;
  }
  @Override
  protected Void doInBackground(Void... voids) {
   // on below line calling method
   // to delete all courses.
   dao.deleteAllCourses();
   return null;
  }
 }
}
