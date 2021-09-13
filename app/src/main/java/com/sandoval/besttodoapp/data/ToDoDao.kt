package com.sandoval.besttodoapp.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sandoval.besttodoapp.data.models.ToDoData
import com.sandoval.besttodoapp.utils.databaseTable

//In this class we declare the queries that we will need for our app.
//For best practices, the Dao class should be declared as an interface
// The DAO allows to insert, read, update and delete. Using the annotations of room
// @Insert, @Read, @Update, @Delete. For custom queries just use @Query

@Dao
interface ToDoDao {

    //Query all data
    //We will query all the data but for this we will use a LiveData object to
    //control the states and the lifecycle of this data.
    @Query("SELECT * FROM todo_table ORDER BY id ASC")
    fun getAllData(): LiveData<List<ToDoData>>

    //Insert new data - suspend function to be used in a coroutine in the future
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertData(toDoData: ToDoData)
}