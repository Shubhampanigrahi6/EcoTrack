
package com.superhumans.ecotrack.presentation.community.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.SmartToy
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.superhumans.ecotrack.ui.theme.*

private data class ChatMessage(
    val text: String,
    val isUser: Boolean
)

@Composable
fun ChatScreen() {
    var input by remember { mutableStateOf("") }

    val messages = remember {
        mutableStateListOf(
            ChatMessage(
                "Hey! 👋 I'm EcoBot, your sustainability assistant. Ask me anything about living greener!",
                false
            )
        )
    }

    val listState = rememberLazyListState()

    fun getBotReply(question: String): String {
        val query = question.lowercase()

        return when {
            "recycl" in query ->
                "Great question! ♻️ Separate paper, plastic, glass and metal. Clean containers before recycling and check your local recycling rules."

            "plastic" in query ->
                "Try carrying a reusable water bottle, cloth shopping bags and avoiding single-use packaging. Small changes make a big difference!"

            "transport" in query || "travel" in query ->
                "Walking, cycling and public transport are great ways to reduce your travel carbon footprint. Combine trips whenever possible! 🚲"

            "carbon" in query || "co2" in query ->
                "You can reduce CO₂ emissions by using cleaner transport, saving electricity, eating more plant-based meals and reducing waste."

            "water" in query ->
                "Save water by fixing leaks, taking shorter showers and turning off taps while brushing. 💧"

            "energy" in query || "electricity" in query ->
                "Switch off unused lights, use LED bulbs, unplug idle devices and make use of natural daylight."

            "hello" in query || "hi" in query ->
                "Hello! 🌱 What would you like to learn about sustainable living today?"

            else ->
                "That's an interesting question! 🌱 Try focusing on reducing waste, saving resources and choosing sustainable alternatives. Can you tell me a little more about what you'd like to know?"
        }
    }

    fun sendMessage() {
        val question = input.trim()
        if (question.isBlank()) return

        messages.add(ChatMessage(question, true))
        input = ""
        messages.add(ChatMessage(getBotReply(question), false))
    }

    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) {
            listState.animateScrollToItem(messages.lastIndex)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(EcoBackground)
    ) {
        Surface(
            color = EcoWhite,
            shadowElevation = 2.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.padding(18.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(46.dp)
                        .background(EcoSurfaceBlue, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Default.SmartToy,
                        contentDescription = null,
                        tint = EcoBlue,
                        modifier = Modifier.size(27.dp)
                    )
                }

                Spacer(Modifier.width(12.dp))

                Column {
                    Text(
                        "EcoBot",
                        fontSize = 19.sp,
                        fontWeight = FontWeight.Bold,
                        color = EcoNavy
                    )
                    Text(
                        "Your sustainability assistant",
                        fontSize = 11.sp,
                        color = EcoTextSecondary
                    )
                }

                Spacer(Modifier.weight(1f))

                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = EcoSurfaceBlue
                ) {
                    Text(
                        "ONLINE",
                        color = EcoSuccess,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(
                            horizontal = 10.dp,
                            vertical = 6.dp
                        )
                    )
                }
            }
        }

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            state = listState,
            contentPadding = PaddingValues(vertical = 18.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            items(messages) { message ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = if (message.isUser)
                        Arrangement.End else Arrangement.Start
                ) {
                    Surface(
                        shape = RoundedCornerShape(
                            topStart = 18.dp,
                            topEnd = 18.dp,
                            bottomStart = if (message.isUser) 18.dp else 4.dp,
                            bottomEnd = if (message.isUser) 4.dp else 18.dp
                        ),
                        color = if (message.isUser) EcoBlue else EcoWhite,
                        shadowElevation = 1.dp,
                        modifier = Modifier.widthIn(max = 300.dp)
                    ) {
                        Text(
                            text = message.text,
                            color = if (message.isUser) EcoWhite else EcoNavy,
                            fontSize = 14.sp,
                            lineHeight = 21.sp,
                            modifier = Modifier.padding(14.dp)
                        )
                    }
                }
            }
        }

        Surface(
            color = EcoWhite,
            shadowElevation = 8.dp
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .background(
                            EcoBackground,
                            RoundedCornerShape(24.dp)
                        )
                        .padding(horizontal = 16.dp, vertical = 14.dp)
                ) {
                    if (input.isEmpty()) {
                        Text(
                            "Ask EcoBot something...",
                            color = EcoTextSecondary,
                            fontSize = 13.sp
                        )
                    }

                    BasicTextField(
                        value = input,
                        onValueChange = { input = it },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        textStyle = androidx.compose.ui.text.TextStyle(
                            color = EcoNavy,
                            fontSize = 14.sp
                        )
                    )
                }

                Spacer(Modifier.width(10.dp))

                IconButton(
                    onClick = { sendMessage() },
                    enabled = input.isNotBlank(),
                    modifier = Modifier
                        .size(48.dp)
                        .background(
                            if (input.isNotBlank()) EcoBlue
                            else EcoTextLight,
                            CircleShape
                        )
                ) {
                    Icon(
                        Icons.Default.Send,
                        contentDescription = "Send message",
                        tint = EcoWhite
                    )
                }
            }
        }
    }
}
