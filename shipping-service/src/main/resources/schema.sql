
# 发货申请表
# 代表某个积分兑换产生的发货记录
create table if not exists shipping_order (
  id int primary key auto_increment,
  type tinyint not null comment '0:购买, 1:积分兑换',
  order_id int,
  reward_exchange_id int,
  product_id int not null,
  number int not null default 1,
  addr varchar(255) not null default '上海市',
  shipped bit(1) default 0,
  order_confirmed bit(1) default 0,
  deleted bit(1) default 0,
  version int default 0,
  create_time datetime,
  last_update_time datetime
);