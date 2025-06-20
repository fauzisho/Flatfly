package org.saas.kmp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Circle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

data class TaskItem(
    val id: String,
    val title: String,
    val priority: Int,
    val isCompleted: Boolean = false
)

data class TaskCategory(
    val title: String,
    val count: Int,
    val items: List<TaskItem>,
    val isExpanded: Boolean = true
)

// Function to get appropriate icons for different tasks
fun getTaskIcon(taskTitle: String): ImageVector {
    return when {
        taskTitle.contains("University", ignoreCase = true) -> Icons.Default.School
        taskTitle.contains("Account", ignoreCase = true) -> Icons.Default.AccountBalance
        taskTitle.contains("Insurance", ignoreCase = true) -> Icons.Default.Security
        taskTitle.contains("CV", ignoreCase = true) || taskTitle.contains("Career", ignoreCase = true) -> Icons.Default.Work
        taskTitle.contains("Passport", ignoreCase = true) -> Icons.Default.Flight
        taskTitle.contains("Housing", ignoreCase = true) || taskTitle.contains("Contract", ignoreCase = true) -> Icons.Default.Home
        taskTitle.contains("Certificate", ignoreCase = true) -> Icons.Default.VerifiedUser
        taskTitle.contains("Visa", ignoreCase = true) -> Icons.Default.CardMembership
        taskTitle.contains("Pet", ignoreCase = true) -> Icons.Default.Pets
        taskTitle.contains("Bank", ignoreCase = true) -> Icons.Default.AccountBalance
        else -> Icons.Default.Assignment
    }
}

// Function to get task category color
fun getTaskCategoryColor(category: String): Color {
    return when (category) {
        "Completed" -> Color(0xFF4CAF50)
        "In Progress" -> Color(0xFF2196F3)
        "Not Started" -> Color(0xFF9E9E9E)
        else -> Color.Gray
    }
}

