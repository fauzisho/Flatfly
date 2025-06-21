package org.saas.kmp.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import org.saas.kmp.model.HouseProperty
import org.saas.kmp.model.PropertyFilter
import org.saas.kmp.model.UserProfile
import org.saas.kmp.screens.components.EmailGeneratorDialog
import org.saas.kmp.screens.components.FilterDialog
import org.saas.kmp.screens.components.ProfileSection
import org.saas.kmp.screens.components.PropertyCard
import org.saas.kmp.screens.components.PropertyDetailDialog
import org.saas.kmp.screens.components.PropertyList
import org.saas.kmp.screens.sampledata.SampleData


@Composable
fun ResidenceScreen(
    rootNavController: NavController,
    paddingValues: PaddingValues
) {
    var selectedTab by remember { mutableStateOf("Find Houses") }
    var showFilters by remember { mutableStateOf(false) }
    var showEmailDialog by remember { mutableStateOf(false) }
    var selectedProperty by remember { mutableStateOf<HouseProperty?>(null) }
    var showPropertyDetail by remember { mutableStateOf(false) }
    var currentFilter by remember { mutableStateOf(PropertyFilter()) }

    // Sample user profile
    val userProfile = remember { UserProfile() }

    // Sample properties data
    val sampleProperties = remember { SampleData.sampleProperties }

    // Filter properties based on current filter and calculate match percentage
    val filteredProperties = remember(currentFilter) {
        sampleProperties.filter { property ->
            property.price in currentFilter.priceRange &&
                    property.rooms >= currentFilter.minRooms &&
                    property.rooms <= currentFilter.maxRooms &&
                    (currentFilter.area == "Any" || property.address.contains(
                        currentFilter.area,
                        ignoreCase = true
                    )) &&
                    (currentFilter.furnished == null || property.features.contains("Furnished") == currentFilter.furnished) &&
                    (currentFilter.petFriendly == null || property.features.contains("Pet-Friendly") == currentFilter.petFriendly) &&
                    (currentFilter.parking == null || property.features.contains("Parking") == currentFilter.parking) &&
                    (currentFilter.balcony == null || property.features.contains("Balcony") == currentFilter.balcony)
        }.sortedByDescending { it.matchPercentage }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(paddingValues)
    ) {
        // Header with tabs
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(end = 16.dp)
        ) {
            items(listOf("Find Houses", "My Profile", "Saved Houses", "Applications")) { tab ->
                Text(
                    text = tab,
                    color = if (selectedTab == tab) Color.White else Color.Gray,
                    fontWeight = if (selectedTab == tab) FontWeight.Bold else FontWeight.Normal,
                    modifier = Modifier
                        .background(
                            if (selectedTab == tab) Color.Black else Color.Transparent,
                            RoundedCornerShape(16.dp)
                        )
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                        .clickable { selectedTab = tab },
                    maxLines = 1,
                    fontSize = 14.sp
                )
            }
        }

        // Control buttons
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Filter button
            OutlinedButton(
                onClick = { showFilters = true },
                colors = ButtonDefaults.outlinedButtonColors(),
                border = BorderStroke(1.dp, Color(0xFF2196F3)),
                shape = RoundedCornerShape(8.dp)
            ) {
                Icon(
                    Icons.Default.FilterList,
                    contentDescription = "Filters",
                    tint = Color(0xFF2196F3),
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "Filters",
                    color = Color(0xFF2196F3),
                    fontSize = 13.sp
                )
            }

            // Results count
            Text(
                text = "${filteredProperties.size} properties found",
                color = Color.Gray,
                fontSize = 13.sp
            )
        }

        // Content based on selected tab
        when (selectedTab) {
            "Find Houses" -> {
                PropertyList(
                    properties = filteredProperties,
                    onPropertyClick = { property ->
                        selectedProperty = property
                        showPropertyDetail = true
                    },
                    onEmailClick = { property ->
                        selectedProperty = property
                        showEmailDialog = true
                    }
                )
            }

            "My Profile" -> {
                ProfileSection(
                    userProfile = userProfile,
                    modifier = Modifier.fillMaxSize()
                )
            }

            "Saved Houses" -> {
                SavedHousesSection(
                    properties = filteredProperties.take(2),
                    modifier = Modifier.fillMaxSize()
                )
            }

            "Applications" -> {
                ApplicationsSection(
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }

    // Dialogs
    if (showFilters) {
        FilterDialog(
            currentFilter = currentFilter,
            onFilterUpdate = { newFilter ->
                currentFilter = newFilter
                showFilters = false
            },
            onDismiss = { showFilters = false }
        )
    }

    if (showPropertyDetail && selectedProperty != null) {
        PropertyDetailDialog(
            property = selectedProperty!!,
            onDismiss = {
                showPropertyDetail = false
                selectedProperty = null
            },
            onEmailClick = {
                showPropertyDetail = false
                showEmailDialog = true
            }
        )
    }

    if (showEmailDialog && selectedProperty != null) {
        EmailGeneratorDialog(
            property = selectedProperty!!,
            userProfile = userProfile,
            onDismiss = {
                showEmailDialog = false
                selectedProperty = null
            }
        )
    }
}


@Composable
fun SavedHousesSection(
    properties: List<HouseProperty>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(16.dp)
    ) {
        if (properties.isEmpty()) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    Icons.Default.Favorite,
                    contentDescription = null,
                    tint = Color.Gray,
                    modifier = Modifier.size(64.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "No saved houses yet",
                    color = Color.Gray,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Start exploring properties and save your favorites!",
                    color = Color.Gray,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center
                )
            }
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(properties) { property ->
                    PropertyCard(
                        property = property,
                        onClick = { /* View property */ },
                        onEmailClick = { /* Send email */ }
                    )
                }
            }
        }
    }
}

