CREATE TABLE `zc_category` (
  `id` varchar(36) NOT NULL COMMENT '主键',
  `name` varchar(36) DEFAULT NULL COMMENT '分类名称',
  `icon` varchar(100) DEFAULT NULL COMMENT '图标地址',
  `seq` int(11) DEFAULT NULL COMMENT '排序',
  `pid` varchar(36) DEFAULT NULL COMMENT '父ID',
  `forum_introduce` varchar(500) DEFAULT NULL COMMENT '版块介绍',
  `chief_moderator_id` varchar(36) DEFAULT NULL COMMENT '首席版主ID',
  `addUserId` varchar(36) DEFAULT NULL COMMENT '创建人ID',
  `addtime` datetime DEFAULT NULL COMMENT '创建时间',
  `updateUserId` varchar(36) DEFAULT NULL COMMENT '更新人ID',
  `updatetime` datetime DEFAULT NULL COMMENT '更新时间',
  `isDeleted` tinyint(1) DEFAULT NULL COMMENT '是否删除,1删除，0未删除',
	PRIMARY KEY (`id`)
)
COMMENT='微拍分类表'
COLLATE='utf8_general_ci'
ENGINE=InnoDB;

CREATE TABLE `zc_forum_bbs` (
  `id` varchar(36) NOT NULL COMMENT '主键',
  `category_id` varchar(36) DEFAULT NULL COMMENT '分类ID',
  `bbs_title` varchar(100) DEFAULT NULL COMMENT '帖子标题',
  `bbs_content` varchar(500) DEFAULT NULL COMMENT '帖子内容',
  `bbs_type` varchar(4) DEFAULT NULL COMMENT '帖子类别{BT}',
  `bbs_status` varchar(4) DEFAULT NULL COMMENT '帖子状态（打开、关闭）{BS}',
  `isDeleted` tinyint(1) DEFAULT NULL COMMENT '是否删除,1删除，0未删除',
  `isOffReply` tinyint(1) DEFAULT NULL COMMENT '是否关闭回复',
  `isTop` tinyint(1) DEFAULT NULL COMMENT '是否置顶',
  `isLight` tinyint(1) DEFAULT NULL COMMENT '是否加亮',
  `isEssence` tinyint(1) DEFAULT NULL COMMENT '是否加精',
  `addUserId` varchar(36) DEFAULT NULL COMMENT '发帖人ID',
  `addtime` datetime DEFAULT NULL COMMENT '发帖时间',
  `updateUserId` varchar(36) DEFAULT NULL COMMENT '更新人ID',
  `updatetime` datetime DEFAULT NULL COMMENT '更新时间',
  `bbs_comment` int(11) DEFAULT NULL COMMENT '评论数',
  `bbs_read` int(11) DEFAULT NULL COMMENT '围观数',
  `bbs_reward` int(11) DEFAULT NULL COMMENT '打赏数',
  `bbs_share` int(11) DEFAULT NULL COMMENT '转发数',
  `bbs_listen` int(11) DEFAULT NULL COMMENT '收听数',
	PRIMARY KEY (`id`)
)
COMMENT='论坛帖子表'
COLLATE='utf8_general_ci'
ENGINE=InnoDB;

CREATE TABLE `zc_position_apply` (
  `id` varchar(36) NOT NULL COMMENT '主键',
  `category_id` varchar(36) DEFAULT NULL COMMENT '分类ID',
  `role_id` varchar(36) DEFAULT NULL COMMENT '职位角色ID',
  `apply_user_id` varchar(36) DEFAULT NULL COMMENT '申请人ID',
  `apply_content` varchar(500) DEFAULT NULL COMMENT '申请内容',
  `recommend` varchar(36) DEFAULT NULL COMMENT '推荐人昵称',
  `company_name` varchar(100) DEFAULT NULL COMMENT '就职单位',
  `specialty` varchar(500) DEFAULT NULL COMMENT '个人专长、特长',
  `advice` varchar(500) DEFAULT NULL COMMENT '版面发展、建议',
  `activity_forum` varchar(100) DEFAULT NULL COMMENT '经常参与的版块',
  `online_duration` int(11) DEFAULT NULL COMMENT '在线时长(小时)',
  `audit_status` varchar(4) DEFAULT NULL COMMENT '审核状态{AS}',
  `audit_time` datetime DEFAULT NULL COMMENT '审核时间',
  `audit_user_id` varchar(36) DEFAULT NULL COMMENT '审核人',
  `addtime` datetime DEFAULT NULL COMMENT '申请时间',
  `pay_status` varchar(4) DEFAULT NULL COMMENT '支付状态',
	PRIMARY KEY (`id`)
)
COMMENT='职位申请表'
COLLATE='utf8_general_ci'
ENGINE=InnoDB;

