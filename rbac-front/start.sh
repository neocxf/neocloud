#!/usr/bin/env bash
node -e "require('express')().use(require('express').static('public', {index:'index.html'})).listen(4000)"
# CREATE USER 'admin'@'%' IDENTIFIED BY 'root';
# CREATE DATABASE billing CHARACTER SET utf8 COLLATE utf8_general_ci;
# GRANT ALL PRIVILEGES ON billing.* TO 'admin'@'%' WITH GRANT OPTION;
# FLUSH PRIVILEGES;
# ALTER USER admin IDENTIFIED WITH mysql_native_password BY 'root';

