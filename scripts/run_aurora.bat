@echo off
setlocal enabledelayedexpansion

set "src_dir=%cd%"
cd %AURORA_PATH%

rem utf-8
chcp 65001>nul
set args=%*

rem procura, os arquivos .java e compila

set java_files=

for /f "tokens=*" %%f in ('dir /b /s *.java') do (
    set java_files=%%f !java_files!
)

javac --enable-preview -source 14 %java_files% -d ./compiled/

rem set parent_folder=%cd%
rem set test_folder=%cd%\test

cd compiled

java --enable-preview aurora.app.Aurora %*

endlocal
exit /b /0