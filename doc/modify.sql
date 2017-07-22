-- -----------------------------------------------------
-- 修改表tuser，新增字段isGag、hx_password、hx_status  snow.xu 20160901
-- -----------------------------------------------------
ALTER TABLE `tuser`
	ADD COLUMN `isGag` TINYINT(1) NULL DEFAULT '0' COMMENT '是否禁言1:是；0：否' AFTER `modifydatetime`,
	ADD COLUMN `hx_password` VARCHAR(36) NULL COMMENT '环信登录密码' AFTER `isGag`,
	ADD COLUMN `hx_status` TINYINT(1) NULL DEFAULT '0' COMMENT '环信是否注册，1：已注册；0：未注册' AFTER `hx_password`;

-- -----------------------------------------------------
-- 新增支付订单表zc_pay_order  snow.xu 20160905
-- -----------------------------------------------------
CREATE TABLE `zc_pay_order` (
  `id` varchar(36) NOT NULL COMMENT '主键',
  `order_no` varchar(64) DEFAULT NULL COMMENT '订单编号',
  `object_type` varchar(6) DEFAULT NULL COMMENT '类型{PO}',
  `object_id` varchar(36) DEFAULT NULL COMMENT '业务id',
  `channel` varchar(4) DEFAULT NULL COMMENT '支付渠道{CS}',
  `total_fee` double DEFAULT NULL COMMENT '支付金额',
  `user_id` varchar(36) DEFAULT NULL COMMENT '支付人',
  `pay_status` varchar(4) DEFAULT NULL COMMENT '支付状态{PS}',
  `paytime` varchar(36) DEFAULT NULL COMMENT '支付时间',
  `addtime` datetime DEFAULT NULL COMMENT '创建时间',
	PRIMARY KEY (`id`)
)
COMMENT='支付订单表'
COLLATE='utf8_general_ci'
ENGINE=InnoDB;

-- -----------------------------------------------------
-- 修改表zc_bbs_reward，新增字段paytime  snow.xu 20160907
-- -----------------------------------------------------
ALTER TABLE `zc_bbs_reward`
	ADD COLUMN `paytime` DATETIME NULL DEFAULT NULL COMMENT '支付时间' AFTER `pay_status`;

-- -----------------------------------------------------
-- 修改表zc_report，新增字段handle_status、handle_user_id、handle_remark、handle_time snow.xu 20160908
-- -----------------------------------------------------
ALTER TABLE `zc_report`
	ADD COLUMN `handle_status` VARCHAR(4) NULL DEFAULT 'HS01' COMMENT '处理状态{HS}' AFTER `addtime`,
	ADD COLUMN `handle_user_id` VARCHAR(36) NULL DEFAULT NULL COMMENT '处理人' AFTER `handle_status`,
	ADD COLUMN `handle_remark` VARCHAR(500) NULL DEFAULT NULL COMMENT '处理结果' AFTER `handle_user_id`,
	ADD COLUMN `handle_time` DATETIME NULL DEFAULT NULL COMMENT '处理时间' AFTER `handle_remark`;

-- -----------------------------------------------------
-- 修改表zc_file，新增排序seq字段 snow.xu 20160920
-- -----------------------------------------------------
ALTER TABLE `zc_file`
	ADD COLUMN `seq` INT(11) NULL DEFAULT NULL COMMENT '排序，越小越靠前' AFTER `duration`;

-- -----------------------------------------------------
-- 修改表zc_wallet_detail，新增字段 snow.xu 20170112
-- -----------------------------------------------------
ALTER TABLE `zc_wallet_detail`
	ADD COLUMN `bank_account` VARCHAR(18) NULL DEFAULT NULL COMMENT '开户名' AFTER `handle_time`,
	ADD COLUMN `bank_phone` VARCHAR(18) NULL DEFAULT NULL COMMENT '开户预留手机号' AFTER `bank_account`,
	ADD COLUMN `bank_id_no` VARCHAR(18) NULL DEFAULT NULL COMMENT '开户身份证号' AFTER `bank_phone`,
	ADD COLUMN `bank_card` VARCHAR(36) NULL DEFAULT NULL COMMENT '银行卡号' AFTER `bank_id_no`;

