package org.saas.kmp.screens.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.BorderStroke
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.saas.kmp.model.HouseProperty

@Composable
fun PropertyCard(
    property: HouseProperty,
    onClick: () -> Unit,
    onEmailClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(12.dp),
        border = if (property.matchPercentage > 80) BorderStroke(2.dp, Color(0xFF4CAF50)) else null
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Header with match percentage
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = property.title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier.weight(1f)
                )

                if (property.matchPercentage > 0) {
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = when {
                                property.matchPercentage >= 80 -> Color(0xFF4CAF50)
                                property.matchPercentage >= 60 -> Color(0xFFFF9800)
                                else -> Color(0xFF9E9E9E)
                            }.copy(alpha = 0.1f)
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = "${property.matchPercentage}% match",
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            color = when {
                                property.matchPercentage >= 80 -> Color(0xFF4CAF50)
                                property.matchPercentage >= 60 -> Color(0xFFFF9800)
                                else -> Color(0xFF9E9E9E)
                            },
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Address and basic info
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Default.LocationOn,
                    contentDescription = null,
                    tint = Color.Gray,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = property.address,
                    color = Color.Gray,
                    fontSize = 13.sp,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Property details
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                PropertyDetail(
                    icon = Icons.Default.Euro,
                    text = "${property.price}/month",
                    color = Color(0xFF2196F3)
                )
                PropertyDetail(
                    icon = Icons.Default.Bed,
                    text = "${property.rooms} rooms"
                )
                PropertyDetail(
                    icon = Icons.Default.SquareFoot,
                    text = "${property.area}m²"
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Features
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                items(property.features.take(3)) { feature ->
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFFF5F5F5)
                        ),
                        shape = RoundedCornerShape(6.dp)
                    ) {
                        Text(
                            text = feature,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            fontSize = 11.sp,
                            color = Color(0xFF666666)
                        )
                    }
                }
                if (property.features.size > 3) {
                    item {
                        Card(
                            colors = CardDefaults.cardColors(
                                containerColor = Color(0xFFE3F2FD)
                            ),
                            shape = RoundedCornerShape(6.dp)
                        ) {
                            Text(
                                text = "+${property.features.size - 3} more",
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                fontSize = 11.sp,
                                color = Color(0xFF2196F3)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Action buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedButton(
                    onClick = onClick,
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.outlinedButtonColors(),
                    border = BorderStroke(1.dp, Color(0xFF2196F3)),
                    shape = RoundedCornerShape(6.dp)
                ) {
                    Text(
                        text = "View Details",
                        color = Color(0xFF2196F3),
                        fontSize = 12.sp
                    )
                }

                Button(
                    onClick = onEmailClick,
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF4CAF50)
                    ),
                    shape = RoundedCornerShape(6.dp)
                ) {
                    Icon(
                        Icons.Default.Email,
                        contentDescription = null,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Contact",
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}
