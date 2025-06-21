package org.saas.kmp.screens.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.saas.kmp.model.HouseProperty

@Composable
fun PropertyList(
    properties: List<HouseProperty>,
    onPropertyClick: (HouseProperty) -> Unit,
    onEmailClick: (HouseProperty) -> Unit
) {
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