ALTER TABLE `zc_wallet_detail`
	CHANGE COLUMN `handle_remark` `handle_remark` VARCHAR(500) NULL DEFAULT NULL COMMENT '处理结果' AFTER `handle_user_id`;


-- -----------------------------------------------------
-- 修改表zc_order，新增字段 snow.xu 20170215
-- -----------------------------------------------------
ALTER TABLE `zc_order`
	ADD COLUMN `return_confirm_time` DATETIME NULL DEFAULT NULL COMMENT '卖家同意/拒绝退货时间' AFTER `return_apply_time`;
ALTER TABLE `zc_order`
	ADD COLUMN `return_apply_reason` VARCHAR(4) NULL DEFAULT NULL COMMENT '申请退货理由' AFTER `return_apply_time`,
	ADD COLUMN `return_apply_reason_other` VARCHAR(255) NULL DEFAULT NULL COMMENT '申请退货理由-其他' AFTER `return_apply_reason`;
ALTER TABLE `zc_order`
	ADD COLUMN `return_deliver_time` DATETIME NULL DEFAULT NULL COMMENT '退货发货时间' AFTER `deliver_time`,
	ADD COLUMN `return_express_name` VARCHAR(128) NULL DEFAULT NULL COMMENT '退货快递公司' AFTER `express_no`,
	ADD COLUMN `return_express_no` VARCHAR(128) NULL DEFAULT NULL COMMENT '退货运单号' AFTER `return_express_name`;
ALTER TABLE `zc_order`
	ADD COLUMN `refuse_return_reason` VARCHAR(4) NULL DEFAULT NULL COMMENT '拒绝退货理由' AFTER `return_apply_reason_other`,
	ADD COLUMN `refuse_return_reason_other` VARCHAR(255) NULL DEFAULT NULL COMMENT '拒绝退货理由-其他' AFTER `refuse_return_reason`;

ALTER TABLE `zc_address`
	ADD COLUMN `order_id` VARCHAR(36) NULL DEFAULT NULL COMMENT '订单ID' AFTER `is_default`;


-- -----------------------------------------------------
-- 新增线下转账记录表zc_offline_transfer  snow.xu 20170322
-- -----------------------------------------------------
CREATE TABLE `zc_offline_transfer` (
  `id` varchar(36) NOT NULL COMMENT '主键',
  `transfer_no` varchar(64) DEFAULT NULL COMMENT '转账编号',
  `user_id` varchar(36) DEFAULT NULL COMMENT '转账人ID',
  `transfer_user_name` varchar(6) DEFAULT NULL COMMENT '汇款人姓名',
  `transfer_amount` double DEFAULT NULL COMMENT '汇款金额',
  `transfer_time` datetime DEFAULT NULL COMMENT '汇款时间',
  `remark` varchar(128) DEFAULT NULL COMMENT '汇款备注',
  `handle_status` varchar(4) DEFAULT 'HS01' COMMENT '处理状态{HS}',
  `handle_user_id` varchar(36) DEFAULT NULL COMMENT '处理人',
  `handle_remark` varchar(300) DEFAULT NULL COMMENT '处理结果',
  `handle_time` datetime DEFAULT NULL COMMENT '处理时间',
  `is_wallet` tinyint(1) DEFAULT '0' COMMENT '是否充值到余额',
  `addtime` datetime DEFAULT NULL COMMENT '创建时间',
	PRIMARY KEY (`id`)
)
COMMENT='线下转账记录表'
COLLATE='utf8_general_ci'
ENGINE=InnoDB;


-- -----------------------------------------------------
-- 修改分类表zc_category，新增字段auto_read  snow.xu 20170508
-- -----------------------------------------------------
ALTER TABLE `zc_category`
	ADD COLUMN `auto_read` INT NULL DEFAULT '0' COMMENT '定时增加该分类下的所有帖子的阅读数量' AFTER `isDeleted`;

