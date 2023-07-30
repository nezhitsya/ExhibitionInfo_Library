package com.nezhitsya.example.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "exhibition")
data class MyExhibition(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val exhibitionTl: String,
    var exhibitionImg: String,
    var exhibitionDate: String,
    var exhibitionPlace: String,
    var exhibitionMap: String,
    var exhibitionPrice: String,
    var exhibitionView: String
)