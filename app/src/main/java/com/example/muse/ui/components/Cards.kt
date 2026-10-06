package com.example.muse.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.muse.data.Subject
import com.example.muse.ui.theme.MuseBlueLight
import androidx.compose.ui.tooling.preview.Preview
import com.example.muse.ui.theme.MuseTheme

@Composable
fun SubjectCard(content: Subject, modifier: Modifier, onClick: () -> Unit) {
//    var count by remember { mutableStateOf(0) }
    Card(modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.onPrimary
        ),
        onClick = onClick

//        .colors(
//            color = MaterialTheme.colorScheme.onPrimary
//        )
    ) {
//        Image (
//            painter = painterResource(R.drawable.profile_picture),
//            contentDescription = "contact",
//            modifier = Modifier
//                .size(40.dp)
//                .clip(CircleShape)
//                .border(1.5.dp, MaterialTheme.colorScheme.primary, CircleShape)
//        )

        Column(modifier = Modifier
            .padding(all = 16.dp)) {
            Box(modifier = Modifier
                .size(48.dp)
                .background(
                    color = MuseBlueLight,
                    shape = CircleShape
                ),
                contentAlignment = Alignment.Center) {
//                Icon(
//                    painter = painterResource(id = R.drawable.calculator),
//                    contentDescription = "Calculator",
//                    tint = MaterialTheme.colorScheme.primary
//                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = content.name,
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.titleLarge,
                fontSize = 14.sp)
            Text(
                text = content.description,
                color = MaterialTheme.colorScheme.secondary
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

//        Button(onClick = { count += 1 }) { Text("$count \uD83D\uDC4D") }
    }
}

@Composable
fun ActionCard(content: Subject, modifier: Modifier, onClick: () -> Unit) {
//    var count by remember { mutableStateOf(0) }
    Card(modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.onPrimary
        ),
        onClick = onClick

//        .colors(
//            color = MaterialTheme.colorScheme.onPrimary
//        )
    ) {
//        Image (
//            painter = painterResource(R.drawable.profile_picture),
//            contentDescription = "contact",
//            modifier = Modifier
//                .size(40.dp)
//                .clip(CircleShape)
//                .border(1.5.dp, MaterialTheme.colorScheme.primary, CircleShape)
//        )

        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(all = 16.dp)) {
            Row(
                horizontalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .background(
                            color = MuseBlueLight,
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
//                Icon(
//                    painter = painterResource(id = R.drawable.calculator),
//                    contentDescription = "Calculator",
//                    tint = MaterialTheme.colorScheme.primary
//                )
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(
                        text = content.name,
                        color = MaterialTheme.colorScheme.onBackground,
                        style = MaterialTheme.typography.titleLarge,
                        fontSize = 14.sp
                    )
                    Text(
                        text = content.description,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
            }
        }

        Spacer(modifier = Modifier.width(8.dp))

//        Button(onClick = { count += 1 }) { Text("$count \uD83D\uDC4D") }
    }
}

@Composable
fun RecommendationCard(content: Subject, modifier: Modifier, onClick: () -> Unit) {
//    var count by remember { mutableStateOf(0) }
    Card(modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.onPrimary
        ),
        onClick = onClick

//        .colors(
//            color = MaterialTheme.colorScheme.onPrimary
//        )
    ) {
//        Image (
//            painter = painterResource(R.drawable.profile_picture),
//            contentDescription = "contact",
//            modifier = Modifier
//                .size(40.dp)
//                .clip(CircleShape)
//                .border(1.5.dp, MaterialTheme.colorScheme.primary, CircleShape)
//        )

        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(all = 16.dp)) {
            Row {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .background(
                            color = MuseBlueLight,
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
//                Icon(
//                    painter = painterResource(id = R.drawable.calculator),
//                    contentDescription = "Calculator",
//                    tint = MaterialTheme.colorScheme.primary
//                )
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(
                        text = content.name,
                        color = MaterialTheme.colorScheme.onBackground,
                        style = MaterialTheme.typography.titleLarge,
                        fontSize = 14.sp
                    )
                    Text(
                        text = content.description,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
            }
        }

        Spacer(modifier = Modifier.width(8.dp))

//        Button(onClick = { count += 1 }) { Text("$count \uD83D\uDC4D") }
    }
}

@Preview(showBackground = true)
@Composable
fun CardsPreview() {
    MuseTheme {
        Column() {
            SubjectCard(Subject("Test", "Test"), Modifier, onClick = {})
            ActionCard(Subject("Test", "Test"), Modifier, onClick = {})
            RecommendationCard(Subject("Test", "Test"), Modifier, onClick = {})
        }
    }
}