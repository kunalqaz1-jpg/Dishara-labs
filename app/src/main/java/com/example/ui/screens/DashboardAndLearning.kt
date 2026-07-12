package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.ui.theme.*
import com.example.ui.viewmodel.AppScreen
import com.example.ui.viewmodel.DisharaViewModel
import com.example.ui.viewmodel.UserState
import kotlinx.coroutines.launch
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import com.example.data.*

data class BatchInfo(
    val id: String,
    val nameEn: String,
    val nameHi: String,
    val gradeEn: String,
    val gradeHi: String,
    val modulesCount: Int,
    val durationMonths: Int,
    val descriptionEn: String,
    val descriptionHi: String,
    val topics: List<String>
)

val AvailableBatches = listOf(
    BatchInfo(
        id = "udaan",
        nameEn = "Udaan Batch (Class 9)",
        nameHi = "उड़ान बैच (कक्षा 9)",
        gradeEn = "Grade 9",
        gradeHi = "कक्षा 9",
        modulesCount = 2,
        durationMonths = 12,
        descriptionEn = "Explore computer fundamentals, design thinking, binary logic, game logic, and loop repetition. No coding required!",
        descriptionHi = "कंप्यूटर के बुनियादी सिद्धांतों, डिजाइन थिंकिंग, बाइनरी लॉजिक, गेम लॉजिक और लूप्स को समझें। कोडिंग की आवश्यकता नहीं!",
        topics = listOf("Computer Fundamentals", "Design Thinking", "Binary & Bits", "File Management", "Scratch/Visual Game Logic", "Loops & Sequencing")
    ),
    BatchInfo(
        id = "batch_10",
        nameEn = "Udaan Fast-Track (Class 10)",
        nameHi = "उड़ान फास्ट-ट्रैक (कक्षा 10)",
        gradeEn = "Grade 10",
        gradeHi = "कक्षा 10",
        modulesCount = 4,
        durationMonths = 12,
        descriptionEn = "Fast-track design & fundamentals, then master HTML5, CSS3, Flexbox layouts, JavaScript interactions, and programming logic.",
        descriptionHi = "फास्ट-ट्रैक डिजाइन और बुनियादी बातें, फिर HTML5, CSS3, फ्लेक्सबॉक्स लेआउट, जावास्क्रिप्ट इंटरैक्शन और प्रोग्रामिंग लॉजिक सीखें।",
        topics = listOf("Design & UI Wireframes", "HTML5 & CSS3 Layouts", "JavaScript Interactions", "Variables & Calculators", "Arrays & Functions", "APIs & Web Apps")
    ),
    BatchInfo(
        id = "pragati",
        nameEn = "Pragati Batch (Class 11)",
        nameHi = "प्रगति बैच (कक्षा 11)",
        gradeEn = "Grade 11",
        gradeHi = "कक्षा 11",
        modulesCount = 5,
        durationMonths = 12,
        descriptionEn = "Fast-track foundations and web building, then dive into databases, auth, CRUD actions, and full-stack integration.",
        descriptionHi = "फास्ट-ट्रैक बुनियादी बातें और वेब बिल्डिंग, फिर डेटाबेस, ऑथेंटिकेशन, CRUD एक्शन्स और फुल-स्टैक सीखें।",
        topics = listOf("Web Design Prototyping", "Flexbox & CSS Grids", "Programming Logic", "Database CRUD Operations", "Authentication Security", "Full-Stack Deployment")
    ),
    BatchInfo(
        id = "batch_12",
        nameEn = "Pragati Advanced (Class 12)",
        nameHi = "प्रगति उन्नत (कक्षा 12)",
        gradeEn = "Grade 12",
        gradeHi = "कक्षा 12",
        modulesCount = 6,
        durationMonths = 12,
        descriptionEn = "Rapid foundations and web building, full-stack database CRUD, plus Advanced DLCs: AI Copilot, Git/GitHub, React, Python, and Cybersecurity.",
        descriptionHi = "रैपिड बुनियादी बातें और वेब बिल्डिंग, फुल-स्टैक डेटाबेस CRUD, और उन्नत DLCs: AI कोपायलट, Git, React, Python और साइबर सुरक्षा।",
        topics = listOf("Full-Stack Web Apps", "AI Assisted Debugging", "Git & GitHub Workflows", "React Framework", "Python & Automation", "Cybersecurity Basics")
    ),
    BatchInfo(
        id = "lakshya",
        nameEn = "Lakshya Career Batch",
        nameHi = "लक्ष्य करियर बैच",
        gradeEn = "College & Career",
        gradeHi = "कॉलेज और करियर",
        modulesCount = 2,
        durationMonths = 12,
        descriptionEn = "Advanced programming, data structures, professional soft skills, resume building, and interview preparation.",
        descriptionHi = "उन्नत प्रोग्रामिंग, डेटा संरचनाएं, व्यावसायिक सॉफ्ट स्किल्स, बायोडाटा निर्माण और साक्षात्कार की तैयारी।",
        topics = listOf("Advanced Coding (Java/Kotlin)", "Software Engineering Concepts", "Data Science Foundations", "Resume Building", "Interview Prep", "Professional Placement")
    )
)

// Common Bottom Navigation Bar matching Material 3 with active pills and labels
@Composable
fun BottomNavBar(selectedScreen: AppScreen, onTabSelected: (AppScreen) -> Unit) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(16.dp, RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)),
        color = SurfaceContainerLowest,
        shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
    ) {
        Row(
            modifier = Modifier
                .navigationBarsPadding()
                .fillMaxWidth()
                .padding(vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            NavBarItem(
                title = "Home",
                icon = Icons.Default.Home,
                selected = selectedScreen == AppScreen.HOME,
                onClick = { onTabSelected(AppScreen.HOME) }
            )
            NavBarItem(
                title = "Courses",
                icon = Icons.Default.School,
                selected = selectedScreen == AppScreen.LEARN_COURSES || selectedScreen == AppScreen.MODULE_DETAIL,
                onClick = { onTabSelected(AppScreen.LEARN_COURSES) }
            )
            NavBarItem(
                title = "Study Plan",
                icon = Icons.Default.Book,
                selected = selectedScreen == AppScreen.STUDY_PLAN,
                onClick = { onTabSelected(AppScreen.STUDY_PLAN) }
            )
            NavBarItem(
                title = "Profile",
                icon = Icons.Default.Person,
                selected = selectedScreen == AppScreen.PROFILE,
                onClick = { onTabSelected(AppScreen.PROFILE) }
            )
        }
    }
}

@Composable
fun RowScope.NavBarItem(title: String, icon: ImageVector, selected: Boolean, onClick: () -> Unit) {
    val scale = animateFloatAsState(targetValue = if (selected) 1.05f else 0.95f, label = "tabScale")
    val color = if (selected) OnSecondaryContainer else OnSurfaceVariant

    Box(
        modifier = Modifier
            .weight(1f)
            .clickable { onClick() }
            .padding(vertical = 4.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.graphicsLayer {
                scaleX = scale.value
                scaleY = scale.value
            }
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .background(if (selected) SecondaryContainer else Color.Transparent)
                    .padding(horizontal = 16.dp, vertical = 6.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, contentDescription = null, tint = color, modifier = Modifier.size(22.dp))
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.labelMedium,
                fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium,
                fontSize = 10.sp,
                color = color,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

// Common Top App Bar
@Composable
fun TopAppBar(
    title: String,
    onMenuClick: () -> Unit = {},
    hasUnread: Boolean = false,
    onNotificationClick: () -> Unit = {}
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = SurfaceContainerLowest,
        shadowElevation = 2.dp
    ) {
        Row(
            modifier = Modifier
                .statusBarsPadding()
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onMenuClick, modifier = Modifier.size(40.dp)) {
                    Icon(Icons.Default.Menu, contentDescription = "Menu Drawer", tint = Primary)
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = Primary
                )
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onNotificationClick, modifier = Modifier.size(40.dp)) {
                    Box {
                        Icon(Icons.Outlined.Notifications, contentDescription = "Notifications", tint = Primary)
                        if (hasUnread) {
                            Box(
                                modifier = Modifier
                                    .size(10.dp)
                                    .clip(CircleShape)
                                    .background(Error)
                                    .align(Alignment.TopEnd)
                                    .border(1.5.dp, Color.White, CircleShape)
                            )
                        }
                    }
                }
            }
        }
    }
}

// Custom High-Contrast Progress Circle indicator matching M3
@Composable
fun ProgressRing(progress: Float, modifier: Modifier = Modifier) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawCircle(
                color = SurfaceContainerHighest,
                style = Stroke(width = 8.dp.toPx())
            )
            drawArc(
                color = Secondary,
                startAngle = -90f,
                sweepAngle = 360f * progress,
                useCenter = false,
                style = Stroke(width = 8.dp.toPx())
            )
        }
        Text(
            text = "${(progress * 100).toInt()}%",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = Primary
        )
    }
}

@Composable
fun NotificationsDialog(viewModel: DisharaViewModel) {
    val showDialog by viewModel.showNotificationsDialog.collectAsState()
    if (!showDialog) return

    val notifications by viewModel.notifications.collectAsState()
    var isSimulationMode by remember { mutableStateOf(false) }
    var simulationTitle by remember { mutableStateOf("") }
    var simulationContent by remember { mutableStateOf("") }
    var simulationImportant by remember { mutableStateOf(false) }

    androidx.compose.ui.window.Dialog(
        onDismissRequest = { viewModel.setShowNotificationsDialog(false) }
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(24.dp))
                .background(SurfaceContainerLowest),
            shape = RoundedCornerShape(24.dp),
            tonalElevation = 8.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = if (isSimulationMode) "Simulate Teacher Alert" else "Teacher Alerts",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = Primary
                    )
                    IconButton(onClick = { isSimulationMode = !isSimulationMode }) {
                        Icon(
                            imageVector = if (isSimulationMode) Icons.Default.Notifications else Icons.Default.Settings,
                            contentDescription = "Toggle Mode",
                            tint = Secondary
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))

                if (isSimulationMode) {
                    OutlinedTextField(
                        value = simulationTitle,
                        onValueChange = { simulationTitle = it },
                        label = { Text("Title") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    OutlinedTextField(
                        value = simulationContent,
                        onValueChange = { simulationContent = it },
                        label = { Text("Message Content") },
                        modifier = Modifier.fillMaxWidth(),
                        minLines = 2
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = simulationImportant,
                            onCheckedChange = { simulationImportant = it }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Mark as Important (High Priority)", style = MaterialTheme.typography.bodyMedium)
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        TextButton(onClick = { isSimulationMode = false }) {
                            Text("Back", color = OnSurfaceVariant)
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Button(
                            onClick = {
                                if (simulationTitle.isNotEmpty() && simulationContent.isNotEmpty()) {
                                    viewModel.sendTeacherNotification(
                                        title = simulationTitle,
                                        content = simulationContent,
                                        isImportant = simulationImportant
                                    )
                                    simulationTitle = ""
                                    simulationContent = ""
                                    simulationImportant = false
                                    isSimulationMode = false
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Primary)
                        ) {
                            Text("Send Alert", color = Color.White)
                        }
                    }
                } else {
                    if (notifications.isEmpty()) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 32.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Icon(Icons.Outlined.Notifications, contentDescription = null, modifier = Modifier.size(48.dp), tint = OnSurfaceVariant.copy(alpha = 0.5f))
                                Spacer(modifier = Modifier.height(8.dp))
                                Text("No notifications yet", color = OnSurfaceVariant)
                            }
                        }
                    } else {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(max = 350.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            items(notifications) { item ->
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable { viewModel.markNotificationAsRead(item.id) },
                                    colors = CardDefaults.cardColors(
                                        containerColor = if (item.isImportant) Error.copy(alpha = 0.05f) else SurfaceContainerLow
                                    ),
                                    border = BorderStroke(
                                        width = if (!item.isRead) 1.5.dp else 1.dp,
                                        color = if (item.isImportant) Error.copy(alpha = 0.3f) else if (!item.isRead) Secondary.copy(alpha = 0.5f) else Color.Transparent
                                    ),
                                    shape = RoundedCornerShape(12.dp)
                                ) {
                                    Column(modifier = Modifier.padding(16.dp)) {
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Row(verticalAlignment = Alignment.CenterVertically) {
                                                if (item.isImportant) {
                                                    Icon(Icons.Default.Warning, contentDescription = "Important", tint = Error, modifier = Modifier.size(16.dp))
                                                    Spacer(modifier = Modifier.width(6.dp))
                                                }
                                                Text(
                                                    text = item.title,
                                                    style = MaterialTheme.typography.titleSmall,
                                                    fontWeight = FontWeight.Bold,
                                                    color = if (item.isImportant) Error else Primary
                                                )
                                            }
                                            if (!item.isRead) {
                                                Box(
                                                    modifier = Modifier
                                                        .size(8.dp)
                                                        .clip(CircleShape)
                                                        .background(Secondary)
                                                )
                                            }
                                        }
                                        Spacer(modifier = Modifier.height(6.dp))
                                        Text(text = item.content, style = MaterialTheme.typography.bodyMedium, color = OnSurface)
                                        Spacer(modifier = Modifier.height(8.dp))
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            Text("By ${item.senderName}", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, color = OnSurfaceVariant)
                                            Text(text = item.timestamp, style = MaterialTheme.typography.labelSmall, color = OnSurfaceVariant)
                                        }
                                    }
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = { viewModel.setShowNotificationsDialog(false) },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = Primary)
                    ) {
                        Text("Close", color = Color.White)
                    }
                }
            }
        }
    }
}

