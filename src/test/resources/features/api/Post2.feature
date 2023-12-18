#language:ru
@test
Функционал: Тестирование корректности расчета 2x
  - Создание POST запроса с телом из json файла
  - Проверка статус кода и сравнение результата расчета

  Сценарий: Тестирование корректности расчета
    Когда создать контекстные переменные
      | result       |  -1.0 |
    И создать запрос
      | method | url                                              | body        |
      | POST   | http://localhost:8089/api/calculations/operation | post2.json |
    И добавить header
      | Content-Type | application/json |
    Тогда отправить запрос
    И статус код 200
    Когда извлечь данные
      |  result_1 | $.result |
    И сравнить значения
      | ${result_1} | == | ${result}|