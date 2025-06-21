package org.saas.kmp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.BorderStroke
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Assignment
import androidx.compose.material.icons.automirrored.filled.InsertDriveFile
import androidx.compose.material.icons.automirrored.filled.List
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
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
        taskTitle.contains("CV", ignoreCase = true) || taskTitle.contains(
            "Career",
            ignoreCase = true
        ) -> Icons.Default.Work

        taskTitle.contains("Passport", ignoreCase = true) -> Icons.Default.Flight
        taskTitle.contains("Housing", ignoreCase = true) || taskTitle.contains(
            "Contract",
            ignoreCase = true
        ) -> Icons.Default.Home

        taskTitle.contains("Certificate", ignoreCase = true) -> Icons.Default.VerifiedUser
        taskTitle.contains("Visa", ignoreCase = true) -> Icons.Default.CardMembership
        taskTitle.contains("Pet", ignoreCase = true) -> Icons.Default.Pets
        taskTitle.contains("Bank", ignoreCase = true) -> Icons.Default.AccountBalance
        else -> Icons.AutoMirrored.Filled.Assignment
    }
}

@Composable
fun DocumentScreen(
    @Suppress("UNUSED_PARAMETER") rootNavController: NavController,
    paddingValues: PaddingValues
) {
    var selectedTab by remember { mutableStateOf("All Tasks") }
    var isGridView by remember { mutableStateOf(false) }
    var showUploadDialog by remember { mutableStateOf(false) }
    var selectedTask by remember { mutableStateOf<TaskItem?>(null) }
    var showTaskDetailDialog by remember { mutableStateOf(false) }

    // Sample data
    val completedTasks = listOf(
        TaskItem("H1-2", "LOA University", 5, true),
        TaskItem("H1-3", "Block Account", 1, true),
        TaskItem("H1-4", "TK Insurance", 1, true),
        TaskItem("H1-5", "CV Personal and Career", 3, true),
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
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(end = 16.dp)
        ) {
            items(listOf("All Tasks", "Completed", "In Progress", "Not Started")) { tab ->
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
                    maxLines = 1
                )
            }
        }

        // Control buttons row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Magic Upload button
            Button(
                onClick = { showUploadDialog = true },
                modifier = Modifier.height(32.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF03A9F4)
                ),
                contentPadding = PaddingValues(horizontal = 12.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = "Magic Upload",
                    color = Color.White,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            // View toggle buttons
            Row {
                IconButton(
                    onClick = { isGridView = false },
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        Icons.AutoMirrored.Filled.List,
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
                columns = GridCells.Fixed(4),
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                categories.forEach { category ->
                    // Category header
                    item(span = { GridItemSpan(4) }) {
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
                            TaskGridCard(
                                task = task,
                                onClick = {
                                    selectedTask = task
                                    showTaskDetailDialog = true
                                }
                            )
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
                        },
                        onTaskClick = { task ->
                            selectedTask = task
                            showTaskDetailDialog = true
                        }
                    )
                }
            }
        }
    }

    // Upload Dialog
    if (showUploadDialog) {
        FileUploadDialog(
            onDismiss = { showUploadDialog = false },
            onFileSelected = { _ ->
                // Handle file selection here
                showUploadDialog = false
                // You can add logic to process the uploaded file
            }
        )
    }

    // Task Detail Dialog
    if (showTaskDetailDialog && selectedTask != null) {
        TaskDetailDialog(
            task = selectedTask!!,
            onDismiss = {
                showTaskDetailDialog = false
                selectedTask = null
            },
            onTaskUpdate = { updatedTask ->
                // Update the task in the categories
                categories = categories.map { category ->
                    category.copy(
                        items = category.items.map { task ->
                            if (task.id == updatedTask.id) updatedTask else task
                        }
                    )
                }
                showTaskDetailDialog = false
                selectedTask = null
            },
            onTaskDelete = { taskId ->
                // Remove the task from categories
                categories = categories.map { category ->
                    category.copy(
                        items = category.items.filter { it.id != taskId },
                        count = category.items.filter { it.id != taskId }.size
                    )
                }
                showTaskDetailDialog = false
                selectedTask = null
            }
        )
    }
}

