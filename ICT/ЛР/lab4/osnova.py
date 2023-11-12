f = open('data.yml')
st_out = ''
lines = [i.replace('\n', '') for i in f]
open_curves = []
tabs = 0
first_slash = False

for n in lines:
    if n == '---':
        st_out += '<?xml version="1.0" encoding="UTF-8"?>\n'
        st_out += '<schedule>\n'
    if ':' in n:
        tabs_prev = tabs
        tabs = n.count('  ')
        if (tabs_prev - tabs) > 0:
            st_out += '\t' * (len(open_curves))
            st_out += f'</{open_curves[-1]}>\n'
            open_curves.pop(-1)
        open_curves.append(n[:n.index(':')].replace(' ', ''))
        if (n.index(':') != 0) and (n[n.index(':') + 1:] != '') and (n[0] != '-'):
            st_out += '\t' * len(open_curves)
            st_out += f'<{open_curves[-1]}>'
            st_out += n[n.index(':') + 2:] + f"</{open_curves[-1]}>\n"
            open_curves.pop(-1)
        else:
            if n[0] == '-':
                if first_slash:
                    st_out += '\t' * (len(open_curves) - 1)
                    st_out += f'</{open_curves[0]}>\n'
                    st_out += '\t' * (len(open_curves) - 1)
                    st_out += f'<{open_curves[0]}>\n'
                st_out += '\t' * len(open_curves)
                st_out += f'<{open_curves[-1][1:]}>'
                st_out += n[n.index(':') + 2:] + f"</{open_curves[-1][1:]}>\n"
                open_curves.pop(-1)
                first_slash = True
            else:
                st_out += '\t' * len(open_curves)
                st_out += f'<{open_curves[-1]}>\n'
st_out += '\t' * (len(open_curves))
st_out += f'</{open_curves[-1]}>\n'
open_curves.pop(-1)
st_out += '\t' * (len(open_curves))
st_out += f'</{open_curves[0]}>\n'
st_out += '</schedule>'


with open('res_osn.xml', 'w') as file:
    print(st_out, file=file)
