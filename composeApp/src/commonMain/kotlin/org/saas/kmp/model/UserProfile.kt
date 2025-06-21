package org.saas.kmp.model

data class UserProfile(
    val name: String = "John Doe",
    val budget: IntRange = 800..1500,
    val preferredArea: String = "City Center",
    val roomCount: Int = 2,
    val petFriendly: Boolean = false,
    val furnished: Boolean = true,
    val balcony: Boolean = true,
    val parking: Boolean = false
)