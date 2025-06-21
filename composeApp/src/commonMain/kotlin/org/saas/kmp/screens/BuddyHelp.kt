package org.saas.kmp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import kotlinx.datetime.*
import kotlin.random.Random

// Helper function to generate random IDs for KMP compatibility
fun generateRandomId(): String {
    val chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
    return (1..16)
        .map { chars.random() }
        .joinToString("")
}

// Data classes for chat system
data class ChatContact(
    val id: String,
    val name: String,
    val avatar: String,
    val lastMessage: String,
    val timestamp: String,
    val unreadCount: Int = 0,
    val isOnline: Boolean = false,
    val isAI: Boolean = false
)

data class ChatMessage(
    val id: String,
    val senderId: String,
    val message: String,
    val timestamp: Long,
    val isFromMe: Boolean
)

@Composable
fun BuddyHelpScreen(
    rootNavController: NavController,
    paddingValues: PaddingValues,
    onDialogVisibilityChange: (Boolean) -> Unit
) {
    var selectedContact by remember { mutableStateOf<ChatContact?>(null) }
    var showChatRoom by remember { mutableStateOf(false) }

    // Sample chat contacts with AI Buddy at the top
    val chatContacts = remember {
        mutableStateListOf(
            ChatContact(
                id = "ai_buddy",
                name = "🤖 AI Buddy Help",
                avatar = "🤖",
                lastMessage = "Hi! I'm here to help you with anything you need. Ask me about housing, documents, or university life!",
                timestamp = "Now",
                unreadCount = 0,
                isOnline = true,
                isAI = true
            ),
            ChatContact(
                id = "sarah_miller",
                name = "Sarah Miller 🇺🇸",
                avatar = "👩",
                lastMessage = "Thanks for the housing tips! The apartment viewing went great.",
                timestamp = "2 hours ago",
                unreadCount = 2,
                isOnline = true
            ),
            ChatContact(
                id = "john_smith",
                name = "John Smith 🇬🇧",
                avatar = "👨",
                lastMessage = "Do you know any good restaurants near the university?",
                timestamp = "Yesterday",
                unreadCount = 0,
                isOnline = false
            ),
            ChatContact(
                id = "maria_garcia",
                name = "Maria Garcia 🇪🇸",
                avatar = "👩‍🦱",
                lastMessage = "I can help you with the visa application process if needed.",
                timestamp = "2 days ago",
                unreadCount = 1,
                isOnline = true
            ),
            ChatContact(
                id = "alex_chen",
                name = "Alex Chen 🇨🇳",
                avatar = "👨‍💼",
                lastMessage = "The document you shared was really helpful!",
                timestamp = "3 days ago",
                unreadCount = 0,
                isOnline = false
            ),
            ChatContact(
                id = "emma_johansson",
                name = "Emma Johansson 🇸🇪",
                avatar = "👩‍💼",
                lastMessage = "I found a great apartment in the city center if you're interested!",
                timestamp = "1 week ago",
                unreadCount = 0,
                isOnline = true
            ),
            ChatContact(
                id = "pierre_dubois",
                name = "Pierre Dubois 🇫🇷",
                avatar = "👨‍🎓",
                lastMessage = "The university orientation was really helpful, thanks for the tips!",
                timestamp = "1 week ago",
                unreadCount = 0,
                isOnline = false
            ),
            ChatContact(
                id = "yuki_tanaka",
                name = "Yuki Tanaka 🇯🇵",
                avatar = "👩‍🎓",
                lastMessage = "I can share some good study spots around campus.",
                timestamp = "2 weeks ago",
                unreadCount = 0,
                isOnline = true
            )
        )
    }

    if (showChatRoom && selectedContact != null) {
        // Hide navigation bar when in chat room
        LaunchedEffect(Unit) {
            onDialogVisibilityChange(true)
        }
        
        ChatRoomScreen(
            contact = selectedContact!!,
            onBack = {
                showChatRoom = false
                selectedContact = null
                // Show navigation bar when leaving chat room
                onDialogVisibilityChange(false)
            }
        )
    } else {
        // Chat list screen
        ChatListScreen(
            contacts = chatContacts,
            paddingValues = paddingValues,
            onContactClick = { contact ->
                selectedContact = contact
                showChatRoom = true
            }
        )
    }
}

@Composable
fun ChatListScreen(
    contacts: List<ChatContact>,
    paddingValues: PaddingValues,
    onContactClick: (ChatContact) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(paddingValues)
    ) {
        // Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Buddy Help",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.weight(1f)
            )
            
            IconButton(onClick = { /* Search functionality */ }) {
                Icon(
                    Icons.Default.Search,
                    contentDescription = "Search",
                    tint = Color.Gray
                )
            }
        }

        // Chat list
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(0.dp)
        ) {
            items(contacts) { contact ->
                ChatContactItem(
                    contact = contact,
                    onClick = { onContactClick(contact) }
                )
                
                // Divider (except for last item)
                if (contact != contacts.last()) {
                    Divider(
                        modifier = Modifier.padding(start = 72.dp),
                        color = Color.Gray.copy(alpha = 0.2f),
                        thickness = 0.5.dp
                    )
                }
            }
        }
    }
}

