package org.saas.kmp.screens.sampledata

import org.saas.kmp.model.HouseProperty

object SampleData {
    val sampleProperties: List<HouseProperty> by lazy {
        listOf(
            HouseProperty(
                id = "H001",
                title = "Modern City Apartment",
                address = "123 Main Street, City Center",
                price = 1200,
                rooms = 2,
                area = 65,
                description = "Beautiful modern apartment in the heart of the city with all amenities.",
                features = listOf("Furnished", "Balcony", "Central Heating", "High-Speed Internet"),
                contactEmail = "landlord@example.com",
                contactPhone = "+49 123 456 7890",
                landlordName = "Maria Schmidt",
                matchPercentage = 95
            ),
            HouseProperty(
                id = "H002",
                title = "Cozy Studio Near University",
                address = "456 College Ave, University District",
                price = 850,
                rooms = 1,
                area = 35,
                description = "Perfect for students, close to university and public transport.",
                features = listOf("Furnished", "WiFi", "Laundry", "Security"),
                contactEmail = "student.housing@uni.de",
                contactPhone = "+49 987 654 3210",
                landlordName = "Hans Mueller",
                matchPercentage = 75
            ),
            HouseProperty(
                id = "H003",
                title = "Family House with Garden",
                address = "789 Oak Street, Suburban Area",
                price = 1800,
                rooms = 4,
                area = 120,
                description = "Spacious family house with private garden and parking space.",
                features = listOf("Garden", "Parking", "Pet-Friendly", "Dishwasher"),
                contactEmail = "family.home@realty.de",
                contactPhone = "+49 555 123 4567",
                landlordName = "Anna Weber",
                matchPercentage = 60
            ),
            HouseProperty(
                id = "H004",
                title = "Luxury Penthouse Downtown",
                address = "321 Sky Tower, Downtown",
                price = 2500,
                rooms = 3,
                area = 90,
                description = "Luxurious penthouse with panoramic city views and premium finishes.",
                features = listOf("City View", "Balcony", "Elevator", "Concierge", "Gym"),
                contactEmail = "luxury@skytower.de",
                contactPhone = "+49 111 222 3333",
                landlordName = "Robert Klein",
                matchPercentage = 40
            )
        )
    }
}