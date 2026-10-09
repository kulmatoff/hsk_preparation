package com.example.muse.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Assignment
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.muse.data.content.ContentProvider
import com.example.muse.data.progress.HomeworkTaskEntity
import com.example.muse.ui.components.BackRow
import com.example.muse.ui.components.CircleIcon
import com.example.muse.ui.components.SectionTitle
import com.example.muse.ui.components.styleFor
import com.example.muse.ui.progress.ProgressViewModel
import com.example.muse.ui.theme.MuseBlue
import com.example.muse.ui.theme.MuseBlueLight
import com.example.muse.ui.theme.MuseBorder
import com.example.muse.ui.theme.MuseTextPrimary
import com.example.muse.ui.theme.MuseTextSecondary
import com.example.muse.ui.theme.MuseTheme

/**
 * Экран «Моя домашняя работа»: все задания по предметам,
 * отметка выполненных, счётчик оставшихся.
 */
@Composable
fun HomeworkScreen(
    onBack: () -> Unit = {},
    progressViewModel: ProgressViewModel = viewModel(
        factory = ProgressViewModel.factory(LocalContext.current.applicationContext)
    )
) {
    val homework by progressViewModel.homework.collectAsStateWithLifecycle()
    val subjects by ContentProvider.subjects.collectAsStateWithLifecycle()
    val pendingCount = homework.count { !it.done }

    // Сначала невыполненные, внутри групп — по предмету и теме
    val pending = homework.filter { !it.done }
    val done = homework.filter { it.done }
    val ordered = pending + done

    Scaffold { padding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {
                Spacer(modifier = Modifier.height(8.dp))
                BackRow(label = "Назад", onClick = onBack)

                Spacer(modifier = Modifier.height(12.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    CircleIcon(
                        icon = Icons.Outlined.Assignment,
                        tint = MuseBlue,
                        background = MuseBlueLight,
                        size = 56.dp,
                        iconSize = 28.dp
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(
                            text = "Моя домашняя работа",
                            color = MuseTextPrimary,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = if (pendingCount == 0) {
                                "Все задания выполнены ✓"
                            } else {
                                "$pendingCount заданий не выполнено"
                            },
                            color = MuseTextSecondary,
                            fontSize = 15.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                if (homework.isEmpty()) {
                    Text(
                        text = "Загрузка заданий...",
                        color = MuseTextSecondary,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(top = 32.dp)
                    )
                } else {
                    LazyColumn {
                        // Группировка по предметам с сохранением порядка из ContentProvider
                        val bySubject = ordered.groupBy { it.subjectId }
                        subjects.forEach { subject ->
                            val tasks = bySubject[subject.id] ?: return@forEach
                            val style = styleFor(subject)

                            item(key = "header_${subject.id}") {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    SectionTitle(
                                        subject.name,
                                        modifier = Modifier.padding(vertical = 8.dp)
                                    )
                                }
                            }

                            items(tasks, key = { it.id }) { task ->
                                HomeworkTaskRow(
                                    task = task,
                                    onToggle = { done ->
                                        progressViewModel.setHomeworkDone(task.id, done)
                                    }
                                )
                                Spacer(modifier = Modifier.height(10.dp))
                            }
                        }
                        item(key = "bottom_spacer") {
                            Spacer(modifier = Modifier.height(24.dp))
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun HomeworkTaskRow(
    task: HomeworkTaskEntity,
    onToggle: (Boolean) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (task.done) MuseBorder.copy(alpha = 0.4f)
            else MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = task.done,
                onCheckedChange = onToggle,
                colors = CheckboxDefaults.colors(checkedColor = MuseBlue)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = task.title,
                    color = MuseTextPrimary,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    textDecoration = if (task.done) TextDecoration.LineThrough
                    else TextDecoration.None
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "${task.topicTitle} · срок: ${task.deadline}",
                    color = MuseTextSecondary,
                    fontSize = 13.sp
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeworkScreenPreview() {
    MuseTheme {
        HomeworkScreen()
    }
}
