package com.example.adoptacaterpillar.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.SubcomposeAsyncImage
import com.example.adoptacaterpillar.ui.viewmodel.CatViewModel
import java.io.File

@Composable
fun RandomCatScreen(viewModel: CatViewModel) {
    val currentImagePath by viewModel.currentRandomCatPath.collectAsState()
    val isLoading by viewModel.isLoadingRandomCat.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (currentImagePath != null) {
            SubcomposeAsyncImage(
                model = File(currentImagePath!!),
                contentDescription = "Random Cat",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp),
                contentScale = ContentScale.Crop
            )
        } else {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp),
                contentAlignment = Alignment.Center
            ) {
                Text("No cat yet, click refresh!")
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { viewModel.downloadNewRandomCat() },
            enabled = !isLoading
        ) {
            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.size(20.dp))
            } else {
                Text("Refresh Cat")
            }
        }
    }
}
