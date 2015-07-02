@echo off
cd /d "%~dp0"
call variables
call variables-home
call:check-directories
pause
cls

setlocal EnableDelayedExpansion

call:copy-dot-projects

set log0="%~dpn0.log"
if exist %log0% del %log0%

set bat1="%~dpn0.rename-1.bat"
if exist %bat1% del %bat1%

set bat2="%~dpn0.rename-2.bat"
if exist %bat2% del %bat2%

set tok0="%~dpn0.unsorted.txt"
if exist %tok0% del %tok0%

set tok1="%~dpn0.tokens-1.txt"
if exist %tok1% del %tok1%

set tok2="%~dpn0.tokens-2.txt"
if exist %tok2% del %tok2%

call:setOldAndNewProgramFilesVariables
echo.
call:setOldAndNewGlassFishVariables
echo.
call:setOldAndNewJBossVariables
echo.
call:setOldAndNewPostgreSQLVariables
echo.
call:setOldAndNewProjectPath
echo.
if defined bad_project_path (
    goto:eof
)
call:setOldAndNewProjectCode
echo.
if defined bad_project_code (
    goto:eof
)
call:checkVersionControlFolders-1
if /i "%yesOrNo%" == "Y" (
    echo.
    echo cannot refactor a workspace under version control
    echo export a clean directory tree from the repository
    echo.
    pause
    goto:eof
)
call:checkVersionControlFolders-2
if /i "%yesOrNo%" == "Y" (
    echo.
    echo cannot refactor a workspace under version control
    echo export a clean directory tree from the repository
    echo.
    pause
    goto:eof
)
set replacer="%third_party_dir%\tools\fart\fart.exe"
if not exist %replacer% (
    set replacer
    echo.
    echo ERROR: file %replacer% NOT FOUND!
    echo Download SourceForge FART tool from http://sourceforge.net/projects/fart-it
    echo Then copy "fart.exe" file to "%third_party_dir%\tools\fart"
    echo.
    pause
    goto:eof
)

:refactor
set yesOrNo=Y
set /p yesOrNo="rename %old_project_code% to %new_project_code% ? (Y/N) [%yesOrNo%] "
if /i not "%yesOrNo%" == "Y" goto:eof
echo.
rem call:checkFiles
rem call:checkFolders
rem if %files% GTR 0 call %bat1%
rem if %folders% GTR 0 call %bat2%
call:replaceOldProjectCode
call:replaceOldProjectPath
call:replaceOldGlassFishHomePath
call:replaceOldJBossHomePath
call:replaceOldPostgreSQLHomePath
call:replaceOldPostgreSQLDriverVersion
echo.
set yesOrNo=N
set /p yesOrNo="open log file ? (Y/N) [%yesOrNo%] "
if /i "%yesOrNo%" == "Y" (
    start /d %SystemRoot% notepad %log0%
    echo.
    pause
)
echo.
if exist %bat1% del %bat1%
if exist %bat2% del %bat2%
if exist %tok0% del %tok0%
if exist %tok1% del %tok1%
if exist %tok2% del %tok2%
endlocal
goto:eof

:check-directories
echo checking directories
echo.
rem  check-dir %workspace%
call:check-dir %adalid_dir%
call:check-dir %third_party_dir%
call:check-glassfish-directories
call:check-jboss-directories
call:check-postgresql-directories
goto:eof

:check-glassfish-directories
set GLASSFISH
set ok=
if defined GLASSFISH_HOME (
    set ok=true
    call:check-dir %GLASSFISH_HOME%
)
set GLASSFISH_HOME_OK=%ok%
goto:eof

:check-jboss-directories
set JBOSS
set ok=
if defined JBOSS_HOME (
    set ok=true
    call:check-dir %JBOSS_HOME%
)
set JBOSS_HOME_OK=%ok%
goto:eof

:check-postgresql-directories
set POSTGRESQL
set ok=
if defined POSTGRESQL_HOME (
    set ok=true
    call:check-dir %POSTGRESQL_HOME%
)
set POSTGRESQL_HOME_OK=%ok%
goto:eof

