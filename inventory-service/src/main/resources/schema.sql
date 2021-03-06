create table if not exists inventory_item
(
    `id`               int primary key auto_increment,
    `quantity`         int(11)               DEFAULT 0 COMMENT '产品数量',
    `product_code`     varchar(100) not NULL COMMENT '产品code',
    `version`          int(11)      NOT NULL DEFAULT '0' COMMENT '版本号',
    `create_time`      datetime     NOT NULL COMMENT '创建时间',
    `deleted`          bit(1)                default 0,
    `last_update_time` datetime,
    unique key prod_key_idx (`product_code`)
);

create table if not exists inventory_request_history
(
    `id`               int primary key auto_increment,
    `user_id`          int(11)               DEFAULT 0 COMMENT '用户标识id',
    `product_id`       int(11)               DEFAULT 0 COMMENT '产品id',
    `product_code`     varchar(100) not NULL COMMENT '产品code',
    `quantity`         int(11)      NOT NULL DEFAULT '0' COMMENT '请求的产品数量',
    `create_time`      datetime     NOT NULL COMMENT '创建时间',
    `deleted`          bit(1)                default 0,
    `last_update_time` datetime
);