-- -----------------------------------------------------
-- 修改帖子表zc_forum_bbs，新增字段last_update_user_id、last_update_time  snow.xu 20170522
-- -----------------------------------------------------
ALTER TABLE `zc_forum_bbs`
	ADD COLUMN `last_update_user_id` VARCHAR(36) NULL DEFAULT NULL COMMENT '最后编辑帖子人（不包括加亮等）' AFTER `last_comment_time`,
	ADD COLUMN `last_update_time` DATETIME NULL DEFAULT NULL COMMENT '最后编辑帖子时间（不包括加亮等）' AFTER `last_update_user_id`;


ALTER TABLE `zc_order`
	CHANGE COLUMN `face_to_face` `face_status` VARCHAR(4) NULL DEFAULT NULL COMMENT '当面交易状态{FS}' AFTER `order_close_reason`,
	ADD COLUMN `face_time` DATETIME NULL DEFAULT NULL COMMENT '当面交易申请时间' AFTER `face_status`;

-- -----------------------------------------------------
-- 新增数据字段帖子-日志类型  snow.xu 20170526
-- -----------------------------------------------------
ALTER TABLE `zc_wallet_detail`
	ADD COLUMN `wallet_amount` double NULL DEFAULT NULL COMMENT '钱包当前余额' AFTER `bank_card`;

INSERT INTO tbasedata (id,name,basetype_code,seq) VALUES ('BL001','编辑','BL','100');
INSERT INTO tbasedata (id,name,basetype_code,seq) VALUES ('BL002','打开/关闭','BL','100');
INSERT INTO tbasedata (id,name,basetype_code,seq) VALUES ('BL003','打开/关闭回复','BL','100');
INSERT INTO tbasedata (id,name,basetype_code,seq) VALUES ('BL004','置顶/加精/加亮','BL','100');
INSERT INTO tbasedata (id,name,basetype_code,seq) VALUES ('BL005','移动','BL','100');

update zc_wallet_detail d set d.wallet_amount = (select amount from zc_wallet w where w.user_id = d.user_id)


