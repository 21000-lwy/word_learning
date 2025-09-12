/*
 Navicat Premium Data Transfer

 Source Server         : ybc
 Source Server Type    : MySQL
 Source Server Version : 80041 (8.0.41)
 Source Host           : localhost:3306
 Source Schema         : word_learning

 Target Server Type    : MySQL
 Target Server Version : 80041 (8.0.41)
 File Encoding         : 65001

 Date: 10/09/2025 20:55:05
*/

CREATE DATABASE IF NOT EXISTS `word_learning` CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;

USE `word_learning`;

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for user_word_progress
-- ----------------------------
DROP TABLE IF EXISTS `user_word_progress`;
CREATE TABLE `user_word_progress`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `correct_count` int NULL DEFAULT NULL,
  `created_at` datetime(6) NULL DEFAULT NULL,
  `last_reviewed` datetime(6) NULL DEFAULT NULL,
  `mastery_level` int NULL DEFAULT NULL,
  `next_review` datetime(6) NULL DEFAULT NULL,
  `updated_at` datetime(6) NULL DEFAULT NULL,
  `wrong_count` int NULL DEFAULT NULL,
  `user_id` bigint NOT NULL,
  `word_id` bigint NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FKciba19f12lld0spe86k0kgs9i`(`user_id` ASC) USING BTREE,
  INDEX `FKaruauoevlbohih8usngx3o9e2`(`word_id` ASC) USING BTREE,
  CONSTRAINT `FKaruauoevlbohih8usngx3o9e2` FOREIGN KEY (`word_id`) REFERENCES `words` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FKciba19f12lld0spe86k0kgs9i` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_word_progress