CREATE TABLE `zc_report` (
  `id` varchar(36) NOT NULL COMMENT '主键',
  `object_type` varchar(4) DEFAULT NULL COMMENT '举报对象类型',
  `object_id` varchar(36) DEFAULT NULL COMMENT '举报对象ID',
  `report_reason` varchar(300) DEFAULT NULL COMMENT '举报理由',
  `user_id` varchar(36) DEFAULT NULL COMMENT '举报人',
  `addtime` datetime DEFAULT NULL COMMENT '举报时间',
	PRIMARY KEY (`id`)
)
COMMENT='举报记录表'
COLLATE='utf8_general_ci'
ENGINE=InnoDB;

CREATE TABLE `zc_bbs_reward` (
  `id` varchar(36) NOT NULL COMMENT '主键',
  `bbs_id` varchar(36) DEFAULT NULL COMMENT '帖子ID',
  `reward_fee` float DEFAULT NULL COMMENT '打赏金额',
  `user_id` varchar(36) DEFAULT NULL COMMENT '打赏人',
  `addtime` datetime DEFAULT NULL COMMENT '打赏时间',
  `pay_status` varchar(4) DEFAULT NULL COMMENT '支付状态',
	PRIMARY KEY (`id`)
)
COMMENT='打赏记录表'
COLLATE='utf8_general_ci'
ENGINE=InnoDB;

CREATE TABLE `zc_read_record` (
  `id` varchar(36) NOT NULL COMMENT '主键',
  `object_type` varchar(4) DEFAULT NULL COMMENT '访问对象类型',
  `object_id` varchar(36) DEFAULT NULL COMMENT '访问对象ID',
  `user_id` varchar(36) DEFAULT NULL COMMENT '用户id',
  `addtime` datetime DEFAULT NULL COMMENT '访问时间',
	PRIMARY KEY (`id`)
)
COMMENT='访问/围观记录表'
COLLATE='utf8_general_ci'
ENGINE=InnoDB;

CREATE TABLE `zc_share_record` (
  `id` varchar(36) NOT NULL COMMENT '主键',
  `bbs_id` varchar(36) DEFAULT NULL COMMENT '帖子ID',
  `share_channel` varchar(4) DEFAULT NULL COMMENT '分享渠道',
  `user_id` varchar(36) DEFAULT NULL COMMENT '用户id',
  `addtime` datetime DEFAULT NULL COMMENT '转发时间',
	PRIMARY KEY (`id`)
)
COMMENT='转发/分享记录表'
COLLATE='utf8_general_ci'
ENGINE=InnoDB;

CREATE TABLE `zc_bbs_comment` (
  `id` varchar(36) NOT NULL COMMENT '主键',
  `bbs_id` varchar(36) DEFAULT NULL COMMENT '帖子ID',
  `comment` varchar(500) DEFAULT NULL COMMENT '评论内容',
  `pid` varchar(36) DEFAULT NULL COMMENT '父评论ID',
  `isDeleted` tinyint(1) DEFAULT NULL COMMENT '是否删除,1删除，0未删除',
  `user_id` varchar(36) DEFAULT NULL COMMENT '评论人',
  `addtime` datetime DEFAULT NULL COMMENT '评论时间',
	PRIMARY KEY (`id`)
)
COMMENT='帖子评论表'
COLLATE='utf8_general_ci'
ENGINE=InnoDB;

