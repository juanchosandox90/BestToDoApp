package com.sandoval.besttodoapp.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.sandoval.besttodoapp.data.models.ToDoData

//In this class we declare the queries that we will need for our app.
//For best practices, the Dao class should be declared as an interface
// The DAO allows to insert, read, update and delete. Using the annotations of room
// @Insert, @Read, @Update, @Delete. For custom queries just use @Query

@Dao
interface ToDoDao {

    //Query all data
    //We will query all the data but for this we will use a LiveData object in the future to
    //control de states and the lifecycle of this data.
    //TODO: Query all data with another function -> getAllData and use a LiveData component.

    //Insert new data - suspend function to be used in a couroutine in the future
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertData(toDoData: ToDoData)
}