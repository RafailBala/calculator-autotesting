#language:ru
@test
Функционал: Тестирование корректности расчета 16x
  - Создание POST запроса с телом из json файла
  - Проверка статус кода и сравнение результата расчета

  Сценарий: Тестирование корректности расчета
    Когда создать контекстные переменные
      | result       |  19.0 |
    # ООООООО
    И создать запрос
      | method | url                                              | body           |
      | POST   | http://localhost:8089/api/calculations/operation | post16.json |
    * добавить header
      | Content-Type | application/json |
    * отправить запрос
    * статус код 200
    * извлечь данные
      |  result_1 | $.result |
    * сравнить значения
      | ${result_1} | == | ${result}|