CREATE TABLE `zc_file` (
  `id` varchar(36) NOT NULL COMMENT '主键',
  `object_type` varchar(4) DEFAULT NULL COMMENT '对象类型',
  `object_id` varchar(36) DEFAULT NULL COMMENT '对象ID',
  `file_type` varchar(4) DEFAULT NULL COMMENT '文件类型{FT}',
  `file_original_url` varchar(100) DEFAULT NULL COMMENT '原文件URL',
  `file_handle_url` varchar(100) DEFAULT NULL COMMENT '处理后文件Url',
  `file_original_size` int(11) DEFAULT NULL COMMENT '原文件大小',
  `file_handle_size` int(11) DEFAULT NULL COMMENT '处理后文件文件大小',
  `duration` int(11) DEFAULT NULL COMMENT '时长（S）',
  `addtime` datetime DEFAULT NULL COMMENT '创建时间',
	PRIMARY KEY (`id`)
)
COMMENT='文件管理表'
COLLATE='utf8_general_ci'
ENGINE=InnoDB;

CREATE TABLE `zc_product` (
  `id` varchar(36) NOT NULL COMMENT '主键',
  `p_no` varchar(255) DEFAULT NULL COMMENT '拍品编号',
  `category_id` varchar(36) DEFAULT NULL COMMENT '分类id',
  `deadline` datetime DEFAULT NULL COMMENT '截止时间',
  `real_deadline` datetime DEFAULT NULL COMMENT '实际截止时间',
  `starting_price` float DEFAULT NULL COMMENT '起拍价',
  `approval_days` varchar(4) DEFAULT NULL COMMENT '几天包退,3:3天包退；7:7天包退；NULL：不包退',
  `is_free_shipping` tinyint(1) DEFAULT NULL COMMENT '是否包邮,1：包邮 0：不包邮',
  `margin` float DEFAULT NULL COMMENT '保证金',
  `is_need_real_id` tinyint(1) DEFAULT NULL COMMENT '是否需要实名,1：需要 0：不需要',
  `is_need_protection_price` tinyint(1) DEFAULT NULL COMMENT '是否需要保证金,1：需要 0：不需要',
  `starting_time` datetime DEFAULT NULL COMMENT '开拍时间',
  `fixed_price` float DEFAULT NULL COMMENT '一口价',
  `reference_price` float DEFAULT NULL COMMENT '参考价',
  `content` text DEFAULT NULL COMMENT '内容',
  `status` varchar(4) DEFAULT NULL COMMENT '状态，0草稿10未开始20竞拍中30已成交40已流拍50已失败',
  `on_off` varchar(4) DEFAULT NULL COMMENT '上下架，1上架0下架',
  `is_close` tinyint(1) DEFAULT NULL COMMENT '封存，1封存0未封存',
  `read_count` int(11) DEFAULT NULL COMMENT '围观数量',
  `like_count` int(11) DEFAULT NULL COMMENT '点赞数量',
  `share_count` int(11) DEFAULT NULL COMMENT '分享数量',
  `current_price` float DEFAULT NULL COMMENT '当前价',
  `user_id` varchar(36) DEFAULT NULL COMMENT '成交人ID',
  `hammer_price` float(11) DEFAULT NULL COMMENT '成交金额',
  `hammer_time` datetime DEFAULT NULL COMMENT '成交时间',
  `isDeleted` tinyint(1) DEFAULT NULL COMMENT '是否删除,1删除，0未删除',
  `addUserId` varchar(36) DEFAULT NULL COMMENT '创建人ID',
  `addtime` datetime DEFAULT NULL COMMENT '创建时间',
  `updateUserId` varchar(36) DEFAULT NULL COMMENT '更新人ID',
  `updatetime` datetime DEFAULT NULL COMMENT '更新时间',
	PRIMARY KEY (`id`)
)
COMMENT='拍品表'
COLLATE='utf8_general_ci'
ENGINE=InnoDB;

CREATE TABLE `zc_product_range` (
  `id` varchar(36) NOT NULL COMMENT '主键',
  `product_id` varchar(36) DEFAULT NULL COMMENT '拍品id',
  `product_add_time` datetime DEFAULT NULL COMMENT '拍品创建时间',
  `step` int(11) DEFAULT NULL COMMENT '第几阶段，从1开始，n阶段结束+1=n+1阶段起始',
  `start_price` float DEFAULT NULL COMMENT '起始价格',
  `end_price` float DEFAULT NULL COMMENT '结束价格',
  `price` float DEFAULT NULL COMMENT '加价幅度',
  `addUserId` varchar(36) DEFAULT NULL COMMENT '创建人ID',
  `addtime` datetime DEFAULT NULL COMMENT '创建时间',
  `updateUserId` varchar(36) DEFAULT NULL COMMENT '更新人ID',
  `updatetime` datetime DEFAULT NULL COMMENT '更新时间',
	PRIMARY KEY (`id`)
)
COMMENT='拍品加价幅度表'
COLLATE='utf8_general_ci'
ENGINE=InnoDB;