:setOldAndNewProjectCode
set old_project_code=jee1
set old_project_code
set bad_project_code=
set new_project_code=
call:check-name-for-spaces "%project%"
if defined name_contains_spaces (
    echo.
    echo Project name contains spaces and therefore is invalid
    set bad_project_code=true
)
set project_first_character=%project:~0,1%
call:lcase project_first_character lower_character
call:ucase project_first_character upper_character
if "%lower_character%" == "%upper_character%" (
    echo.
    echo new project code %project% does not start with a letter
    set bad_project_code=true
)
call:check project invalid_characters
if not "%invalid_characters%" == "" (
    echo.
    echo new project code %project% contains invalid characters
    set bad_project_code=true
)
if /i "%project%" == "workspace" (
    echo.
    echo new project code cannot be workspace
    set bad_project_code=true
)
if defined bad_project_code (
    echo.
    pause
    echo.
    goto:eof
)
call:lcase project new_project_code
set new_project_code
if not "%project%" == "%new_project_code%" (
    echo.
    echo *** warning: new project code got converted to lower case
)
if /i "%old_project_code%" == "%new_project_code%" (
    echo.
    echo old and new project codes must be different
    echo.
    pause
    echo.
    set bad_project_code=true
)
goto:eof

:setOldAndNewProjectPath
set space=%%20
set old_project_path=C:/workspace/
set old_project_path
set new_project_path=%workspace%\
set new_project_path=%new_project_path:\=/%
set new_project_path=%new_project_path: =!space!%
set new_project_path
echo.
set bad_project_path=
call:check-path-for-spaces "%workspace%"
if defined path_contains_spaces (
    echo Project directory path contains spaces and therefore is invalid
    echo.
    pause
    echo.
    set bad_project_path=true
    goto:eof
)
if /i "%new_project_path%" == "%old_project_path%" (
    echo Project directory remains the same
) else (
    echo Project directory has changed and therefore all references to it must be refactored
)
goto:eof

:setOldAndNewProgramFilesVariables
set space=%%20
set old_program_files=C:/Program%%20Files/
set old_program_files
set new_program_files=%ProgramFiles%\
set new_program_files=%new_program_files:\=/%
set new_program_files=%new_program_files: =!space!%
set new_program_files
echo.
if /i "%new_program_files%" == "%old_program_files%" (
    echo Program Files directory remains the same
) else (
    echo Program Files directory has changed and therefore all references to it must be refactored
)
goto:eof

:setOldAndNewGlassFishVariables
set old_glassfish_home=C:/Program%%20Files/glassfish-4.0/glassfish/
set old_glassfish_home
if defined GLASSFISH_HOME (
    call:setNewGlassFishHome
) else (
    set new_glassfish_home=%old_glassfish_home%
)
set new_glassfish_home
echo.
if /i "%new_glassfish_home%" == "%old_glassfish_home%" (
    echo GlassFish home directory remains the same
) else (
    echo GlassFish home directory has changed and therefore all references to it must be refactored
)
goto:eof

:setNewGlassFishHome
set space=%%20
set new_glassfish_home=%GLASSFISH_HOME%\
set new_glassfish_home=%new_glassfish_home:\=/%
set new_glassfish_home=%new_glassfish_home: =!space!%
goto:eof

:setOldAndNewJBossVariables
set old_jboss_home=C:/jboss-as-7.1.1.Final/
set old_jboss_home
if defined JBOSS_HOME (
    call:setNewJBossHome
) else (
    set new_jboss_home=%old_jboss_home%
)
set new_jboss_home
echo.
if /i "%new_jboss_home%" == "%old_jboss_home%" (
    echo JBoss home directory remains the same
) else (
    echo JBoss home directory has changed and therefore all references to it must be refactored
)
goto:eof

:setNewJBossHome
set space=%%20
set new_jboss_home=%JBOSS_HOME%\
set new_jboss_home=%new_jboss_home:\=/%
set new_jboss_home=%new_jboss_home: =!space!%
goto:eof

