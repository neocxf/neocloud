# 积分表
# 每条记录代表某个用户持有的积分的数量
create table if not exists reward
(
    id               int primary key auto_increment,
    credit           int      default null,
    user_id          int not null,
    deleted          bit(1)   default 0,
    version          int      default 0,
    create_time      datetime default current_timestamp,
    last_update_time datetime default current_timestamp on update current_timestamp,
    unique key user_idx (user_id)
);

# 积分兑换记录表
# 代表某个用户用积分兑换商品的记录
create table if not exists reward_exchange
(
    id               int primary key auto_increment,
    user_id          int        not null,
    reward_id        int        not null,
    order_id         bigint(32) not null,
    credit           int        not null,
    deleted          bit(1)   default 0,
    version          int      default 0,
    create_time      datetime default current_timestamp,
    last_update_time datetime default current_timestamp on update current_timestamp
);