INSERT INTO tbasedata (id,name,basetype_code,seq,description) VALUES ('TM001','E1Nuiaw5MGCmLWxF74fyYYVp1I_xQIzwBwhSFH8ySrM','TM','100','认证失败通知');
INSERT INTO tbasedata (id,name,basetype_code,seq,description) VALUES ('TM002','FbK8rD55RVm76F7wAGsiNN1jEJT-P_9F1P9_gIHHNmo','TM','100','转账通知');
INSERT INTO tbasedata (id,name,basetype_code,seq,description) VALUES ('TM003','LDWdvz4zzExb3lto9NwXGex6bcJDSbafaVdCBOd5KHA','TM','100','提现成功通知');
INSERT INTO tbasedata (id,name,basetype_code,seq,description) VALUES ('TM004','PJcRzMdQZg4IX-FAxV759RqdG5e6vbUgJC20e3TsJdc','TM','100','退货申请结果通知');
INSERT INTO tbasedata (id,name,basetype_code,seq,description) VALUES ('TM005','EDSdqzbUMyuhX5-7q7dhDCT52z-05oCqPDER8KCtLgE','TM','100','充值成功通知');
INSERT INTO tbasedata (id,name,basetype_code,seq,description) VALUES ('TM006','cOBzm5aekMSaH5uUiUG2Bpt1MHasD_k5bTuSuVHkct8','TM','100','出价被超通知');
INSERT INTO tbasedata (id,name,basetype_code,seq,description) VALUES ('TM007','dR1fVX69sLj4RTnbRoYoMNm-uWFlJ38bclWXRIi5ozU','TM','100','拍卖结果通知');
INSERT INTO tbasedata (id,name,basetype_code,seq,description) VALUES ('TM008','dR6n77fa0ibsIdh22Pd9-lh6O5JCaTTphcXayZgDoLw','TM','100','拍卖结束提醒');
INSERT INTO tbasedata (id,name,basetype_code,seq,description) VALUES ('TM009','fcv11oig2vas9vOcZvqsCctw5MggVDMBcP-IPKoJ8FE','TM','100','订单发货提醒');
INSERT INTO tbasedata (id,name,basetype_code,seq,description) VALUES ('TM010','iIVq1hycbzCmNGVKrIwkk2fK2npwCJU38rbEQGN7RgQ','TM','100','发货提醒');
INSERT INTO tbasedata (id,name,basetype_code,seq,description) VALUES ('TM011','jXS_AYFYKj7lLPC0l7Zn3W9K4xOqSzp54-uTIQLBCx0','TM','100','保证金不退回通知');
INSERT INTO tbasedata (id,name,basetype_code,seq,description) VALUES ('TM012','l20c7uj243g_IFRUA_90ZOzjMnFSZY5K3RWpZl-idA0','TM','100','交易完成通知');
INSERT INTO tbasedata (id,name,basetype_code,seq,description) VALUES ('TM013','qbCKN9CK23F40HTXKlDCVXQKWpri1E38Sp1WEQrVgqM','TM','100','出价成功通知');
INSERT INTO tbasedata (id,name,basetype_code,seq,description) VALUES ('TM014','s2_sV3KfQsEUH-jpSzOI344vwuqBL_3iQq3Z8HwiQKU','TM','100','退款通知');
INSERT INTO tbasedata (id,name,basetype_code,seq,description) VALUES ('TM015','sRVpX2m-h5wy7Ppc1bjw5h7-m6HXWUvSFSmRo6Kbml8','TM','100','扣款通知');
INSERT INTO tbasedata (id,name,basetype_code,seq,description) VALUES ('TM016','tFJktmKwd-GlJgk2m_-kIYsVGtn55KZTByAZcQaVxFo','TM','100','退货申请提醒');
INSERT INTO tbasedata (id,name,basetype_code,seq,description) VALUES ('TM017','tIwenEOFtpEYWieN1V22BH1XPVHgsPppTpaFDx0s-TU','TM','100','认证成功通知');
INSERT INTO tbasedata (id,name,basetype_code,seq,description) VALUES ('TM018','w1wGLR6cYAZDuJurdIg-RQN0rF8gkwb5p9j6ybG58l4','TM','100','申请结果通知');
INSERT INTO tbasedata (id,name,basetype_code,seq,description) VALUES ('TM019','xuT44N7YW0chb-mu49fXVmJjQmaxhmL3q6csttrUKp8','TM','100','提现失败通知');
INSERT INTO tbasedata (id,name,basetype_code,seq,description) VALUES ('TM020','xvmbAkdIol_KPYACbYW1zqvcma_2QRhm98HSx2djpxE','TM','100','实时交易提醒');
INSERT INTO tbasedata (id,name,basetype_code,seq,description) VALUES ('TM021','zzYCUjHwdKP753wMuv3BbjAZAaBEcqJh7TmKn32FPEo','TM','100','付款提醒');

update zc_wallet set pay_password = md5(pay_password) where pay_password is not null;



-- oss图片路径替换cdn加速地址 start --------------

update tbasedata set icon = REPLACE(icon,'http://wsale.oss-cn-shanghai.aliyuncs.com/','http://img10.zcys2016.com/') where icon is not NULL;

update zc_authentication set id_front_byhand = REPLACE(id_front_byhand,'http://wsale.oss-cn-shanghai.aliyuncs.com/','http://img10.zcys2016.com/') where id_front_byhand is not NULL;
update zc_authentication set id_front = REPLACE(id_front,'http://wsale.oss-cn-shanghai.aliyuncs.com/','http://img10.zcys2016.com/') where id_front is not NULL;
update zc_authentication set id_back = REPLACE(id_back,'http://wsale.oss-cn-shanghai.aliyuncs.com/','http://img10.zcys2016.com/') where id_back is not NULL;
update zc_authentication set bussiness_license = REPLACE(bussiness_license,'http://wsale.oss-cn-shanghai.aliyuncs.com/','http://img10.zcys2016.com/') where bussiness_license is not NULL;
update zc_authentication set legal_person_id_front = REPLACE(legal_person_id_front,'http://wsale.oss-cn-shanghai.aliyuncs.com/','http://img10.zcys2016.com/') where legal_person_id_front is not NULL;
update zc_authentication set legal_person_id_back = REPLACE(legal_person_id_back,'http://wsale.oss-cn-shanghai.aliyuncs.com/','http://img10.zcys2016.com/') where legal_person_id_back is not NULL;

