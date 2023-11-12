import time


def add_part(a, i, prev_tabs, st, open_dash):
    while st[i].replace(' ', '')[0] == '-':
        if st[i - 1].split(':')[1].replace(' ', '').replace('\n', '') == '':
            open_dash.append(st[i - 1].split(':')[0].replace(' ', ''))
            a[open_dash[-1]] = []
        st[i] = st[i].replace('-', ' ')
        dop_part = add_part({}, i, prev_tabs, st, open_dash)
        a[open_dash[-1]].append(dop_part[0])
        i = dop_part[1]
        if i >= len(st):
            break
    if i >= len(st):
        return a, i
    cur_tabs = st[i].count('  ')
    if cur_tabs - prev_tabs > 0:
        while True:
            cur_st = st[i].split(':')
            if cur_st[1].replace(' ', '') == '\n':
                dop_part = add_part({}, i + 1, prev_tabs, st, open_dash)
                a[cur_st[0].replace(' ', '')] = dop_part[0]
                i = dop_part[1]
            else:
                if cur_st[1][0] == ' ':
                    a[cur_st[0].replace(' ', '')] = ':'.join(cur_st[1:]).replace('\n', '')[1:]
                else:
                    a[cur_st[0].replace(' ', '')] = ':'.join(cur_st[1:]).replace('\n', '')
                i += 1
            prev_tabs = cur_tabs
            if i >= len(st):
                return a, i
            cur_tabs = st[i].count('  ')
            if not cur_tabs - prev_tabs == 0:
                return a, i
    elif cur_tabs - prev_tabs == 0:
        if st[i + 1].split(':')[0].replace(' ', '')[0] != '-':
            cur_st = st[i].split(':')
            a[cur_st[0].replace(' ', '')] = add_part({}, i + 1, cur_tabs, st, open_dash)[0]
            return a, i
        else:
            return add_part({}, i + 1, 0, st, open_dash)


def make_xml(collection, parent=None):
    if parent is None:
        xml_str = '\n'
        xml_str += make_xml(collection, parent="")
        return xml_str
    else:
        xml_str = ''
        for key, value in collection.items():
            xml_str += f"<{key}>"
            if isinstance(value, dict):
                xml_str += make_xml(value)
            elif isinstance(value, list):
                for item in value:
                    xml_str += make_xml(item)
            else:
                xml_str += str(value)
            xml_str += f"</{key}>"
        return xml_str


start_time = time.time()
for _ in range(1000):
    f = open("in1.yml")
    tabs_prev = 0
    st = f.readlines()
    open_dash = []
    data = add_part({}, 0, 0, st, open_dash)[0]
    st_res = make_xml(data)
    st_res = '<?xml version="1.0" encoding="UTF-8"?>' + st_res

    with open("res_dop3.xml", 'w') as out_file:
        print(st_res, file=out_file)

stop_time = time.time()
print('Время: ', stop_time - start_time, ' секунд')
