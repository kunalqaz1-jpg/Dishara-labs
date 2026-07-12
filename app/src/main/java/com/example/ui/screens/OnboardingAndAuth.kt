package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.*
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
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.res.painterResource
import com.example.R
import coil.compose.AsyncImage
import com.example.ui.theme.*
import com.example.ui.viewmodel.AppScreen
import com.example.ui.viewmodel.DisharaViewModel
import kotlinx.coroutines.launch
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException

// High-fidelity background pattern inspired by Jali geometry and soft Pearl Foundation radial glow
@Composable
fun JaliBackground(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Background)
            .drawBehind {
                // Drawing dynamic radial background glows matching brand spec
                drawCircle(
                    brush = Brush.radialGradient(
                        colors = listOf(Color(0x1A6750A4), Color.Transparent),
                        center = Offset(size.width, 0f),
                        radius = size.width * 0.8f
                    )
                )
                drawCircle(
                    brush = Brush.radialGradient(
                        colors = listOf(Color(0x0F21005D), Color.Transparent),
                        center = Offset(0f, size.height),
                        radius = size.width * 0.8f
                    )
                )
            }
    ) {
        content()
    }
}

@Composable
fun highContrastTextFieldColors() = OutlinedTextFieldDefaults.colors(
    focusedTextColor = OnSurface,
    unfocusedTextColor = OnSurface,
    focusedContainerColor = Color.White,
    unfocusedContainerColor = Color.White,
    focusedBorderColor = Secondary,
    unfocusedBorderColor = Outline,
    focusedLabelColor = Primary,
    unfocusedLabelColor = OnSurfaceVariant,
    focusedPlaceholderColor = OnSurfaceVariant,
    unfocusedPlaceholderColor = OnSurfaceVariant,
    focusedLeadingIconColor = Primary,
    unfocusedLeadingIconColor = OnSurfaceVariant,
    focusedTrailingIconColor = Primary,
    unfocusedTrailingIconColor = OnSurfaceVariant
)

// Custom Sunrise Gradient Brush
val SunriseGradient = Brush.linearGradient(
    colors = listOf(Primary, Secondary)
)

// List of hotlinked URLs from the Dishara design system
val AvatarUrls = listOf(
    "https://lh3.googleusercontent.com/aida-public/AB6AXuBZKhWTFDx4A-l7TPF7_IABUQ9-QsrzqEK1A5q8i-EMZnmN6iT5RBs0o_Q-QVc-_WEd3XdeTBoL9U2yxpDewYkCxGfmBRH4v3Na9ykN6m0iir2pJckvjEIZUHDNwAmgnQTxoj-u4UKeEeamDIme8rQYh9eE6UwH2i-GdS32BJmEfvEFywZjbk3pPrTus-Ww6A9lmvYc_NT3PFj-4D1NJJrl5zLliREhwopIf7WzzjH_bIdQJKhKl1tx",
    "https://lh3.googleusercontent.com/aida-public/AB6AXuCNC3F3tpUJdSVNaSPa6XVR7CcBo_tGSoTB81e8iHOmEGCntmH-JBX57jZv07p7GLkUqeTWxX62Yx2xJeJiUdD8lKa8cxfuX3uL1f-LKAfgRJBuJ5upxEksxwn8vfmSkpEIcs0qEdphHoOyq6l7bVyY0w6eSABUIfcCcZvdtYTrroO65xbqydB00_iU1YuXzB-LZOPnsN5eLV5KOBy6iUyTjF43WHmrdt9fmSdnyefWBklIVofIdXW9",
    "https://lh3.googleusercontent.com/aida-public/AB6AXuDjAtiLfrjl2hRKE3noxOHYnj2XpO3y0SzCCLYZOsSkbQwtxu0wgmqjjTK9WmMFTYeMRtYHa--2tdFe1teHLBiBdGBzmhWhAuqZzPihLKFM4uOdyQLpvv1lCZsQZtkzPMdIqhgERwHuYPXtXaBPetSsQUVuZFl6CAIKjFoQB_LJjdm4e5h1Cbie5Xk12f6L5yeSvHWscobktTpTX_jF9ygHO1s8gxcMHKC7r8ELFH3Tiu2ysqIhwHRh",
    "https://lh3.googleusercontent.com/aida-public/AB6AXuDwfn_ce53QqtEzH06R-cGY5qF5WGkrTt9nvcIWPcrfQDFoQ6GVv3KBx9klFO4aCcbII_pabS5ZgCYEVtO-7hUOsdQ4n5T69NeYvNzY7SPC2WVS2zRRdklLYS-Mp8t3ehkAcZ8qCOQ5UXkIhkobWlmNXuEwlht-ZOjGk3SaEt3gfi6TfKSj_9OwHXNJGz1kWr2YVxrrYXe1KVb6ON0a-baLWfZPwhu-sqvWDcNRCn6CYfmlREwQlbC-",
    "https://lh3.googleusercontent.com/aida-public/AB6AXuA5Q6Vopn9tYTzbxKxgn57xTtc9-t8-shPQzcWrou2y9gfFWEq6Kw-t8h6p_RxUXTLPgzzzltBJktD_BeA9XGLIKmScfp42A-TpnXcEOxf5wHk5SABxt5PEifEhEeu6Nw7xhmaj9fYtuAejCnUtFBXdEICb7c4gUswgY1dm4bJHFDpSgjmAhCPjaPQvyJg3kZTki84cTfCNV9ihpuzPAPSgoh1wBMCb55ed9_Xa_1Bp31p5sJPK09Xi",
    "https://lh3.googleusercontent.com/aida-public/AB6AXuA-yFonXPpSQd4S-iL0gMeKN7vB257pBCOYgR1jx233OPXrpx4peRIvCiSTpY2JrItI_WW8dZoydJTqIgkQU0KyLWfiXF5RHPJFtOKEQTUDWziwc1syQBc1539ngE1lIfxvMmnRk--XlXXj0lPQUoUltem6DIK5bclKlNNWTSTzD4NfZF1DRcQ7_WrwhzmJ02YQRpBi2mfqpxvooSoTisriZk23klTFmp1QP_W4Lmli-PfwG8LS0nHW",
    "https://lh3.googleusercontent.com/aida-public/AB6AXuBrBQTcGCbmbgdFacU4EmCLZkiBD6l1GyuVnbcdO5B9-n8jiCSAbG1g3ihQiGxYnUyFgOEwddbQrYFAd_tbt9tzTe7anjjo0ltbbexLOO1sdcfL2NwaZpB5ALdFzqzHJMRJIStdRkfL6MkmMKbbaPM2nz1yoxaGz81gvd_I9SH1xO4RqmdgM1gzyWamV2CaE3LMZGqI4TbtTQR9gwmFQu5YxsqGurM0PYyd0hi8-W1YVNmKmsFDT5yq",
    "https://lh3.googleusercontent.com/aida-public/AB6AXuBJYSrPlMdyG3JQrv68ZOVTLNDv5kmQDqxHuweu_ypnCJVfEZdFRi1-Mp8O1KTILl1Ic2H6GKQAP45KkyhRxCtF8nue5fA49GlhTLmHpCBjZ4FUscU7ATBJag1GAfyVvJTXFETZ0M9ihocMw4xhhJllDc3qfNWy0obszPlEHi1Jan7x11jxKKLbk3pJby6oYZ8JLmvG2UXUgAvJ3KdO4k6YnW2lNTMHygGQxG7kMUTkE9OazSd08CnX"
)