// 1. Dashboard screen layout
@Composable
fun HomeScreen(viewModel: DisharaViewModel) {
    val tempState by viewModel.tempProfile.collectAsState()
    val dbProgress by viewModel.dbProgressList.collectAsState()

    // Calculate completed lessons dynamically based on actual batch curriculum
    val activeBatchId = tempState.enrolledBatchId.ifEmpty { "udaan" }
    val batchModules = com.example.data.CurriculumProvider.getModulesForBatch(activeBatchId)
    val allLessons = batchModules.flatMap { it.lessons }
    val totalLessons = allLessons.size.coerceAtLeast(1)
    val completedCount = allLessons.count { lesson ->
        dbProgress.any { it.lessonId == lesson.id && it.completed }
    }
    val progressFraction = completedCount.toFloat() / totalLessons.toFloat()

    val notifications by viewModel.notifications.collectAsState()
    val hasUnread = notifications.any { !it.isRead }

    Scaffold(
        topBar = {
            TopAppBar(
                title = "Dishara",
                onMenuClick = { viewModel.setDrawerOpen(true) },
                hasUnread = hasUnread,
                onNotificationClick = { viewModel.setShowNotificationsDialog(true) }
            )
        },
        bottomBar = { BottomNavBar(selectedScreen = AppScreen.HOME, onTabSelected = { viewModel.navigateTo(it) }) }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Background)
                .padding(innerPadding)
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(8.dp))
                // Welcome Profile Row
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .border(2.dp, Color.White, CircleShape)
                            .shadow(2.dp, CircleShape)
                    ) {
                        AsyncImage(
                            model = AvatarUrls[tempState.avatarIndex],
                            contentDescription = "Avatar index logo",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = "Hello, ${tempState.name}! 👋",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = Primary
                        )
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.LocalFireDepartment, contentDescription = null, tint = Color(0xFFFF9800), modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "${tempState.currentStreak} days streak",
                                style = MaterialTheme.typography.bodySmall,
                                fontWeight = FontWeight.Bold,
                                color = OnSurfaceVariant
                            )
                        }
                    }
                }
            }

            // Mera Sapna Banner Card (Warm golden foundation)
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(4.dp, RoundedCornerShape(16.dp)),
                    colors = CardDefaults.cardColors(containerColor = WarmBannerBg),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        Icon(
                            imageVector = Icons.Default.Stars,
                            contentDescription = null,
                            tint = AmberStar.copy(alpha = 0.08f),
                            modifier = Modifier
                                .size(120.dp)
                                .align(Alignment.TopEnd)
                                .offset(x = 16.dp, y = (-16).dp)
                        )
                        Column(modifier = Modifier.padding(20.dp)) {
                            Text(
                                "MY DREAM / MERA SAPNA",
                                color = OnSurfaceVariant,
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.sp
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Star, contentDescription = null, tint = AmberStar, modifier = Modifier.size(24.dp))
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = tempState.dream.ifEmpty { "Software Engineer" },
                                    style = MaterialTheme.typography.headlineMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = Primary
                                )
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                "Every day is a step closer to your dream",
                                style = MaterialTheme.typography.bodySmall,
                                fontStyle = FontStyle.Italic,
                                color = OnSurfaceVariant
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(AmberStar))
                                Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(AmberStar.copy(alpha = 0.4f)))
                                Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(AmberStar.copy(alpha = 0.4f)))
                            }
                        }
                    }
                }
            }

            // Quick Actions Scroll
            item {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    item {
                        ActionPill(title = "Today's Lesson", icon = Icons.Default.MenuBook) {
                            viewModel.navigateTo(AppScreen.LESSON_PLAYER)
                        }
                    }
                    item {
                        ActionPill(title = "Career Explorer", icon = Icons.Default.Explore) {}
                    }
                    item {
                        ActionPill(title = "Dishara Mitra", icon = Icons.Default.SmartToy) {
                            viewModel.navigateTo(AppScreen.LESSON_PLAYER)
                        }
                    }
                }
            }

            // Today's Goal Card (with interactive progress ring and Start Button)
            item {
                val passedQuizzes by viewModel.passedQuizzes.collectAsState()
                
                // Find a completed lesson with a pending quiz
                val lessonWithPendingQuiz = allLessons.find { lesson ->
                    val isLessonCompleted = dbProgress.any { it.lessonId == lesson.id && it.completed }
                    val isQuizPassed = passedQuizzes.contains(lesson.id)
                    isLessonCompleted && !isQuizPassed
                }
                
                val hasPendingQuiz = lessonWithPendingQuiz != null
                val activeLesson = lessonWithPendingQuiz ?: allLessons.find { lesson ->
                    !dbProgress.any { it.lessonId == lesson.id && it.completed }
                } ?: allLessons.firstOrNull()

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.5.dp, Primary.copy(alpha = 0.15f), RoundedCornerShape(20.dp))
                        .shadow(6.dp, RoundedCornerShape(20.dp)),
                    colors = CardDefaults.cardColors(containerColor = SurfaceContainerLowest),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(if (hasPendingQuiz) Error.copy(alpha = 0.15f) else SecondaryContainer)
                                    .padding(horizontal = 10.dp, vertical = 4.dp)
                            ) {
                                Text(
                                    text = if (hasPendingQuiz) "PENDING QUIZ" else "TODAY'S LECTURE",
                                    style = MaterialTheme.typography.labelMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = if (hasPendingQuiz) Error else OnSecondaryContainer
                                )
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            if (activeLesson != null) {
                                val module = batchModules.find { m -> m.lessons.any { it.id == activeLesson.id } }
                                val moduleTitle = module?.titleEn ?: "Module"
                                Text(
                                    text = activeLesson.titleEn,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = Primary
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = moduleTitle,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = OnSurfaceVariant
                                )
                            } else {
                                Text(
                                    text = "All lessons complete!",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = Primary
                                )
                            }
                            Spacer(modifier = Modifier.height(16.dp))
                            Button(
                                onClick = {
                                    activeLesson?.let { lesson ->
                                        if (hasPendingQuiz) {
                                            viewModel.startLessonMiniQuiz(lesson.id)
                                        } else {
                                            val parentModule = batchModules.find { m -> m.lessons.any { it.id == lesson.id } }
                                            parentModule?.let { viewModel.selectModule(it.id) }
                                            viewModel.selectLesson(lesson.id)
                                            viewModel.navigateTo(AppScreen.LESSON_PLAYER)
                                        }
                                    }
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                                contentPadding = PaddingValues(),
                                modifier = Modifier.height(44.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(SunriseGradient, RoundedCornerShape(22.dp))
                                        .padding(horizontal = 24.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = if (hasPendingQuiz) "Complete Quizzes" else "Start Learning",
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                        ProgressRing(progress = progressFraction, modifier = Modifier.size(96.dp))
                    }
                }
            }

            // Active Batch Card Progress Bento
            item {
                if (tempState.enrolledBatchId.isEmpty()) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(1.5.dp, Primary.copy(alpha = 0.15f), RoundedCornerShape(16.dp))
                            .shadow(4.dp, RoundedCornerShape(16.dp)),
                        colors = CardDefaults.cardColors(containerColor = SurfaceContainerLowest),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(20.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Icon(
                                    imageVector = Icons.Default.School,
                                    contentDescription = null,
                                    tint = Secondary,
                                    modifier = Modifier.size(32.dp)
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                                Text(
                                    text = "Enroll in a Batch",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = Primary
                                )
                            }
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = "Choose a learning batch to unlock personalized study plans, dynamic milestones, and track your coding journey.",
                                style = MaterialTheme.typography.bodyMedium,
                                color = OnSurfaceVariant
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Button(
                                onClick = { viewModel.navigateTo(AppScreen.BATCH_DETAILS) },
                                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                                contentPadding = PaddingValues(),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(48.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(SunriseGradient, RoundedCornerShape(24.dp)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text("Explore Batches", color = Color.White, fontWeight = FontWeight.Bold)
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
                                    }
                                }
                            }
                        }
                    }
                } else {
                    val currentBatch = AvailableBatches.find { it.id == tempState.enrolledBatchId } ?: AvailableBatches[0]
                    val completedFrac = if (currentBatch.modulesCount > 0) completedCount.toFloat() / currentBatch.modulesCount.toFloat() else 0f
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(1.dp, OutlineVariant.copy(alpha = 0.4f), RoundedCornerShape(16.dp))
                            .shadow(2.dp, RoundedCornerShape(16.dp)),
                        colors = CardDefaults.cardColors(containerColor = SurfaceContainerLowest),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Column(modifier = Modifier.padding(20.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Text("ACTIVE BATCH", style = MaterialTheme.typography.labelMedium, color = OnSurfaceVariant, fontWeight = FontWeight.Bold)
                                    Text("${currentBatch.nameEn} (${currentBatch.gradeEn})", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = Primary)
                                }
                                Icon(Icons.Default.Groups, contentDescription = null, tint = Primary, modifier = Modifier.size(36.dp))
                            }
                            Spacer(modifier = Modifier.height(16.dp))
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(8.dp)
                                    .clip(CircleShape)
                                    .background(SurfaceContainerHighest)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxHeight()
                                        .fillMaxWidth(completedFrac.coerceIn(0.05f, 1f))
                                        .clip(CircleShape)
                                        .background(SunriseGradient)
                                    )
                            }
                            Spacer(modifier = Modifier.height(12.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text("$completedCount/${currentBatch.modulesCount} Modules Done", style = MaterialTheme.typography.bodySmall, color = OnSurfaceVariant)
                                TextButton(onClick = { viewModel.navigateTo(AppScreen.LEARN_COURSES) }) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text("Continue", color = Secondary, fontWeight = FontWeight.Bold)
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, tint = Secondary, modifier = Modifier.size(16.dp))
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // Bento Grid Row: Achievements snapshot (spanning full width since Community card is removed)
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(3.dp, RoundedCornerShape(16.dp)),
                    colors = CardDefaults.cardColors(containerColor = Primary),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(Icons.Default.MilitaryTech, contentDescription = null, tint = SecondaryContainer, modifier = Modifier.size(28.dp))
                            Text("+5 XP per Quiz Question / Lecture", color = SecondaryContainer, style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold)
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Column {
                            Text("Total Points", color = Color.White.copy(alpha = 0.8f), style = MaterialTheme.typography.labelMedium)
                            Text("${tempState.totalXp} XP", color = Color.White, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            // Continue Learning Horizontal Row with hotlinked image cards
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Continue Learning", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = Primary)
                    TextButton(onClick = { viewModel.navigateTo(AppScreen.LEARN_COURSES) }) {
                        Text("See All", color = Secondary, fontWeight = FontWeight.Bold)
                    }
                }
            }

            
        }
        NotificationsDialog(viewModel)
    }
}

@Composable
fun ActionPill(title: String, icon: ImageVector, onClick: () -> Unit) {
    Surface(
        modifier = Modifier
            .shadow(2.dp, CircleShape)
            .clickable { onClick() },
        color = SurfaceContainerLowest,
        shape = CircleShape,
        border = BorderStroke(1.dp, OutlineVariant.copy(alpha = 0.5f))
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(icon, contentDescription = null, tint = Primary, modifier = Modifier.size(20.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text(title, style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold, color = Primary)
        }
    }
}

@Composable
fun ContinueLearningCard(title: String, subtitle: String, imageUrl: String, progress: Float, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .width(160.dp)
            .border(1.dp, OutlineVariant.copy(alpha = 0.3f), RoundedCornerShape(12.dp))
            .shadow(4.dp, RoundedCornerShape(12.dp))
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceContainerLowest)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(90.dp)
                    .background(SurfaceVariant)
            ) {
                AsyncImage(
                    model = imageUrl,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                // Linear indicator showing micro-progress
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(4.dp)
                        .align(Alignment.BottomStart)
                        .background(SurfaceContainerHighest)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth(progress)
                            .background(Secondary)
                    )
                }
            }
            Column(modifier = Modifier.padding(12.dp)) {
                Text(title, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold, color = OnSurface, maxLines = 1)
                Spacer(modifier = Modifier.height(2.dp))
                Text(subtitle, fontSize = 10.sp, color = OnSurfaceVariant, maxLines = 1)
            }
        }
    }
}