:setOldAndNewPostgreSQLVariables
set old_postgresql_home=C:/Program%%20Files/PostgreSQL/9.2/
set old_postgresql_home
if defined POSTGRESQL_HOME (
    call:setNewPostgreSQLHome
) else (
    set new_postgresql_home=%old_postgresql_home%
)
set new_postgresql_home
echo.
if /i "%new_postgresql_home%" == "%old_postgresql_home%" (
    echo PostgreSQL home directory remains the same
) else (
    echo PostgreSQL home directory has changed and therefore all references to it must be refactored
)
echo.
set old_postgresql_driver_version=9.2-1002
set old_postgresql_driver_version
if defined POSTGRESQL_DRIVER_VERSION (
    set new_postgresql_driver_version=%POSTGRESQL_DRIVER_VERSION%
) else (
    set new_postgresql_driver_version=%old_postgresql_driver_version%
)
set new_postgresql_driver_version
echo.
if /i "%new_postgresql_driver_version%" == "%old_postgresql_driver_version%" (
    echo PostgreSQL driver version remains the same
) else (
    echo PostgreSQL driver version has changed and therefore all references to it must be refactored
)
goto:eof

:setNewPostgreSQLHome
set space=%%20
set new_postgresql_home=%POSTGRESQL_HOME%\
set new_postgresql_home=%new_postgresql_home:\=/%
set new_postgresql_home=%new_postgresql_home: =!space!%
goto:eof

:checkVersionControlFolders-1
set svn_dir=.svn
set yesOrNo=N
pushd "%project_dir%"
for /f "tokens=1* delims=\" %%d in ("%CD%") do set project_drive=%%d
set XD=%project_drive%\

:checkVersionControlFolders-1-loop
if exist %svn_dir% call:checkVersionControlFolder %CD%\%svn_dir%
if /i not "%CD%" == "%XD%" if /i not "%yesOrNo%" == "Y" (
    cd ..
    goto:checkVersionControlFolders-1-loop
)
popd
goto:eof

:checkVersionControlFolders-2
set svn_dir=.svn
set yesOrNo=N
for /R "%project_dir%" %%d in (.) do (
    if "%%~nxd" == "%svn_dir%" call:checkVersionControlFolder %%d
)
goto:eof

:checkVersionControlFolder
set yesOrNo=Y
echo %~f1 is a version control directory
goto:eof

:checkFiles
set /a files=0
if exist %tok0% del %tok0%
for /R "%project_source_dir%" %%f in (*.*) do call:checkFile "%%f"
if %files% GTR 0 echo.
echo %files% files to rename
echo.
echo @echo off>>%bat1%
echo @echo %%~n0: %files% files to rename>>%bat1%
echo @echo.>>%bat1%
if %files% GTR 0 (
    echo rem pause>>%bat1%
    sort %tok0% /o %tok1%
    type %tok1%>>%bat1%
    echo rem pause>>%bat1%
    echo @echo.>>%bat1%
)
if exist %tok0% del %tok0%
goto:eof

:checkFile
set old=%~nx1
call set new=%%old:%old_project_code%=%new_project_code%%%
if /i not "%old%" == "%new%" (
    set /a files+=1
    echo ren %~f1 %new%
    echo ren %~f1 %new%>>%tok0%
)
goto:eof

:checkFolders
set /a folders=0
if exist %tok0% del %tok0%
for /R "%project_source_dir%" %%d in (.) do call:checkFolder "%%d"
if %folders% GTR 0 echo.
echo %folders% folders to rename
echo.
echo @echo off>>%bat2%
echo @echo %%~n0: %folders% folders to rename>>%bat2%
echo @echo.>>%bat2%
if %folders% GTR 0 (
    echo rem pause>>%bat2%
    sort /r %tok0% /o %tok2%
    type %tok2%>>%bat2%
    echo rem pause>>%bat2%
    echo @echo.>>%bat2%
)
if exist %tok0% del %tok0%
goto:eof

