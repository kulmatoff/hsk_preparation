package com.example.muse.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.KeyboardArrowRight
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.muse.ui.theme.MuseBackground
import com.example.muse.ui.theme.MuseBlue
import com.example.muse.ui.theme.MuseBlueLight
import com.example.muse.ui.theme.MuseBorder
import com.example.muse.ui.theme.MuseGreen
import com.example.muse.ui.theme.MuseGreenLight
import com.example.muse.ui.theme.MuseOrange
import com.example.muse.ui.theme.MuseOrangeLight
import com.example.muse.ui.theme.MuseTextPrimary
import com.example.muse.ui.theme.MuseTextSecondary
import com.example.muse.ui.theme.MuseTheme
import com.example.muse.ui.theme.MuseYellow
import com.example.muse.ui.theme.MuseYellowLight

/** Заголовок секции серым капсом: «ПРЕДМЕТЫ», «ДЕЙСТВИЯ» и т.д. */
@Composable
fun SectionTitle(title: String, modifier: Modifier = Modifier) {
    Text(
        text = title.uppercase(),
        color = MuseTextSecondary,
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = 1.5.sp,
        modifier = modifier
    )
}

/** Круг с иконкой — базовый элемент карточек. */
@Composable
fun CircleIcon(
    icon: ImageVector,
    tint: Color,
    background: Color,
    modifier: Modifier = Modifier,
    size: Dp = 48.dp,
    iconSize: Dp = 24.dp
) {
    Box(
        modifier = modifier
            .size(size)
            .background(color = background, shape = CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = tint,
            modifier = Modifier.size(iconSize)
        )
    }
}

/** Кнопка «Назад» со стрелкой. */
@Composable
fun BackRow(label: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .clickable(onClick = onClick)
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
            contentDescription = "Назад",
            tint = MuseTextSecondary,
            modifier = Modifier.size(22.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = label,
            color = MuseTextSecondary,
            fontSize = 17.sp
        )
    }
}

/** Синий hero-баннер «Экзамен CSCA» с кнопкой «Начать подготовку». */
@Composable
fun HeroBanner(onStartClick: () -> Unit, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(24.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.linearGradient(
                        colors = listOf(Color(0xFF24379B), Color(0xFF1B2A7A))
                    )
                )
                .padding(24.dp)
        ) {
            Column {
                Text(
                    text = "Центральноазиатский",
                    color = Color(0xFFC7D0F5),
                    fontSize = 15.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Экзамен CSCA",
                    color = Color.White,
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "4 предмета · видео · домашка · тесты",
                    color = Color(0xFFC7D0F5),
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.height(20.dp))
                Surface(
                    color = Color(0x33FFFFFF),
                    shape = RoundedCornerShape(50),
                    modifier = Modifier.clickable(onClick = onStartClick)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Star,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Начать подготовку",
                            color = Color.White,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }
    }
}

/** Карточка предмета в сетке на главном экране. */
@Composable
fun SubjectGridCard(
    name: String,
    subtitle: String,
    icon: ImageVector,
    tint: Color,
    tintBackground: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        onClick = onClick
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            CircleIcon(icon = icon, tint = tint, background = tintBackground)
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = name,
                color = MuseTextPrimary,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = subtitle,
                color = MuseTextSecondary,
                fontSize = 14.sp
            )
        }
    }
}

/** Строка действия на главном экране: иконка, заголовок, подпись, стрелка. */
@Composable
fun ActionRow(
    title: String,
    subtitle: String,
    icon: ImageVector,
    tint: Color,
    tintBackground: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CircleIcon(icon = icon, tint = tint, background = tintBackground)
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    color = MuseTextPrimary,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = subtitle,
                    color = MuseTextSecondary,
                    fontSize = 14.sp
                )
            }
            Icon(
                imageVector = Icons.Outlined.KeyboardArrowRight,
                contentDescription = null,
                tint = MuseTextSecondary
            )
        }
    }
}