// 2. Courses Explorer screen ("सीखो / Learn")
@Composable
fun LearnCoursesScreen(viewModel: DisharaViewModel) {
    val tempState by viewModel.tempProfile.collectAsState()
    val dbProgress by viewModel.dbProgressList.collectAsState()
    val completedCount = dbProgress.filter { it.completed }.size

    val searchVisible by viewModel.searchBarVisible.collectAsState()
    val searchVal by viewModel.searchQuery.collectAsState()
    val currentCategory by viewModel.selectedCategory.collectAsState()

    val categories = listOf("All", "Coding", "Design", "Career", "Soft Skills")

    Scaffold(
        topBar = {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = SurfaceContainerLowest,
                shadowElevation = 2.dp
            ) {
                Column(modifier = Modifier.statusBarsPadding()) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp, vertical = 12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            IconButton(onClick = { viewModel.setDrawerOpen(true) }, modifier = Modifier.size(40.dp)) {
                                Icon(Icons.Default.Menu, contentDescription = null, tint = Primary)
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                "📚 सीखो / Learn",
                                style = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.Bold,
                                color = Primary
                            )
                        }
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            IconButton(onClick = { viewModel.toggleSearchBar() }) {
                                Icon(Icons.Default.Search, contentDescription = "Search", tint = Primary)
                            }
                            Box(
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .background(SurfaceContainerLow)
                                    .padding(horizontal = 12.dp, vertical = 6.dp)
                            ) {
                                Text("HI | EN", color = OnSurfaceVariant, style = MaterialTheme.typography.labelMedium)
                            }
                        }
                    }

                    // Search panel expander
                    AnimatedVisibility(visible = searchVisible) {
                        OutlinedTextField(
                            value = searchVal,
                            onValueChange = { viewModel.updateSearchQuery(it) },
                            placeholder = { Text("विषय खोजें / Search courses...") },
                            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = Primary) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp, vertical = 8.dp),
                            shape = RoundedCornerShape(12.dp),
                            singleLine = true
                        )
                    }
                }
            }
        },
        bottomBar = { BottomNavBar(selectedScreen = AppScreen.LEARN_COURSES, onTabSelected = { viewModel.navigateTo(it) }) }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Background)
                .padding(innerPadding)
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // Hero Sunrise Progress banner
            item {
                Spacer(modifier = Modifier.height(12.dp))
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(4.dp, RoundedCornerShape(20.dp)),
                    colors = CardDefaults.cardColors(containerColor = Color.Transparent),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(SunriseGradient)
                            .padding(20.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1.3f)) {
                                Box(
                                    modifier = Modifier
                                        .clip(CircleShape)
                                        .background(SecondaryContainer)
                                        .padding(horizontal = 10.dp, vertical = 4.dp)
                                ) {
                                    val badgeText = if (tempState.enrolledBatchId.isEmpty()) {
                                        "No Active Batch / कोई सक्रिय बैच नहीं"
                                    } else {
                                        val currentBatch = AvailableBatches.find { it.id == tempState.enrolledBatchId } ?: AvailableBatches[0]
                                        "${currentBatch.nameHi} (${currentBatch.gradeHi})"
                                    }
                                    Text(badgeText, style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold, color = OnSecondaryContainer)
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    "आपका सफर / Your Journey",
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    if (tempState.enrolledBatchId.isEmpty()) {
                                        "Please select a learning batch to start your career journey."
                                    } else {
                                        "Continue where you left off in your professional development path."
                                    },
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color.White.copy(alpha = 0.8f)
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                                Card(
                                    colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.12f)),
                                    shape = RoundedCornerShape(12.dp),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Column(modifier = Modifier.padding(12.dp)) {
                                        if (tempState.enrolledBatchId.isEmpty()) {
                                            Row(
                                                modifier = Modifier.fillMaxWidth(),
                                                horizontalArrangement = Arrangement.SpaceBetween,
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                Text("Not Enrolled / अभी जुड़ें", style = MaterialTheme.typography.labelMedium, color = Color.White)
                                                TextButton(
                                                    onClick = { viewModel.navigateTo(AppScreen.BATCH_DETAILS) },
                                                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp),
                                                    colors = ButtonDefaults.textButtonColors(contentColor = Color.White)
                                                ) {
                                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                                        Text("Select / चुनें", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold)
                                                        Spacer(modifier = Modifier.width(4.dp))
                                                        Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, modifier = Modifier.size(12.dp))
                                                    }
                                                }
                                            }
                                        } else {
                                            val currentBatch = AvailableBatches.find { it.id == tempState.enrolledBatchId } ?: AvailableBatches[0]
                                            val completedFrac = if (currentBatch.modulesCount > 0) completedCount.toFloat() / currentBatch.modulesCount.toFloat() else 0f
                                            val pctText = "${(completedFrac * 100).toInt()}%"
                                            Row(
                                                modifier = Modifier.fillMaxWidth(),
                                                horizontalArrangement = Arrangement.SpaceBetween
                                            ) {
                                                Text("प्रगति / Progress", style = MaterialTheme.typography.labelMedium, color = Color.White)
                                                Text(pctText, style = MaterialTheme.typography.labelMedium, color = Color.White, fontWeight = FontWeight.Bold)
                                            }
                                            Spacer(modifier = Modifier.height(6.dp))
                                            Box(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .height(6.dp)
                                                    .clip(CircleShape)
                                                    .background(Color.White.copy(alpha = 0.2f))
                                            ) {
                                                Box(
                                                    modifier = Modifier
                                                        .fillMaxHeight()
                                                        .fillMaxWidth(completedFrac.coerceIn(0.05f, 1f))
                                                        .clip(CircleShape)
                                                        .background(SecondaryContainer)
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.width(16.dp))
                            // Hotlinked graphic loaded with Coil AsyncImage
                            AsyncImage(
                                model = "https://lh3.googleusercontent.com/aida-public/AB6AXuAkjHXKvUSKSopfNC-A0XB-51N8jv0u1kxLc5mYazUbtjMWyF56pb4nJ4nUV6aAXrrcMDemsHZN37SikjFScwQAueEVpFZF2l0UkQ9k5Ha-lhow4FlZNLIT7yYcEBmGJrRp3wt4wdPKEkaTu_j28Npr0ONnGQljIXp0XBzRY6ix_y_uyhNOfrbQ15hv93kXcAEPAX-ZZrnT4JLuwpVHjUuleq4JvspVwzDfnRQXOaNj5OsSGFvSbW40",
                                contentDescription = "Vibrant claymorphic rising sun over digital tech circuits design",
                                modifier = Modifier
                                    .weight(0.7f)
                                    .aspectRatio(0.9f)
                                    .clip(RoundedCornerShape(12.dp)),
                                contentScale = ContentScale.Fit
                            )
                        }
                    }
                }
            }

            // Categories Filter horizontal Pills
            item {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(categories) { cat ->
                        val active = currentCategory == cat
                        val bgColor = if (active) Primary else SurfaceContainerHigh
                        val textColor = if (active) Color.White else OnSurfaceVariant
                        Box(
                            modifier = Modifier
                                .clip(CircleShape)
                                .background(bgColor)
                                .clickable { viewModel.setCourseCategory(cat) }
                                .padding(horizontal = 20.dp, vertical = 8.dp)
                        ) {
                            Text(cat, color = textColor, style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            // Core modules lists dynamically fetched from the batch curriculum
            val activeBatchId = tempState.enrolledBatchId.ifEmpty { "udaan" }
            val batchModules = com.example.data.CurriculumProvider.getModulesForBatch(activeBatchId)
            val filteredModules = batchModules.filter { module ->
                (currentCategory == "All" || module.category.equals(currentCategory, ignoreCase = true)) &&
                (searchVal.isEmpty() || 
                 module.titleEn.lowercase().contains(searchVal.lowercase()) || 
                 module.titleHi.contains(searchVal) ||
                 module.descriptionEn.lowercase().contains(searchVal.lowercase()) ||
                 module.descriptionHi.contains(searchVal))
            }

            if (filteredModules.isEmpty()) {
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
                        colors = CardDefaults.cardColors(containerColor = SurfaceContainerLow)
                    ) {
                        Column(
                            modifier = Modifier.padding(24.dp).fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "कोई कोर्स नहीं मिला / No courses found",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = OnSurfaceVariant
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "कृपया कोई दूसरा विषय खोजें।",
                                style = MaterialTheme.typography.bodySmall,
                                color = Outline
                            )
                        }
                    }
                }
            } else {
                items(filteredModules) { module ->
                    val isFirst = filteredModules.indexOf(module) == 0
                    ModuleRowItem(
                        title = "${module.titleHi} / ${module.titleEn}",
                        videos = "${module.lessons.size} Lessons / पाठ",
                        tasks = module.category,
                        completedText = if (isFirst) "Active • चालू करें" else "Available • उपलब्ध",
                        icon = if (module.iconName == "code") Icons.Default.Code else Icons.Default.Brush,
                        percent = if (isFirst) 0.1f else 0.0f,
                        active = isFirst,
                        onClick = { viewModel.selectModule(module.id) }
                    )
                }
            }

            // Other Batches Layout
            item {
                Text("दूसरे बैच / Other Batches", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = Primary)
            }

            item {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 24.dp)
                ) {
                    item {
                        OtherBatchCard(
                            tag = "ADVANCED",
                            title = "Pragati (Grade 11-12)",
                            desc = "Data Science & App Development track for higher grades.",
                            imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuDDKiGvGfWs-4QynmIMmbS-TjT9UsIzFaoxiLkAPoLBJKZTMDoVvXPGRBUjAqC6uJlANbR37VOgUXy9AL4li-0jha2WM0sGsdA615zy9xAsS2A3-00kFb1qMOec5PqNn7krss3DP35lO6jhiKGUZlmSpLi27g6KpIIU_1Dz8H9jLqSPo8_Tw9yFGAs1z3ryba-Nt9LXeq1LA6k2jnhIVJJD6vBO7BlitPcIgRYEapXCdYk50CO70MFM",
                            tagBg = PrimaryContainer
                        )
                    }
                    item {
                        OtherBatchCard(
                            tag = "ENTREPRENEUR",
                            title = "Lakshya (Alumni)",
                            desc = "Placement support and professional networking in IT.",
                            imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuDHN2d21Vlz9h-qZsODlpu6kLeVOWmp4uyaTBCGpBVdF8ByIw3ffGov9PK0_aDY8ns_yBozDsBXGE9vIU5N0KmOirsCUV0M04ZN13Gu5uVAIgMYzLIQXgov8q4TLbbK8ZXj59Sbq5N0o_7gx1uFqB_HaDCE7kuGHRdYBl_aPQDN8ezVQH1GhHOnOHCb3JWK-V_EuhlK0LY_j6Q9Mc-AgdmmxOBXqYOHuZzboMHddL3kuyu9EnVrb_1-",
                            tagBg = Secondary
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ModuleRowItem(
    title: String,
    videos: String,
    tasks: String,
    completedText: String,
    icon: ImageVector,
    percent: Float,
    active: Boolean,
    isLocked: Boolean = false,
    onClick: () -> Unit
) {
    var expanded by remember { mutableStateOf(active) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(4.dp, RoundedCornerShape(12.dp))
            .border(
                border = if (expanded && !isLocked) BorderStroke(1.5.dp, Secondary) else BorderStroke(0.dp, Color.Transparent),
                shape = RoundedCornerShape(12.dp)
            ),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceContainerLowest)
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { if (!isLocked) expanded = !expanded }
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(if (isLocked) SurfaceContainer else SecondaryContainer.copy(alpha = 0.3f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(icon, contentDescription = null, tint = if (isLocked) Outline else Secondary, modifier = Modifier.size(24.dp))
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(title, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold, color = if (isLocked) OnSurfaceVariant.copy(alpha = 0.6f) else Primary)
                        Spacer(modifier = Modifier.height(4.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                            if (videos.isNotEmpty()) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(if (isLocked) Icons.Default.Lock else Icons.Default.PlayCircle, contentDescription = null, modifier = Modifier.size(14.dp), tint = OnSurfaceVariant)
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(videos, fontSize = 11.sp, color = OnSurfaceVariant)
                                }
                            }
                            if (tasks.isNotEmpty()) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Default.Task, contentDescription = null, modifier = Modifier.size(14.dp), tint = OnSurfaceVariant)
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(tasks, fontSize = 11.sp, color = OnSurfaceVariant)
                                }
                            }
                        }
                    }
                }
                Icon(
                    imageVector = if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                    contentDescription = null,
                    tint = Outline
                )
            }

            AnimatedVisibility(visible = expanded && !isLocked) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                        .drawBehind {
                            drawLine(
                                color = OutlineVariant.copy(alpha = 0.5f),
                                start = Offset(0f, 0f),
                                end = Offset(size.width, 0f),
                                strokeWidth = 1.dp.toPx()
                            )
                        }
                ) {
                    Spacer(modifier = Modifier.height(12.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                            .clip(CircleShape)
                            .background(SurfaceContainerHighest)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxHeight()
                                .fillMaxWidth(percent)
                                .clip(CircleShape)
                                .background(Secondary)
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(completedText, style = MaterialTheme.typography.bodySmall, color = OnSurfaceVariant)
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = onClick,
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = Primary),
                        shape = RoundedCornerShape(24.dp)
                    ) {
                        Text(if (percent > 0) "Continue Learning" else "Resume Module", color = Color.White, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Composable
fun OtherBatchCard(tag: String, title: String, desc: String, imageUrl: String, tagBg: Color) {
    Card(
        modifier = Modifier
            .width(280.dp)
            .shadow(4.dp, RoundedCornerShape(20.dp)),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceContainerLowest)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .background(SurfaceVariant)
            ) {
                AsyncImage(
                    model = imageUrl,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                Box(
                    modifier = Modifier
                        .padding(12.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(tagBg.copy(alpha = 0.85f))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                        .align(Alignment.TopStart)
                ) {
                    Text(tag, color = Color.White, fontSize = 9.sp, fontWeight = FontWeight.Bold)
                }

                // Lock Overlay
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Primary.copy(alpha = 0.35f)),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.9f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.Lock, contentDescription = "Locked", tint = Primary)
                    }
                }
            }
            Column(modifier = Modifier.padding(16.dp)) {
                Text(title, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold, color = Primary)
                Spacer(modifier = Modifier.height(6.dp))
                Text(desc, style = MaterialTheme.typography.bodySmall, color = OnSurfaceVariant, maxLines = 2)
            }
        }
    }
}