:checkFolder
set old=%~nx1
call set new=%%old:%old_project_code%=%new_project_code%%%
if /i not "%old%" == "%new%" (
    set /a folders+=1
    echo ren %~f1 %new%
    echo ren %~f1 %new%>>%tok0%
)
goto:eof

:replaceOldProjectCode
rem :replace-old-project-code-1 bat
rem :replace-old-project-code-1 classpath
rem :replace-old-project-code-1 cli
rem :replace-old-project-code-1 css
rem :replace-old-project-code-1 html
call:replace-old-project-code-1 java
rem :replace-old-project-code-1 jrtx
rem :replace-old-project-code-1 jrxml
rem :replace-old-project-code-1 js
rem :replace-old-project-code-1 jsp
rem :replace-old-project-code-1 jspf
rem :replace-old-project-code-1 osql
rem :replace-old-project-code-1 password
rem :replace-old-project-code-1 project
rem :replace-old-project-code-1 properties
rem :replace-old-project-code-1 psql
rem :replace-old-project-code-1 sh
rem :replace-old-project-code-1 sql
rem :replace-old-project-code-1 tld
rem :replace-old-project-code-1 txt
rem :replace-old-project-code-1 userlibraries
rem :replace-old-project-code-1 vm
rem :replace-old-project-code-1 xml
rem :replace-old-project-code-2 org.eclipse
goto:eof

:replace-old-project-code-1
set old_string=%old_project_code%ap101
set new_string=%new_project_code%ap101
call:replace-old-project-code-1-1 %1
goto:eof

:replace-old-project-code-2
call:replace-old-project-code-2-1 %1
set old_string=%old_project_code%ap101
set new_string=%new_project_code%ap101
call:replace-old-project-code-2-1 %1
goto:eof

:replace-old-project-code-1-1
call:lcase old_string findstring
call:lcase new_string replacestring
call:file-suffix-replacer %1
rem call:ucase old_string findstring
rem call:ucase new_string replacestring
rem call:file-suffix-replacer %1
echo.
goto:eof

:replace-old-project-code-2-1
call:lcase old_string findstring
call:lcase new_string replacestring
call:file-prefix-replacer %1
rem call:ucase old_string findstring
rem call:ucase new_string replacestring
rem call:file-prefix-replacer %1
echo.
goto:eof

:replaceOldProjectPath
set findstring=%old_project_path%
set replacestring=%new_project_path%
if /i not "%findstring%" == "%replacestring%" (
    call:file-suffix-replacer userlibraries
    call:file-suffix-replacer xml
)
goto:eof

:replaceOldProgramFilesPath
set findstring=%old_program_files%
set replacestring=%new_program_files%
if /i not "%findstring%" == "%replacestring%" (
    call:file-suffix-replacer userlibraries
    call:file-suffix-replacer xml
)
goto:eof

:replaceOldGlassFishHomePath
set findstring=%old_glassfish_home%
set replacestring=%new_glassfish_home%
if /i not "%findstring%" == "%replacestring%" (
    call:file-suffix-replacer userlibraries
    call:file-suffix-replacer xml
)
goto:eof

:replaceOldJBossHomePath
set findstring=%old_jboss_home%
set replacestring=%new_jboss_home%
if /i not "%findstring%" == "%replacestring%" (
    call:file-suffix-replacer userlibraries
    call:file-suffix-replacer xml
)
goto:eof

:replaceOldPostgreSQLHomePath
set findstring=%old_postgresql_home%
set replacestring=%new_postgresql_home%
if /i not "%findstring%" == "%replacestring%" (
    call:file-suffix-replacer userlibraries
    call:file-suffix-replacer xml
)
goto:eof

:replaceOldPostgreSQLDriverVersion
set findstring=%old_postgresql_driver_version%
set replacestring=%new_postgresql_driver_version%
if /i not "%findstring%" == "%replacestring%" (
    call:file-suffix-replacer userlibraries
    call:file-suffix-replacer xml
)
goto:eof

