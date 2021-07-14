package com.example.contactsdemo.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.contactsdemo.Pojo.Contacts;

import java.util.List;

@androidx.room.Dao
public interface ContactsDao{
 @Insert(onConflict=OnConflictStrategy.REPLACE)
 void insert(Contacts contacts);

 @Update()
 void update(Contacts contacts);

 // below method is use to update
 // the data in our database.
 @Query("UPDATE contacts_table SET favFlag = :fav WHERE phoneNumber = :number")
 void updateFav(String number,int fav);

 @Query("UPDATE contacts_table SET deleteFlag = :del WHERE phoneNumber = :number")
 void updateDel(String number,int del);

 // below line is use to delete a
 // specific course in our database.
 @Query("UPDATE contacts_table SET deleteFlag = 1 WHERE phoneNumber = :phoneNumber")
 void delete(String phoneNumber);

 @Query("UPDATE contacts_table SET deleteFlag = 0 WHERE deleteFlag = 1")
 void restoreAll();

 // on below line we are making query to
 // delete all courses from our database.
 @Query("DELETE FROM contacts_table")
 void deleteAllCourses();

 // below line is to read all the courses from our database.
 // in this we are ordering our courses in ascending order
 // with our course name.
 @Query("SELECT * FROM contacts_table where deleteFlag= :deleteId order by name")
 LiveData<List<Contacts>> getAllContacts(int deleteId);

 @Query("SELECT * FROM contacts_table where favFlag = :favId order by name")
 LiveData<List<Contacts>> getAllFavouriteContacts(int favId);

 @Query("SELECT * FROM contacts_table where deleteFlag = :deleteId order by name")
 LiveData<List<Contacts>> getAllDeletedContacts(int deleteId);

 @Query("SELECT * FROM contacts_table where deleteFlag= :deleteId order by name")
 List<Contacts> getAllContacts1(int deleteId);

 @Query("SELECT * FROM contacts_table where favFlag = :favId order by name")
 List<Contacts> getAllFavouriteContacts1(int favId);

 @Query("SELECT * FROM contacts_table where deleteFlag = :deleteId order by name")
 List<Contacts> getAllDeletedContacts1(int deleteId);

 @Query("SELECT count(*) FROM contacts_table where deleteFlag = 0 order by name")
 Long getCount();


}
