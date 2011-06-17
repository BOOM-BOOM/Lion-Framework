@echo off
@title Lion Framework
java -Xmx2048m -Xms2048m -server -cp bin;lib/* org.lion.Server
pause