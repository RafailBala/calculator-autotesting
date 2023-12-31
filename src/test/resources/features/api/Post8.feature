#language:ru
@test
Функционал: Тестирование корректности расчета 8x
  - Создание POST запроса с телом из json файла
  - Проверка статус кода и сравнение результата расчета

  Сценарий: Тестирование корректности расчета
    Когда создать контекстные переменные
      | result       |  0.8888888888888888 |
    # ООООООО
    И создать запрос
      | method | url                                              | body       |
      | POST   | http://localhost:8089/api/calculations/operation | post8.json |
    И добавить header
      | Content-Type | application/json |
    Тогда отправить запрос
    И статус код 200
    Когда извлечь данные
      |  result_1 | $.result |
    И сравнить значения
      | ${result_1} | == | ${result}|