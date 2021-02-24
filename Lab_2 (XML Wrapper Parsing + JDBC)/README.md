# Распределённые информационные системы. Задача 1.
Знакомство с системой сборки Gradle, работа с XML с использованием StAX API.

## Задача 1. Часть 1.
Инициировать проект в Gradle, настроить плагин application, подключить библиотеки:
- Log4j2.
- Apache commons compress.
- Apache commons CLI.

## Задача 1. Часть 2.
Написать классы, обрабатывающие входной файл XML в архиве формата bzip2 (RU-NVS.osm.bz2), содержащий данные Open Street Map по Новосибирску, используя StAX API. 
Программа должна подсчитать и выдать следующую статистику:
- Количество правок внесенных каждым пользователем, отсортированное в обратном порядке по количеству правок (атрибут 'user' в тегах 'node'). 
- Количество уникальных имен ключей с количеством тегов ‘node’, помеченных ими.