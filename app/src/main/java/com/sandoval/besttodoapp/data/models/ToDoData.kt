package com.sandoval.besttodoapp.data.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sandoval.besttodoapp.utils.databaseTable
import kotlinx.android.parcel.Parcelize

@Entity(tableName = databaseTable)
@Parcelize
data class ToDoData(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val priority: Priority,
    val description: String
) : Parcelable
