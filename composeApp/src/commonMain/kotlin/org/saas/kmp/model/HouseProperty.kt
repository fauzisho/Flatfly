package org.saas.kmp.model

data class HouseProperty(
    val id: String,
    val title: String,
    val address: String,
    val price: Int,
    val rooms: Int,
    val area: Int, // m²
    val description: String,
    val features: List<String>,
    val imageUrl: String = "",
    val contactEmail: String,
    val contactPhone: String,
    val landlordName: String,
    val available: Boolean = true,
    val matchPercentage: Int = 0
)
