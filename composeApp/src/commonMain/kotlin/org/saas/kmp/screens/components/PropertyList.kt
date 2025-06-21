package org.saas.kmp.screens.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import org.saas.kmp.model.HouseProperty

@Composable
fun PropertyList(
    properties: List<HouseProperty>,
    onPropertyClick: (HouseProperty) -> Unit,
    onEmailClick: (HouseProperty) -> Unit
) {
    BoxWithConstraints(
        modifier = Modifier.fillMaxSize()
    ) {
        val screenWidth = maxWidth
        val density = LocalDensity.current
        
        // Determine if we should use grid layout (2 columns) based on screen width
        val useGridLayout = screenWidth > 600.dp
        
        if (useGridLayout) {
            // Grid layout for larger screens (web/desktop)
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Group properties into pairs for 2-column layout
                val groupedProperties = properties.chunked(2)
                
                items(groupedProperties) { propertyPair ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        // First property card
                        Box(modifier = Modifier.weight(1f)) {
                            PropertyCard(
                                property = propertyPair[0],
                                onClick = { onPropertyClick(propertyPair[0]) },
                                onEmailClick = { onEmailClick(propertyPair[0]) }
                            )
                        }
                        
                        // Second property card (if exists)
                        if (propertyPair.size > 1) {
                            Box(modifier = Modifier.weight(1f)) {
                                PropertyCard(
                                    property = propertyPair[1],
                                    onClick = { onPropertyClick(propertyPair[1]) },
                                    onEmailClick = { onEmailClick(propertyPair[1]) }
                                )
                            }
                        } else {
                            // Empty space if odd number of properties
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }
            }
        } else {
            // Single column layout for mobile screens
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(properties) { property ->
                    PropertyCard(
                        property = property,
                        onClick = { onPropertyClick(property) },
                        onEmailClick = { onEmailClick(property) }
                    )
                }
            }
        }
    }
}