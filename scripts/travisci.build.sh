#!/usr/bin/env bash
echo "author godcheese"
java -version
chmod +x ./mvnw
sudo mysql -e "use mysql; update user set authentication_string=PASSWORD('123456') where User='root'; update user set plugin='mysql_native_password';flush privileges;"
sudo mysql_upgrade -uroot -p123456
sudo mysql -uroot -p123456 -e "use mysql;create user 'oryx'@'%';update user set authentication_string=PASSWORD('123456') where User='oryx';update user set plugin='mysql_native_password';grant usage on *.* to 'oryx' require none with max_queries_per_hour 0 max_connections_per_hour 0 max_updates_per_hour 0 max_user_connections 0;create database if not exists oryx;grant all privileges on oryx.* to 'oryx'@'%';flush privileges;"
sudo mysql -uoryx -p123456 oryx < db/mysql/oryx/oryx.sql
sudo mysql -uoryx -p123456 oryx < db/mysql/oauth2/oauth2.sql
sudo mysql -uoryx -p123456 oryx < db/mysql/quartz/tables_mysql_innodb.sql
sudo ./mvnw -e clean install -DskipTests=true -Dmaven.javadoc.skip=true -Dspring-boot.run.profiles=dev
sudo ./mvnw -e clean install -DskipTests=true -Dmaven.javadoc.skip=true -Dspring-boot:run.prifiles=prod