CREATE TABLE `zc_user_auto_price` (
  `id` varchar(36) NOT NULL COMMENT '主键',
  `user_id` varchar(36) DEFAULT NULL COMMENT '自动出价用户id',
  `product_id` varchar(36) DEFAULT NULL COMMENT '拍品id',
  `max_price` float DEFAULT NULL COMMENT '最高价，当前价+加价幅度>最高价，那么自动出价停止',
  `addUserId` varchar(36) DEFAULT NULL COMMENT '创建人ID',
  `addtime` datetime DEFAULT NULL COMMENT '创建时间',
  `updateUserId` varchar(36) DEFAULT NULL COMMENT '更新人ID',
  `updatetime` datetime DEFAULT NULL COMMENT '更新时间',
	PRIMARY KEY (`id`)
)
COMMENT='用户自动出价表'
COLLATE='utf8_general_ci'
ENGINE=InnoDB;

CREATE TABLE `zc_best_product` (
  `id` varchar(36) NOT NULL COMMENT '主键',
  `channel` varchar(4) DEFAULT NULL COMMENT '精拍频道 0首页1分类',
  `product_id` varchar(36) DEFAULT NULL COMMENT '拍品id',
  `status` varchar(4) DEFAULT NULL COMMENT '状态 0申请中1审核通过2审核不通过',
  `start_time` datetime DEFAULT NULL COMMENT '起始时间',
  `end_time` datetime DEFAULT NULL COMMENT '结束时间',
  `pay_status` varchar(4) DEFAULT NULL COMMENT '支付状态',
  `addUserId` varchar(36) DEFAULT NULL COMMENT '创建人ID',
  `addtime` datetime DEFAULT NULL COMMENT '创建时间',
  `updateUserId` varchar(36) DEFAULT NULL COMMENT '更新人ID',
  `updatetime` datetime DEFAULT NULL COMMENT '更新时间',
	PRIMARY KEY (`id`)
)
COMMENT='精拍表'
COLLATE='utf8_general_ci'
ENGINE=InnoDB;

CREATE TABLE `zc_auction` (
  `id` varchar(36) NOT NULL COMMENT '主键',
  `product_id` varchar(36) DEFAULT NULL COMMENT '拍品id',
  `buyer_id` varchar(36) DEFAULT NULL COMMENT '购买者id',
  `bid` float DEFAULT NULL COMMENT '出价金额',
  `status` varchar(4) DEFAULT NULL COMMENT '成交状态 0未成交1已成交',
  `is_auto` tinyint(1) DEFAULT NULL COMMENT '是否自动出价 1是0否',
  `addUserId` varchar(36) DEFAULT NULL COMMENT '创建人ID',
  `addtime` datetime DEFAULT NULL COMMENT '创建时间',
  `updateUserId` varchar(36) DEFAULT NULL COMMENT '更新人ID',
  `updatetime` datetime DEFAULT NULL COMMENT '更新时间',
	PRIMARY KEY (`id`)
)
COMMENT='拍卖表'
COLLATE='utf8_general_ci'
ENGINE=InnoDB;

CREATE TABLE `zc_product_like` (
  `id` varchar(36) NOT NULL COMMENT '主键',
  `product_id` varchar(36) DEFAULT NULL COMMENT '拍品id',
  `like_user_id` varchar(36) DEFAULT NULL COMMENT '点赞人id',
  `like_time` datetime DEFAULT NULL COMMENT '点赞时间',
  `addUserId` varchar(36) DEFAULT NULL COMMENT '创建人ID',
  `addtime` datetime DEFAULT NULL COMMENT '创建时间',
  `updateUserId` varchar(36) DEFAULT NULL COMMENT '更新人ID',
  `updatetime` datetime DEFAULT NULL COMMENT '更新时间',
	PRIMARY KEY (`id`)
)
COMMENT='拍品点赞表'
COLLATE='utf8_general_ci'
ENGINE=InnoDB;

