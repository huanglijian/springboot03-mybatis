-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: localhost    Database: creatcenter
-- ------------------------------------------------------
-- Server version	5.7.20-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `account`
--

DROP TABLE IF EXISTS `account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `account` (
  `acc_id` int(15) NOT NULL AUTO_INCREMENT,
  `acc_money` double(15,2) NOT NULL,
  `acc_type` varchar(25) NOT NULL,
  `acc_foreid` varchar(255) NOT NULL,
  PRIMARY KEY (`acc_id`)
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account`
--

LOCK TABLES `account` WRITE;
/*!40000 ALTER TABLE `account` DISABLE KEYS */;
INSERT INTO `account` VALUES (1,7168.91,'发布者','af8cfc18-b84d-4825-a49c-e0f6cb527858'),(5,0.00,'发布者','7006494107424a4ab6a2365907acc2d6'),(6,0.00,'普通用户','db76983ae221487dbcc0751f4744a57f'),(7,0.00,'普通用户','0a8e00e298f84927b64947220e55baef'),(8,0.00,'普通用户','15f52c274c5f4901add842445fa68a2e'),(9,0.00,'undefined','fc8af2c38a6746c5adbc1f16a8be8c84'),(10,746.80,'管理员','7f628d5d-2265-49d4-b12d-d65b8f280901'),(11,0.00,'发布者','cbbb863ab2ef4c9683b35414f354aeed'),(12,11572.00,'普通用户','e9ae842a-e70f-49b8-a9b5-bee24a13c8bb'),(13,0.00,'普通用户','46c7019fc82a4db4869818f13744a444'),(14,0.00,'普通用户','9f3f34eae58247dfa84b8c8b85059f75'),(15,0.00,'普通用户','05497f212ace40b3b32310b456220681'),(16,0.00,'普通用户','4ed9ce2894d544cab63b6c28be889697'),(17,0.00,'普通用户','04be96492a784f26a5db9ffe679b5460'),(18,0.00,'普通用户','66a75c8f9f5249769171a322a99884c5'),(19,0.00,'普通用户','9aebf999ec9440d68150da597b13f9f7'),(20,0.00,'发布者','cc12c8fc0b1441a8b152336983cd2e93'),(21,0.00,'普通用户','ded80c33c8624663960df94214129b8e'),(22,0.00,'普通用户','441f8d7dde6e4a43bfe7720c85930365'),(23,0.00,'发布者','507f9ccc4ff44a7985118edd6268c46d'),(24,900.00,'普通用户','33d0023cceda4b7dbc6d403fa349ca0a'),(25,0.00,'普通用户','61c0ccaed30846d4a6a6c0563efa64af'),(26,0.00,'普通用户','173d1db82f0948288790c57855b69b4b'),(27,0.00,'普通用户','36ab99a97d344f3e8053b0fd5cc96f52'),(28,0.00,'发布者','d22139d06a8e47c89759a1949a9b4274'),(29,0.00,'发布者','477843437276468091519f62b6d522e8'),(30,0.00,'普通用户','3fdc347f78624422bd9f3db7fb14c107'),(31,0.00,'普通用户','e1e703cfbe324d618ca507819c4ecda6'),(32,0.00,'发布者','f424a2703c9640f296f6511d97699c18'),(33,0.00,'发布者','660c3cf9200b40698e1f00e5715deaf4'),(34,0.00,'发布者','4ad9a93a681f4318929bc94f8be46176'),(35,0.00,'普通用户','17a2c367709145a39d9cb394a7d95b5c'),(36,0.00,'发布者','5f435d92de594de18d6ec97380d06fd3'),(37,0.00,'发布者','19dab81fd2e04c86be2912398a062a18'),(38,0.00,'发布者','47340319c80f4c7a90d50e2ca525ffb0'),(39,0.00,'发布者','ab3d87a1908f4355ae9b6856d3d2ece0'),(40,0.00,'发布者','6657322bd2224374a7f5c275cc898988'),(41,0.00,'发布者','2cbf39eab72d40a4bc1d5aba8b83430e'),(42,0.00,'普通用户','d57b383e40a04c2bb3a851237453ae4c');
/*!40000 ALTER TABLE `account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `admin`
--

DROP TABLE IF EXISTS `admin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `admin` (
  `admin_id` varchar(255) NOT NULL DEFAULT '',
  `admin_name` varchar(25) NOT NULL,
  `admin_phone` varchar(25) NOT NULL,
  `admin_img` varchar(1000) NOT NULL,
  PRIMARY KEY (`admin_id`),
  CONSTRAINT `admin_alluser` FOREIGN KEY (`admin_id`) REFERENCES `alluser` (`all_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admin`
--

LOCK TABLES `admin` WRITE;
/*!40000 ALTER TABLE `admin` DISABLE KEYS */;
INSERT INTO `admin` VALUES ('0b38a39372cc4ae3bf40b7a365c0c3fc','测试添加','12345678911','default.jpg'),('7f628d5d-2265-49d4-b12d-d65b8f280901','超级管理员','1234','default.jpg');
/*!40000 ALTER TABLE `admin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `alluser`
--

DROP TABLE IF EXISTS `alluser`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `alluser` (
  `all_id` varchar(255) NOT NULL DEFAULT '',
  `all_type` varchar(25) NOT NULL,
  `all_pwd` varchar(255) NOT NULL,
  `all_salt` varchar(25) NOT NULL,
  `all_email` varchar(25) NOT NULL,
  `all_state` varchar(25) NOT NULL,
  PRIMARY KEY (`all_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `alluser`
--

LOCK TABLES `alluser` WRITE;
/*!40000 ALTER TABLE `alluser` DISABLE KEYS */;
INSERT INTO `alluser` VALUES ('04be96492a784f26a5db9ffe679b5460','普通用户','d27a14297a4499ef2369e556f35c0af393f9c35b7a664929f637ec81b0168454','85LDBbJS70MYw1wziYT1','17876255555@163.com','1'),('05497f212ace40b3b32310b456220681','普通用户','656d3918a3f16ead186351d8f1368c396ebd87647722528f1cb1c04320b87ec1','1MuW9y8bJZRAdj4mNsXl','17876253333@163.com','1'),('0a8e00e298f84927b64947220e55baef','普通用户','3df6b2c36dcc54c8b020c17fb62add3712c0043a5ba9253d856152543624ded7','zvBUSrWPbH7XP5K7psGa','mzb1','1'),('0b38a39372cc4ae3bf40b7a365c0c3fc','管理员','16bde813a5c4b40d39ce5096edf798479fdc437a7eae219755776cf844a3c6e3','KLIIi8nhRwuYjfGJej5G','adminadmin','1'),('15f52c274c5f4901add842445fa68a2e','普通用户','b4c4fe1b1198480da0db1bd1ab023d7be405b73f5d89b7de4f4f3c38f527bbf3','qSyrrw6XpK3uX7YuWv0O','17876253441','1'),('173d1db82f0948288790c57855b69b4b','普通用户','bc636329646c3f940f9a513696646b8e9ed1d61ba938b4908256a74356ba0b34','oajPvnfDs8FgGQFf7Vz3','mzb2','1'),('17a2c367709145a39d9cb394a7d95b5c','普通用户','182298cf6881af7a08e564c5b57cfccafcc20efa132fb162083392724ecb5327','PJuT52TGjyHQj5XhCGct','1225515396@qq.com','1'),('2cbf39eab72d40a4bc1d5aba8b83430e','发布者','aa3988e1ba6538b94b39e5bafcdb7139af1346a4b4a856a096bb31a1a5b73509','M3qZP1dmR4BKxXCV75rO','2725846875@qq.com','1'),('33d0023cceda4b7dbc6d403fa349ca0a','普通用户','a718b2d9b941190c34e3396e850b0f2ea6b2fb439b49976670382ca0734a105c','iOQMZtpA4wdLmV3GrwAp','mzb3','1'),('36ab99a97d344f3e8053b0fd5cc96f52','普通用户','fe2bf885edb9b8b8a5f929538943c6c9b6795c47f9605e6dbebf86d3083405ec','NjcHessKPhi2A1L7omZC','mzb5','1'),('3fdc347f78624422bd9f3db7fb14c107','普通用户','c28d6d2ca78658bfc9f9344edab9995694b359242e96a92c4ee76ef289da0b2b','O69MDaxnJCufZMgq5Jea','hljzhuzhang','1'),('441f8d7dde6e4a43bfe7720c85930365','普通用户','f7124f463cf72f96fbb8a7326cff626fada8f5570b38da50068c9869bb93a9bb','nfvW64pdrT3aML8jP2ew','hlj2','1'),('46c7019fc82a4db4869818f13744a444','普通用户','4dbab1655247f7ce5b25f56900d3427dac057016a3f6d80aecf1b522a3ad0eff','GHMd5ZK91ZlNomZ3FYgx','17876251111@163.com','1'),('4ed9ce2894d544cab63b6c28be889697','普通用户','ecdb789edba237ab4e0f3fdd49ea8c36d7fc04e8c3d9ba4ae8cd09cb575971ca','5dESfEa9LijJvuBNiICO','17876254444@163.com','1'),('507f9ccc4ff44a7985118edd6268c46d','发布者','a4f915a3a67354aaa893ccdb323834df29d67d33f9a9e5b82cab319057472544','jqvQDb886Ybc6w0gpKV6','fbz','1'),('61c0ccaed30846d4a6a6c0563efa64af','普通用户','4221132782570f5544703d369e335f264f053c2316c9b4339d8d3ab38d18d41c','5Zwkp4UXEs0iyYuBrP4H','782788572@qq.com','1'),('660c3cf9200b40698e1f00e5715deaf4','发布者','78f21a7750ef683835f25f30da083329a7842e081fe2b434db6a78bc63cc2821','rRsbwx5mTRkG3YDU3opS','lsf','1'),('66a75c8f9f5249769171a322a99884c5','普通用户','eaf7f93f670611a7a726366c9f158c0a212868ffcc265d0dd26b708a2d2adad8','gnGQ2gkAOz7md0jRT9pj','19','1'),('7006494107424a4ab6a2365907acc2d6','发布者','98a3e1fbd63db32665ee32699b220e7ab264ee854da156baa7a20499184de48b','yITOHm2WbPgh08fWhWWr','17876253446','1'),('7f628d5d-2265-49d4-b12d-d65b8f280901','管理员','a3c83e5ea590221aabb6db9c2565d4c419f87db2298305682111bb8b5960c0a9','UU5df0jPGdDYlnH0Tbvj','0','1'),('9aebf999ec9440d68150da597b13f9f7','普通用户','a3c83e5ea590221aabb6db9c2565d4c419f87db2298305682111bb8b5960c0a9','UU5df0jPGdDYlnH0Tbvj','18','1'),('9f3f34eae58247dfa84b8c8b85059f75','普通用户','b96c4c0cd71552bceb442c66910baa6a931df21ecacd1545e5c0ef28e447693a','RhZvYftoVc56yLC9OWSy','17876252222@163.com','1'),('af8cfc18-b84d-4825-a49c-e0f6cb527858','发布者','7e47cac7683a705cd5f0d6652801a4d4929e729c8c4ff7668cd3f066f81f9d36','PtO0LyrZfGL9WIJvCM0t','2','1'),('cbbb863ab2ef4c9683b35414f354aeed','发布者','4d67210ebd413cbe4c993d8bd26d8b01f8f47ef7c0e8dbb53ce9cd92d0e3c228','gmgiHgbK6Q6OlogUnFHA','17','1'),('d57b383e40a04c2bb3a851237453ae4c','普通用户','5183b5943c53f9888784ceebf5032172e8993518f868705b826f4f9eb5a0f545','LAGGmncl4ha2BgPeWYU1','734779953@qq.com','1'),('db76983ae221487dbcc0751f4744a57f','普通用户','6bb56150038471b78404d9a0d2f3246ac7744313d27d330b2374234d5dad9eda','kKTplr8JkcXChGqag831','670509577@qq.com','1'),('ded80c33c8624663960df94214129b8e','普通用户','fbab31b65ce9c596e129e1e5c4a7da453047607ab6b923759e8b1367b0b4ddfb','RmXEj6p3J0eCbvgyNDBE','hlj1','1'),('e1e703cfbe324d618ca507819c4ecda6','普通用户','cdbc6ec30b6770b141cf8ace9c1bc170ab6db44d1ef4cf681eb627ed308e01e8','pX1I9lOts1IP49Du60iL','duanyu','1'),('e9ae842a-e70f-49b8-a9b5-bee24a13c8bb','普通用户','2a051879401bfabac6e04348f99409007c805a4bc84b0e1bf7a19daad20f7a98','mT0GOujModvYhr6N4fAU','1','1');
/*!40000 ALTER TABLE `alluser` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bidding`
--

DROP TABLE IF EXISTS `bidding`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bidding` (
  `bid_id` int(15) NOT NULL AUTO_INCREMENT,
  `bid_time` datetime DEFAULT NULL,
  `bid_starttime` datetime DEFAULT NULL,
  `bid_endtime` datetime DEFAULT NULL,
  `bid_state` varchar(25) DEFAULT NULL,
  `bid_scheme` varchar(1000) DEFAULT NULL,
  `bid_cycle` varchar(25) DEFAULT NULL,
  `bid_money` varchar(25) DEFAULT NULL,
  `bid_phone` varchar(25) DEFAULT NULL,
  `bid_email` varchar(25) DEFAULT NULL,
  `bid_studio` varchar(255) NOT NULL,
  `bid_proj` int(15) NOT NULL,
  PRIMARY KEY (`bid_id`),
  KEY `stu_id2` (`bid_studio`),
  KEY `pj_id` (`bid_proj`),
  CONSTRAINT `pj_id` FOREIGN KEY (`bid_proj`) REFERENCES `project` (`proj_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `stu_id2` FOREIGN KEY (`bid_studio`) REFERENCES `studio` (`stu_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=232 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bidding`
--

LOCK TABLES `bidding` WRITE;
/*!40000 ALTER TABLE `bidding` DISABLE KEYS */;
INSERT INTO `bidding` VALUES (228,'2018-10-17 15:05:27',NULL,'2018-10-17 15:05:53','竞标中止','我们工作室很牛逼 ，交给我们','20','1000','17876253511','4619@qq.com','fd23cad5cc424e82a79679f128449de7',22),(229,'2018-10-17 16:05:11',NULL,'2018-10-17 16:07:30','竞标中止','我要竞标了','31','500','17876253511','4619@qq.com','9a322fa945164d3fa3d8bc5c37e77dc1',22),(230,'2018-10-17 16:06:36',NULL,NULL,'竞标结束','22222','44','900','17876253511','4619@qq.com','9a322fa945164d3fa3d8bc5c37e77dc1',24),(231,'2018-10-19 16:40:41',NULL,NULL,'竞标中','ffrvtvgrgrvtgbtgbtgtb','30','1500','17876253412','489280101@swdsd','1edbf67138364b50aa252b35d6d73ac2',115);
/*!40000 ALTER TABLE `bidding` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `collectori`
--

DROP TABLE IF EXISTS `collectori`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `collectori` (
  `colo_id` int(15) NOT NULL AUTO_INCREMENT,
  `colo_time` datetime NOT NULL,
  `colo_users` varchar(255) NOT NULL,
  `colo_ogi` int(15) NOT NULL,
  PRIMARY KEY (`colo_id`),
  KEY `u_id3` (`colo_users`),
  KEY `ori_id` (`colo_ogi`),
  CONSTRAINT `ori_id` FOREIGN KEY (`colo_ogi`) REFERENCES `original` (`orig_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `u_id3` FOREIGN KEY (`colo_users`) REFERENCES `users` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `collectori`
--

LOCK TABLES `collectori` WRITE;
/*!40000 ALTER TABLE `collectori` DISABLE KEYS */;
INSERT INTO `collectori` VALUES (22,'2018-10-14 18:16:48','e9ae842a-e70f-49b8-a9b5-bee24a13c8bb',122),(24,'2018-10-16 16:47:51','e9ae842a-e70f-49b8-a9b5-bee24a13c8bb',3),(25,'2018-10-16 16:49:52','e9ae842a-e70f-49b8-a9b5-bee24a13c8bb',1);
/*!40000 ALTER TABLE `collectori` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `collectpj`
--

DROP TABLE IF EXISTS `collectpj`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `collectpj` (
  `colp_id` int(15) NOT NULL AUTO_INCREMENT,
  `colp_time` datetime NOT NULL,
  `colp_user` varchar(255) NOT NULL,
  `colp_pjid` int(15) NOT NULL,
  PRIMARY KEY (`colp_id`),
  KEY `user_id7` (`colp_user`),
  KEY `pj_id3` (`colp_pjid`),
  CONSTRAINT `pj_id3` FOREIGN KEY (`colp_pjid`) REFERENCES `project` (`proj_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `user_id7` FOREIGN KEY (`colp_user`) REFERENCES `users` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `collectpj`
--

LOCK TABLES `collectpj` WRITE;
/*!40000 ALTER TABLE `collectpj` DISABLE KEYS */;
INSERT INTO `collectpj` VALUES (3,'2018-09-27 09:31:41','e9ae842a-e70f-49b8-a9b5-bee24a13c8bb',2),(11,'2018-09-28 16:30:20','e9ae842a-e70f-49b8-a9b5-bee24a13c8bb',4),(12,'2018-09-28 16:30:31','e9ae842a-e70f-49b8-a9b5-bee24a13c8bb',6),(17,'2018-10-11 16:47:57','e9ae842a-e70f-49b8-a9b5-bee24a13c8bb',17),(46,'2018-10-17 15:52:32','33d0023cceda4b7dbc6d403fa349ca0a',22);
/*!40000 ALTER TABLE `collectpj` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `collectres`
--

DROP TABLE IF EXISTS `collectres`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `collectres` (
  `colr_id` int(15) NOT NULL AUTO_INCREMENT,
  `colr_time` datetime NOT NULL,
  `colr_users` varchar(255) NOT NULL,
  `colr_res` int(15) NOT NULL,
  PRIMARY KEY (`colr_id`),
  KEY `u_id2` (`colr_users`),
  KEY `res_id` (`colr_res`),
  CONSTRAINT `res_id` FOREIGN KEY (`colr_res`) REFERENCES `resource` (`res_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `u_id2` FOREIGN KEY (`colr_users`) REFERENCES `users` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `collectres`
--

LOCK TABLES `collectres` WRITE;
/*!40000 ALTER TABLE `collectres` DISABLE KEYS */;
INSERT INTO `collectres` VALUES (1,'2018-09-27 17:08:42','e9ae842a-e70f-49b8-a9b5-bee24a13c8bb',1),(3,'2018-09-21 17:08:59','e9ae842a-e70f-49b8-a9b5-bee24a13c8bb',3),(7,'2018-10-01 13:35:20','15f52c274c5f4901add842445fa68a2e',1),(9,'2018-10-16 16:07:24','61c0ccaed30846d4a6a6c0563efa64af',1),(10,'2018-10-19 17:07:19','3fdc347f78624422bd9f3db7fb14c107',1);
/*!40000 ALTER TABLE `collectres` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `danmu`
--

DROP TABLE IF EXISTS `danmu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `danmu` (
  `dm_id` int(15) NOT NULL AUTO_INCREMENT,
  `dm_text` varchar(255) NOT NULL,
  `dm_color` varchar(25) NOT NULL,
  `dm_position` int(15) NOT NULL,
  `dm_time` int(15) NOT NULL,
  `dm_sendtime` datetime NOT NULL,
  `dm_resource` int(15) NOT NULL,
  PRIMARY KEY (`dm_id`),
  KEY `res_id2` (`dm_resource`),
  CONSTRAINT `res_id2` FOREIGN KEY (`dm_resource`) REFERENCES `resource` (`res_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `danmu`
--

LOCK TABLES `danmu` WRITE;
/*!40000 ALTER TABLE `danmu` DISABLE KEYS */;
INSERT INTO `danmu` VALUES (1,'我是组成弹幕','white',0,10,'2018-09-30 16:10:47',1),(2,'我是组成弹幕','white',0,20,'2018-09-30 16:10:47',1),(3,'我是组成弹幕','white',0,30,'2018-09-30 16:10:47',1),(4,'我是组成弹幕','white',0,40,'2018-09-30 16:10:47',1),(5,'我是组成弹幕','white',0,50,'2018-09-30 16:10:47',1),(6,'我是组成弹幕','white',0,60,'2018-09-30 16:10:47',1),(7,'我是组成弹幕','white',0,70,'2018-09-30 16:10:47',1),(8,'我是组成弹幕','white',0,80,'2018-09-30 16:10:47',1),(9,'我是组成弹幕','white',0,90,'2018-09-30 16:10:47',1),(10,'我是组成弹幕','white',0,100,'2018-09-30 16:10:48',1),(11,'我是组成弹幕','white',0,110,'2018-09-30 16:10:48',1),(12,'我是组成弹幕','white',0,120,'2018-09-30 16:10:48',1),(13,'我是组成弹幕','white',0,130,'2018-09-30 16:10:48',1),(14,'我是组成弹幕','white',0,140,'2018-09-30 16:10:48',1),(15,'我是组成弹幕','white',0,150,'2018-09-30 16:10:48',1),(16,'我是组成弹幕','white',0,160,'2018-09-30 16:10:48',1),(17,'我是组成弹幕','white',0,170,'2018-09-30 16:10:48',1),(18,'我是组成弹幕','white',0,180,'2018-09-30 16:10:48',1),(19,'我是组成弹幕','white',0,190,'2018-09-30 16:10:48',1),(20,'我是组成弹幕','white',0,200,'2018-09-30 16:10:48',1),(21,'测试弹幕测试弹幕测试弹幕测试弹幕测试弹幕测试弹幕','#ffffff',0,813,'2018-10-01 15:39:57',1),(22,'测试弹幕刷新','#ffffff',0,3,'2018-10-17 23:09:28',3),(23,'我是刷新弹幕','#ffffff',0,186,'2018-10-17 23:11:02',3),(24,'fdfs','#ffffff',0,82,'2018-10-20 22:01:53',15),(25,'gfhgf','#ffffff',0,105,'2018-10-20 22:01:59',15);
/*!40000 ALTER TABLE `danmu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `funds`
--

DROP TABLE IF EXISTS `funds`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `funds` (
  `fund_id` varchar(255) NOT NULL DEFAULT '',
  `fund_datetime` datetime NOT NULL,
  `fund_money` double(15,2) NOT NULL,
  `fund_type` varchar(25) NOT NULL,
  `fund_income` varchar(255) DEFAULT NULL,
  `fund_outlay` varchar(255) DEFAULT NULL,
  `fund_remark` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`fund_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `funds`
--

LOCK TABLES `funds` WRITE;
/*!40000 ALTER TABLE `funds` DISABLE KEYS */;
INSERT INTO `funds` VALUES ('032506417456495ab4479cc4e74090c0','2018-10-08 22:53:52',2082.70,'项目尾款','平台','af8cfc18-b84d-4825-a49c-e0f6cb527858',NULL),('05d5fcf6ab804660b8b080010b6e59e2','2018-10-09 10:11:42',-20.00,'提现','af8cfc18-b84d-4825-a49c-e0f6cb527858','支付宝',NULL),('067166a92e6346dda324c135aa6efdf6','2018-10-08 15:56:16',-1.20,'提现','af8cfc18-b84d-4825-a49c-e0f6cb527858','支付宝',NULL),('0854c768709c473b91f72f3fd4d8b604','2018-10-08 22:53:52',-2082.70,'项目尾款','af8cfc18-b84d-4825-a49c-e0f6cb527858','平台',NULL),('0c8e71351a1e440eae3ea325628bfa0a','2018-10-08 22:53:53',-2203.00,'项目款项','平台','e9ae842a-e70f-49b8-a9b5-bee24a13c8bb',NULL),('0fd3d99454954644a78355115cf8011e','2018-10-04 18:38:09',200.00,'充值','af8cfc18-b84d-4825-a49c-e0f6cb527858','支付宝',NULL),('1759a963b36a4df9ab623c53a9f5f0d1','2018-10-09 17:15:41',-20.00,'提现','e9ae842a-e70f-49b8-a9b5-bee24a13c8bb','支付宝',NULL),('18862503efd449549287323203765e8b','2018-10-05 17:17:25',232.00,'项目押金','平台','af8cfc18-b84d-4825-a49c-e0f6cb527858',NULL),('1f2ead56ad3c4e9e92d9325e77243657','2018-10-10 08:58:10',-100.00,'提现','e9ae842a-e70f-49b8-a9b5-bee24a13c8bb','支付宝',NULL),('1fa8f5a0a53d44879eb0d8a15332b607','2018-10-04 18:49:13',34.00,'充值','af8cfc18-b84d-4825-a49c-e0f6cb527858','支付宝',NULL),('238f7b5b676e4b93b7324b7ed6c90695','2018-10-04 22:41:52',-200.00,'提现','af8cfc18-b84d-4825-a49c-e0f6cb527858','支付宝',NULL),('28d8d33b365d4668984d21ee66783e8d','2018-10-10 09:00:01',-20.00,'提现','af8cfc18-b84d-4825-a49c-e0f6cb527858','支付宝',NULL),('2c119930c7a14eab98240010092fc87f','2018-10-09 10:04:20',-20.00,'提现','af8cfc18-b84d-4825-a49c-e0f6cb527858','支付宝',NULL),('2c5da1fb37ab45c59d0e45878496e775','2018-10-04 22:54:19',-21.00,'提现','af8cfc18-b84d-4825-a49c-e0f6cb527858','支付宝',NULL),('2e87399fbc224a01922f343aba565cf6','2018-10-10 09:58:14',5000.00,'充值','af8cfc18-b84d-4825-a49c-e0f6cb527858','支付宝',NULL),('3088cf70f9cd42d4a82bff9f8726b21d','2018-10-05 20:37:12',-2188.00,'项目尾款','af8cfc18-b84d-4825-a49c-e0f6cb527858','平台',NULL),('320ec4f2adbe42478d2ee1118406ed82','2018-10-08 22:46:23',-2082.70,'项目尾款','af8cfc18-b84d-4825-a49c-e0f6cb527858','平台',NULL),('34c5f1f9626e4eee97f5f1acacc400b3','2018-10-08 22:29:15',2082.70,'项目尾款','平台','af8cfc18-b84d-4825-a49c-e0f6cb527858',NULL),('36a58ef651ba4eceaad08e96046d8c25','2018-10-05 18:09:43',-232.00,'项目押金','af8cfc18-b84d-4825-a49c-e0f6cb527858','平台',NULL),('3db2d8960e23411eb71141425297da13','2018-10-04 18:47:15',23.00,'充值','af8cfc18-b84d-4825-a49c-e0f6cb527858','支付宝',NULL),('40397f35e60c4a5b81b2edc0b9dc3220','2018-10-09 10:09:18',-20.00,'提现','af8cfc18-b84d-4825-a49c-e0f6cb527858','支付宝',NULL),('42fd1eb12a3e44b2b5d65685a40824c9','2018-10-05 20:32:51',-232.00,'项目押金','af8cfc18-b84d-4825-a49c-e0f6cb527858','平台',NULL),('43c580e537e849a5b088e70f1298f296','2018-10-05 20:32:51',232.00,'项目押金','平台','af8cfc18-b84d-4825-a49c-e0f6cb527858',NULL),('5c68b0e4ef8c466eb16f652a5bf511dc','2018-10-04 22:44:17',-1.00,'提现','af8cfc18-b84d-4825-a49c-e0f6cb527858','支付宝',NULL),('5cccfd0d88fc402f808bda26e6a158e4','2018-10-09 17:12:05',-20.00,'提现','e9ae842a-e70f-49b8-a9b5-bee24a13c8bb','支付宝',NULL),('5cd04a2a706f4b3ebaeed1c4a75de6a3','2018-10-04 23:02:35',-1.00,'提现','af8cfc18-b84d-4825-a49c-e0f6cb527858','支付宝',NULL),('5de96bb3597340b89ca552f522ebd2c5','2018-10-05 20:37:12',-2320.00,'项目款项','平台','e9ae842a-e70f-49b8-a9b5-bee24a13c8bb',NULL),('5eed5ef588014722bc6b09aebccf186b','2018-10-09 17:12:47',-100.00,'提现','e9ae842a-e70f-49b8-a9b5-bee24a13c8bb','支付宝',NULL),('60c0f27330f541c3913587f662e781bc','2018-10-09 10:03:58',-1.20,'提现','af8cfc18-b84d-4825-a49c-e0f6cb527858','支付宝',NULL),('62dd5932aeda400db372ee5d8d7ebf0c','2018-10-05 18:09:43',232.00,'项目押金','平台','af8cfc18-b84d-4825-a49c-e0f6cb527858',NULL),('6abd8916f9374063a0ad7878c7deebc7','2018-10-04 23:04:02',-0.36,'提现','af8cfc18-b84d-4825-a49c-e0f6cb527858','支付宝',NULL),('6ac0bd97b9ba4be395bfd466c425ea03','2018-10-10 09:01:04',200.00,'充值','af8cfc18-b84d-4825-a49c-e0f6cb527858','支付宝',NULL),('6ad1811950874bd09da5f34adcb08a41','2018-10-08 22:29:15',-2082.70,'项目尾款','af8cfc18-b84d-4825-a49c-e0f6cb527858','平台',NULL),('6f4d821d0237476191b04b5d1c3ccd09','2018-10-05 18:01:01',232.00,'项目押金','平台','af8cfc18-b84d-4825-a49c-e0f6cb527858',NULL),('76352e22a2ef4826b3a35d9650fea81e','2018-10-17 16:52:42',1000.00,'充值','660c3cf9200b40698e1f00e5715deaf4','支付宝',NULL),('793af4e7ea2444b7b61f08f1ea53be23','2018-10-08 22:46:24',2203.00,'项目款项','e9ae842a-e70f-49b8-a9b5-bee24a13c8bb','平台',NULL),('7a0af97cd31a484280071fb2647a78e8','2018-10-05 20:37:12',2188.00,'项目尾款','平台','af8cfc18-b84d-4825-a49c-e0f6cb527858',NULL),('811359fc69ff4560a3b0212cbafaac2e','2018-10-09 17:23:54',-20.00,'提现','e9ae842a-e70f-49b8-a9b5-bee24a13c8bb','支付宝',NULL),('865c5f697ad346509c215ff95d8ab2e2','2018-10-05 18:27:36',132.00,'项目退款','af8cfc18-b84d-4825-a49c-e0f6cb527858','平台',NULL),('869f67c2483c4000b83590f3f5a008a1','2018-10-09 10:14:42',-20.00,'提现','af8cfc18-b84d-4825-a49c-e0f6cb527858','支付宝',NULL),('8affc2eff8af46a0ac29c72b6698b5ce','2018-10-08 22:52:12',2082.70,'项目尾款','平台','af8cfc18-b84d-4825-a49c-e0f6cb527858',NULL),('8b46c193f3924778afb5c3974eeab7d8','2018-10-05 18:27:36',-132.00,'项目退款','平台','af8cfc18-b84d-4825-a49c-e0f6cb527858',NULL),('8dbdb540b1834c8aa9e5e9332b6aac89','2018-10-09 10:14:48',-20.00,'提现','af8cfc18-b84d-4825-a49c-e0f6cb527858','支付宝',NULL),('8e9b305677ef4d7d97a13367c1773f41','2018-10-09 17:22:39',-100.00,'提现','e9ae842a-e70f-49b8-a9b5-bee24a13c8bb','支付宝',NULL),('90b19bd34c2f49b9a4534ef83b65f60d','2018-10-17 17:14:59',-900.00,'项目款项','平台','33d0023cceda4b7dbc6d403fa349ca0a',NULL),('9b29f5a7716348ed8f9d48d9e76f12ef','2018-10-08 22:52:12',2203.00,'项目款项','e9ae842a-e70f-49b8-a9b5-bee24a13c8bb','平台',NULL),('a515e724533f40abb5fe1e22cb07499f','2018-10-08 22:46:24',-2203.00,'项目款项','平台','e9ae842a-e70f-49b8-a9b5-bee24a13c8bb',NULL),('a719fc83c0c9496ca8fa0f0de76be531','2018-10-13 18:58:18',500.00,'充值','af8cfc18-b84d-4825-a49c-e0f6cb527858','支付宝',NULL),('ad735fb45804402a96c1ed1811b2896c','2018-10-09 10:15:29',-20.00,'提现','af8cfc18-b84d-4825-a49c-e0f6cb527858','支付宝',NULL),('ad897c43e5bd4328bb52e2cb4830211e','2018-10-08 22:46:24',2082.70,'项目尾款','平台','af8cfc18-b84d-4825-a49c-e0f6cb527858',NULL),('b4cbafbaf3394b27baf421a2ef9a4c3c','2018-10-05 17:17:25',-232.00,'项目押金','af8cfc18-b84d-4825-a49c-e0f6cb527858','平台',NULL),('b740392b145248f38c4d02ea233c2c02','2018-10-01 18:41:14',23.00,'充值','af8cfc18-b84d-4825-a49c-e0f6cb527858','支付宝',NULL),('b83a13dafedf4e27ab3e6742ae44e69d','2018-10-08 22:53:53',2203.00,'项目款项','e9ae842a-e70f-49b8-a9b5-bee24a13c8bb','平台',NULL),('ba02189b37144ec19441715a3b256e1a','2018-10-09 10:10:57',-50.10,'提现','af8cfc18-b84d-4825-a49c-e0f6cb527858','支付宝',NULL),('c59ee3ddeeac402c959d04d613fa52b6','2018-10-01 20:37:12',2320.00,'项目款项','e9ae842a-e70f-49b8-a9b5-bee24a13c8bb','平台',NULL),('c717132ca833490d9b222088fba70fe1','2018-10-17 17:14:58',-910.00,'项目尾款','660c3cf9200b40698e1f00e5715deaf4','平台',NULL),('c8cb0fb839fc48b8adb50b3d41fcfdca','2018-10-10 09:01:58',500.00,'充值','e9ae842a-e70f-49b8-a9b5-bee24a13c8bb','支付宝',NULL),('d6f22fbe92a5482b82f3e94436aa501c','2018-10-08 22:29:15',2203.00,'项目款项','e9ae842a-e70f-49b8-a9b5-bee24a13c8bb','平台',NULL),('d9024e70b24140fc8b5333d3356a465f','2018-10-17 17:14:58',910.00,'项目尾款','平台','660c3cf9200b40698e1f00e5715deaf4',NULL),('e23dcdf286674acba39f3301a632925d','2018-10-09 10:05:54',-100.00,'提现','af8cfc18-b84d-4825-a49c-e0f6cb527858','支付宝',NULL),('e5b468c602904596ba882b7943556eb2','2018-10-09 10:07:47',-20.00,'提现','af8cfc18-b84d-4825-a49c-e0f6cb527858','支付宝',NULL),('e8ec6a7206784695adda2fd3e714548c','2018-10-17 17:03:52',-90.00,'项目押金','660c3cf9200b40698e1f00e5715deaf4','平台',NULL),('ec39dabc5ec24f8bac113d558dff1d64','2018-10-17 17:03:52',90.00,'项目押金','平台','660c3cf9200b40698e1f00e5715deaf4',NULL),('eca111e4301a4fc9a2c6adf3e00e6b54','2018-10-05 18:01:01',-232.00,'项目押金','af8cfc18-b84d-4825-a49c-e0f6cb527858','平台',NULL),('ecc01306fe084876b3cf9bac69b8a300','2018-10-17 17:14:59',900.00,'项目款项','33d0023cceda4b7dbc6d403fa349ca0a','平台',NULL),('efc4b2aaabfa4b678a36535b7b0ee85f','2018-10-08 22:52:12',-2203.00,'项目款项','平台','e9ae842a-e70f-49b8-a9b5-bee24a13c8bb',NULL),('f1dd5f5fd617488f872a8b993ceb3b2e','2018-10-08 22:52:12',-2082.70,'项目尾款','af8cfc18-b84d-4825-a49c-e0f6cb527858','平台',NULL),('f612ee2d6f90477090d043c133384cc9','2018-10-08 22:29:15',-2203.00,'项目款项','平台','e9ae842a-e70f-49b8-a9b5-bee24a13c8bb',NULL),('f7ca9682740246088f00d5c6e45754ff','2018-10-09 17:22:28',-100.00,'提现','e9ae842a-e70f-49b8-a9b5-bee24a13c8bb','支付宝',NULL),('ff43ad46c9964509b21d1f162f804fd0','2018-10-09 17:19:47',-100.00,'提现','e9ae842a-e70f-49b8-a9b5-bee24a13c8bb','支付宝',NULL);
/*!40000 ALTER TABLE `funds` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gradeori`
--

DROP TABLE IF EXISTS `gradeori`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `gradeori` (
  `grao_id` int(15) NOT NULL AUTO_INCREMENT,
  `grao_user` varchar(255) NOT NULL,
  `grao_ori` int(15) NOT NULL,
  `garo_sco` int(15) NOT NULL,
  PRIMARY KEY (`grao_id`),
  KEY `u_id6` (`grao_user`),
  KEY `ori_id2` (`grao_ori`),
  CONSTRAINT `ori_id2` FOREIGN KEY (`grao_ori`) REFERENCES `original` (`orig_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `u_id6` FOREIGN KEY (`grao_user`) REFERENCES `users` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gradeori`
--

LOCK TABLES `gradeori` WRITE;
/*!40000 ALTER TABLE `gradeori` DISABLE KEYS */;
INSERT INTO `gradeori` VALUES (6,'e9ae842a-e70f-49b8-a9b5-bee24a13c8bb',1,3),(7,'e9ae842a-e70f-49b8-a9b5-bee24a13c8bb',7,3);
/*!40000 ALTER TABLE `gradeori` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `invite`
--

DROP TABLE IF EXISTS `invite`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `invite` (
  `inv_id` int(15) NOT NULL AUTO_INCREMENT,
  `inv_creattime` datetime NOT NULL,
  `inv_project` int(15) NOT NULL,
  `inv_studio` varchar(255) NOT NULL,
  `inv_prom` varchar(255) NOT NULL,
  PRIMARY KEY (`inv_id`),
  KEY `invit_project` (`inv_project`),
  KEY `invit_studio` (`inv_studio`),
  KEY `invit_prom` (`inv_prom`),
  CONSTRAINT `invit_project` FOREIGN KEY (`inv_project`) REFERENCES `project` (`proj_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `invit_prom` FOREIGN KEY (`inv_prom`) REFERENCES `promulgator` (`prom_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `invit_studio` FOREIGN KEY (`inv_studio`) REFERENCES `studio` (`stu_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `invite`
--

LOCK TABLES `invite` WRITE;
/*!40000 ALTER TABLE `invite` DISABLE KEYS */;
INSERT INTO `invite` VALUES (12,'2018-10-17 15:45:29',23,'fd23cad5cc424e82a79679f128449de7','af8cfc18-b84d-4825-a49c-e0f6cb527858'),(13,'2018-10-19 16:20:41',115,'1edbf67138364b50aa252b35d6d73ac2','af8cfc18-b84d-4825-a49c-e0f6cb527858'),(14,'2018-10-19 16:20:47',115,'9a322fa945164d3fa3d8bc5c37e77dc1','af8cfc18-b84d-4825-a49c-e0f6cb527858');
/*!40000 ALTER TABLE `invite` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `invitenotice`
--

DROP TABLE IF EXISTS `invitenotice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `invitenotice` (
  `inno_id` int(15) NOT NULL AUTO_INCREMENT,
  `inno_proid` int(15) NOT NULL,
  `inno_time` datetime NOT NULL,
  `inno_foreid` varchar(255) NOT NULL,
  `inno_state` varchar(25) NOT NULL DEFAULT '否',
  `inno_proname` varchar(255) NOT NULL,
  PRIMARY KEY (`inno_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `invitenotice`
--

LOCK TABLES `invitenotice` WRITE;
/*!40000 ALTER TABLE `invitenotice` DISABLE KEYS */;
/*!40000 ALTER TABLE `invitenotice` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `jobs`
--

DROP TABLE IF EXISTS `jobs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `jobs` (
  `job_id` int(15) NOT NULL AUTO_INCREMENT,
  `job_creattime` datetime NOT NULL,
  `job_money` varchar(25) NOT NULL,
  `job_intro` varchar(1000) NOT NULL,
  `job_require` varchar(1000) NOT NULL,
  `job_state` varchar(25) NOT NULL,
  `job_num` int(15) NOT NULL,
  `job_type` varchar(25) DEFAULT NULL,
  `job_name` varchar(45) NOT NULL,
  `job_studio` varchar(255) NOT NULL,
  PRIMARY KEY (`job_id`),
  KEY `stu_id1` (`job_studio`),
  CONSTRAINT `stu_id1` FOREIGN KEY (`job_studio`) REFERENCES `studio` (`stu_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=384 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `jobs`
--

LOCK TABLES `jobs` WRITE;
/*!40000 ALTER TABLE `jobs` DISABLE KEYS */;
INSERT INTO `jobs` VALUES (379,'2018-10-17 15:20:06','6k','1、负责后台系统的研发，及时解决项目涉及到的技术问题。 \r\n2、参与系统需求分析与设计，负责完成核心代码编写，接口规范制定\r\n3、参与后台服务性能效率优化','1、计算机相关专业毕业，有大数据高并发的处理经验，精通spring框架，阅读过spring源码，熟悉其事务机制 \r\n2、熟练应用Linux操作系统，熟悉GIT等版本管理软件\r\n3、掌握网站访问速度的各种优化方案,并提出优化方案更佳\r\n4、掌握Spring、SpringMVC、mybatis、Redis、JavaScript、CSS3、XML、AJAX等知识，能够灵活运用\r\n5、熟悉软件设计流程和软件工程规范，具备编写良好而规范的设计和技术文档的能力\r\n6、熟悉Oracle、sql server、mysql等大型数据库一种或多种，熟练编写SQL语句及sql优化\r\n7、对技术有激情，喜欢钻研，能快速接受和掌握新技术，有较强的独立、主动的学习能力，良好的沟通表达能力和团队协作能力。','招聘中',20,NULL,'java工程师初级','1edbf67138364b50aa252b35d6d73ac2'),(380,'2018-10-17 15:20:57','10k','1、负责后台系统的研发，及时解决项目涉及到的技术问题。 \r\n2、参与系统需求分析与设计，负责完成核心代码编写，接口规范制定\r\n3、参与后台服务性能效率优化','1、计算机相关专业毕业，有大数据高并发的处理经验，精通spring框架，阅读过spring源码，熟悉其事务机制 \r\n2、熟练应用Linux操作系统，熟悉GIT等版本管理软件\r\n3、掌握网站访问速度的各种优化方案,并提出优化方案更佳\r\n4、掌握Spring、SpringMVC、mybatis、Redis、JavaScript、CSS3、XML、AJAX等知识，能够灵活运用\r\n5、熟悉软件设计流程和软件工程规范，具备编写良好而规范的设计和技术文档的能力\r\n6、熟悉Oracle、sql server、mysql等大型数据库一种或多种，熟练编写SQL语句及sql优化\r\n7、对技术有激情，喜欢钻研，能快速接受和掌握新技术，有较强的独立、主动的学习能力，良好的沟通表达能力和团队协作能力。','招聘中',10,NULL,'java工程师中级','1edbf67138364b50aa252b35d6d73ac2'),(381,'2018-10-17 15:21:26','3k','1、负责后台系统的研发，及时解决项目涉及到的技术问题。 \r\n2、参与系统需求分析与设计，负责完成核心代码编写，接口规范制定\r\n3、参与后台服务性能效率优化','1、计算机相关专业毕业，有大数据高并发的处理经验，精通spring框架，阅读过spring源码，熟悉其事务机制 \r\n2、熟练应用Linux操作系统，熟悉GIT等版本管理软件\r\n3、掌握网站访问速度的各种优化方案,并提出优化方案更佳\r\n4、掌握Spring、SpringMVC、mybatis、Redis、JavaScript、CSS3、XML、AJAX等知识，能够灵活运用\r\n5、熟悉软件设计流程和软件工程规范，具备编写良好而规范的设计和技术文档的能力\r\n6、熟悉Oracle、sql server、mysql等大型数据库一种或多种，熟练编写SQL语句及sql优化\r\n7、对技术有激情，喜欢钻研，能快速接受和掌握新技术，有较强的独立、主动的学习能力，良好的沟通表达能力和团队协作能力。','招聘中',10,NULL,'java工程师实习','1edbf67138364b50aa252b35d6d73ac2'),(382,'2018-10-17 16:22:06','6k','1','1','招聘中',10,NULL,'123','9a322fa945164d3fa3d8bc5c37e77dc1'),(383,'2018-10-19 16:14:48','3k','负责公司网站前端及部分后台业务系统前端开发工作\r\n\r\n1、参与公司各项目中的Web前端功能设计、开发和实现；\r\n\r\n2、与后端开发人员配合，高质量完成网站前端开发工作。','1、计算机相关专业，本科及以上学历，3年以上专业前端开发经验；\r\n2、熟悉W3C标准，精通html，CSS，Javascript，对表现与数据分离，Web语义化等有深刻理解 ；\r\n\r\n3、熟悉前端性能优化，对常见漏洞有一定的理解和相关实践；\r\n4、对Nodejs在项目有相关实践，了解ES5、ES6；\r\n5、熟练掌握jQuery，Vue（至少一种）等主流框架，了解AMD/CMD规范；\r\n\r\n6、熟悉前端构建工具的使用Webpack，Grunt，Gulp，至少使用过其中一种。\r\n7、对前端领域的新知识保持关注和学习的热情，有良好的编程风格和文档习惯；\r\n8、能简单使用一些Linux命令优先。','招聘中',10,NULL,'前端工程师','1edbf67138364b50aa252b35d6d73ac2');
/*!40000 ALTER TABLE `jobs` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `jobuser`
--

DROP TABLE IF EXISTS `jobuser`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `jobuser` (
  `ju_id` int(15) NOT NULL AUTO_INCREMENT,
  `ju_time` datetime NOT NULL,
  `ju_file` varchar(255) NOT NULL,
  `ju_state` varchar(25) NOT NULL,
  `ju_users` varchar(255) NOT NULL,
  `ju_jobs` int(15) NOT NULL,
  PRIMARY KEY (`ju_id`),
  KEY `u_id4` (`ju_users`),
  KEY `job_id` (`ju_jobs`),
  CONSTRAINT `job_id` FOREIGN KEY (`ju_jobs`) REFERENCES `jobs` (`job_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `u_id4` FOREIGN KEY (`ju_users`) REFERENCES `users` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=70 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `jobuser`
--

LOCK TABLES `jobuser` WRITE;
/*!40000 ALTER TABLE `jobuser` DISABLE KEYS */;
INSERT INTO `jobuser` VALUES (43,'2018-10-17 16:32:48','QQ截图20171117212207.png','未通过','33d0023cceda4b7dbc6d403fa349ca0a',382),(65,'2018-10-19 17:08:56','面试工作室简历.docx','已通过','e1e703cfbe324d618ca507819c4ecda6',379),(68,'2018-10-20 21:02:01','af5164c7-e59c-4aab-99bd-58ed8369c52e.docx','审核中','d57b383e40a04c2bb3a851237453ae4c',379),(69,'2018-10-20 21:55:05','e2e99a1d-214f-4494-b201-d2b4789919e0.docx','审核中','d57b383e40a04c2bb3a851237453ae4c',379);
/*!40000 ALTER TABLE `jobuser` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `likeres`
--

DROP TABLE IF EXISTS `likeres`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `likeres` (
  `likr_id` int(15) NOT NULL AUTO_INCREMENT,
  `likr_user` varchar(255) NOT NULL,
  `likr_res` int(15) NOT NULL,
  PRIMARY KEY (`likr_id`),
  KEY `res_id3` (`likr_res`),
  KEY `u_id5_idx` (`likr_user`),
  CONSTRAINT `res_id3` FOREIGN KEY (`likr_res`) REFERENCES `resource` (`res_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `u_id5` FOREIGN KEY (`likr_user`) REFERENCES `alluser` (`all_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `likeres`
--

LOCK TABLES `likeres` WRITE;
/*!40000 ALTER TABLE `likeres` DISABLE KEYS */;
INSERT INTO `likeres` VALUES (1,'0a8e00e298f84927b64947220e55baef',1),(2,'0a8e00e298f84927b64947220e55baef',2),(3,'0a8e00e298f84927b64947220e55baef',3),(5,'db76983ae221487dbcc0751f4744a57f',1),(6,'db76983ae221487dbcc0751f4744a57f',2),(7,'db76983ae221487dbcc0751f4744a57f',3),(9,'db76983ae221487dbcc0751f4744a57f',5),(12,'7006494107424a4ab6a2365907acc2d6',1),(13,'15f52c274c5f4901add842445fa68a2e',1),(14,'e9ae842a-e70f-49b8-a9b5-bee24a13c8bb',3),(15,'61c0ccaed30846d4a6a6c0563efa64af',1),(16,'3fdc347f78624422bd9f3db7fb14c107',1);
/*!40000 ALTER TABLE `likeres` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `notice`
--

DROP TABLE IF EXISTS `notice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `notice` (
  `noti_id` int(15) NOT NULL AUTO_INCREMENT,
  `noti_msg` varchar(1000) NOT NULL,
  `noti_state` varchar(25) NOT NULL DEFAULT '否',
  `noti_time` datetime NOT NULL,
  `noti_foreid` varchar(255) NOT NULL,
  PRIMARY KEY (`noti_id`)
) ENGINE=InnoDB AUTO_INCREMENT=81 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notice`
--

LOCK TABLES `notice` WRITE;
/*!40000 ALTER TABLE `notice` DISABLE KEYS */;
INSERT INTO `notice` VALUES (5,'666','否','2018-09-29 11:49:00','3fdc347f78624422bd9f3db7fb14c107'),(6,'555','是','2018-09-06 11:49:46','e9ae842a-e70f-49b8-a9b5-bee24a13c8bb'),(7,'您好，您所发布的项目发的所发生的已在2018-09-28 16:12:35中止竞标,如需查看详细信息，请查看竞标项目相关功能','否','2018-09-28 16:12:35','af8cfc18-b84d-4825-a49c-e0f6cb527858'),(8,'您好，您所竞标的项目发的所发生的已在2018-09-28 16:12:35中止竞标,如需查看详细信息，请查看竞标项目相关功能','否','2018-09-28 16:12:35','cf20d9f3c7554dfdaa9a45a41494f2c4'),(9,'您好，您所竞标的项目发的所发生的已在2018-09-28 16:12:35中止竞标,如需查看详细信息，请查看竞标项目相关功能','否','2018-09-28 16:12:35','cf20d9f3c7554dfdaa9a45a41494f2c4'),(10,'您好，您所发布的项目发的所发生的已在2018-09-28 16:30:26中止竞标,如需查看详细信息，请查看竞标项目相关功能','否','2018-09-28 16:30:27','af8cfc18-b84d-4825-a49c-e0f6cb527858'),(11,'您好，您所竞标的项目发的所发生的已在2018-09-28 16:30:26中止竞标,如需查看详细信息，请查看竞标项目相关功能','否','2018-09-28 16:30:27','cf20d9f3c7554dfdaa9a45a41494f2c4'),(12,'您好，您所竞标的项目发的所发生的已在2018-09-28 16:30:26中止竞标,如需查看详细信息，请查看竞标项目相关功能','否','2018-09-28 16:30:27','cf20d9f3c7554dfdaa9a45a41494f2c4'),(13,'您好，您所发布的项目发的所发生的已在2018-09-28 16:33:03中止竞标,如需查看详细信息，请查看竞标项目相关功能','否','2018-09-28 16:33:03','af8cfc18-b84d-4825-a49c-e0f6cb527858'),(14,'您好，您所竞标的项目发的所发生的已在2018-09-28 16:33:03中止竞标,如需查看详细信息，请查看竞标项目相关功能','否','2018-09-28 16:33:03','cf20d9f3c7554dfdaa9a45a41494f2c4'),(15,'您好，您所竞标的项目发的所发生的已在2018-09-28 16:33:03中止竞标,如需查看详细信息，请查看竞标项目相关功能','否','2018-09-28 16:33:03','cf20d9f3c7554dfdaa9a45a41494f2c4'),(16,'您好，您所发布的项目魔兽官方平台人气协议软件已在2018-09-28 16:44:33中止竞标,如需查看详细信息，请查看竞标项目相关功能','否','2018-09-28 16:44:33','af8cfc18-b84d-4825-a49c-e0f6cb527858'),(17,'您好，您所竞标的项目魔兽官方平台人气协议软件已在2018-09-28 16:44:33中止竞标,如需查看详细信息，请查看竞标项目相关功能','否','2018-09-28 16:44:33','cf20d9f3c7554dfdaa9a45a41494f2c4'),(18,'您好，甲方发起的中止项目操作已在2018-09-29 09:27:11被甲方取消中止,项目范德萨范德萨发现可继续开发，如需查看详细信息，请查看项目管理相关功能','否','2018-09-29 09:27:11','cf20d9f3c7554dfdaa9a45a41494f2c4'),(19,'您好，您已取消项目中止操作，范德萨范德萨发已在2018-09-29 09:27:11取消中止,现可继续开发，如需查看详细信息，请查看项目管理相关功能','否','2018-09-29 09:27:11','af8cfc18-b84d-4825-a49c-e0f6cb527858'),(20,'您好，您正在开发的项目范德萨范德萨发已在2018-09-29 09:27:33被甲方发起项目中止,如需查看详细信息，请查看项目管理相关功能','否','2018-09-29 09:27:33','cf20d9f3c7554dfdaa9a45a41494f2c4'),(21,'您好，您正在开发的项目范德萨范德萨发已在2018-09-29 09:27:33发起项目中止,请等待乙方的确认，如需查看详细信息，请查看项目管理相关功能','否','2018-09-29 09:27:33','af8cfc18-b84d-4825-a49c-e0f6cb527858'),(22,'您好，甲方发起的中止项目操作已在2018-09-29 09:28:33被甲方取消中止,项目范德萨范德萨发现可继续开发，如需查看详细信息，请查看项目管理相关功能','否','2018-09-29 09:28:33','cf20d9f3c7554dfdaa9a45a41494f2c4'),(23,'您好，您已取消项目中止操作，范德萨范德萨发已在2018-09-29 09:28:33取消中止,现可继续开发，如需查看详细信息，请查看项目管理相关功能','否','2018-09-29 09:28:33','af8cfc18-b84d-4825-a49c-e0f6cb527858'),(24,'您好，您所发布的项目发的所发生的已在2018-09-29 10:37:41发布成功,如需查看详细信息，请查看项目管理相关功能','否','2018-09-29 10:37:42','af8cfc18-b84d-4825-a49c-e0f6cb527858'),(25,'您好，您正在开发的项目范德萨范德萨发已在2018-09-29 17:54:02被甲方发起项目中止,如需查看详细信息，请查看项目管理相关功能','否','2018-09-29 17:54:02','cf20d9f3c7554dfdaa9a45a41494f2c4'),(26,'您好，您正在开发的项目范德萨范德萨发已在2018-09-29 17:54:02发起项目中止,请等待乙方的确认，如需查看详细信息，请查看项目管理相关功能','否','2018-09-29 17:54:03','af8cfc18-b84d-4825-a49c-e0f6cb527858'),(27,'您好，甲方发起的中止项目操作已在2018-09-29 17:54:05被甲方取消中止,项目范德萨范德萨发现可继续开发，如需查看详细信息，请查看项目管理相关功能','否','2018-09-29 17:54:06','cf20d9f3c7554dfdaa9a45a41494f2c4'),(28,'您好，您已取消项目中止操作，范德萨范德萨发已在2018-09-29 17:54:05取消中止,现可继续开发，如需查看详细信息，请查看项目管理相关功能','否','2018-09-29 17:54:06','af8cfc18-b84d-4825-a49c-e0f6cb527858'),(29,'您好，您所发布的项目发的所发生的已在2018-10-01 16:47:05发布成功,如需查看详细信息，请查看项目管理相关功能','否','2018-10-01 16:47:06','af8cfc18-b84d-4825-a49c-e0f6cb527858'),(30,'您好，您所发布的项目发的所发生的已在2018-10-01 16:47:25中止竞标,如需查看详细信息，请查看竞标项目相关功能','否','2018-10-01 16:47:25','af8cfc18-b84d-4825-a49c-e0f6cb527858'),(34,'您好，您所发布的项目发的所发生的已在2018-10-01 17:18:32中止竞标,如需查看详细信息，请查看竞标项目相关功能','否','2018-10-01 17:18:33','af8cfc18-b84d-4825-a49c-e0f6cb527858'),(36,'您好，您所发布的项目发的所发生的已在2018-10-05 14:36:33中止竞标,如需查看详细信息，请查看竞标项目相关功能','否','2018-10-05 14:36:33','af8cfc18-b84d-4825-a49c-e0f6cb527858'),(37,'您好，您所发布的项目做一个简单的小程序已在2018-10-05 17:17:25选定承接方，已进入开发状态,如需查看详细信息，请查看项目相关功能','否','2018-10-05 17:17:26','af8cfc18-b84d-4825-a49c-e0f6cb527858'),(38,'您好，您所竞标的项目做一个简单的小程序已在2018-10-05 17:17:25被选定为承接方，已进入开发状态,如需查看详细信息，请查看项目相关功能','否','2018-10-05 17:17:26','13'),(39,'您好，您所发布的项目做一个简单的小程序已在2018-10-05 18:01:00选定承接方，已进入开发状态,如需查看详细信息，请查看项目相关功能','否','2018-10-05 18:01:01','af8cfc18-b84d-4825-a49c-e0f6cb527858'),(40,'您好，您所竞标的项目做一个简单的小程序已在2018-10-05 18:01:00被选定为承接方，已进入开发状态,如需查看详细信息，请查看项目相关功能','否','2018-10-05 18:01:01','13'),(41,'您好，您所发布的项目做一个简单的小程序已在2018-10-05 18:09:43选定承接方，已进入开发状态,如需查看详细信息，请查看项目相关功能','否','2018-10-05 18:09:43','af8cfc18-b84d-4825-a49c-e0f6cb527858'),(42,'您好，您所竞标的项目做一个简单的小程序已在2018-10-05 18:09:43被选定为承接方，已进入开发状态,如需查看详细信息，请查看项目相关功能','否','2018-10-05 18:09:43','13'),(43,'您好，您发起中止的项目做一个简单的小程序已在2018-10-05 18:27:35被甲方确认中止,如需查看详细信息，请查看项目管理相关功能','否','2018-10-05 18:27:35','13'),(44,'您好，您已确认乙方发起的项目中止操作，做一个简单的小程序已在2018-10-05 18:27:35中止,如需查看详细信息，请查看项目管理相关功能','否','2018-10-05 18:27:35','af8cfc18-b84d-4825-a49c-e0f6cb527858'),(45,'您好，您所发布的项目做一个简单的小程序已在2018-10-05 20:32:51选定承接方，已进入开发状态,如需查看详细信息，请查看项目相关功能','否','2018-10-05 20:32:52','af8cfc18-b84d-4825-a49c-e0f6cb527858'),(46,'您好，您所竞标的项目做一个简单的小程序已在2018-10-05 20:32:51被选定为承接方，已进入开发状态,如需查看详细信息，请查看项目相关功能','否','2018-10-05 20:32:52','13'),(47,'您好，您正在开发的项目做一个简单的小程序已在2018-10-05 20:37:11确定完成,如需查看详细信息，请查看项目相关功能','否','2018-10-05 20:37:12','af8cfc18-b84d-4825-a49c-e0f6cb527858'),(48,'您好，您正在开发的项目做一个简单的小程序已在2018-10-05 20:37:11被确定完成，相关资金已进入工作室创始人账户,如需查看详细信息，请查看项目相关功能','否','2018-10-05 20:37:12','13'),(50,'您好，您正在开发的项目做一个简单的小程序已在2018-10-08 22:29:14确定完成,如需查看详细信息，请查看项目相关功能','否','2018-10-08 22:29:15','af8cfc18-b84d-4825-a49c-e0f6cb527858'),(51,'您好，您正在开发的项目做一个简单的小程序已在2018-10-08 22:29:14被确定完成，相关资金已进入工作室创始人账户,如需查看详细信息，请查看项目相关功能','否','2018-10-08 22:29:15','13'),(52,'您好，您的工作室所开发的项目做一个简单的小程序已在2018-10-08 22:29:14被确定完成，相关资金已进入您的账户,如需查看详细信息，请查看项目相关功能','是','2018-10-08 22:29:15','e9ae842a-e70f-49b8-a9b5-bee24a13c8bb'),(53,'您好，您正在开发的项目做一个简单的小程序已在2018-10-08 22:46:23确定完成,如需查看详细信息，请查看项目相关功能','否','2018-10-08 22:46:24','af8cfc18-b84d-4825-a49c-e0f6cb527858'),(54,'您好，您正在开发的项目做一个简单的小程序已在2018-10-08 22:46:23被确定完成，相关资金已进入工作室创始人账户,如需查看详细信息，请查看项目相关功能','否','2018-10-08 22:46:24','13'),(55,'您好，您的工作室所开发的项目做一个简单的小程序已在2018-10-08 22:46:23被确定完成，相关资金已进入您的账户,如需查看详细信息，请查看项目相关功能','是','2018-10-08 22:46:24','e9ae842a-e70f-49b8-a9b5-bee24a13c8bb'),(56,'您好，您正在开发的项目做一个简单的小程序已在2018-10-08 22:52:12确定完成,如需查看详细信息，请查看项目相关功能','否','2018-10-08 22:52:12','af8cfc18-b84d-4825-a49c-e0f6cb527858'),(57,'您好，您正在开发的项目做一个简单的小程序已在2018-10-08 22:52:12被确定完成，相关资金已进入工作室创始人账户,如需查看详细信息，请查看项目相关功能','否','2018-10-08 22:52:12','13'),(60,'您好，您正在开发的项目做一个简单的小程序已在2018-10-08 22:53:52被确定完成，相关资金已进入工作室创始人账户,如需查看详细信息，请查看项目相关功能','否','2018-10-08 22:53:53','13'),(62,'您好，您所发布的项目发的所发生的已在2018-10-10 09:55:19发布成功,如需查看详细信息，请查看项目管理相关功能','否','2018-10-10 09:55:19','af8cfc18-b84d-4825-a49c-e0f6cb527858'),(63,'您好，您所发布的项目发的所发生的已在2018-10-10 09:55:49中止竞标,如需查看详细信息，请查看竞标项目相关功能','否','2018-10-10 09:55:50','af8cfc18-b84d-4825-a49c-e0f6cb527858'),(64,'您好，您正在开发的项目做一个简单的小程序已在2018-10-10 09:56:58被甲方发起项目中止,如需查看详细信息，请查看项目管理相关功能','否','2018-10-10 09:56:59','13'),(65,'您好，您正在开发的项目做一个简单的小程序已在2018-10-10 09:56:58发起项目中止,请等待乙方的确认，如需查看详细信息，请查看项目管理相关功能','否','2018-10-10 09:56:59','af8cfc18-b84d-4825-a49c-e0f6cb527858'),(66,'您好，甲方发起的中止项目操作已在2018-10-10 09:57:01被甲方取消中止,项目做一个简单的小程序现可继续开发，如需查看详细信息，请查看项目管理相关功能','否','2018-10-10 09:57:02','13'),(67,'您好，您已取消项目中止操作，做一个简单的小程序已在2018-10-10 09:57:01取消中止,现可继续开发，如需查看详细信息，请查看项目管理相关功能','否','2018-10-10 09:57:02','af8cfc18-b84d-4825-a49c-e0f6cb527858'),(68,'您好，您所发布的项目创客平台已在2018-10-15 10:01:33发布成功,如需查看详细信息，请查看项目管理相关功能','否','2018-10-15 10:01:34','507f9ccc4ff44a7985118edd6268c46d'),(69,'您好，您所发布的项目创客平台1已在2018-10-15 10:17:23发布成功,如需查看详细信息，请查看项目管理相关功能','否','2018-10-15 10:17:23','507f9ccc4ff44a7985118edd6268c46d'),(70,'您好，您所发布的项目夏伟豪很伟大已在2018-10-16 15:59:49发布成功,如需查看详细信息，请查看项目管理相关功能','否','2018-10-16 15:59:49','507f9ccc4ff44a7985118edd6268c46d'),(71,'您好，您所发布的项目协同办公管理系统已在2018-10-17 15:33:35发布成功,如需查看详细信息，请查看项目管理相关功能','否','2018-10-17 15:33:39','af8cfc18-b84d-4825-a49c-e0f6cb527858'),(72,'您好，您所发布的项目夏伟豪你给我记住已在2018-10-17 15:50:15发布成功,如需查看详细信息，请查看项目管理相关功能','否','2018-10-17 15:50:15','660c3cf9200b40698e1f00e5715deaf4'),(73,'您好，您所发布的项目夏伟豪你给我记住已在2018-10-17 17:03:51选定承接方，已进入开发状态,如需查看详细信息，请查看项目相关功能','否','2018-10-17 17:03:52','660c3cf9200b40698e1f00e5715deaf4'),(74,'您好，您所竞标的项目夏伟豪你给我记住已在2018-10-17 17:03:51被选定为承接方，已进入开发状态,如需查看详细信息，请查看项目相关功能','否','2018-10-17 17:03:52','9a322fa945164d3fa3d8bc5c37e77dc1'),(75,'您好，您正在开发的项目夏伟豪你给我记住已在2018-10-17 17:14:58确定完成,如需查看详细信息，请查看项目相关功能','否','2018-10-17 17:14:59','660c3cf9200b40698e1f00e5715deaf4'),(76,'您好，您正在开发的项目夏伟豪你给我记住已在2018-10-17 17:14:58被确定完成，相关资金已进入工作室创始人账户,如需查看详细信息，请查看项目相关功能','否','2018-10-17 17:14:59','9a322fa945164d3fa3d8bc5c37e77dc1'),(77,'您好，您的工作室所开发的项目夏伟豪你给我记住已在2018-10-17 17:14:58被确定完成，相关资金已进入您的账户,如需查看详细信息，请查看项目相关功能','是','2018-10-17 17:14:59','33d0023cceda4b7dbc6d403fa349ca0a'),(78,'您好，您所发布的项目滴滴打人已在2018-10-19 16:20:24发布成功,如需查看详细信息，请查看项目管理相关功能','否','2018-10-19 16:20:24','af8cfc18-b84d-4825-a49c-e0f6cb527858'),(79,'您好，您所发布的项目美团外卖已在2018-10-19 16:52:30发布成功,如需查看详细信息，请查看项目管理相关功能','否','2018-10-19 16:52:31','af8cfc18-b84d-4825-a49c-e0f6cb527858'),(80,'您好，您所发布的项目微信小程序已在2018-10-19 17:06:52发布成功,如需查看详细信息，请查看项目管理相关功能','否','2018-10-19 17:06:53','2cbf39eab72d40a4bc1d5aba8b83430e');
/*!40000 ALTER TABLE `notice` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `original`
--

DROP TABLE IF EXISTS `original`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `original` (
  `orig_id` int(15) NOT NULL AUTO_INCREMENT,
  `orig_name` varchar(255) NOT NULL,
  `orig_intro` varchar(1000) NOT NULL,
  `orig_type` varchar(25) NOT NULL,
  `orig_tag` varchar(255) NOT NULL,
  `orig_uploadtime` datetime NOT NULL,
  `orig_path` varchar(255) NOT NULL,
  `orig_grade` double(15,0) NOT NULL,
  `orig_users` varchar(255) NOT NULL,
  PRIMARY KEY (`orig_id`),
  KEY `u_id` (`orig_users`),
  CONSTRAINT `u_id` FOREIGN KEY (`orig_users`) REFERENCES `users` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=127 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `original`
--

LOCK TABLES `original` WRITE;
/*!40000 ALTER TABLE `original` DISABLE KEYS */;
INSERT INTO `original` VALUES (1,'人人开源项目','基于Spring+SpringMVC+Mybatis分布式敏捷开发系统架构，提供整套公共微服务服务模块：集中权限管理（单点登录）、内容管理、支付中心、用户管理（支持第三方登录）、微信平台、存储系统、配置中心、日志分析、任务和通知等，支持服务治理、监控和追踪，努力为中小型企业打造全方位J2EE企业级开发解决方案。','Java','WEB,开发框架','2008-09-28 11:32:10','',3,'e9ae842a-e70f-49b8-a9b5-bee24a13c8bb'),(3,'测试','测试数据2','C','测试,算法','2018-09-28 11:32:10','',5,'e9ae842a-e70f-49b8-a9b5-bee24a13c8bb'),(7,'测试444','66666666666666666666666666666666666666666666666666666666666666666666666666','c++','开发软件','2018-09-28 11:32:10','',3,'db76983ae221487dbcc0751f4744a57f'),(11,'人人开源项目','测试数据','Java','WEB,开发框架','2018-09-28 11:32:10','',5,'db76983ae221487dbcc0751f4744a57f'),(12,'算法','基于Spring+SpringMVC+Mybatis分布式敏捷开发系统架构，提供整套公共微服务服务模块：集中权限管理（单点登录）、内容管理、支付中心、用户管理（支持第三方登录）、微信平台、存储系统、配置中心、日志分析、任务和通知等，支持服务治理、监控和追踪，努力为中小型企业打造全方位J2EE企业级开发解决方案。','Java','WEB,java,开发框架','2018-09-28 11:32:10','',3,'9f3f34eae58247dfa84b8c8b85059f75'),(31,'WEB人人开源项目3','基于Spring+SpringMVC+Mybatis分布式敏捷开发系统架构，提供整套公共微服务服务模块：集中权限管理（单点登录）、内容管理、支付中心、用户管理（支持第三方登录）、微信平台、存储系统、配置中心、日志分析、任务和通知等，支持服务治理、监控和追踪，努力为中小型企业打造全方位J2EE企业级开发解决方案。','Java','WEB,开发框架','2018-09-28 11:32:10','',2,'0a8e00e298f84927b64947220e55baef'),(51,'测试','基于Spring+SpringMVC+Mybatis分布式敏捷开发系统架构，提供整套公共微服务服务模块：集中权限管理（单点登录）、内容管理、支付中心、用户管理（支持第三方登录）、微信平台、存储系统、配置中心、日志分析、任务和通知等，支持服务治理、监控和追踪，努力为中小型企业打造全方位J2EE企业级开发解决方案。','Java','WEB,开发框架','2018-09-28 11:32:10','',1,'db76983ae221487dbcc0751f4744a57f'),(111,'人人开源项目4','基于Spring+SpringMVC+Mybatis分布式敏捷开发系统架构，提供整套公共微服务服务模块：集中权限管理（单点登录）、内容管理、支付中心、用户管理（支持第三方登录）、微信平台、存储系统、配置中心、日志分析、任务和通知等，支持服务治理、监控和追踪，努力为中小型企业打造全方位J2EE企业级开发解决方案。','Java','WEB,开发框架','2018-09-28 11:32:10','',4,'ded80c33c8624663960df94214129b8e'),(122,'开发框架','基于Spring+SpringMVC+Mybatis分布式敏捷开发系统架构，提供整套公共微服务服务模块：集中权限管理（单点登录）、内容管理、支付中心、用户管理（支持第三方登录）、微信平台、存储系统、配置中心、日志分析、任务和通知等，支持服务治理、监控和追踪，努力为中小型企业打造全方位J2EE企业级开发解决方案。','Java','WEB,开发框架','2018-09-28 11:32:10','',2,'db76983ae221487dbcc0751f4744a57f'),(123,'人人开源项目4','基于Spring+SpringMVC+Mybatis分布式敏捷开发系统架构，提供整套公共微服务服务模块：集中权限管理（单点登录）、内容管理、支付中心、用户管理（支持第三方登录）、微信平台、存储系统、配置中心、日志分析、任务和通知等，支持服务治理、监控和追踪，努力为中小型企业打造全方位J2EE企业级开发解决方案。','Java','WEB,开发框架','2018-09-28 11:32:10','',5,'ded80c33c8624663960df94214129b8e'),(124,'刺客信条奥德赛免安装版','3DM出品，必属精品','网站开发','Java','2018-10-11 19:16:09','',3,'e9ae842a-e70f-49b8-a9b5-bee24a13c8bb'),(125,'123开发框架','66','桌面应用','66','2018-10-11 19:17:58','ce020397-f844-4c1d-b065-88a54164823f.jpg',0,'db76983ae221487dbcc0751f4744a57f'),(126,'测试125','66','Java','JAVA','2018-10-17 16:06:31','SASASA',5,'173d1db82f0948288790c57855b69b4b');
/*!40000 ALTER TABLE `original` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `permission`
--

DROP TABLE IF EXISTS `permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `permission` (
  `per_id` int(15) NOT NULL AUTO_INCREMENT,
  `per_url` varchar(255) DEFAULT NULL,
  `per_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`per_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `permission`
--

LOCK TABLES `permission` WRITE;
/*!40000 ALTER TABLE `permission` DISABLE KEYS */;
INSERT INTO `permission` VALUES (1,'/user','user:user'),(2,'/user/add','user:add'),(3,'/user/delete','user:delete'),(4,'/users','users:usercenter');
/*!40000 ALTER TABLE `permission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `project`
--

DROP TABLE IF EXISTS `project`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `project` (
  `proj_id` int(15) NOT NULL AUTO_INCREMENT,
  `proj_name` varchar(255) NOT NULL,
  `proj_secret` varchar(25) NOT NULL,
  `proj_type` varchar(25) NOT NULL,
  `proj_money` varchar(15) NOT NULL,
  `proj_creattime` datetime NOT NULL,
  `proj_starttime` datetime DEFAULT NULL,
  `proj_endtime` datetime DEFAULT NULL,
  `proj_cycletime` int(15) NOT NULL,
  `proj_intro` varchar(1000) NOT NULL,
  `proj_file` varchar(255) DEFAULT NULL,
  `proj_filename` varchar(255) DEFAULT NULL,
  `proj_state` varchar(25) NOT NULL,
  `proj_tag` varchar(255) DEFAULT NULL,
  `proj_img` varchar(255) NOT NULL,
  `proj_studio` varchar(255) DEFAULT NULL,
  `proj_prom` varchar(255) NOT NULL,
  PRIMARY KEY (`proj_id`),
  KEY `pro_id` (`proj_prom`),
  KEY `stu_id` (`proj_studio`),
  CONSTRAINT `pro_id` FOREIGN KEY (`proj_prom`) REFERENCES `promulgator` (`prom_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `stu_id` FOREIGN KEY (`proj_studio`) REFERENCES `studio` (`stu_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=118 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `project`
--

LOCK TABLES `project` WRITE;
/*!40000 ALTER TABLE `project` DISABLE KEYS */;
INSERT INTO `project` VALUES (1,'协同办公管理系统','公开','网站开发','面议','2018-10-17 15:33:35',NULL,NULL,60,'协同办公管理系统是以知识管理为核心，协同运作为进化手段，使企业的资源融会贯通，吐故纳新，始终以崭新的形象生机勃勃地面对多变的外界环境的管理软件平台。平台至少包括公文管理、事务管理、会议中心、新闻中心、资料中心、互动中心、查询中心、系统管理和网站管理等九个主要子系统和二次开发平台的协同办公平台。','E:/ck/project/file/1537858971186.jpg','','竞标中','Java,J2EE,HTML,JavaScript,CSS,','/static/img/img_xwh/wechat.png',NULL,'af8cfc18-b84d-4825-a49c-e0f6cb527858'),(2,'二维地图 gis系统 开发 （arcgis）','公开','网站开发','面议','2018-10-14 08:33:11','2018-10-15 08:46:12','2018-09-26 09:46:24',30,'二维地图开发，增加功能，需要懂地理信息，精通arcgis和C#，需求个人合作，价格可谈，长期合作。\r\n\r\n有参考系统及代码，需要有能力、有时间、有诚信。','E:/ck/project/file/1537858971186.jpg','1537858971186.jpg','竞标超时','JavaScript,HTML,Java,J2EE,CSS,','E:/ChuangKeFile/Project/file/wechat.png',NULL,'7006494107424a4ab6a2365907acc2d6'),(4,'联网对战桌球游戏','公开','网站开发','面议','2018-10-13 08:33:11','2018-09-27 15:06:17',NULL,30,'仿照腾讯桌球的玩法<br>主要就是联网对战。先只做8球规则。\r\n使用技术最好是客户端使用h5，后端使用java(我自己可以开服后端)，希望能接入实时语音聊天功能\r\n详细的可以详聊。qq:758757132','E:/ck/project/file/1537858971186.jpg','1537858971186.jpg','竞标中止','JavaScript,HTML,Java,J2EE,CSS,','/static/img/img_xwh/wechat.png',NULL,'af8cfc18-b84d-4825-a49c-e0f6cb527858'),(6,'用电管理PC后台管理系统项目外包（驻场开发）','公开','网站开发','1123','2018-10-13 03:02:24','2018-09-24 15:06:23',NULL,32,'1、已知一组号码和其校验码，求根据号码生成校验码的算法。\r\n2、用于测试的数据见附件，文件中第1列为号码，第2列为校验码，每一行的校验码是根据相应的号码经过某种算法计算而来的。\r\n3、由数据生成校验码的算法可能是奇偶、海明、CRC、MD5等算法，也可能是其它算法。存在着校验码不是由号码经过计算得来的情况，也就是说号码与校验码可能根本无关，但这种可能性极低。\r\n4、需要提供源码，源码最好是java的，也可以是c/c++。\r\n5、接包方找出算法之后联系我，我再提供一些号码，接包方算出对应的校验码，验证无误之后我方可以支付50%的项目款。接包方提供源码并经我方验证无误后，支付另外的50%的项目款。\r\n6、希望经验丰富的大神承接项目，非诚勿扰！','E:/ck/project/file/1537858971186.jpg',NULL,'竞标超时','IOS,ipad,Windows,Visual Basic,','/static/img/img_xwh/wechat.png',NULL,'af8cfc18-b84d-4825-a49c-e0f6cb527858'),(14,'虚拟币对冲','公开','桌面应用','面议','2018-09-16 10:37:41','2018-09-06 10:58:37',NULL,344,'想做一个虚币对冲网站，手机电脑都能用，，能自动对冲的，跟比特币精灵的对冲差不多，能够手机使用软件简单易懂就行','E:/ck/project/file/1537858971186.jpg','a1.jpg','竞标超时','IOS,ipad,Windows,Linux,','/static/img/img_xwh/wechat.png',NULL,'af8cfc18-b84d-4825-a49c-e0f6cb527858'),(15,'在线报名系统123','公开','网站开发','面议','2018-10-11 16:47:05',NULL,NULL,2342,'包括用户端与管理端。用户端能够实现在线报名、在线缴费以及查看通知等功能；管理端主要查看报名信息、管理报名信息以及发布通知等主要功能。','E:/ck/project/file/1537858971186.jpg',NULL,'竞标超时','Android,IOS,ipad,','1.jpg',NULL,'af8cfc18-b84d-4825-a49c-e0f6cb527858'),(16,'网站抢单软件','公开','APPUI','345','2018-10-11 16:50:01',NULL,NULL,56,'需要找人做个指定网站自动抢单的脚本插件软件。牛逼的人请来，可根据难度商量价格','E:/ck/project/file/1537858971186.jpg',NULL,'竞标超时','IOS,ipad,Windows,HTML,','/static/img/img_xwh/wechat.png',NULL,'af8cfc18-b84d-4825-a49c-e0f6cb527858'),(17,'酒吧霸屏系统','公开','网站开发','345','2018-10-14 18:36:17','2018-10-17 16:23:15',NULL,23,'仿微瞄酒吧互动的酒吧霸屏系统，要求使用java语言+vue 开发\r\n工期2个月\r\n报价5-15W','E:/ck/project/file/1537858971186.jpg',NULL,'开发中','IOS,ipad,Windows,,HTML,','/static/img/img_xwh/wechat.png','958d73b1ef3f483687baf4bb16ad954b','af8cfc18-b84d-4825-a49c-e0f6cb527858'),(18,'闸机伴侣的APP开发','公开','网站开发','345','2018-10-10 09:55:19',NULL,NULL,89,'1，闸机伴侣，是一个设备的称呼，具体配置在附件中。这个设备是安卓系统，要求开发个APP\r\n2，功能有：调用百度的人脸识别技术，识别到的人脸和WEB数据里的保存的人脸进行比对，比对通过，通过通讯协议通知闸机里的继电机开闸或关闸。APP的UI界面我们设计好，WEB系统我们也开发好，需要做好和WEB系统的调试。\r\n3，人员要求：精通安卓、接口开发。\r\n3，验收标准，能实时保持交流，按时交付，无BUG，提供项目源码','E:/ck/project/file/1537858971186.jpg',NULL,'竞标中止','IOS,ipad,Windows,Linux,','/static/img/img_xwh/wechat.png',NULL,'af8cfc18-b84d-4825-a49c-e0f6cb527858'),(19,'智慧社区','公开','网站开发','面议','2018-10-14 10:01:33','2018-10-16 10:45:21',NULL,20,'使用.net开发，需要对接第三方门禁和梯禁系统。软件部分包括社区app，物业后台管理，物业人员app，商家管理后台。主体功能有手机视频对讲/手机开门，多店铺商城系统，抢单模式家政系统，缴费系统等','E:/ck/project/file/1537858971186.jpg',NULL,'开发中','Android,IOS,ipad,Windows,','/static/img/img_xwh/wechat.png','958d73b1ef3f483687baf4bb16ad954b','507f9ccc4ff44a7985118edd6268c46d'),(20,'domino长期开发','公开','网站开发','2000','2018-10-12 10:17:23',NULL,NULL,10,'\r\n虽然没有技术难度但是我们希望是domino开发方面的高手','E:/ck/project/file/1537858971186.jpg',NULL,'开发完成','Android,IOS,ipad,,HTML,','/static/img/img_xwh/wechat.png','9a7d995828494df48f7af5479e013dd0','507f9ccc4ff44a7985118edd6268c46d'),(21,'智慧社区2','公开','网站开发','面议','2018-10-14 10:01:33','2018-10-16 09:35:50','2018-10-17 09:35:57',20,'使用.net开发，需要对接第三方门禁和梯禁系统。软件部分包括社区app，物业后台管理，物业人员app，商家管理后台。主体功能有手机视频对讲/手机开门，多店铺商城系统，抢单模式家政系统，缴费系统等','E:/ck/project/file/1537858971186.jpg',NULL,'开发中','Android,IOS,ipad,Windows,','/static/img/img_xwh/wechat.png','1edbf67138364b50aa252b35d6d73ac2','507f9ccc4ff44a7985118edd6268c46d'),(22,'夏伟豪很伟大','公开','网站开发','2000','2018-10-16 15:59:49',NULL,NULL,20,'123213213213213123\r\n123123123123312312312312\r\n3123123123123\r\n21321312321123','E:/ck/project/file/1537858971186.jpg',NULL,'竞标中','ipad,IOS,Visual C++,','/static/img/img_xwh/wechat.png',NULL,'507f9ccc4ff44a7985118edd6268c46d'),(23,'协同办公管理系统','公开','网站开发','面议','2018-10-17 15:33:35',NULL,NULL,60,'协同办公管理系统是以知识管理为核心，协同运作为进化手段，使企业的资源融会贯通，吐故纳新，始终以崭新的形象生机勃勃地面对多变的外界环境的管理软件平台。平台至少包括公文管理、事务管理、会议中心、新闻中心、资料中心、互动中心、查询中心、系统管理和网站管理等九个主要子系统和二次开发平台的协同办公平台。','E:/ck/project/file/1537858971186.jpg',NULL,'竞标中','Java,J2EE,HTML,JavaScript,CSS,','/static/img/img_xwh/wechat.png',NULL,'af8cfc18-b84d-4825-a49c-e0f6cb527858'),(24,'夏伟豪你给我记住','公开','APPUI','900','2018-10-17 15:50:15','2018-10-17 17:03:51','2018-10-17 17:14:58',44,'斯蒂芬斯蒂芬斯蒂芬斯蒂芬斯蒂芬斯蒂芬斯蒂芬斯蒂芬斯蒂芬斯蒂芬斯蒂芬斯蒂芬斯蒂芬斯蒂芬斯蒂芬斯蒂芬斯蒂芬斯蒂芬斯蒂芬森防守对方','E:/ck/project/file/1537858971186.jpg','160-xiangmu.jpg','开发完成','IOS,.NET,','/static/img/img_xwh/wechat.png','9a322fa945164d3fa3d8bc5c37e77dc1','660c3cf9200b40698e1f00e5715deaf4'),(101,'二维地图 gis系统 开发 （arcgis）','公开','网站开发','面议','2018-10-14 08:33:11','2018-10-15 08:46:12','2018-09-26 09:46:24',30,'二维地图开发，增加功能，需要懂地理信息，精通arcgis和C#，需求个人合作，价格可谈，长期合作。\r\n\r\n有参考系统及代码，需要有能力、有时间、有诚信。','E:/ck/project/file/1537858971186.jpg','1537858971186.jpg','竞标超时','JavaScript,HTML,Java,J2EE,CSS,','/static/img/img_xwh/wechat.png',NULL,'7006494107424a4ab6a2365907acc2d6'),(102,'联网对战桌球游戏','公开','网站开发','面议','2018-10-13 08:33:11','2018-09-27 15:06:17','2018-10-19 15:38:04',30,'仿照腾讯桌球的玩法<br>主要就是联网对战。先只做8球规则。\r\n使用技术最好是客户端使用h5，后端使用java(我自己可以开服后端)，希望能接入实时语音聊天功能\r\n详细的可以详聊。qq:758757132','E:/ck/project/file/1537858971186.jpg','1537858971186.jpg','竞标中止','JavaScript,HTML,Java,J2EE,CSS,','/static/img/img_xwh/wechat.png',NULL,'af8cfc18-b84d-4825-a49c-e0f6cb527858'),(103,'用电管理PC后台管理系统项目外包（驻场开发）','公开','网站开发','1123','2018-10-13 03:02:24','2018-09-24 15:06:23','2018-10-19 15:38:07',32,'1、已知一组号码和其校验码，求根据号码生成校验码的算法。\r\n2、用于测试的数据见附件，文件中第1列为号码，第2列为校验码，每一行的校验码是根据相应的号码经过某种算法计算而来的。\r\n3、由数据生成校验码的算法可能是奇偶、海明、CRC、MD5等算法，也可能是其它算法。存在着校验码不是由号码经过计算得来的情况，也就是说号码与校验码可能根本无关，但这种可能性极低。\r\n4、需要提供源码，源码最好是java的，也可以是c/c++。\r\n5、接包方找出算法之后联系我，我再提供一些号码，接包方算出对应的校验码，验证无误之后我方可以支付50%的项目款。接包方提供源码并经我方验证无误后，支付另外的50%的项目款。\r\n6、希望经验丰富的大神承接项目，非诚勿扰！','E:/ck/project/file/1537858971186.jpg','','竞标超时','IOS,ipad,Windows,Visual Basic,','/static/img/img_xwh/wechat.png',NULL,'af8cfc18-b84d-4825-a49c-e0f6cb527858'),(104,'虚拟币对冲','公开','桌面应用','面议','2018-09-16 10:37:41','2018-09-06 10:58:37','2018-10-19 15:38:11',344,'想做一个虚币对冲网站，手机电脑都能用，，能自动对冲的，跟比特币精灵的对冲差不多，能够手机使用软件简单易懂就行','E:/ck/project/file/1537858971186.jpg','a1.jpg','竞标超时','IOS,ipad,Windows,Linux,','/static/img/img_xwh/wechat.png',NULL,'af8cfc18-b84d-4825-a49c-e0f6cb527858'),(105,'在线报名系统','公开','网站开发','面议','2018-10-11 16:47:05',NULL,NULL,2342,'包括用户端与管理端。用户端能够实现在线报名、在线缴费以及查看通知等功能；管理端主要查看报名信息、管理报名信息以及发布通知等主要功能。','E:/ck/project/file/1537858971186.jpg','','竞标超时','Android,IOS,ipad,','/static/img/img_xwh/wechat.png',NULL,'af8cfc18-b84d-4825-a49c-e0f6cb527858'),(106,'网站抢单软件','公开','APPUI','345','2018-10-11 16:50:01',NULL,NULL,56,'需要找人做个指定网站自动抢单的脚本插件软件。牛逼的人请来，可根据难度商量价格','E:/ck/project/file/1537858971186.jpg','','竞标超时','IOS,ipad,Windows,HTML,','/static/img/img_xwh/wechat.png',NULL,'af8cfc18-b84d-4825-a49c-e0f6cb527858'),(107,'酒吧霸屏系统','公开','网站开发','345','2018-10-14 18:36:17',NULL,NULL,23,'仿微瞄酒吧互动的酒吧霸屏系统，要求使用java语言+vue 开发\r\n工期2个月\r\n报价5-15W','E:/ck/project/file/1537858971186.jpg','','开发中','IOS,ipad,Windows,,HTML,','/static/img/img_xwh/wechat.png','1edbf67138364b50aa252b35d6d73ac2','af8cfc18-b84d-4825-a49c-e0f6cb527858'),(108,'闸机伴侣的APP开发','公开','网站开发','345','2018-10-10 09:55:19',NULL,NULL,89,'1，闸机伴侣，是一个设备的称呼，具体配置在附件中。这个设备是安卓系统，要求开发个APP\r\n2，功能有：调用百度的人脸识别技术，识别到的人脸和WEB数据里的保存的人脸进行比对，比对通过，通过通讯协议通知闸机里的继电机开闸或关闸。APP的UI界面我们设计好，WEB系统我们也开发好，需要做好和WEB系统的调试。\r\n3，人员要求：精通安卓、接口开发。\r\n3，验收标准，能实时保持交流，按时交付，无BUG，提供项目源码','E:/ck/project/file/1537858971186.jpg','','竞标中止','IOS,ipad,Windows,Linux,','/static/img/img_xwh/wechat.png',NULL,'af8cfc18-b84d-4825-a49c-e0f6cb527858'),(109,'智慧社区','公开','网站开发','面议','2018-10-14 10:01:33',NULL,NULL,20,'使用.net开发，需要对接第三方门禁和梯禁系统。软件部分包括社区app，物业后台管理，物业人员app，商家管理后台。主体功能有手机视频对讲/手机开门，多店铺商城系统，抢单模式家政系统，缴费系统等','E:/ck/project/file/1537858971186.jpg','','开发中','Android,IOS,ipad,Windows,','/static/img/img_xwh/wechat.png','1edbf67138364b50aa252b35d6d73ac2','507f9ccc4ff44a7985118edd6268c46d'),(110,'domino长期开发','公开','网站开发','2000','2018-10-12 10:17:23',NULL,NULL,10,'\r\n虽然没有技术难度但是我们希望是domino开发方面的高手','E:/ck/project/file/1537858971186.jpg','','开发完成','Android,IOS,ipad,,HTML,','/static/img/img_xwh/wechat.png','9a7d995828494df48f7af5479e013dd0','507f9ccc4ff44a7985118edd6268c46d'),(111,'智慧社区2','公开','网站开发','面议','2018-10-14 10:01:33',NULL,'2018-10-17 09:35:57',20,'使用.net开发，需要对接第三方门禁和梯禁系统。软件部分包括社区app，物业后台管理，物业人员app，商家管理后台。主体功能有手机视频对讲/手机开门，多店铺商城系统，抢单模式家政系统，缴费系统等','E:/ck/project/file/1537858971186.jpg','','开发中','Android,IOS,ipad,Windows,','/static/img/img_xwh/wechat.png','1edbf67138364b50aa252b35d6d73ac2','507f9ccc4ff44a7985118edd6268c46d'),(112,'夏伟豪很伟大','公开','网站开发','2000','2018-10-16 15:59:49',NULL,NULL,20,'123213213213213123\r\n123123123123312312312312\r\n3123123123123\r\n21321312321123','E:/ck/project/file/1537858971186.jpg','','竞标中','ipad,IOS,Visual C++,','/static/img/img_xwh/wechat.png',NULL,'507f9ccc4ff44a7985118edd6268c46d'),(114,'夏伟豪你给我记住','公开','APPUI','900','2018-10-17 15:50:15','2018-10-17 17:03:51','2018-10-17 17:14:58',44,'斯蒂芬斯蒂芬斯蒂芬斯蒂芬斯蒂芬斯蒂芬斯蒂芬斯蒂芬斯蒂芬斯蒂芬斯蒂芬斯蒂芬斯蒂芬斯蒂芬斯蒂芬斯蒂芬斯蒂芬斯蒂芬斯蒂芬森防守对方','E:/ck/project/file/1537858971186.jpg','160-xiangmu.jpg','开发完成','IOS,.NET,','/static/img/img_xwh/wechat.png','9a322fa945164d3fa3d8bc5c37e77dc1','660c3cf9200b40698e1f00e5715deaf4'),(115,'滴滴打人','公开','网站开发','230','2018-10-19 16:20:24',NULL,NULL,23,'获得首发机会的数据库恢复科举大师傅艰苦地方就是打开链接反抗类毒素解放昆仑山的就圣诞快乐房价圣诞快乐房价多少开了房间',NULL,NULL,'竞标中','ipad,Linux,.NET,','53ee2099-7ac9-442e-83e5-949c13ec36a3.png',NULL,'af8cfc18-b84d-4825-a49c-e0f6cb527858'),(116,'美团外卖','私密','桌面应用','面议','2018-10-19 16:52:30',NULL,NULL,123,'负担和法规和高房价和健康和高科技和客观反对或购房计划给客户给客户经理计划符合各方就换个房间号公开火锅方大化工发甲方根据',NULL,NULL,'竞标中','ipad,Windows,Linux,','d337b399-d272-4a32-a5be-37ee4a6b9299.jpg',NULL,'af8cfc18-b84d-4825-a49c-e0f6cb527858'),(117,'微信小程序','公开','微信开发','面議','2018-10-19 17:06:52',NULL,NULL,30,'有經驗者聯係有經驗者聯係有經驗者聯係有經驗者聯係有經驗者聯係有經驗者聯係有經驗者聯係有經驗者聯係有經驗者聯係有經驗者聯係有經驗者聯係有經驗者聯係有經驗者聯係有經驗者聯係有經驗者聯係',NULL,NULL,'竞标中','Android,IOS,Linux,','290b8f09-626c-43c5-8140-221733ab6aeb.jpg',NULL,'2cbf39eab72d40a4bc1d5aba8b83430e');
/*!40000 ALTER TABLE `project` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `promulgator`
--

DROP TABLE IF EXISTS `promulgator`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `promulgator` (
  `prom_id` varchar(255) NOT NULL,
  `prom_name` varchar(25) NOT NULL,
  `prom_phone` varchar(25) NOT NULL,
  `prom_abipay` varchar(25) NOT NULL,
  `prom_paypwd` varchar(25) NOT NULL,
  `prom_logintime` datetime NOT NULL,
  `prom_intro` varchar(1000) DEFAULT NULL,
  `prom_img` varchar(1000) NOT NULL,
  PRIMARY KEY (`prom_id`),
  CONSTRAINT `prom_alluser` FOREIGN KEY (`prom_id`) REFERENCES `alluser` (`all_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `promulgator`
--

LOCK TABLES `promulgator` WRITE;
/*!40000 ALTER TABLE `promulgator` DISABLE KEYS */;
INSERT INTO `promulgator` VALUES ('2cbf39eab72d40a4bc1d5aba8b83430e','何海明','17876253464','17876253464','123456','2018-10-19 16:59:48',NULL,'default.jpg'),('507f9ccc4ff44a7985118edd6268c46d','mazhenbin','17876253511','17876253511','123456','2018-10-16 15:59:08',NULL,'default.jpg'),('660c3cf9200b40698e1f00e5715deaf4','admin','','','123456','2018-10-17 17:14:46',NULL,'default.jpg'),('7006494107424a4ab6a2365907acc2d6','17876253446','17876253446','17876253446','123456','2018-10-08 16:21:55',NULL,'7006494107424a4ab6a2365907acc2d6.jpg'),('7f628d5d-2265-49d4-b12d-d65b8f280901','阿里巴巴公司','17812345678','17812345678','123456','2018-09-20 00:00:00','阿里巴巴网络技术有限公司（简称：阿里巴巴集团）是以曾担任英语教师的马云为首的18人于1999年在浙江杭州创立。阿里巴巴集团经营多项业务，另外也从关联公司的业务和服务中取得经营商业生态系统上的支援。','default.jpg'),('af8cfc18-b84d-4825-a49c-e0f6cb527858','阿里巴巴','17812348989','tpkyhc3293@sandbox.com','123456','2018-10-20 16:06:33','阿里巴巴网络技术有限公司（简称：阿里巴巴集团）是以曾担任英语教师的马云为首的18人于1999年在浙江杭州创立。阿里巴巴集团经营多项业务，另外也从关联公司的业务和服务中取得经营商业生态系统上的支援。','7dc6b0bd-5e6d-4914-b106-ee9e123e3e9f.png'),('cbbb863ab2ef4c9683b35414f354aeed','17876253446','17876253446','17876253446','123456','2018-10-08 11:48:54',NULL,'default.jpg');
/*!40000 ALTER TABLE `promulgator` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `resource`
--

DROP TABLE IF EXISTS `resource`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `resource` (
  `res_id` int(15) NOT NULL AUTO_INCREMENT,
  `res_uploadtime` datetime NOT NULL,
  `res_name` varchar(255) NOT NULL,
  `res_intro` varchar(1000) NOT NULL,
  `res_path` varchar(255) NOT NULL,
  `res_tag` varchar(255) NOT NULL,
  `res_img` varchar(255) NOT NULL,
  `res_likenum` int(15) NOT NULL,
  `res_long` varchar(25) NOT NULL,
  PRIMARY KEY (`res_id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `resource`
--

LOCK TABLES `resource` WRITE;
/*!40000 ALTER TABLE `resource` DISABLE KEYS */;
INSERT INTO `resource` VALUES (1,'2018-09-27 15:48:49','Java面向对象编程','Java这是测试数据这是测试数据这是测试数据这是测试数据这是测试数据这是测试数据这是测试数据这是测试数据','测试数据测试数据测试数据测试数据测试数据','测试,测试,测试,H5,Java','57035ff200014b8a06000338-240-135.jpg',6,'30'),(2,'2018-09-27 15:48:49','Java语言教程1','Java语言教程这是测试数据这是测试数据这是测试数据这是测试数据这是测试数据这是测试数据这是测试数据这是测试数据','测试数据测试数据测试数据测试数据测试数据','测试,测试,测试,H5,Java','57035ff200014b8a06000338-240-135.jpg',2,'30'),(3,'2018-09-27 15:48:49','Python语言教程','Pathon语言教程这是测试数据这是测试数据这是测试数据这是测试数据这是测试数据这是测试数据这是测试数据这是测试数据','测试数据测试数据测试数据测试数据测试数据','测试,测试,测试,H5,Java','574669dc0001993606000338-240-135.jpg',3,'30'),(4,'2018-09-27 15:48:50','测试数据1421','这是测试数据这是测试数据这是测试数据这是测试数据这是测试数据这是测试数据这是测试数据这是测试数据','测试数据测试数据测试数据测试数据测试数据','测试,测试,测试,H5,Java,unreal','574669dc0001993606000338-240-135.jpg',0,'30'),(5,'2018-09-27 15:48:49','C语言教程1','C语言教程这是测试数据这是测试数据这是测试数据这是测试数据这是测试数据这是测试数据这是测试数据这是测试数据','测试数据测试数据测试数据测试数据测试数据','测试,测试,测试,H5,Java,unreal','574669dc0001993606000338-240-135.jpg',1,'30'),(9,'2017-10-01 10:06:12','Java语言教程','emmmmmm','地址','H5,Html','57035ff200014b8a06000338-240-135.jpg',5,'20'),(11,'2018-09-27 15:48:49','Java面向对象编程1','Java这是测试数据这是测试数据这是测试数据这是测试数据这是测试数据这是测试数据这是测试数据这是测试数据','测试数据测试数据测试数据测试数据测试数据','测试,测试,测试,H5,Java,unreal','57035ff200014b8a06000338-240-135.jpg',4,'30'),(13,'2018-09-12 15:48:49','Java语言教程1','Java','更新成功','测试,测试,测试,H5,Java,unreal','57035ff200014b8a06000338-240-135.jpg',2,'30'),(14,'2018-05-27 15:48:49','Python语言教程','Pathon语言教程这是测试数据这是测试数据这是测试数据这是测试数据这是测试数据这是测试数据这是测试数据这是测试数据','测试数据测试数据测试数据测试数据测试数据','测试,测试,测试,H5,Java,unreal','574669dc0001993606000338-240-135.jpg',2,'30'),(15,'2018-09-27 15:48:50','测试数据142','这是测试数据这是测试数据这是测试数据这是测试数据这是测试数据这是测试数据这是测试数据这是测试数据','测试数据测试数据测试数据测试数据测试数据','测试,测试,测试,H5,Java,unreal','574669dc0001993606000338-240-135.jpg',0,'30'),(16,'2018-09-27 15:48:49','C语言教程1','C语言教程这是测试数据这是测试数据这是测试数据这是测试数据这是测试数据这是测试数据这是测试数据这是测试数据','测试数据测试数据测试数据测试数据测试数据','测试,测试,测试,H5,Java,unreal','574669dc0001993606000338-240-135.jpg',1,'30'),(17,'2017-10-01 10:06:12','Java语言教程1','emmmmmm','地址','H5,Html','57035ff200014b8a06000338-240-135.jpg',5,'20');
/*!40000 ALTER TABLE `resource` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role` (
  `ro_id` int(15) NOT NULL AUTO_INCREMENT,
  `ro_name` varchar(25) NOT NULL,
  `ro_description` varchar(255) NOT NULL,
  PRIMARY KEY (`ro_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'admin','系统管理员'),(2,'promulgator','发布者'),(3,'users','普通用户'),(4,'studioMaster','工作室创始人');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rolepermission`
--

DROP TABLE IF EXISTS `rolepermission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rolepermission` (
  `rp_id` int(15) NOT NULL AUTO_INCREMENT,
  `rp_roid` int(15) NOT NULL,
  `rp_perid` int(15) NOT NULL,
  PRIMARY KEY (`rp_id`),
  KEY `role_idx` (`rp_roid`),
  KEY `permission_idx` (`rp_perid`),
  CONSTRAINT `permission` FOREIGN KEY (`rp_perid`) REFERENCES `permission` (`per_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `role` FOREIGN KEY (`rp_roid`) REFERENCES `role` (`ro_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rolepermission`
--

LOCK TABLES `rolepermission` WRITE;
/*!40000 ALTER TABLE `rolepermission` DISABLE KEYS */;
INSERT INTO `rolepermission` VALUES (1,1,2),(2,1,3),(3,2,1),(4,1,1),(5,3,4);
/*!40000 ALTER TABLE `rolepermission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `studio`
--

DROP TABLE IF EXISTS `studio`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `studio` (
  `stu_id` varchar(255) NOT NULL,
  `stu_creattime` datetime NOT NULL,
  `stu_name` varchar(255) NOT NULL,
  `stu_projectnum` int(15) DEFAULT '0',
  `stu_grade` double(15,2) DEFAULT '0.00',
  `stu_membernum` int(15) DEFAULT '0',
  `stu_intro` varchar(1000) DEFAULT NULL,
  `stu_img` varchar(1000) NOT NULL,
  `stu_field` varchar(255) DEFAULT NULL,
  `stu_tag` varchar(255) DEFAULT NULL,
  `stu_province` varchar(25) NOT NULL,
  `stu_city` varchar(25) NOT NULL,
  `stu_area` varchar(25) NOT NULL,
  `stu_income` double(15,0) DEFAULT '0',
  `stu_creatid` varchar(255) NOT NULL,
  PRIMARY KEY (`stu_id`),
  KEY `u_id1` (`stu_creatid`),
  CONSTRAINT `u_id1` FOREIGN KEY (`stu_creatid`) REFERENCES `users` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `studio`
--

LOCK TABLES `studio` WRITE;
/*!40000 ALTER TABLE `studio` DISABLE KEYS */;
INSERT INTO `studio` VALUES ('1edbf67138364b50aa252b35d6d73ac2','2018-10-17 15:19:27','阳光明媚工作室',0,0.00,2,'软通动力信息技术（集团）有限公司（以下简称：软通动力）是中国领先的创新型软件及信息技术服务商。公司2001年成立于北京，立足中国，服务全球市场。经过16年发展，目前公司在全球61个城市设有120余个分支机构33个全球交付中心，员工总数近40000人 [2-3] \r\n软通动力具备端到端“软件+服务”综合业务能力和强大的纵深服务优势，凭借深厚的技术实力和强大的生态整合能力，公司主营业务覆盖软件技术服务、企业数字化转型服务、智慧城市服务以及云计算与互联网平台服务四大业务领域','niming.png','网站开发','Java J2EE ','广东省','肇庆市','端州区',0,'3fdc347f78624422bd9f3db7fb14c107'),('958d73b1ef3f483687baf4bb16ad954b','2018-10-20 10:38:50','bala工作室',0,0.00,1,'123213\r\n213213\r\n21321\r\n321321\r\n321321\r\n32132\r\n1321321','niming.png','桌面应用,UI设计,APP','Android IOS .NET ipad ','江西省','新余市','分宜镇',0,'33d0023cceda4b7dbc6d403fa349ca0a'),('9a322fa945164d3fa3d8bc5c37e77dc1','2018-10-17 15:53:50','测试工作室',1,5.00,1,'我们是测试组','3cd0f797-7938-4392-8ca5-e311d26ec44d.png','桌面应用,APP','Windows IOS ','广东省','广州市','天河区',0,'33d0023cceda4b7dbc6d403fa349ca0a'),('9a7d995828494df48f7af5479e013dd0','2018-10-17 15:08:12','天美工作室',2,0.00,1,'1 4564654654\r\n22 456465465 \r\n3 5465465456 \r\n4 46546546545 \r\n5465465465456 \r\n6465465465456','niming.png','网站开发,UI设计','Android IOS Windows Linux ','内蒙古','鄂尔多斯市','鄂托克前旗',0,'0a8e00e298f84927b64947220e55baef'),('fd23cad5cc424e82a79679f128449de7','2018-10-17 15:02:35','光子工作室',0,0.00,1,'1.牛逼\r\n2.崛起\r\n3.无敌','b0fd6099-6cdf-4ce7-9adc-e9110c5efa88.png','网站开发,桌面应用','Android IOS ipad ','重庆市','重庆市','南岸区',0,'36ab99a97d344f3e8053b0fd5cc96f52');
/*!40000 ALTER TABLE `studio` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `userrole`
--

DROP TABLE IF EXISTS `userrole`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `userrole` (
  `ur_id` int(15) NOT NULL AUTO_INCREMENT,
  `ur_allid` varchar(255) NOT NULL,
  `ur_roid` int(15) NOT NULL,
  PRIMARY KEY (`ur_id`),
  KEY `alluser_idx` (`ur_allid`),
  KEY `role_idx` (`ur_roid`),
  CONSTRAINT `userrole_alluser` FOREIGN KEY (`ur_allid`) REFERENCES `alluser` (`all_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `userrole_role` FOREIGN KEY (`ur_roid`) REFERENCES `role` (`ro_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `userrole`
--

LOCK TABLES `userrole` WRITE;
/*!40000 ALTER TABLE `userrole` DISABLE KEYS */;
INSERT INTO `userrole` VALUES (1,'7f628d5d-2265-49d4-b12d-d65b8f280901',1),(7,'7006494107424a4ab6a2365907acc2d6',2),(9,'db76983ae221487dbcc0751f4744a57f',3),(10,'0a8e00e298f84927b64947220e55baef',3),(11,'15f52c274c5f4901add842445fa68a2e',3),(12,'cbbb863ab2ef4c9683b35414f354aeed',2),(13,'46c7019fc82a4db4869818f13744a444',3),(14,'9f3f34eae58247dfa84b8c8b85059f75',3),(15,'05497f212ace40b3b32310b456220681',3),(16,'4ed9ce2894d544cab63b6c28be889697',3),(17,'04be96492a784f26a5db9ffe679b5460',3),(18,'66a75c8f9f5249769171a322a99884c5',3),(19,'9aebf999ec9440d68150da597b13f9f7',3),(21,'ded80c33c8624663960df94214129b8e',3),(22,'441f8d7dde6e4a43bfe7720c85930365',3),(23,'0b38a39372cc4ae3bf40b7a365c0c3fc',1),(24,'507f9ccc4ff44a7985118edd6268c46d',2),(25,'33d0023cceda4b7dbc6d403fa349ca0a',3),(26,'61c0ccaed30846d4a6a6c0563efa64af',3),(27,'173d1db82f0948288790c57855b69b4b',3),(28,'36ab99a97d344f3e8053b0fd5cc96f52',3),(31,'3fdc347f78624422bd9f3db7fb14c107',3),(32,'e1e703cfbe324d618ca507819c4ecda6',3),(34,'660c3cf9200b40698e1f00e5715deaf4',2),(36,'17a2c367709145a39d9cb394a7d95b5c',3),(37,'af8cfc18-b84d-4825-a49c-e0f6cb527858',2),(38,'2cbf39eab72d40a4bc1d5aba8b83430e',2),(39,'d57b383e40a04c2bb3a851237453ae4c',3);
/*!40000 ALTER TABLE `userrole` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `user_id` varchar(255) NOT NULL,
  `user_name` varchar(25) NOT NULL,
  `user_phone` varchar(25) NOT NULL,
  `user_sex` varchar(25) DEFAULT NULL,
  `user_age` int(15) DEFAULT NULL,
  `user_intro` varchar(1000) DEFAULT NULL,
  `user_abipay` varchar(25) NOT NULL,
  `user_paypwd` varchar(45) NOT NULL,
  `user_logintime` datetime NOT NULL,
  `user_img` varchar(1000) NOT NULL,
  `user_studio` varchar(255) DEFAULT NULL,
  `user_entrytime` datetime DEFAULT NULL,
  `user_quittme` datetime DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  KEY `st_id` (`user_studio`),
  CONSTRAINT `users_alluser` FOREIGN KEY (`user_id`) REFERENCES `alluser` (`all_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES ('04be96492a784f26a5db9ffe679b5460','刘明','13800138000',NULL,NULL,NULL,'13800138000','123456','2018-10-15 08:53:34','default.jpg',NULL,NULL,NULL),('05497f212ace40b3b32310b456220681','孙猴子','13800138000',NULL,NULL,NULL,'13800138000','123456','2018-10-10 09:43:11','default.jpg',NULL,NULL,NULL),('0a8e00e298f84927b64947220e55baef','mzb','17876253511',NULL,NULL,NULL,'17876253511','123456','2018-10-19 17:16:33','default.jpg','9a7d995828494df48f7af5479e013dd0',NULL,NULL),('15f52c274c5f4901add842445fa68a2e','17876253446','17876253446',NULL,NULL,NULL,'17876253446','123456','2018-10-01 22:56:41','default.jpg','',NULL,NULL),('173d1db82f0948288790c57855b69b4b','mazhenbin2','17876253511',NULL,NULL,NULL,'17876253511','123456','2018-10-19 15:44:33','default.jpg','c765654c17d34aff988ac7089cea2e4f','2018-10-17 18:45:26','2018-10-19 13:16:10'),('17a2c367709145a39d9cb394a7d95b5c','admin','',NULL,NULL,NULL,'','','2018-10-17 15:41:36','default.jpg',NULL,NULL,NULL),('33d0023cceda4b7dbc6d403fa349ca0a','mazhenbin1','17876253511',NULL,NULL,NULL,'17876253511','123456','2018-10-20 16:14:03','default.jpg','958d73b1ef3f483687baf4bb16ad954b',NULL,NULL),('36ab99a97d344f3e8053b0fd5cc96f52','mzb5','17876253511',NULL,NULL,NULL,'17876253511','123456','2018-10-17 15:00:19','default.jpg','fd23cad5cc424e82a79679f128449de7',NULL,NULL),('3fdc347f78624422bd9f3db7fb14c107','乔峰','17876253530',NULL,NULL,NULL,'17876253530','123456','2018-10-20 20:38:15','default.jpg','1edbf67138364b50aa252b35d6d73ac2',NULL,NULL),('441f8d7dde6e4a43bfe7720c85930365','石神','17876253530',NULL,NULL,NULL,'17876253530','123456','2018-10-17 19:06:41','default.jpg',NULL,'2018-10-17 19:10:33','2018-10-17 19:14:01'),('46c7019fc82a4db4869818f13744a444','唐僧','13800138000',NULL,NULL,NULL,'13800138000','123456','2018-10-10 08:53:01','default.jpg',NULL,NULL,NULL),('4ed9ce2894d544cab63b6c28be889697','沙僧','13800138000',NULL,NULL,NULL,'13800138000','123456','2018-10-10 08:58:55','default.jpg',NULL,NULL,NULL),('61c0ccaed30846d4a6a6c0563efa64af','@@@嘎嘎嘎','17876253455',NULL,8856,'','782788572@qq.com','000000','2018-10-16 15:19:46','default.jpg',NULL,NULL,NULL),('66a75c8f9f5249769171a322a99884c5','123123213','17876253446',NULL,NULL,NULL,'17876253446','123456','2018-10-10 09:14:15','default.jpg',NULL,NULL,NULL),('9aebf999ec9440d68150da597b13f9f7','123123123','17876253446',NULL,NULL,NULL,'17876253446','123456','2018-10-10 09:39:56','default.jpg',NULL,NULL,NULL),('9f3f34eae58247dfa84b8c8b85059f75','猪八戒','13800138000',NULL,NULL,NULL,'13800138000','123456','2018-10-10 08:54:37','default.jpg',NULL,NULL,NULL),('d57b383e40a04c2bb3a851237453ae4c','虚竹','17876253530',NULL,NULL,NULL,'17876253530','123456','2018-10-20 20:41:28','default.jpg',NULL,'2018-10-20 21:01:16','2018-10-20 21:01:50'),('db76983ae221487dbcc0751f4744a57f','test21','17876253446',NULL,NULL,NULL,'17876253446','123456','2018-10-08 10:30:47','default.jpg',NULL,NULL,NULL),('ded80c33c8624663960df94214129b8e','黄栎健','17876253530',NULL,NULL,NULL,'17876253530','123456','2018-10-12 16:27:49','',NULL,NULL,NULL),('e1e703cfbe324d618ca507819c4ecda6','段誉','17876253530',NULL,NULL,NULL,'17876253530','123456','2018-10-20 20:39:05','default.jpg','1edbf67138364b50aa252b35d6d73ac2','2018-10-19 17:09:11','2018-10-19 17:06:08'),('e9ae842a-e70f-49b8-a9b5-bee24a13c8bb','hlj','13800138000','女',100,'1','tpkyhc3293@sandbox.com','100861','2018-10-19 23:35:43','3f0fbdc7-5f6a-49d0-a6fd-a945408f7f0d.png',NULL,NULL,NULL);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-10-21 10:49:28