// 3. Study Plan list screen ("आपका Study Plan")
@Composable
fun StudyPlanScreen(viewModel: DisharaViewModel) {
    val tempState by viewModel.tempProfile.collectAsState()
    val activeWeek by viewModel.activeWeek.collectAsState()
    val dbProgress by viewModel.dbProgressList.collectAsState()

    // Mock/pre-loaded plan details
    val focusChips = listOf("Design", "Coding")
    val studyHoursGoal = "हफ्ते में 5 घंटे"

    val notifications by viewModel.notifications.collectAsState()
    val hasUnread = notifications.any { !it.isRead }

    Scaffold(
        topBar = {
            TopAppBar(
                title = "Dishara",
                onMenuClick = { viewModel.setDrawerOpen(true) },
                hasUnread = hasUnread,
                onNotificationClick = { viewModel.setShowNotificationsDialog(true) }
            )
        },
        bottomBar = { BottomNavBar(selectedScreen = AppScreen.STUDY_PLAN, onTabSelected = { viewModel.navigateTo(it) }) }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Background)
                .padding(innerPadding)
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("आपका Study Plan", style = MaterialTheme.typography.headlineLarge, color = Primary, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.width(8.dp))
                    IconButton(onClick = {}) {
                        Icon(Icons.Default.Edit, contentDescription = "Edit Study Plan", tint = Primary)
                    }
                }
            }

            // Summary Info Cluster (glass card layout)
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(4.dp, RoundedCornerShape(16.dp)),
                    colors = CardDefaults.cardColors(containerColor = SurfaceContainerLowest),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Text("My Focus", style = MaterialTheme.typography.labelMedium, color = OnSurfaceVariant, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            focusChips.forEach { chip ->
                                Box(
                                    modifier = Modifier
                                        .clip(CircleShape)
                                        .background(Primary.copy(alpha = 0.05f))
                                        .border(1.dp, Primary.copy(alpha = 0.1f), CircleShape)
                                        .padding(horizontal = 12.dp, vertical = 6.dp)
                                ) {
                                    Text(chip, color = Primary, style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Schedule, contentDescription = null, tint = Primary)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(studyHoursGoal, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold, color = Primary)
                        }
                    }
                }
            }

            // Career Path Display Row
            item {
                Text("Career Path लक्ष्य", style = MaterialTheme.typography.labelMedium, color = OnSurfaceVariant, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Card(
                        modifier = Modifier
                            .weight(1.2f)
                            .height(100.dp)
                            .shadow(3.dp, RoundedCornerShape(16.dp)),
                        colors = CardDefaults.cardColors(containerColor = Primary),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Box(modifier = Modifier.fillMaxSize()) {
                            Icon(
                                Icons.Default.Terminal,
                                contentDescription = null,
                                tint = Color.White.copy(alpha = 0.08f),
                                modifier = Modifier
                                    .size(80.dp)
                                    .align(Alignment.BottomEnd)
                                    .offset(x = 10.dp, y = 10.dp)
                            )
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text("Web Dev", style = MaterialTheme.typography.titleLarge, color = Color.White, fontWeight = FontWeight.Bold)
                                Text("Junior Specialist", style = MaterialTheme.typography.bodySmall, color = Color.White.copy(alpha = 0.7f))
                            }
                        }
                    }

                    Card(
                        modifier = Modifier
                            .weight(1f)
                            .height(100.dp)
                            .border(1.5.dp, OutlineVariant, RoundedCornerShape(16.dp))
                            .shadow(1.dp, RoundedCornerShape(16.dp)),
                        colors = CardDefaults.cardColors(containerColor = SurfaceContainerLowest),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .clickable { }
                                .padding(12.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Icon(Icons.Default.AddCircle, contentDescription = null, tint = OnSurfaceVariant)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text("लक्ष्य जोड़ें", style = MaterialTheme.typography.labelMedium, color = OnSurfaceVariant, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            // Weekly Roadmap
            item {
                Text("Weekly Roadmap", style = MaterialTheme.typography.titleMedium, color = Primary, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(12.dp))

                // Week selector list
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(listOf(1, 2, 3, 4)) { week ->
                        val active = week == activeWeek
                        val bgColor = if (active) Primary else SurfaceContainerLow
                        val textColor = if (active) Color.White else OnSurfaceVariant
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .background(bgColor)
                                .clickable { viewModel.setStudyPlanWeek(week) }
                                .padding(horizontal = 24.dp, vertical = 12.dp)
                        ) {
                            Text("Week $week", color = textColor, style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            // Timeline active weeks list items
            item {
                Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    val isEnglish = true
                    val activeBatchId = tempState.enrolledBatchId.ifEmpty { "udaan" }
                    val batchModules = com.example.data.CurriculumProvider.getModulesForBatch(activeBatchId)
                    val allLessons = batchModules.flatMap { it.lessons }

                    // Assign lessons to weeks (5 lessons per week)
                    val lessonsPerPage = 5
                    val startIndex = (activeWeek - 1) * lessonsPerPage
                    val endIndex = minOf(startIndex + lessonsPerPage, allLessons.size)
                    val weekLessons = if (startIndex < allLessons.size) {
                        allLessons.subList(startIndex, endIndex)
                    } else {
                        emptyList()
                    }

                    if (weekLessons.isEmpty()) {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = SurfaceContainerLow)
                        ) {
                            Box(modifier = Modifier.padding(16.dp), contentAlignment = Alignment.Center) {
                                Text(
                                    text = if (isEnglish) "No lessons scheduled for this week." else "इस सप्ताह के लिए कोई पाठ निर्धारित नहीं है।",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = OnSurfaceVariant
                                )
                            }
                        }
                    } else {
                        weekLessons.forEachIndexed { index, lesson ->
                            val isLessonCompleted = dbProgress.firstOrNull { it.lessonId == lesson.id }?.completed ?: false
                            val status = when {
                                isLessonCompleted -> "Completed"
                                index == 0 -> "InProgress"
                                else -> "Locked"
                            }
                            
                            val title = if (isEnglish) lesson.titleEn else lesson.titleHi
                            val subtitle = if (isEnglish) lesson.subtitleEn else lesson.subtitleHi
                            
                            RoadmapLessonItem(
                                title = title,
                                subtitle = "$subtitle • ${lesson.durationMin} mins",
                                status = status,
                                onClick = {
                                    viewModel.selectLesson(lesson.id)
                                }
                            )
                        }
                    }
                }
            }

            // Progress summary panel
            item {
                val activeBatchId = tempState.enrolledBatchId.ifEmpty { "udaan" }
                val batchModules = com.example.data.CurriculumProvider.getModulesForBatch(activeBatchId)
                val allLessons = batchModules.flatMap { it.lessons }
                val completedCount = allLessons.count { lesson -> dbProgress.any { it.lessonId == lesson.id && it.completed } }
                val totalCount = allLessons.size
                val progressRatio = if (totalCount > 0) completedCount.toFloat() / totalCount else 0.0f

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(4.dp, RoundedCornerShape(20.dp)),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = SurfaceContainerLowest)
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Progress Summary", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = OnSurface)
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.LocalFireDepartment, contentDescription = null, tint = Error, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("7-day streak", color = Error, style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold)
                            }
                        }
                        Spacer(modifier = Modifier.height(20.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            ProgressRing(progress = progressRatio, modifier = Modifier.size(80.dp))
                            Spacer(modifier = Modifier.width(24.dp))
                            Column {
                                Text("$completedCount/$totalCount Lessons completed", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold, color = OnSurface)
                                val badgeCount = if (completedCount >= totalCount && totalCount > 0) 2 else if (completedCount > totalCount / 2) 1 else 0
                                Text("$badgeCount Milestone badges earned", style = MaterialTheme.typography.bodySmall, color = OnSurfaceVariant)
                            }
                        }
                    }
                }
            }

            // Long term Milestones vertical checklist
            item {
                Text("Long-term Milestones", style = MaterialTheme.typography.titleMedium, color = Primary, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(12.dp))
                Column(modifier = Modifier.padding(start = 12.dp)) {
                    MilestoneCheckItem(
                        title = "Frontend Certification",
                        subtitle = "Complete Week 4 to unlock",
                        active = true
                    )
                    MilestoneCheckItem(
                        title = "First Portfolio Project",
                        subtitle = "Estimated: Month 2",
                        active = false
                    )
                }
            }

            // Regenerate plan button
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 24.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    OutlinedButton(
                        onClick = { viewModel.navigateTo(AppScreen.PROFILE_INTERESTS) },
                        border = BorderStroke(2.dp, Secondary),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = Secondary),
                        modifier = Modifier.height(48.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.RestartAlt, contentDescription = null)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Regenerate Plan", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
        NotificationsDialog(viewModel)
    }
}

@Composable
fun RoadmapLessonItem(title: String, subtitle: String, status: String, onClick: () -> Unit) {
    val completed = status == "Completed"
    val inProgress = status == "InProgress"
    val locked = status == "Locked"

    val borderStroke = when {
        completed -> BorderStroke(1.5.dp, Color(0xFF4CAF50))
        inProgress -> BorderStroke(1.5.dp, Primary)
        else -> BorderStroke(1.dp, OutlineVariant.copy(alpha = 0.4f))
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(if (inProgress) 6.dp else 2.dp, RoundedCornerShape(12.dp))
            .clickable(enabled = !locked) { onClick() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceContainerLowest),
        border = borderStroke
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(
                            when {
                                completed -> Color(0xFFE8F5E9)
                                inProgress -> SecondaryContainer
                                else -> SurfaceContainer
                            }
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = when {
                            completed -> Icons.Default.CheckCircle
                            inProgress -> Icons.Default.Sync
                            else -> Icons.Default.Lock
                        },
                        contentDescription = null,
                        tint = when {
                            completed -> Color(0xFF4CAF50)
                            inProgress -> OnSecondaryContainer
                            else -> Outline
                        },
                        modifier = Modifier.size(20.dp)
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        color = if (locked) OnSurfaceVariant.copy(alpha = 0.5f) else OnSurface,
                        modifier = if (completed) Modifier.drawBehind {
                            // Strike-through effect
                            drawLine(
                                color = OnSurfaceVariant.copy(alpha = 0.6f),
                                start = Offset(0f, size.height / 2),
                                end = Offset(size.width, size.height / 2),
                                strokeWidth = 1.5.dp.toPx()
                            )
                        } else Modifier
                    )
                    Text(subtitle, fontSize = 11.sp, color = OnSurfaceVariant)
                }
            }
            if (inProgress) {
                Button(
                    onClick = onClick,
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Primary),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 6.dp)
                ) {
                    Text("Start", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                }
            } else if (completed) {
                Icon(Icons.AutoMirrored.Filled.ArrowForwardIos, contentDescription = null, tint = Outline, modifier = Modifier.size(16.dp))
            }
        }
    }
}

@Composable
fun MilestoneCheckItem(title: String, subtitle: String, active: Boolean) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(28.dp)
                .clip(CircleShape)
                .background(if (active) Primary else SurfaceContainerHighest),
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Default.Flag, contentDescription = null, tint = if (active) Color.White else Outline, modifier = Modifier.size(14.dp))
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(title, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold, color = if (active) OnSurface else Outline)
            Text(subtitle, style = MaterialTheme.typography.bodySmall, color = OnSurfaceVariant)
        }
    }
}

// 4. Module Curriculum Details screen ("Curriculum / Detail")
@Composable
fun ModuleDetailScreen(viewModel: DisharaViewModel) {
    val tempState by viewModel.tempProfile.collectAsState()
    val selectedModuleId by viewModel.selectedModuleId.collectAsState()
    val isEnglish = true
    
    val activeBatchId = tempState.enrolledBatchId.ifEmpty { "udaan" }
    val batchModules = com.example.data.CurriculumProvider.getModulesForBatch(activeBatchId)
    val module = com.example.data.CurriculumProvider.getModuleById(selectedModuleId ?: "")
        ?: batchModules.firstOrNull()
        ?: com.example.data.CurriculumProvider.batchesCurriculum.values.flatten().first()

    val title = if (isEnglish) module.titleEn else module.titleHi
    val description = if (isEnglish) module.descriptionEn else module.descriptionHi

    Scaffold(
        topBar = {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = SurfaceContainerLowest,
                shadowElevation = 1.dp
            ) {
                Row(
                    modifier = Modifier
                        .statusBarsPadding()
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { viewModel.navigateBack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Primary)
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (isEnglish) "Course Details" else "कोर्स की जानकारी",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = Primary
                    )
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Background)
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            // Hero Header Section Image
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp)
            ) {
                AsyncImage(
                    model = "https://lh3.googleusercontent.com/aida-public/AB6AXuBdj_z52KvmP5ljXEH17DC1GRFy7W3cGheJ8MoQpXMEEBey-W3K431jw02-vqNym33pILnyIpkAyd_WDuff8r2yrLMgUgSPIyPNXI0xk0gAfU4R_1tk9Pe0x2DGqElsZp49MQwz-pJ2Nu9HkMw2aVmp2OGQJANE1lJHx3D5shHjtaXpjY0YJz67ri5WohjoQZMSRKxMYeu2lQ_6mLh5odZBLHxVOzBlf73hgYTQn2vJ5VIWBZwjvKvh",
                    contentDescription = "Holographic code blocks and science charts classroom graphic",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.6f))
                            )
                        )
                )
                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineLarge,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(24.dp)
                )
            }

            // Overview Details
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
                    .shadow(4.dp, RoundedCornerShape(16.dp)),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceContainerLowest)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        text = description,
                        style = MaterialTheme.typography.bodyMedium,
                        color = OnSurfaceVariant,
                        lineHeight = 22.sp
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    HorizontalDivider(color = OutlineVariant.copy(alpha = 0.5f))
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(Icons.Default.Book, contentDescription = null, tint = Primary)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = if (isEnglish) "1 Module" else "1 मॉड्यूल",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold,
                                color = OnSurface
                            )
                        }
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(Icons.Default.PlayCircle, contentDescription = null, tint = Primary)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = if (isEnglish) "${module.lessons.size} Lessons" else "${module.lessons.size} पाठ",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold,
                                color = OnSurface
                            )
                        }
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(Icons.Default.Schedule, contentDescription = null, tint = Primary)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "${module.lessons.sumOf { it.durationMin }} Mins",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold,
                                color = OnSurface
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        listOf(module.category, "Interactive", "Bilingual").forEach { tag ->
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(SecondaryContainer.copy(alpha = 0.3f))
                                    .padding(horizontal = 10.dp, vertical = 4.dp)
                            ) {
                                Text(tag, color = OnSecondaryContainer, style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }

            // Curriculum accordion list
            Text(
                text = if (isEnglish) "Lessons Curriculum" else "पाठ्यक्रम",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = Primary,
                modifier = Modifier.padding(horizontal = 24.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp, bottom = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(2.dp, RoundedCornerShape(12.dp)),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = SurfaceContainerLowest),
                    border = BorderStroke(1.dp, OutlineVariant.copy(alpha = 0.4f))
                ) {
                    Column {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(32.dp)
                                        .clip(CircleShape)
                                        .background(PrimaryContainer),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text("01", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                                }
                                Spacer(modifier = Modifier.width(12.dp))
                                Column {
                                    Text(
                                        text = if (isEnglish) "Course Contents" else "कोर्स की सामग्री",
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = "${module.lessons.size} Lessons • ${module.lessons.sumOf { it.durationMin }} mins",
                                        style = MaterialTheme.typography.labelMedium,
                                        color = OnSurfaceVariant
                                    )
                                }
                            }
                            Icon(Icons.Default.ExpandLess, contentDescription = null, tint = Outline)
                        }

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Surface)
                                .padding(8.dp)
                        ) {
                            module.lessons.forEachIndexed { index, lesson ->
                                val lessonTitle = if (isEnglish) lesson.titleEn else lesson.titleHi
                                CurriculumLessonRow(
                                    title = "${index + 1}. $lessonTitle",
                                    status = if (index == 0) "Now Playing" else "Completed"
                                ) {
                                    viewModel.selectLesson(lesson.id)
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // WORLD QUIZ COMPONENT
            val worldIndex = when {
                module.id.contains("world_1") || module.id.contains("lvl_1") -> 1
                module.id.contains("world_2") || module.id.contains("lvl_2") -> 2
                module.id.contains("world_3") || module.id.contains("lvl_3") -> 3
                module.id.contains("world_4") || module.id.contains("lvl_4") -> 4
                module.id.contains("world_5") || module.id.contains("lvl_5") -> 5
                else -> 1
            }
            val passedWorlds by viewModel.passedWorlds.collectAsState()
            val isUnlocked = worldIndex <= 1 || passedWorlds.contains(worldIndex - 1)

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .padding(bottom = 120.dp)
                    .shadow(if (isUnlocked) 6.dp else 1.dp, RoundedCornerShape(16.dp)),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (isUnlocked) SurfaceContainerLowest else SurfaceContainerLowest.copy(alpha = 0.6f)
                ),
                border = BorderStroke(
                    width = if (isUnlocked) 2.dp else 1.dp,
                    color = if (isUnlocked) Primary.copy(alpha = 0.5f) else OutlineVariant.copy(alpha = 0.3f)
                )
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(CircleShape)
                                    .background(if (isUnlocked) PrimaryContainer else Color.LightGray),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = if (isUnlocked) Icons.Default.WorkspacePremium else Icons.Default.Lock,
                                    contentDescription = null,
                                    tint = if (isUnlocked) Primary else Color.Gray,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    text = "World $worldIndex Grand Quiz",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isUnlocked) OnSurface else OnSurface.copy(alpha = 0.5f)
                                )
                                Text(
                                    text = "25 Questions • Passing Score 60%",
                                    style = MaterialTheme.typography.labelMedium,
                                    color = OnSurfaceVariant.copy(alpha = 0.7f)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = if (isUnlocked) {
                            "Test your mastery of World $worldIndex concepts! Passing unlocks the next World."
                        } else {
                            "🔒 World Quiz locked. You must pass World ${worldIndex - 1} Grand Quiz first to unlock this world's quiz!"
                        },
                        style = MaterialTheme.typography.bodySmall,
                        color = if (isUnlocked) OnSurfaceVariant else Error
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Button(
                        onClick = { viewModel.startWorldQuiz(worldIndex) },
                        enabled = isUnlocked,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Primary,
                            disabledContainerColor = Color.LightGray
                        ),
                        shape = RoundedCornerShape(24.dp)
                    ) {
                        Text(
                            text = if (isUnlocked) "🔥 START GRAND QUIZ" else "🔒 LOCKED",
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CurriculumLessonRow(title: String, status: String, onClick: () -> Unit) {
    val active = status == "Now Playing"
    val completed = status == "Completed"
    val locked = status == "Locked"

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(if (active) Color.White else Color.Transparent)
            .clickable(enabled = !locked) { onClick() }
            .padding(horizontal = 12.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = when {
                    completed -> Icons.Default.PlayCircle
                    active -> Icons.Default.Article
                    else -> Icons.Default.Quiz
                },
                contentDescription = null,
                tint = Primary,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(title, style = MaterialTheme.typography.bodySmall, fontWeight = if (active) FontWeight.Bold else FontWeight.Medium)
        }
        if (completed) {
            Icon(Icons.Default.CheckCircle, contentDescription = null, tint = Secondary, modifier = Modifier.size(18.dp))
        } else if (active) {
            Text("Now Playing", color = Secondary, style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold)
        } else {
            Icon(Icons.Default.Lock, contentDescription = null, tint = OutlineVariant, modifier = Modifier.size(18.dp))
        }
    }
}

@Composable
fun CurriculumLockedChapter(num: String, title: String, subtitle: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(1.dp, RoundedCornerShape(12.dp)),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceContainerLowest),
        border = BorderStroke(1.dp, OutlineVariant.copy(alpha = 0.2f))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(SurfaceVariant),
                    contentAlignment = Alignment.Center
                ) {
                    Text(num, color = OnSurfaceVariant, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(title, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold, color = OnSurfaceVariant.copy(alpha = 0.6f))
                    Text(subtitle, style = MaterialTheme.typography.labelMedium, color = OnSurfaceVariant.copy(alpha = 0.5f))
                }
            }
            Icon(Icons.Default.Lock, contentDescription = "Locked", tint = OutlineVariant)
        }
    }
}

