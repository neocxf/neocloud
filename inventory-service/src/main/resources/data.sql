create table inventory (
   `id` int primary key auto_increment,
   `quantity` int(11) NOT NULL DEFAULT '0' COMMENT '版本号',
   `product_code` varchar(100) DEFAULT NULL COMMENT '修改者',
   `create_time` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
   `deleted` bit(1) default 0,
   `last_update_time` datetime
) default charset = utf8;
insert into inventory(id, quantity, product_code) VALUES
(2, 'product1'),
(5, 'product2'),
(10, 'product3')
;