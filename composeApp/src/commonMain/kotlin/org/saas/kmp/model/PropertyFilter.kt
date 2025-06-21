package org.saas.kmp.model

data class PropertyFilter(
    val priceRange: IntRange = 0..3000,
    val minRooms: Int = 1,
    val maxRooms: Int = 5,
    val area: String = "Any",
    val furnished: Boolean? = null,
    val petFriendly: Boolean? = null,
    val parking: Boolean? = null,
    val balcony: Boolean? = null
)