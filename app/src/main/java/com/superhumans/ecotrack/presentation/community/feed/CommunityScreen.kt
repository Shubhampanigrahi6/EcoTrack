package com.superhumans.ecotrack.presentation.community.feed

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ChatBubbleOutline
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.superhumans.ecotrack.core.components.EcoTrackCard
import com.superhumans.ecotrack.ui.theme.EcoBackground
import com.superhumans.ecotrack.ui.theme.EcoBlue
import com.superhumans.ecotrack.ui.theme.EcoCoral
import com.superhumans.ecotrack.ui.theme.EcoNavy
import com.superhumans.ecotrack.ui.theme.EcoSurfaceBlue
import com.superhumans.ecotrack.ui.theme.EcoTextSecondary
import com.superhumans.ecotrack.ui.theme.EcoWhite

private data class CommunityPost(
    val id: Int,
    val userName: String,
    val initials: String,
    val time: String,
    val content: String,
    val likes: Int,
    val comments: List<String>,
    val likedByMe: Boolean = false
)

@Composable
fun CommunityScreen(
    onOpenChat: () -> Unit = {}
) {

    val posts = remember {
        mutableStateListOf(
            CommunityPost(
                id = 1,
                userName = "Aarav Sharma",
                initials = "AS",
                time = "15 min ago",
                content = "I chose cycling instead of driving to work today. Small steps make a big difference! 🚴",
                likes = 24,
                comments = listOf("Amazing initiative!", "Keep it up!")
            ),
            CommunityPost(
                id = 2,
                userName = "Priya Das",
                initials = "PD",
                time = "1 hour ago",
                content = "Planted two saplings in my neighbourhood today. Let's make our surroundings greener! 🌱",
                likes = 38,
                comments = listOf("Beautiful work!")
            ),
            CommunityPost(
                id = 3,
                userName = "Rahul Verma",
                initials = "RV",
                time = "3 hours ago",
                content = "Carpooled with colleagues today and reduced unnecessary fuel consumption. 🌍",
                likes = 17,
                comments = emptyList()
            )
        )
    }

    var selectedPostId by remember { mutableStateOf<Int?>(null) }
    var commentText by remember { mutableStateOf("") }
    var showCreatePost by remember { mutableStateOf(false) }
    var newPostText by remember { mutableStateOf("") }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = EcoBackground
    ) {
        Box(modifier = Modifier.fillMaxSize()) {

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = androidx.compose.foundation.layout.PaddingValues(
                    start = 20.dp,
                    end = 20.dp,
                    top = 20.dp,
                    bottom = 90.dp
                ),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Text(
                                text = "Community",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                color = EcoNavy
                            )

                            Text(
                                text = "Together, we make a difference.",
                                fontSize = 13.sp,
                                color = EcoTextSecondary
                            )
                        }

                        IconButton(
                            onClick = onOpenChat,
                            modifier = Modifier
                                .clip(CircleShape)
                                .background(EcoWhite)
                        ) {
                            Icon(
                                imageVector = Icons.Default.ChatBubbleOutline,
                                contentDescription = "Open Chat",
                                tint = EcoBlue
                            )
                        }
                    }
                }

                item {
                    CreatePostPrompt(
                        onClick = { showCreatePost = true }
                    )
                }

                item {
                    Text(
                        text = "Latest activities",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = EcoNavy
                    )
                }

                items(posts, key = { it.id }) { post ->
                    CommunityPostCard(
                        post = post,
                        onLikeClick = {
                            val index = posts.indexOfFirst { it.id == post.id }
                            if (index != -1) {
                                val current = posts[index]
                                posts[index] = current.copy(
                                    likedByMe = !current.likedByMe,
                                    likes = current.likes +
                                            if (current.likedByMe) -1 else 1
                                )
                            }
                        },
                        onCommentClick = {
                            selectedPostId = post.id
                            commentText = ""
                        }
                    )
                }
            }

            Button(
                onClick = { showCreatePost = true },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(20.dp),
                shape = RoundedCornerShape(18.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = EcoBlue
                )
            ) {
                Icon(Icons.Default.Add, contentDescription = null)
                Text(
                    text = " Create Post",
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }

    val selectedPost = posts.firstOrNull { it.id == selectedPostId }

    if (selectedPost != null) {
        AlertDialog(
            onDismissRequest = { selectedPostId = null },
            title = {
                Text("Comments", color = EcoNavy)
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    if (selectedPost.comments.isEmpty()) {
                        Text(
                            "No comments yet. Start the conversation!",
                            color = EcoTextSecondary
                        )
                    } else {
                        selectedPost.comments.forEach { comment ->
                            Text(
                                text = "• $comment",
                                color = EcoNavy,
                                fontSize = 13.sp
                            )
                        }
                    }

                    OutlinedTextField(
                        value = commentText,
                        onValueChange = { commentText = it },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text("Write a comment...") },
                        singleLine = true
                    )
                }
            },
            confirmButton = {
                TextButton(
                    enabled = commentText.isNotBlank(),
                    onClick = {
                        val index = posts.indexOfFirst {
                            it.id == selectedPost.id
                        }
                        if (index != -1) {
                            val current = posts[index]
                            posts[index] = current.copy(
                                comments = current.comments + commentText.trim()
                            )
                        }
                        commentText = ""
                        selectedPostId = null
                    }
                ) {
                    Text("Post comment", color = EcoBlue)
                }
            },
            dismissButton = {
                TextButton(onClick = { selectedPostId = null }) {
                    Text("Close")
                }
            }
        )
    }

    if (showCreatePost) {
        AlertDialog(
            onDismissRequest = { showCreatePost = false },
            title = {
                Text("Create a post", color = EcoNavy)
            },
            text = {
                OutlinedTextField(
                    value = newPostText,
                    onValueChange = { newPostText = it },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = {
                        Text("Share your sustainable action...")
                    },
                    minLines = 3
                )
            },
            confirmButton = {
                TextButton(
                    enabled = newPostText.isNotBlank(),
                    onClick = {
                        posts.add(
                            0,
                            CommunityPost(
                                id = (posts.maxOfOrNull { it.id } ?: 0) + 1,
                                userName = "Shubham",
                                initials = "SH",
                                time = "Just now",
                                content = newPostText.trim(),
                                likes = 0,
                                comments = emptyList()
                            )
                        )
                        newPostText = ""
                        showCreatePost = false
                    }
                ) {
                    Text("Publish", color = EcoBlue)
                }
            },
            dismissButton = {
                TextButton(onClick = { showCreatePost = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
private fun CreatePostPrompt(
    onClick: () -> Unit
) {
    EcoTrackCard {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(42.dp)
                    .clip(CircleShape)
                    .background(EcoSurfaceBlue),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "SH",
                    color = EcoBlue,
                    fontWeight = FontWeight.Bold
                )
            }

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(3.dp)
            ) {
                Text(
                    text = "Share your eco story",
                    color = EcoNavy,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 13.sp
                )
                Text(
                    text = "Inspire your community today",
                    color = EcoTextSecondary,
                    fontSize = 11.sp
                )
            }

            IconButton(onClick = onClick) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = "Create post",
                    tint = EcoBlue
                )
            }
        }
    }
}