@Composable
fun ChatContactItem(
    contact: ChatContact,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Avatar
        Box(
            modifier = Modifier.size(56.dp),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape)
                    .background(
                        if (contact.isAI) Color(0xFF4CAF50).copy(alpha = 0.1f)
                        else Color(0xFF2196F3).copy(alpha = 0.1f)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = contact.avatar,
                    fontSize = 24.sp
                )
            }
            
            // Online indicator
            if (contact.isOnline) {
                Box(
                    modifier = Modifier
                        .size(16.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF4CAF50))
                        .align(Alignment.BottomEnd)
                )
            }
        }

        Spacer(modifier = Modifier.width(12.dp))

        // Chat info
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = contact.name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black,
                    modifier = Modifier.weight(1f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                
                Text(
                    text = contact.timestamp,
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
            
            Spacer(modifier = Modifier.height(4.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = contact.lastMessage,
                    fontSize = 14.sp,
                    color = Color.Gray,
                    modifier = Modifier.weight(1f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                
                if (contact.unreadCount > 0) {
                    Box(
                        modifier = Modifier
                            .size(20.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF4CAF50)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = contact.unreadCount.toString(),
                            fontSize = 12.sp,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ChatRoomScreen(
    contact: ChatContact,
    onBack: () -> Unit
) {
    var messageText by remember { mutableStateOf("") }
    var messages by remember { 
        mutableStateOf(
            if (contact.isAI) {
                listOf(
                    ChatMessage(
                        id = "1",
                        senderId = contact.id,
                        message = "Hi! I'm your AI Buddy Help assistant. I'm here to help you with:",
                        timestamp = Clock.System.now().toEpochMilliseconds() - 300000,
                        isFromMe = false
                    ),
                    ChatMessage(
                        id = "2",
                        senderId = contact.id,
                        message = "🏠 Housing and apartment hunting\n📄 Document assistance\n🎓 University life guidance\n💼 Job search tips\n🗺️ Local area recommendations",
                        timestamp = Clock.System.now().toEpochMilliseconds() - 240000,
                        isFromMe = false
                    ),
                    ChatMessage(
                        id = "3",
                        senderId = contact.id,
                        message = "What would you like help with today?",
                        timestamp = Clock.System.now().toEpochMilliseconds() - 180000,
                        isFromMe = false
                    )
                )
            } else {
                listOf(
                    ChatMessage(
                        id = "1",
                        senderId = contact.id,
                        message = "Hey! How are you settling in?",
                        timestamp = Clock.System.now().toEpochMilliseconds() - 7200000,
                        isFromMe = false
                    ),
                    ChatMessage(
                        id = "2",
                        senderId = "me",
                        message = "Hi! Things are going well. Still looking for an apartment though.",
                        timestamp = Clock.System.now().toEpochMilliseconds() - 7140000,
                        isFromMe = true
                    ),
                    ChatMessage(
                        id = "3",
                        senderId = contact.id,
                        message = contact.lastMessage,
                        timestamp = Clock.System.now().toEpochMilliseconds() - 7200000,
                        isFromMe = false
                    )
                )
            }
        )
    }
    
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    // Full screen chat room
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Chat header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(top = 40.dp) // Status bar padding
                    .padding(horizontal = 8.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBack) {
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.Gray
                    )
                }
                
                Box(
                    modifier = Modifier.size(40.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(
                                if (contact.isAI) Color(0xFF4CAF50).copy(alpha = 0.1f)
                                else Color(0xFF2196F3).copy(alpha = 0.1f)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = contact.avatar,
                            fontSize = 18.sp
                        )
                    }
                    
                    if (contact.isOnline) {
                        Box(
                            modifier = Modifier
                                .size(12.dp)
                                .clip(CircleShape)
                                .background(Color(0xFF4CAF50))
                                .align(Alignment.BottomEnd)
                        )
                    }
                }
                
                Spacer(modifier = Modifier.width(12.dp))
                
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = contact.name,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black
                    )
                    if (contact.isOnline) {
                        Text(
                            text = if (contact.isAI) "Always available" else "Online",
                            fontSize = 12.sp,
                            color = Color(0xFF4CAF50)
                        )
                    }
                }
            }
            
            Divider(color = Color.Gray.copy(alpha = 0.2f))

            // Messages
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(vertical = 16.dp)
            ) {
                items(messages) { message ->
                    ChatMessageBubble(message = message)
                }
            }

            // Message input
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.Bottom
            ) {
                OutlinedTextField(
                    value = messageText,
                    onValueChange = { messageText = it },
                    modifier = Modifier.weight(1f),
                    placeholder = { Text("Type a message...", color = Color.Gray) },
                    shape = RoundedCornerShape(24.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF2196F3),
                        unfocusedBorderColor = Color.Gray.copy(alpha = 0.3f)
                    ),
                    maxLines = 4
                )
                
                Spacer(modifier = Modifier.width(8.dp))
                
                FloatingActionButton(
                    onClick = {
                        if (messageText.isNotBlank()) {
                            val newMessage = ChatMessage(
                                id = generateRandomId(),
                                senderId = "me",
                                message = messageText,
                                timestamp = Clock.System.now().toEpochMilliseconds(),
                                isFromMe = true
                            )
                            messages = messages + newMessage
                            messageText = ""
                            
                            // Auto-scroll to bottom
                            coroutineScope.launch {
                                listState.animateScrollToItem(messages.size - 1)
                            }
                            
                            // Simulate AI response for AI Buddy
                            if (contact.isAI) {
                                coroutineScope.launch {
                                    kotlinx.coroutines.delay(1500)
                                    val aiResponse = generateAIResponse(newMessage.message)
                                    val aiMessage = ChatMessage(
                                        id = generateRandomId(),
                                        senderId = contact.id,
                                        message = aiResponse,
                                        timestamp = Clock.System.now().toEpochMilliseconds(),
                                        isFromMe = false
                                    )
                                    messages = messages + aiMessage
                                    listState.animateScrollToItem(messages.size - 1)
                                }
                            }
                        }
                    },
                    modifier = Modifier.size(48.dp),
                    containerColor = Color(0xFF2196F3),
                    contentColor = Color.White
                ) {
                    Icon(
                        Icons.AutoMirrored.Filled.Send,
                        contentDescription = "Send",
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun ChatMessageBubble(
    message: ChatMessage
) {
    val timestamp = Instant.fromEpochMilliseconds(message.timestamp)
        .toLocalDateTime(TimeZone.currentSystemDefault())
    val timeString = "${timestamp.hour.toString().padStart(2, '0')}:${timestamp.minute.toString().padStart(2, '0')}"
    
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (message.isFromMe) Arrangement.End else Arrangement.Start
    ) {
        Card(
            modifier = Modifier.widthIn(max = 280.dp),
            colors = CardDefaults.cardColors(
                containerColor = if (message.isFromMe) 
                    Color(0xFF2196F3) 
                else 
                    Color(0xFFF5F5F5)
            ),
            shape = RoundedCornerShape(
                topStart = 16.dp,
                topEnd = 16.dp,
                bottomStart = if (message.isFromMe) 16.dp else 4.dp,
                bottomEnd = if (message.isFromMe) 4.dp else 16.dp
            )
        ) {
            Column(
                modifier = Modifier.padding(12.dp)
            ) {
                Text(
                    text = message.message,
                    color = if (message.isFromMe) Color.White else Color.Black,
                    fontSize = 14.sp,
                    lineHeight = 18.sp
                )
                
                Spacer(modifier = Modifier.height(4.dp))
                
                Text(
                    text = timeString,
                    color = if (message.isFromMe) 
                        Color.White.copy(alpha = 0.7f) 
                    else 
                        Color.Gray,
                    fontSize = 11.sp,
                    modifier = Modifier.align(Alignment.End)
                )
            }
        }
    }
}

// Simple AI response generator
fun generateAIResponse(userMessage: String): String {
    val lowerMessage = userMessage.lowercase()
    
    return when {
        lowerMessage.contains("housing") || lowerMessage.contains("apartment") || lowerMessage.contains("house") -> {
            "I can help you with housing! Here are some tips:\n\n🏠 Use filters to narrow down your search\n📍 Check the location and transport connections\n💰 Consider all costs (rent, utilities, deposit)\n📄 Prepare your documents in advance\n\nWould you like specific advice on any of these areas?"
        }
        lowerMessage.contains("document") || lowerMessage.contains("visa") || lowerMessage.contains("passport") -> {
            "Document assistance is one of my specialties! I can help with:\n\n📄 University enrollment documents\n🛂 Visa applications and renewals\n🏦 Bank account requirements\n🏥 Health insurance documentation\n\nWhat specific documents do you need help with?"
        }
        lowerMessage.contains("university") || lowerMessage.contains("study") || lowerMessage.contains("course") -> {
            "University life guidance coming right up! 🎓\n\n📚 Course registration and schedules\n👥 Student organizations and clubs\n📖 Library and study resources\n🎯 Academic support services\n\nWhat aspect of university life interests you most?"
        }
        lowerMessage.contains("job") || lowerMessage.contains("work") || lowerMessage.contains("career") -> {
            "Great question about work opportunities! 💼\n\n🔍 Job search platforms and websites\n📝 CV/Resume optimization\n🤝 Networking opportunities\n⚖️ Work permit requirements\n\nAre you looking for part-time work or career opportunities?"
        }
        lowerMessage.contains("hello") || lowerMessage.contains("hi") || lowerMessage.contains("hey") -> {
            "Hello! 👋 Nice to meet you! I'm here to help make your experience easier. Whether you need help with housing, documents, university life, or anything else, just ask away!"
        }
        lowerMessage.contains("thank") -> {
            "You're very welcome! 😊 I'm always here to help. Don't hesitate to ask if you have more questions!"
        }
        else -> {
            "That's an interesting question! While I try to help with everything, I'm especially good at:\n\n🏠 Housing assistance\n📄 Document guidance\n🎓 University support\n💼 Career advice\n\nCould you tell me more about what specific help you need?"
        }
    }
}
