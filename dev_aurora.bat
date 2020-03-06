@echo off
setlocal enabledelayedexpansion

rem utf-8
chcp 65001>nul
set args=%*

rem procura, os arquivos .java e compila

set java_files=

for /f "tokens=*" %%f in ('dir /b /s *.java') do (
    set java_files=%%f !java_files!
)

javac %java_files% -d ./compiled/

rem set parent_folder=%cd%
set test_folder=%cd%\test

cd compiled

java aurora.app.Aurora %* %test_folder%\aurora.au

endlocal
exit /b /0