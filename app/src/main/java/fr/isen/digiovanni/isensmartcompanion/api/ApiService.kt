package fr.isen.digiovanni.isensmartcompanion.api

import fr.isen.digiovanni.isensmartcompanion.models.Event
import retrofit2.http.GET
import retrofit2.http.Path

interface EventService {
    @GET("event/{id}.json")
    suspend fun getEventDetails(@Path("id") id: String): Event

    // Récupérer tous les événements
    @GET("events.json")  // Modifie ceci selon ton endpoint
    suspend fun getEvents(): List<Event>
}