package fr.isen.digiovanni.isensmartcompanion.notifications

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.os.Build
import androidx.core.app.NotificationCompat
import fr.isen.digiovanni.isensmartcompanion.MainActivity
import fr.isen.digiovanni.isensmartcompanion.R
import android.app.AlarmManager
import android.os.SystemClock
import android.provider.Settings
import android.util.Log

class ReminderReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent?) {
        val eventId = intent?.getStringExtra("eventId") ?: return
        val eventTitle = intent.getStringExtra("eventTitle") ?: "Event Reminder"

        // Créer le canal de notification pour Android 8+
        val channelId = "event_reminder_channel"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, "Event Reminders", NotificationManager.IMPORTANCE_HIGH)
            val notificationManager = context.getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }

        // Intent pour ouvrir l'application lorsqu'on clique sur la notification
        val notificationIntent = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

        // Construire la notification
        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Reminder: $eventTitle")
            .setContentText("Don't forget your event!")
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        // Envoyer la notification
        val notificationManager = context.getSystemService(NotificationManager::class.java)
        notificationManager.notify(eventId.hashCode(), notification)
    }

    companion object {
        // Méthode statique pour planifier les notifications
        fun scheduleNotification(context: Context, eventId: String, eventTitle: String) {
            try {
                // Vérifier si l'application a la permission de planifier des alarmes exactes
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
                    if (!alarmManager.canScheduleExactAlarms()) {
                        // Demander à l'utilisateur de permettre les alarmes exactes
                        val intent = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM)
                        context.startActivity(intent)
                        return
                    }
                }

                // Créer un intent pour le Receiver
                val intent = Intent(context, ReminderReceiver::class.java).apply {
                    putExtra("eventId", eventId)
                    putExtra("eventTitle", eventTitle)
                }

                // Créer un PendingIntent
                val pendingIntent = PendingIntent.getBroadcast(context, eventId.hashCode(), intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

                // Utiliser AlarmManager pour planifier la notification
                val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
                val triggerAtMillis = SystemClock.elapsedRealtime() + 10000 // 10 secondes

                alarmManager.setExact(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtMillis, pendingIntent)

            } catch (e: Exception) {
                Log.e("scheduleNotification", "Error scheduling notification: ${e.message}")
            }
        }
    }
}
