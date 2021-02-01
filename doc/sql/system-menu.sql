-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
  `name` varchar(50) NOT NULL COMMENT '菜单名称',
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父菜单ID',
  `order_num` int(4) DEFAULT '0' COMMENT '显示顺序',
  `url` varchar(200) DEFAULT '' COMMENT '路由地址',
  `method` tinyint(4) DEFAULT NULL,
  `type` tinyint(4) DEFAULT '0' COMMENT '菜单类型（M目录 C菜单 F按钮）',
  `visible` tinyint(4) DEFAULT '0' COMMENT '菜单状态（0显示 1隐藏）',
  `status` tinyint(4) DEFAULT '0' COMMENT '菜单状态（0正常 1停用）',
  `perms` varchar(100) DEFAULT NULL COMMENT '权限标识',
  `icon` varchar(100) DEFAULT '#' COMMENT '菜单图标',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  `created_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `version` int(10) DEFAULT NULL,
  `del_flag` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1355340337126453251 DEFAULT CHARSET=utf8 COMMENT='菜单权限表';

-- ----------------------------
-- Records of menu
-- ----------------------------
INSERT INTO `menu` VALUES ('1355334878432759810', '系统管理', null, '1', '', null, '1', '0', '0', 'system-menu', '#', '', 'system', '2021-01-30 09:59:51', 'system', '2021-01-30 09:59:51', '0', '0');
INSERT INTO `menu` VALUES ('1355335417656721410', '菜单列表', '1355334878432759810', '1', '/admin/menu', '1', '2', '0', '0', 'menu:list:admin', '#', '', 'system', '2021-01-30 10:01:59', 'system', '2021-01-30 10:01:59', '0', '0');
INSERT INTO `menu` VALUES ('1355336312566001665', '新增', '1355335417656721410', '1', '/admin/menu/add', '3', '3', '0', '0', 'menu:add', '#', '', 'system', '2021-01-30 10:05:33', 'system', '2021-01-30 10:05:33', '0', '0');
INSERT INTO `menu` VALUES ('1355336590186995714', '修改', '1355335417656721410', '1', '/admin/menu/update', '2', '3', '0', '0', 'menu:update', '#', '', 'system', '2021-01-30 10:06:39', 'system', '2021-01-30 10:06:39', '0', '0');
INSERT INTO `menu` VALUES ('1355336844277907457', '删除', '1355335417656721410', '3', '/admin/menu/{id}', '4', '3', '0', '0', 'menu:delete', '#', '', 'system', '2021-01-30 10:07:39', 'system', '2021-01-30 10:07:39', '0', '0');
INSERT INTO `menu` VALUES ('1355337443832705025', '角色列表', '1355334878432759810', '3', '/admin/role', '1', '2', '0', '0', 'role:list:admin', '#', '', 'system', '2021-01-30 10:10:02', 'system', '2021-01-30 10:10:02', '0', '0');
INSERT INTO `menu` VALUES ('1355337809026547714', '新增', '1355337443832705025', '1', '/admin/role/add', '3', '3', '0', '0', 'role:add', '#', '', 'system', '2021-01-30 10:11:29', 'system', '2021-01-30 10:11:29', '0', '0');
INSERT INTO `menu` VALUES ('1355338040967385089', '修改', '1355337443832705025', '2', '/admin/role/update', '2', '3', '0', '0', 'role:update', '#', '', 'system', '2021-01-30 10:12:25', 'system', '2021-01-30 10:12:25', '0', '0');
INSERT INTO `menu` VALUES ('1355338317476876289', '删除', '1355337443832705025', '3', '/admin/role/{id}', '4', '3', '0', '0', 'role:delete', '#', '', 'system', '2021-01-30 10:13:31', 'system', '2021-01-30 10:13:31', '0', '0');
INSERT INTO `menu` VALUES ('1355339254819966978', '用户列表', '1355334878432759810', '3', '/admin/user', '1', '2', '0', '0', 'user:list:admin', '#', '', 'system', '2021-01-30 10:17:14', 'system', '2021-01-30 10:17:14', '0', '0');
INSERT INTO `menu` VALUES ('1355339542876385282', '新增', '1355339254819966978', '3', '/admin/user/add', '3', '3', '0', '0', 'user:add', '#', '', 'system', '2021-01-30 10:18:23', 'system', '2021-01-30 10:18:23', '0', '0');
INSERT INTO `menu` VALUES ('1355339795134328834', '修改', '1355339254819966978', '2', '/admin/user/update', '2', '3', '0', '0', 'user:update', '#', '', 'system', '2021-01-30 10:19:23', 'system', '2021-01-30 10:19:23', '0', '0');
INSERT INTO `menu` VALUES ('1355340033517568002', '删除', '1355339254819966978', '3', '/admin/user/{id}', '4', '3', '0', '0', 'user:delete', '#', '', 'system', '2021-01-30 10:20:20', 'system', '2021-01-30 10:20:20', '0', '0');
INSERT INTO `menu` VALUES ('1355340337126453250', '重置密码', '1355339254819966978', '4', '/admin/user/{id}/reset-pwd', '2', '3', '0', '0', 'user:reset-pwd', '#', '', 'system', '2021-01-30 10:21:32', 'system', '2021-01-30 10:21:32', '0', '0');

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `name` varchar(100) DEFAULT NULL COMMENT '角色名称',
  `sort` int(4) DEFAULT NULL COMMENT '显示顺序',
  `status` char(1) DEFAULT NULL COMMENT '角色状态（0正常 1停用）',
  `created_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `version` int(10) DEFAULT NULL COMMENT '备注',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1355355582171656194 DEFAULT CHARSET=utf8 COMMENT='角色信息表';

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('1355355582171656193', '超级管理员', '1', '0', 'system', '2021-01-30 11:22:07', 'system', '2021-01-30 11:22:07', '0', '0');

