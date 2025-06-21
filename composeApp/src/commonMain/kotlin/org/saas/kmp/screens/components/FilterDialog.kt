package org.saas.kmp.screens.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.saas.kmp.model.PropertyFilter
import org.saas.kmp.screens.FeatureToggle

@Composable
fun FilterDialog(
    currentFilter: PropertyFilter,
    onFilterUpdate: (PropertyFilter) -> Unit,
    onDismiss: () -> Unit
) {
    var localFilter by remember { mutableStateOf(currentFilter) }
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Filter Properties",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                FeatureToggle(
                    label = "Balcony",
                    value = localFilter.balcony,
                    onValueChange = { localFilter = localFilter.copy(balcony = it) }
                )

                // Add more filter options here as needed
                // Example:
                // FeatureToggle(
                //     label = "Parking",
                //     value = localFilter.parking,
                //     onValueChange = { localFilter = localFilter.copy(parking = it) }
                // )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onFilterUpdate(localFilter)
                    onDismiss()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF2196F3)
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = "Apply Filters",
                    fontSize = 14.sp,
                    color = Color.White
                )
            }
        },
        dismissButton = {
            OutlinedButton(
                onClick = {
                    localFilter = PropertyFilter() // Reset to default
                },
                colors = ButtonDefaults.outlinedButtonColors(),
                border = BorderStroke(1.dp, Color.Gray),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = "Reset",
                    color = Color.Gray,
                    fontSize = 14.sp
                )
            }
        },
        shape = RoundedCornerShape(16.dp),
        containerColor = Color.White
    )
}