update zc_banner set url = REPLACE(url,'http://wsale.oss-cn-shanghai.aliyuncs.com/','http://img10.zcys2016.com/') where url is not NULL;

update zc_bbs_comment set comment = REPLACE(comment,'http://wsale.oss-cn-shanghai.aliyuncs.com/','http://img10.zcys2016.com/') where comment is not NULL and ctype = 'IMAGE';

update zc_category set icon = REPLACE(icon,'http://wsale.oss-cn-shanghai.aliyuncs.com/','http://img10.zcys2016.com/') where icon is not NULL;

update zc_chat_msg set content = REPLACE(content,'http://wsale.oss-cn-shanghai.aliyuncs.com/','http://img10.zcys2016.com/') where content is not NULL and (mtype = 'IMAGE' or mtype = 'AUDIO');

update zc_file set file_original_url = REPLACE(file_original_url,'http://wsale.oss-cn-shanghai.aliyuncs.com/','http://img10.zcys2016.com/') where file_original_url is not NULL;
update zc_file set file_handle_url = REPLACE(file_handle_url,'http://wsale.oss-cn-shanghai.aliyuncs.com/','http://img10.zcys2016.com/') where file_handle_url is not NULL;

update zc_shop set logo_url = REPLACE(logo_url,'http://wsale.oss-cn-shanghai.aliyuncs.com/','http://img10.zcys2016.com/') where logo_url is not NULL;

-- oss图片路径替换cdn加速地址 end ---------------

-- -----------------------------------------------------
-- 修改用户表tuser，新增字段service_fee_per  snow.xu 20170614
-- -----------------------------------------------------
ALTER TABLE `tuser`
	ADD COLUMN `service_fee_per` INT NULL DEFAULT '0' COMMENT '拍品收取技术服务费百分比' AFTER `isGag`;
ALTER TABLE `zc_pay_order`
	ADD COLUMN `service_fee` BIGINT NULL DEFAULT '0' COMMENT '技术服务费' AFTER `total_fee`;
ALTER TABLE `zc_pay_order`
	ADD COLUMN `refund_fee` BIGINT NULL DEFAULT NULL COMMENT '退款金额' AFTER `refund_no`;