CREATE TABLE `zc_banner` (
  `id` varchar(36) NOT NULL COMMENT '主键',
  `title` varchar(255) DEFAULT NULL COMMENT '标题',
  `url` varchar(255) DEFAULT NULL COMMENT '图片URL',
  `detail_url` varchar(255) DEFAULT NULL COMMENT '详情URL，详情页地址，可不填',
  `sort_number` int(11) DEFAULT NULL COMMENT '排序，数字越大越靠前',
  `status` varchar(4) DEFAULT NULL COMMENT '状态1草稿2发布3取消发布',
  `addUserId` varchar(36) DEFAULT NULL COMMENT '创建人ID',
  `addtime` datetime DEFAULT NULL COMMENT '创建时间',
  `updateUserId` varchar(36) DEFAULT NULL COMMENT '更新人ID',
  `updatetime` datetime DEFAULT NULL COMMENT '更新时间',
	PRIMARY KEY (`id`)
)
COMMENT='轮播表'
COLLATE='utf8_general_ci'
ENGINE=InnoDB;

CREATE TABLE `zc_order` (
  `id` varchar(36) NOT NULL COMMENT '主键',
  `order_number` varchar(255) DEFAULT NULL COMMENT '订单号',
  `product_id` varchar(36) DEFAULT NULL COMMENT '拍品id',
  `sold_user_id` varchar(36) DEFAULT NULL COMMENT '卖家id',
  `buy_user_id` varchar(36) DEFAULT NULL COMMENT '买家id',
  `hammer_price` float DEFAULT NULL COMMENT '成交金额',
  `hammer_time` datetime DEFAULT NULL COMMENT '成交时间',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态，0待付款2已支付4待发货6已发货8待收货10已收货12退款中14已退款16交易成功22已关闭',
  `xiaoer_status` tinyint(1) DEFAULT NULL COMMENT '小二介入状态,0未介入1介入中2判定退回3判定交易成功4撤回小二介入',
  `delay_time` datetime DEFAULT NULL COMMENT '延期到期时间',
  `delay_times` int(1) DEFAULT NULL COMMENT '延期次数,最多两次',
  `deliver_time` datetime DEFAULT NULL COMMENT '发货时间',
  `receive_time` datetime DEFAULT NULL COMMENT '收货时间',
  `return_apply_time` datetime DEFAULT NULL COMMENT '退款申请时间',
  `return_time` datetime DEFAULT NULL COMMENT '退款时间',
  `compensation` float DEFAULT NULL COMMENT '补偿金额',
  `addUserId` varchar(36) DEFAULT NULL COMMENT '创建人ID',
  `addtime` datetime DEFAULT NULL COMMENT '创建时间',
  `updateUserId` varchar(36) DEFAULT NULL COMMENT '更新人ID',
  `updatetime` datetime DEFAULT NULL COMMENT '更新时间',
  `pay_status` varchar(4) DEFAULT NULL COMMENT '支付状态',
	PRIMARY KEY (`id`)
)
COMMENT='订单表'
COLLATE='utf8_general_ci'
ENGINE=InnoDB;

CREATE TABLE `zc_comment` (
  `id` varchar(36) NOT NULL COMMENT '主键',
  `order_id` varchar(36) DEFAULT NULL COMMENT '订单id',
  `product_id` varchar(36) DEFAULT NULL COMMENT '拍品id',
  `grade` float DEFAULT NULL COMMENT '评分',
  `content` text DEFAULT NULL COMMENT '评价内容',
  `addUserId` varchar(36) DEFAULT NULL COMMENT '创建人ID',
  `addtime` datetime DEFAULT NULL COMMENT '创建时间',
	PRIMARY KEY (`id`)
)
COMMENT='评价表'
COLLATE='utf8_general_ci'
ENGINE=InnoDB;

