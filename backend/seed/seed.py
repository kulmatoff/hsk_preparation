"""Наполнение таблицы muse-content демо-контентом.

Запуск (после деплоя и настройки AWS CLI):
    python3 seed/seed.py --table muse-content --region eu-north-1

Структура: предмет -> тема -> подтема соответствует
модели приложения: course -> chapter -> lesson.
"""

import argparse

import boto3

SUBJECTS = [
    {
        "title": "Математика",
        "topics": [
            ("Алгебра и уравнения", ["Линейные уравнения", "Квадратные уравнения", "Системы уравнений"]),
            ("Геометрия", ["Треугольники", "Окружность", "Площади фигур"]),
            ("Функции и графики", ["Линейная функция", "Квадратичная функция", "Обратная пропорциональность"]),
            ("Тригонометрия", ["Синус и косинус", "Тригонометрические уравнения", "Формулы приведения"]),
            ("Теория вероятностей", ["Случайные события", "Комбинаторика", "Вероятность события"]),
            ("Производная и интеграл", ["Производная функции", "Правила дифференцирования", "Первообразная и интеграл"]),
        ],
    },
    {
        "title": "Физика",
        "topics": [
            ("Кинематика", ["Равномерное движение", "Равноускоренное движение", "Движение по окружности"]),
            ("Динамика", ["Законы Ньютона", "Сила трения", "Закон всемирного тяготения"]),
            ("Статика и гидростатика", ["Момент силы", "Давление", "Архимедова сила"]),
            ("Законы сохранения", ["Импульс тела", "Закон сохранения импульса", "Работа и энергия"]),
            ("Электричество и магнетизм", ["Закон Ома", "Цепи постоянного тока", "Магнитное поле"]),
            ("Термодинамика", ["Температура и теплота", "Уравнение состояния газа", "Первый закон термодинамики"]),
        ],
    },
    {
        "title": "Химия",
        "topics": [
            ("Основы химии", ["Атомы и молекулы", "Химические элементы", "Валентность"]),
            ("Строение атома", ["Состав атомного ядра", "Электронные оболочки", "Изотопы"]),
            ("Периодический закон", ["Строение периодической таблицы", "Свойства элементов", "Периодические закономерности"]),
            ("Химические реакции", ["Классификация реакций", "Окислительно-восстановительные реакции", "Электролиз"]),
            ("Растворы", ["Растворимость веществ", "Массовая доля", "Молярная концентрация"]),
            ("Органическая химия", ["Углеводороды", "Спирты и фенолы", "Карбоновые кислоты"]),
        ],
    },
    {
        "title": "Китайский",
        "topics": [
            ("Фонетика и пиньинь", ["Тоны", "Инициали и финали", "Правила чтения"]),
            ("Иероглифика и черты", ["Основные черты", "Порядок написания", "Ключи и радикалы"]),
            ("Грамматика HSK 1–2", ["Порядок слов в предложении", "Частицы 了 и 的", "Вопросительные конструкции"]),
            ("Грамматика HSK 3–4", ["Сложные предложения", "Сравнительные конструкции", "Предлоги и глаголы"]),
            ("Аудирование", ["Цифры и время", "Повседневные диалоги", "Тренировка скорости восприятия"]),
            ("Лексика и чтение", ["Частотные слова", "Чтение текстов", "Скорочтение"]),
        ],
    },
]

CONTENT_VERSION = 1


def build_items():
    courses, chapters, lessons = [], [], []
    chapter_id = 0
    lesson_id = 0

    for course_id, subject in enumerate(SUBJECTS, start=1):
        courses.append({
            "entity": "course",
            "id": course_id,
            "title": subject["title"],
        })
        for chapter_pos, (topic_title, subtopics) in enumerate(subject["topics"], start=1):
            chapter_id += 1
            chapters.append({
                "entity": "chapter",
                "id": chapter_id,
                "courseId": course_id,
                "title": topic_title,
                "position": chapter_pos,
            })
            for lesson_pos, subtopic_title in enumerate(subtopics, start=1):
                lesson_id += 1
                lessons.append({
                    "entity": "lesson",
                    "id": lesson_id,
                    "chapterId": chapter_id,
                    "courseId": course_id,
                    "chapterTitle": topic_title,
                    "chapterPosition": chapter_pos,
                    "title": subtopic_title,
                    "content": f"Конспект урока «{subtopic_title}» ({topic_title}). Материал будет добавлен.",
                    "position": lesson_pos,
                })

    meta = [{"entity": "meta", "id": 1, "version": CONTENT_VERSION}]
    return meta + courses + chapters + lessons


def main():
    parser = argparse.ArgumentParser()
    parser.add_argument("--table", default="muse-content")
    parser.add_argument("--region", default="eu-north-1")
    args = parser.parse_args()

    table = boto3.resource("dynamodb", region_name=args.region).Table(args.table)
    items = build_items()

    with table.batch_writer() as batch:
        for item in items:
            batch.put_item(Item=item)

    print(f"Загружено записей: {len(items)} "
          f"(курсов: {len(SUBJECTS)}, версия контента: {CONTENT_VERSION})")


if __name__ == "__main__":
    main()