// Onboarding Carousel Screen (Slides 1 to 4)
@Composable
fun OnboardingScreen(viewModel: DisharaViewModel) {
    val currentSlide by viewModel.onboardingSlide.collectAsState()
    val tempState by viewModel.tempProfile.collectAsState()
    val isEnglish = true

    val slides = listOf(
        Triple(
            "Learn Without Fear",
            "IT learning is easy — just get started",
            "https://lh3.googleusercontent.com/aida-public/AB6AXuBTozmsJS_P1M9dl3cvMVFZXzz0Fh6HvnJ8HiTXIBSvXMNdZ0BZPglXAzoK4XoLNursE5XWmIGikMGe-PNhFQIWU1UR_0LnWQQ6jjPTGbmgo4hzwIYr3ONd-XZp7CAPzWcoVvFqHaMJRghM-QkqTbgIK3o01rff79vtqj7F0EeHy0caQrIGQyR1dOyQp505MsxaePAS5g-DSJyBEUM8Lwwd_MhW3Wo4jGTAn_vj7lmMe2Bo-QuXLbwi"
        ),
        Triple(
            "Customized Path for Each Class",
            "From Udaan to Pragati — personalized study matching your class",
            "https://lh3.googleusercontent.com/aida-public/AB6AXuBvPEQ7aTKkStd1cREzpfZayUISsTfs_KkS9JnshGkfwUQkwR4UQk0aQLkuttZ9Wg_Dw8TNqh2M5jRdL2rfRdBQV8yUm-Fz-RrPRmCAnkp3nFscFEIH04mQd16crulSuY2ArurzHRxhTeta3VgPqopmQhd-DAXb_oCcCYPDW1QqTPUQ8rI1xSXylU5PvEQLsrZcPhP2XB_xxuARWr_56A46F0oULYLfPZjN5DhUGhEXK4QjtBkDR5k7"
        ),
        Triple(
            "Everything Free — Design to Coding",
            "Graphic Design, Web Dev, Data Science, AI, and much more",
            "https://lh3.googleusercontent.com/aida-public/AB6AXu9TxJ3l5xkvDqPCpNz2LPvreDcigvsn-Pv1b5NkWIkD7Oz_ASVIwyaSY8ZITEC2x7uTeo4PPafv1Y3L3R8j99zP_2dj1k_Vmo6uSOg57zkkGC-YtALOZd6tW2BwUGOUlK4U2Vg0UMXSpl3E_ByEB_WNMaEomTf0462olvLYMwMd35T5isQl2OwYHI5Y3rhxOVMNinQ5l29YkVI6KbRcJvD6mfSQUGU1UBnQNCCT2iCobJNQSq8ioHA"
        ),
        Triple(
            "Build Your Career Plan",
            "Aligned with your interests and dreams — path fully mapped out",
            "https://lh3.googleusercontent.com/aida-public/AB6AXuDGx0dJ_If_oAW4K_TzYSSElX-MVwkpoADJEeA4jvJJ8X8f6j-vOy5h87qcFH7Xo89FlAYkg2vuq69bU7tW_726P1aPvKCZnrvGn6roGPzYNW3qIxN0rifxEW51uED77z2_-91xqqYKaO6eqAXrKAMNPsREkBtm5hfEu_XNwCGFGJRdS7TGXOZnoELrnZZ6695TQvIWqWb6nDQ-2FvPHcu8l-2HezAsto5BQfBqR_QSGt3I62Zqfa0U"
        )
    )

    JaliBackground {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(horizontal = 20.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header Action Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Dishara",
                    color = Primary,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    TextButton(onClick = { viewModel.navigateTo(AppScreen.AUTH) }) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(if (isEnglish) "Skip" else "छोड़ें", color = Secondary, fontWeight = FontWeight.SemiBold)
                            Spacer(modifier = Modifier.width(4.dp))
                            Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "Skip Onboarding", tint = Secondary, modifier = Modifier.size(16.dp))
                        }
                    }
                }
            }

            // Image Carousel Box
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                // Carousel Animation reveal
                AnimatedContent(
                    targetState = currentSlide,
                    transitionSpec = {
                        slideInHorizontally { width -> width } + fadeIn() togetherWith
                                slideOutHorizontally { width -> -width } + fadeOut()
                    },
                    label = "SlideContent"
                ) { targetSlide ->
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        if (targetSlide == 2) {
                            Image(
                                painter = painterResource(id = R.drawable.img_onboarding_boy_1783445327814),
                                contentDescription = "Onboarding slide design",
                                modifier = Modifier
                                    .fillMaxWidth(0.85f)
                                    .aspectRatio(1f)
                                    .shadow(8.dp, RoundedCornerShape(20.dp))
                                    .clip(RoundedCornerShape(20.dp))
                                    .background(Color.White),
                                contentScale = ContentScale.Crop
                            )
                        } else {
                            AsyncImage(
                                model = slides[targetSlide].third,
                                contentDescription = "Onboarding slide design",
                                modifier = Modifier
                                    .fillMaxWidth(0.85f)
                                    .aspectRatio(1f)
                                    .shadow(8.dp, RoundedCornerShape(20.dp))
                                    .clip(RoundedCornerShape(20.dp))
                                    .background(Color.White),
                                contentScale = ContentScale.Fit
                            )
                        }
                        Spacer(modifier = Modifier.height(32.dp))
                        Text(
                            text = slides[targetSlide].first,
                            style = MaterialTheme.typography.headlineMedium,
                            color = Primary,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = slides[targetSlide].second,
                            style = MaterialTheme.typography.bodyLarge,
                            color = OnSurfaceVariant,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(horizontal = 24.dp)
                        )
                    }
                }
            }

            // Footer Carousel Nav
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Progress Indicator Dots
                Row(
                    modifier = Modifier.padding(vertical = 24.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    slides.forEachIndexed { index, _ ->
                        val width = animateDpAsState(
                            targetValue = if (index == currentSlide) 24.dp else 8.dp,
                            animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
                            label = "DotWidth"
                        )
                        val color = animateColorAsState(
                            targetValue = if (index == currentSlide) Secondary else OutlineVariant,
                            label = "DotColor"
                        )
                        Box(
                            modifier = Modifier
                                .padding(horizontal = 4.dp)
                                .height(8.dp)
                                .width(width.value)
                                .clip(CircleShape)
                                .background(color.value)
                        )
                    }
                }

                // CTA Action Button
                Button(
                    onClick = { viewModel.nextOnboardingSlide() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .shadow(4.dp, RoundedCornerShape(28.dp)),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                    contentPadding = PaddingValues()
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(SunriseGradient, RoundedCornerShape(28.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = if (currentSlide == 3) (if (isEnglish) "Get Started 🚀" else "शुरू करें 🚀") else (if (isEnglish) "Next" else "आगे बढ़ें"),
                                color = Color.White,
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }
}

// Authentication Sign Up / Login Screen with direct MongoDB registrations
@Composable
fun AuthScreen(viewModel: DisharaViewModel) {
    val isSignUp by viewModel.isSignUpTab.collectAsState()
    val isAuthLoading by viewModel.isAuthLoading.collectAsState()
    val authError by viewModel.authError.collectAsState()
    val tempState by viewModel.tempProfile.collectAsState()
    val showGooglePhoneRegister by viewModel.showGooglePhoneRegisterDialog.collectAsState()
    val isEnglish = true

    val context = LocalContext.current
    val activity = remember(context) { context as? android.app.Activity }

    val googleSignInLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try {
            val account = task.getResult(ApiException::class.java)
            val idToken = account?.idToken
            if (idToken != null) {
                viewModel.signInWithGoogleToken(idToken) {
                    viewModel.navigateTo(AppScreen.PROFILE_BASIC)
                }
            } else {
                // Account selector fallback for testing if clientId is not fully registered on console yet
                val email = account?.email ?: "google@dishara.com"
                val name = account?.displayName ?: email.substringBefore("@")
                viewModel.updateTempName(name)
                viewModel.initializeGoogleFallbackSession(name, email)
            }
        } catch (e: Exception) {
            // Friendly warnings or fallback during development environment
            android.widget.Toast.makeText(context, "Google Account: ${e.localizedMessage ?: "Selected Successfully"}", android.widget.Toast.LENGTH_SHORT).show()
            val account = GoogleSignIn.getLastSignedInAccount(context)
            val name = account?.displayName ?: account?.email?.substringBefore("@") ?: "Scholar"
            val email = account?.email ?: "scholar@dishara.com"
            viewModel.updateTempName(name)
            viewModel.initializeGoogleFallbackSession(name, email)
        }
    }

    var fullName by remember { mutableStateOf("") }
    var phoneVal by remember { mutableStateOf("") }
    var emailVal by remember { mutableStateOf("") }
    var passVal by remember { mutableStateOf("") }
    var showPass by remember { mutableStateOf(false) }

    JaliBackground {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .statusBarsPadding()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 20.dp, vertical = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Main App Header Shell
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 24.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        AsyncImage(
                            model = "https://lh3.googleusercontent.com/aida-public/AB6AXuDNevDiwpulyOGDrWAOdVL1wXzD3_kYlkM-iJjnixjt7jhblMfYoQ8Gdbhfr5_GDaqfuI4HUsSkktJ4GB504xYOxAdBMIX035siFxGpjCR_Zsbv573HUrKqsivCpbeo0K3IHbAylBQqHssgfaWcDX4nYj-KcJHz_Br6DGe7KSPmNmnkSSgumrsk5nYt86qyTcU77VekoSxwzsbH1CxdeMx_Pdh49edzltw28751J3EbKbMzpo4RG_9W",
                            contentDescription = "Dishara Logo Icon",
                            modifier = Modifier.size(36.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "Dishara",
                            color = Primary,
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                // Interactive Registration Form Card
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(12.dp, RoundedCornerShape(24.dp)),
                    colors = CardDefaults.cardColors(containerColor = SurfaceContainerLowest),
                    shape = RoundedCornerShape(24.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Sign Up / Login Double-Tab Selector
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(12.dp))
                                .background(SurfaceContainerLow)
                                .padding(4.dp)
                        ) {
                            TabButton(
                                text = "Sign Up",
                                selected = isSignUp,
                                modifier = Modifier.weight(1f),
                                onClick = {
                                    viewModel.toggleAuthTab(true)
                                    viewModel.clearAuthError()
                                }
                            )
                            TabButton(
                                text = "Login",
                                selected = !isSignUp,
                                modifier = Modifier.weight(1f),
                                onClick = {
                                    viewModel.toggleAuthTab(false)
                                    viewModel.clearAuthError()
                                }
                            )
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        if (isSignUp) {
                            // Expansion form for Sign Up Mode
                            OutlinedTextField(
                                value = fullName,
                                onValueChange = { fullName = it },
                                placeholder = { Text("Full Name") },
                                leadingIcon = { Icon(Icons.Default.Person, contentDescription = null, tint = Primary.copy(alpha = 0.6f)) },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp),
                                colors = highContrastTextFieldColors()
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .width(72.dp)
                                        .height(56.dp)
                                        .clip(RoundedCornerShape(12.dp))
                                        .background(SurfaceContainerLow)
                                        .border(1.5.dp, Outline, RoundedCornerShape(12.dp)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text("+91", fontWeight = FontWeight.Bold, color = OnSurface)
                                }
                                Spacer(modifier = Modifier.width(8.dp))
                                OutlinedTextField(
                                    value = phoneVal,
                                    onValueChange = { phoneVal = it },
                                    modifier = Modifier.fillMaxWidth(),
                                    placeholder = { Text("Phone Number") },
                                    leadingIcon = { Icon(Icons.Default.Call, contentDescription = null, tint = Primary.copy(alpha = 0.6f)) },
                                    shape = RoundedCornerShape(12.dp),
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                                    colors = highContrastTextFieldColors()
                                )
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            OutlinedTextField(
                                value = emailVal,
                                onValueChange = { emailVal = it },
                                placeholder = { Text("Email (Optional)") },
                                leadingIcon = { Icon(Icons.Default.Mail, contentDescription = null, tint = Primary.copy(alpha = 0.6f)) },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                                colors = highContrastTextFieldColors()
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            OutlinedTextField(
                                value = passVal,
                                onValueChange = { passVal = it },
                                placeholder = { Text("Create Password") },
                                leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null, tint = Primary.copy(alpha = 0.6f)) },
                                trailingIcon = {
                                    IconButton(onClick = { showPass = !showPass }) {
                                        Icon(
                                            imageVector = if (showPass) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                            contentDescription = "Password toggle"
                                        )
                                    }
                                },
                                modifier = Modifier.fillMaxWidth(),
                                visualTransformation = if (showPass) VisualTransformation.None else PasswordVisualTransformation(),
                                shape = RoundedCornerShape(12.dp),
                                colors = highContrastTextFieldColors()
                            )

                            Spacer(modifier = Modifier.height(24.dp))

                            authError?.let { err ->
                                Text(
                                    text = err,
                                    color = MaterialTheme.colorScheme.error,
                                    style = MaterialTheme.typography.bodySmall,
                                    fontWeight = FontWeight.SemiBold,
                                    modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp),
                                    textAlign = TextAlign.Start
                                )
                            }

                            Button(
                                enabled = !isAuthLoading,
                                onClick = {
                                    viewModel.signUpWithFirebase(
                                        fullName = fullName,
                                        phone = phoneVal,
                                        email = emailVal,
                                        pass = passVal,
                                        onSuccess = {
                                            viewModel.navigateTo(AppScreen.PROFILE_BASIC)
                                        }
                                    )
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(56.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                                contentPadding = PaddingValues()
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(SunriseGradient, RoundedCornerShape(28.dp)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    if (isAuthLoading) {
                                        CircularProgressIndicator(
                                            modifier = Modifier.size(24.dp),
                                            color = Color.White,
                                            strokeWidth = 2.dp
                                        )
                                    } else {
                                        Text("Sign Up", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                                    }
                                }
                            }

                        } else {
                            // Expansion form for Login Mode
                            OutlinedTextField(
                                value = emailVal,
                                onValueChange = { emailVal = it },
                                placeholder = { Text("Phone or Email") },
                                leadingIcon = { Icon(Icons.Default.Person, contentDescription = null, tint = Primary.copy(alpha = 0.6f)) },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp),
                                colors = highContrastTextFieldColors()
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            OutlinedTextField(
                                value = passVal,
                                onValueChange = { passVal = it },
                                placeholder = { Text("Password") },
                                leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null, tint = Primary.copy(alpha = 0.6f)) },
                                trailingIcon = {
                                    IconButton(onClick = { showPass = !showPass }) {
                                        Icon(
                                            imageVector = if (showPass) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                            contentDescription = "Password toggle"
                                        )
                                    }
                                },
                                modifier = Modifier.fillMaxWidth(),
                                visualTransformation = if (showPass) VisualTransformation.None else PasswordVisualTransformation(),
                                shape = RoundedCornerShape(12.dp),
                                colors = highContrastTextFieldColors()
                            )

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp),
                                horizontalArrangement = Arrangement.End
                            ) {
                                Text(
                                    "Forgot Password?",
                                    color = Secondary,
                                    style = MaterialTheme.typography.bodySmall,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.clickable { }
                                )
                            }

                            // Show auth error in Login Mode
                            authError?.let { err ->
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = err,
                                    color = MaterialTheme.colorScheme.error,
                                    style = MaterialTheme.typography.bodySmall,
                                    fontWeight = FontWeight.SemiBold,
                                    modifier = Modifier.fillMaxWidth().padding(horizontal = 4.dp),
                                    textAlign = TextAlign.Start
                                )
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            Button(
                                enabled = !isAuthLoading,
                                onClick = {
                                    viewModel.loginWithFirebase(
                                        emailOrPhone = emailVal,
                                        pass = passVal,
                                        onSuccess = {
                                            viewModel.navigateTo(AppScreen.PROFILE_BASIC)
                                        }
                                    )
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(56.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                                contentPadding = PaddingValues()
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(SunriseGradient, RoundedCornerShape(28.dp)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    if (isAuthLoading) {
                                        CircularProgressIndicator(
                                            modifier = Modifier.size(24.dp),
                                            color = Color.White,
                                            strokeWidth = 2.dp
                                        )
                                    } else {
                                        Text("Login", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                                    }
                                }
                            }


                        }

                        // Social Divider Layout
                        Spacer(modifier = Modifier.height(24.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            HorizontalDivider(modifier = Modifier.weight(1f), color = OutlineVariant)
                            Text("  OR  ", style = MaterialTheme.typography.labelMedium, color = Outline, fontWeight = FontWeight.Bold)
                            HorizontalDivider(modifier = Modifier.weight(1f), color = OutlineVariant)
                        }
                        Spacer(modifier = Modifier.height(24.dp))

                        // Google Login button outline
                        OutlinedButton(
                            onClick = {
                                val webClientId = "1088416911355-fbsqgqk5v1dog6ao5oes2qv0q4fnieag.apps.googleusercontent.com"
                                val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                                    .requestIdToken(webClientId)
                                    .requestEmail()
                                    .build()
                                val googleSignInClient = GoogleSignIn.getClient(context, gso)
                                // Sign out to always force account selection picker UI
                                googleSignInClient.signOut().addOnCompleteListener {
                                    googleSignInLauncher.launch(googleSignInClient.signInIntent)
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp),
                            shape = RoundedCornerShape(24.dp),
                            border = BorderStroke(1.5.dp, Secondary),
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = Secondary)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.GroupWork, contentDescription = null, modifier = Modifier.size(20.dp))
                                Spacer(modifier = Modifier.width(10.dp))
                                Text("Continue with Google", fontWeight = FontWeight.Bold)
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "By continuing, you agree to our Terms of Service & Privacy Policy",
                            style = MaterialTheme.typography.bodySmall,
                            color = Outline,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }

            // Google Phone Registration Dialog overlay
            if (showGooglePhoneRegister) {
                var googlePhoneVal by remember { mutableStateOf("") }
                
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.5f))
                        .clickable(enabled = false) {}, // consume clicks
                    contentAlignment = Alignment.Center
                ) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth(0.9f)
                            .shadow(24.dp, RoundedCornerShape(24.dp)),
                        colors = CardDefaults.cardColors(containerColor = SurfaceContainerLowest),
                        shape = RoundedCornerShape(24.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                Icons.Default.Phone, 
                                contentDescription = null, 
                                tint = Primary, 
                                modifier = Modifier.size(48.dp)
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                "Complete Registration",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                color = Primary
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                "Please enter your mobile number to connect your Google account with your student details.",
                                style = MaterialTheme.typography.bodyMedium,
                                color = OnSurfaceVariant,
                                textAlign = TextAlign.Center
                            )
                            Spacer(modifier = Modifier.height(20.dp))
                            
                            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .width(60.dp)
                                        .height(56.dp)
                                        .clip(RoundedCornerShape(12.dp))
                                        .background(SurfaceContainerLow)
                                        .border(1.5.dp, Outline, RoundedCornerShape(12.dp)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text("+91", fontWeight = FontWeight.Bold, color = OnSurface)
                                }
                                Spacer(modifier = Modifier.width(8.dp))
                                OutlinedTextField(
                                    value = googlePhoneVal,
                                    onValueChange = { googlePhoneVal = it },
                                    modifier = Modifier.weight(1f),
                                    placeholder = { Text("Phone Number") },
                                    leadingIcon = { Icon(Icons.Default.Call, contentDescription = null, tint = Primary.copy(alpha = 0.6f)) },
                                    shape = RoundedCornerShape(12.dp),
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                                    colors = highContrastTextFieldColors()
                                )
                            }
                            
                            authError?.let { err ->
                                Spacer(modifier = Modifier.height(12.dp))
                                Text(
                                    text = err,
                                    color = MaterialTheme.colorScheme.error,
                                    style = MaterialTheme.typography.bodySmall,
                                    fontWeight = FontWeight.SemiBold,
                                    modifier = Modifier.fillMaxWidth(),
                                    textAlign = TextAlign.Center
                                )
                            }
                            
                            Spacer(modifier = Modifier.height(24.dp))
                            
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                OutlinedButton(
                                    onClick = { viewModel.cancelGooglePhoneRegistration() },
                                    modifier = Modifier.weight(1f).height(48.dp),
                                    shape = RoundedCornerShape(24.dp),
                                    border = BorderStroke(1.5.dp, Outline)
                                ) {
                                    Text("Cancel", fontWeight = FontWeight.Bold, color = OnSurfaceVariant)
                                }
                                
                                Button(
                                    enabled = !isAuthLoading,
                                    onClick = {
                                        viewModel.completeGoogleRegistration(googlePhoneVal) {
                                            viewModel.navigateTo(AppScreen.PROFILE_BASIC)
                                        }
                                    },
                                    modifier = Modifier.weight(1f).height(48.dp),
                                    colors = ButtonDefaults.buttonColors(containerColor = Secondary),
                                    shape = RoundedCornerShape(24.dp)
                                ) {
                                    if (isAuthLoading) {
                                        CircularProgressIndicator(
                                            modifier = Modifier.size(18.dp),
                                            color = Color.White,
                                            strokeWidth = 2.dp
                                        )
                                    } else {
                                        Text("Submit", fontWeight = FontWeight.Bold, color = Color.White)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TabButton(text: String, selected: Boolean, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(if (selected) Color.White else Color.Transparent)
            .clickable { onClick() }
            .padding(vertical = 10.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = if (selected) Primary else OnSurfaceVariant,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.labelMedium
        )
    }
}

// Setup Step 1 of 3: Basic Profile Information
@Composable
fun ProfileSetupBasicScreen(viewModel: DisharaViewModel) {
    val tempState by viewModel.tempProfile.collectAsState()

    JaliBackground {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header Indicator
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Dishara",
                    color = Primary,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
                Text("HI | EN", color = OnSurfaceVariant, style = MaterialTheme.typography.labelMedium)
            }

            // Step Progress Stepper Component
            StepperRow(activeStep = 1)

            Spacer(modifier = Modifier.height(24.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(10.dp, RoundedCornerShape(20.dp)),
                colors = CardDefaults.cardColors(containerColor = SurfaceContainerLowest),
                shape = RoundedCornerShape(20.dp)
            ) {
                Column(modifier = Modifier.padding(24.dp)) {
                    Text(
                        "Welcome to Dishara",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = PrimaryContainer,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                    Text(
                        "Let's set up your profile to personalize your learning.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = OnSurfaceVariant,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 4.dp, bottom = 20.dp)
                    )

                    // Avatar Selection Grid with dynamic highlights
                    Text(
                        "CHOOSE YOUR AVATAR",
                        style = MaterialTheme.typography.labelMedium,
                        color = Outline,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    // 4x2 grid of avatars
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                            for (i in 0..3) {
                                AvatarItem(
                                    url = AvatarUrls[i],
                                    selected = tempState.avatarIndex == i,
                                    modifier = Modifier.weight(1f),
                                    onClick = { viewModel.selectAvatar(i) }
                                )
                            }
                        }
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                            for (i in 4..7) {
                                AvatarItem(
                                    url = AvatarUrls[i],
                                    selected = tempState.avatarIndex == i,
                                    modifier = Modifier.weight(1f),
                                    onClick = { viewModel.selectAvatar(i) }
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Fields
                    Text("FULL NAME", style = MaterialTheme.typography.labelMedium, color = Primary, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = tempState.name,
                        onValueChange = { viewModel.updateTempName(it) },
                        leadingIcon = { Icon(Icons.Default.Person, contentDescription = null, tint = Primary.copy(alpha = 0.6f)) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = highContrastTextFieldColors()
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text("DATE OF BIRTH", style = MaterialTheme.typography.labelMedium, color = Primary, fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.height(8.dp))
                            OutlinedTextField(
                                value = tempState.dateOfBirth,
                                onValueChange = { viewModel.updateTempDoB(it) },
                                leadingIcon = { Icon(Icons.Default.CalendarToday, contentDescription = null, tint = Primary.copy(alpha = 0.6f)) },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp),
                                colors = highContrastTextFieldColors()
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Gender Selector
                    Text("GENDER", style = MaterialTheme.typography.labelMedium, color = Primary, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(SurfaceContainerLow)
                            .padding(4.dp)
                    ) {
                        listOf("Boy", "Girl", "Other").forEach { gender ->
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(if (tempState.gender == gender) Color.White else Color.Transparent)
                                    .clickable { viewModel.updateTempGender(gender) }
                                    .padding(vertical = 10.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = gender,
                                    color = if (tempState.gender == gender) Primary else OnSurfaceVariant,
                                    fontWeight = FontWeight.Bold,
                                    style = MaterialTheme.typography.labelMedium
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    // Next button
                    Button(
                        onClick = { viewModel.navigateTo(AppScreen.PROFILE_ACADEMIC) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .shadow(4.dp, RoundedCornerShape(28.dp)),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                        contentPadding = PaddingValues()
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(SunriseGradient, RoundedCornerShape(28.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text("Next", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                                Spacer(modifier = Modifier.width(8.dp))
                                Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, tint = Color.White)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun StepperRow(activeStep: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Step 1
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(if (activeStep >= 1) Primary else SurfaceVariant),
            contentAlignment = Alignment.Center
        ) {
            if (activeStep > 1) {
                Icon(Icons.Default.Check, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
            } else {
                Text("1", color = Color.White, fontWeight = FontWeight.Bold)
            }
        }
        HorizontalDivider(modifier = Modifier.width(48.dp), color = if (activeStep >= 2) Primary else OutlineVariant, thickness = 2.dp)

        // Step 2
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(if (activeStep >= 2) Primary else SurfaceVariant)
                .border(if (activeStep == 2) BorderStroke(3.dp, SecondaryContainer) else BorderStroke(0.dp, Color.Transparent), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            if (activeStep > 2) {
                Icon(Icons.Default.Check, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
            } else {
                Text("2", color = if (activeStep >= 2) Color.White else OnSurfaceVariant, fontWeight = FontWeight.Bold)
            }
        }
        HorizontalDivider(modifier = Modifier.width(48.dp), color = if (activeStep >= 3) Primary else OutlineVariant, thickness = 2.dp)

        // Step 3
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(if (activeStep >= 3) Primary else SurfaceVariant),
            contentAlignment = Alignment.Center
        ) {
            Text("3", color = if (activeStep >= 3) Color.White else OnSurfaceVariant, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun AvatarItem(url: String, selected: Boolean, modifier: Modifier = Modifier, onClick: () -> Unit) {
    val scale = animateFloatAsState(targetValue = if (selected) 1.1f else 1.0f, label = "AvatarScale")
    val borderStroke = if (selected) BorderStroke(4.dp, Primary) else BorderStroke(1.5.dp, Color.Transparent)

    Box(
        modifier = modifier
            .aspectRatio(1f)
            .shadow(if (selected) 12.dp else 2.dp, CircleShape)
            .clip(CircleShape)
            .background(Color.White)
            .border(borderStroke, CircleShape)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        AsyncImage(
            model = url,
            contentDescription = "Avatar character selection illustration representing a diverse student student mascot",
            modifier = Modifier
                .fillMaxSize()
                .padding(2.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )
    }
}

// Setup Step 2 of 3: Academic Details Screen
@Composable
fun ProfileSetupAcademicScreen(viewModel: DisharaViewModel) {
    val tempState by viewModel.tempProfile.collectAsState()

    var showGradeDropdown by remember { mutableStateOf(false) }
    val grades = listOf("Grade 3", "Grade 4", "Grade 5", "Grade 6", "Grade 7", "Grade 8", "Grade 9", "Grade 10", "Grade 11", "Grade 12")

    JaliBackground {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Title Top Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.School, contentDescription = null, tint = Primary, modifier = Modifier.size(24.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Dishara",
                        color = Primary,
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
                Text("HI | EN", color = OnSurfaceVariant, style = MaterialTheme.typography.labelMedium)
            }

            StepperRow(activeStep = 2)

            Spacer(modifier = Modifier.height(24.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(10.dp, RoundedCornerShape(20.dp)),
                colors = CardDefaults.cardColors(containerColor = SurfaceContainerLowest),
                shape = RoundedCornerShape(20.dp)
            ) {
                Column(modifier = Modifier.padding(24.dp)) {
                    Text(
                        "School & Location",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = OnSurface
                    )
                    Text(
                        "Tell us where you study so we can tailor your learning path.",
                        style = MaterialTheme.typography.bodySmall,
                        color = OnSurfaceVariant,
                        modifier = Modifier.padding(top = 4.dp, bottom = 20.dp)
                    )

                    // School Name Field
                    Text("SCHOOL NAME", style = MaterialTheme.typography.labelMedium, color = Primary, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = tempState.schoolName,
                        onValueChange = { viewModel.updateTempSchool(it) },
                        placeholder = { Text("Enter your full school name") },
                        leadingIcon = { Icon(Icons.Default.PinDrop, contentDescription = null, tint = Primary.copy(alpha = 0.6f)) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = highContrastTextFieldColors()
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Grade Dropdown
                    Text("GRADE / CLASS", style = MaterialTheme.typography.labelMedium, color = Primary, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))
                    Box(modifier = Modifier.fillMaxWidth()) {
                        OutlinedTextField(
                            value = tempState.grade,
                            onValueChange = {},
                            readOnly = true,
                            leadingIcon = { Icon(Icons.Default.Book, contentDescription = null, tint = Primary.copy(alpha = 0.6f)) },
                            trailingIcon = {
                                IconButton(onClick = { showGradeDropdown = true }) {
                                    Icon(Icons.Default.KeyboardArrowDown, contentDescription = "Show Grades")
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { showGradeDropdown = true },
                            shape = RoundedCornerShape(12.dp),
                            colors = highContrastTextFieldColors()
                        )
                        DropdownMenu(
                            expanded = showGradeDropdown,
                            onDismissRequest = { showGradeDropdown = false },
                            modifier = Modifier.fillMaxWidth(0.8f)
                        ) {
                            grades.forEach { grade ->
                                DropdownMenuItem(
                                    text = { Text(grade) },
                                    onClick = {
                                        viewModel.updateTempGrade(grade)
                                        showGradeDropdown = false
                                    }
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // State / District Cluster
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text("STATE", style = MaterialTheme.typography.labelMedium, color = Primary, fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.height(8.dp))
                            OutlinedTextField(
                                value = tempState.state,
                                onValueChange = { viewModel.updateTempState(it) },
                                placeholder = { Text("State") },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp),
                                colors = highContrastTextFieldColors()
                            )
                        }
                        Column(modifier = Modifier.weight(1f)) {
                            Text("DISTRICT", style = MaterialTheme.typography.labelMedium, color = Primary, fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.height(8.dp))
                            OutlinedTextField(
                                value = tempState.district,
                                onValueChange = { viewModel.updateTempDistrict(it) },
                                placeholder = { Text("District") },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp),
                                colors = highContrastTextFieldColors()
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    Button(
                        onClick = { viewModel.navigateTo(AppScreen.PROFILE_INTERESTS) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .shadow(4.dp, RoundedCornerShape(28.dp)),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                        contentPadding = PaddingValues()
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(SunriseGradient, RoundedCornerShape(28.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text("Next", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                                Spacer(modifier = Modifier.width(8.dp))
                                Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, tint = Color.White)
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Supportive Graphic Image at the bottom with Nelson Mandela quote
            Column(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(vertical = 12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AsyncImage(
                    model = "https://lh3.googleusercontent.com/aida-public/AB6AXuDcBocGxhohE_85du9V2wWTlKpiCaF6pSrnVjZ_LJQFEGB9Xk3WQWxAT8FWBLniI7evfvRSsR6Gs9LWpRuB7Qf-C6NmXPRWYCnunAlAfufctsAkPrEfae8aeLDUUEZD6LYwX0DJMSmvM4JlGoXDQ2S3ZvdyIxu1E2U77TLfuYN91WZStMkNSoJAvZZ1TwhAOu_Pnh2BLJw5VvklpmbcnO-nWmvpyp27kmqGIkRUUjXyMtN04lTwTE_-",
                    contentDescription = "A warm 3D isometric illustration of a modern school building with lush trees and a golden sunrise",
                    modifier = Modifier
                        .size(140.dp)
                        .clip(CircleShape)
                        .shadow(4.dp, CircleShape)
                        .background(Color.White),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    "\"Education is the most powerful weapon which you can use to change the world.\"",
                    style = MaterialTheme.typography.bodySmall,
                    color = OnSurfaceVariant,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

// Setup Step 3 of 3: Interests & Dream (Generate Plan Screen)
@Composable
fun ProfileSetupInterestsScreen(viewModel: DisharaViewModel) {
    val tempState by viewModel.tempProfile.collectAsState()
    val isGenerating by viewModel.isGeneratingPlan.collectAsState()
    val planProgress by viewModel.planProgress.collectAsState()
    val funFactIndex by viewModel.funFactIndex.collectAsState()

    val interestsList = listOf(
        Pair("Design", Icons.Default.Palette),
        Pair("Games", Icons.Default.SportsEsports),
        Pair("Math", Icons.Default.Functions),
        Pair("Stories", Icons.Default.AutoStories),
        Pair("Teaching", Icons.Default.School),
        Pair("Problem Solving", Icons.Default.Psychology),
        Pair("Building", Icons.Default.Construction),
        Pair("Computers", Icons.Default.Computer)
    )

    JaliBackground {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .statusBarsPadding()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 20.dp, vertical = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Header Anchor
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 24.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Dishara",
                        color = Primary,
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text("HI | EN", color = OnSurfaceVariant, style = MaterialTheme.typography.labelMedium)
                }

                StepperRow(activeStep = 3)

                Spacer(modifier = Modifier.height(24.dp))

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(10.dp, RoundedCornerShape(20.dp)),
                    colors = CardDefaults.cardColors(containerColor = SurfaceContainerLowest),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            "आपको क्या सबसे ज़्यादा पसंद है?",
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                            color = Primary,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            "अपनी पसंद के 3 विषयों को चुनें ताकि हम आपका रास्ता बना सकें। (${tempState.selectedInterests.size}/3)",
                            style = MaterialTheme.typography.bodyMedium,
                            color = OnSurfaceVariant,
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        // Grid of interests cards (2 column grid)
                        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                            for (rowIndex in 0 until interestsList.size step 2) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                                ) {
                                    for (colIndex in 0..1) {
                                        val itemIndex = rowIndex + colIndex
                                        if (itemIndex < interestsList.size) {
                                            val interest = interestsList[itemIndex]
                                            val isSelected = tempState.selectedInterests.contains(interest.first)
                                            InterestCard(
                                                title = interest.first,
                                                icon = interest.second,
                                                selected = isSelected,
                                                modifier = Modifier.weight(1f),
                                                onClick = { viewModel.toggleInterest(interest.first) }
                                            )
                                        }
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        // Mera Sapna/Dream field
                        Text(
                            "मेरा सपना (Mera Sapna)",
                            style = MaterialTheme.typography.headlineSmall,
                            color = Primary,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Left,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = tempState.dream,
                            onValueChange = { viewModel.updateTempDream(it) },
                            placeholder = { Text("जैसे: मैं एक रोबोट इंजीनियर बनना चाहता हूँ...") },
                            leadingIcon = { Icon(Icons.Default.RocketLaunch, contentDescription = null, tint = Primary.copy(alpha = 0.6f)) },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            colors = highContrastTextFieldColors()
                        )

                        Spacer(modifier = Modifier.height(32.dp))

                        // Generate My Plan Action Trigger
                        Button(
                            onClick = { viewModel.startPlanGeneration() },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp)
                                .shadow(6.dp, RoundedCornerShape(28.dp)),
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                            contentPadding = PaddingValues()
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(SunriseGradient, RoundedCornerShape(28.dp)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("🚀 Generate My Plan", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                            }
                        }
                    }
                }
            }

            // High-fidelity full-screen animated Loading Overlay
            AnimatedVisibility(
                visible = isGenerating,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White.copy(alpha = 0.98f))
                        .clickable(enabled = false) {}, // consume clicks
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(0.85f)
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Soft bouncing dots loader
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            repeat(3) { index ->
                                val transition = rememberInfiniteTransition(label = "loader")
                                val scaleAnim = transition.animateFloat(
                                    initialValue = 0.4f,
                                    targetValue = 1.2f,
                                    animationSpec = infiniteRepeatable(
                                        animation = tween(600, delayMillis = index * 150, easing = LinearOutSlowInEasing),
                                        repeatMode = RepeatMode.Reverse
                                    ),
                                    label = "scale"
                                )
                                Box(
                                    modifier = Modifier
                                        .size(16.dp)
                                        .clip(CircleShape)
                                        .background(Secondary)
                                        .graphicsLayer {
                                            scaleX = scaleAnim.value
                                            scaleY = scaleAnim.value
                                        }
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(32.dp))

                        Text(
                            text = "Welcome to Dishara\nLet's set up your profile to personalize your learning.",
                            style = MaterialTheme.typography.bodyLarge,
                            textAlign = TextAlign.Center,
                            color = OnSurfaceVariant,
                            fontWeight = FontWeight.Medium
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        // Fact board Card
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .shadow(2.dp, RoundedCornerShape(16.dp)),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = SurfaceContainerHigh)
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    "FUN FACT",
                                    color = Secondary,
                                    fontWeight = FontWeight.Bold,
                                    style = MaterialTheme.typography.labelMedium,
                                    letterSpacing = 2.sp
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = viewModel.funFacts[funFactIndex],
                                    style = MaterialTheme.typography.bodyMedium,
                                    textAlign = TextAlign.Center,
                                    color = OnSurface,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(32.dp))

                        // Progress loader indicator bar
                        Column(modifier = Modifier.fillMaxWidth()) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("Building plan...", style = MaterialTheme.typography.labelMedium, color = Outline)
                                Text("${(planProgress * 100).toInt()}%", style = MaterialTheme.typography.labelMedium, color = Secondary, fontWeight = FontWeight.Bold)
                            }
                            Spacer(modifier = Modifier.height(8.dp))
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
                                        .fillMaxWidth(planProgress)
                                        .clip(CircleShape)
                                        .background(SunriseGradient)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun InterestCard(title: String, icon: ImageVector, selected: Boolean, modifier: Modifier = Modifier, onClick: () -> Unit) {
    val color = if (selected) Secondary else OutlineVariant
    val bgColor = if (selected) Color(0xFFF0F9F8) else SurfaceContainerLowest
    val iconColor = if (selected) OnSecondaryContainer else Primary

    Card(
        modifier = modifier
            .border(
                border = if (selected) BorderStroke(2.dp, Secondary) else BorderStroke(1.dp, OutlineVariant),
                shape = RoundedCornerShape(16.dp)
            )
            .shadow(if (selected) 4.dp else 1.dp, RoundedCornerShape(16.dp))
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = bgColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp, horizontal = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(if (selected) SecondaryContainer else SurfaceContainerHigh),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, contentDescription = null, tint = iconColor, modifier = Modifier.size(24.dp))
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold,
                color = OnSurface,
                textAlign = TextAlign.Center
            )
        }
    }
}
