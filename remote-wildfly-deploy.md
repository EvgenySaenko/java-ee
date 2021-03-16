Подключение SSH
194.58.120.78


Пароль SSH
Ees7ko4eito9

Обновляем регистр пакетов
```bash
apt update
```
Устанавливаем JDK
```bash
apt install default-jdk
```
Проверяем версию JDK
```bash
java -version
```
Устанавливаем zip и unzip
```bash
apt install zip unzip
```
Создаем пользователя и указываем каталог, куда установим сервер в качестве домашнего
```bash
useradd -r -d /opt/wildfly -s /bin/false wildfly
```
Проверяем, добавился ли пользователь
```bash
cat /etc/passwd
```
Автоматически создается и группа для него, это можно проверить так:
```bash
cat /etc/group
```

Скачиваем Wildfly в каталог /tmp
```bash
wget https://download.jboss.org/wildfly/22.0.1.Final/wildfly-preview-22.0.1.Final.zip -P /tmp/
```
Распаковываем его в каталог /opt
```bash
unzip /tmp/wildfly-preview-22.0.1.Final.zip -d /opt/
```
Создаем символическую ссылку на каталог /opt/wildfly
```bash
ln -s /opt/wildfly-preview-22.0.1.Final/ /opt/wildfly
```

Делаем пользователя которого создали - Владельцем каталога куда установлен Wildfly
```bash
chown -RH wildfly: /opt/wildfly
```
Через команду ls -la можно проверить и увидеть

Создаем директорию
```bash
mkdir /etc/wildfly
```

Копируем файлы настроек и скриптов для запуска сервера в качестве фонового сервиса
```bash
cp /opt/wildfly/docs/contrib/scripts/systemd/wildfly.conf /etc/wildfly/
```

Редактируем файл /etc/wildfly/wildfly.conf при помощи редактора vi или nano
Содержимое после редактирования должно быть таким. Добавляем переменную WILDFLY_CONSOLE_BIND
```bash
# The configuration you want to run
WILDFLY_CONFIG=standalone.xml

# The mode you want to run
WILDFLY_MODE=standalone

# The address to bind to
WILDFLY_BIND=0.0.0.0

# The address console to bind to
WILDFLY_CONSOLE_BIND=0.0.0.0
```


Копируем файл  launch.sh
```bash
cp /opt/wildfly/docs/contrib/scripts/systemd/launch.sh /opt/wildfly/bin/
```
Редактируем файл /opt/wildfly/bin/launch.sh при помощи редактора vi или nano
Содержимое после редактирования должно быть таким. В двух местах добавляем параметр `-bmanagement $4`
```bash
#!/bin/bash

if [ "x$WILDFLY_HOME" = "x" ]; then
    WILDFLY_HOME="/opt/wildfly"
fi

if [[ "$1" == "domain" ]]; then
    $WILDFLY_HOME/bin/domain.sh -c $2 -b $3 -bmanagement $4
else
    $WILDFLY_HOME/bin/standalone.sh -c $2 -b $3 -bmanagement $4
fi
```

Копируем файл  wildfly.service
```bash
cp /opt/wildfly/docs/contrib/scripts/systemd/wildfly.service /etc/systemd/system/
```

Редактируем файл /etc/systemd/system/wildfly.service
После редактирования содержимое должно быть таким. В строке запуска скрипта launch.sh добавляем параметр $WILDFLY_CONSOLE_BIND
```
[Unit]
Description=The WildFly Application Server
After=syslog.target network.target
Before=httpd.service

[Service]
Environment=LAUNCH_JBOSS_IN_BACKGROUND=1
EnvironmentFile=-/etc/wildfly/wildfly.conf
User=wildfly
LimitNOFILE=102642
PIDFile=/var/run/wildfly/wildfly.pid
ExecStart=/opt/wildfly/bin/launch.sh $WILDFLY_MODE $WILDFLY_CONFIG $WILDFLY_BIND $WILDFLY_CONSOLE_BIND
StandardOutput=null

[Install]
WantedBy=multi-user.target
```



Открываем порт для консоли управления сервера (порт 8080 уже открыт)
```bash
 ufw allow 9990/tcp
```
Обновляем список сервисов перед запуском
```bash
systemctl daemon-reload
```
Запускаем сервис
```bash
systemctl start wildfly
```
Проверяем статус
```bash
systemctl status wildfly
```
Смотрим логи
```bash
tail -f /opt/wildfly/standalone/log/server.log
```

!!!!Важно проверить права на файлах даже если сервер запустился
Переходим в папку wildfly/bin
```bash
cd wildfly/bin
```
Далее смотрим что там лежит командой ls -la
И если файл launch.sh принадлежит пользователю root слева от него
То еще раз делаем пользователя владельцем этого файла
```bash
chown -RH wildfly: launch.sh
```
P.S. Изменять права на по идее нужно после того как мы эти файлы скопировали сюда

И тут же проверяем через команду ls -la

И заново перезапускаем
```bash
systemctl daemon-reload
systemctl restart wildfly
systemctl status wildfly
```

Можно без systemctl daemon-reload

Для деплоймента на запущенный сервер нужно видоизменить настройки wildfly-maven-plugin вот таким образом
```xml
    <build>
        <finalName>first-web-app</finalName>

        <plugins>
            <plugin>
                <groupId>org.wildfly.plugins</groupId>
                <artifactId>wildfly-maven-plugin</artifactId>
                <version>2.0.2.Final</version>
                <configuration>
                    <hostname>80.78.245.66</hostname>
                    <username>admin</username>
                    <password>123</password>
                </configuration>
            </plugin>
        </plugins>
    </build>
```

Можно не добавлять эти параметры в pom.xml, а создать новую цель сборки с таким набором команд и параметров
clean install wildfly:deploy -Dwildfly.hostname=194.58.120.78 -Dwildfly.username=admin -Dwildfly.password=admin








