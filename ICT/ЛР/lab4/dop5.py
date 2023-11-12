def make_csv(data, parent_key='', csv_str=''):
    for key, value in data.items():
        if isinstance(value, dict):
            csv_str = make_csv(value, f'{parent_key}{key}.', csv_str)
        elif isinstance(value, list):
            for i, item in enumerate(value, start=1):
                csv_str = make_csv(item, f'{parent_key}{key}{i}.', csv_str)
        else:
            csv_str += f'{parent_key}{key},{value}\n'
    return csv_str

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

f = open("in.yml")
tabs_prev = 0
st = f.readlines()
open_dash = []
data = add_part({}, 0, 0, st, open_dash)[0]
csv_data = make_csv(data)
with open('res_dop5.csv', 'w') as out_file:
    print(csv_data, file=out_file)
