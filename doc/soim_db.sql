/*
 Navicat Premium Data Transfer

 Source Server         : mysql
 Source Server Type    : MySQL
 Source Server Version : 80022
 Source Host           : 127.0.0.1:3306
 Source Schema         : soim_db

 Target Server Type    : MySQL
 Target Server Version : 80022
 File Encoding         : 65001

 Date: 12/06/2022 15:11:15
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for im_friend_0
-- ----------------------------
DROP TABLE IF EXISTS `im_friend_0`;
CREATE TABLE `im_friend_0`  (
  `id` bigint(0) NOT NULL,
  `relation_id` bigint(0) NOT NULL COMMENT '好友关系id(消息存储路由分片)',
  `uid` bigint(0) NOT NULL COMMENT '用户id',
  `fid` bigint(0) NOT NULL COMMENT '好友id',
  `status` tinyint(0) NOT NULL COMMENT '状态:0删除,1正常,2临时好友(群单聊)',
  `unread_num` int(0) NOT NULL COMMENT '群未读消息数量',
  `unread_offset_id` bigint(0) NOT NULL COMMENT '群未读消息id偏移量(大于等于该id的都未读)',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '好友关系表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for im_friend_1
-- ----------------------------
DROP TABLE IF EXISTS `im_friend_1`;
CREATE TABLE `im_friend_1`  (
  `id` bigint(0) NOT NULL,
  `relation_id` bigint(0) NOT NULL COMMENT '好友关系id(消息存储路由分片)',
  `uid` bigint(0) NOT NULL COMMENT '用户id',
  `fid` bigint(0) NOT NULL COMMENT '好友id',
  `status` tinyint(0) NOT NULL COMMENT '状态:0删除,1正常,2临时好友(群单聊)',
  `unread_num` int(0) NOT NULL COMMENT '群未读消息数量',
  `unread_offset_id` bigint(0) NOT NULL COMMENT '群未读消息id偏移量(大于等于该id的都未读)',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '好友关系表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for im_friend_2
-- ----------------------------
DROP TABLE IF EXISTS `im_friend_2`;
CREATE TABLE `im_friend_2`  (
  `id` bigint(0) NOT NULL,
  `relation_id` bigint(0) NOT NULL COMMENT '好友关系id(消息存储路由分片)',
  `uid` bigint(0) NOT NULL COMMENT '用户id',
  `fid` bigint(0) NOT NULL COMMENT '好友id',
  `status` tinyint(0) NOT NULL COMMENT '状态:0删除,1正常,2临时好友(群单聊)',
  `unread_num` int(0) NOT NULL COMMENT '群未读消息数量',
  `unread_offset_id` bigint(0) NOT NULL COMMENT '群未读消息id偏移量(大于等于该id的都未读)',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '好友关系表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for im_friend_3
-- ----------------------------
DROP TABLE IF EXISTS `im_friend_3`;
CREATE TABLE `im_friend_3`  (
  `id` bigint(0) NOT NULL,
  `relation_id` bigint(0) NOT NULL COMMENT '好友关系id(消息存储路由分片)',
  `uid` bigint(0) NOT NULL COMMENT '用户id',
  `fid` bigint(0) NOT NULL COMMENT '好友id',
  `status` tinyint(0) NOT NULL COMMENT '状态:0删除,1正常,2临时好友(群单聊)',
  `unread_num` int(0) NOT NULL COMMENT '群未读消息数量',
  `unread_offset_id` bigint(0) NOT NULL COMMENT '群未读消息id偏移量(大于等于该id的都未读)',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '好友关系表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for im_friend_4
-- ----------------------------
DROP TABLE IF EXISTS `im_friend_4`;
CREATE TABLE `im_friend_4`  (
  `id` bigint(0) NOT NULL,
  `relation_id` bigint(0) NOT NULL COMMENT '好友关系id(消息存储路由分片)',
  `uid` bigint(0) NOT NULL COMMENT '用户id',
  `fid` bigint(0) NOT NULL COMMENT '好友id',
  `status` tinyint(0) NOT NULL COMMENT '状态:0删除,1正常,2临时好友(群单聊)',
  `unread_num` int(0) NOT NULL COMMENT '群未读消息数量',
  `unread_offset_id` bigint(0) NOT NULL COMMENT '群未读消息id偏移量(大于等于该id的都未读)',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '好友关系表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for im_friend_5
-- ----------------------------
DROP TABLE IF EXISTS `im_friend_5`;
CREATE TABLE `im_friend_5`  (
  `id` bigint(0) NOT NULL,
  `relation_id` bigint(0) NOT NULL COMMENT '好友关系id(消息存储路由分片)',
  `uid` bigint(0) NOT NULL COMMENT '用户id',
  `fid` bigint(0) NOT NULL COMMENT '好友id',
  `status` tinyint(0) NOT NULL COMMENT '状态:0删除,1正常,2临时好友(群单聊)',
  `unread_num` int(0) NOT NULL COMMENT '群未读消息数量',
  `unread_offset_id` bigint(0) NOT NULL COMMENT '群未读消息id偏移量(大于等于该id的都未读)',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '好友关系表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for im_friend_6
-- ----------------------------
DROP TABLE IF EXISTS `im_friend_6`;
CREATE TABLE `im_friend_6`  (
  `id` bigint(0) NOT NULL,
  `relation_id` bigint(0) NOT NULL COMMENT '好友关系id(消息存储路由分片)',
  `uid` bigint(0) NOT NULL COMMENT '用户id',
  `fid` bigint(0) NOT NULL COMMENT '好友id',
  `status` tinyint(0) NOT NULL COMMENT '状态:0删除,1正常,2临时好友(群单聊)',
  `unread_num` int(0) NOT NULL COMMENT '群未读消息数量',
  `unread_offset_id` bigint(0) NOT NULL COMMENT '群未读消息id偏移量(大于等于该id的都未读)',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '好友关系表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for im_friend_7
-- ----------------------------
DROP TABLE IF EXISTS `im_friend_7`;
CREATE TABLE `im_friend_7`  (
  `id` bigint(0) NOT NULL,
  `relation_id` bigint(0) NOT NULL COMMENT '好友关系id(消息存储路由分片)',
  `uid` bigint(0) NOT NULL COMMENT '用户id',
  `fid` bigint(0) NOT NULL COMMENT '好友id',
  `status` tinyint(0) NOT NULL COMMENT '状态:0删除,1正常,2临时好友(群单聊)',
  `unread_num` int(0) NOT NULL COMMENT '群未读消息数量',
  `unread_offset_id` bigint(0) NOT NULL COMMENT '群未读消息id偏移量(大于等于该id的都未读)',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '好友关系表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for im_group_0
-- ----------------------------
DROP TABLE IF EXISTS `im_group_0`;
CREATE TABLE `im_group_0`  (
  `id` bigint(0) NOT NULL COMMENT '群聊id',
  `master_uid` bigint(0) NOT NULL COMMENT '群主id',
  `status` int(0) NOT NULL COMMENT '状态:0删除,1.正常,3禁用,4.解散',
  `group_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '群聊名称',
  `user_limit` int(0) NOT NULL COMMENT '群聊人数限制',
  `user_num` int(0) NOT NULL COMMENT '当前群聊人数',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '群聊' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for im_group_1
-- ----------------------------
DROP TABLE IF EXISTS `im_group_1`;
CREATE TABLE `im_group_1`  (
  `id` bigint(0) NOT NULL COMMENT '群聊id',
  `master_uid` bigint(0) NOT NULL COMMENT '群主id',
  `status` int(0) NOT NULL COMMENT '状态:0删除,1.正常,3禁用,4.解散',
  `group_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '群聊名称',
  `user_limit` int(0) NOT NULL COMMENT '群聊人数限制',
  `user_num` int(0) NOT NULL COMMENT '当前群聊人数',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '群聊' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for im_group_2
-- ----------------------------
DROP TABLE IF EXISTS `im_group_2`;
CREATE TABLE `im_group_2`  (
  `id` bigint(0) NOT NULL COMMENT '群聊id',
  `master_uid` bigint(0) NOT NULL COMMENT '群主id',
  `status` int(0) NOT NULL COMMENT '状态:0删除,1.正常,3禁用,4.解散',
  `group_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '群聊名称',
  `user_limit` int(0) NOT NULL COMMENT '群聊人数限制',
  `user_num` int(0) NOT NULL COMMENT '当前群聊人数',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '群聊' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for im_group_3
-- ----------------------------
DROP TABLE IF EXISTS `im_group_3`;
CREATE TABLE `im_group_3`  (
  `id` bigint(0) NOT NULL COMMENT '群聊id',
  `master_uid` bigint(0) NOT NULL COMMENT '群主id',
  `status` int(0) NOT NULL COMMENT '状态:0删除,1.正常,3禁用,4.解散',
  `group_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '群聊名称',
  `user_limit` int(0) NOT NULL COMMENT '群聊人数限制',
  `user_num` int(0) NOT NULL COMMENT '当前群聊人数',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '群聊' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for im_group_message_0
-- ----------------------------
DROP TABLE IF EXISTS `im_group_message_0`;
CREATE TABLE `im_group_message_0`  (
  `id` bigint(0) NOT NULL COMMENT 'id',
  `group_id` bigint(0) NOT NULL COMMENT '群聊id',
  `sender` bigint(0) NOT NULL COMMENT '发送者id',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `content` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '消息内容',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '群聊天消息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for im_group_message_1
-- ----------------------------
DROP TABLE IF EXISTS `im_group_message_1`;
CREATE TABLE `im_group_message_1`  (
  `id` bigint(0) NOT NULL COMMENT 'id',
  `group_id` bigint(0) NOT NULL COMMENT '群聊id',
  `sender` bigint(0) NOT NULL COMMENT '发送者id',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `content` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '消息内容',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '群聊天消息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for im_group_message_2
-- ----------------------------
DROP TABLE IF EXISTS `im_group_message_2`;
CREATE TABLE `im_group_message_2`  (
  `id` bigint(0) NOT NULL COMMENT 'id',
  `group_id` bigint(0) NOT NULL COMMENT '群聊id',
  `sender` bigint(0) NOT NULL COMMENT '发送者id',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `content` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '消息内容',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '群聊天消息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for im_group_message_3
-- ----------------------------
DROP TABLE IF EXISTS `im_group_message_3`;
CREATE TABLE `im_group_message_3`  (
  `id` bigint(0) NOT NULL COMMENT 'id',
  `group_id` bigint(0) NOT NULL COMMENT '群聊id',
  `sender` bigint(0) NOT NULL COMMENT '发送者id',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `content` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '消息内容',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '群聊天消息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for im_group_message_4
-- ----------------------------
DROP TABLE IF EXISTS `im_group_message_4`;
CREATE TABLE `im_group_message_4`  (
  `id` bigint(0) NOT NULL COMMENT 'id',
  `group_id` bigint(0) NOT NULL COMMENT '群聊id',
  `sender` bigint(0) NOT NULL COMMENT '发送者id',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `content` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '消息内容',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '群聊天消息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for im_group_message_5
-- ----------------------------
DROP TABLE IF EXISTS `im_group_message_5`;
CREATE TABLE `im_group_message_5`  (
  `id` bigint(0) NOT NULL COMMENT 'id',
  `group_id` bigint(0) NOT NULL COMMENT '群聊id',
  `sender` bigint(0) NOT NULL COMMENT '发送者id',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `content` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '消息内容',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '群聊天消息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for im_group_message_6
-- ----------------------------
DROP TABLE IF EXISTS `im_group_message_6`;
CREATE TABLE `im_group_message_6`  (
  `id` bigint(0) NOT NULL COMMENT 'id',
  `group_id` bigint(0) NOT NULL COMMENT '群聊id',
  `sender` bigint(0) NOT NULL COMMENT '发送者id',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `content` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '消息内容',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '群聊天消息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for im_group_message_7
-- ----------------------------
DROP TABLE IF EXISTS `im_group_message_7`;
CREATE TABLE `im_group_message_7`  (
  `id` bigint(0) NOT NULL COMMENT 'id',
  `group_id` bigint(0) NOT NULL COMMENT '群聊id',
  `sender` bigint(0) NOT NULL COMMENT '发送者id',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `content` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '消息内容',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '群聊天消息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for im_group_message_8
-- ----------------------------
DROP TABLE IF EXISTS `im_group_message_8`;
CREATE TABLE `im_group_message_8`  (
  `id` bigint(0) NOT NULL COMMENT 'id',
  `group_id` bigint(0) NOT NULL COMMENT '群聊id',
  `sender` bigint(0) NOT NULL COMMENT '发送者id',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `content` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '消息内容',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '群聊天消息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for im_group_message_9
-- ----------------------------
DROP TABLE IF EXISTS `im_group_message_9`;
CREATE TABLE `im_group_message_9`  (
  `id` bigint(0) NOT NULL COMMENT 'id',
  `group_id` bigint(0) NOT NULL COMMENT '群聊id',
  `sender` bigint(0) NOT NULL COMMENT '发送者id',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `content` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '消息内容',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '群聊天消息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for im_group_message_10
-- ----------------------------
DROP TABLE IF EXISTS `im_group_message_10`;
CREATE TABLE `im_group_message_10`  (
  `id` bigint(0) NOT NULL COMMENT 'id',
  `group_id` bigint(0) NOT NULL COMMENT '群聊id',
  `sender` bigint(0) NOT NULL COMMENT '发送者id',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `content` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '消息内容',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '群聊天消息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for im_group_message_11
-- ----------------------------
DROP TABLE IF EXISTS `im_group_message_11`;
CREATE TABLE `im_group_message_11`  (
  `id` bigint(0) NOT NULL COMMENT 'id',
  `group_id` bigint(0) NOT NULL COMMENT '群聊id',
  `sender` bigint(0) NOT NULL COMMENT '发送者id',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `content` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '消息内容',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '群聊天消息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for im_group_message_12
-- ----------------------------
DROP TABLE IF EXISTS `im_group_message_12`;
CREATE TABLE `im_group_message_12`  (
  `id` bigint(0) NOT NULL COMMENT 'id',
  `group_id` bigint(0) NOT NULL COMMENT '群聊id',
  `sender` bigint(0) NOT NULL COMMENT '发送者id',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `content` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '消息内容',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '群聊天消息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for im_group_message_13
-- ----------------------------
DROP TABLE IF EXISTS `im_group_message_13`;
CREATE TABLE `im_group_message_13`  (
  `id` bigint(0) NOT NULL COMMENT 'id',
  `group_id` bigint(0) NOT NULL COMMENT '群聊id',
  `sender` bigint(0) NOT NULL COMMENT '发送者id',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `content` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '消息内容',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '群聊天消息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for im_group_message_14
-- ----------------------------
DROP TABLE IF EXISTS `im_group_message_14`;
CREATE TABLE `im_group_message_14`  (
  `id` bigint(0) NOT NULL COMMENT 'id',
  `group_id` bigint(0) NOT NULL COMMENT '群聊id',
  `sender` bigint(0) NOT NULL COMMENT '发送者id',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `content` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '消息内容',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '群聊天消息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for im_group_message_15
-- ----------------------------
DROP TABLE IF EXISTS `im_group_message_15`;
CREATE TABLE `im_group_message_15`  (
  `id` bigint(0) NOT NULL COMMENT 'id',
  `group_id` bigint(0) NOT NULL COMMENT '群聊id',
  `sender` bigint(0) NOT NULL COMMENT '发送者id',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `content` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '消息内容',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '群聊天消息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for im_group_user_0
-- ----------------------------
DROP TABLE IF EXISTS `im_group_user_0`;
CREATE TABLE `im_group_user_0`  (
  `id` bigint(0) NOT NULL COMMENT 'id',
  `group_id` bigint(0) NOT NULL COMMENT '群聊id',
  `uid` bigint(0) NOT NULL COMMENT '用户id',
  `unread_num` int(0) NOT NULL COMMENT '群未读消息数量',
  `unread_offset_id` bigint(0) NULL DEFAULT NULL COMMENT '群未读消息id偏移量(大于等于该id的都未读)',
  `last_active` datetime(0) NULL DEFAULT NULL COMMENT '最后活跃时间',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '群聊用户列表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for im_group_user_1
-- ----------------------------
DROP TABLE IF EXISTS `im_group_user_1`;
CREATE TABLE `im_group_user_1`  (
  `id` bigint(0) NOT NULL COMMENT 'id',
  `group_id` bigint(0) NOT NULL COMMENT '群聊id',
  `uid` bigint(0) NOT NULL COMMENT '用户id',
  `unread_num` int(0) NOT NULL COMMENT '群未读消息数量',
  `unread_offset_id` bigint(0) NULL DEFAULT NULL COMMENT '群未读消息id偏移量(大于等于该id的都未读)',
  `last_active` datetime(0) NULL DEFAULT NULL COMMENT '最后活跃时间',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '群聊用户列表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for im_group_user_2
-- ----------------------------
DROP TABLE IF EXISTS `im_group_user_2`;
CREATE TABLE `im_group_user_2`  (
  `id` bigint(0) NOT NULL COMMENT 'id',
  `group_id` bigint(0) NOT NULL COMMENT '群聊id',
  `uid` bigint(0) NOT NULL COMMENT '用户id',
  `unread_num` int(0) NOT NULL COMMENT '群未读消息数量',
  `unread_offset_id` bigint(0) NULL DEFAULT NULL COMMENT '群未读消息id偏移量(大于等于该id的都未读)',
  `last_active` datetime(0) NULL DEFAULT NULL COMMENT '最后活跃时间',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '群聊用户列表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for im_group_user_3
-- ----------------------------
DROP TABLE IF EXISTS `im_group_user_3`;
CREATE TABLE `im_group_user_3`  (
  `id` bigint(0) NOT NULL COMMENT 'id',
  `group_id` bigint(0) NOT NULL COMMENT '群聊id',
  `uid` bigint(0) NOT NULL COMMENT '用户id',
  `unread_num` int(0) NOT NULL COMMENT '群未读消息数量',
  `unread_offset_id` bigint(0) NULL DEFAULT NULL COMMENT '群未读消息id偏移量(大于等于该id的都未读)',
  `last_active` datetime(0) NULL DEFAULT NULL COMMENT '最后活跃时间',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '群聊用户列表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for im_group_user_4
-- ----------------------------
DROP TABLE IF EXISTS `im_group_user_4`;
CREATE TABLE `im_group_user_4`  (
  `id` bigint(0) NOT NULL COMMENT 'id',
  `group_id` bigint(0) NOT NULL COMMENT '群聊id',
  `uid` bigint(0) NOT NULL COMMENT '用户id',
  `unread_num` int(0) NOT NULL COMMENT '群未读消息数量',
  `unread_offset_id` bigint(0) NULL DEFAULT NULL COMMENT '群未读消息id偏移量(大于等于该id的都未读)',
  `last_active` datetime(0) NULL DEFAULT NULL COMMENT '最后活跃时间',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '群聊用户列表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for im_group_user_5
-- ----------------------------
DROP TABLE IF EXISTS `im_group_user_5`;
CREATE TABLE `im_group_user_5`  (
  `id` bigint(0) NOT NULL COMMENT 'id',
  `group_id` bigint(0) NOT NULL COMMENT '群聊id',
  `uid` bigint(0) NOT NULL COMMENT '用户id',
  `unread_num` int(0) NOT NULL COMMENT '群未读消息数量',
  `unread_offset_id` bigint(0) NULL DEFAULT NULL COMMENT '群未读消息id偏移量(大于等于该id的都未读)',
  `last_active` datetime(0) NULL DEFAULT NULL COMMENT '最后活跃时间',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '群聊用户列表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for im_group_user_6
-- ----------------------------
DROP TABLE IF EXISTS `im_group_user_6`;
CREATE TABLE `im_group_user_6`  (
  `id` bigint(0) NOT NULL COMMENT 'id',
  `group_id` bigint(0) NOT NULL COMMENT '群聊id',
  `uid` bigint(0) NOT NULL COMMENT '用户id',
  `unread_num` int(0) NOT NULL COMMENT '群未读消息数量',
  `unread_offset_id` bigint(0) NULL DEFAULT NULL COMMENT '群未读消息id偏移量(大于等于该id的都未读)',
  `last_active` datetime(0) NULL DEFAULT NULL COMMENT '最后活跃时间',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '群聊用户列表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for im_group_user_7
-- ----------------------------
DROP TABLE IF EXISTS `im_group_user_7`;
CREATE TABLE `im_group_user_7`  (
  `id` bigint(0) NOT NULL COMMENT 'id',
  `group_id` bigint(0) NOT NULL COMMENT '群聊id',
  `uid` bigint(0) NOT NULL COMMENT '用户id',
  `unread_num` int(0) NOT NULL COMMENT '群未读消息数量',
  `unread_offset_id` bigint(0) NULL DEFAULT NULL COMMENT '群未读消息id偏移量(大于等于该id的都未读)',
  `last_active` datetime(0) NULL DEFAULT NULL COMMENT '最后活跃时间',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '群聊用户列表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for im_message_0
-- ----------------------------
DROP TABLE IF EXISTS `im_message_0`;
CREATE TABLE `im_message_0`  (
  `id` bigint(0) NOT NULL COMMENT 'id',
  `relation_id` bigint(0) NULL DEFAULT NULL,
  `sender` bigint(0) NOT NULL COMMENT '发送人id',
  `receiver` bigint(0) NOT NULL COMMENT '接受者id',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `content` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '消息内容',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '聊天消息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for im_message_1
-- ----------------------------
DROP TABLE IF EXISTS `im_message_1`;
CREATE TABLE `im_message_1`  (
  `id` bigint(0) NOT NULL COMMENT 'id',
  `relation_id` bigint(0) NULL DEFAULT NULL,
  `sender` bigint(0) NOT NULL COMMENT '发送人id',
  `receiver` bigint(0) NOT NULL COMMENT '接受者id',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `content` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '消息内容',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '聊天消息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for im_message_2
-- ----------------------------
DROP TABLE IF EXISTS `im_message_2`;
CREATE TABLE `im_message_2`  (
  `id` bigint(0) NOT NULL COMMENT 'id',
  `relation_id` bigint(0) NULL DEFAULT NULL,
  `sender` bigint(0) NOT NULL COMMENT '发送人id',
  `receiver` bigint(0) NOT NULL COMMENT '接受者id',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `content` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '消息内容',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '聊天消息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for im_message_3
-- ----------------------------
DROP TABLE IF EXISTS `im_message_3`;
CREATE TABLE `im_message_3`  (
  `id` bigint(0) NOT NULL COMMENT 'id',
  `relation_id` bigint(0) NULL DEFAULT NULL,
  `sender` bigint(0) NOT NULL COMMENT '发送人id',
  `receiver` bigint(0) NOT NULL COMMENT '接受者id',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `content` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '消息内容',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '聊天消息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for im_message_4
-- ----------------------------
DROP TABLE IF EXISTS `im_message_4`;
CREATE TABLE `im_message_4`  (
  `id` bigint(0) NOT NULL COMMENT 'id',
  `relation_id` bigint(0) NULL DEFAULT NULL,
  `sender` bigint(0) NOT NULL COMMENT '发送人id',
  `receiver` bigint(0) NOT NULL COMMENT '接受者id',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `content` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '消息内容',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '聊天消息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for im_message_5
-- ----------------------------
DROP TABLE IF EXISTS `im_message_5`;
CREATE TABLE `im_message_5`  (
  `id` bigint(0) NOT NULL COMMENT 'id',
  `relation_id` bigint(0) NULL DEFAULT NULL,
  `sender` bigint(0) NOT NULL COMMENT '发送人id',
  `receiver` bigint(0) NOT NULL COMMENT '接受者id',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `content` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '消息内容',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '聊天消息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for im_message_6
-- ----------------------------
DROP TABLE IF EXISTS `im_message_6`;
CREATE TABLE `im_message_6`  (
  `id` bigint(0) NOT NULL COMMENT 'id',
  `relation_id` bigint(0) NULL DEFAULT NULL,
  `sender` bigint(0) NOT NULL COMMENT '发送人id',
  `receiver` bigint(0) NOT NULL COMMENT '接受者id',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `content` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '消息内容',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '聊天消息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for im_message_7
-- ----------------------------
DROP TABLE IF EXISTS `im_message_7`;
CREATE TABLE `im_message_7`  (
  `id` bigint(0) NOT NULL COMMENT 'id',
  `relation_id` bigint(0) NULL DEFAULT NULL,
  `sender` bigint(0) NOT NULL COMMENT '发送人id',
  `receiver` bigint(0) NOT NULL COMMENT '接受者id',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `content` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '消息内容',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '聊天消息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for im_segment_id
-- ----------------------------
DROP TABLE IF EXISTS `im_segment_id`;
CREATE TABLE `im_segment_id`  (
  `biz_tag` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '业务标签',
  `current_id` bigint(0) NOT NULL COMMENT '当前id值',
  `init_step` bigint(0) NOT NULL COMMENT '业务标签初始步长',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `version` bigint(0) NOT NULL COMMENT '版本号',
  PRIMARY KEY (`biz_tag`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '分段id' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for im_segment_id
-- ----------------------------
DROP TABLE IF EXISTS `im_segment_id`;
CREATE TABLE `im_segment_id`  (
  `biz_tag` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '业务标签',
  `current_id` bigint(0) NOT NULL COMMENT '当前id值',
  `init_step` bigint(0) NOT NULL COMMENT '业务标签初始步长',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `version` bigint(0) NOT NULL COMMENT '版本号',
  PRIMARY KEY (`biz_tag`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '分段id' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of im_segment_id
-- ----------------------------
INSERT INTO `im_segment_id` VALUES ('im_friend', 12002, 1000, '2022-06-03 19:45:15', '2022-06-12 13:08:59', 2);
INSERT INTO `im_segment_id` VALUES ('im_friend_relation_id', 12002, 1000, '2022-06-03 19:45:15', '2022-06-12 13:08:59', 2);
INSERT INTO `im_segment_id` VALUES ('im_group', 12002, 1000, '2022-06-05 09:52:41', '2022-06-07 17:39:39', 2);
INSERT INTO `im_segment_id` VALUES ('im_group_message', 16006, 1000, '2022-06-07 16:45:41', '2022-06-12 13:09:48', 6);
INSERT INTO `im_segment_id` VALUES ('im_group_user', 13003, 1000, '2022-06-05 09:52:42', '2022-06-07 17:39:39', 3);
INSERT INTO `im_segment_id` VALUES ('im_message', 35025, 1000, '2022-06-03 19:55:01', '2022-06-12 13:09:10', 25);

-- ----------------------------
-- Table structure for im_user_0
-- ----------------------------
DROP TABLE IF EXISTS `im_user_0`;
CREATE TABLE `im_user_0`  (
  `id` bigint(0) NOT NULL COMMENT 'id',
  `status` int(0) NOT NULL COMMENT '状态:0删除,1.正常,2.禁用',
  `account` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '登录账号',
  `nickname` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户昵称',
  `password` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '密码',
  `phone` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `last_login_time` datetime(0) NULL DEFAULT NULL COMMENT '最后登录时间',
  `last_active_time` datetime(0) NULL DEFAULT NULL COMMENT '最后活跃时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_account_status_0`(`account`, `status`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of im_user_0
-- ----------------------------
INSERT INTO `im_user_0` VALUES (728220, 1, 'zeus', '宙斯', '123456', '17882451901', '2022-04-14 10:50:55', NULL, NULL);

-- ----------------------------
-- Table structure for im_user_1
-- ----------------------------
DROP TABLE IF EXISTS `im_user_1`;
CREATE TABLE `im_user_1`  (
  `id` bigint(0) NOT NULL COMMENT 'id',
  `status` int(0) NOT NULL COMMENT '状态:0删除,1.正常,2.禁用',
  `account` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '登录账号',
  `nickname` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户昵称',
  `password` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '密码',
  `phone` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `last_login_time` datetime(0) NULL DEFAULT NULL COMMENT '最后登录时间',
  `last_active_time` datetime(0) NULL DEFAULT NULL COMMENT '最后活跃时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_account_status`(`account`, `status`) USING BTREE,
  INDEX `idx_account_status_1`(`account`, `status`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of im_user_1
-- ----------------------------
INSERT INTO `im_user_1` VALUES (727717, 1, 'prometheus', '普罗米修斯', '123456', '17882451908', '2022-04-14 10:46:23', NULL, NULL);

-- ----------------------------
-- Table structure for im_user_2
-- ----------------------------
DROP TABLE IF EXISTS `im_user_2`;
CREATE TABLE `im_user_2`  (
  `id` bigint(0) NOT NULL COMMENT 'id',
  `status` int(0) NOT NULL COMMENT '状态:0删除,1.正常,2.禁用',
  `account` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '登录账号',
  `nickname` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户昵称',
  `password` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '密码',
  `phone` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `last_login_time` datetime(0) NULL DEFAULT NULL COMMENT '最后登录时间',
  `last_active_time` datetime(0) NULL DEFAULT NULL COMMENT '最后活跃时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_account_status`(`account`, `status`) USING BTREE,
  INDEX `idx_account_status_2`(`account`, `status`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of im_user_2
-- ----------------------------
INSERT INTO `im_user_2` VALUES (729726, 1, 'poros', '波罗斯', '123456', '17882451902', '2022-04-14 10:53:39', NULL, NULL);

-- ----------------------------
-- Table structure for im_user_3
-- ----------------------------
DROP TABLE IF EXISTS `im_user_3`;
CREATE TABLE `im_user_3`  (
  `id` bigint(0) NOT NULL COMMENT 'id',
  `status` int(0) NOT NULL COMMENT '状态:0删除,1.正常,2.禁用',
  `account` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '登录账号',
  `nickname` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户昵称',
  `password` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '密码',
  `phone` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `last_login_time` datetime(0) NULL DEFAULT NULL COMMENT '最后登录时间',
  `last_active_time` datetime(0) NULL DEFAULT NULL COMMENT '最后活跃时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_account_status_3`(`account`, `status`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of im_user_3
-- ----------------------------
SET FOREIGN_KEY_CHECKS = 1;
