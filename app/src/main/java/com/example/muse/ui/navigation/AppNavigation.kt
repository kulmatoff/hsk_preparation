package com.example.muse.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.muse.ui.screens.HomeworkScreen
import com.example.muse.ui.screens.MainPage
import com.example.muse.ui.screens.SubjectScreen
import com.example.muse.ui.screens.TopicScreen
import com.example.muse.ui.screens.auth.LoginScreen
import com.example.muse.ui.screens.auth.ProfileScreen
import com.example.muse.ui.screens.auth.RegisterScreen

object Routes {
    const val HOME = "home"
    const val HOMEWORK = "homework"
    const val SUBJECT = "subject/{subjectId}"
    const val TOPIC = "topic/{subjectId}/{topicIndex}"
    const val PROFILE = "profile"
    const val LOGIN = "login"
    const val REGISTER = "register"

    fun subject(subjectId: String) = "subject/$subjectId"
    fun topic(subjectId: String, topicIndex: Int) = "topic/$subjectId/$topicIndex"
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.HOME
    ) {
        composable(Routes.HOME) {
            MainPage(
                onSubjectClick = { subjectId ->
                    navController.navigate(Routes.subject(subjectId))
                },
                onProfileClick = {
                    navController.navigate(Routes.PROFILE)
                },
                onActionClick = { action ->
                    when (action) {
                        "homework" -> navController.navigate(Routes.HOMEWORK)
                        else -> Unit // остальные разделы — в следующих этапах
                    }
                }
            )
        }

        composable(Routes.HOMEWORK) {
            HomeworkScreen(onBack = { navController.popBackStack() })
        }

        composable(Routes.PROFILE) {
            ProfileScreen(
                onBack = { navController.popBackStack() },
                onLoginClick = { navController.navigate(Routes.LOGIN) },
                onRegisterClick = { navController.navigate(Routes.REGISTER) }
            )
        }

        composable(Routes.LOGIN) {
            LoginScreen(
                onBack = { navController.popBackStack() },
                onRegisterClick = { navController.navigate(Routes.REGISTER) },
                onSuccess = { navController.popBackStack() }
            )
        }

        composable(Routes.REGISTER) {
            RegisterScreen(
                onBack = { navController.popBackStack() },
                onSuccess = {
                    // Возвращаемся на профиль: после REGISTER в стеке лежит LOGIN или PROFILE,
                    // сессия уже создана — экран профиля сам покажет вошедшее состояние.
                    navController.popBackStack(Routes.PROFILE, inclusive = false)
                }
            )
        }

        composable(
            route = Routes.SUBJECT,
            arguments = listOf(navArgument("subjectId") { type = NavType.StringType })
        ) { backStackEntry ->
            val subjectId = backStackEntry.arguments?.getString("subjectId") ?: ""
            SubjectScreen(
                subjectId = subjectId,
                onBack = { navController.popBackStack() },
                onTopicClick = { topicIndex ->
                    navController.navigate(Routes.topic(subjectId, topicIndex))
                }
            )
        }

        composable(
            route = Routes.TOPIC,
            arguments = listOf(
                navArgument("subjectId") { type = NavType.StringType },
                navArgument("topicIndex") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val subjectId = backStackEntry.arguments?.getString("subjectId") ?: ""
            val topicIndex = backStackEntry.arguments?.getInt("topicIndex") ?: 0
            TopicScreen(
                subjectId = subjectId,
                topicIndex = topicIndex,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
