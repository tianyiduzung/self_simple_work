CREATE TABLE IF NOT EXISTS `man` (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键',
    mgNo varchar(128) NOT NULL DEFAULT '' COMMENT '用户名',
    password varchar(128) NOT NULL DEFAULT '' COMMENT '密码'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '用户登录信息表';

CREATE TABLE IF NOT EXISTS `Person` (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键',
    EmployeeID varchar(64) NOT NULL DEFAULT '' COMMENT '',
    Name varchar(64) NOT NULL DEFAULT '' COMMENT '',
    Sex varchar(64) NOT NULL DEFAULT '' COMMENT '',
    Date varchar(64) NOT NULL DEFAULT '' COMMENT '',
    City varchar(64) NOT NULL DEFAULT '' COMMENT '',
    Nation varchar(64) NOT NULL DEFAULT '' COMMENT '',
    Polity varchar(64) NOT NULL DEFAULT '' COMMENT '',
    Culture varchar(64) NOT NULL DEFAULT '' COMMENT '',
    Marriage varchar(64) NOT NULL DEFAULT '' COMMENT '',
    Graduate varchar(64) NOT NULL DEFAULT '' COMMENT '',
    Spec varchar(64) NOT NULL DEFAULT '' COMMENT '',
    Speci varchar(64) NOT NULL DEFAULT '' COMMENT '',
    Wtype varchar(64) NOT NULL DEFAULT '' COMMENT '',
    Duty varchar(64) NOT NULL DEFAULT '' COMMENT '',
    Depart varchar(64) NOT NULL DEFAULT '' COMMENT '',
    IDcard varchar(64) NOT NULL DEFAULT '' COMMENT '',
    Address varchar(64) NOT NULL DEFAULT '' COMMENT '',
    Postcode varchar(64) NOT NULL DEFAULT '' COMMENT '',
    HomePhone varchar(64) NOT NULL DEFAULT '' COMMENT '',
    Mobile varchar(64) NOT NULL DEFAULT '' COMMENT '',
    Email varchar(64) NOT NULL DEFAULT '' COMMENT ''
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '用户信息表';

CREATE TABLE IF NOT EXISTS `Wage` (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键',
    mgNo varchar(128) NOT NULL DEFAULT '' COMMENT '用户名',
    password varchar(128) NOT NULL DEFAULT '' COMMENT '密码'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT 'Wage';

CREATE TABLE IF NOT EXISTS `Attend` (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键',
    mgNo varchar(128) NOT NULL DEFAULT '' COMMENT '用户名',
    password varchar(128) NOT NULL DEFAULT '' COMMENT '密码'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT 'Attend';