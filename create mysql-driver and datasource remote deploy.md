данные для запуска
Подключение SSH
194.58.120.78  - это в качестве логина в путти


Пароль SSH
Ees7ko4eito9


-запускаем putty
- выбираем настройку сохраненную wildfly и жмем загрузить(вставляются автоматически данные ip, user итд
- жмем запуск
- вводим пароль в ручную Ees7ko4eito9
- стартуем сервер
```bash
  systemctl start wildfly
```

- проверяем статус
```bash
  systemctl status wildfly
```

- переходим в дирректорию
```bash
cd /opt/wildfly/bin
```

-проверим есть ли файл jboss-cli.sh
введя команду ls -la

качаем mysql connector java
вот так у меня называется файл - "mysql-connector-java-8.0.23"
https://mvnrepository.com/artifact/mysql/mysql-connector-java/8.0.23
лучше качать с мавен репозитория - строка files - выбираем .jar

- переходим в домашнюю дирректорию
```bash
  cd ~
```

root@194-58-120-78:/opt/wildfly/bin# - были тут
должны перейти сюда
root@194-58-120-78:#

- скачиваем оттуда конектор введя вот это (после курл буква O закглавная)
```bash
  curl -O https://repo1.maven.org/maven2/mysql/mysql-connector-java/8.0.23/mysql-connector-java-8.0.23.jar
```

- если проверим  введя ls -la должны увидеть что в наличии файл
  mysql-connector-java-8.0.23.jar

- клонируем конкретно ветку lesson5
```bash
git clone --branch=lesson5 https://github.com/EvgenySaenko/java-ee.git
```

-проверяем
```bash
root@194-58-120-78:~# ls -la
root@194-58-120-78:~# cd java-ee
```

- должна появится папка  first-jsf-app
```bash
root@194-58-120-78:~/java-ee# ls -la
```

- заходим в нее
```bash
root@194-58-120-78:~/java-ee# cd first-jsf-app
```

-редактируем файл
vi mysql-driver.cli слеши должны быть именно так
тут мы указываем что он лежить в рут дирректории унас на сервере
--resources=/root/mysql-connector-java-8.0.23.jar

-устанавливаем mysql driver
-вызываем файл jboss-cli и показываем путь к нашему скрипту
```bash
 /opt/wildfly/bin/jboss-cli.sh --connect --file=mysql-driver.cli
```

-устанавливаем mysql server
```bash
root@194-58-120-78:~/java-ee/first-jsf-app# sudo apt install mysql-server
```
соглашаемся в процессе -  вводя - Y

-входим в mysql
```bash
root@194-58-120-78:~/java-ee/first-jsf-app# mysql -uroot -proot
```


-создадим пользователя и дамим ему привилегии
```bash
mysql> CREATE USER 'user'@'localhost' IDENTIFIED BY 'user';
Query OK, 0 rows affected (0.01 sec)

mysql> GRANT ALL PRIVILEGES ON * . * TO 'user'@'localhost';
Query OK, 0 rows affected (0.00 sec)

mysql> FLUSH PRIVILEGES;
Query OK, 0 rows affected (0.01 sec)
```

-пробуем под ним залогиниться
сначала выходим из mysql
```bash
-exit
```

далее воодим mysql -uuser -puser
```bash
root@194-58-120-78:~/java-ee/first-jsf-app# mysql -uuser -puser
```

-правим файл datasource.cli
```bash
 vi datasource.cli
```

а именно вот эти строки вставив нашего юзера и пароль
```bash
 --user-name=user \
   --password=user
```

-создадим источник данных
```bash
root@194-58-120-78:~/java-ee/first-jsf-app# /opt/wildfly/bin/jboss-cli.sh --connect --file=datasource.cli 
```

- ну и запускаем remote deploy из idea


- посмотреть логи переходим в каталог log
```bash
root@194-58-120-78:~/java-ee/first-jsf-app# cd /opt/wildfly/standalone/log
```

-смотрим логи
```bash
root@194-58-120-78:/opt/wildfly/standalone/log# tail -f server.log
```










