#language:ru
@test
Функционал: Тестирование корректности расчета 10x
  - Создание POST запроса с телом из json файла
  - Проверка статус кода и сравнение результата расчета

  Сценарий: Тестирование корректности расчета
    * создать контекстные переменные
      | result       |  22.0 |
    # ООООООО
    * создать запрос
      | method | url                                              | body        |
      | POST   | http://localhost:8089/api/calculations/operation | post10.json |
    * добавить header
      | Content-Type | application/json |
    * отправить запрос
    * статус код 200
    * извлечь данные
      |  result_1 | $.result |
    * сравнить значения
      | ${result_1} | == | ${result}|