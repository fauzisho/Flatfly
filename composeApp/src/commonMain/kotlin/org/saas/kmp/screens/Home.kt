package org.saas.kmp.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.datetime.*

// Data classes for dashboard
data class TaskStats(
    val completed: Int,
    val inProgress: Int,
    val notStarted: Int
) {
    val total: Int get() = completed + inProgress + notStarted
    val completedPercentage: Float get() = if (total > 0) completed.toFloat() / total else 0f
    val inProgressPercentage: Float get() = if (total > 0) inProgress.toFloat() / total else 0f
    val notStartedPercentage: Float get() = if (total > 0) notStarted.toFloat() / total else 0f
}

data class TaskHomeItem(
    val id: String,
    val title: String,
    val description: String,
    val status: TaskStatus,
    val priority: Priority,
    val createdDate: String,
    val dueDate: String? = null,
    val companyLogo: String? = null,
    val completedDaysAgo: Int? = null
)

data class AppointmentItem(
    val id: String,
    val title: String,
    val description: String,
    val dateTime: String,
    val location: String,
    val type: AppointmentType,
    val status: AppointmentStatus = AppointmentStatus.SCHEDULED,
    val image: String? = null
)

enum class TaskStatus {
    COMPLETED, IN_PROGRESS, NOT_STARTED
}

enum class Priority {
    LOW, MODERATE, HIGH
}

enum class AppointmentType {
    INTERVIEW, MEETING, VIEWING, CONSULTATION
}

enum class AppointmentStatus {
    SCHEDULED, IN_PROGRESS, COMPLETED, CANCELLED
}

@Composable
fun HomeScreen(
    rootNavController: NavController,
    paddingValues: PaddingValues
) {
    // Sample data
    val taskStats = TaskStats(
        completed = 21,
        inProgress = 3,
        notStarted = 1
    )
    
    val completedTasks = listOf(
        TaskHomeItem(
            id = "1",
            title = "LOA University",
            description = "TH letter of acceptance",
            status = TaskStatus.COMPLETED,
            priority = Priority.HIGH,
            createdDate = "20/06/2023",
            companyLogo = "🎓",
            completedDaysAgo = 2
        ),
        TaskHomeItem(
            id = "2",
            title = "Block Account",
            description = "12,000 Euro block account",
            status = TaskStatus.COMPLETED,
            priority = Priority.HIGH,
            createdDate = "18/06/2023",
            companyLogo = "🏦",
            completedDaysAgo = 2
        )
    )
    
    val todoTasks = listOf(
        TaskHomeItem(
            id = "3",
            title = "Housing Insurance",
            description = "will cover losses due to fire, smoke, theft or vandalism, and certain kinds of water damage",
            status = TaskStatus.NOT_STARTED,
            priority = Priority.MODERATE,
            createdDate = "20/06/2023",
            companyLogo = "🏠"
        ),
        TaskHomeItem(
            id = "4",
            title = "Schufa Certificate",
            description = "Schufa to your future landlord. The report ranks your financial stability and lets the landlord know if you're a reliable payer or not.",
            status = TaskStatus.IN_PROGRESS,
            priority = Priority.MODERATE,
            createdDate = "20/06/2023",
            companyLogo = "📋"
        )
    )
    
    val appointments = listOf(
        AppointmentItem(
            id = "1",
            title = "Online Interview WG-Student",
            description = "DONAU SIDE: Modernes Studio Apartment mit Fitnessstudio & Co-Working",
            dateTime = "19/06/2023",
            location = "Zoom link: ...",
            type = AppointmentType.INTERVIEW,
            status = AppointmentStatus.IN_PROGRESS,
            image = "🏢"
        )
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F9FA))
            .padding(paddingValues),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(16.dp)
    ) {
        // Welcome Header
        item {
            WelcomeHeader()
        }

        // Task Status Overview
        item {
            TaskStatusSection(taskStats = taskStats)
        }

        // Quick Actions
        item {
            QuickActionsSection()
        }

        // Completed Tasks
        item {
            CompletedTasksSection(
                tasks = completedTasks,
                onSeeAllClick = { /* Navigate to documents */ }
            )
        }

        // To-Do List
        item {
            TodoSection(
                tasks = todoTasks,
                onTaskClick = { /* Handle task click */ },
                onSeeAllClick = { /* Navigate to documents */ }
            )
        }

        // Appointments
        item {
            AppointmentsSection(
                appointments = appointments,
                onAppointmentClick = { /* Handle appointment click */ },
                onSeeAllClick = { /* Navigate to calendar */ }
            )
        }

        // Bottom spacing
        item {
            Spacer(modifier = Modifier.height(100.dp))
        }
    }
}
@Composable
fun WelcomeHeader() {
    val currentTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    val hour = currentTime.hour
    val greeting = when (hour) {
        in 5..11 -> "Good Morning"
        in 12..16 -> "Good Afternoon"
        in 17..20 -> "Good Evening"
        else -> "Good Night"
    }

    val dayOfWeek = currentTime.dayOfWeek.name.lowercase().replaceFirstChar { it.uppercase() }.take(3)
    val month = currentTime.month.name.lowercase().replaceFirstChar { it.uppercase() }.take(3)
    val dayOfMonth = currentTime.dayOfMonth
    val currentDate = "$dayOfWeek, $month $dayOfMonth"

    Column {
        Text(
            text = greeting,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Text(
            text = currentDate,
            fontSize = 16.sp,
            color = Color.Gray,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}
@Composable
fun TaskStatusSection(taskStats: TaskStats) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Task Status",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Icon(
                    Icons.Default.Assignment,
                    contentDescription = null,
                    tint = Color.Gray,
                    modifier = Modifier.size(20.dp)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                // Completed
                TaskStatCircle(
                    percentage = taskStats.completedPercentage,
                    count = taskStats.completed,
                    total = taskStats.total,
                    label = "Completed",
                    color = Color(0xFF4CAF50)
                )

                // In Progress
                TaskStatCircle(
                    percentage = taskStats.inProgressPercentage,
                    count = taskStats.inProgress,
                    total = taskStats.total,
                    label = "In Progress",
                    color = Color(0xFF2196F3)
                )

                // Not Started
                TaskStatCircle(
                    percentage = taskStats.notStartedPercentage,
                    count = taskStats.notStarted,
                    total = taskStats.total,
                    label = "Not Started",
                    color = Color(0xFFFF5722)
                )
            }
        }
    }
}

