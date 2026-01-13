package com.example.adoptacaterpillar.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.adoptacaterpillar.ui.viewmodel.BreedViewModel

@Composable
fun BreedDetailScreen(breedId: String?, viewModel: BreedViewModel = hiltViewModel()) {
    val breeds by viewModel.breeds.collectAsState()
    val breed = breeds.find { it.id == breedId }

    if (breed == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = androidx.compose.ui.Alignment.Center
        ) {
            Text("Unknown breed")
        }
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = breed.name,
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold
        )

        if (breed.origin.isNotEmpty()) {
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("\uD83D\uDCCD Origin", fontWeight = FontWeight.Bold)
                    Text(breed.origin)
                }
            }
        }

        if (breed.temperament.isNotEmpty()) {
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("✨ Temperament", fontWeight = FontWeight.Bold)
                    Text(breed.temperament)
                }
            }
        }

        if (breed.description.isNotEmpty()) {
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("\uD83D\uDCAD Description", fontWeight = FontWeight.Bold)
                    Text(breed.description)
                }
            }
        }

        if (breed.lifeSpan.isNotEmpty()) {
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("\uD83C\uDF82 Life span", fontWeight = FontWeight.Bold)
                    Text("${breed.lifeSpan} years")
                }
            }
        }
    }
}
