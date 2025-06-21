package org.saas.kmp.screens.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.BorderStroke
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.saas.kmp.model.HouseProperty
import org.saas.kmp.model.UserProfile

@Composable
fun EmailGeneratorDialog(
    property: HouseProperty,
    userProfile: UserProfile,
    onDismiss: () -> Unit
) {
    var emailSubject by remember { mutableStateOf("") }
    var emailContent by remember { mutableStateOf("") }
    var selectedTemplate by remember { mutableStateOf("Viewing Request") }

    // Generate email content based on template
    LaunchedEffect(selectedTemplate, property, userProfile) {
        when (selectedTemplate) {
            "Viewing Request" -> {
                emailSubject = "Property Viewing Request - ${property.title}"
                emailContent = """
Dear ${property.landlordName},

I hope this email finds you well. I am writing to express my interest in the property located at ${property.address}, which I found through your listing.

About myself:
- Name: ${userProfile.name}
- Budget: €${userProfile.budget.first} - €${userProfile.budget.last} per month
- Looking for: ${userProfile.roomCount} room(s)
- Preferred area: ${userProfile.preferredArea}

I am particularly interested in this property because it matches my requirements for ${if (userProfile.furnished) "furnished accommodation" else "accommodation"} ${if (userProfile.petFriendly) "that is pet-friendly" else ""}.

Would it be possible to schedule a viewing at your earliest convenience? I am available most days and can be flexible with timing.

I would also appreciate any additional information about:
- Utility costs and what's included in the rent
- Lease terms and move-in requirements
- Availability date

Thank you for your time and consideration. I look forward to hearing from you soon.

Best regards,
${userProfile.name}
                """.trimIndent()
            }

            "Application" -> {
                emailSubject = "Rental Application - ${property.title}"
                emailContent = """
Dear ${property.landlordName},

I would like to formally apply for the rental property at ${property.address}.

Applicant Information:
- Full Name: ${userProfile.name}
- Monthly Budget: €${userProfile.budget.first} - €${userProfile.budget.last}
- Desired Move-in Date: As soon as possible
- Lease Duration: 12+ months preferred

I am a reliable tenant with stable income and excellent references. I am particularly drawn to this property because of its ${
                    property.features.joinToString(
                        ", "
                    )
                } and its location in ${property.address.substringAfterLast(", ")}.

I have attached the following documents:
- Proof of income/employment
- Previous landlord references
- ID copy
- Schufa credit report

I would be happy to provide any additional documentation you may require and am available for an interview at your convenience.

The monthly rent of €${property.price} fits comfortably within my budget, and I am prepared to pay the required deposit and first month's rent upon lease signing.

Thank you for considering my application. I look forward to the opportunity to discuss this further.

Best regards,
${userProfile.name}
                """.trimIndent()
            }

            "Follow-up" -> {
                emailSubject = "Follow-up: Property Inquiry - ${property.title}"
                emailContent = """
Dear ${property.landlordName},

I hope you are doing well. I am following up on my previous inquiry regarding the property at ${property.address}.

I remain very interested in this ${property.rooms}-room property and would appreciate any updates on its availability. The property's features, particularly ${
                    property.features.take(
                        2
                    ).joinToString(" and ")
                }, make it an ideal match for my housing needs.

If the property is still available, I would be grateful for the opportunity to:
- Schedule a viewing
- Submit a formal application
- Provide any additional information you might need

I am a serious candidate with all necessary documentation ready and can move forward quickly with the rental process.

Please let me know if there's a convenient time to discuss this further or if you need any additional information from me.

Thank you for your time.

Best regards,
${userProfile.name}
                """.trimIndent()
            }
        }
    }

    // Full screen overlay
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Header with status bar padding
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 40.dp) // Add status bar padding
                    .padding(horizontal = 16.dp, vertical = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Email AI Generator",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Text(
                        text = "To: ${property.contactEmail}",
                        fontSize = 13.sp,
                        color = Color.Gray,
                        modifier = Modifier.padding(top = 2.dp)
                    )
                }

                IconButton(onClick = onDismiss) {
                    Icon(
                        Icons.Default.Close,
                        contentDescription = "Close",
                        tint = Color.Gray,
                        modifier = Modifier.size(22.dp)
                    )
                }
            }

            // Content
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Template selector
                item {
                    Column {
                        Text(
                            text = "Email Template",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.Black
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(
                                listOf(
                                    "Viewing Request",
                                    "Application",
                                    "Follow-up"
                                )
                            ) { template ->
                                org.saas.kmp.screens.FilterChip(
                                    selected = selectedTemplate == template,
                                    onClick = { selectedTemplate = template },
                                    text = template
                                )
                            }
                        }
                    }
                }

                // Subject
                item {
                    Column {
                        Text(
                            text = "Subject",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.Black
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = emailSubject,
                            onValueChange = { emailSubject = it },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                            shape = RoundedCornerShape(8.dp)
                        )
                    }
                }

                // Email content
                item {
                    Column {
                        Text(
                            text = "Email Content",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.Black
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = emailContent,
                            onValueChange = { emailContent = it },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(300.dp), // Reduced from 400dp to 300dp
                            shape = RoundedCornerShape(8.dp),
                            maxLines = 15 // Reduced from 20 to 15
                        )
                    }
                }

                // Action buttons
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 20.dp, bottom = 80.dp), // Reduced spacing
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        OutlinedButton(
                            onClick = { /* Copy to clipboard */ },
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.outlinedButtonColors(),
                            border = BorderStroke(1.dp, Color(0xFF03A9F4)),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Icon(
                                Icons.Default.ContentCopy,
                                contentDescription = null,
                                tint = Color(0xFF03A9F4),
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Copy",
                                color = Color(0xFF03A9F4),
                                fontSize = 13.sp
                            )
                        }

                        Button(
                            onClick = {
                                // Close dialog when send is clicked
                                onDismiss()
                            },
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF4CAF50)
                            ),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Icon(
                                Icons.AutoMirrored.Filled.Send,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Send Email",
                                fontSize = 13.sp
                            )
                        }
                    }
                }
            }
        }
    }
}