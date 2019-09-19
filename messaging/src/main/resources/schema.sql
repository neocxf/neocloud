# 消息表
# 记录异步操作的各种消息（各个阶段），用于保证分布式事务的一致性，内置多个executor
create table if not exists message (
    `id` int primary key auto_increment,
    `version` int(11) NOT NULL DEFAULT '0' COMMENT '版本号',
    `editor` varchar(100) DEFAULT NULL COMMENT '修改者',
    `creator` varchar(100) DEFAULT NULL COMMENT '创建者',
    `edit_time` datetime DEFAULT NULL COMMENT '最后修改时间',
    `create_time` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
    `message_id` varchar(50) NOT NULL DEFAULT '' COMMENT '消息ID',
    `message_body` longtext NOT NULL COMMENT '消息内容',
    `message_data_type` varchar(50) DEFAULT NULL COMMENT '消息数据类型',
    `consumer_queue` varchar(100) NOT NULL DEFAULT '' COMMENT '消费队列',
    `message_send_times` smallint(6) NOT NULL DEFAULT '0' COMMENT '消息重发次数',
    `already_dead` varchar(20) NOT NULL DEFAULT '' COMMENT '是否死亡',
    `status` varchar(20) NOT NULL DEFAULT '' COMMENT '状态: 预存储，发送中，销毁/确认消费',
    `remark` varchar(200) DEFAULT NULL COMMENT '备注',
    `field` varchar(1024) DEFAULT NULL COMMENT '扩展字段',
    `deleted` bit(1) default 0,
    `last_update_time` datetime
);
