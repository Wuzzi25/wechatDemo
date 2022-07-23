CREATE TABLE `Counters` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `count` int(11) NOT NULL DEFAULT '1',
  `createdAt` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updatedAt` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- 用戶信息表，用於存儲用户基础信息
CREATE TABLE `User` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `userCode` varchar(36) NOT NULL DEFAULT '' COMMENT '用戶編號，uuid',
    `wechatName` varchar(100) NOT NULL DEFAULT '' COMMENT '用户微信名称',
    `wechatAccount` varchar(100) NOT NULL DEFAULT '' COMMENT '微信號，唯一键',
    `userName` varchar(50) NOT NULL DEFAULT '' COMMENT '用户输入的姓名',
    `userPhone` varchar(16) NOT NULL DEFAULT '' COMMENT '手機號，唯一鍵',
    `vipFlag`  int(11) NOT NULL DEFAULT 0 COMMENT 'vip标识，0普通用户 1会员 2超级用户',
    `qrCode` varchar(200) NOT NULL DEFAULT '' COMMENT '用户二维码，创建时就生成，会员身份时返回给前端',
    `isRecommend` int(11) NOT NULL DEFAULT 0 COMMENT '是否被推荐，0否 1是',
    `recommendUserCode` varchar(36) NOT NULL DEFAULT '' COMMENT '推荐人',
    `beVipAt` timestamp DEFAULT NULL CURRENT_TIMESTAMP COMMENT '成为会员时间',
    `createdAt` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updatedAt` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB COLLATE = utf8mb4_unicode_ci DEFAULT CHARSET=utf8mb4;

create unique index uni_user_userCode on User(userCode);
create unique index uni_user_wechat on User(wechatAccount);

-- 账户余额表
CREATE TABLE `Account` (
   `id` int(11) NOT NULL AUTO_INCREMENT,
   `userCode` varchar(36) NOT NULL DEFAULT '' COMMENT '用戶編號，uuid',
   `balance` DECIMAL(10,2) NOT NULL DEFAULT 0 COMMENT '账号余额',
   `createAt` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
   `updatedAt` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
   PRIMARY KEY (`id`)
) ENGINE=InnoDB COLLATE = utf8mb4_unicode_ci DEFAULT CHARSET=utf8mb4;

create unique index uni_account_userCode on Account(userCode);

-- 账户余额历史表
CREATE TABLE `AccountHistory` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `userCode` varchar(36) NOT NULL DEFAULT '' COMMENT '用戶編號，uuid',
    `change` DECIMAL(10,2) NOT NULL DEFAULT 0 COMMENT '变动金额',
    `updateType` int(11) NOT NULL DEFAULT 0 COMMENT '变更类型，0 推荐奖励，1 提现',
    `addFrom` varchar(120) NOT NULL DEFAULT '' COMMENT '推荐成功的好友列表',
    `needAudit` int(11) NOT NULL DEFAULT 0 COMMENT '是否需要审核，0 不需要，1 需要',
    `status` int(11) NOT NULL DEFAULT 0 COMMENT '0 有效/审核通过，1 审核不通过，2 审核中',
    `createdAt` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updatedAt` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB COLLATE = utf8mb4_unicode_ci DEFAULT CHARSET=utf8mb4;

create index idx_accountHis_userCode on AccountHistory(userCode);

-- 推荐记录表
CREATE TABLE `Recommend` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `userCode` varchar(36) NOT NULL DEFAULT '' COMMENT '主动发起推荐用戶編號，uuid',
    `beRecommendUserCode` varchar(36) NOT NULL DEFAULT '' COMMENT '推荐成功的用户编号，uuid',
    `beenReward` int(11) NOT NULL DEFAULT 0 COMMENT '是否已奖励',
    `createdAt` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updatedAt` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB COLLATE = utf8mb4_unicode_ci DEFAULT CHARSET=utf8mb4;

create unique index uni_recommend_u1_u2 on Recommend(userCode,recommendUserCode);