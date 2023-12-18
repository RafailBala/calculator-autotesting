#language:ru
@test

Функционал: Тестирование данных за определенный промежуток времени
  - Создание Get запроса с телом из json файла, значения которого являются входными данными
  - После проверяет статус код ответа и сравниваем данные ответа со знаениями из файла

  Сценарий: Тестирвоание данных за определенный промежуток времени
    Дано даты "2023-11-12" и "2023-12-15"
    И создать запрос
      |method  | url                                                      | body                     |
      | GET    | http://localhost:8089/api/calculations/findByDateBetween | findByDateBetween.json  |
    И добавить header
      | Content-Type | application/json |
    Тогда отправить запрос
    И статус код 200
    Когда извлечь все данные
    И сравнить все значения из файла "C:\\Users\\balae\\IdeaProjects\\calculator-autotesting\\src\\main\\resources\\db\\data\\betweenDate.json"