-- ----------------------------
INSERT INTO `user_word_progress` VALUES (1, 0, '2025-09-10 16:27:50.763629', '2025-09-10 16:27:50.761629', 0, '2025-09-10 16:37:50.761629', '2025-09-10 16:27:50.763629', 1, 1, 1);
INSERT INTO `user_word_progress` VALUES (2, 0, '2025-09-10 16:27:58.195737', '2025-09-10 16:27:58.193742', 0, '2025-09-10 16:37:58.193742', '2025-09-10 16:27:58.195737', 1, 1, 2);
INSERT INTO `user_word_progress` VALUES (3, 1, '2025-09-10 16:45:53.702975', '2025-09-10 16:45:53.692111', 1, '2025-09-10 17:45:53.692111', '2025-09-10 16:45:53.702975', 0, 1, 3);
INSERT INTO `user_word_progress` VALUES (4, 1, '2025-09-10 16:46:05.473276', '2025-09-10 16:46:05.471279', 1, '2025-09-10 17:46:05.471279', '2025-09-10 16:46:05.473276', 0, 1, 4);
INSERT INTO `user_word_progress` VALUES (5, 0, '2025-09-10 17:22:56.720931', '2025-09-10 17:22:56.715928', 0, '2025-09-10 17:32:56.715928', '2025-09-10 17:22:56.720931', 1, 1, 5);
INSERT INTO `user_word_progress` VALUES (6, 1, '2025-09-10 20:09:37.901293', '2025-09-10 20:09:37.898992', 1, '2025-09-10 21:09:37.898992', '2025-09-10 20:09:37.901293', 0, 1, 6);
INSERT INTO `user_word_progress` VALUES (7, 0, '2025-09-10 20:09:42.083549', '2025-09-10 20:09:42.082504', 0, '2025-09-10 20:19:42.082504', '2025-09-10 20:09:42.083549', 1, 1, 7);
INSERT INTO `user_word_progress` VALUES (8, 1, '2025-09-10 20:17:50.224999', '2025-09-10 20:17:50.224999', 1, '2025-09-10 21:17:50.224999', '2025-09-10 20:17:50.224999', 0, 1, 8);
INSERT INTO `user_word_progress` VALUES (9, 1, '2025-09-10 20:18:08.232536', '2025-09-10 20:18:08.231530', 1, '2025-09-10 21:18:08.231530', '2025-09-10 20:18:08.232536', 0, 1, 9);
INSERT INTO `user_word_progress` VALUES (10, 0, '2025-09-10 20:18:13.088940', '2025-09-10 20:18:13.087949', 0, '2025-09-10 20:28:13.087949', '2025-09-10 20:18:13.088940', 1, 1, 10);

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) NULL DEFAULT NULL,
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `updated_at` datetime(6) NULL DEFAULT NULL,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `role` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `UK_6dotkott2kjsp8vw4d0m25fb7`(`email` ASC) USING BTREE,
  UNIQUE INDEX `UK_r43af9ap4edm43mmtq01oddj6`(`username` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES (1, '2025-09-10 16:10:55.794559', '398250338@qq.com', '123456', '2025-09-10 16:10:55.794559', '123', '');
INSERT INTO `users` VALUES (2, '2025-09-10 20:02:37.914285', 'admin@example.com', 'admin123', '2025-09-10 20:02:37.914285', 'admin', 'ADMIN');

-- ----------------------------
-- Table structure for words
-- ----------------------------
DROP TABLE IF EXISTS `words`;
CREATE TABLE `words`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `chinese` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `created_at` datetime(6) NULL DEFAULT NULL,
  `difficulty_level` int NULL DEFAULT NULL,
  `english` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `example` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `pronunciation` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `UK_pjwwj0xp7slpxs1hv99po85lo`(`english` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 52 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of words
-- ----------------------------
INSERT INTO `words` VALUES (1, '你好', '2025-09-10 16:25:29.394342', 1, 'hello', 'Hello, how are you? 你好，你好吗？', '/həˈloʊ/');
INSERT INTO `words` VALUES (2, '世界', '2025-09-10 16:25:29.443876', 1, 'world', 'Welcome to the world! 欢迎来到这个世界！', '/wɜːrld/');
INSERT INTO `words` VALUES (3, '爱', '2025-09-10 16:25:29.453167', 1, 'love', 'I love you. 我爱你。', '/lʌv/');
INSERT INTO `words` VALUES (4, '书', '2025-09-10 16:25:29.460279', 1, 'book', 'This is a good book. 这是一本好书。', '/bʊk/');
INSERT INTO `words` VALUES (5, '学习', '2025-09-10 16:25:29.468281', 1, 'study', 'I study English every day. 我每天学习英语。', '/ˈstʌdi/');
INSERT INTO `words` VALUES (6, '电脑', '2025-09-10 16:25:29.477811', 1, 'computer', 'I use a computer to work. 我用电脑工作。', '/kəmˈpjuːtər/');
INSERT INTO `words` VALUES (7, '学校', '2025-09-10 16:25:29.492522', 1, 'school', 'I go to school by bus. 我坐公交车去学校。', '/skuːl/');
INSERT INTO `words` VALUES (8, '老师', '2025-09-10 16:25:29.501533', 1, 'teacher', 'My teacher is very kind. 我的老师很和蔼。', '/ˈtiːtʃər/');
INSERT INTO `words` VALUES (9, '学生', '2025-09-10 16:25:29.510670', 1, 'student', 'She is a good student. 她是一个好学生。', '/ˈstuːdənt/');
INSERT INTO `words` VALUES (10, '朋友', '2025-09-10 16:25:29.518907', 1, 'friend', 'He is my best friend. 他是我最好的朋友。', '/frend/');
INSERT INTO `words` VALUES (11, '家庭', '2025-09-10 16:25:29.529899', 1, 'family', 'I love my family. 我爱我的家庭。', '/ˈfæməli/');
INSERT INTO `words` VALUES (12, '房子', '2025-09-10 16:25:29.540910', 1, 'house', 'This is my house. 这是我的房子。', '/haʊs/');
INSERT INTO `words` VALUES (13, '水', '2025-09-10 16:25:29.554757', 1, 'water', 'I drink water every day. 我每天喝水。', '/ˈwɔːtər/');
INSERT INTO `words` VALUES (14, '食物', '2025-09-10 16:25:29.569365', 1, 'food', 'Chinese food is delicious. 中国菜很美味。', '/fuːd/');
INSERT INTO `words` VALUES (15, '时间', '2025-09-10 16:25:29.584666', 1, 'time', 'Time is money. 时间就是金钱。', '/taɪm/');
INSERT INTO `words` VALUES (16, '钱', '2025-09-10 16:25:29.599205', 1, 'money', 'Money can\'t buy happiness. 金钱买不到快乐。', '/ˈmʌni/');
INSERT INTO `words` VALUES (17, '快乐的', '2025-09-10 16:25:29.611199', 1, 'happy', 'I am very happy today. 我今天很快乐。', '/ˈhæpi/');
INSERT INTO `words` VALUES (18, '美丽的', '2025-09-10 16:25:29.626369', 1, 'beautiful', 'She is very beautiful. 她很美丽。', '/ˈbjuːtɪfl/');
INSERT INTO `words` VALUES (19, '好的', '2025-09-10 16:25:29.637474', 1, 'good', 'This is a good idea. 这是个好主意。', '/ɡʊd/');
INSERT INTO `words` VALUES (20, '坏的', '2025-09-10 16:25:29.650372', 1, 'bad', 'That\'s not a bad thing. 那不是坏事。', '/bæd/');
INSERT INTO `words` VALUES (21, '大的', '2025-09-10 16:25:29.659496', 1, 'big', 'This is a big house. 这是一座大房子。', '/bɪɡ/');
INSERT INTO `words` VALUES (22, '小的', '2025-09-10 16:25:29.667708', 1, 'small', 'I have a small car. 我有一辆小汽车。', '/smɔːl/');
INSERT INTO `words` VALUES (23, '新的', '2025-09-10 16:25:29.679757', 1, 'new', 'I bought a new phone. 我买了一部新手机。', '/nuː/');
INSERT INTO `words` VALUES (24, '旧的', '2025-09-10 16:25:29.687858', 1, 'old', 'This is an old building. 这是一座旧建筑。', '/oʊld/');
INSERT INTO `words` VALUES (25, '年轻的', '2025-09-10 16:25:29.695339', 1, 'young', 'She is very young. 她很年轻。', '/jʌŋ/');
INSERT INTO `words` VALUES (26, '工作', '2025-09-10 16:25:29.703354', 1, 'work', 'I work in an office. 我在办公室工作。', '/wɜːrk/');
INSERT INTO `words` VALUES (27, '玩', '2025-09-10 16:25:29.710840', 1, 'play', 'Children like to play games. 孩子们喜欢玩游戏。', '/pleɪ/');
INSERT INTO `words` VALUES (28, '吃', '2025-09-10 16:25:29.718843', 1, 'eat', 'I eat breakfast at 7 AM. 我早上7点吃早餐。', '/iːt/');
INSERT INTO `words` VALUES (29, '喝', '2025-09-10 16:25:29.727354', 1, 'drink', 'I drink coffee in the morning. 我早上喝咖啡。', '/drɪŋk/');
INSERT INTO `words` VALUES (30, '睡觉', '2025-09-10 16:25:29.733387', 1, 'sleep', 'I sleep 8 hours every night. 我每晚睡8小时。', '/sliːp/');
INSERT INTO `words` VALUES (31, '走路', '2025-09-10 16:25:29.742396', 1, 'walk', 'I walk to work every day. 我每天走路上班。', '/wɔːk/');
INSERT INTO `words` VALUES (32, '跑', '2025-09-10 16:25:29.751579', 1, 'run', 'I run in the park. 我在公园里跑步。', '/rʌn/');
INSERT INTO `words` VALUES (33, '读', '2025-09-10 16:25:29.759683', 1, 'read', 'I read books before sleep. 我睡前读书。', '/riːd/');
INSERT INTO `words` VALUES (34, '写', '2025-09-10 16:25:29.767686', 1, 'write', 'I write in my diary. 我写日记。', '/raɪt/');
INSERT INTO `words` VALUES (35, '听', '2025-09-10 16:25:29.776230', 1, 'listen', 'I listen to music. 我听音乐。', '/ˈlɪsən/');
INSERT INTO `words` VALUES (36, '说话', '2025-09-10 16:25:29.787752', 1, 'speak', 'I speak Chinese and English. 我说中文和英文。', '/spiːk/');
INSERT INTO `words` VALUES (37, '看', '2025-09-10 16:25:29.795762', 1, 'watch', 'I watch TV in the evening. 我晚上看电视。', '/wɑːtʃ/');
INSERT INTO `words` VALUES (38, '学习', '2025-09-10 16:25:29.805763', 1, 'learn', 'I learn something new every day. 我每天学习新东西。', '/lɜːrn/');
INSERT INTO `words` VALUES (39, '教', '2025-09-10 16:25:29.816819', 1, 'teach', 'My mother teaches me cooking. 我妈妈教我做饭。', '/tiːtʃ/');
INSERT INTO `words` VALUES (40, '帮助', '2025-09-10 16:25:29.822818', 1, 'help', 'Can you help me? 你能帮助我吗？', '/help/');
INSERT INTO `words` VALUES (41, '思考', '2025-09-10 16:25:29.830817', 1, 'think', 'I think this is correct. 我认为这是正确的。', '/θɪŋk/');
INSERT INTO `words` VALUES (42, '知道', '2025-09-10 16:25:29.837820', 1, 'know', 'I know the answer. 我知道答案。', '/noʊ/');
INSERT INTO `words` VALUES (43, '理解', '2025-09-10 16:25:29.845915', 1, 'understand', 'I understand your problem. 我理解你的问题。', '/ˌʌndərˈstænd/');
INSERT INTO `words` VALUES (44, '记住', '2025-09-10 16:25:29.853688', 1, 'remember', 'Please remember this word. 请记住这个单词。', '/rɪˈmembər/');
INSERT INTO `words` VALUES (45, '忘记', '2025-09-10 16:25:29.864708', 1, 'forget', 'Don\'t forget to call me. 别忘了给我打电话。', '/fərˈɡet/');
INSERT INTO `words` VALUES (46, '重要的', '2025-09-10 16:25:29.875214', 1, 'important', 'This is very important. 这很重要。', '/ɪmˈpɔːrtənt/');
INSERT INTO `words` VALUES (47, '有趣的', '2025-09-10 16:25:29.887220', 1, 'interesting', 'This book is very interesting. 这本书很有趣。', '/ˈɪntrəstɪŋ/');
INSERT INTO `words` VALUES (48, '困难的', '2025-09-10 16:25:29.897219', 1, 'difficult', 'This question is difficult. 这个问题很难。', '/ˈdɪfɪkəlt/');
INSERT INTO `words` VALUES (49, '容易的', '2025-09-10 16:25:29.908664', 1, 'easy', 'This game is very easy. 这个游戏很容易。', '/ˈiːzi/');
INSERT INTO `words` VALUES (50, '快的', '2025-09-10 16:25:29.921675', 1, 'fast', 'He runs very fast. 他跑得很快。', '/fæst/');
INSERT INTO `words` VALUES (51, '慢的', '2025-09-10 16:25:29.935064', 1, 'slow', 'The turtle moves slowly. 乌龟移动得很慢。', '/sloʊ/');

SET FOREIGN_KEY_CHECKS = 1;
