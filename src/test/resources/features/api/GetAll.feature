#language:ru
@test

Функционал: Тестирование калькулятора

  Предыстория: Данные с расчетами калькулятора
    Дано в БД имеются следующие данные
      | 1  | 12 | 10 | 12 | 10 | + | 2023-11-14 | 09:36:34.840702500 | 24 |
      | 2  | 12 | 10 | 12 | 10 | + | 2023-11-14 | 09:36:34.840702500 | 24 |
      | 3  | 12 | 10 | 12 | 10 | + | 2023-11-15 | 12:36:34.870702500 | 24 |
      | 4  | 12 | 10 | 12 | 10 | + | 2023-11-15 | 12:36:34.840702500 | 24 |
      | 5  | 12 | 10 | 12 | 10 | + | 2023-11-16 | 00:36:34.840702500 | 24 |
      | 6  | 12 | 10 | 12 | 10 | + | 2023-11-16 | 08:36:34.840702500 | 24 |
      | 7  | 12 | 10 | 12 | 10 | + | 2023-11-16 | 09:36:34.880702500 | 24 |
      | 8  | 12 | 10 | 12 | 10 | + | 2023-11-17 | 10:36:34.840702500 | 24 |
      | 9  | 12 | 10 | 12 | 10 | + | 2023-11-17 | 09:36:34.840702500 | 24 |
      | 10 | 12 | 10 | 12 | 10 | + | 2023-11-20 | 04:36:34.840702500 | 24 |
      | 11 | 12 | 10 | 12 | 10 | + | 2023-11-20 | 03:36:34.840702500 | 24 |

  Сценарий: Запрос get для получения всех данных
    #Дано файл с даннными "C:\\Users\\balae\\IdeaProjects\\calculator-autotesting\\src\\main\\resources\\db\\data\\V20231025_2__Insert.sql"
    И создать запрос
      | method | url                                        |
      | GET    | http://localhost:8089/api/calculations/all |

    Тогда отправить запрос
    И статус код 200
    Когда извлечь все данные
    И сравнить все значения из файла "C:\\Users\\balae\\IdeaProjects\\calculator-autotesting\\src\\main\\resources\\db\\data\\allData.json"


  Сценарий: Тестирвоание получения данных за определенный промежуток времени
    Дано даты "2023-11-12" и "2023-12-15"
    И создать запрос
      | method | url                                                      | body                   |
      | GET    | http://localhost:8089/api/calculations/findByDateBetween | findByDateBetween.json |
    И добавить header
      | Content-Type | application/json |
    Тогда отправить запрос
    И статус код 200
    Когда извлечь все данные
    И сравнить все значения из файла "C:\\Users\\balae\\IdeaProjects\\calculator-autotesting\\src\\main\\resources\\db\\data\\betweenDate.json"


  Сценарий: Тестирование корректности расчета 02x
    Когда создать контекстные переменные
      | result | -1.0 |
    И создать запрос
      | method | url                                              | body       |
      | POST   | http://localhost:8089/api/calculations/operation | post2.json |
    И добавить header
      | Content-Type | application/json |
    Тогда отправить запрос
    И статус код 200
    Когда извлечь данные
      | result_1 | $.result |
    И сравнить значения
      | ${result_1} | == | ${result} |


  Сценарий: Тестирование корректности расчета 08x
    Когда создать контекстные переменные
      | result | 0.8888888888888888 |
    И создать запрос
      | method | url                                              | body       |
      | POST   | http://localhost:8089/api/calculations/operation | post8.json |
    И добавить header
      | Content-Type | application/json |
    Тогда отправить запрос
    И статус код 200
    Когда извлечь данные
      | result_1 | $.result |
    И сравнить значения
      | ${result_1} | == | ${result} |

  Сценарий: Тестирование корректности расчета 10x
    * создать контекстные переменные
      | result | 22.0 |
    * создать запрос
      | method | url                                              | body        |
      | POST   | http://localhost:8089/api/calculations/operation | post10.json |
    * добавить header
      | Content-Type | application/json |
    * отправить запрос
    * статус код 200
    * извлечь данные
      | result_1 | $.result |
    * сравнить значения
      | ${result_1} | == | ${result} |


  Сценарий: Тестирование корректности расчета 16x
    Когда создать контекстные переменные
      | result | 19.0 |
    И создать запрос
      | method | url                                              | body        |
      | POST   | http://localhost:8089/api/calculations/operation | post16.json |
    * добавить header
      | Content-Type | application/json |
    * отправить запрос
    * статус код 200
    * извлечь данные
      | result_1 | $.result |
    * сравнить значения
      | ${result_1} | == | ${result} |


  Сценарий: Тестирование корректности расчета 16_10x
    Когда создать контекстные переменные
      | result | 14.0 |
    # ООООООО
    И создать запрос
      | method | url                                              | body           |
      | POST   | http://localhost:8089/api/calculations/operation | post16_10.json |
    * добавить header
      | Content-Type | application/json |
    * отправить запрос
    * статус код 200
    * извлечь данные
      | result_1 | $.result |
    * сравнить значения
      | ${result_1} | == | ${result} |