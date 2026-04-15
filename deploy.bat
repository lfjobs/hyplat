@echo off
setlocal enabledelayedexpansion

:: ================= 配置区域 =================
set PROJECT_HOME=E:\my-first-project\hyplat
set TOMCAT_HOME=E:\apache-tomcat-9.0.74
set LOG_FILE=%PROJECT_HOME%\log\build.log
set WAR_NAME=hyplat.war
:: ============================================

echo.
echo ============================================================
echo [1/6] 开始 Maven 编译打包...
echo ============================================================

cd /d %PROJECT_HOME%
if not exist log mkdir log

call mvn clean package -DskipTests > %LOG_FILE% 2>&1
if %ERRORLEVEL% NEQ 0 (
    echo [ERROR] Maven 打包失败！请查看日志: %LOG_FILE%
    type %LOG_FILE%
    pause
    exit /b 1
)
echo [SUCCESS] Maven 打包成功！

echo.
echo ============================================================
echo [2/6] 停止 Tomcat...
echo ============================================================
if exist "%TOMCAT_HOME%\bin\shutdown.bat" (
    call "%TOMCAT_HOME%\bin\shutdown.bat" 2>nul
    timeout /t 3 /nobreak >nul
)
echo [SUCCESS] Tomcat 已停止

echo.
echo ============================================================
echo [3/6] 清理 Tomcat 部署文件...
echo ============================================================
:: 删除解压后的文件夹
if exist "%TOMCAT_HOME%\webapps\hyplat" (
    echo 正在删除: webapps\hyplat
    rd /s /q "%TOMCAT_HOME%\webapps\hyplat"
)
:: 删除旧war包
if exist "%TOMCAT_HOME%\webapps\%WAR_NAME%" (
    echo 正在删除: webapps\%WAR_NAME%
    del /f /q "%TOMCAT_HOME%\webapps\%WAR_NAME%"
)
echo [SUCCESS] 部署文件清理完成

echo.
echo ============================================================
echo [4/6] 清理 Tomcat 日志...
echo ============================================================
if exist "%TOMCAT_HOME%\logs\" (
    echo 正在清理 logs 目录...
    del /f /q "%TOMCAT_HOME%\logs\*" 2>nul
)
echo [SUCCESS] 日志清理完成

echo.
echo ============================================================
echo [5/6] 部署 WAR 包到 Tomcat...
echo ============================================================
set WAR_SOURCE=%PROJECT_HOME%\target\%WAR_NAME%
if not exist "%WAR_SOURCE%" (
    echo [ERROR] 未找到 WAR 包: %WAR_SOURCE%
    pause
    exit /b 1
)
copy /y "%WAR_SOURCE%" "%TOMCAT_HOME%\webapps\%WAR_NAME%"
echo [SUCCESS] WAR 包部署完成

echo.
echo ============================================================
echo [6/6] 启动 Tomcat...
echo ============================================================
call "%TOMCAT_HOME%\bin\startup.bat"
echo [SUCCESS] Tomcat 启动指令已发送

echo.
echo ============================================================
echo 部署完成！
echo 请等待约1-2分钟后访问: http://localhost:8080/hyplat
echo ============================================================
pause
