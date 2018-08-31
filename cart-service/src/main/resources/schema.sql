drop table if exists `cart`;
create table `cart` (
  id bigint(20) primary key auto_increment,
  user_id bigint(20) not null,
  cart_line_ids varchar(255)
);

drop table if exists `cart_line`;
create table `cart_line` (
  id bigint(20) primary key auto_increment,
  inventory_id bigint(20),
  number bigint(20)
);

drop table if exists `category`;
create table `category` (
  id bigint(20)  primary key auto_increment,
  name varchar(255) not null
);