import re
import time

start_time = time.time()

for _ in range(1000):
    f = open('data.yml')
    lines = [i.replace('\n', '') for i in f]
    st_out = '\n'.join(lines)

    st_out = re.sub(r"---", '<?xml version="1.0" encoding="UTF-8"?>\n<schedule>\n', st_out)

    st_out = re.sub("(\w+): (.+)", r'<\1>\2</\1>', st_out)

    for i in re.findall(r"(^\s*\S*\n(\s{4}.+)+)", st_out, flags=re.MULTILINE):
        st = ''.join(i[:1])
        old_st = st
        match = re.findall(r"(\S*):$", st, flags=re.MULTILINE)
        st = st.replace(f"{match[0]}:", f'<{match[0]}>')
        st += f'</{match[0]}>\n'
        st_out = st_out.replace(old_st, st, 1)

    open_lists = re.findall(r"(\S*):$\n-", st_out, flags=re.MULTILINE)
    st_out = re.sub(r"^-", f"</{open_lists[0]}>\n<{open_lists[0]}>\n", st_out, flags=re.MULTILINE)
    st_out = re.sub(rf"({open_lists[0]}:\n</{open_lists[0]}>)", '', st_out, flags=re.MULTILINE)
    st_out += f"</{open_lists[0]}>\n</schedule>"

    with open('res_dop2.xml', 'w') as f:
        print(st_out, file=f)

stop_time = time.time()

print('Время: ', stop_time - start_time, ' секунд')