// 5. Lesson Player Screen ("Lesson View" with actual code highlights & real AI tutor)
@Composable
fun LessonPlayerScreen(viewModel: DisharaViewModel) {
    val inputVal by viewModel.mitraInput.collectAsState()
    val chatMessages by viewModel.mitraMessages.collectAsState()
    val isAiLoading by viewModel.isMitraLoading.collectAsState()
    val dbProgress by viewModel.dbProgressList.collectAsState()

    val tempState by viewModel.tempProfile.collectAsState()
    val isEnglish = true
    val selectedLessonId by viewModel.selectedLessonId.collectAsState()
    
    val activeBatchId = tempState.enrolledBatchId.ifEmpty { "udaan" }
    val batchModules = com.example.data.CurriculumProvider.getModulesForBatch(activeBatchId)
    val defaultLesson = batchModules.firstOrNull()?.lessons?.firstOrNull()
        ?: com.example.data.CurriculumProvider.batchesCurriculum.values.flatten().flatMap { it.lessons }.first()
    
    val lesson = com.example.data.CurriculumProvider.getLessonById(selectedLessonId ?: "")
        ?: defaultLesson

    val title = if (isEnglish) lesson.titleEn else lesson.titleHi
    val subtitle = if (isEnglish) lesson.subtitleEn else lesson.subtitleHi
    val reading = if (isEnglish) lesson.readingEn else lesson.readingHi

    val coroutineScope = rememberCoroutineScope()

    // Determine current lesson completion from Room
    val isCompleted = dbProgress.firstOrNull { it.lessonId == lesson.id }?.completed ?: false

    Scaffold(
        topBar = {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = SurfaceContainerLowest,
                shadowElevation = 2.dp
            ) {
                Row(
                    modifier = Modifier
                        .statusBarsPadding()
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        IconButton(onClick = { viewModel.navigateBack() }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Primary)
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                            Text(title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = Primary)
                            Text(subtitle, style = MaterialTheme.typography.labelMedium, color = Outline)
                        }
                    }
                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(SurfaceContainerLow)
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Text("HI | EN", color = OnSurfaceVariant, style = MaterialTheme.typography.labelMedium)
                    }
                }
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Background)
                .padding(innerPadding)
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // High-definition video screen mockup
            item {
                Spacer(modifier = Modifier.height(12.dp))
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(16f / 9f)
                        .shadow(4.dp, RoundedCornerShape(16.dp)),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        AsyncImage(
                            model = "https://lh3.googleusercontent.com/aida-public/AB6AXuCPAqz4cRp4mRbKgGvrOyfQG5v-JQeVO66w3QPnUx1SlEzJ6SkkKo8Q4RVSr57rA6V7LRzOWIzRPFvWrNYAp4_rHYpx0PbdcN2B6yglIL1j0RO25MG1nzSYqebPXeS-bIn69NfiYTEf7F5yP2V7hRxRoeKzOd0zTu6bzEHoQSlrazPpkMo7JEciL1cBas6yJVKfgIxqHB_RM5q0UEykWhVwgywgCeEJ6JhP1Sm0jctzaiyVNWxS4dDi",
                            contentDescription = "HD code editor learning screenshot video player",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.Black.copy(alpha = 0.25f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(64.dp)
                                    .clip(CircleShape)
                                    .background(Primary.copy(alpha = 0.8f))
                                    .border(2.dp, Color.White.copy(alpha = 0.3f), CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(Icons.Default.PlayArrow, contentDescription = "Play lesson video", tint = Color.White, modifier = Modifier.size(32.dp))
                            }
                        }
                    }
                }
            }

            // Conceptual Reading Article Card
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(2.dp, RoundedCornerShape(16.dp)),
                    colors = CardDefaults.cardColors(containerColor = SurfaceContainerLowest),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(title, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold, color = Primary, modifier = Modifier.weight(1f))
                            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(6.dp))
                                        .background(Color(0xFFE8F5E9))
                                        .padding(horizontal = 8.dp, vertical = 4.dp)
                                ) {
                                    Text(lesson.difficulty, color = Color(0xFF2E7D32), fontSize = 10.sp, fontWeight = FontWeight.Bold)
                                }
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(6.dp))
                                        .background(SurfaceContainerHigh)
                                        .padding(horizontal = 8.dp, vertical = 4.dp)
                                ) {
                                    Text("${lesson.durationMin} mins", color = OnSurfaceVariant, fontSize = 10.sp)
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = reading,
                            style = MaterialTheme.typography.bodyMedium,
                            color = OnSurface,
                            lineHeight = 22.sp
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // Key Terms highlights box
                        if (lesson.keyTerms.isNotEmpty()) {
                            Card(
                                colors = CardDefaults.cardColors(containerColor = SurfaceContainerLow),
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(Icons.Default.Key, contentDescription = null, tint = Primary, modifier = Modifier.size(16.dp))
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text(if (isEnglish) "Key Terms" else "मुख्य शब्द", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold, color = Primary)
                                    }
                                    Spacer(modifier = Modifier.height(10.dp))
                                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                                        lesson.keyTerms.forEach { term ->
                                            Row(verticalAlignment = Alignment.CenterVertically) {
                                                Box(modifier = Modifier.size(6.dp).clip(CircleShape).background(Primary))
                                                Spacer(modifier = Modifier.width(8.dp))
                                                Text(term, style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.SemiBold)
                                            }
                                        }
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.height(16.dp))
                        }

                        // Code highlighter card
                        if (lesson.codeSnippet.isNotEmpty()) {
                            Text(if (isEnglish) "Example Code / Code Snippet:" else "उदाहरण कोड / कोड स्निपेट:", style = MaterialTheme.typography.bodySmall, color = OnSurfaceVariant)
                            Spacer(modifier = Modifier.height(8.dp))
                            Card(
                                colors = CardDefaults.cardColors(containerColor = InverseSurface),
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    Text(
                                        text = lesson.codeSnippet,
                                        color = InverseOnSurface,
                                        fontSize = 13.sp,
                                        lineHeight = 18.sp
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Mark as Complete button
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    val completed = isCompleted
                    Button(
                        onClick = {
                            viewModel.markLessonComplete(lesson.id, "dynamic_module", !completed)
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = if (completed) Color(0xFF4CAF50) else Secondary),
                        modifier = Modifier
                            .fillMaxWidth(0.9f)
                            .height(54.dp),
                        shape = RoundedCornerShape(27.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(if (completed) Icons.Default.CheckCircle else Icons.Default.TaskAlt, contentDescription = null, tint = Color.White)
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(if (completed) "Completed!" else "Mark as Complete", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        }
                    }
                }
            }

            // Floating / Inline Mitra AI Chat Bot panel
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(4.dp, RoundedCornerShape(16.dp)),
                    colors = CardDefaults.cardColors(containerColor = PrimaryContainer),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(CircleShape)
                                    .background(SecondaryContainer),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(Icons.Default.SmartToy, contentDescription = null, tint = OnSecondaryContainer, modifier = Modifier.size(20.dp))
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text("Mitra AI", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = Color.White)
                                Text("Active & ready to help", fontSize = 11.sp, color = Color.White.copy(alpha = 0.7f))
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Box displaying recent chat history
                        Card(
                            colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.08f)),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(160.dp),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            LazyColumn(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(12.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                items(chatMessages) { msg ->
                                    val bubbleBg = if (msg.second) Secondary else Color.White.copy(alpha = 0.15f)
                                    val alignment = if (msg.second) Alignment.End else Alignment.Start
                                    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = alignment) {
                                        Box(
                                            modifier = Modifier
                                                .clip(RoundedCornerShape(12.dp))
                                                .background(bubbleBg)
                                                .padding(horizontal = 12.dp, vertical = 8.dp)
                                        ) {
                                            Text(msg.first, color = Color.White, fontSize = 13.sp)
                                        }
                                    }
                                }
                                if (isAiLoading) {
                                    item {
                                        Text("दिशारा मित्र सोच रहा है...", color = Color.White.copy(alpha = 0.6f), fontStyle = FontStyle.Italic, fontSize = 12.sp)
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        // Text input field
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            OutlinedTextField(
                                value = inputVal,
                                onValueChange = { viewModel.updateMitraInput(it) },
                                placeholder = { Text("Ask me about the DOM Tree...", color = Color.White.copy(alpha = 0.4f)) },
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(12.dp),
                                singleLine = true,
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = SecondaryContainer,
                                    unfocusedBorderColor = Color.White.copy(alpha = 0.2f),
                                    focusedTextColor = Color.White,
                                    unfocusedTextColor = Color.White
                                )
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            IconButton(
                                onClick = { viewModel.sendMitraMessage() },
                                modifier = Modifier
                                    .size(48.dp)
                                    .clip(CircleShape)
                                    .background(SecondaryContainer)
                            ) {
                                Icon(Icons.AutoMirrored.Filled.Send, contentDescription = "Send prompt", tint = OnSecondaryContainer)
                            }
                        }
                    }
                }
            }

            // Resources links
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 100.dp)
                        .shadow(2.dp, RoundedCornerShape(12.dp)),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = SurfaceContainerHigh)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Folder, contentDescription = null, tint = Primary)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Resources", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold, color = Primary)
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                        ResourceRowItem(title = "DOM Cheat Sheet", icon = Icons.Default.PictureAsPdf, tint = Error)
                        ResourceRowItem(title = "MDN Docs - DOM", icon = Icons.Default.Link, tint = Primary)
                    }
                }
            }
        }

        // Persistent Lesson Navigator bottom bar
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .shadow(12.dp, RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
                color = SurfaceContainerLowest,
                shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
            ) {
                Row(
                    modifier = Modifier
                        .navigationBarsPadding()
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 14.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedButton(
                        onClick = { viewModel.navigateBack() },
                        border = BorderStroke(1.5.dp, Secondary),
                        shape = RoundedCornerShape(20.dp),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = Secondary)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBackIos, contentDescription = null, modifier = Modifier.size(12.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Prev", fontWeight = FontWeight.Bold)
                        }
                    }

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            Box(modifier = Modifier.size(12.dp, 4.dp).clip(CircleShape).background(Primary))
                            Box(modifier = Modifier.size(12.dp, 4.dp).clip(CircleShape).background(Primary))
                            Box(modifier = Modifier.size(12.dp, 4.dp).clip(CircleShape).background(Secondary))
                            Box(modifier = Modifier.size(12.dp, 4.dp).clip(CircleShape).background(SurfaceVariant))
                        }
                        Spacer(modifier = Modifier.height(2.dp))
                        Text("Lesson 3 of 12", fontSize = 10.sp, color = OnSurfaceVariant, fontWeight = FontWeight.Bold)
                    }

                    Button(
                        onClick = { viewModel.completeQuiz(80) }, // Triggers final quiz rewards screen!
                        colors = ButtonDefaults.buttonColors(containerColor = Secondary),
                        shape = RoundedCornerShape(20.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("Next", color = Color.White, fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.width(4.dp))
                            Icon(Icons.AutoMirrored.Filled.ArrowForwardIos, contentDescription = null, tint = Color.White, modifier = Modifier.size(12.dp))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ResourceRowItem(title: String, icon: ImageVector, tint: Color) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { }
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(icon, contentDescription = null, tint = tint, modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(12.dp))
            Text(title, style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold)
        }
        Icon(Icons.Default.Download, contentDescription = "Download Resource", tint = Outline, modifier = Modifier.size(16.dp))
    }
}