-- ----------------------------
-- Table structure for role_menu
-- ----------------------------
DROP TABLE IF EXISTS `role_menu`;
CREATE TABLE `role_menu` (
  `id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  `menu_id` bigint(20) NOT NULL COMMENT '菜单ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色和菜单关联表';

-- ----------------------------
-- Records of role_menu
-- ----------------------------
INSERT INTO `role_menu` VALUES ('1355355582276513794', '1355355582171656193', '1355334878432759810');
INSERT INTO `role_menu` VALUES ('1355355582284902401', '1355355582171656193', '1355335417656721410');
INSERT INTO `role_menu` VALUES ('1355355582293291010', '1355355582171656193', '1355336312566001665');
INSERT INTO `role_menu` VALUES ('1355355582293291011', '1355355582171656193', '1355336590186995714');
INSERT INTO `role_menu` VALUES ('1355355582293291012', '1355355582171656193', '1355336844277907457');
INSERT INTO `role_menu` VALUES ('1355355582301679617', '1355355582171656193', '1355337443832705025');
INSERT INTO `role_menu` VALUES ('1355355582301679618', '1355355582171656193', '1355337809026547714');
INSERT INTO `role_menu` VALUES ('1355355582301679619', '1355355582171656193', '1355338040967385089');
INSERT INTO `role_menu` VALUES ('1355355582310068225', '1355355582171656193', '1355338317476876289');
INSERT INTO `role_menu` VALUES ('1355355582310068226', '1355355582171656193', '1355339254819966978');
INSERT INTO `role_menu` VALUES ('1355355582310068227', '1355355582171656193', '1355339542876385282');
INSERT INTO `role_menu` VALUES ('1355355582310068228', '1355355582171656193', '1355339795134328834');
INSERT INTO `role_menu` VALUES ('1355355582318456834', '1355355582171656193', '1355340033517568002');
INSERT INTO `role_menu` VALUES ('1355355582326845442', '1355355582171656193', '1355340337126453250');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `user_name` varchar(50) DEFAULT NULL COMMENT '用户账号',
  `nick_name` varchar(30) DEFAULT NULL COMMENT '用户昵称',
  `type` tinyint(4) DEFAULT '0' COMMENT '用户类型（0:系统用户1:普通用户）',
  `email` varchar(50) DEFAULT '' COMMENT '用户邮箱',
  `phone` varchar(50) DEFAULT '' COMMENT '手机号码',
  `sex` tinyint(4) DEFAULT '0' COMMENT '用户性别（0男 1女 2未知）',
  `avatar` varchar(100) DEFAULT '' COMMENT '头像地址',
  `password` varchar(100) DEFAULT '' COMMENT '密码',
  `status` tinyint(4) DEFAULT '0' COMMENT '帐号状态（0正常 1停用）',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `version` int(10) DEFAULT NULL,
  `created_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1355085027333017602 DEFAULT CHARSET=utf8 COMMENT='用户信息表';

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1355025873780269057', 'alibaba', '阿里巴巴', '0', '56456554@qq,com', 'B14E4B6007611A9DA947AEB2EC6B429C', '1', 'https://www.baidu.com/', '{bcrypt}$2a$10$ld4MS8ZZfQZajh2rY.352ud1JTxuCN5qmd4gNfoA22p3uc8IZQs/e', '0', null, '0', 'system', '2021-01-29 13:31:58', 'system', '2021-01-29 13:31:58', '0');

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户和角色关联表';

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES ('1355006743912030209', '1355025873780269057', '1355355582171656193');

-- ----------------------------
-- Table structure for oauth_client_details
-- ----------------------------
DROP TABLE IF EXISTS `oauth_client_details`;
CREATE TABLE `oauth_client_details` (
  `client_id` varchar(128) NOT NULL,
  `resource_ids` text,
  `client_secret` varchar(128) DEFAULT NULL,
  `scope` varchar(128) DEFAULT NULL,
  `authorized_grant_types` varchar(128) DEFAULT NULL,
  `web_server_redirect_uri` varchar(128) DEFAULT NULL,
  `authorities` varchar(128) DEFAULT NULL,
  `access_token_validity` int(11) DEFAULT NULL,
  `refresh_token_validity` int(11) DEFAULT NULL,
  `additional_information` text,
  `autoapprove` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`client_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of oauth_client_details
-- ----------------------------
INSERT INTO `oauth_client_details` VALUES ('mall_web', 'mall-system', '{bcrypt}$2a$10$ld4MS8ZZfQZajh2rY.352ud1JTxuCN5qmd4gNfoA22p3uc8IZQs/e', 'read,write', 'refresh_token,sms_code,password,auth_register_sms_code,wechat', null, null, '259000', '259000', null, null);