CREATE TABLE `zc_order_break` (
  `id` varchar(36) NOT NULL COMMENT '主键',
  `order_id` varchar(36) DEFAULT NULL COMMENT '订单id',
  `product_id` varchar(36) DEFAULT NULL COMMENT '拍品id',
  `sold_user_id` varchar(36) DEFAULT NULL COMMENT '卖家id',
  `buy_user_id` varchar(36) DEFAULT NULL COMMENT '买家id',
  `status` varchar(4) DEFAULT NULL COMMENT '违约类型10未支付违约20未发货违约',
  `addUserId` varchar(36) DEFAULT NULL COMMENT '创建人ID',
  `addtime` datetime DEFAULT NULL COMMENT '创建时间',
  `updateUserId` varchar(36) DEFAULT NULL COMMENT '更新人ID',
  `updatetime` datetime DEFAULT NULL COMMENT '更新时间',
	PRIMARY KEY (`id`)
)
COMMENT='违约流水表'
COLLATE='utf8_general_ci'
ENGINE=InnoDB;

CREATE TABLE `zc_product_margin` (
  `id` varchar(36) NOT NULL COMMENT '主键',
  `product_id` varchar(36) DEFAULT NULL COMMENT '拍品id',
  `buy_user_id` varchar(36) DEFAULT NULL COMMENT '买家id',
  `margin` float DEFAULT NULL COMMENT '保证金',
  `return_time` datetime DEFAULT NULL COMMENT '退回时间',
  `pay_status` varchar(4) DEFAULT NULL COMMENT '支付状态',
  `addUserId` varchar(36) DEFAULT NULL COMMENT '创建人ID',
  `addtime` datetime DEFAULT NULL COMMENT '创建时间',
  `updateUserId` varchar(36) DEFAULT NULL COMMENT '更新人ID',
  `updatetime` datetime DEFAULT NULL COMMENT '更新时间',
	PRIMARY KEY (`id`)
)
COMMENT='拍品保证金表'
COLLATE='utf8_general_ci'
ENGINE=InnoDB;

CREATE TABLE `zc_order_xiaoer` (
  `id` varchar(36) NOT NULL COMMENT '主键',
  `order_id` varchar(36) DEFAULT NULL COMMENT '订单id',
  `product_id` varchar(36) DEFAULT NULL COMMENT '拍品id',
  `sold_user_id` varchar(36) DEFAULT NULL COMMENT '卖家id',
  `buy_user_id` varchar(36) DEFAULT NULL COMMENT '买家id',
  `reason` varchar(4) DEFAULT NULL COMMENT '原因，页面写死几种原因',
  `content` text DEFAULT NULL COMMENT '内容',
  `urls` text DEFAULT NULL COMMENT '多图片URL',
  `status` tinyint(2) DEFAULT NULL COMMENT '小二介入状态 0未介入1介入中2判定退回3判定交易成功4撤回小二介入',
  `addUserId` varchar(36) DEFAULT NULL COMMENT '创建人ID',
  `addtime` datetime DEFAULT NULL COMMENT '创建时间',
  `updateUserId` varchar(36) DEFAULT NULL COMMENT '更新人ID',
  `updatetime` datetime DEFAULT NULL COMMENT '更新时间',
	PRIMARY KEY (`id`)
)
COMMENT='小二介入流水表'
COLLATE='utf8_general_ci'
ENGINE=InnoDB;

CREATE TABLE `zc_shop` (
  `id` varchar(36) NOT NULL COMMENT '主键',
  `user_id` varchar(36) DEFAULT NULL COMMENT '所有者Id',
  `name` varchar(255) DEFAULT NULL COMMENT '店铺名称',
  `logo_url` varchar(255) DEFAULT NULL COMMENT '店铺LOGO',
  `notice` varchar(255) DEFAULT NULL COMMENT '店铺公告',
  `introduction` varchar(255) DEFAULT NULL COMMENT '店铺介绍',
  `protection_price` float DEFAULT NULL COMMENT '消保金',
  `is_need_phone` tinyint(1) DEFAULT NULL COMMENT '首次出价需要手机号，1需要0不需要',
  `is_need_reel_id` tinyint(1) DEFAULT NULL COMMENT '首次出价需实名认证，1需要0不需要',
  `shop_url` varchar(255) DEFAULT NULL COMMENT '店铺链接',
  `shop_qrcode_url` varchar(255) DEFAULT NULL COMMENT '店铺二维码',
  `grade` float DEFAULT NULL COMMENT '店铺评分',
  `addUserId` varchar(36) DEFAULT NULL COMMENT '创建人ID',
  `addtime` datetime DEFAULT NULL COMMENT '创建时间',
  `updateUserId` varchar(36) DEFAULT NULL COMMENT '更新人ID',
  `updatetime` datetime DEFAULT NULL COMMENT '更新时间',
	PRIMARY KEY (`id`)
)
COMMENT='店铺表'
COLLATE='utf8_general_ci'
ENGINE=InnoDB;

