Пользовательские инструкции.

 - Установить базу данных postgreSql 9.6
 - Настроить ее параметры в файле src/main/resources/application.properties
 - Создать таблицы, используя sql скрипты в файле src/main/sqlTablesDumb/tables/sql
 - Настроить параметры email отправителя в файле src/main/resources/application.properties
 - Запустить приложение.
 - Проверить работоспособность email rest запросом /auth/testEmail?emailTo=email_получателя
 - Если email отправляется, раскомментировать все использования emailService в UsersController

 Сценарии использования REST сервера клиентом:

/////////////////////////////////////////////////////////////////////////////////
 Авторизация:

 POST запрос /auth/login c json параметрами:

    {
         "login": "test",
         "password": "test"
    }

 Пример ответа:

     {
         "userId": "10207e39-d66d-4ea0-9f0d-8f3f060012f4",
         "username": "admin",
         "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInJvbGVzIjpbIlVTRVIiLCJBRE1JTiJdLCJpYXQiOjE1ODk1MTYzMTMsImV4cCI6MTU4OTUxODExM30.HgeiF8fVayWe8XvpgZH_VN4mV2WvfCZ9zT2rz63xvxE"
     }

 Клиенту нужно сохранить свой userId и jwtToken для дальнейших запросов
 jwtToken вставляется в заголовок запросов Authorization c префиксом Bearer_

/////////////////////////////////////////////////////////////////////////////////
 Регистрация:

 POST запрос /auth/register c json параметрами:

    {
        "login": "test1",
        "password": "test",
        "email": "petr@gmail.com",
        "firstName": "Петр",
        "familyName": "Петров",
        "patronymic": "Петрович"
    }

Ответ: статус 200 OK

Все последущие запросы выполняются с использованием токена, полученного при авторизации
/////////////////////////////////////////////////////////////////////////////////
 Получение списка всех оказанных услуг c пагинацией(Доступно только админу):

 GET запрос /admin/seasonServices/getAll с json параметрами:

     {
        "pageNumber": 0,
        "pageSize": 10
     }

 Пример ответа:

 [
     {
         "id": "f884eb5e-6ea5-47d0-9466-bf84349204c0",
         "serviceName": "Выдача охотбилетов единого федерального образца",
         "creationDateTime": 1589282732208,
         "serialNumber": 3
     },
     {
         "id": "6b1480db-e39e-4b7e-b573-efbed70b4b2a",
         "serviceName": "Выдача охотбилетов единого федерального образца",
         "creationDateTime": 1589282732208,
         "serialNumber": 4
     },
     {
         "id": "35efda8a-1e75-4b71-95bd-7c017dd75bc8",
         "serviceName": "Выдача охотбилетов единого федерального образца",
         "creationDateTime": 0,
         "serialNumber": 1
     },
     {
         "id": "c42d66cb-d753-48a5-bbee-547dba3b2a03",
         "serviceName": "Выдача охотбилетов единого федерального образца",
         "creationDateTime": 0,
         "serialNumber": 2
     }
 ]

/////////////////////////////////////////////////////////////////////////////////
Получение списка всех оказанных услуг конкретного пользователя по uuid юзера c пагинацией(Доступно админу, юзеру):

GET запрос /users/seasonServices/getAll с json параметрами:

    {
        "userId": "10207e39-d66d-4ea0-9f0d-8f3f060012f4",
        "pageNumber": 0,
        "pageSize": 10
    }

Пример ответа:

    [
        {
            "id": "f884eb5e-6ea5-47d0-9466-bf84349204c0",
            "serviceName": "Выдача охотбилетов единого федерального образца",
            "creationDateTime": 1589282732208,
            "serialNumber": 3
        },
        {
            "id": "6b1480db-e39e-4b7e-b573-efbed70b4b2a",
            "serviceName": "Выдача охотбилетов единого федерального образца",
            "creationDateTime": 1589282732208,
            "serialNumber": 4
        }
    ]

/////////////////////////////////////////////////////////////////////////////////
Получение полной информации по оказанной услуге по uuid услуги (Доступно юзеру, админу)

GET запрос /users/seasonServices/getOne/{providedServiceID}

    {
        "userId": "10207e39-d66d-4ea0-9f0d-8f3f060012f4",
        "pageNumber": 0,
        "pageSize": 10
    }

Пример ответа:

    {
        "id": "f884eb5e-6ea5-47d0-9466-bf84349204c0",
        "serviceName": "Выдача охотбилетов единого федерального образца",
        "creationDateTime": 1589282732208,
        "serialNumber": 3,
        "firstName": "first name",
        "familyName": "family name",
        "patronymic": null,
        "provisionDate": 1589516605723
    }

/////////////////////////////////////////////////////////////////////////////////
Запрос на осуществление услуги (Доступно юзеру, админу)

POST запрос /users/seasonServices/provide с json параметрами:

    {
        "userId": "10207e39-d66d-4ea0-9f0d-8f3f060012f4",
        "serviceName": "Выдача охотбилетов единого федерального образца",
        "creationDateTime": 1589282732208
    }

Пример ответа:

    {
        "serialNumber": 5
    }

    Серийный номер заявления, который после запроса выводится пользователю

В случае, если услуги для оказания закончились ответ следущий:

    {
        "timestamp": "2020-05-15T05:06:25.551+0000",
        "status": 400,
        "errors": [
            "Service Выдача охотбилетов единого федерального образца has limit of usage and it's ended"
        ]
    }