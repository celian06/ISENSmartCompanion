package fr.isen.digiovanni.isensmartcompanion

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import com.google.gson.Gson
//import fr.isen.digiovanni.isensmartcompanion.R
import fr.isen.digiovanni.isensmartcompanion.models.Event

class EventDetailActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_detail)

        // Récupérer l'événement passé en paramètre
        val eventJson = intent.getStringExtra("event")
        val event = Gson().fromJson(eventJson, Event::class.java)

        // Afficher les détails
        findViewById<TextView>(R.id.eventTitle).text = event.title
        findViewById<TextView>(R.id.eventDate).text = "Date: ${event.date}"
        findViewById<TextView>(R.id.eventLocation).text = "Lieu: ${event.location}"
        findViewById<TextView>(R.id.eventDescription).text = event.description
    }
}