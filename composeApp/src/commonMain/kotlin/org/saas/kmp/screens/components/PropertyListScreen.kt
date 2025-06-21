package org.saas.kmp.screens.components

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import org.saas.kmp.model.PropertyFilter

// Usage example:
@Composable
fun PropertyListScreen() {
    var showFilters by remember { mutableStateOf(false) }
    var currentFilter by remember { mutableStateOf(PropertyFilter()) }

    // Your other UI content here...

    // Button to show filter dialog
    Button(
        onClick = { showFilters = true }
    ) {
        Text("Show Filters")
    }

    // Filter dialog
    if (showFilters) {
        FilterDialog(
            currentFilter = currentFilter,
            onFilterUpdate = { newFilter ->
                currentFilter = newFilter
                showFilters = false
                // Apply filters to your property list here
            },
            onDismiss = { showFilters = false }
        )
    }
}