CREATE TABLE `zc_protection` (
  `id` varchar(36) NOT NULL COMMENT '主键',
  `user_id` varchar(36) DEFAULT NULL COMMENT '所有者Id',
  `protection_type` varchar(4) DEFAULT NULL COMMENT '类型 0充值10补齐20扣除30提现40转账到钱包60钱包转账到消保金',
  `price` float DEFAULT NULL COMMENT '金额',
  `reason` varchar(255) DEFAULT NULL COMMENT '理由：首次充值因消保金未足额，进行补充值因某某事由，扣除消保金消保金提现',
  `pay_status` varchar(4) DEFAULT NULL COMMENT '支付状态',
  `addUserId` varchar(36) DEFAULT NULL COMMENT '创建人ID',
  `addtime` datetime DEFAULT NULL COMMENT '创建时间',
  `updateUserId` varchar(36) DEFAULT NULL COMMENT '更新人ID',
  `updatetime` datetime DEFAULT NULL COMMENT '更新时间',
	PRIMARY KEY (`id`)
)
COMMENT='消保金流水表'
COLLATE='utf8_general_ci'
ENGINE=InnoDB;

CREATE TABLE `zc_authentication` (
  `id` varchar(36) NOT NULL COMMENT '主键',
  `auth_type` varchar(4) DEFAULT NULL COMMENT '类型，0个人1企业',
  `user_name` varchar(255) DEFAULT NULL COMMENT '姓名',
  `id_type` varchar(4) DEFAULT NULL COMMENT '证件类型 1身份证2驾驶证',
  `id_no` varchar(255) DEFAULT NULL COMMENT '证件号码',
  `phone` varchar(255) DEFAULT NULL COMMENT '手机号',
  `id_front_byhand` varchar(255) DEFAULT NULL COMMENT '手持证件正面',
  `id_front` varchar(255) DEFAULT NULL COMMENT '证件正面',
  `id_back` varchar(255) DEFAULT NULL COMMENT '证件反面',
  `company_name` varchar(255) DEFAULT NULL COMMENT '企业名称',
  `credit_id` varchar(255) DEFAULT NULL COMMENT '统一社会信用码',
  `bussiness_license` varchar(255) DEFAULT NULL COMMENT '营业执照',
  `legal_person_name` varchar(255) DEFAULT NULL COMMENT '法人姓名',
  `legal_person_id` varchar(255) DEFAULT NULL COMMENT '法人身份证号',
  `legal_person_id_front` varchar(255) DEFAULT NULL COMMENT '法人身份证正面',
  `legal_person_id_back` varchar(255) DEFAULT NULL COMMENT '法人身份证反面',
  `pay_status` varchar(4) DEFAULT NULL COMMENT '支付状态',
  `addUserId` varchar(36) DEFAULT NULL COMMENT '创建人ID',
  `addtime` datetime DEFAULT NULL COMMENT '创建时间',
  `updateUserId` varchar(36) DEFAULT NULL COMMENT '更新人ID',
  `updatetime` datetime DEFAULT NULL COMMENT '更新时间',
	PRIMARY KEY (`id`)
)
COMMENT='实名认证表'
COLLATE='utf8_general_ci'
ENGINE=InnoDB;

CREATE TABLE `zc_shieldorfans` (
  `id` varchar(36) NOT NULL COMMENT '主键',
  `object_type` varchar(4) DEFAULT NULL COMMENT '类型 1粉丝2屏蔽',
  `object_by_id` varchar(36) DEFAULT NULL COMMENT '被关注Id/被屏蔽人ID',
  `object_id` varchar(36) DEFAULT NULL COMMENT '关注人Id/屏蔽人ID',
  `addtime` datetime DEFAULT NULL COMMENT '创建时间',
	PRIMARY KEY (`id`)
)
COMMENT='屏蔽和粉丝表'
COLLATE='utf8_general_ci'
ENGINE=InnoDB;

