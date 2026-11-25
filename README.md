# Личный кабинет успеваемости студента

Android-приложение (Kotlin, Jetpack Compose, Room). Командный проект.

**Текущий статус: спринт 2 — модель данных и локальное хранение.**

Функционал ограничен уровнем спринта 2: сущности, Room/SQLite, CRUD-операции, тестовые данные и сохранение после перезапуска. Интерфейс приведён в точное соответствие макету (все экраны прототипа сохранены визуально), но интерактивная логика фильтров, динамики и калькулятора цели — задел под следующие спринты и не является обязательной для приёмки спринта 2.

| Роль | Участник |
| --- | --- |
| Lead Developer | Князев Андрей |
| Product Owner / аналитик | Руднев Никита |
| QA / DevOps | Чепурнов Дмитрий |

## Что реализовано в спринте 2 (контрольная точка)

- Сущности `Discipline` и `Grade`, связь 1 — N, внешний ключ RESTRICT.
- Entity, DAO, AppDatabase (Room), Converters.
- GradeRepository: добавление, чтение, изменение, удаление дисциплин и оценок.
- Запрет удаления дисциплины при наличии оценок («Сначала удалите оценки (N)»).
- Загрузка тестовых данных в пустую базу без дублей.
- Подключение данных к экранам через AppViewModel (Flow).
- Форма оценки и удаление уже пишут в базу (чтобы можно было проверить persistence).
- Автотесты базы (androidTest) и сценарий ручной проверки `docs/persistence-check.md`.

## Как открыть и проверить

1. Android Studio (Koala или новее), JDK 17. File → Open → папка проекта, дождаться Gradle Sync (подключён плагин KSP для Room).
2. Запуск: Run на эмуляторе или устройстве. При первом запуске база заполняется тестовыми данными (4 дисциплины, 14 оценок).
3. Юнит-тесты: `app/src/test` (ПКМ → Run Tests).
4. Тесты базы данных: `app/src/androidTest` (нужен эмулятор или устройство).
5. Проверка сохранения после перезапуска — `docs/persistence-check.md`.

## Структура

```
app/src/main/java/com/example/studentgrades/
  StudentGradesApp.kt      # создаёт БД и репозиторий
  MainActivity.kt
  data/
    local/                 # Room: DisciplineEntity, GradeEntity, DAO, AppDatabase, Converters, Mappers
    repository/            # GradeRepository: CRUD + seed
    TestData.kt            # тестовые данные (docs/test-data.md)
    AppData.kt             # снимок данных для экранов
  domain/                  # правила, расчёты, валидация (чистый Kotlin)
  ui/
    theme/                 # цвета макета
    components/            # общие элементы интерфейса по макету
    navigation/            # маршруты и граф переходов
    AppViewModel.kt        # данные из БД → экраны
    home/ disciplines/ grades/ gradeform/ dynamics/ calculator/
app/src/test/              # юнит-тесты расчётов и валидации (JVM)
app/src/androidTest/       # тесты БД: CRUD, связь 1—N, перезапуск
docs/                      # документация и прототип
```

## Документы спринта 2

- docs/db-schema.md — схема базы данных
- docs/test-data.md — тестовые данные и эталонные средние
- docs/persistence-check.md — сценарий проверки сохранения
- docs/tasks-sprint2.md — задачи спринта 2
- docs/design.md — соответствие интерфейса макету
- docs/screen-graph-prototype.html — прототип экранов (источник дизайна)
- docs/passport.md, screens.md, scenarios.md, rules-and-model.md — требования (спринт 1)
