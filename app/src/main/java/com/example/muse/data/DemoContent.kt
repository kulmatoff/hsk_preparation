package com.example.muse.data

/**
 * Демонстрационный контент приложения.
 *
 * Пока бэкенд недоступен, все предметы, темы, подтемы и домашние задания
 * хранятся локально в этом файле. Когда API заработает, этот объект
 * заменяется данными из ContentRepository.
 */

data class Subtopic(val title: String)

data class Topic(
    val title: String,
    val videoDuration: String,
    val subtopics: List<Subtopic>,
    val homeworkTasks: Int,
    val homeworkDeadline: String
)

data class SubjectContent(
    val id: String,
    val name: String,
    val cardSubtitle: String,
    val pageSubtitle: String,
    val topics: List<Topic>,
    val progressDone: Int
) {
    val progressTotal: Int get() = topics.size
}

object DemoContent {

    val subjects = listOf(
        SubjectContent(
            id = "math",
            name = "Математика",
            cardSubtitle = "6 тем",
            pageSubtitle = "6 тем · CSCA подготовка",
            progressDone = 2,
            topics = listOf(
                Topic(
                    title = "Алгебра и уравнения",
                    videoDuration = "12:47",
                    subtopics = listOf(
                        Subtopic("Линейные уравнения"),
                        Subtopic("Квадратные уравнения"),
                        Subtopic("Системы уравнений")
                    ),
                    homeworkTasks = 5,
                    homeworkDeadline = "завтра"
                ),
                Topic(
                    title = "Геометрия",
                    videoDuration = "15:20",
                    subtopics = listOf(
                        Subtopic("Треугольники"),
                        Subtopic("Окружность"),
                        Subtopic("Площади фигур")
                    ),
                    homeworkTasks = 4,
                    homeworkDeadline = "послезавтра"
                ),
                Topic(
                    title = "Функции и графики",
                    videoDuration = "14:05",
                    subtopics = listOf(
                        Subtopic("Линейная функция"),
                        Subtopic("Квадратичная функция"),
                        Subtopic("Обратная пропорциональность")
                    ),
                    homeworkTasks = 6,
                    homeworkDeadline = "через 3 дня"
                ),
                Topic(
                    title = "Тригонометрия",
                    videoDuration = "18:30",
                    subtopics = listOf(
                        Subtopic("Синус и косинус"),
                        Subtopic("Тригонометрические уравнения"),
                        Subtopic("Формулы приведения")
                    ),
                    homeworkTasks = 5,
                    homeworkDeadline = "через 3 дня"
                ),
                Topic(
                    title = "Теория вероятностей",
                    videoDuration = "11:15",
                    subtopics = listOf(
                        Subtopic("Случайные события"),
                        Subtopic("Комбинаторика"),
                        Subtopic("Вероятность события")
                    ),
                    homeworkTasks = 3,
                    homeworkDeadline = "через 5 дней"
                ),
                Topic(
                    title = "Производная и интеграл",
                    videoDuration = "20:10",
                    subtopics = listOf(
                        Subtopic("Производная функции"),
                        Subtopic("Правила дифференцирования"),
                        Subtopic("Первообразная и интеграл")
                    ),
                    homeworkTasks = 7,
                    homeworkDeadline = "через 6 дней"
                )
            )
        ),
        SubjectContent(
            id = "physics",
            name = "Физика",
            cardSubtitle = "6 тем",
            pageSubtitle = "6 тем · CSCA подготовка",
            progressDone = 0,
            topics = listOf(
                Topic(
                    title = "Кинематика",
                    videoDuration = "13:40",
                    subtopics = listOf(
                        Subtopic("Равномерное движение"),
                        Subtopic("Равноускоренное движение"),
                        Subtopic("Движение по окружности")
                    ),
                    homeworkTasks = 4,
                    homeworkDeadline = "завтра"
                ),
                Topic(
                    title = "Динамика",
                    videoDuration = "16:00",
                    subtopics = listOf(
                        Subtopic("Законы Ньютона"),
                        Subtopic("Сила трения"),
                        Subtopic("Закон всемирного тяготения")
                    ),
                    homeworkTasks = 5,
                    homeworkDeadline = "через 2 дня"
                ),
                Topic(
                    title = "Статика и гидростатика",
                    videoDuration = "12:20",
                    subtopics = listOf(
                        Subtopic("Момент силы"),
                        Subtopic("Давление"),
                        Subtopic("Архимедова сила")
                    ),
                    homeworkTasks = 3,
                    homeworkDeadline = "через 3 дня"
                ),
                Topic(
                    title = "Законы сохранения",
                    videoDuration = "15:45",
                    subtopics = listOf(
                        Subtopic("Импульс тела"),
                        Subtopic("Закон сохранения импульса"),
                        Subtopic("Работа и энергия")
                    ),
                    homeworkTasks = 4,
                    homeworkDeadline = "через 4 дня"
                ),
                Topic(
                    title = "Электричество и магнетизм",
                    videoDuration = "19:30",
                    subtopics = listOf(
                        Subtopic("Закон Ома"),
                        Subtopic("Цепи постоянного тока"),
                        Subtopic("Магнитное поле")
                    ),
                    homeworkTasks = 6,
                    homeworkDeadline = "через 5 дней"
                ),
                Topic(
                    title = "Термодинамика",
                    videoDuration = "14:15",
                    subtopics = listOf(
                        Subtopic("Температура и теплота"),
                        Subtopic("Уравнение состояния газа"),
                        Subtopic("Первый закон термодинамики")
                    ),
                    homeworkTasks = 4,
                    homeworkDeadline = "через 6 дней"
                )
            )
        ),
        SubjectContent(
            id = "chemistry",
            name = "Химия",
            cardSubtitle = "6 тем",
            pageSubtitle = "6 тем · CSCA подготовка",
            progressDone = 0,
            topics = listOf(
                Topic(
                    title = "Основы химии",
                    videoDuration = "11:50",
                    subtopics = listOf(
                        Subtopic("Атомы и молекулы"),
                        Subtopic("Химические элементы"),
                        Subtopic("Валентность")
                    ),
                    homeworkTasks = 4,
                    homeworkDeadline = "завтра"
                ),
                Topic(
                    title = "Строение атома",
                    videoDuration = "14:30",
                    subtopics = listOf(
                        Subtopic("Состав атомного ядра"),
                        Subtopic("Электронные оболочки"),
                        Subtopic("Изотопы")
                    ),
                    homeworkTasks = 5,
                    homeworkDeadline = "через 2 дня"
                ),
                Topic(
                    title = "Периодический закон",
                    videoDuration = "12:10",
                    subtopics = listOf(
                        Subtopic("Строение периодической таблицы"),
                        Subtopic("Свойства элементов"),
                        Subtopic("Периодические закономерности")
                    ),
                    homeworkTasks = 3,
                    homeworkDeadline = "через 3 дня"
                ),
                Topic(
                    title = "Химические реакции",
                    videoDuration = "16:40",
                    subtopics = listOf(
                        Subtopic("Классификация реакций"),
                        Subtopic("Окислительно-восстановительные реакции"),
                        Subtopic("Электролиз")
                    ),
                    homeworkTasks = 6,
                    homeworkDeadline = "через 4 дня"
                ),
                Topic(
                    title = "Растворы",
                    videoDuration = "13:25",
                    subtopics = listOf(
                        Subtopic("Растворимость веществ"),
                        Subtopic("Массовая доля"),
                        Subtopic("Молярная концентрация")
                    ),
                    homeworkTasks = 4,
                    homeworkDeadline = "через 5 дней"
                ),
                Topic(
                    title = "Органическая химия",
                    videoDuration = "18:50",
                    subtopics = listOf(
                        Subtopic("Углеводороды"),
                        Subtopic("Спирты и фенолы"),
                        Subtopic("Карбоновые кислоты")
                    ),
                    homeworkTasks = 5,
                    homeworkDeadline = "через 6 дней"
                )
            )
        ),
        SubjectContent(
            id = "chinese",
            name = "Китайский",
            cardSubtitle = "HSK 1-5 · CSCA",
            pageSubtitle = "HSK 1-5 · CSCA подготовка",
            progressDone = 0,
            topics = listOf(
                Topic(
                    title = "Фонетика и пиньинь",
                    videoDuration = "10:30",
                    subtopics = listOf(
                        Subtopic("Тоны"),
                        Subtopic("Инициали и финали"),
                        Subtopic("Правила чтения")
                    ),
                    homeworkTasks = 3,
                    homeworkDeadline = "завтра"
                ),
                Topic(
                    title = "Иероглифика и черты",
                    videoDuration = "12:00",
                    subtopics = listOf(
                        Subtopic("Основные черты"),
                        Subtopic("Порядок написания"),
                        Subtopic("Ключи и радикалы")
                    ),
                    homeworkTasks = 4,
                    homeworkDeadline = "через 2 дня"
                ),
                Topic(
                    title = "Грамматика HSK 1–2",
                    videoDuration = "14:20",
                    subtopics = listOf(
                        Subtopic("Порядок слов в предложении"),
                        Subtopic("Частицы 了 и 的"),
                        Subtopic("Вопросительные конструкции")
                    ),
                    homeworkTasks = 5,
                    homeworkDeadline = "через 3 дня"
                ),
                Topic(
                    title = "Грамматика HSK 3–4",
                    videoDuration = "17:10",
                    subtopics = listOf(
                        Subtopic("Сложные предложения"),
                        Subtopic("Сравнительные конструкции"),
                        Subtopic("Предлоги и глаголы")
                    ),
                    homeworkTasks = 6,
                    homeworkDeadline = "через 4 дня"
                ),
                Topic(
                    title = "Аудирование",
                    videoDuration = "15:30",
                    subtopics = listOf(
                        Subtopic("Цифры и время"),
                        Subtopic("Повседневные диалоги"),
                        Subtopic("Тренировка скорости восприятия")
                    ),
                    homeworkTasks = 4,
                    homeworkDeadline = "через 5 дней"
                ),
                Topic(
                    title = "Лексика и чтение",
                    videoDuration = "13:15",
                    subtopics = listOf(
                        Subtopic("Частотные слова"),
                        Subtopic("Чтение текстов"),
                        Subtopic("Скорочтение")
                    ),
                    homeworkTasks = 5,
                    homeworkDeadline = "через 6 дней"
                )
            )
        )
    )

    fun subjectById(id: String): SubjectContent? = subjects.firstOrNull { it.id == id }
}
