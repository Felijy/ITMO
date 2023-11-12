def dict_to_csv(data, parent_key='', csv_str=''):
    for key, value in data.items():
        if isinstance(value, dict):
            csv_str = dict_to_csv(value, f'{parent_key}{key}.', csv_str)
        elif isinstance(value, list):
            for i, item in enumerate(value, start=1):
                csv_str = dict_to_csv(item, f'{parent_key}{key}{i}.', csv_str)
        else:
            csv_str += f'{parent_key}{key},{value}\n'
    return csv_str


data = {'schedule': {'day_number': '2', 'week_number': '11', 'date': '"2023-11-07"',
                     'lessons': {'lessons': [{'id': 'lesson 206', 'name': 'Математический анализ',
                                              'lesson_type': 'Лекции', 'group': 'МАТ АН ПИиКТ 13',
                                              'time': {'start': '08:20', 'end': '09:50'},
                                              'teacher_name': 'Правдин Константин Владимирович',
                                              'place': {'campus': 'Кронверкский пр., д.49, лит.А',
                                                        'room': 'Ауд.Orange Classroom (1229)',
                                                        'distant': 'Очно - дистанционный'}},
                                             {'id': 'lesson 210', 'name': 'Математический анализ',
                                              'lesson_type': 'Практические занятия',
                                              'group': 'МАТ АН ПИиКТ 13.3', 'time': {'start': '10:00', 'end': '11:30'},
                                              'teacher_name': 'Блейхер Оксана Владимировна',
                                              'place': {'campus': 'Кронверкский пр., д.49, лит.А',
                                                        'room': 'Ауд.2430', 'distant': 'Очно - дистанционный'}},
                                             {'id': 'lesson 217', 'name': 'Основы дискретной математики (базовый уровень)',
                                              'lesson_type': 'Лекции', 'group': 'ДИСКР МАТ 2',
                                              'time': {'start': '11:40', 'end': '13:10'},
                                              'teacher_name': 'Поляков Владимир Иванович',
                                              'place': {'campus': 'Кронверкский пр., д.49, лит.А',
                                                        'room': 'Ауд.2337', 'distant': 'Очно - дистанционный'}},
                                             {'id': 'lesson 220',
                                              'name': 'Основы дискретной математики (базовый уровень)',
                                              'lesson_type': 'Практические занятия', 'group': 'ДИСКР МАТ 2.1',
                                              'time': {'start': '13:30', 'end': '15:00'},
                                              'teacher_name': 'Поляков Владимир Иванович',
                                              'place': {'campus': 'Кронверкский пр., д.49, лит.А',
                                                        'room': 'Ауд.2337', 'distant': 'Очно - дистанционный'}}]}}}

# Save to file
csv_data = dict_to_csv(data)
print(csv_data)
