Это консольное приложение основанное на SpringBoot + hibernate + PostgreSql. 
Признаюсь, далеко не самое удобное, и как оказалось, совсем не быстрое, но хотелось попробовать.

Параметры передаются через командную строку. 
Пример Команды:

 java -jar DemoApplication user=guest ip=127.0.0.2 replenishResource amount=15 resource=resource1 replenishResource amount=200 resource=resource2 addResourceToInventory resource=rresource1 addResourceToInventory resource=rresource2  listAllAvailableResources

То есть запуск приложения с такой строкой должен подключиться под пользователем guest (ip 127.0.0.1) и выполнить последовательно такие действия:
 - replenishResource amount=15 resource=resource1  - добавить расходный ресурс 1
- replenishResource amount=200 resource=resource2- добавить расходный ресурс 2
- addResourceToInventory resource=rresource1 - добавить  ресурс для аренды 1
- addResourceToInventory resource=rresource2 - добавить  ресурс для аренды 2
- listAllAvailableResources - вывести список всех ресурсов

Отфильрованный вывод такой:
OUTPUT: === replenishResource:
OUTPUT: 2 resource1 amount=15
OUTPUT: === replenishResource:
OUTPUT: 4 resource2 amount=200
OUTPUT: === addResourceToInventory: Resource rresource1 was added to Inventory
OUTPUT: === addResourceToInventory: Resource rresource2 was added to Inventory
OUTPUT: === listAllAvailableResources:
OUTPUT: 2 resource1 amount=15
OUTPUT: 4 resource2 amount=200
OUTPUT: 8 rresource2 status=AVAILABLE
OUTPUT: 6 rresource1 status=AVAILABLE

В лог таблице фиксируются такие записи  на каждое действие пользователя :
postgres=# select * from   user_action_log_record;
 id |           dt            | ip_address |                             props                              | user_id
----+-------------------------+------------+----------------------------------------------------------------+---------
  3 | 2018-12-02 20:04:43.928 | 127.0.0.2  | result=success,amount=15,resource=2,action=replenishResource,  |       1
  5 | 2018-12-02 20:04:44     | 127.0.0.2  | result=success,amount=200,resource=4,action=replenishResource, |       1
  7 | 2018-12-02 20:04:44.028 | 127.0.0.2  | result=success,resource=6,action=addResourceToInventory,       |       1
  9 | 2018-12-02 20:04:44.064 | 127.0.0.2  | result=success,resource=8,action=addResourceToInventory,       |       1
 10 | 2018-12-02 20:04:44.103 | 127.0.0.2  | result=success,action=listAllAvailableResources,               |       1
(5 rows)


 * 
 * Параметры user и ip должны быть указаны вначале
 * дальше указывается action и параметры для этого действия 
 * В командной строке можно указывать несколько действий
 * 
 * Actions&Parameters:
 * replenishResource  - пополняет расходный ресурс
 *      amount=<resource income>
 *      resource=<resourceName>
 * addResourceToInventory - добавляет ресурс для аренды
 *      resource=<resourceName>
 * listResourcesForConsumption  - список доступных расходных ресурсов 
 * 
 * listAllAvailableResources - список всех доступных ресурсов
 * 
 * consumeResource  - пополняет расходный ресурс
 *      amount=<resource quantity>
 *      resource=<resourceName>
 * listConsumedResources  - список расходных ресурсов которые закончились на данный момент
 * checkoutResource - возвращает ресурс
 *      resource=<resourceName>
 * checkinResource - берет в пользование ресурс
 *      resource=<resourceName>
 * listResourcesForRent - список доступных ресурсов для аренды
 * 
