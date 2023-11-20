## 1. Spring Boot-приложение:

Логика работы в следующем:

![photo_2023-11-16_14-56-57](https://github.com/lavrenovamaria/ClientUtility/assets/84707645/0e334753-1079-49dc-8ed9-cbdc13fcf0e2)


# Приложение Client Utility

Приложение Client Utility предназначено для обработки данных из файла со значениями в соответствии с флагами, указанными пользователем.

Программа предоставляет различные функции, включая стратегии сортировки, префильтры и постфильтры, позволяя пользователям настраивать

последовательность обработки в соответствии с их требованиями.

## Использование

Для запуска приложения выполните следующую команду в терминале:


### java -jar ClientUtilityApplication.jar --in <путь_к_входному_файлу> --out <путь_к_выходному_файлу> [флаги...]


## Обработка ошибок
Неверный формат даты:
- Если встречается недопустимая дата, программа выдает исключение, указывающее конкретные записи с недопустимыми датами.

Всё создано как sequence filters, т.е. у опциональных флагов возможны комбинации[--males-only true --top 5 --last 2] выведет списик всех мужчин, далее возьмёт первых 5 из списка и из 5-ти возьмёт 2 последних.
1: --males-only true --females-only true -> all
2. --males-only false --females-only true -> all females
3. --males-only true --females-only false -> all males
4. --males-only false --females-only false -> nobody

TODO:
1. Не реализованы exceptions(+user-friendly messages)
2. Unit tests
3. Tie-tie birthday
4. Другие форматы файлов(JSON, XTML)
5. Вынос из main всех классов, согласно принципам SOLID и GoF