CREATE TABLE `zc_address` (
  `id` varchar(36) NOT NULL COMMENT '主键',
  `user_id` varchar(36) DEFAULT NULL COMMENT '所有者Id',
  `atype` int(1) DEFAULT NULL COMMENT '类型1-收货地址 2-退货地址',
  `userName` varchar(36) DEFAULT NULL COMMENT '收货人姓名',
  `telNumber` varchar(36) DEFAULT NULL COMMENT '收货人手机号码',
  `provinceName` varchar(128) DEFAULT NULL COMMENT '国标收货地址第一级地址（省）',
  `cityName` varchar(128) DEFAULT NULL COMMENT '国标收货地址第二级地址（市）',
  `countyName` varchar(128) DEFAULT NULL COMMENT '国标收货地址第三级地址（县/区）',
  `detailInfo` varchar(256) DEFAULT NULL COMMENT '详细收货地址信息',
  `postalCode` varchar(36) DEFAULT NULL COMMENT '邮政编码',
  `is_default` tinyint(1) DEFAULT NULL COMMENT '是否默认，1默认0不是默认',
  `addtime` datetime DEFAULT NULL COMMENT '创建时间',
	PRIMARY KEY (`id`)
)
COMMENT='收货地址表'
COLLATE='utf8_general_ci'
ENGINE=InnoDB;

CREATE TABLE `zc_chat_msg` (
  `id` varchar(36) NOT NULL COMMENT '主键',
  `mtype` varchar(18) DEFAULT NULL COMMENT '消息类型：text、image、audio',
  `content` longtext DEFAULT NULL COMMENT '消息内容',
  `from_user_id` varchar(36) DEFAULT NULL COMMENT '发送人',
  `to_user_id` varchar(36) DEFAULT NULL COMMENT '接收人',
  `unread` tinyint(1) DEFAULT NULL COMMENT '是否未读1：是 0：否',
  `addtime` datetime DEFAULT NULL COMMENT '创建时间',
	PRIMARY KEY (`id`)
)
COMMENT='私信聊天记录表'
COLLATE='utf8_general_ci'
ENGINE=InnoDB;

CREATE TABLE `zc_wallet` (
  `id` varchar(36) NOT NULL COMMENT '主键',
  `user_id` varchar(36) DEFAULT NULL COMMENT '用户ID',
  `amount` double DEFAULT NULL COMMENT '可用余额',
  `frozen_amount` double DEFAULT NULL COMMENT '冻结款项',
  `pay_password` varchar(100) DEFAULT NULL COMMENT '支付密码',
  `real_name` varchar(36) DEFAULT NULL COMMENT '姓名',
  `id_no` varchar(36) DEFAULT NULL COMMENT '证件号码',
  `addtime` datetime DEFAULT NULL COMMENT '创建时间',
  `updatetime` datetime DEFAULT NULL COMMENT '更新时间',
	PRIMARY KEY (`id`)
)
COMMENT='钱包表'
COLLATE='utf8_general_ci'
ENGINE=InnoDB;

CREATE TABLE `zc_wallet_detail` (
  `id` varchar(36) NOT NULL COMMENT '主键',
  `user_id` varchar(36) DEFAULT NULL COMMENT '用户ID',
  `order_no` varchar(64) DEFAULT NULL COMMENT '交易号',
  `amount` double DEFAULT NULL COMMENT '交易金额',
  `wtype` varchar(4) DEFAULT NULL COMMENT '交易类型{WT}',
  `description` varchar(255) DEFAULT NULL COMMENT '交易描述',
  `channel` varchar(4) DEFAULT NULL COMMENT '支付渠道{CS}',
  `bank_info` varchar(255) DEFAULT NULL COMMENT '银行信息（银行卡支付）',
  `addtime` datetime DEFAULT NULL COMMENT '交易时间',
	PRIMARY KEY (`id`)
)
COMMENT='钱包收支明细表'
COLLATE='utf8_general_ci'
ENGINE=InnoDB;


