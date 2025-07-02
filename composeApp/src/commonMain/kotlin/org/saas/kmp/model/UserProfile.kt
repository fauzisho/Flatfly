package org.saas.kmp.model

data class UserProfile(
    val name: String = "Michael Salim\n",
    val budget: IntRange = 300..700,
    val preferredArea: String = "City Center, Near THI",
    val roomCount: Int = 2,
    val petFriendly: Boolean = false,
    val furnished: Boolean = true,
    val balcony: Boolean = true,
    val parking: Boolean = false
)