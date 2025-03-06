package fr.isen.digiovanni.isensmartcompanion

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import fr.isen.digiovanni.isensmartcompanion.models.Event
import fr.isen.digiovanni.isensmartcompanion.notifications.ReminderReceiver

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

        // Récupérer le bouton de notification
        val notificationButton = findViewById<ImageButton>(R.id.notificationButton)
        val sharedPreferences = getSharedPreferences("EventPrefs", Context.MODE_PRIVATE)
        val isNotified = sharedPreferences.getBoolean("event_${event.id}", false)

        // Mettre à jour l'icône initiale
        updateNotificationIcon(notificationButton, isNotified)

        // Gérer le clic sur le bouton
        notificationButton.setOnClickListener {
            val newState = !sharedPreferences.getBoolean("event_${event.id}", false)
            sharedPreferences.edit().putBoolean("event_${event.id}", newState).apply()

            updateNotificationIcon(notificationButton, newState)

            if (newState) {
                ReminderReceiver.scheduleNotification(this, event.id.toString(), event.title)
            }
        }
    }

    private fun updateNotificationIcon(button: ImageButton, isEnabled: Boolean) {
        if (isEnabled) {
            button.setImageResource(android.R.drawable.ic_notification_overlay) // Icône activée
        } else {
            button.setImageResource(android.R.drawable.ic_delete) // Icône désactivée
        }
    }
}
