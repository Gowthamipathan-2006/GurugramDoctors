package com.example.gurugramdoctors

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.background
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// NOTE: All data below is SAMPLE data. Replace with real doctors' details before real-world use.
data class Doctor(
    val name: String,
    val expertise: String,
    val workplace: String,
    val address: String,
    val qualification: String,
    val experience: String
)

val doctors = listOf(
    Doctor("Dr. Ananya Sharma", "Cardiologist", "CityHeart Hospital",
        "Sector 44, Gurugram, Haryana 122003", "MBBS, MD (Medicine), DM (Cardiology)", "15 years"),
    Doctor("Dr. Rohan Mehta", "Orthopedic Surgeon", "BoneCare Clinic",
        "DLF Phase 3, Gurugram, Haryana 122002", "MBBS, MS (Orthopaedics)", "12 years"),
    Doctor("Dr. Priya Verma", "Pediatrician", "Little Steps Children's Hospital",
        "Sohna Road, Gurugram, Haryana 122018", "MBBS, MD (Pediatrics)", "10 years"),
    Doctor("Dr. Vikram Singh", "Neurologist", "NeuroLife Institute",
        "Golf Course Road, Gurugram, Haryana 122002", "MBBS, MD, DM (Neurology)", "18 years"),
    Doctor("Dr. Neha Kapoor", "Dermatologist", "SkinGlow Clinic",
        "MG Road, Gurugram, Haryana 122001", "MBBS, MD (Dermatology)", "8 years"),
    Doctor("Dr. Arjun Malhotra", "General Physician", "Wellness Family Clinic",
        "Sector 56, Gurugram, Haryana 122011", "MBBS, MD (General Medicine)", "14 years"),
    Doctor("Dr. Kavita Rao", "Gynecologist", "Motherhood Care Centre",
        "Sector 14, Gurugram, Haryana 122001", "MBBS, MS (Obstetrics & Gynaecology)", "16 years")
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { MaterialTheme { App() } }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App() {
    var selected by remember { mutableStateOf<Doctor?>(null) }
    BackHandler(enabled = selected != null) { selected = null }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(selected?.name ?: "Doctors in Gurugram") },
                navigationIcon = {
                    if (selected != null) {
                        IconButton(onClick = { selected = null }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                        }
                    }
                }
            )
        }
    ) { padding ->
        Box(Modifier.padding(padding)) {
            val d = selected
            if (d == null) DoctorList { selected = it } else DoctorProfile(d)
        }
    }
}

@Composable
fun Avatar(name: String, size: Int) {
    val initial = name.removePrefix("Dr. ").first().toString()
    Box(
        Modifier.size(size.dp).background(MaterialTheme.colorScheme.primary, CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Text(initial, color = MaterialTheme.colorScheme.onPrimary, fontSize = (size / 2).sp)
    }
}

@Composable
fun DoctorList(onClick: (Doctor) -> Unit) {
    LazyColumn(contentPadding = PaddingValues(12.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
        items(doctors) { d ->
            Card(Modifier.fillMaxWidth().clickable { onClick(d) }) {
                Row(Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
                    Avatar(d.name, 52)
                    Spacer(Modifier.width(14.dp))
                    Column {
                        Text(d.name, fontWeight = FontWeight.Bold, fontSize = 17.sp)
                        Text(d.expertise, color = MaterialTheme.colorScheme.primary)
                        Text(d.workplace, fontSize = 13.sp)
                    }
                }
            }
        }
    }
}

@Composable
fun DoctorProfile(d: Doctor) {
    Column(
        Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Avatar(d.name, 96)
        Spacer(Modifier.height(12.dp))
        Text(d.name, fontSize = 22.sp, fontWeight = FontWeight.Bold)
        Text(d.expertise, color = MaterialTheme.colorScheme.primary, fontSize = 16.sp)
        Spacer(Modifier.height(20.dp))
        InfoRow("Expertise", d.expertise)
        InfoRow("Workplace", d.workplace)
        InfoRow("Address", d.address)
        InfoRow("Qualification", d.qualification)
        InfoRow("Experience", d.experience)
    }
}

@Composable
fun InfoRow(label: String, value: String) {
    Card(Modifier.fillMaxWidth().padding(vertical = 5.dp)) {
        Column(Modifier.padding(14.dp)) {
            Text(label, fontSize = 12.sp, color = MaterialTheme.colorScheme.primary)
            Text(value, fontSize = 16.sp)
        }
    }
}
