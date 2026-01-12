package com.example.adoptacaterpillar.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.adoptacaterpillar.ui.viewmodel.CatViewModel

@Composable
fun CatDetailScreen(viewModel: CatViewModel, catId: String?) {
    val cat = catId?.let { viewModel.getCatById(it) }

    if (cat != null) {
        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            AsyncImage(
                model = cat.imageUrl,
                contentDescription = cat.name,
                modifier = Modifier.fillMaxWidth().height(300.dp),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = cat.name, style = MaterialTheme.typography.headlineMedium)
            Text(text = cat.breed, style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.secondary)
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = cat.description, style = MaterialTheme.typography.bodyLarge)
        }
    } else {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
            Text("Cat not found")
        }
    }
}
