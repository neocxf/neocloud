
create table if not exists `t_order` (
    id bigint(32) primary key auto_increment,
    product_id  int default null,
    price double default null,
    number  int default null,
    credit  int default null,
    user_id int not null,
    last_message_id varchar(255) default null,
    status varchar(64) default null,
    deleted bit(1) default 0,
    version int default 0,
    create_time datetime default current_timestamp,
    last_update_time datetime default current_timestamp on update current_timestamp,
    field1 text
);

DROP TABLE IF EXISTS `dictionary`;
CREATE TABLE `dictionary`  (
    `dictionary_id` bigint(20) NOT NULL,
    `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `value` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    PRIMARY KEY (`dictionary_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for order0
-- ----------------------------
DROP TABLE IF EXISTS `order_0`;
CREATE TABLE `order_0`  (
    `order_id` bigint(32) NOT NULL,
    `user_id` bigint(32) NOT NULL,
    `address_id` BIGINT,
    `credit` BIGINT,
    `last_message_id` varchar(64),
    `status` VARCHAR(50),
    `field1` text,
    `deleted` bit(1) default 0,
    create_time datetime default current_timestamp,
    last_update_time datetime default current_timestamp on update current_timestamp,
    PRIMARY KEY (`order_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for order1
-- ----------------------------
DROP TABLE IF EXISTS `order_1`;
CREATE TABLE `order_1`  (
    `order_id` bigint(32) NOT NULL,
    `user_id` bigint(32) NOT NULL,
    `address_id` BIGINT,
    `credit` BIGINT,
    `last_message_id` varchar(64),
    `status` VARCHAR(50),
    `field1` text,
    `deleted` bit(1) default 0,
    create_time datetime default current_timestamp,
    last_update_time datetime default current_timestamp on update current_timestamp,
    PRIMARY KEY (`order_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for order_item0
-- ----------------------------
DROP TABLE IF EXISTS `order_item_0`;
CREATE TABLE `order_item_0`  (
    `user_id` bigint(20) NOT NULL DEFAULT 0,
    `order_item_id` bigint(32) NOT NULL,
    `order_id` bigint(32) NOT NULL,
    `product_id` bigint(32) NOT NULL,
    `product_name` VARCHAR(255),
    `num` int,
    `cost_per_unit` double DEFAULT NULL,
    deleted bit(1) default 0,
    create_time datetime default current_timestamp,
    last_update_time datetime default current_timestamp on update current_timestamp,
    PRIMARY KEY (`order_item_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for order_item1
-- ----------------------------
DROP TABLE IF EXISTS `order_item_1`;
CREATE TABLE `order_item_1`  (
     `user_id` bigint(20) NOT NULL DEFAULT 0,
     `order_item_id` bigint(32) NOT NULL,
     `order_id` bigint(32) NOT NULL,
     `product_id` bigint(32) NOT NULL,
     `product_name` VARCHAR(255),
     `num` int,
     `cost_per_unit` double DEFAULT NULL,
     deleted bit(1) default 0,
     create_time datetime default current_timestamp,
     last_update_time datetime default current_timestamp on update current_timestamp,
     PRIMARY KEY (`order_item_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;
