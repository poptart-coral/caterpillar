package com.example.adoptacaterpillar.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.adoptacaterpillar.domain.model.Cat
import com.example.adoptacaterpillar.ui.viewmodel.CatViewModel

@Composable
fun CatListScreen(viewModel: CatViewModel, onCatClick: (String) -> Unit) {
    val cats by viewModel.cats.collectAsState()

    LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        items(cats) { cat ->
            CatItem(cat = cat, onClick = { onCatClick(cat.id) })
        }
    }
}

@Composable
fun CatItem(cat: Cat, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = cat.imageUrl,
                contentDescription = cat.name,
                modifier = Modifier.size(80.dp),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = cat.name, style = MaterialTheme.typography.titleMedium)
                Text(text = cat.breed, style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}