/** Карточка рекомендации (синяя или оранжевая). */
@Composable
fun RecommendationCard(
    badgeIcon: ImageVector,
    badgeText: String,
    title: String,
    subtitle: String,
    accent: Color,
    background: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = background),
        onClick = onClick
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = badgeIcon,
                    contentDescription = null,
                    tint = accent,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = badgeText.uppercase(),
                    color = accent,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = title,
                color = MuseTextPrimary,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = subtitle,
                color = MuseTextSecondary,
                fontSize = 14.sp
            )
        }
    }
}

/** Карточка прогресса предмета: «Прогресс 2 / 6» + полоса. */
@Composable
fun ProgressCard(done: Int, total: Int, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Прогресс",
                    color = MuseTextSecondary,
                    fontSize = 16.sp
                )
                Text(
                    text = "$done / $total",
                    color = MuseBlue,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            LinearProgressIndicator(
                progress = { if (total == 0) 0f else done.toFloat() / total },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(RoundedCornerShape(4.dp)),
                color = MuseBlue,
                trackColor = MuseBorder
            )
        }
    }
}

/** Строка темы на экране предмета. Пройденные темы отмечаются галочкой. */
@Composable
fun TopicRow(
    title: String,
    completed: Boolean = false,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 22.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                color = MuseTextPrimary,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.weight(1f)
            )
            if (completed) {
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .background(MuseGreenLight, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Check,
                        contentDescription = "Пройдено",
                        tint = MuseGreen,
                        modifier = Modifier.size(16.dp)
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
            }
            Icon(
                imageVector = Icons.Outlined.KeyboardArrowRight,
                contentDescription = null,
                tint = MuseTextSecondary
            )
        }
    }
}

/** Тёмная карточка видеоурока с кнопкой play. */
@Composable
fun VideoCard(
    topicTitle: String,
    duration: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier.clickable(onClick = onClick),
        shape = RoundedCornerShape(24.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF10173F))
                .padding(20.dp)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(140.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(64.dp)
                            .background(Color(0x33FFFFFF), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Filled.PlayArrow,
                            contentDescription = "Смотреть",
                            tint = Color.White,
                            modifier = Modifier.size(36.dp)
                        )
                    }
                }
                Text(
                    text = "Нажмите для просмотра",
                    color = Color(0xFFC7D0F5),
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.height(10.dp))
                Row(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Видеоурок — $topicTitle",
                        color = Color(0xFFC7D0F5),
                        fontSize = 14.sp,
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = duration,
                        color = Color(0xFFC7D0F5),
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}

/** Нумерованный список подтем единой белой карточкой. */
@Composable
fun SubtopicGroup(titles: List<String>, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column {
            titles.forEachIndexed { index, title ->
                if (index > 0) {
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(MuseBorder)
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 18.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .background(MuseBlueLight, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "${index + 1}",
                            color = MuseBlue,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = title,
                        color = MuseTextPrimary,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}

/** Цветная карточка материала на экране темы. */
@Composable
fun MaterialRow(
    title: String,
    subtitle: String,
    icon: ImageVector,
    tint: Color,
    background: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = background),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CircleIcon(icon = icon, tint = tint, background = Color.White)
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    color = tint,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = subtitle,
                    color = MuseTextSecondary,
                    fontSize = 14.sp
                )
            }
            Icon(
                imageVector = Icons.Outlined.KeyboardArrowRight,
                contentDescription = null,
                tint = MuseTextSecondary
            )
        }
    }
}

/** Круглый аватар профиля в шапке главного экрана. */
@Composable
fun ProfileAvatar(modifier: Modifier = Modifier, onClick: () -> Unit = {}) {
    Box(
        modifier = modifier
            .size(48.dp)
            .clip(CircleShape)
            .background(MuseBlue)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Outlined.Person,
            contentDescription = "Профиль",
            tint = Color.White,
            modifier = Modifier.size(26.dp)
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFEEF2FF)
@Composable
private fun ComponentsPreview() {
    MuseTheme {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .background(MuseBackground),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            HeroBanner(onStartClick = {})
            ProgressCard(done = 2, total = 6)
            TopicRow(title = "Алгебра и уравнения", onClick = {})
            MaterialRow(
                title = "Домашка",
                subtitle = "5 заданий · срок сдачи: завтра",
                icon = Icons.Outlined.Star,
                tint = MuseYellow,
                background = MuseYellowLight,
                onClick = {}
            )
        }
    }
}