@Composable
private fun CommunityPostCard(
    post: CommunityPost,
    onLikeClick: () -> Unit,
    onCommentClick: () -> Unit
) {
    EcoTrackCard {
        Column(verticalArrangement = Arrangement.spacedBy(13.dp)) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .clip(CircleShape)
                        .background(EcoSurfaceBlue),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = post.initials,
                        color = EcoBlue,
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp
                    )
                }

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 10.dp),
                    verticalArrangement = Arrangement.spacedBy(3.dp)
                ) {
                    Text(
                        text = post.userName,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = EcoNavy
                    )
                    Text(
                        text = post.time,
                        fontSize = 11.sp,
                        color = EcoTextSecondary
                    )
                }

                Icon(
                    imageVector = Icons.Default.MoreHoriz,
                    contentDescription = "More options",
                    tint = EcoTextSecondary
                )
            }

            Text(
                text = post.content,
                color = EcoNavy,
                fontSize = 13.sp,
                lineHeight = 20.sp
            )

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(EcoSurfaceBlue)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(22.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = onLikeClick,
                        modifier = Modifier.size(34.dp)
                    ) {
                        Icon(
                            imageVector = if (post.likedByMe) {
                                Icons.Default.Favorite
                            } else {
                                Icons.Default.FavoriteBorder
                            },
                            contentDescription = if (post.likedByMe) {
                                "Unlike post"
                            } else {
                                "Like post"
                            },
                            tint = if (post.likedByMe) {
                                EcoCoral
                            } else {
                                EcoTextSecondary
                            }
                        )
                    }

                    Text(
                        text = post.likes.toString(),
                        fontSize = 12.sp,
                        color = EcoTextSecondary
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = onCommentClick,
                        modifier = Modifier.size(34.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ChatBubbleOutline,
                            contentDescription = "Comments",
                            tint = EcoTextSecondary
                        )
                    }

                    Text(
                        text = post.comments.size.toString(),
                        fontSize = 12.sp,
                        color = EcoTextSecondary
                    )
                }
            }
        }
    }
}
