package com.example.adoptacaterpillar.ui.screens

import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri

@Composable
fun AboutScreen() {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        // Hero
        Text(
            text = "🐛🐱",
            style = MaterialTheme.typography.displayLarge
        )

        Text(
            text = "Adopt a Cat-erpillar",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "yeah, I know it's a weird name lol",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.secondary
        )

        // The name explanation
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.tertiaryContainer
            )
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "okay so... about the name",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "There's this kinda stupid but also really cute trend on TikTok/YouTube where " +
                        "people use AI to generate cats mixed with caterpillars. " +
                        "Cat + Caterpillar = Cat-erpillar. Get it? 😅\n\n" +
                        "Honestly it's just adorable and makes zero sense, which is perfect.",
                    style = MaterialTheme.typography.bodyMedium
                )

                OutlinedButton(
                    onClick = {
                        val intent = Intent(
                            Intent.ACTION_VIEW,
                            "https://www.youtube.com/shorts/U0pXkJ_t-Fc".toUri()
                        )
                        context.startActivity(intent)
                    },
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    Icon(
                        Icons.Default.PlayArrow,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("see the weird trend here")
                }
            }
        }

        Divider()

        // What this app actually does
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            )
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = "what this app does:",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Before adopting a cat, you probably should know what you're getting into, right?\n\n" +
                        "• Learn random facts about cats (some useless, some actually useful)\n" +
                        "• Check out 60+ cat breeds with their personalities and stuff\n" +
                        "• Look at cute cat pictures because who doesn't like that\n",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        Text(
            text = "Features",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )

        SimpleFeature(
            emoji = "🎲",
            text = "Random cat facts that you'll forget in 5 minutes"
        )

        SimpleFeature(
            emoji = "🐈",
            text = "Browse cat breeds (Siamese, Persian, that one that looks angry...)"
        )

        SimpleFeature(
            emoji = "📸",
            text = "Cute cat photos to send to your friends"
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Footer
        Text(
            text = "made by someone who likes cats\nPauline (:",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun SimpleFeature(emoji: String, text: String) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.Top
        ) {
            Text(
                text = emoji,
                style = MaterialTheme.typography.headlineMedium
            )
            Text(
                text = text,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}
