SET INSTALL_EXE="%~dp0embedded-exec.exe"
SET CP="%~dp0..\lib\*"
SET LOG_PATH="%~dp0..\logs"

SET JVM="%~dp0jre7\bin\client\jvm.dll"

echo %INSTALL_EXE%
%INSTALL_EXE% //IS//embedded-exec^
 --DisplayName="embedded-exec"^
 --Install=%INSTALL_EXE%^
 --Startup=auto^
 --Jvm=%JVM%^
 --StartMode=jvm^
 --StopMode=jvm^
 --StartClass=example.EmbeddedMain^
 --StartParams="..\web-project-1.0-SNAPSHOT.war"^
 --LogPath=%LOG_PATH%^
 --StdOutput=auto^
 --StdError=auto^
 --Classpath=%CP%^
 --StopClass=example.EmbeddedMain^
 --StopMethod=shutdown^
 --StopTimeout=10^
