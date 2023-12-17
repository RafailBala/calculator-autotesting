#language:ru
@test

Функционал: Тестирование сервиса Helpdesk
  - Создание нового тикета POST запросом с телом из json файла, значения которого заполняем сгенерированным значениями
  - После создания нового тикета, GET запросом запрашиваем данный тикет и проверяем, что его данные соответствует данным из тела запроса

  Сценарий: Создание тикета
    # Первая часть теста - Создание тикета. Эти данные подставятся в тело запроса в шаблон тела файла createTicket.json
    # Генерится дандомная страка по маске
        # E - Английская буква,
        # R - русская буква,
        # D - цифра. Остальные символы игнорятся
        # Условна дана строка TEST_EEE_DDD_RRR - снегерится примерно такая - TEST_QRG_904_ЙЦУ

    * сгенерировать переменные
      | title          |  EEEEEEE                 |
      | submitter_email|  EEEEEEE@EEEDDD.EE       |
      | status         |  1                       |
      | on_hold        |  true                    |
      | description    |  EEEEE                   |
      | resolution     |  ResolutionEEE           |
      | priority       |  1                       |
      | secret_key     |  KeyEEEDDD               |
      | queue          |  1                       |

    # Создаем тикет
    * создать запрос
      | method | url                                               | body                      |
      | POST   | https://ft-sandbox.workbench.lanit.ru/api/tickets | helpdeskCreateTicket.json |
    * добавить header
      | Content-Type | application/json |
    * отправить запрос
    * статус код 201
    * извлечь данные
      |  id_ticket | $.id |
    * сравнить значения
      | ${id_ticket} | != | null |

     # Вторая часть теста - получение токена и тикета для сравнения данных

     # получение токена
    * создать запрос
      | method | url                                               | body                    |
      | POST   | https://at-sandbox.workbench.lanit.ru/api/login   | helpdeskCreateUser.json |
    * добавить header
      | Content-Type | application/json |
    * отправить запрос
    * статус код 200
    * извлечь данные
      | token | $.token |

     # Почему-то все время возвращается один и тот же токен "5bdc32b94e12f22b5236d8bfe3abcb7e34e7012b"
     # который получает "недопустимый токен", проверял и в постман, там тоже возвращается этот же токен и не проходит.


     # получение тикета и сравннеие данных
    * создать запрос
      | method | url                                                            |
      | GET    | https://ft-sandbox.workbench.lanit.ru/api/tickets/${id_ticket} |
    * добавить header
      | Content-Type  | application/json                              |
      | Authorization | Token 9a4153920acabd6e76be36ca5274318de8eac681|
    * отправить запрос
    * статус код 200
    * извлечь данные
      | resp_title          | $.title          |
      | resp_submitter_email| $.submitter_email|
      | resp_status         | $.status         |
      | resp_on_hold        | $.on_hold        |
      | resp_description    | $.description    |
      | resp_resolution     | $.resolution     |
      | resp_priority       | $.priority       |
      | resp_secret_key     | $.secret_key     |
      | resp_queue          | $.queue          |

    * сравнить значения
      | ${title}             |==|  ${resp_title}           |
      | ${submitter_email}   |==|  ${resp_submitter_email} |
      | ${status}            |==|  ${resp_status}          |
      | ${on_hold}           |==|  ${resp_on_hold}         |
      | ${description}       |==|  ${resp_description}     |
      | ${resolution}        |==|  ${resp_resolution}      |
      | ${priority}          |==|  ${resp_priority}        |
      | ${secret_key}        |==|  ${resp_secret_key}      |
      | ${queue}             |==|  ${resp_queue}           |



