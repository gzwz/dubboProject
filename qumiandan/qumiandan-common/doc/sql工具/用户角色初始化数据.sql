-- 机构
INSERT INTO `sys_organization` (`id`,`num`,`pid`,`simplename`,`fullname`,`tips`,`version`,`create_id`,`update_id`,`create_date`,`update_date`) VALUES (1,1,0,'zsk','智售客','1',1,1,1,NULL,NULL);

-- 用户admin
INSERT INTO `sys_user` VALUES (1, NULL, 'admin', NULL, NULL, 'e10adc3949ba59abbe56e057f20f883e', NULL, NULL, 1, 0, 1, NULL, NULL, NULL, NULL, 1, NULL, '2018-11-17 13:22:57', NULL);
-- 用户扩展
INSERT INTO `sys_user_info` VALUES (1, 1, 'admin', '管理员', NULL, '15085958874', NULL, 1, NULL, NULL, NULL, 'http://thirdwx.qlogo.cn/mmopen/vi_32/lHicnMiagoDSd3CD6hD06ntQibFwzgibsN2BAy8MtYgFxZvbpwHCsfOVEHI9sISMY04jdJ8o0OW8epBNaS3OrwKGiag/132');



-- shiro
INSERT INTO `sys_shiro_filter` VALUES (1, '/login/**', 'anon', 1);
INSERT INTO `sys_shiro_filter` VALUES (2, '/static/**', 'anon', 2);
INSERT INTO `sys_shiro_filter` VALUES (3, '/init', 'authc,kickout,perms[/content/add]', 3);
INSERT INTO `sys_shiro_filter` VALUES (4, '/role/addRole', 'perms[/role/addRole]', 4);
INSERT INTO `sys_shiro_filter` VALUES (5, '/role/updateRole ', 'perms[/role/updateRole]', 5);
INSERT INTO `sys_shiro_filter` VALUES (6, '/role/deleteRole', 'perms[/role/deleteRole]', 6);
INSERT INTO `sys_shiro_filter` VALUES (7, '/role/list', 'perms[/role/list]', 7);
INSERT INTO `sys_shiro_filter` VALUES (8, '/permission/addPermission', 'perms[/permission/addPermission]', 8);
INSERT INTO `sys_shiro_filter` VALUES (9, '/permission/updatePermission', 'perms[/permission/updatePermission]', 9);
INSERT INTO `sys_shiro_filter` VALUES (10, '/permission/list', 'perms[/permission/list]', 10);
INSERT INTO `sys_shiro_filter` VALUES (11, '/rolePermission/addRolePermission', 'perms[/rolePermission/addRolePermission]', 11);
INSERT INTO `sys_shiro_filter` VALUES (12, '/rolePermission/updateRolePermission', 'perms[/rolePermission/updateRolePermission]', 12);

-- 菜单
INSERT INTO `sys_permission` VALUES (1, 1, 0, '/user/magager', '用户管理', 1, 1, 4, 4, '2018-11-22 18:59:33', NULL);
INSERT INTO `sys_permission` VALUES (2, 2, 1, '/permission/list', '权限管理', 1, 1, 4, 0, '2018-11-22 17:33:03', NULL);
INSERT INTO `sys_permission` VALUES (3, 3, 2, '/permission/addPermission', '添加权限', 2, 1, 4, 0, '2018-11-22 17:42:08', NULL);
INSERT INTO `sys_permission` VALUES (4, 4, 2, '/permission/updatePermission', '更新权限', 2, 1, 4, 4, '2018-11-22 17:42:42', '2018-11-22 18:58:18');
INSERT INTO `sys_permission` VALUES (5, 5, 1, '/role/list', '角色管理', 1, 1, 4, 0, '2018-11-22 18:59:33', NULL);
INSERT INTO `sys_permission` VALUES (6, 6, 5, '/role/addRole', '添加角色', 2, 1, 4, NULL, '2018-11-23 13:52:42', NULL);
INSERT INTO `sys_permission` VALUES (7, 7, 5, '/role/updateRole', '修改角色', 2, 1, 4, NULL, '2018-11-23 13:53:07', NULL);
INSERT INTO `sys_permission` VALUES (8, 8, 5, '/role/deleteRole', '删除角色', 2, 1, 4, NULL, '2018-11-23 13:53:29', NULL);
INSERT INTO `sys_permission` VALUES (9, 9, 0, '/rolePermission/addRolePermission', '角色添加权限', 2, 1, 4, NULL, '2018-11-23 14:39:47', NULL);
INSERT INTO `sys_permission` VALUES (10, 10, 0, '/rolePermission/updatePermission', '角色修改权限', 2, 1, 4, NULL, '2018-11-23 14:39:47', NULL);

-- 角色
INSERT INTO `sys_role` VALUES (1, 1, '管理员', 1, 'admin', 4, NULL, '2018-11-22 14:58:10', NULL, 1);

-- 用户角色管理
INSERT INTO `sys_user_role` VALUES (1, 1, 1, 1);

-- 角色权限关联
INSERT INTO `sys_role_permission` VALUES (1, 1, 1);
INSERT INTO `sys_role_permission` VALUES (2, 2, 1);
INSERT INTO `sys_role_permission` VALUES (3, 3, 1);
INSERT INTO `sys_role_permission` VALUES (4, 4, 1);
INSERT INTO `sys_role_permission` VALUES (5, 5, 1);
INSERT INTO `sys_role_permission` VALUES (6, 6, 1);
INSERT INTO `sys_role_permission` VALUES (7, 7, 1);
INSERT INTO `sys_role_permission` VALUES (8, 8, 1);
INSERT INTO `sys_role_permission` VALUES (9, 9, 1);
INSERT INTO `sys_role_permission` VALUES (10, 10, 1);


