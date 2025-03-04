package fr.isen.digiovanni.isensmartcompanion.models

//import com.google.gson.annotations.SerializedName

data class Event(
    val id: String,
    val title: String,
    val description: String,
    val date: String,
    val location: String,
    val category: String
)
