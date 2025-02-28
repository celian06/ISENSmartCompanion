package fr.isen.digiovanni.isensmartcompanion.models

class FakeEventRepository {
    object FakeEventRepository {
        val eventList = listOf(
            Event(1, "BDE Evening", "A fun evening with games and music.", "2025-05-20", "ISEN Campus", "Social"),
            Event(2, "Gala", "A formal event with dinner and dancing.", "2025-06-15", "ISEN Campus", "Formal"),
            Event(3, "Cohesion Day", "A team-building day with outdoor activities.", "2025-07-10", "ISEN Campus", "Outdoor")
        )
    }
}