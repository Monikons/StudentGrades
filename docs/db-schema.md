# Схема базы данных

База `student_grades.db` (Room 2.6.1, версия 1). Хранится во внутренней памяти приложения и переживает перезапуск.

```
disciplines (1) ──< grades (N)
```

## disciplines
| Поле | Тип | Правила |
| --- | --- | --- |
| id | INTEGER, PK, автоинкремент | |
| name | TEXT, NOT NULL | название дисциплины |
| teacherName | TEXT, NULL | преподаватель (необязательно) |

## grades
| Поле | Тип | Правила |
| --- | --- | --- |
| id | INTEGER, PK, автоинкремент | |
| disciplineId | INTEGER, NOT NULL | внешний ключ → disciplines.id, индекс |
| value | INTEGER, NOT NULL | оценка 2–5 (диапазон проверяет `GradeValidator`) |
| date | INTEGER, NOT NULL | дата как число дней от 1970-01-01 (`LocalDate.toEpochDay`) |
| workType | TEXT, NOT NULL | CONTROL, PRACTICE, LAB, ANSWER, OTHER |
| comment | TEXT, NOT NULL | комментарий, по умолчанию пустая строка |

## Связь и удаление
Внешний ключ с `ON DELETE RESTRICT`: дисциплину с оценками удалить нельзя. Репозиторий заранее проверяет это и возвращает `HasGrades(N)` — интерфейс покажет «Сначала удалите оценки (N)». Оценка с несуществующей дисциплиной не сохраняется (ошибка внешнего ключа).

## Слои
| Слой | Класс | Роль |
| --- | --- | --- |
| Таблицы | DisciplineEntity, GradeEntity | структура данных |
| Доступ | DisciplineDao, GradeDao | добавление, чтение (Flow), изменение, удаление |
| База | AppDatabase | подключает DAO и конвертеры |
| Репозиторий | GradeRepository | операции для приложения, правило удаления, тестовые данные |
| Состояние | AppViewModel → AppData | отдаёт данные экранам, обновляется при любом изменении таблиц |

Переход на новую версию схемы (спринты 3+) делается миграцией; сейчас `exportSchema = false`.
