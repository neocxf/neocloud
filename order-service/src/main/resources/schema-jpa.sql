
create table if not exists `t_order` (
    id int primary key auto_increment,
    product_id  int default null,
    price double default null,
    number  int default null,
    credit  int default null,
    user_id int not null,
    last_message_id varchar(255) default null,
    status varchar(64) default null,
    deleted bit(1) default 0,
    version int default 0,
    create_time datetime,
    last_update_time datetime
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
    `address_id` BIGINT NOT NULL,
    `status` VARCHAR(50),
    `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
    `total_price` double DEFAULT NULL,
    PRIMARY KEY (`order_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for order1
-- ----------------------------
DROP TABLE IF EXISTS `order_1`;
CREATE TABLE `order_1`  (
    `order_id` bigint(32) NOT NULL,
    `user_id` bigint(32) NOT NULL,
    `address_id` BIGINT NOT NULL,
    `status` VARCHAR(50),
    `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
    `total_price` double DEFAULT NULL,
    PRIMARY KEY (`order_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for order_item0
-- ----------------------------
DROP TABLE IF EXISTS `order_item_0`;
CREATE TABLE `order_item_0`  (
    `order_item_id` bigint(32) NOT NULL,
    `order_id` bigint(32) NOT NULL,
    `status` VARCHAR(50),
    `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `price` double DEFAULT NULL,
    `user_id` bigint(20) NOT NULL DEFAULT 0,
    PRIMARY KEY (`order_item_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for order_item1
-- ----------------------------
DROP TABLE IF EXISTS `order_item_1`;
CREATE TABLE `order_item_1`  (
    `order_item_id` bigint(32) NOT NULL,
    `order_id` bigint(32) NOT NULL,
    `status` VARCHAR(50),
    `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `price` double DEFAULT NULL,
    `user_id` bigint(20) NOT NULL DEFAULT 0,
    PRIMARY KEY (`order_item_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;
