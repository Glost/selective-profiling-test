# selective-profiling-test
The test for joining the selective profiling project at the JetBrains internship, 2017.

# Схема вывода на экран
На экран дерево выводится следующим способом: выводится корневая вершина, после неё и стрелки вправо выводятся через точку с запятой все её потомки.
На следующей строке выводится первый из её потомков таким же способом: сначала потомок, потом все его потомки.
И далее это точно так же продолжается рекурсивно.
Данный способ вывода (вместо рисования полноценных деревьев) выбран в связи с сильным ограничением места в окне консоли.

# Запись в файл
Запись производится в бинарный файл. Отказ от более понятных человеку представлений, таких как XML и JSON, сделан в пользу меньшего размера файла и большей производительности (при больших деревьях вызова это может быть критично):
программа просто подряд пишет и читает значения из файла, нет никакой "тяжёлой" работы с текстом.
Формат бинарного файла следующий: сначала записывается количество задач (соответствующее количеству деревьев вызовов).
Далее подряд, без любых разделителей записаны деревья, следующим образом:
1) в самом начале записана корневая вершина: сначала записаны название метода и строчное представление аргумента вызова, затем число потомков вершины;
2) затем рекурсивно записаны её потомки аналогичным образом.

В принципе, данный метод во многом аналогичен встроенной в Java сериализации (для объектов классов, реализующих интерфейс Serializable), однако, возможно, позволяет лучше контролировать процесс.
