package com.example.adoptacaterpillar.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil3.compose.SubcomposeAsyncImage
import coil3.request.CachePolicy
import coil3.request.ImageRequest
import com.example.adoptacaterpillar.ui.viewmodel.CatViewModel
import java.io.File
@Composable
fun RandomCatScreen(viewModel: CatViewModel) {
    val refreshKey by viewModel.refreshTrigger.collectAsState()  // <<< Utilise refreshTrigger

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        SubcomposeAsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data("https://cataas.com/cat?refresh=$refreshKey")  // <<< URL avec clé
                .diskCachePolicy(CachePolicy.DISABLED)
                .memoryCachePolicy(CachePolicy.DISABLED)
                .build(),
            loading = {
                Box(
                    modifier = Modifier.fillMaxWidth().height(400.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            },
            error = {
                Box(
                    modifier = Modifier.fillMaxWidth().height(400.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Erreur: vérifiez votre connexion")
                }
            },
            contentDescription = "Random Cat",
            modifier = Modifier.fillMaxWidth().height(400.dp),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(24.dp))
        Button(onClick = { viewModel.refreshRandomCat() }) {
            Text("Refresh Cat")
        }
    }
}