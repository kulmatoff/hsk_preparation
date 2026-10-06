## HSK Preparation mobile app

[Click here Figma design](https://www.figma.com/make/EVhuldaXz1E0NDseWdOLos/%D0%9C%D0%BE%D0%B1%D0%B8%D0%BB%D1%8C%D0%BD%D0%BE%D0%B5-%D0%BF%D1%80%D0%B8%D0%BB%D0%BE%D0%B6%D0%B5%D0%BD%D0%B8%D0%B5-%D0%B4%D0%BB%D1%8F-CSCA?p=f&t=0vKgovwqlKj0VqQ4-0)

Project structure brief summary

```
com.example.muse/
├── data
│   └── Subject.kt            # defining data types as Subject
├── ui
│   ├── components
│   │   └── Cards.kt          # Reusable components (@composable) utilizing Card type
│   ├── navigation
│   │   └── AppNavigation.kt  # Organizing the navigation between screens
│   └── screens
│       ├── MainPage.kt       # "Front page" of the app
│       └── SecondPage.kt     # "Test page"
└── MainActivity.kt           # Main starting file
```



Plans
 - [✅] Finish design
 - [✗] Завершить главную страницу
 - [✗] Добавить базу данных курсов, видео, домашних заданий, тестов, и тд
 - [✗] Создать базу данных пользователей
 - [✗] Добавить личную страницу пользователя
 - [✗] Добавить трекер прогресса, выполненных домашних заданий, и тд
 - [✗] Создать отдельные страницы для каждых курсов