@Composable
fun TaskCategorySection(
    category: TaskCategory,
    onToggleExpanded: (String) -> Unit,
    onTaskClick: (TaskItem) -> Unit
) {
    Column {
        // Category header
        TaskCategoryHeader(
            category = category,
            onToggleExpanded = onToggleExpanded
        )

        // Category items
        if (category.isExpanded) {
            Column(
                modifier = Modifier.padding(start = 32.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                category.items.forEach { task ->
                    TaskItemRow(
                        task = task,
                        onClick = { onTaskClick(task) }
                    )
                }
            }
        }
    }
}

@Composable
fun TaskItemRow(
    task: TaskItem,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Task status icon
        Icon(
            if (task.isCompleted) Icons.Default.TaskAlt else Icons.Default.RadioButtonUnchecked,
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
                    .background(Color(0xFF03A9F4))
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
fun TaskGridCard(
    task: TaskItem,
    onClick: () -> Unit
) {
    val taskIcon = getTaskIcon(task.title)
    val statusColor = if (task.isCompleted) Color(0xFF4CAF50) else Color(0xFF2196F3)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(0.85f)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            if (task.priority > 0) {
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(10.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color(0xFF03A9F4))
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

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(14.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                if (task.priority > 0) {
                    Spacer(modifier = Modifier.height(8.dp))
                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape)
                            .background(statusColor.copy(alpha = 0.1f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = taskIcon,
                            contentDescription = null,
                            tint = statusColor,
                            modifier = Modifier.size(40.dp)
                        )
                    }

                    Text(
                        text = task.id,
                        color = Color.Gray,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium
                    )

                    Text(
                        text = task.title,
                        color = Color.Black,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.SemiBold,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        textAlign = TextAlign.Center,
                        lineHeight = 28.sp
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(statusColor.copy(alpha = 0.1f))
                        .padding(vertical = 12.dp)
                ) {
                    Icon(
                        if (task.isCompleted) Icons.Default.CheckCircle else Icons.Default.Schedule,
                        contentDescription = null,
                        tint = statusColor,
                        modifier = Modifier.size(24.dp)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = if (task.isCompleted) "Completed" else "Pending",
                        color = statusColor,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}

@Composable
fun FileUploadDialog(
    onDismiss: () -> Unit,
    onFileSelected: (String) -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Upload Document",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Upload area that mimics the drag & drop design
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clickable { onFileSelected("document.pdf") },
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFF8F9FA)
                    ),
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(
                        2.dp,
                        Color(0xFFE3E8ED)
                    )
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.CloudUpload,
                            contentDescription = null,
                            tint = Color(0xFF6C757D),
                            modifier = Modifier.size(48.dp)
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "Drag&Drop files here",
                            color = Color(0xFF495057),
                            fontSize = 16.sp
                        )

                        Text(
                            text = "or",
                            color = Color(0xFF6C757D),
                            fontSize = 14.sp,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )

                        Button(
                            onClick = { onFileSelected("selected_document.pdf") },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF6C63FF)
                            ),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(
                                text = "Browse",
                                color = Color.White,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Supported formats: PDF, DOC, DOCX, JPG, PNG",
                    color = Color.Gray,
                    fontSize = 12.sp
                )
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text(
                    text = "Cancel",
                    color = Color.Gray
                )
            }
        },
        shape = RoundedCornerShape(16.dp)
    )
}