@Composable
fun TaskStatCircle(
    percentage: Float,
    count: Int,
    total: Int,
    label: String,
    color: Color
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier.size(80.dp),
            contentAlignment = Alignment.Center
        ) {
            Canvas(
                modifier = Modifier.size(80.dp)
            ) {
                val strokeWidth = 8.dp.toPx()
                
                // Background circle
                drawCircle(
                    color = Color.Gray.copy(alpha = 0.1f),
                    radius = size.minDimension / 2 - strokeWidth / 2,
                    style = Stroke(strokeWidth)
                )
                
                // Progress arc
                drawArc(
                    color = color,
                    startAngle = -90f,
                    sweepAngle = 360f * percentage,
                    useCenter = false,
                    style = Stroke(strokeWidth, cap = StrokeCap.Round)
                )
            }
            
            Text(
                text = "${(percentage * 100).toInt()}%",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .clip(CircleShape)
                    .background(color)
            )
            Text(
                text = label,
                fontSize = 12.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun QuickActionsSection() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Text(
                text = "Quick Actions",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(16.dp))

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(
                    listOf(
                        Triple("Add Task", Icons.Default.Add, Color(0xFF2196F3)),
                        Triple("Find Housing", Icons.Default.Home, Color(0xFF4CAF50)),
                        Triple("Schedule", Icons.Default.Schedule, Color(0xFFFF9800)),
                        Triple("Documents", Icons.Default.Description, Color(0xFF9C27B0))
                    )
                ) { (title, icon, color) ->
                    QuickActionItem(
                        title = title,
                        icon = icon,
                        color = color,
                        onClick = { /* Handle quick action */ }
                    )
                }
            }
        }
    }
}

