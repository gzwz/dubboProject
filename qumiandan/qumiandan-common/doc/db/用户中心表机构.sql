use user_center;

drop index username_index on sys_user;

drop index unionid_index on sys_user;

drop index alipay_id_index on sys_user;

drop table if exists sys_user;

/*==============================================================*/
/* Table: sys_user                                              */
/*==============================================================*/

create table sys_user
(
   id                   bigint not null comment '编号',
   unionid				varchar(50) comment '微信账号unionid',
   username             varchar(50) comment '账号',
   open_id              varchar(50) comment '微信openid',
   alipay_id            varchar(50) comment '支付宝id',
   password             varchar(45) comment '密码',
   salt                 varchar(45) comment 'md5密码盐',
   name                 varchar(45) comment '名字',
   orgid                bigint comment '部门id',
   pid                  bigint comment '父级id',
   status               int(11) comment '状态(1：启用  2：冻结  3：删除）',
   proc_ode             varchar(10) comment '省',
   city_code            varchar(10) comment '市',
   county_cdoe          varchar(10) comment '区',
   version              int(11) comment '保留字段',
   create_id            bigint(18) comment '创建人',
   update_id            bigint(18) comment '更新人',
   create_date          datetime comment '创建时间',
   update_date          datetime,
   primary key (id)
);

alter table sys_user comment '用户表基础信息表';

/*==============================================================*/
/* Index: username                                              */
/*==============================================================*/

CREATE INDEX username_index ON sys_user(username); 
/*==============================================================*/
/* Index: open_id                                                 */
/*==============================================================*/
CREATE INDEX unionid_index ON sys_user(unionid); 
/*==============================================================*/
/* Index: alipay_id                                               */
/*==============================================================*/
create  INDEX alipay_id_index on sys_user(alipay_id);


drop index mobile_index on sys_user_info;

drop index id_card_index on sys_user_info;

drop table if exists sys_user_info;

/*==============================================================*/
/* Table: sys_user_info                                         */
/*==============================================================*/
/*create table sys_user_info
(
   user_id              bigint not null comment '用户id',
   nickname             varchar(50),
   realname             varchar(50),
   head_sculpture       varchar(255),
   id_card              varchar(18) comment '身份证',
   mobile               varchar(20) comment '电话',
   phone                varchar(20) comment '座机',
   sex                  int comment '性别',
   email                varchar(50) comment '邮箱',
   bank_account         varchar(25) comment '银行卡',
   birthday             datetime,
   primary key (user_id)
);*/
CREATE TABLE `sys_user_info` (
   `user_id` bigint(20) NOT NULL COMMENT '用户id',
   `nickname` varchar(50) DEFAULT NULL COMMENT '昵称',
   `realname` varchar(50) DEFAULT NULL COMMENT '真实名字',
   `id_card` varchar(18) DEFAULT NULL COMMENT '身份证',
   `mobile` varchar(20) DEFAULT NULL COMMENT '电话',
   `phone` varchar(20) DEFAULT NULL COMMENT '座机',
   `sex` int(11) DEFAULT NULL COMMENT '性别',
   `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
   `bank_account` varchar(25) DEFAULT NULL COMMENT '银行卡',
   `birthday` datetime DEFAULT NULL COMMENT '生日',
   `portrait` varchar(255) DEFAULT NULL COMMENT '头像',
   PRIMARY KEY (`user_id`),
   KEY `mobile_index` (`mobile`),
   KEY `id_card_index` (`id_card`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

alter table sys_user_info comment '用户信息表';

# create  INDEX mobile_index on sys_user_info(mobile);
#
# create  INDEX id_card_index on sys_user_info(id_card);


drop table if exists sys_role;
/*==============================================================*/
/* Table: sys_role                                              */
/*==============================================================*/
create table sys_role
(
   id                   bigint not null auto_increment comment '角色id',
   sort                 int comment '序号',
   name                 varchar(255) comment '角色名称',
   deptid               int(11) comment '部门名称',
   ealias               varchar(50) comment '英文别名',
   create_id            bigint,
   update_id            bigint,
   create_date          datetime,
   update_date          datetime,
   primary key (id)
);


drop table if exists sys_user_role;

/*==============================================================*/
/* Table: sys_user_role                                         */
/*==============================================================*/
create table sys_user_role
(
   id                   bigint not null comment '编号',
   sys_user_id          bigint not null comment '用户id',
   sys_role_id          bigint not null comment '角色id',
   primary key (id)
);

alter table sys_user_role comment '用户角色关系表';


drop table if exists sys_permission;

/*==============================================================*/
/* Table: sys_permission                                        */
/*==============================================================*/
create table sys_permission
(
   id                   bigint not null auto_increment comment '编号',
   permission           varchar(255) comment '权限',
   name                 varchar(100) comment '权限名',
   sort                 int,
   pid                  bigint comment '父级权限',
   status               int comment '菜单状态 :  1:启用   0:不启用',
   create_id            bigint(18) comment '创建者id',
   update_id            bigint(18) comment '修改者id',
   create_date          datetime comment '创建时间',
   update_date          datetime comment '修改时间',
   primary key (id)
);

alter table sys_permission comment '权限表(菜单)';


/*==============================================================*/
/* Table: sys_role_permission                                   */
/*==============================================================*/
create table sys_role_permission
(
   id                   bigint auto_increment comment '编号',
   permission_id        bigint(11) not null comment '菜单id',
   role_id              int(11) not null comment '角色id',
   primary key (id)
);

alter table sys_role_permission comment '角色和权限关联表';


drop table if exists sys_organization;

/*==============================================================*/
/* Table: sys_organization                                      */
/*==============================================================*/
create table sys_organization
(
   id                   bigint not null comment '编号id',
   num                  bigint,
   pid                  bigint,
   simplename           varchar(45),
   fullname             varchar(255),
   tips                 varchar(255),
   version              int(11),
   create_id            bigint(18) comment '创建人id',
   update_id            bigint(18) comment '更新人id',
   create_date          datetime comment '创建时间',
   update_date          datetime comment '更新时间',
   primary key (id)
);
alter table sys_organization comment '组织机构';


DROP TABLE IF EXISTS sys_shiro_filter;
CREATE TABLE sys_shiro_filter  (
  id bigint(11) NOT NULL AUTO_INCREMENT comment '编号id',			
  name varchar(255) comment '接口地址名',
  perms varchar(255) comment '权限',
  sort int(11) comment '排序',
  PRIMARY KEY (id)
);
alter table sys_shiro_filter comment 'shiro控制权限列表';