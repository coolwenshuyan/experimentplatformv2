-- 先执行inin1.sql语句，一定要启动项目后再导入experimentplatformv2.sql
-- 先执行inin1.sql语句，一定要启动项目后再导入experimentplatformv2.sql
-- 先执行inin1.sql语句，一定要启动项目后再导入experimentplatformv2.sql
-- 先执行inin1.sql语句，一定要启动项目后再导入experimentplatformv2.sql
-- 先执行inin1.sql语句，一定要启动项目后再导入experimentplatformv2.sql
-- 先执行inin1.sql语句，一定要启动项目后再导入experimentplatformv2.sql


SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for seq_table
-- ----------------------------
DROP TABLE IF EXISTS `seq_table`;
CREATE TABLE `seq_table`  (
  `sequence_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `next_val` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`sequence_name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of seq_table
-- ----------------------------
INSERT INTO `seq_table` VALUES ('t_admin', 0);
INSERT INTO `seq_table` VALUES ('t_class', 3);
INSERT INTO `seq_table` VALUES ('t_college_report', 16);
INSERT INTO `seq_table` VALUES ('t_docker', 0);
INSERT INTO `seq_table` VALUES ('t_effect', 15);
INSERT INTO `seq_table` VALUES ('t_exp_model', 9);
INSERT INTO `seq_table` VALUES ('t_kaohemodel', 6);
INSERT INTO `seq_table` VALUES ('t_kaohemodel_score', 48);
INSERT INTO `seq_table` VALUES ('t_mreport', 0);
INSERT INTO `seq_table` VALUES ('t_mreport_answer', 0);
INSERT INTO `seq_table` VALUES ('t_mtest_answer', 417);
INSERT INTO `seq_table` VALUES ('t_mtest_answer_stu', 71);
INSERT INTO `seq_table` VALUES ('t_mtest_quest', 103);
INSERT INTO `seq_table` VALUES ('t_newsinfo', 5);
INSERT INTO `seq_table` VALUES ('t_question', 9);
INSERT INTO `seq_table` VALUES ('t_reply', 2);
INSERT INTO `seq_table` VALUES ('t_resource', 1);
INSERT INTO `seq_table` VALUES ('t_role', 1);
INSERT INTO `seq_table` VALUES ('t_role_res', 1);
INSERT INTO `seq_table` VALUES ('t_student', 19);
INSERT INTO `seq_table` VALUES ('t_teacher', 6);
INSERT INTO `seq_table` VALUES ('t_totalscore_current', 10);
INSERT INTO `seq_table` VALUES ('t_totalscore_pass', 0);
INSERT INTO `seq_table` VALUES ('t_user', 2);
INSERT INTO `seq_table` VALUES ('t_user_role', 2);

-- ----------------------------
-- Table structure for t_admin
-- ----------------------------
DROP TABLE IF EXISTS `t_admin`;
CREATE TABLE `t_admin`  (
  `id` int(11) NOT NULL,
  `mobile` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `uname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of t_admin
-- ----------------------------
INSERT INTO `t_admin` VALUES (1, '13882238627', '0192023a7bbd73250516f069df18b500', 'admin');
INSERT INTO `t_user` (`id`, `nickname`, `password`, `status`, `username`) VALUES (1, '朱老板', 'eb62f6b9306db575c2d596b1279627a4', '\0', '0');
INSERT INTO `t_user` (`id`, `nickname`, `password`, `status`, `username`) VALUES (2, 'Coolwen', '2cfd4560539f887a5e420412b370b361', '\0', '1');
INSERT INTO `t_role` (`id`, `name`, `sn`) VALUES (1, '管理员', 'admin');
INSERT INTO `t_resource` (`id`, `name`, `permission`, `url`) VALUES (1, '系统管理', 'admin:*', '/**');
INSERT INTO `t_user_role` (`id`, `role_id`, `user_id`) VALUES (1, 1, 1);
INSERT INTO `t_user_role` (`id`, `role_id`, `user_id`) VALUES (2, 1, 2);
INSERT INTO `t_role_res` (`id`, `res_id`, `role_id`) VALUES (1, 1, 1);
