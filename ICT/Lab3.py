import re

#Задание 1
smile = r"=-{P"

def calc_smiles(st):
  res = re.findall(smile, st)
  print(len(res))


calc_smiles("=-{P;<O=<|=-{P9<{P") # 2
calc_smiles("=-{):-|;<\=<") # 0
calc_smiles("[-{(X<P=-{P:<(8<|=-{|") # 1
calc_smiles("=-{P=-{P=-{P=-{P=-{P") # 5
calc_smiles("X-{P=-{O=-{P=<{P=-(") # 1

#Задание 2
def print_res(st):
  reg_exp = "(([$0-1][0-9$]|[$2][0-3$]):[$0-5][0-9$]:[$0-5][0-9$])|(([$0-1][0-9$]|[$2][0-3$]):[$0-5][0-9$])"
  res = re.sub(reg_exp, '(TBD)', st)
  print(res)

print_res('Уважаемые студенты! В эту субботу в 15:00 планируется доп. занятие на 2 часа. То есть в 23:00:01 оно уже точно кончится.')
print_res('Сегодня поезд отправится в 20:00:00, ранее об был задержан на 15 часов и должен был отправится в 05:05:05')
print_res('В связи с ошибкой в системе наши часы 3 дня показыли время 45:20 ошибка была исправлена, сейчас они показывают 20:59')
print_res('20:15 это то же самое что 21:00')
print_res('25:40:60 не должно быть показано, заметить на 20:41:54')

#Задание 3
def check(st):
  words_sep = r"\b\w+\b"
  louds = ['Аа','Ее','Ёё','Ии','Оо','Уу','Ыы','Ээ','Юю','Яя']
  words = re.findall(words_sep, st)
  res = []
  for i in words:
    for j in louds:
      reg_exp = fr"^(?:[^аеёиоуыэюяАЕЁИОУЫЭЮЯ]*[{j}][^аеёиоуыэюяАЕЁИОУЫЭЮЯ]*)*$"
      n = re.findall(reg_exp, i)
      if len(n) > 0:
        res.append(n[0])
  res.sort(key=lambda x: (len(x), x))
  for s in res:
    print(s)


check('Классное слово – обороноспособность, которое должно идти после слов: трава и молоко.')
print('\n')
check('Много слов в которх похож гласн')
print('\n')
check('Строка, нету совпадений')
print('\n')
check('Строка, совпадение одно')
print('\n')
check('')

#Задание 4 (доп)
tweet = 'Good advice! RT @TheNextWeb: What I would do differently if I was learning to code today https://t.co/lbwejOpx0d cc: @garybernhardt #rstats'
reg_exp = r"RT|cc|(#\S*)|(@\S*)|(http\S*)|[,.!?;:]"

res = re.sub(reg_exp, "", tweet)
print(res)