@Composable
fun DocumentScreen(
    rootNavController: NavController,
    paddingValues: PaddingValues
) {
    var selectedTab by remember { mutableStateOf("All Tasks") }
    var isGridView by remember { mutableStateOf(false) }
    
    // Sample data
    val completedTasks = listOf(
        TaskItem("H1-2", "LOA University", 5, true),
        TaskItem("H1-3", "Block Account", 1, true),
        TaskItem("H1-4", "TK Insurance", 1, true),
        TaskItem("H1-5", "CV Personal and Career", 3, true), // Added priority badge
        TaskItem("H1-6", "Passport", 2, true)
    )
    
    val inProgressTasks = listOf(
        TaskItem("HT-2", "Housing Insurance", 5),
        TaskItem("HT-3", "Schufa Certificate", 1),
        TaskItem("HT-4", "Visa Early", 1)
    )
    
    val notStartedTasks = listOf(
        TaskItem("HT-2", "Housing Contract", 5),
        TaskItem("HT-3", "Pet Passport", 1),
        TaskItem("HT-4", "Local Bank Account", 1)
    )
    
    val allCategories = listOf(
        TaskCategory("Completed", 5, completedTasks),
        TaskCategory("In Progress", 3, inProgressTasks),
        TaskCategory("Not Started", 3, notStartedTasks)
    )
    
    // Filter categories based on selected tab
    val filteredCategories = when (selectedTab) {
        "All Tasks" -> allCategories
        "Completed" -> listOf(TaskCategory("Completed", 5, completedTasks))
        "In Progress" -> listOf(TaskCategory("In Progress", 3, inProgressTasks))
        "Not Started" -> listOf(TaskCategory("Not Started", 3, notStartedTasks))
        else -> allCategories
    }
    
    var categories by remember {
        mutableStateOf(allCategories)
    }
    
    // Update categories when tab changes
    LaunchedEffect(selectedTab) {
        categories = filteredCategories.map { it.copy(isExpanded = true) }
    }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(paddingValues)
    ) {
        // Top tabs
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            listOf("All Tasks", "Completed", "In Progress", "Not Started").forEach { tab ->
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
                        .clickable { selectedTab = tab }
                )
            }
            
            Spacer(modifier = Modifier.weight(1f))
            
            // View toggle buttons
            Row {
                IconButton(
                    onClick = { isGridView = false },
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        Icons.Default.List,
                        contentDescription = "List View",
                        tint = if (!isGridView) Color.Black else Color.Gray
                    )
                }
                IconButton(
                    onClick = { isGridView = true },
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        Icons.Default.GridView,
                        contentDescription = "Grid View",
                        tint = if (isGridView) Color.Black else Color.Gray
                    )
                }
            }
        }
        
        // Task list
        if (isGridView) {
            // Grid View - Use a single LazyVerticalGrid for all items
            LazyVerticalGrid(
                columns = GridCells.Fixed(4), // Changed to 4 columns
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                categories.forEach { category ->
                    // Category header
                    item(span = { GridItemSpan(4) }) { // Span all 4 columns
                        TaskCategoryHeader(
                            category = category,
                            onToggleExpanded = { categoryTitle ->
                                categories = categories.map { 
                                    if (it.title == categoryTitle) it.copy(isExpanded = !it.isExpanded) 
                                    else it 
                                }
                            }
                        )
                    }
                    
                    // Category items
                    if (category.isExpanded) {
                        items(category.items) { task ->
                            TaskGridCard(task = task)
                        }
                    }
                }
            }
        } else {
            // List View
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(categories) { category ->
                    TaskCategorySection(
                        category = category,
                        onToggleExpanded = { categoryTitle ->
                            categories = categories.map { 
                                if (it.title == categoryTitle) it.copy(isExpanded = !it.isExpanded) 
                                else it 
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun TaskCategorySection(
    category: TaskCategory,
    onToggleExpanded: (String) -> Unit
) {
    Column {
        // Category header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { onToggleExpanded(category.title) },
                modifier = Modifier.size(24.dp)
            ) {
                Icon(
                    if (category.isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                    contentDescription = if (category.isExpanded) "Collapse" else "Expand",
                    tint = Color.Gray
                )
            }
            
            Icon(
                if (category.title == "Completed") Icons.Default.TaskAlt else Icons.Outlined.Circle,
                contentDescription = null,
                tint = Color.Gray,
                modifier = Modifier.size(20.dp)
            )
            
            Spacer(modifier = Modifier.width(8.dp))
            
            Text(
                text = category.title,
                fontWeight = FontWeight.Medium,
                color = Color.Black
            )
            
            Spacer(modifier = Modifier.width(8.dp))
            
            Text(
                text = category.count.toString(),
                color = Color.Gray,
                fontSize = 14.sp
            )
        }
        
        // Category items
        if (category.isExpanded) {
            Column(
                modifier = Modifier.padding(start = 32.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                category.items.forEach { task ->
                    TaskItemRow(task = task)
                }
            }
        }
    }
}

@Composable
fun TaskItemRow(task: TaskItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Task indicator
        Icon(
            Icons.Default.RadioButtonUnchecked,
            contentDescription = null,
            tint = Color.Gray,
            modifier = Modifier.size(16.dp)
        )
        
        Spacer(modifier = Modifier.width(12.dp))
        
        // Task ID
        Text(
            text = task.id,
            color = Color.Gray,
            fontSize = 14.sp,
            modifier = Modifier.width(40.dp)
        )
        
        Spacer(modifier = Modifier.width(12.dp))
        
        // Task status icon
        Icon(
            if (task.isCompleted) Icons.Default.TaskAlt else Icons.Default.RadioButtonUnchecked,
            contentDescription = null,
            tint = Color.Gray,
            modifier = Modifier.size(16.dp)
        )
        
        Spacer(modifier = Modifier.width(12.dp))
        
        // Task title
        Text(
            text = task.title,
            color = Color.Black,
            fontSize = 14.sp,
            modifier = Modifier.weight(1f)
        )
        
        // Priority badge
        if (task.priority > 0) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFFB39DDB)) // Soft purple color
                    .padding(horizontal = 8.dp, vertical = 2.dp)
            ) {
                Text(
                    text = task.priority.toString(),
                    color = Color.White,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Composable
fun TaskCategoryHeader(
    category: TaskCategory,
    onToggleExpanded: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = { onToggleExpanded(category.title) },
            modifier = Modifier.size(24.dp)
        ) {
            Icon(
                if (category.isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                contentDescription = if (category.isExpanded) "Collapse" else "Expand",
                tint = Color.Gray
            )
        }
        
        Icon(
            if (category.title == "Completed") Icons.Default.TaskAlt else Icons.Outlined.Circle,
            contentDescription = null,
            tint = Color.Gray,
            modifier = Modifier.size(20.dp)
        )
        
        Spacer(modifier = Modifier.width(8.dp))
        
        Text(
            text = category.title,
            fontWeight = FontWeight.Medium,
            color = Color.Black
        )
        
        Spacer(modifier = Modifier.width(8.dp))
        
        Text(
            text = category.count.toString(),
            color = Color.Gray,
            fontSize = 14.sp
        )
    }
}

@Composable
fun TaskCategoryGridSection(
    category: TaskCategory,
    onToggleExpanded: (String) -> Unit
) {
    Column {
        // Category header (same as list view)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { onToggleExpanded(category.title) },
                modifier = Modifier.size(24.dp)
            ) {
                Icon(
                    if (category.isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                    contentDescription = if (category.isExpanded) "Collapse" else "Expand",
                    tint = Color.Gray
                )
            }
            
            Icon(
                if (category.title == "Completed") Icons.Default.TaskAlt else Icons.Outlined.Circle,
                contentDescription = null,
                tint = Color.Gray,
                modifier = Modifier.size(20.dp)
            )
            
            Spacer(modifier = Modifier.width(8.dp))
            
            Text(
                text = category.title,
                fontWeight = FontWeight.Medium,
                color = Color.Black
            )
            
            Spacer(modifier = Modifier.width(8.dp))
            
            Text(
                text = category.count.toString(),
                color = Color.Gray,
                fontSize = 14.sp
            )
        }
        
        // Category items in grid
        if (category.isExpanded) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.padding(start = 32.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(category.items) { task ->
                    TaskGridCard(task = task)
                }
            }
        }
    }
}

@Composable
fun TaskGridCard(task: TaskItem) {
    val taskIcon = getTaskIcon(task.title)
    val statusColor = if (task.isCompleted) Color(0xFF4CAF50) else Color(0xFF2196F3)
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(0.85f),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            // Priority badge in top-right corner
            if (task.priority > 0) {
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(10.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color(0xFFB39DDB))
                        .padding(horizontal = 6.dp, vertical = 3.dp)
                ) {
                    Text(
                        text = task.priority.toString(),
                        color = Color.White,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            
            // Main content - using Column with SpaceBetween to eliminate gaps
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(14.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                // Top spacer for priority badge
                if (task.priority > 0) {
                    Spacer(modifier = Modifier.height(8.dp))
                }
                
                // Top content group
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Icon with background circle - 2x bigger
                    Box(
                        modifier = Modifier
                            .size(80.dp) // 2x bigger (was 40dp)
                            .clip(CircleShape)
                            .background(statusColor.copy(alpha = 0.1f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = taskIcon,
                            contentDescription = null,
                            tint = statusColor,
                            modifier = Modifier.size(40.dp) // 2x bigger (was 20dp)
                        )
                    }
                    
                    // Task ID - 2x bigger
                    Text(
                        text = task.id,
                        color = Color.Gray,
                        fontSize = 20.sp, // 2x bigger (was 10sp)
                        fontWeight = FontWeight.Medium
                    )
                    
                    // Task title - 2x bigger
                    Text(
                        text = task.title,
                        color = Color.Black,
                        fontSize = 24.sp, // 2x bigger (was 12sp)
                        fontWeight = FontWeight.SemiBold,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                        lineHeight = 28.sp // 2x bigger (was 14sp)
                    )
                }
                
                // Status indicator at bottom - 2x bigger
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp)) // 2x bigger (was 6dp)
                        .background(statusColor.copy(alpha = 0.1f))
                        .padding(vertical = 12.dp) // 2x bigger (was 6dp)
                ) {
                    Icon(
                        if (task.isCompleted) Icons.Default.CheckCircle else Icons.Default.Schedule,
                        contentDescription = null,
                        tint = statusColor,
                        modifier = Modifier.size(24.dp) // 2x bigger (was 12dp)
                    )
                    
                    Spacer(modifier = Modifier.width(8.dp)) // 2x bigger (was 4dp)
                    
                    Text(
                        text = if (task.isCompleted) "Completed" else "Pending",
                        color = statusColor,
                        fontSize = 20.sp, // 2x bigger (was 10sp)
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}
