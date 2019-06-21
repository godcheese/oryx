#!/usr/bin/env bash

java -version
chmod +x mvnw
sudo mysql -e "use mysql; update user set authentication_string=PASSWORD('123456') where User='root'; update user set plugin='mysql_native_password';flush privileges;"
sudo mysql_upgrade -uroot -p123456
sudo mysql -uroot -p123456 -e "use mysql;create user 'nimrodbackend'@'%';update user set authentication_string=PASSWORD('123456') where User='nimrodbackend';update user set plugin='mysql_native_password';grant usage on *.* to 'nimrodbackend' require none with max_queries_per_hour 0 max_connections_per_hour 0 max_updates_per_hour 0 max_user_connections 0;create database if not exists nimrodbackend;grant all privileges on nimrodbackend.* to 'nimrodbackend'@'%';flush privileges;"
sudo mysql -unimrod -p123456 nimrodbackend < db/mysql/nimrodbackend/nimrodbackend.sql
sudo mysql -unimrod -p123456 nimrodbackend < db/mysql/oauth2/oauth2.sql
sudo mysql -unimrod -p123456 nimrodbackend < db/mysql/quartz/tables_mysql_innodb.sql