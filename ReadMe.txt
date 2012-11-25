Для установки программы "Помощник расписания" из исходников необходимо:
Скачать и установить:
1. java se 6.
2. maven 3, прописать в переменные среды M3_HOME=C:\maven\; В Path: %M3_HOME%\bin
3. svn последний версии с сайта: http://tortoisesvn.net/
4. скачать исходники программы с сайта: http://code.google.com/p/assistant-schedule/source/checkout, т. е. сделать svn checkout https://assistant-schedule.googlecode.com/svn
5. открыть папку ru.kai.assistantschedule.build и в ней вызвать cmd.exe
6. В командной строке написать: mvn install
7. Если все прошло успешно и проект собрался, то в папке ru.kai.assistantschedule.repository, рядом с папкой target, 
появится папка assistant_schedule_app. Внутри её находиться assistantschedule.exe!
Все можно ее запускать!

Полезные ссылки:
http://www.modumind.com/2012/11/12/rcp-best-practices-get-your-product-building-right-away/

Коммит!