@Composable
fun TaskDetailDialog(
    task: TaskItem,
    onDismiss: () -> Unit,
    onTaskUpdate: (TaskItem) -> Unit,
    onTaskDelete: (String) -> Unit
) {
    val editedTask by remember { mutableStateOf(task) }
    val description by remember { mutableStateOf("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.") }
    val notes by remember { mutableStateOf("Initial setup completed. Waiting for documentation review.") }
    val dueDate by remember { mutableStateOf("2024-12-31") }
    val progress by remember { mutableStateOf(if (task.isCompleted) 100f else 65f) }

    val attachedFiles = remember {
        listOf(
            "university_application.pdf",
            "transcript.pdf",
            "passport_copy.jpg"
        )
    }

    Dialog(
        onDismissRequest = onDismiss
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.98f)
                .fillMaxHeight(0.85f),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                // Header with minimal horizontal padding
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp, vertical = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = editedTask.title,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                        Text(
                            text = "Task ID: ${editedTask.id}",
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

                // Content with minimal horizontal padding
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Status and Priority Row
                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            // Status
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "Status",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = Color.Black
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Card(
                                    colors = CardDefaults.cardColors(
                                        containerColor = when {
                                            editedTask.isCompleted -> Color(0xFF4CAF50).copy(alpha = 0.1f)
                                            else -> Color(0xFF2196F3).copy(alpha = 0.1f)
                                        }
                                    ),
                                    shape = RoundedCornerShape(8.dp)
                                ) {
                                    Row(
                                        modifier = Modifier.padding(10.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                                    ) {
                                        Icon(
                                            if (editedTask.isCompleted) Icons.Default.CheckCircle else Icons.Default.Schedule,
                                            contentDescription = null,
                                            tint = if (editedTask.isCompleted) Color(0xFF4CAF50) else Color(
                                                0xFF2196F3
                                            ),
                                            modifier = Modifier.size(18.dp)
                                        )
                                        Text(
                                            text = if (editedTask.isCompleted) "Completed" else "In Progress",
                                            color = if (editedTask.isCompleted) Color(0xFF4CAF50) else Color(
                                                0xFF2196F3
                                            ),
                                            fontWeight = FontWeight.Medium
                                        )
                                    }
                                }
                            }

                            // Priority
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "Priority",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = Color.Black
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                if (editedTask.priority > 0) {
                                    Card(
                                        colors = CardDefaults.cardColors(
                                            containerColor = Color(0xFF03A9F4).copy(alpha = 0.1f)
                                        ),
                                        shape = RoundedCornerShape(8.dp)
                                    ) {
                                        Text(
                                            text = "Priority ${editedTask.priority}",
                                            modifier = Modifier.padding(10.dp),
                                            color = Color(0xFF03A9F4),
                                            fontWeight = FontWeight.Medium,
                                            fontSize = 13.sp
                                        )
                                    }
                                } else {
                                    Text(
                                        text = "No priority set",
                                        color = Color.Gray,
                                        fontSize = 14.sp,
                                        modifier = Modifier.padding(vertical = 10.dp)
                                    )
                                }
                            }
                        }
                    }

                    // Progress
                    item {
                        Column {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "Progress",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = Color.Black
                                )
                                Text(
                                    text = "${progress.toInt()}%",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = Color(0xFF2196F3)
                                )
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            LinearProgressIndicator(
                                progress = { progress / 100f },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(8.dp)
                                    .clip(RoundedCornerShape(4.dp)),
                                color = Color(0xFF2196F3),
                                trackColor = Color(0xFF2196F3).copy(alpha = 0.1f)
                            )
                        }
                    }

                    // Due Date
                    item {
                        Column {
                            Text(
                                text = "Due Date",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color.Black
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Card(
                                colors = CardDefaults.cardColors(
                                    containerColor = Color(0xFFF5F5F5)
                                ),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Row(
                                    modifier = Modifier.padding(10.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    Icon(
                                        Icons.Default.CalendarToday,
                                        contentDescription = null,
                                        tint = Color.Gray,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Text(
                                        text = dueDate,
                                        color = Color.Black
                                    )
                                }
                            }
                        }
                    }

                    // Description
                    item {
                        Column {
                            Text(
                                text = "Description",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color.Black
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Card(
                                colors = CardDefaults.cardColors(
                                    containerColor = Color(0xFFF5F5F5)
                                ),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Text(
                                    text = description,
                                    modifier = Modifier.padding(10.dp),
                                    color = Color.Black,
                                    lineHeight = 18.sp,
                                    fontSize = 13.sp
                                )
                            }
                        }
                    }

                    // Attached Files
                    item {
                        Column {
                            Text(
                                text = "Attached Files",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color.Black
                            )
                            Spacer(modifier = Modifier.height(8.dp))

                            attachedFiles.forEach { fileName ->
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 2.dp),
                                    colors = CardDefaults.cardColors(
                                        containerColor = Color(0xFFF5F5F5)
                                    ),
                                    shape = RoundedCornerShape(8.dp)
                                ) {
                                    Row(
                                        modifier = Modifier.padding(10.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {
                                        Icon(
                                            when {
                                                fileName.endsWith(".pdf") -> Icons.Default.PictureAsPdf
                                                fileName.endsWith(".jpg") || fileName.endsWith(".png") -> Icons.Default.Image
                                                else -> Icons.AutoMirrored.Filled.InsertDriveFile
                                            },
                                            contentDescription = null,
                                            tint = Color(0xFF2196F3),
                                            modifier = Modifier.size(18.dp)
                                        )
                                        Text(
                                            text = fileName,
                                            modifier = Modifier.weight(1f),
                                            color = Color.Black,
                                            fontSize = 13.sp
                                        )
                                        IconButton(
                                            onClick = { /* Download file */ },
                                            modifier = Modifier.size(24.dp)
                                        ) {
                                            Icon(
                                                Icons.Default.Download,
                                                contentDescription = "Download",
                                                tint = Color.Gray,
                                                modifier = Modifier.size(16.dp)
                                            )
                                        }
                                    }
                                }
                            }

                            // Add file button
                            OutlinedButton(
                                onClick = { /* Add file */ },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(8.dp),
                                border = BorderStroke(1.dp, Color(0xFF2196F3).copy(alpha = 0.5f))
                            ) {
                                Icon(
                                    Icons.Default.Add,
                                    contentDescription = null,
                                    tint = Color(0xFF2196F3),
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Add File",
                                    color = Color(0xFF2196F3)
                                )
                            }
                        }
                    }

                    // Notes
                    item {
                        Column {
                            Text(
                                text = "Notes",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color.Black
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Card(
                                colors = CardDefaults.cardColors(
                                    containerColor = Color(0xFFF5F5F5)
                                ),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Text(
                                    text = notes,
                                    modifier = Modifier.padding(16.dp),
                                    color = Color.Black,
                                    lineHeight = 20.sp
                                )
                            }
                        }
                    }
                }

                // Action Buttons with minimal padding
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp, vertical = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    OutlinedButton(
                        onClick = { onTaskDelete(task.id) },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.outlinedButtonColors(),
                        border = BorderStroke(1.dp, Color.Red.copy(alpha = 0.5f)),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Icon(
                            Icons.Default.Delete,
                            contentDescription = null,
                            tint = Color.Red,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Delete",
                            color = Color.Red,
                            fontSize = 13.sp
                        )
                    }

                    Button(
                        onClick = {
                            val updatedTask = editedTask.copy(
                                isCompleted = !editedTask.isCompleted
                            )
                            onTaskUpdate(updatedTask)
                        },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (editedTask.isCompleted) Color(0xFFFF9800) else Color(
                                0xFF4CAF50
                            )
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Icon(
                            if (editedTask.isCompleted) Icons.Default.Refresh else Icons.Default.Check,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = if (editedTask.isCompleted) "Mark Incomplete" else "Mark Complete",
                            fontSize = 13.sp
                        )
                    }
                }
            }
        }
    }
}