@Composable
fun ApplicationsSection(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(16.dp)
    ) {
        Text(
            text = "My Applications",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Sample application status
        val applications = listOf(
            Triple("Modern City Apartment", "Pending", Color(0xFFFF9800)),
            Triple("Cozy Studio Near University", "Approved", Color(0xFF4CAF50)),
            Triple("Family House with Garden", "Rejected", Color(0xFF9E9E9E))
        )

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(applications) { (title, status, color) ->
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = title,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.Black,
                            modifier = Modifier.weight(1f)
                        )

                        Card(
                            colors = CardDefaults.cardColors(
                                containerColor = color.copy(alpha = 0.1f)
                            ),
                            shape = RoundedCornerShape(6.dp)
                        ) {
                            Text(
                                text = status,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                color = color,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun FilterSection(
    title: String,
    content: @Composable () -> Unit
) {
    Column {
        Text(
            text = title,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(8.dp))
        content()
    }
}

@Composable
fun FilterChip(
    selected: Boolean,
    onClick: () -> Unit,
    text: String
) {
    Card(
        modifier = Modifier.clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = if (selected) Color(0xFF2196F3) else Color(0xFFF5F5F5)
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            color = if (selected) Color.White else Color.Black,
            fontSize = 13.sp,
            fontWeight = if (selected) FontWeight.Medium else FontWeight.Normal
        )
    }
}

@Composable
fun FeatureToggle(
    label: String,
    value: Boolean?,
    onValueChange: (Boolean?) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            fontSize = 14.sp,
            color = Color.Black
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            FilterChip(
                selected = value == true,
                onClick = { onValueChange(if (value == true) null else true) },
                text = "Yes"
            )
            FilterChip(
                selected = value == false,
                onClick = { onValueChange(if (value == false) null else false) },
                text = "No"
            )
            FilterChip(
                selected = value == null,
                onClick = { onValueChange(null) },
                text = "Any"
            )
        }
    }
}