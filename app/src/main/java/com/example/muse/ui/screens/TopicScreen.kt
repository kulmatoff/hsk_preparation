package com.example.muse.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Assignment
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.MenuBook
import androidx.compose.material.icons.outlined.QuestionAnswer
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.muse.data.DemoContent
import com.example.muse.ui.components.BackRow
import com.example.muse.ui.components.MaterialRow
import com.example.muse.ui.components.SectionTitle
import com.example.muse.ui.components.SubtopicGroup
import com.example.muse.ui.components.VideoCard
import com.example.muse.ui.progress.ProgressViewModel
import com.example.muse.ui.theme.MuseBlue
import com.example.muse.ui.theme.MuseBlueLight
import com.example.muse.ui.theme.MuseGreen
import com.example.muse.ui.theme.MuseGreenLight
import com.example.muse.ui.theme.MuseTextPrimary
import com.example.muse.ui.theme.MuseTextSecondary
import com.example.muse.ui.theme.MuseTheme
import com.example.muse.ui.theme.MuseYellow
import com.example.muse.ui.theme.MuseYellowLight

/**
 * Экран темы: видеоурок, подтемы и материалы
 * («Разбор темы текстом», «Домашка», «Вопросы»).
 */
@Composable
fun TopicScreen(
    subjectId: String,
    topicIndex: Int,
    onBack: () -> Unit = {},
    onVideoClick: () -> Unit = {},
    onMaterialClick: (String) -> Unit = {}
) {
    val subject = DemoContent.subjectById(subjectId)
    val topic = subject?.topics?.getOrNull(topicIndex)
    val progressViewModel: ProgressViewModel = viewModel(
        factory = ProgressViewModel.factory(LocalContext.current.applicationContext)
    )
    val completedTopics by progressViewModel.completedTopics.collectAsStateWithLifecycle()
    val isCompleted = completedTopics[subjectId]?.contains(topicIndex) == true

    Scaffold { padding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            color = MaterialTheme.colorScheme.background
        ) {
            if (subject == null || topic == null) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Тема не найдена",
                        color = MuseTextSecondary,
                        fontSize = 17.sp
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    BackRow(label = "Назад", onClick = onBack)
                }
                return@Surface
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp)
            ) {
                Spacer(modifier = Modifier.height(8.dp))

                BackRow(label = subject.name, onClick = onBack)

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = topic.title,
                    color = MuseTextPrimary,
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "${subject.name} · CSCA подготовка",
                    color = MuseTextSecondary,
                    fontSize = 15.sp
                )

                Spacer(modifier = Modifier.height(20.dp))

                VideoCard(
                    topicTitle = topic.title,
                    duration = topic.videoDuration,
                    modifier = Modifier.fillMaxWidth(),
                    onClick = onVideoClick
                )

                Spacer(modifier = Modifier.height(24.dp))

                SectionTitle("Подтемы")
                Spacer(modifier = Modifier.height(12.dp))
                SubtopicGroup(
                    titles = topic.subtopics.map { it.title },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(24.dp))

                SectionTitle("Материалы")
                Spacer(modifier = Modifier.height(12.dp))

                MaterialRow(
                    title = "Разбор темы текстом",
                    subtitle = "Подробное объяснение с примерами",
                    icon = Icons.Outlined.MenuBook,
                    tint = MuseBlue,
                    background = MuseBlueLight,
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { onMaterialClick("text") }
                )
                Spacer(modifier = Modifier.height(12.dp))
                MaterialRow(
                    title = "Домашка",
                    subtitle = "${topic.homeworkTasks} заданий · срок сдачи: ${topic.homeworkDeadline}",
                    icon = Icons.Outlined.Assignment,
                    tint = MuseYellow,
                    background = MuseYellowLight,
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { onMaterialClick("homework") }
                )
                Spacer(modifier = Modifier.height(12.dp))
                MaterialRow(
                    title = "Вопросы",
                    subtitle = "Задать вопрос учителю",
                    icon = Icons.Outlined.QuestionAnswer,
                    tint = MuseGreen,
                    background = MuseGreenLight,
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { onMaterialClick("questions") }
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Отметка о прохождении темы
                if (isCompleted) {
                    OutlinedButton(
                        onClick = {
                            progressViewModel.setTopicCompleted(subjectId, topicIndex, false)
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(50),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = MuseGreen
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.CheckCircle,
                            contentDescription = null,
                            modifier = Modifier.height(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "  Тема пройдена — отменить",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                } else {
                    Button(
                        onClick = {
                            progressViewModel.setTopicCompleted(subjectId, topicIndex, true)
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(50),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MuseBlue,
                            contentColor = Color.White
                        )
                    ) {
                        Text(
                            text = "Отметить тему пройденной",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TopicScreenPreview() {
    MuseTheme {
        TopicScreen(subjectId = "math", topicIndex = 0)
    }
}
