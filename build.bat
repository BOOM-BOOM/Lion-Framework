@echo off
@title Lion Framework
if exist bin goto start
md bin
:start
echo Compiling...
javac -d bin -cp lib/netty-3.2.4.Final.jar;lib/netty-3.2.4.Final-sources.jar -sourcepath src src/org/lion/Server.java
pause