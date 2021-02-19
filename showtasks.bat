call runcrud
if "%ERRORLEVEL%" == "0" goto runchrome
echo.
echo RUNCRUD has errors - breaking work
goto fail

:runchrome
start /d "C:\Users\Lenovo-PC\AppData\Local\Google\Chrome\Application" chrome.exe "http://localhost:8080/crud/v1/task/getTasks"
if "%ERRORLEVEL%" == "0" goto end

:fail
echo.
echo There were errors

:end
echo.
echo work is finished.
