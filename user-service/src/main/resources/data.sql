INSERT INTO `user_info` (`id`,`name`,`password`,`salt`,`username`) VALUES (1, '管理员','99ee4c06a93ed15ebe0b151c3c87a22c', 'xbNIxrQfn6COSYn1/GdloA==', 'admin') on duplicate key update username=values(username) ;
INSERT INTO `sys_permission` (`id`,`description`,`name`,`url`) VALUES (1,'查询用户','userInfo:view','/userList') on duplicate key update name=values(name);
INSERT INTO `sys_permission` (`id`,`description`,`name`,`url`) VALUES (2,'增加用户','userInfo:add','/userAdd') on duplicate key update name=values(name);
INSERT INTO `sys_permission` (`id`,`description`,`name`,`url`) VALUES (3,'删除用户','userInfo:delete','/userDelete') on duplicate key update name=values(name);
INSERT INTO `sys_role` (`id`,`description`,`name`) VALUES (1,'管理员','admin') on duplicate key update name=values(name);
INSERT INTO `sys_role_permission` (`permission_id`,`role_id`) VALUES (1,1) on duplicate key update permission_id=values(permission_id);
INSERT INTO `sys_role_permission` (`permission_id`,`role_id`) VALUES (2,1) on duplicate key update permission_id=values(permission_id);
INSERT INTO `sys_user_role` (`role_id`,`uid`) VALUES (1,1) on duplicate key update role_id=values(role_id);

select user();