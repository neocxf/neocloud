create table if not exists customers(id bigint primary key auto_increment,name varchar(20) , age int) default charset =utf8;
create table if not exists orders(id bigint primary key auto_increment , orderno varchar(20) , price float , cid int) default charset =utf8;
create table if not exists user_info(id bigint primary key auto_increment,name varchar(50) , password varchar(50) not null, salt varchar(50), username varchar(50) unique not null)default charset =utf8;
create table if not exists sys_permission(id bigint primary key auto_increment,description longtext , name varchar(50) unique not null , url varchar(100))default charset =utf8;
create table if not exists sys_role(id bigint primary key auto_increment,description longtext , name varchar(50) unique not null)default charset =utf8;
create table if not exists sys_role_permission(id bigint primary key auto_increment,permission_id bigint not null , role_id bigint not null, unique key ur_pk(role_id, permission_id))default charset =utf8;
create table if not exists sys_user_role(id bigint primary key auto_increment,uid bigint not null , role_id bigint not null, unique key ur_pk(uid, role_id))default charset =utf8;