@Composable
fun QuickActionItem(
    title: String,
    icon: ImageVector,
    color: Color,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable { onClick() }
    ) {
        Box(
            modifier = Modifier
                .size(56.dp)
                .clip(CircleShape)
                .background(color.copy(alpha = 0.1f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                icon,
                contentDescription = null,
                tint = color,
                modifier = Modifier.size(24.dp)
            )
        }
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Text(
            text = title,
            fontSize = 12.sp,
            color = Color.Gray,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun CompletedTasksSection(
    tasks: List<TaskHomeItem>,
    onSeeAllClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.CheckCircle,
                        contentDescription = null,
                        tint = Color(0xFF4CAF50),
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Completed Tasks",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }
                
                TextButton(onClick = onSeeAllClick) {
                    Text(
                        text = "See All",
                        color = Color(0xFF2196F3),
                        fontSize = 14.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            tasks.forEach { task ->
                CompletedTaskItem(task = task)
                if (task != tasks.last()) {
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }
    }
}

@Composable
fun CompletedTaskItem(task: TaskHomeItem) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Status icon
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(Color(0xFF4CAF50).copy(alpha = 0.1f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                Icons.Default.Check,
                contentDescription = null,
                tint = Color(0xFF4CAF50),
                modifier = Modifier.size(20.dp)
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = task.title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black
            )
            Text(
                text = task.description,
                fontSize = 14.sp,
                color = Color.Gray,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = "Status: Completed",
                fontSize = 12.sp,
                color = Color(0xFF4CAF50),
                modifier = Modifier.padding(top = 2.dp)
            )
            task.completedDaysAgo?.let {
                Text(
                    text = "Completed $it days ago.",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
        }

        // Company logo
        task.companyLogo?.let { logo ->
            Text(
                text = logo,
                fontSize = 24.sp,
                modifier = Modifier.padding(start = 8.dp)
            )
        }
    }
}

@Composable
fun TodoSection(
    tasks: List<TaskHomeItem>,
    onTaskClick: (TaskHomeItem) -> Unit,
    onSeeAllClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.Schedule,
                        contentDescription = null,
                        tint = Color(0xFF2196F3),
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "To-Do",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "20 June",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "• Today",
                        fontSize = 14.sp,
                        color = Color(0xFF2196F3)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            tasks.forEach { task ->
                TodoTaskItem(
                    task = task,
                    onClick = { onTaskClick(task) }
                )
                if (task != tasks.last()) {
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }
    }
}

@Composable
fun TodoTaskItem(
    task: TaskHomeItem,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF8F9FA)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.Top
        ) {
            // Status indicator
            Box(
                modifier = Modifier
                    .size(12.dp)
                    .clip(CircleShape)
                    .background(
                        when (task.status) {
                            TaskStatus.NOT_STARTED -> Color(0xFFFF5722)
                            TaskStatus.IN_PROGRESS -> Color(0xFF2196F3)
                            TaskStatus.COMPLETED -> Color(0xFF4CAF50)
                        }
                    )
                    .padding(top = 4.dp)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = task.title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                )
                Text(
                    text = task.description,
                    fontSize = 14.sp,
                    color = Color.Gray,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(top = 4.dp, bottom = 8.dp)
                )
                
                // Status and Priority info
                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Text(
                            text = "Priority: ${task.priority.name.lowercase().replaceFirstChar { it.uppercase() }}",
                            fontSize = 12.sp,
                            color = when (task.priority) {
                                Priority.HIGH -> Color(0xFFFF5722)
                                Priority.MODERATE -> Color(0xFF2196F3)
                                Priority.LOW -> Color(0xFF4CAF50)
                            }
                        )
                        Text(
                            text = "Status: ${if (task.status == TaskStatus.NOT_STARTED) "Not Started" else "In Progress"}",
                            fontSize = 12.sp,
                            color = when (task.status) {
                                TaskStatus.NOT_STARTED -> Color(0xFFFF5722)
                                TaskStatus.IN_PROGRESS -> Color(0xFF2196F3)
                                TaskStatus.COMPLETED -> Color(0xFF4CAF50)
                            }
                        )
                    }
                    Text(
                        text = "Created on: ${task.createdDate}",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
            }

            // Company logo
            task.companyLogo?.let { logo ->
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.White),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = logo,
                        fontSize = 20.sp
                    )
                }
            }
        }
    }
}

@Composable
fun AppointmentsSection(
    appointments: List<AppointmentItem>,
    onAppointmentClick: (AppointmentItem) -> Unit,
    onSeeAllClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.Event,
                        contentDescription = null,
                        tint = Color(0xFF9C27B0),
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Appointments",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }
                
                TextButton(onClick = onSeeAllClick) {
                    Text(
                        text = "See All",
                        color = Color(0xFF2196F3),
                        fontSize = 14.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            appointments.forEach { appointment ->
                AppointmentItem(
                    appointment = appointment,
                    onClick = { onAppointmentClick(appointment) }
                )
            }
        }
    }
}

@Composable
fun AppointmentItem(
    appointment: AppointmentItem,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF8F9FA)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.Top
        ) {
            // Status indicator
            Box(
                modifier = Modifier
                    .size(12.dp)
                    .clip(CircleShape)
                    .background(
                        when (appointment.status) {
                            AppointmentStatus.SCHEDULED -> Color(0xFF2196F3)
                            AppointmentStatus.IN_PROGRESS -> Color(0xFFFF9800)
                            AppointmentStatus.COMPLETED -> Color(0xFF4CAF50)
                            AppointmentStatus.CANCELLED -> Color(0xFF9E9E9E)
                        }
                    )
                    .padding(top = 4.dp)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = appointment.title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                )
                Text(
                    text = appointment.description,
                    fontSize = 14.sp,
                    color = Color.Gray,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(top = 4.dp)
                )
                Text(
                    text = appointment.location,
                    fontSize = 14.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(top = 2.dp, bottom = 8.dp)
                )
                
                // Status and Priority info
                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Text(
                            text = "Priority: Moderate",
                            fontSize = 12.sp,
                            color = Color(0xFF2196F3)
                        )
                        Text(
                            text = "Status: ${appointment.status.name.lowercase().replaceFirstChar { it.uppercase() }.replace("_", " ")}",
                            fontSize = 12.sp,
                            color = when (appointment.status) {
                                AppointmentStatus.SCHEDULED -> Color(0xFF2196F3)
                                AppointmentStatus.IN_PROGRESS -> Color(0xFFFF9800)
                                AppointmentStatus.COMPLETED -> Color(0xFF4CAF50)
                                AppointmentStatus.CANCELLED -> Color(0xFF9E9E9E)
                            }
                        )
                    }
                    Text(
                        text = "Created on: ${appointment.dateTime}",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
            }

            // Appointment image/icon
            appointment.image?.let { image ->
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.Gray.copy(alpha = 0.1f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = image,
                        fontSize = 24.sp
                    )
                }
            }
        }
    }
}