// 6. Quiz Results Screen (Celebration score details & review)
@Composable
fun QuizResultsScreen(viewModel: DisharaViewModel) {
    val lastResult by viewModel.lastQuizResult.collectAsState()
    val isEnglish = true

    Scaffold(
        topBar = {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = SurfaceContainerLowest,
                shadowElevation = 1.dp
            ) {
                Row(
                    modifier = Modifier
                        .statusBarsPadding()
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { viewModel.navigateTo(AppScreen.HOME) }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Primary)
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Quiz Results",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = Primary
                    )
                }
            }
        },
        bottomBar = { BottomNavBar(selectedScreen = AppScreen.LEARN_COURSES, onTabSelected = { viewModel.navigateTo(it) }) }
    ) { innerPadding ->
        if (lastResult == null) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("No quiz results found.", style = MaterialTheme.typography.bodyMedium)
            }
        } else {
            val result = lastResult!!
            val scorePercent = (result.correctCount * 100) / result.totalCount

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Background)
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                // Celebration / Performance ring
                Box(
                    modifier = Modifier.size(180.dp),
                    contentAlignment = Alignment.Center
                ) {
                    ProgressRing(progress = scorePercent / 100f, modifier = Modifier.fillMaxSize())
                    Text(
                        text = if (result.passed) "🎉" else "😢",
                        fontSize = 40.sp,
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .offset(x = 10.dp, y = (-10).dp)
                    )
                }

                Text(
                    text = "You scored ${result.correctCount}/${result.totalCount}!",
                    style = MaterialTheme.typography.headlineMedium,
                    color = if (result.passed) Color(0xFF2E7D32) else Error,
                    fontWeight = FontWeight.Bold
                )

                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(if (result.passed) Color(0xFFE8F5E9) else Color(0xFFFFEBEE))
                        .padding(horizontal = 24.dp, vertical = 10.dp)
                ) {
                    Text(
                        text = if (result.passed) "Passed! Well done!" else "Keep trying to pass (Need 60%)!",
                        color = if (result.passed) Color(0xFF2E7D32) else Color(0xFFC62828),
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold
                    )
                }

                // Attempt information block
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = SurfaceContainerLowest),
                    border = BorderStroke(1.dp, OutlineVariant.copy(alpha = 0.3f))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Quiz Details",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = Primary
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Quiz Type:", style = MaterialTheme.typography.bodySmall, color = OnSurfaceVariant)
                            Text(
                                text = if (result.isWorldQuiz) "World ${result.worldIndex} Grand Quiz" else "Lesson Practice Quiz",
                                style = MaterialTheme.typography.bodySmall,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Attempt number:", style = MaterialTheme.typography.bodySmall, color = OnSurfaceVariant)
                            Text(
                                text = "Attempt ${result.attemptNumber}",
                                style = MaterialTheme.typography.bodySmall,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }

                // If passed, display XP rewards
                if (result.passed) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(2.dp, PrimaryContainer.copy(alpha = 0.2f), RoundedCornerShape(16.dp))
                            .shadow(4.dp, RoundedCornerShape(16.dp)),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = SurfaceContainerLowest)
                    ) {
                        Row(
                            modifier = Modifier.padding(20.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(64.dp)
                                    .clip(CircleShape)
                                    .background(Brush.linearGradient(colors = listOf(Color(0xFFFFD54F), Color(0xFFFF8F00)))),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(Icons.Default.WorkspacePremium, contentDescription = null, tint = Color.White, modifier = Modifier.size(36.dp))
                            }
                            Spacer(modifier = Modifier.width(16.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = if (result.isWorldQuiz) "World ${result.worldIndex} Badge" else "Practice Completion",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = Primary
                                )
                                Text("New Reward Unlocked!", style = MaterialTheme.typography.bodySmall, color = OnSurfaceVariant)
                            }
                            Text(
                                text = if (result.isWorldQuiz) "+100 XP" else "+30 XP",
                                color = Secondary,
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }

                // IF failed and attemptNumber >= 3 -> SHOW recommended levels to review!
                if (!result.passed && result.attemptNumber >= 3) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(1.5.dp, Color(0xFFF57F17).copy(alpha = 0.3f), RoundedCornerShape(16.dp)),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFDE7))
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.MenuBook, contentDescription = null, tint = Color(0xFFF57F17))
                                Spacer(modifier = Modifier.width(12.dp))
                                Text(
                                    text = "Recommended Review Levels",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFFE65100)
                                )
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Since you have completed 3 or more attempts, we highly recommend going back and reviewing these core levels before retaking the quiz again:",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color(0xFF5D4037)
                            )
                            Spacer(modifier = Modifier.height(12.dp))

                            // Fetch lessons for the current world to display as quick review shortcuts
                            val activeBatchId = "udaan"
                            val worldModule = com.example.data.CurriculumProvider.getModulesForBatch(activeBatchId)
                                .find { it.id.startsWith("world_${result.worldIndex}") }
                            
                            worldModule?.lessons?.take(4)?.forEach { lesson ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 4.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(Color.White)
                                        .clickable { viewModel.selectLesson(lesson.id) }
                                        .padding(horizontal = 12.dp, vertical = 8.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                                        Icon(Icons.Default.PlayCircle, contentDescription = null, tint = Primary, modifier = Modifier.size(20.dp))
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(
                                            text = lesson.titleEn,
                                            style = MaterialTheme.typography.bodySmall,
                                            fontWeight = FontWeight.Bold,
                                            color = OnSurface,
                                            maxLines = 1
                                        )
                                    }
                                    Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "Open", tint = Outline, modifier = Modifier.size(16.dp))
                                }
                            }
                        }
                    }
                }

                // Action buttons
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (!result.passed) {
                        Button(
                            onClick = { viewModel.resetQuizAttempts() },
                            colors = ButtonDefaults.buttonColors(containerColor = Primary),
                            modifier = Modifier
                                .fillMaxWidth(0.9f)
                                .height(54.dp),
                            shape = RoundedCornerShape(27.dp)
                        ) {
                            Text("Retry Quiz", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        }
                    } else {
                        Button(
                            onClick = { viewModel.navigateTo(AppScreen.HOME) },
                            colors = ButtonDefaults.buttonColors(containerColor = Primary),
                            modifier = Modifier
                                .fillMaxWidth(0.9f)
                                .height(54.dp),
                            shape = RoundedCornerShape(27.dp)
                        ) {
                            Text("Go to Dashboard", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        }
                    }

                    OutlinedButton(
                        onClick = { viewModel.navigateTo(AppScreen.LEARN_COURSES) },
                        modifier = Modifier
                            .fillMaxWidth(0.9f)
                            .height(54.dp),
                        border = BorderStroke(2.dp, Secondary),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = Secondary),
                        shape = RoundedCornerShape(27.dp)
                    ) {
                        Text("Back to Course", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Review answers accordion
                Text(
                    text = "Review Answers & Explanations",
                    style = MaterialTheme.typography.titleLarge,
                    color = OnSurface,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth()
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 32.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    result.questions.forEachIndexed { qIndex, question ->
                        val userAnsIndex = result.userAnswers[qIndex]
                        val isCorrect = userAnsIndex == question.correctIndex
                        val userAnsText = userAnsIndex?.let { question.options.getOrNull(it) } ?: "Not Answered"
                        val correctAnsText = question.options[question.correctIndex]

                        QuizReviewItem(
                            question = "Q${qIndex + 1}. ${question.text}",
                            correct = isCorrect,
                            answerText = correctAnsText,
                            userAns = userAnsText,
                            correctAns = correctAnsText,
                            explanation = question.explanation
                        )
                    }
                }
            }
        }
    }
}

