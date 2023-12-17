#language:ru
@test

Функционал: Тестирование сервиса Helpdesk
  - Создание нового тикета POST запросом с телом из json файла со статусом CLOSED(4)
  - После создания нового тикета, PUT запросом изменяем статус тикета на OPEN(1) и проверяем статус код,
    который должен соответствовать ошибке со статусом 422

  Сценарий: Создание и обновление тикета со статусом CLOSED на стутус OPEN
    # Первая часть теста - Создание тикета. Эти данные подставятся в тело запроса в шаблон тела файла createTicket.json
    # Генерится дандомная страка по маске
        # E - Английская буква,
        # R - русская буква,
        # D - цифра. Остальные символы игнорятся
        # Условна дана строка TEST_EEE_DDD_RRR - снегерится примерно такая - TEST_QRG_904_ЙЦУ


    # Создаем тикет
    * создать запрос
      | method | url                                               | body                      |
      | POST   | https://ft-sandbox.workbench.lanit.ru/api/tickets | helpdeskCreateClosedTicket.json |
    * добавить header
      | Content-Type | application/json |
    * отправить запрос
    * статус код 201
    * извлечь данные
      |  id_ticket | $.id |
    * сравнить значения
      | ${id_ticket} | != | null |

     # Вторая часть теста - получение токена и обновления тикета

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


     # изменение тикета и проверка статус кода на ошибку
    * создать запрос
      | method | url                                                            | body                      |
      | PUT    | https://ft-sandbox.workbench.lanit.ru/api/tickets/${id_ticket} | helpdeskUpdateTicket.json |
    * добавить header
      | Content-Type  | application/json                              |
      | Authorization | Token 9a4153920acabd6e76be36ca5274318de8eac681|
    * отправить запрос
    * статус код 422