CREATE TABLE `zc_topic` (
  `id` varchar(36) NOT NULL COMMENT '主键',
  `category_id` varchar(36) DEFAULT NULL COMMENT '所属分类',
  `title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '帖子标题',
  `icon` varchar(255) DEFAULT NULL COMMENT '封面',
  `content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '专题内容',
  `topic_comment` int(11) DEFAULT '0' COMMENT '留言数',
  `topic_read` int(11) DEFAULT '0' COMMENT '阅读数',
  `topic_reward` int(11) DEFAULT '0' COMMENT '打赏数',
  `topic_praise` int(11) DEFAULT '0' COMMENT '点赞数',
  `topic_collect` int(11) DEFAULT '0' COMMENT '收藏数',
  `seq` int(11) DEFAULT '0' COMMENT '热门排序',
  `addUserId` varchar(36) DEFAULT NULL COMMENT '发布人',
  `addtime` datetime DEFAULT NULL COMMENT '发布时间',
  `updateUserId` varchar(36) DEFAULT NULL COMMENT '更新人ID',
  `updatetime` datetime DEFAULT NULL COMMENT '更新时间',
  `isDeleted` tinyint(1) DEFAULT '0' COMMENT '是否删除,1删除，0未删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='集专题';

CREATE TABLE `zc_topic_comment` (
  `id` varchar(36) NOT NULL COMMENT '主键',
  `topic_id` varchar(36) DEFAULT NULL COMMENT '专题ID',
  `comment` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '留言内容',
  `ctype` varchar(18) DEFAULT 'TEXT' COMMENT '类型：text、image、audio',
  `pid` varchar(36) DEFAULT NULL COMMENT '父留言ID',
  `isDeleted` tinyint(1) DEFAULT '0' COMMENT '是否删除,1删除，0未删除',
  `user_id` varchar(36) DEFAULT NULL COMMENT '留言人',
  `addtime` datetime DEFAULT NULL COMMENT '留言时间',
  `audit_status` varchar(4) DEFAULT 'AS01' COMMENT '审核状态{AS}',
  `audit_time` datetime DEFAULT NULL COMMENT '审核时间',
  `audit_user_id` varchar(36) DEFAULT NULL COMMENT '审核人',
  `audit_remark` varchar(500) DEFAULT NULL COMMENT '审核备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='专题留言表';

CREATE TABLE `zc_praise` (
  `id` varchar(36) NOT NULL COMMENT '主键',
  `object_type` varchar(36) DEFAULT NULL COMMENT '对象类型',
  `object_id` varchar(36) DEFAULT NULL COMMENT '对象ID',
  `user_id` varchar(36) DEFAULT NULL COMMENT '点赞人',
  `addtime` datetime DEFAULT NULL COMMENT '点赞时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='点赞表';

CREATE TABLE `zc_collect` (
  `id` varchar(36) NOT NULL COMMENT '主键',
  `object_type` varchar(36) DEFAULT NULL COMMENT '对象类型',
  `object_id` varchar(36) DEFAULT NULL COMMENT '对象ID',
  `user_id` varchar(36) DEFAULT NULL COMMENT '收藏人',
  `addtime` datetime DEFAULT NULL COMMENT '收藏时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='收藏表';

CREATE TABLE `zc_reward` (
  `id` varchar(36) NOT NULL COMMENT '主键',
  `object_type` varchar(36) DEFAULT NULL COMMENT '对象类型',
  `object_id` varchar(36) DEFAULT NULL COMMENT '对象ID',
  `reward_fee` bigint(20) DEFAULT NULL COMMENT '打赏金额',
  `user_id` varchar(36) DEFAULT NULL COMMENT '打赏人',
  `addtime` datetime DEFAULT NULL COMMENT '打赏时间',
  `pay_status` varchar(4) DEFAULT 'PS01' COMMENT '支付状态',
  `paytime` datetime DEFAULT NULL COMMENT '支付时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='打赏记录表';

-- -----------------------------------------------------
-- 修改支付订单表zc_pay_order，新增字段ref_transaction_no  snow.xu 20170621
-- -----------------------------------------------------
ALTER TABLE `zc_pay_order`
	ADD COLUMN `ref_transaction_no` VARCHAR(64) NULL DEFAULT NULL COMMENT '第三方支付订单号' AFTER `paytime`;
ALTER TABLE `zc_offline_transfer`
  ADD COLUMN `bank_code`  varchar(4) NULL COMMENT '银行编码{TB}' AFTER `remark`;

-- -----------------------------------------------------
-- 废弃表zc_bbs_reward，转移数据至表zc_reward  snow.xu 20170624
-- -----------------------------------------------------
insert into zc_reward (id, object_type, object_id, reward_fee, user_id, addtime, pay_status, paytime)
select t.id, 'BBS', t.bbs_id, round(t.reward_fee*100), t.user_id,t.addtime,t.pay_status, t.paytime  from zc_bbs_reward t

-- -----------------------------------------------------
--  新增字段  snow.xu 20170703
-- -----------------------------------------------------
ALTER TABLE `zc_forum_bbs`
  ADD COLUMN `bbs_collect`  int(11) NULL DEFAULT 0 COMMENT '收藏数' AFTER `bbs_listen`;
ALTER TABLE `zc_shieldorfans`
  ADD COLUMN `isDeleted`  tinyint(1) NULL DEFAULT 0 COMMENT '是否删除,1删除，0未删除' AFTER `object_id`;
ALTER TABLE `zc_product`
  ADD COLUMN `seq`  int(11) NULL DEFAULT 0 COMMENT '热门排序' AFTER `hammer_time`;


-- -----------------------------------------------------
--  新增表zc_notice、zc_sys_msg  snow.xu 20170706
-- -----------------------------------------------------
CREATE TABLE `zc_notice` (
  `id` varchar(36) NOT NULL COMMENT '主键',
  `content` longtext COMMENT '消息内容',
  `status` varchar(4) DEFAULT 'ST02' COMMENT '状态{ST}',
  `isDeleted` tinyint(1) DEFAULT '0' COMMENT '是否删除,1删除，0未删除',
  `addUserId` varchar(36) DEFAULT NULL COMMENT '创建人ID',
  `addtime` datetime DEFAULT NULL COMMENT '创建时间',
  `updateUserId` varchar(36) DEFAULT NULL COMMENT '更新人ID',
  `updatetime` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='公告消息表';

CREATE TABLE `zc_sys_msg` (
  `id` varchar(36) NOT NULL COMMENT '主键',
  `object_type` varchar(36) DEFAULT NULL COMMENT '对象类型',
  `object_id` varchar(36) DEFAULT NULL COMMENT '对象ID',
  `user_id` varchar(36) DEFAULT NULL COMMENT '用户id',
  `newtime` datetime DEFAULT NULL COMMENT '最新消息时间',
  `addtime` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统消息表';

CREATE TABLE `zc_sys_msg_log` (
  `id` varchar(36) NOT NULL COMMENT '主键',
  `sys_msg_id` varchar(36) DEFAULT NULL COMMENT '消息id',
  `mtype` varchar(4) DEFAULT NULL COMMENT '消息类型',
  `time_unit` varchar(20) DEFAULT NULL COMMENT '时间单位',
  `content` varchar(500) DEFAULT NULL COMMENT '消息内容',
  `is_read` tinyint(1) DEFAULT '0' COMMENT '是否已读（1:已读；0：未读）',
  `addtime` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统消息明细表';


CREATE TABLE `zc_intermediary` (
  `id` varchar(36) NOT NULL COMMENT '主键',
  `im_no` varchar(64) NOT NULL COMMENT '交易编号',
  `bbs_id` varchar(36) DEFAULT NULL COMMENT '帖子ID',
  `sell_user_id` varchar(36) DEFAULT NULL COMMENT '卖家id',
  `user_id` varchar(36) DEFAULT NULL COMMENT '买家id',
  `amount` bigint(20) DEFAULT NULL COMMENT '交易金额',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `status` varchar(4) DEFAULT NULL COMMENT '状态{IS}',
  `addtime` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='中介交易表';

CREATE TABLE `zc_intermediary_log` (
  `id` varchar(36) NOT NULL COMMENT '主键',
  `im_id` varchar(36) DEFAULT NULL COMMENT '中介交易ID',
  `user_id` varchar(36) DEFAULT NULL COMMENT '操作人',
  `log_type` varchar(4) DEFAULT NULL COMMENT '日志类型{IL}',
  `content` varchar(500) DEFAULT NULL COMMENT '日志内容',
  `addtime` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='中介交易日志表';


ALTER TABLE `zc_product`
	ADD COLUMN `reserve_price` double DEFAULT '0' COMMENT '保留价' AFTER `current_price`;
ALTER TABLE `zc_best_product`
  ADD COLUMN `shop_seq`  int NULL DEFAULT 0 COMMENT '店铺排序' AFTER `addtime`,
  ADD COLUMN `product_seq`  int NULL DEFAULT 0 COMMENT '拍品排序' AFTER `shop_seq`;
ALTER TABLE `zc_order`
  ADD COLUMN `total_price` double DEFAULT NULL COMMENT '订单金额' AFTER `order_no`,
  ADD COLUMN `is_intermediary`  tinyint NULL DEFAULT 0 COMMENT '是否中介交易' AFTER `return_express_no`;

update zc_order o set total_price = (select p.hammer_price from zc_product p where p.id = o.product_id)


-- -----------------------------------------------------
--   snow.xu 20170721
-- -----------------------------------------------------
update tuser set head_image='http://img10.zcys2016.com/mmopen/headimage/user-default.png' where head_image is null or head_image = ''

ALTER TABLE `zc_wallet_detail`
  ADD COLUMN `bank_name`  varchar(18) NULL COMMENT '开户行名称' AFTER `bank_id_no`;