// 7. Advanced Interactive Quiz Player Screen
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizPlayerScreen(viewModel: DisharaViewModel) {
    val questions by viewModel.currentQuizQuestions.collectAsState()
    val currentIndex by viewModel.currentQuestionIndex.collectAsState()
    val selectedAnswers by viewModel.selectedAnswers.collectAsState()
    val isWorldQuiz by viewModel.isWorldQuiz.collectAsState()
    val worldIndex by viewModel.quizWorldIndex.collectAsState()
    val attempts by viewModel.quizAttempts.collectAsState()
    val studyMode by viewModel.quizStudyMode.collectAsState()
    val lessonId by viewModel.quizLessonId.collectAsState()

    val quizKey = if (isWorldQuiz) "world_$worldIndex" else "lesson_$lessonId"
    val attemptNumber = attempts[quizKey] ?: 1

    Scaffold(
        topBar = {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = SurfaceContainerLowest,
                shadowElevation = 1.dp
            ) {
                Row(
                    modifier = Modifier
                        .statusBarsPadding()
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { viewModel.navigateBack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Primary)
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (isWorldQuiz) "World $worldIndex Grand Quiz" else "Lesson Practice Quiz",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Primary
                    )
                }
            }
        }
    ) { innerPadding ->
        if (studyMode) {
            // Study Mode: Show all correct answers and explanations first
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Background)
                    .padding(innerPadding)
                    .padding(horizontal = 20.dp)
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = PrimaryContainer.copy(alpha = 0.15f))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.MenuBook, contentDescription = null, tint = Primary, modifier = Modifier.size(24.dp))
                            Spacer(modifier = Modifier.width(12.dp))
                            Text("Attempt $attemptNumber Study Session", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = Primary)
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            "Since this is your 3rd attempt or higher, please review the correct answers below carefully to learn the concepts before starting your quiz retake!",
                            style = MaterialTheme.typography.bodySmall,
                            color = OnSurfaceVariant
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                com.example.ui.screens.LazyColumnWithItems(
                    questions = questions,
                    modifier = Modifier.weight(1f)
                ) { qIndex, question ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp),
                        colors = CardDefaults.cardColors(containerColor = SurfaceContainerLowest),
                        border = BorderStroke(1.dp, OutlineVariant.copy(alpha = 0.5f))
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "Q${qIndex + 1}. ${question.text}",
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Bold,
                                color = OnSurface
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            
                            question.options.forEachIndexed { oIndex, option ->
                                val isCorrect = oIndex == question.correctIndex
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(if (isCorrect) Color(0xFFE8F5E9) else Color.Transparent)
                                        .padding(8.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    if (isCorrect) {
                                        Icon(
                                            imageVector = Icons.Default.CheckCircle,
                                            contentDescription = null,
                                            tint = Color(0xFF2E7D32),
                                            modifier = Modifier.size(20.dp)
                                        )
                                    } else {
                                        Box(
                                            modifier = Modifier
                                                .size(20.dp)
                                                .border(1.5.dp, Outline, CircleShape)
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Text(
                                        text = option,
                                        color = if (isCorrect) Color(0xFF1B5E20) else OnSurface,
                                        fontWeight = if (isCorrect) FontWeight.Bold else FontWeight.Normal,
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                }
                            }
                            
                            Spacer(modifier = Modifier.height(12.dp))
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(SecondaryContainer.copy(alpha = 0.2f))
                                    .padding(10.dp)
                            ) {
                                Column {
                                    Text("Explanation:", fontWeight = FontWeight.Bold, fontSize = 11.sp, color = Secondary)
                                    Text(question.explanation, style = MaterialTheme.typography.bodySmall, color = OnSurfaceVariant)
                                }
                            }
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Button(
                    onClick = { viewModel.completeStudySession() },
                    colors = ButtonDefaults.buttonColors(containerColor = Primary),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp)
                        .padding(bottom = 12.dp),
                    shape = RoundedCornerShape(27.dp)
                ) {
                    Text("I am Ready to Start Retake", color = Color.White, fontWeight = FontWeight.Bold)
                }
            }
        } else {
            // Quiz Active taking Mode
            if (questions.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else {
                val currentQuestion = questions[currentIndex]
                val selectedOptionIndex = selectedAnswers[currentIndex]

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Background)
                        .padding(innerPadding)
                        .padding(horizontal = 20.dp)
                ) {
                    Spacer(modifier = Modifier.height(16.dp))

                    // Progress info & Attempt details
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Question ${currentIndex + 1} of ${questions.size}",
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.Bold,
                            color = OnSurfaceVariant
                        )
                        Box(
                            modifier = Modifier
                                .clip(CircleShape)
                                .background(
                                    if (attemptNumber > 1) Color(0xFFFFF9C4) else SecondaryContainer
                                )
                                .padding(horizontal = 12.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = if (attemptNumber == 1) "Attempt 1" else "Attempt $attemptNumber (20% Easier!)",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = if (attemptNumber > 1) Color(0xFFF57F17) else OnSecondaryContainer
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Smooth Linear Progress Indicator
                    LinearProgressIndicator(
                        progress = { (currentIndex + 1).toFloat() / questions.size },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(6.dp)
                            .clip(CircleShape),
                        color = Primary,
                        trackColor = OutlineVariant.copy(alpha = 0.3f)
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // Question Card
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = SurfaceContainerLowest),
                        border = BorderStroke(1.dp, OutlineVariant.copy(alpha = 0.3f))
                    ) {
                        Column(modifier = Modifier.padding(20.dp)) {
                            Text(
                                text = currentQuestion.text,
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                color = OnSurface,
                                lineHeight = 28.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // 4 Options clickable Column
                    Column(
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        currentQuestion.options.forEachIndexed { oIndex, option ->
                            val isSelected = selectedOptionIndex == oIndex
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { viewModel.selectQuizAnswer(currentIndex, oIndex) },
                                colors = CardDefaults.cardColors(
                                    containerColor = if (isSelected) PrimaryContainer.copy(alpha = 0.15f) else SurfaceContainerLowest
                                ),
                                border = BorderStroke(
                                    width = if (isSelected) 2.dp else 1.dp,
                                    color = if (isSelected) Primary else OutlineVariant.copy(alpha = 0.5f)
                                ),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    if (isSelected) {
                                        Box(
                                            modifier = Modifier
                                                .size(20.dp)
                                                .clip(CircleShape)
                                                .background(Primary),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Box(
                                                modifier = Modifier
                                                    .size(8.dp)
                                                    .clip(CircleShape)
                                                    .background(Color.White)
                                            )
                                        }
                                    } else {
                                        Box(
                                            modifier = Modifier
                                                .size(20.dp)
                                                .border(1.5.dp, Outline, CircleShape)
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(12.dp))
                                    Text(
                                        text = option,
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = if (isSelected) Primary else OnSurface,
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                    )
                                }
                            }
                        }
                    }

                    // Navigation Actions
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 24.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            onClick = { viewModel.previousQuizQuestion() },
                            enabled = currentIndex > 0
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Previous Question",
                                tint = if (currentIndex > 0) Primary else Outline.copy(alpha = 0.5f)
                            )
                        }

                        val isLast = currentIndex == questions.size - 1
                        val hasSelected = selectedOptionIndex != null

                        Button(
                            onClick = {
                                if (isLast) {
                                    viewModel.submitQuiz()
                                } else {
                                    viewModel.nextQuizQuestion()
                                }
                            },
                            enabled = hasSelected,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (isLast) Secondary else Primary
                            ),
                            shape = RoundedCornerShape(20.dp),
                            modifier = Modifier.height(44.dp)
                        ) {
                            Text(
                                text = if (isLast) "Submit Quiz" else "Next Question",
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun LazyColumnWithItems(
    questions: List<QuizQuestion>,
    modifier: Modifier = Modifier,
    content: @Composable (Int, QuizQuestion) -> Unit
) {
    Column(
        modifier = modifier.verticalScroll(rememberScrollState())
    ) {
        questions.forEachIndexed { index, question ->
            content(index, question)
        }
    }
}

@Composable
fun QuizReviewItem(question: String = "", titleHtml: String = "", correct: Boolean, answerText: String = "", userAns: String = "", correctAns: String = "", explanation: String) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(2.dp, RoundedCornerShape(12.dp)),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceContainerLowest)
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expanded = !expanded }
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = if (correct) Icons.Default.CheckCircle else Icons.Default.Cancel,
                        contentDescription = null,
                        tint = if (correct) Color(0xFF4CAF50) else Error,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = question.ifEmpty { titleHtml },
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
                Icon(imageVector = if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore, contentDescription = null, tint = Outline)
            }

            AnimatedVisibility(visible = expanded) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Surface)
                        .padding(16.dp)
                ) {
                    if (correct) {
                        Text("आपका उत्तर:", fontSize = 11.sp, color = OnSurfaceVariant)
                        Text(answerText, fontWeight = FontWeight.Bold, color = OnSurface, style = MaterialTheme.typography.bodyMedium)
                    } else {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text("आपका उत्तर:", fontSize = 11.sp, color = Error)
                                Text(userAns, fontWeight = FontWeight.Bold, color = Error, style = MaterialTheme.typography.bodyMedium)
                            }
                            Column(modifier = Modifier.weight(1f)) {
                                Text("सही उत्तर:", fontSize = 11.sp, color = Color(0xFF2E7D32))
                                Text(correctAns, fontWeight = FontWeight.Bold, color = Color(0xFF2E7D32), style = MaterialTheme.typography.bodyMedium)
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Text("व्याख्या:", fontSize = 11.sp, color = OnSurfaceVariant, fontWeight = FontWeight.Bold)
                    Text(explanation, style = MaterialTheme.typography.bodySmall, color = OnSurfaceVariant)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BatchDetailsScreen(viewModel: DisharaViewModel) {
    val tempState by viewModel.tempProfile.collectAsState()
    var showEnrollSuccessDialog by remember { mutableStateOf<BatchInfo?>(null) }
    val dbProgress by viewModel.dbProgressList.collectAsState()
    val completedCount = dbProgress.filter { it.completed }.size

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("बैच विवरण / Batches", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { viewModel.navigateTo(AppScreen.HOME) }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = SurfaceContainerLowest)
            )
        },
        bottomBar = {
            BottomNavBar(selectedScreen = AppScreen.HOME, onTabSelected = { viewModel.navigateTo(it) })
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Background)
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Header Intro Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = SurfaceContainerLowest),
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.dp, OutlineVariant.copy(alpha = 0.4f))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "सही मार्ग चुनें / Choose Your Path",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Primary
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "दिशा में प्रत्येक बैच को आपके अकादमिक स्तर और करियर के लक्ष्यों के अनुसार सावधानीपूर्वक डिज़ाइन किया गया है।\n\nEach batch in Dishara is meticulously designed to align with your academic level and professional career goals.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = OnSurfaceVariant
                    )
                }
            }

            // Batches List
            AvailableBatches.forEach { batch ->
                val isEnrolled = tempState.enrolledBatchId == batch.id

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(if (isEnrolled) 6.dp else 2.dp, RoundedCornerShape(16.dp))
                        .border(
                            width = if (isEnrolled) 2.dp else 1.dp,
                            color = if (isEnrolled) Secondary else OutlineVariant.copy(alpha = 0.4f),
                            shape = RoundedCornerShape(16.dp)
                        ),
                    colors = CardDefaults.cardColors(containerColor = SurfaceContainerLowest),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column {
                        // Card Header with dynamic style
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(if (isEnrolled) SunriseGradient else Brush.linearGradient(listOf(Primary.copy(alpha = 0.08f), Primary.copy(alpha = 0.12f))))
                                .padding(16.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = "${batch.nameHi} / ${batch.nameEn}",
                                        style = MaterialTheme.typography.titleLarge,
                                        fontWeight = FontWeight.Bold,
                                        color = if (isEnrolled) Color.White else Primary
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = "${batch.gradeHi} / ${batch.gradeEn}",
                                        style = MaterialTheme.typography.labelLarge,
                                        fontWeight = FontWeight.SemiBold,
                                        color = if (isEnrolled) Color.White.copy(alpha = 0.9f) else OnSurfaceVariant
                                    )
                                }
                                if (isEnrolled) {
                                    Box(
                                        modifier = Modifier
                                            .clip(CircleShape)
                                            .background(Color.White)
                                            .padding(horizontal = 12.dp, vertical = 4.dp)
                                    ) {
                                        Text(
                                            text = "Active / सक्रिय",
                                            color = Secondary,
                                            style = MaterialTheme.typography.labelMedium,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }
                            }
                        }

                        // Card Body
                        Column(modifier = Modifier.padding(16.dp)) {
                            // Descriptions
                            Text(text = batch.descriptionHi, style = MaterialTheme.typography.bodyMedium, color = OnSurface)
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(text = batch.descriptionEn, style = MaterialTheme.typography.bodySmall, color = OnSurfaceVariant)

                            Spacer(modifier = Modifier.height(16.dp))
                            HorizontalDivider(color = OutlineVariant.copy(alpha = 0.4f))
                            Spacer(modifier = Modifier.height(16.dp))

                            // Stats Row
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                                    Icon(Icons.Default.CalendarToday, contentDescription = null, tint = Secondary, modifier = Modifier.size(18.dp))
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = "${batch.durationMonths} Months",
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                                    Icon(Icons.Default.Layers, contentDescription = null, tint = Secondary, modifier = Modifier.size(18.dp))
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = "${batch.modulesCount} Modules",
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = "What you will learn / आप क्या सीखेंगे:",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = Primary
                            )
                            Spacer(modifier = Modifier.height(8.dp))

                            // Topics list
                            batch.topics.forEach { topic ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 4.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Check,
                                        contentDescription = null,
                                        tint = Color(0xFF4CAF50),
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = topic,
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = OnSurface
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(20.dp))

                            // Enrollment button
                            Button(
                                onClick = {
                                    if (!isEnrolled) {
                                        viewModel.enrollInBatch(batch.id)
                                        showEnrollSuccessDialog = batch
                                    }
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (isEnrolled) SurfaceContainerHigh else Color.Transparent
                                ),
                                enabled = !isEnrolled,
                                contentPadding = PaddingValues(),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(48.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(
                                            if (isEnrolled) Brush.linearGradient(listOf(Color.Transparent, Color.Transparent))
                                            else SunriseGradient,
                                            RoundedCornerShape(24.dp)
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = if (isEnrolled) "Currently Enrolled / नामांकित" else "Enroll in Batch / बैच में शामिल हों",
                                        color = if (isEnrolled) OnSurfaceVariant else Color.White,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    // Congratulations Dialog on successful enrollment
    showEnrollSuccessDialog?.let { batch ->
        AlertDialog(
            onDismissRequest = {
                showEnrollSuccessDialog = null
                viewModel.navigateTo(AppScreen.HOME)
            },
            icon = { Text("🎉", fontSize = 48.sp) },
            title = {
                Text(
                    text = "बधाई हो! / Congratulations!",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            },
            text = {
                Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "आप सफलतापूर्वक ${batch.nameHi} में शामिल हो गए हैं।",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "You have successfully enrolled in ${batch.nameEn}!",
                        style = MaterialTheme.typography.bodySmall,
                        color = OnSurfaceVariant,
                        textAlign = TextAlign.Center
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        showEnrollSuccessDialog = null
                        viewModel.navigateTo(AppScreen.HOME)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Secondary)
                ) {
                    Text("Let's Go!", fontWeight = FontWeight.Bold, color = Color.White)
                }
            }
        )
    }
}

@Composable
fun ProfileScreen(
    viewModel: DisharaViewModel,
    onOpenDownloads: () -> Unit = {},
    onOpenPrivacy: () -> Unit = {},
    onOpenTerms: () -> Unit = {},
    onOpenHelp: () -> Unit = {},
    onOpenAbout: () -> Unit = {}
) {
    val tempState by viewModel.tempProfile.collectAsState()
    val isEnglish = true
    val context = LocalContext.current
    val activeBatch = AvailableBatches.find { it.id == tempState.enrolledBatchId }

    var showEditDialog by remember { mutableStateOf(false) }
    var showGuardianDialog by remember { mutableStateOf(false) }
    var showLogoutConfirm by remember { mutableStateOf(false) }

    // Interactivity state
    var isNotificationsEnabled by remember { mutableStateOf(true) }
    var studyReminderTime by remember { mutableStateOf("8:00 PM") }
    var isGuardianLinked by remember { mutableStateOf(true) }

    Scaffold(
        bottomBar = {
            BottomNavBar(
                selectedScreen = AppScreen.PROFILE,
                onTabSelected = { viewModel.navigateTo(it) }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Background)
                .padding(innerPadding)
                .testTag("profile_screen_content")
        ) {
            // Header Row
            item {
                Row(
                    modifier = Modifier
                        .statusBarsPadding()
                        .fillMaxWidth()
                        .background(SurfaceContainerLowest)
                        .padding(horizontal = 20.dp, vertical = 14.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        IconButton(
                            onClick = { viewModel.setDrawerOpen(true) },
                            modifier = Modifier.size(44.dp).testTag("profile_drawer_menu")
                        ) {
                            Icon(Icons.Default.Menu, contentDescription = "Menu", tint = Primary)
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = if (isEnglish) "👤 Profile" else "👤 प्रोफाइल",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = Primary
                        )
                    }
                    IconButton(
                        onClick = { showEditDialog = true },
                        modifier = Modifier.size(44.dp).testTag("profile_edit_button")
                    ) {
                        Text("✏️", fontSize = 20.sp)
                    }
                }
                HorizontalDivider(color = OutlineVariant.copy(alpha = 0.5f))
            }

            // Profile Card
            item {
                Spacer(modifier = Modifier.height(16.dp))
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .shadow(4.dp, RoundedCornerShape(24.dp))
                        .testTag("profile_info_card"),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = SurfaceContainerLowest),
                    border = BorderStroke(1.dp, OutlineVariant.copy(alpha = 0.5f))
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Avatar
                        Box(
                            modifier = Modifier
                                .size(96.dp)
                                .clip(CircleShape)
                                .border(3.dp, SecondaryContainer, CircleShape)
                        ) {
                            AsyncImage(
                                model = AvatarUrls.getOrNull(tempState.avatarIndex) ?: AvatarUrls[0],
                                contentDescription = "Avatar",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        }
                        Spacer(modifier = Modifier.height(16.dp))

                        // Name
                        Text(
                            text = tempState.name.ifEmpty { if (isEnglish) "Student" else "विद्यार्थी" },
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            color = OnSurface,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(4.dp))

                        // Grade • Batch Info
                        val batchName = if (isEnglish) {
                            activeBatch?.nameEn ?: "Udaan"
                        } else {
                            activeBatch?.nameHi ?: "उड़ान"
                        }
                        Text(
                            text = if (isEnglish) "Grade ${tempState.grade.replace("Grade ", "")}  •  $batchName  🟣" else "कक्षा ${tempState.grade.replace("Grade ", "")}  •  $batchName  🟣",
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Medium,
                            color = Primary,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(4.dp))

                        // School name, District
                        val schoolVal = tempState.schoolName.ifEmpty { "Sarvodaya Vidyalaya" }
                        val districtVal = tempState.district.ifEmpty { "Sitapur" }
                        Text(
                            text = "$schoolVal, $districtVal",
                            style = MaterialTheme.typography.bodyMedium,
                            color = OnSurfaceVariant,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(16.dp))

                        // Stats Row (XP and Streak)
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(Color(0xFFFFF9C4)) // subtle gold
                                    .padding(horizontal = 14.dp, vertical = 8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text("⭐", fontSize = 16.sp)
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "${tempState.totalXp} XP",
                                    style = MaterialTheme.typography.labelLarge,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFFF57F17)
                                )
                            }
                            Spacer(modifier = Modifier.width(16.dp))
                            Row(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(Color(0xFFFFE0B2)) // Orange
                                    .padding(horizontal = 14.dp, vertical = 8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text("🔥", fontSize = 16.sp)
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = if (isEnglish) "${tempState.currentStreak} days" else "${tempState.currentStreak} दिन",
                                    style = MaterialTheme.typography.labelLarge,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFFE65100)
                                )
                            }
                        }
                    }
                }
            }

            // Wishlist Section
            item {
                Spacer(modifier = Modifier.height(24.dp))
                Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("💼", fontSize = 20.sp)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = if (isEnglish) "My Career Wishlist" else "मेरी करियर विशलिस्ट",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = Primary
                            )
                        }
                        TextButton(
                            onClick = {
                                Toast.makeText(
                                    context,
                                    if (isEnglish) "More careers coming soon!" else "जल्द ही और करियर आ रहे हैं!",
                                    Toast.LENGTH_SHORT
                                ).show()
                            },
                            modifier = Modifier.testTag("wishlist_see_all")
                        ) {
                            Text(
                                text = if (isEnglish) "See All →" else "सभी देखें →",
                                style = MaterialTheme.typography.labelLarge,
                                color = Secondary,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    val finalInterests = tempState.selectedInterests.ifEmpty {
                        listOf("Design", "Computers", "Math")
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        finalInterests.forEach { interest ->
                            val careerPair = getCareerInfo(interest, isEnglish)
                            Card(
                                modifier = Modifier
                                    .width(115.dp)
                                    .height(110.dp)
                                    .testTag("wishlist_card_$interest"),
                                shape = RoundedCornerShape(16.dp),
                                colors = CardDefaults.cardColors(containerColor = SurfaceContainerLowest),
                                border = BorderStroke(1.dp, OutlineVariant.copy(alpha = 0.5f))
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(12.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Text(careerPair.first, fontSize = 28.sp)
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(
                                        text = careerPair.second,
                                        style = MaterialTheme.typography.labelLarge,
                                        fontWeight = FontWeight.Bold,
                                        color = OnSurface,
                                        textAlign = TextAlign.Center,
                                        maxLines = 2,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Settings divider line
            item {
                Spacer(modifier = Modifier.height(24.dp))
                HorizontalDivider(modifier = Modifier.padding(horizontal = 20.dp), color = OutlineVariant.copy(alpha = 0.5f))
            }

            // Notifications
            item {
                Box(modifier = Modifier.padding(horizontal = 20.dp)) {
                    ProfileSettingRow(
                        icon = "🔔",
                        title = if (isEnglish) "Notifications" else "सूचनाएं / नोटिफिकेशन",
                        valueText = if (isNotificationsEnabled) (if (isEnglish) "On" else "चालू") else (if (isEnglish) "Off" else "बंद"),
                        onClick = {
                            isNotificationsEnabled = !isNotificationsEnabled
                            Toast.makeText(
                                context,
                                if (isEnglish) {
                                    if (isNotificationsEnabled) "Notifications enabled" else "Notifications disabled"
                                } else {
                                    if (isNotificationsEnabled) "नोटिफिकेशन चालू किया गया" else "नोटिफिकेशन बंद किया गया"
                                },
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    )
                }
            }

            // Study Reminder Time
            item {
                Box(modifier = Modifier.padding(horizontal = 20.dp)) {
                    ProfileSettingRow(
                        icon = "⏰",
                        title = if (isEnglish) "Study Reminder Time" else "अध्ययन अनुस्मारक समय",
                        valueText = studyReminderTime,
                        onClick = {
                            val timePicker = android.app.TimePickerDialog(
                                context,
                                { _, hour, minute ->
                                    val amPm = if (hour >= 12) "PM" else "AM"
                                    val displayHour = if (hour % 12 == 0) 12 else hour % 12
                                    val displayMinute = String.format("%02d", minute)
                                    studyReminderTime = "$displayHour:$displayMinute $amPm"
                                    Toast.makeText(
                                        context,
                                        if (isEnglish) "Reminder set to $studyReminderTime" else "रिमाइंडर $studyReminderTime सेट किया गया",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                },
                                20, 0, false
                            )
                            timePicker.show()
                        }
                    )
                }
            }

            // Guardian Linking
            item {
                Box(modifier = Modifier.padding(horizontal = 20.dp)) {
                    ProfileSettingRow(
                        icon = "👨‍👩‍👧",
                        title = if (isEnglish) "Guardian Linking" else "अभिभावक लिंकिंग",
                        valueText = if (isGuardianLinked) (if (isEnglish) "Linked" else "लिंक किया गया") else (if (isEnglish) "Not Linked" else "लिंक नहीं है"),
                        onClick = { showGuardianDialog = true }
                    )
                }
            }

            // Offline Downloads
            item {
                Box(modifier = Modifier.padding(horizontal = 20.dp)) {
                    ProfileSettingRow(
                        icon = "📥",
                        title = if (isEnglish) "Offline Downloads" else "ऑफ़लाइन डाउनलोड",
                        valueText = "256 MB",
                        onClick = onOpenDownloads
                    )
                }
            }

            // Legal divider line
            item {
                HorizontalDivider(modifier = Modifier.padding(horizontal = 20.dp), color = OutlineVariant.copy(alpha = 0.5f))
            }

            // Privacy Policy
            item {
                Box(modifier = Modifier.padding(horizontal = 20.dp)) {
                    ProfileSettingRow(
                        icon = "🔒",
                        title = if (isEnglish) "Privacy Policy" else "गोपनीयता नीति",
                        onClick = onOpenPrivacy
                    )
                }
            }

            // Terms of Service
            item {
                Box(modifier = Modifier.padding(horizontal = 20.dp)) {
                    ProfileSettingRow(
                        icon = "📄",
                        title = if (isEnglish) "Terms of Service" else "सेवा की शर्तें",
                        onClick = onOpenTerms
                    )
                }
            }

            // Help & Support
            item {
                Box(modifier = Modifier.padding(horizontal = 20.dp)) {
                    ProfileSettingRow(
                        icon = "❓",
                        title = if (isEnglish) "Help & Support" else "सहायता और सहायता",
                        onClick = onOpenHelp
                    )
                }
            }

            // About Dishara
            item {
                Box(modifier = Modifier.padding(horizontal = 20.dp)) {
                    ProfileSettingRow(
                        icon = "ℹ️",
                        title = if (isEnglish) "About Dishara" else "दिशारा के बारे में",
                        onClick = onOpenAbout
                    )
                }
            }

            // Logout divider line
            item {
                HorizontalDivider(modifier = Modifier.padding(horizontal = 20.dp), color = OutlineVariant.copy(alpha = 0.5f))
            }

            // Logout
            item {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { showLogoutConfirm = true }
                        .padding(horizontal = 20.dp),
                    color = Color.Transparent
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("🚪", fontSize = 20.sp)
                        Spacer(modifier = Modifier.width(14.dp))
                        Text(
                            text = if (isEnglish) "Logout" else "लॉगआउट",
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Bold,
                            color = Color.Red
                        )
                    }
                }
            }

            // Version info
            item {
                Text(
                    text = "v1.0.0",
                    style = MaterialTheme.typography.labelSmall,
                    color = Outline,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp, bottom = 24.dp),
                    textAlign = TextAlign.Center
                )
            }
        }
    }

    // Guardian dialog
    if (showGuardianDialog) {
        AlertDialog(
            onDismissRequest = { showGuardianDialog = false },
            title = { Text(if (isEnglish) "Guardian Linking" else "अभिभावक लिंक") },
            text = {
                Column {
                    Text(
                        text = if (isEnglish) {
                            "Linking with guardian allows your parents to receive weekly study progress report SMS automatically."
                        } else {
                            "अभिभावक से लिंक करने से आपके माता-पिता को स्वचालित रूप से साप्ताहिक अध्ययन प्रगति रिपोर्ट एसएमएस प्राप्त करने की अनुमति मिलती है।"
                        },
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            },
            confirmButton = {
                Button(
                    colors = ButtonDefaults.buttonColors(containerColor = Secondary),
                    onClick = {
                        isGuardianLinked = !isGuardianLinked
                        showGuardianDialog = false
                        Toast.makeText(
                            context,
                            if (isEnglish) {
                                if (isGuardianLinked) "Guardian Account Linked Successfully" else "Guardian Account Unlinked"
                            } else {
                                if (isGuardianLinked) "अभिभावक का खाता सफलतापूर्वक लिंक हुआ" else "अभिभावक का खाता अनलिंक हुआ"
                            },
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                ) {
                    Text(
                        text = if (isGuardianLinked) {
                            (if (isEnglish) "Unlink Guardian" else "अभिभावक को अनलिंक करें")
                        } else {
                            (if (isEnglish) "Link Guardian" else "अभिभावक को लिंक करें")
                        },
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            },
            dismissButton = {
                TextButton(onClick = { showGuardianDialog = false }) {
                    Text(if (isEnglish) "Cancel" else "रद्द करें")
                }
            }
        )
    }

    // Logout Dialog
    if (showLogoutConfirm) {
        AlertDialog(
            onDismissRequest = { showLogoutConfirm = false },
            title = { Text(if (isEnglish) "Logout" else "लॉगआउट") },
            text = { Text(if (isEnglish) "Are you sure you want to logout? This will reset local onboarding progress." else "क्या आप लॉगआउट करना चाहते हैं? इससे स्थानीय ऑनबोर्डिंग प्रगति रीसेट हो जाएगी।") },
            confirmButton = {
                Button(
                    onClick = {
                        showLogoutConfirm = false
                        viewModel.logout()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                ) {
                    Text(if (isEnglish) "Logout" else "लॉगआउट", color = Color.White, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showLogoutConfirm = false }) {
                    Text(if (isEnglish) "Cancel" else "रद्द करें")
                }
            }
        )
    }

    // Edit Profile Dialog
    if (showEditDialog) {
        var editName by remember { mutableStateOf(tempState.name) }
        var editSchool by remember { mutableStateOf(tempState.schoolName) }
        var editGrade by remember { mutableStateOf(tempState.grade) }
        var editDistrict by remember { mutableStateOf(tempState.district) }
        var editState by remember { mutableStateOf(tempState.state) }
        var selectedAvatar by remember { mutableStateOf(tempState.avatarIndex) }

        AlertDialog(
            onDismissRequest = { showEditDialog = false },
            title = {
                Text(
                    text = if (isEnglish) "Edit Profile Details" else "प्रोफ़ाइल विवरण संपादित करें",
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Avatar Selection row
                    Text(
                        text = if (isEnglish) "Select Avatar" else "अवतार चुनें",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = Primary
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        AvatarUrls.forEachIndexed { idx, url ->
                            Box(
                                modifier = Modifier
                                    .size(54.dp)
                                    .clip(CircleShape)
                                    .border(
                                        width = if (selectedAvatar == idx) 3.dp else 1.dp,
                                        color = if (selectedAvatar == idx) Secondary else OutlineVariant,
                                        shape = CircleShape
                                    )
                                    .clickable { selectedAvatar = idx }
                            ) {
                                AsyncImage(
                                    model = url,
                                    contentDescription = "Avatar $idx",
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.Crop
                                )
                            }
                        }
                    }

                    // Name
                    OutlinedTextField(
                        value = editName,
                        onValueChange = { editName = it },
                        label = { Text(if (isEnglish) "Full Name" else "पूरा नाम") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    // Grade Selection
                    var expandedGrade by remember { mutableStateOf(false) }
                    Box(modifier = Modifier.fillMaxWidth()) {
                        OutlinedTextField(
                            value = editGrade,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text(if (isEnglish) "Grade" else "कक्षा") },
                            modifier = Modifier.fillMaxWidth(),
                            trailingIcon = {
                                IconButton(onClick = { expandedGrade = true }) {
                                    Icon(Icons.Default.ArrowDropDown, contentDescription = "Dropdown")
                                }
                            }
                        )
                        DropdownMenu(
                            expanded = expandedGrade,
                            onDismissRequest = { expandedGrade = false }
                        ) {
                            val grades = listOf("Grade 9", "Grade 10", "Grade 11", "Grade 12", "College & Career")
                            grades.forEach { gradeOption ->
                                DropdownMenuItem(
                                    text = { Text(gradeOption) },
                                    onClick = {
                                        editGrade = gradeOption
                                        expandedGrade = false
                                    }
                                )
                            }
                        }
                    }

                    // School Name
                    OutlinedTextField(
                        value = editSchool,
                        onValueChange = { editSchool = it },
                        label = { Text(if (isEnglish) "School Name" else "स्कूल का नाम") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    // District
                    OutlinedTextField(
                        value = editDistrict,
                        onValueChange = { editDistrict = it },
                        label = { Text(if (isEnglish) "District" else "जिला") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    // State
                    OutlinedTextField(
                        value = editState,
                        onValueChange = { editState = it },
                        label = { Text(if (isEnglish) "State" else "राज्य") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Button(
                    colors = ButtonDefaults.buttonColors(containerColor = Secondary),
                    onClick = {
                        val updated = tempState.copy(
                            name = editName,
                            schoolName = editSchool,
                            grade = editGrade,
                            district = editDistrict,
                            state = editState,
                            avatarIndex = selectedAvatar
                        )
                        viewModel.saveProfileUpdate(updated)
                        showEditDialog = false
                        Toast.makeText(
                            context,
                            if (isEnglish) "Profile Updated Successfully!" else "प्रोफ़ाइल सफलतापूर्वक अपडेट की गई!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                ) {
                    Text(if (isEnglish) "Save" else "सहेजें", fontWeight = FontWeight.Bold, color = Color.White)
                }
            },
            dismissButton = {
                TextButton(onClick = { showEditDialog = false }) {
                    Text(if (isEnglish) "Cancel" else "रद्द करें")
                }
            }
        )
    }
}

@Composable
fun ProfileSettingRow(
    icon: String,
    title: String,
    valueText: String = "",
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .testTag("setting_row_$title"),
        color = Color.Transparent
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 14.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                Text(icon, fontSize = 20.sp)
                Spacer(modifier = Modifier.width(14.dp))
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium,
                    color = OnSurface
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                if (valueText.isNotEmpty()) {
                    Text(
                        text = valueText,
                        style = MaterialTheme.typography.bodyLarge,
                        color = Secondary,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }
                Text("➜", color = Outline, fontSize = 16.sp)
            }
        }
    }
}

fun getCareerInfo(interest: String, isEnglish: Boolean): Pair<String, String> {
    return when (interest) {
        "Design" -> "🎨" to (if (isEnglish) "UX Design" else "यूएक्स डिज़ाइन")
        "Computers" -> "💻" to (if (isEnglish) "Web Dev" else "वेब डेवलपमेंट")
        "Math" -> "📊" to (if (isEnglish) "Data Analyst" else "डेटा विश्लेषक")
        "Problem Solving" -> "🧠" to (if (isEnglish) "AI Developer" else "एआई डेवलपर")
        "Building" -> "🏗️" to (if (isEnglish) "IoT Robotics" else "रोबोटिक्स")
        "Games" -> "🎮" to (if (isEnglish) "Game Dev" else "गेम डेवलपमेंट")
        "Teaching" -> "👩‍🏫" to (if (isEnglish) "Tech Educator" else "टेक शिक्षक")
        "Stories" -> "📝" to (if (isEnglish) "Tech Writer" else "तकनीकी लेखक")
        else -> "💼" to interest
    }
}
