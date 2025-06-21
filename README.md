## Тестовое задание
Для успешного запуска приложения необходим установленный PostgreSQL.

Собрать проект в Maven-е:
```
./mvnw package
```

Затем для запуска выполнить команду:
```
java -jar target/testtask-0.0.1-SNAPSHOT.jar \
    --spring.datasource.url=<url> \
    --spring.datasource.username=<username> \
    --spring.datasource.password=<password> \
    --spring.sql.init.mode=always 
```
где:
- url - URL для соединения с базой данных,
- username - имя пользователя,
- password - пароль пользователя.

Параметр _--spring.sql.init.mode=always_ требуется только для первой инициализации базы данных далее его нужно не указывать.
При заданном праметре будут созданы таблицы 'account', 'email_data', 'phone_data' и 'user' и заполнятся некоторыми данными.

Запрос на получение токена авторизации:
```
curl http://localhost:8080/auth \
    --header 'Content-Type: application/json' \
    --data-raw '{
        "login": "alex@example.com",
        "password":"secret"
    }'
```

Отправить запрос для поиска пользователей:
```
curl 'localhost:8080/users?email=jane%40example.com' \
    --header 'X-Page-Number: 0' \
    --header 'X-Page-Size: 10' \
    --header 'Authorization: Bearer eyJhbGciOiJSUzI1NiJ9.eyJ1c2VySWQiOjEsImV4cCI6MTc1MDc3OTM2OH0.o2w_JBWPNpa3yaTDUxn3dFEi872_V4ulPuloXhpDKrI21S8qfSCZbvl0Yq_nHXhT7q3EfxrC4pBOcrlxv3SwlhvpDdZ6mhy3plLkC4GHg6ON4_djWGXy1a4sBj28QvxmF5LPqNVE1YN_i_EDucAGGNmh_TPexuPzxtNMSorcxWGIz7z3C-AMe39B9FAtglYm985-hGhSiy-8vUvmWArJpUYgy1-b6FsTiTbSkJBCMYMFgtbXgRjfpz2pzySJYmld758dGOLjzYEEDTFcoDUK-Bmh6O28HMIY1Zq8ltR3CLskUiR9ZVn2WzNLpMIpFH4zRb9qDiiavkMPSsBVrCP6Jw' 
```
Токен с длительным временем действия.
### Имплементированные api:

POST /auth - авторизация пользователя, тело запроса:
```
{
    "login": "alex@example.com",
    "password":"secret"
}
```

GET /users - поиск по пользователям, параметры URL:
- email ("jane@example.com")
- name ("jan")
- phone ("12345678901")
- dateOfBirth ("01.01.1990")

Заголовки запроса:
- X-Page-Size (2) - количество записей на страницу
- X-Page-Number (0) - номер страницы начиная с 0

POST /phones - добавить номер телефона для пользователя, тело запроса:
```
{
    "phone": "12312312312"
}
```

PUT /phones/{id} - обновить номер телефона, тело запроса:
```
{
    "phone": "12312312312"
}
```

DELETE /phones/{id} - удалить номер телефона


POST /emails - добавить email для пользователя, тело запроса:
```
{
    "email": "my@email.com"
}
```

PUT /emails/{id} - обновить email, тело запроса:
```
{
    "email": "my@email.com"
}
```

DELETE /emails/{id} - удалить email