:check
rem Syntax: call :check name1 name2
rem name1 = NAME of variable whose VALUE is to be checked
rem name2 = NAME of variable to hold the invalid characters found
rem Notice: Use variable NAMES in the call, not values (pass "by reference")
set lcab=a b c d e f g h i j k l m n o p q r s t u v w x y z 0 1 2 3 4 5 6 7 8 9
set lcst=!%1!
for %%x in (%lcab%) do set lcst=!lcst:%%x= !
for /f "tokens=* delims= " %%x in ("%lcst%") do set lcst=%%x
set %2=%lcst%
goto:eof

:lcase
rem Syntax: call :lcase name1 name2
rem name1 = NAME of variable whose VALUE is to be converted to lower case
rem name2 = NAME of variable to hold the converted value
rem Notice: Use variable NAMES in the call, not values (pass "by reference")
set lcab=a b c d e f g h i j k l m n o p q r s t u v w x y z
set lcst=!%1!
for %%x in (%lcab%) do set lcst=!lcst:%%x=%%x!
set %2=%lcst%
goto:eof

:ucase
rem Syntax: call :ucase name1 name2
rem name1 = NAME of variable whose VALUE is to be converted to upper case
rem name2 = NAME of variable to hold the converted value
rem Notice: Use variable NAMES in the call, not values (pass "by reference")
set UCAB=A B C D E F G H I J K L M N O P Q R S T U V W X Y Z
set UCST=!%1!
for %%X in (%UCAB%) do set UCST=!UCST:%%X=%%X!
set %2=%UCST%
goto:eof

:file-suffix-replacer
set wildcard=%project_dir%\*.%1
call:wildcard-replacer
goto:eof

:file-prefix-replacer
set wildcard=%project_dir%\%1.*
call:wildcard-replacer
goto:eof

:wildcard-replacer
echo replace "%findstring%" with "%replacestring%" in "%wildcard%"
echo %replacer% -r "%wildcard%" "%findstring%" "%replacestring%" 1>>%log0% 2>nul
%replacer% -r "%wildcard%" "%findstring%" "%replacestring%" 1>>%log0% 2>nul
echo.>>%log0%
goto:eof

:copy-dot-projects
pushd "%project_source_dir%\development\resources\projects"
if exist "%adalid_dir%"         call:copy-dot-project adalid.project        "%adalid_dir%\.project"
if exist "%GLASSFISH_HOME%"     call:copy-dot-project glassfish.project     "%GLASSFISH_HOME%\.project"
if exist "%JBOSS_HOME%"         call:copy-dot-project jboss.project         "%JBOSS_HOME%\.project"
if exist "%third_party_dir%"    call:copy-dot-project third-party.project   "%third_party_dir%\.project"
popd
goto:eof

:copy-dot-project
if not exist "%~f2" (
    if exist "%~f1" (
        if "%USERDOMAIN%" == "JRCG8700" echo %~nx1
        echo write "%~f2"
        type "%~f1">"%~f2"
    )
)
if exist "%~f2" (
    if "%USERDOMAIN%" == "JRCG8700" (
        echo %~nx1 -^> %~f2
        type "%~f2"
    )
    echo.
) else (
    echo ERROR "%~f2" not exists
    echo.
)
goto:eof

:check-dir
call:check-directory "%*\"
goto:eof

:check-directory
set f1="%~f1"
echo checking directory %f1%
if exist %f1% (
    echo directory %f1% is OK
) else (
    set ok=
    echo.
    echo directory %f1% NOT FOUND
)
echo.
goto:eof

:check-name-for-spaces
set str1="%~1"
set str2=%str1: =%
if %str1% == %str2% (
    set name_contains_spaces=
) else (
    set name_contains_spaces=true
)
goto:eof

:check-path-for-spaces
set str1="%~f1"
set str2=%str1: =%
if %str1% == %str2% (
    set path_contains_spaces=
) else (
    set path_contains_spaces=true
)
goto:eof
