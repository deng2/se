@echo off
@setlocal

pushd %~dp0..
set   SE_HOME=%CD%
popd

set CLASSPATH=%SE_HOME%/bin/*;%SE_HOME%/etc;%SE_HOME%/lib/*

if not "%DEBUG%"=="" (
    set JAVA_OPTS=-Xdebug -Xrunjdwp:transport=dt_socket,address=7878,server=y,suspend=y
)  
java %JAVA_OPTS% com.greenbee.se.start.JettyMain %*
