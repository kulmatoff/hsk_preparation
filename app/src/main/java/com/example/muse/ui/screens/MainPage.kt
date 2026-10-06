package com.example.muse.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Assignment
import androidx.compose.material.icons.outlined.Calculate
import androidx.compose.material.icons.outlined.CheckBox
import androidx.compose.material.icons.outlined.EmojiObjects
import androidx.compose.material.icons.outlined.EventAvailable
import androidx.compose.material.icons.outlined.School
import androidx.compose.material.icons.outlined.Science
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.outlined.Translate
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.muse.data.DemoContent
import com.example.muse.ui.components.ActionRow
import com.example.muse.ui.components.HeroBanner
import com.example.muse.ui.components.ProfileAvatar
import com.example.muse.ui.components.RecommendationCard
import com.example.muse.ui.components.SectionTitle
import com.example.muse.ui.components.SubjectGridCard
import com.example.muse.ui.components.styleFor
import com.example.muse.ui.progress.ProgressViewModel
import com.example.muse.ui.theme.MuseBlue
import com.example.muse.ui.theme.MuseBlueLight
import com.example.muse.ui.theme.MuseGreen
import com.example.muse.ui.theme.MuseGreenLight
import com.example.muse.ui.theme.MuseOrange
import com.example.muse.ui.theme.MuseOrangeLight
import com.example.muse.ui.theme.MusePurple
import com.example.muse.ui.theme.MusePurpleLight
import com.example.muse.ui.theme.MuseRed
import com.example.muse.ui.theme.MuseRedLight
import com.example.muse.ui.theme.MuseTextPrimary
import com.example.muse.ui.theme.MuseTextSecondary
import com.example.muse.ui.theme.MuseTheme
import com.example.muse.ui.theme.MuseYellow
import com.example.muse.ui.theme.MuseYellowLight

@Composable
fun MainPage(
    onSubjectClick: (String) -> Unit = {},
    onProfileClick: () -> Unit = {},
    onActionClick: (String) -> Unit = {},
    onRecommendationClick: (String) -> Unit = {},
    progressViewModel: ProgressViewModel = viewModel(
        factory = ProgressViewModel.factory(LocalContext.current.applicationContext)
    )
) {
    val pendingHomework by progressViewModel.pendingHomeworkCount.collectAsStateWithLifecycle()
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
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp)
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                // Шапка: приветствие + аватар
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Добро пожаловать",
                            color = MuseTextSecondary,
                            fontSize = 15.sp
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "Подготовка к CSCA",
                            color = MuseTextPrimary,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    ProfileAvatar(onClick = onProfileClick)
                }

                Spacer(modifier = Modifier.height(20.dp))

                HeroBanner(
                    onStartClick = { onSubjectClick(DemoContent.subjects.first().id) },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(24.dp))

                SectionTitle("Предметы")
                Spacer(modifier = Modifier.height(12.dp))

                // Сетка предметов 2 в ряд
                DemoContent.subjects.chunked(2).forEach { rowSubjects ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        rowSubjects.forEach { subject ->
                            val style = styleFor(subject)
                            SubjectGridCard(
                                name = subject.name,
                                subtitle = subject.cardSubtitle,
                                icon = style.icon,
                                tint = style.tint,
                                tintBackground = style.tintBackground,
                                modifier = Modifier.weight(1f),
                                onClick = { onSubjectClick(subject.id) }
                            )
                        }
                        // Нечётное количество — добиваем пустым местом
                        if (rowSubjects.size == 1) Spacer(modifier = Modifier.weight(1f))
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                }

                Spacer(modifier = Modifier.height(12.dp))
                SectionTitle("Действия")
                Spacer(modifier = Modifier.height(12.dp))

                ActionRow(
                    title = "Тест",
                    subtitle = "Проверить знания",
                    icon = Icons.Outlined.CheckBox,
                    tint = MuseYellow,
                    tintBackground = MuseYellowLight,
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { onActionClick("test") }
                )
                Spacer(modifier = Modifier.height(12.dp))
                ActionRow(
                    title = "Моя домашняя работа",
                    subtitle = if (pendingHomework == 0) {
                        "Все задания выполнены ✓"
                    } else {
                        "$pendingHomework заданий не выполнено"
                    },
                    icon = Icons.Outlined.Assignment,
                    tint = MuseBlue,
                    tintBackground = MuseBlueLight,
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { onActionClick("homework") }
                )
                Spacer(modifier = Modifier.height(12.dp))
                ActionRow(
                    title = "Записаться к учителю",
                    subtitle = "Выбрать время занятия",
                    icon = Icons.Outlined.EventAvailable,
                    tint = MuseGreen,
                    tintBackground = MuseGreenLight,
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { onActionClick("teacher") }
                )

                Spacer(modifier = Modifier.height(24.dp))
                SectionTitle("Рекомендации")
                Spacer(modifier = Modifier.height(12.dp))

                RecommendationCard(
                    badgeIcon = Icons.Outlined.Star,
                    badgeText = "CSCA",
                    title = "Рекомендации CSCA",
                    subtitle = "Стратегия, баллы, типовые задания",
                    accent = MuseBlue,
                    background = MuseBlueLight,
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { onRecommendationClick("csca") }
                )
                Spacer(modifier = Modifier.height(12.dp))
                RecommendationCard(
                    badgeIcon = Icons.Outlined.School,
                    badgeText = "Поступление",
                    title = "Рекомендации по поступлению",
                    subtitle = "Университеты, документы, дедлайны",
                    accent = MuseOrange,
                    background = MuseOrangeLight,
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { onRecommendationClick("admission") }
                )

                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainPagePreview() {
    MuseTheme {
        MainPage()
    }
}
