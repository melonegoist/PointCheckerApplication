# Point Checker Application

## Описание проекта

Point Checker - это SPA веб-приложение, позволяющее пользователям проверять попадание точек в заданную область на графике. Приложение предоставляет следующие возможности:

- Регистрация и аутентификация пользователей с использованием JWT
- Добавление точек через форму или клик по графику
- Визуализация результатов (попадание/непопадание)
- Хранение истории всех попыток пользователя
- Просмотр статистики по активности пользователя

## Технологический стек

### Backend
- **Фреймворк**: Spring Boot
- **База данных**: PostgreSQL + Hibernate ORM
- **Аутентификация**: Spring Security + JWT
- **Сборка**: Gradle + Groovy
- **Тестирование**:
  - Модульное: JUnit + Mockito
  - Функциональное: Selenium
- **Мониторинг**:
  - Сбор метрик: MBean
  - Визуализация: Prometheus + Grafana
  - Профилирование: JConsole, VisualVM

### Frontend
- **Фреймворк**: Vue.js + Vite

**Взаимодействие между backend и frontend организовано посредством REST API**

## Функциональность

### Пользовательская часть
- Регистрация нового пользователя
- Авторизация с сохранением сессии (JWT)
- Выход из аккаунта
- Добавление точек:
  - Через форму с валидацией
  - Кликом по графику
- Просмотр истории всех попыток
- Просмотр статистики:
  - Общее количество точек
  - Количество попаданий/непопаданий
  - Интервалы между кликами

### Системная часть
- Защита от SQL-инъекций
- Защита от выполнения постороннего кода
- Ограничение доступа к API для неавторизованных пользователей
- Автоматическое продление сессии через JWT

## Gradle задачи

Проект включает следующие пользовательские Gradle задачи:

1. Кастомная реализация компиляции
2. Кастомная реализация сборки проекта
3. Запуск тестов (модульные + функциональные)
4. Откат к предыдущим коммитам при ошибках компиляции
5. Генерация Javadoc
6. Создание XML-отчетов
7. Преобразование native2Ascii
8. Валидация XML-файлов проекта
9. Развертывание на удаленный сервер через SCP
10. Поддержка автокоммитов

## Эндпоинты api

_AuthController:_

### `/authenticate`
- **метод**: POST
- **описание**: Аутентификация пользователя и выдача JWT-токена
- **параметры**: 	{ username, password } (JSON)
- **Возвращаемый тип**: { jwt } (токен) или ошибка 401

### `/register`
- **метод**: POST
- **описание**: Регистрация нового пользователя
- **параметры**: 	{ username, password } (JSON)
- **Возвращаемый тип**: { message } или ошибка 409 (если пользователь существует)

### `/validateToken`
- **метод**: POST
- **описание**: Валидация JWT-токена
- **параметры**: 	{ token } (JSON)
- **Возвращаемый тип**: { isValid (boolean), username (если valid) }

_GraphController:_

### `/graph`
- **метод**: POST
- **описание**: Проверка попадания точки в область
- **параметры**: 	{ x, y, r, owner } (JSON)
- **Возвращаемый тип**: { x, y, r, inArea, notificationState }

### `/graph/dots`
- **метод**: GET
- **описание**: Получение всех точек пользователя
- **параметры**: 	username (query parameter)
- **Возвращаемый тип**: List<{ x, y, r, inArea, owner }>

## Скриншоты работы

### Стартовая страница авторизации

![Pasted image](https://github.com/user-attachments/assets/75f71310-f556-45c0-855d-05e5884d7632)

### Попытка войти с неверными данными

![wrong_username](https://github.com/user-attachments/assets/bcf9bdf1-295b-4d99-a8b9-0194fdd20914)

### Страница регистрации

![reg](https://github.com/user-attachments/assets/6a3be5e6-1bc0-4d2a-bebb-46d6e8be91f8)

### Успешный вход в аккаунт

![graph](https://github.com/user-attachments/assets/45c3d306-034f-45e2-9058-5461433262c1)

### Попытка построить точку вне заданных границ

![wrong_range](https://github.com/user-attachments/assets/79ad3f20-9540-4e71-a1f3-58fc885c91a4)

### Попытка отправить форму с пустым полем

![empty_field](https://github.com/user-attachments/assets/fe4d1558-164b-43e4-840a-630966adc762)

### Попытка отправить форму с вводом букв

![words_validation](https://github.com/user-attachments/assets/4e26f311-9184-4158-8407-b2cab75b5260)

### Отрисовка графика при верном вводе

![correct_graph](https://github.com/user-attachments/assets/58eded66-3bdb-4e7e-95e2-d3e00909bf05)

### Отрисовка точки по вводу через форму

![just_dot](https://github.com/user-attachments/assets/b3fea71e-bddf-45dd-9025-eae35391ec3a)

### Отрисовка точки по клику

![after_click2](https://github.com/user-attachments/assets/833db2b5-785f-47bb-9c28-be9a7b138c42)

### Список точек пользователя

![dots_list](https://github.com/user-attachments/assets/5de8794b-667c-482c-aa2f-e4185becfe61)

### Графики метрик пользователя (количество точек, количество непопаданий, средний интервал между кликами)

![metrics](https://github.com/user-attachments/assets/869f75f3-93cd-4256-83b1-223105e32f65)

### Сгенерированный через Gradle JavaDoc

![javadoc](https://github.com/user-attachments/assets/9e8d77d7-24ed-4e63-9fec-f328d97f50d1)


---
Реализовано `melon_egoist`

