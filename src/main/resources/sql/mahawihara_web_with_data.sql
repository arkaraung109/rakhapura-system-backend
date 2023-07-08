-- MySQL dump 10.13  Distrib 8.0.32, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: mahawihara_web_1
-- ------------------------------------------------------
-- Server version	8.0.32

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `academic_year`
--

DROP TABLE IF EXISTS `academic_year`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `academic_year` (
  `id` tinyint NOT NULL AUTO_INCREMENT,
  `name` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `academic_year`
--

LOCK TABLES `academic_year` WRITE;
/*!40000 ALTER TABLE `academic_year` DISABLE KEYS */;
INSERT INTO `academic_year` VALUES (29,'2017-2018'),(30,'2018-2019'),(31,'2019-2020'),(32,'2021-2022');
/*!40000 ALTER TABLE `academic_year` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `attendence`
--

DROP TABLE IF EXISTS `attendence`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `attendence` (
  `id` int NOT NULL AUTO_INCREMENT,
  `student_id` int DEFAULT NULL,
  `exam_id` int DEFAULT NULL,
  `date` varchar(20) DEFAULT NULL,
  `present` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `class_id` (`exam_id`),
  KEY `student_id` (`student_id`),
  CONSTRAINT `attendence_ibfk_2` FOREIGN KEY (`exam_id`) REFERENCES `exam` (`exam_id`),
  CONSTRAINT `attendence_ibfk_3` FOREIGN KEY (`student_id`) REFERENCES `student` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=171 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `attendence`
--

LOCK TABLES `attendence` WRITE;
/*!40000 ALTER TABLE `attendence` DISABLE KEYS */;
INSERT INTO `attendence` VALUES (135,45,10,'12-02-2022',1),(136,46,10,'12-02-2022',1),(137,47,10,'12-02-2022',1),(138,48,10,'12-02-2022',1),(139,49,10,'12-02-2022',1),(140,50,10,'12-02-2022',1),(141,51,10,'12-02-2022',1),(142,52,10,'12-02-2022',1),(143,53,10,'12-02-2022',1),(144,54,10,'12-02-2022',1),(145,45,11,'12-02-2022',1),(146,46,11,'12-02-2022',1),(147,47,11,'12-02-2022',1),(148,48,11,'12-02-2022',1),(149,49,11,'12-02-2022',1),(150,50,11,'12-02-2022',1),(151,51,11,'12-02-2022',1),(152,52,11,'12-02-2022',1),(153,53,11,'12-02-2022',1),(154,54,11,'12-02-2022',1),(155,45,12,'12-02-2022',1),(156,46,12,'12-02-2022',1),(157,47,12,'12-02-2022',1),(158,48,12,'12-02-2022',1),(159,49,12,'12-02-2022',1),(160,50,12,'12-02-2022',1),(161,51,12,'12-02-2022',1),(162,52,12,'12-02-2022',1),(163,53,12,'12-02-2022',1),(164,54,12,'12-02-2022',1),(168,55,10,'13-02-2022',1),(169,55,11,'13-02-2022',0),(170,55,12,'13-02-2022',0);
/*!40000 ALTER TABLE `attendence` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `award`
--

DROP TABLE IF EXISTS `award`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `award` (
  `id` tinyint NOT NULL AUTO_INCREMENT,
  `student_id` int DEFAULT NULL,
  `description` varchar(150) DEFAULT NULL,
  `date` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `student_id` (`student_id`),
  CONSTRAINT `award_ibfk_1` FOREIGN KEY (`student_id`) REFERENCES `student` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `award`
--

LOCK TABLES `award` WRITE;
/*!40000 ALTER TABLE `award` DISABLE KEYS */;
/*!40000 ALTER TABLE `award` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `class`
--

DROP TABLE IF EXISTS `class`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `class` (
  `id` tinyint NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `grade_id` tinyint DEFAULT NULL,
  `academic_year_id` tinyint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `grade_id` (`grade_id`),
  KEY `academic_year_id` (`academic_year_id`),
  CONSTRAINT `class_ibfk_1` FOREIGN KEY (`grade_id`) REFERENCES `grade` (`id`),
  CONSTRAINT `class_ibfk_2` FOREIGN KEY (`academic_year_id`) REFERENCES `academic_year` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `class`
--

LOCK TABLES `class` WRITE;
/*!40000 ALTER TABLE `class` DISABLE KEYS */;
INSERT INTO `class` VALUES (19,'2017-18 မူလတန်း (A)',11,29),(20,'2017-18 မူလတန်း (B)',11,29),(21,'2017-18 မူလတန်း (C)',11,29),(22,'2017-18 ပထမတန်း (A)',12,29),(23,'2017-18 ပထမတန်း (B)',12,29),(24,'2017-18 ပထမတန်း (C)',12,29),(25,'2018-19 မူလတန်း (A)',11,30),(26,'2018-19 မူလတန်း (B)',11,30),(27,'2018-19 မူလတန်း (C)',11,30),(28,'2018-19 ပထမတန်း (A)',12,30),(29,'2018-19 ပထမတန်း (B)',12,30),(30,'2018-19 ပထမတန်း (C)',12,30);
/*!40000 ALTER TABLE `class` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `exam`
--

DROP TABLE IF EXISTS `exam`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `exam` (
  `exam_id` int NOT NULL AUTO_INCREMENT,
  `exam_title_id` tinyint DEFAULT NULL,
  `academic_year_id` tinyint DEFAULT NULL,
  `exam_date` varchar(30) DEFAULT NULL,
  `time` varchar(20) DEFAULT NULL,
  `subject_type_id` tinyint DEFAULT NULL,
  `pass_mark` int DEFAULT NULL,
  `mark_percentage` int DEFAULT NULL,
  PRIMARY KEY (`exam_id`),
  KEY `exam_title_id` (`exam_title_id`),
  KEY `academic_year_id` (`academic_year_id`),
  KEY `subject_type_id` (`subject_type_id`),
  CONSTRAINT `exam_ibfk_1` FOREIGN KEY (`exam_title_id`) REFERENCES `exam_title` (`id`),
  CONSTRAINT `exam_ibfk_2` FOREIGN KEY (`academic_year_id`) REFERENCES `academic_year` (`id`),
  CONSTRAINT `exam_ibfk_3` FOREIGN KEY (`subject_type_id`) REFERENCES `subject_type` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `exam`
--

LOCK TABLES `exam` WRITE;
/*!40000 ALTER TABLE `exam` DISABLE KEYS */;
INSERT INTO `exam` VALUES (10,12,29,'25-11-2017','9:00 AM',16,50,100),(11,12,29,'26-11-2017','9:00 AM',17,35,60),(12,12,29,'27-11-2017','9:00 AM',18,50,100);
/*!40000 ALTER TABLE `exam` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `exam_detail`
--

DROP TABLE IF EXISTS `exam_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `exam_detail` (
  `exam_detail_id` int NOT NULL AUTO_INCREMENT,
  `exam_id` tinyint DEFAULT NULL,
  `pass_mark` int DEFAULT NULL,
  `mark_percentage` int DEFAULT NULL,
  `subject_id` smallint DEFAULT NULL,
  PRIMARY KEY (`exam_detail_id`),
  UNIQUE KEY `subject_id` (`subject_id`,`exam_id`),
  KEY `exam_title_id` (`exam_id`),
  CONSTRAINT `exam_detail_ibfk_1` FOREIGN KEY (`subject_id`) REFERENCES `subject` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `exam_detail`
--

LOCK TABLES `exam_detail` WRITE;
/*!40000 ALTER TABLE `exam_detail` DISABLE KEYS */;
INSERT INTO `exam_detail` VALUES (8,10,10,20,27),(9,10,10,20,28),(10,10,30,60,29),(11,11,5,10,30),(12,11,5,10,31),(13,11,20,40,32),(14,12,10,20,33),(15,12,10,20,34),(16,12,30,60,35);
/*!40000 ALTER TABLE `exam_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `exam_title`
--

DROP TABLE IF EXISTS `exam_title`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `exam_title` (
  `id` tinyint NOT NULL AUTO_INCREMENT,
  `name` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `exam_title`
--

LOCK TABLES `exam_title` WRITE;
/*!40000 ALTER TABLE `exam_title` DISABLE KEYS */;
INSERT INTO `exam_title` VALUES (12,'2017-2018 သာမဏေကျော်မူလတန်း စာမေးပွဲ');
/*!40000 ALTER TABLE `exam_title` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `grade`
--

DROP TABLE IF EXISTS `grade`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `grade` (
  `id` tinyint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `remark` varchar(20) DEFAULT NULL,
  `abbreviate` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `grade`
--

LOCK TABLES `grade` WRITE;
/*!40000 ALTER TABLE `grade` DISABLE KEYS */;
INSERT INTO `grade` VALUES (11,'သာမဏေကျော်မူလတန်း','မူလတန်း','ရမ'),(12,'သာမဏေကျော်ပထမတန်း','ပထမတန်း','ရပ'),(13,'သာမဏေကျော်ဒုတိယတန်း','ဒုတိယတန်း','ရဒ'),(14,'သာမဏေကျော်တတိယတန်း','တတိယတန်း','ရတ'),(15,'သာမဏေကျော်ဥပဇာတန်း','ဥပဇာတန်း','ရဥ');
/*!40000 ALTER TABLE `grade` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hostel`
--

DROP TABLE IF EXISTS `hostel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hostel` (
  `id` tinyint NOT NULL AUTO_INCREMENT,
  `name` varchar(30) DEFAULT NULL,
  `address` varchar(100) DEFAULT NULL,
  `phone` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hostel`
--

LOCK TABLES `hostel` WRITE;
/*!40000 ALTER TABLE `hostel` DISABLE KEYS */;
INSERT INTO `hostel` VALUES (1,'ဧရာဝတီအဆောင်','ဧရာဝတီတိုင်း၊ မြန်အောင်','၀၉၁၁၁၁၁၁၁၁');
/*!40000 ALTER TABLE `hostel` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hostel_student`
--

DROP TABLE IF EXISTS `hostel_student`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hostel_student` (
  `id` int NOT NULL AUTO_INCREMENT,
  `hostel_id` tinyint DEFAULT NULL,
  `student_id` int DEFAULT NULL,
  `exam_title_id` tinyint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `hostel_id` (`hostel_id`),
  KEY `student_id` (`student_id`),
  KEY `exam_title_id` (`exam_title_id`),
  CONSTRAINT `hostel_student_ibfk_1` FOREIGN KEY (`hostel_id`) REFERENCES `hostel` (`id`),
  CONSTRAINT `hostel_student_ibfk_2` FOREIGN KEY (`student_id`) REFERENCES `student` (`id`),
  CONSTRAINT `hostel_student_ibfk_3` FOREIGN KEY (`exam_title_id`) REFERENCES `exam_title` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hostel_student`
--

LOCK TABLES `hostel_student` WRITE;
/*!40000 ALTER TABLE `hostel_student` DISABLE KEYS */;
INSERT INTO `hostel_student` VALUES (1,1,45,12),(2,1,46,12),(3,1,47,12),(4,1,48,12),(5,1,49,12),(6,1,50,12),(7,1,51,12),(8,1,52,12),(9,1,53,12),(10,1,54,12);
/*!40000 ALTER TABLE `hostel_student` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `monestery`
--

DROP TABLE IF EXISTS `monestery`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `monestery` (
  `id` tinyint NOT NULL AUTO_INCREMENT,
  `name` varchar(30) DEFAULT NULL,
  `head_master` varchar(100) DEFAULT NULL,
  `township` varchar(100) DEFAULT NULL,
  `remark` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `monestery`
--

LOCK TABLES `monestery` WRITE;
/*!40000 ALTER TABLE `monestery` DISABLE KEYS */;
INSERT INTO `monestery` VALUES (8,'ကျောင်းတိုက် ၁','ကျောင်းတိုက် ၁ ကျောင်းထိုင်','ရန်ကုန်','-'),(9,'ကျောင်းတိုက် ၂','ကျောင်းတိုက် ၂ ကျောင်းထိုင်','မွန်ပြည်နယ်','-'),(10,'ကျောင်းတိုက် ၃','ကျောင်းတိုက် ၃ ကျောင်းထိုင်','တနင်္သာရီ','-'),(11,'ကျောင်းတိုက် ၄','ကျောင်းတိုက် ၄ ကျောင်းထိုင်','ပဲခူး','-');
/*!40000 ALTER TABLE `monestery` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `punishment`
--

DROP TABLE IF EXISTS `punishment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `punishment` (
  `id` int NOT NULL AUTO_INCREMENT,
  `student_id` int DEFAULT NULL,
  `description` varchar(150) DEFAULT NULL,
  `date_occured` varchar(50) DEFAULT NULL,
  `punishment` varchar(150) DEFAULT NULL,
  `from_` varchar(50) DEFAULT NULL,
  `to_` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `student_id` (`student_id`),
  CONSTRAINT `punishment_ibfk_1` FOREIGN KEY (`student_id`) REFERENCES `student` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `punishment`
--

LOCK TABLES `punishment` WRITE;
/*!40000 ALTER TABLE `punishment` DISABLE KEYS */;
/*!40000 ALTER TABLE `punishment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `region`
--

DROP TABLE IF EXISTS `region`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `region` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(400) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `region`
--

LOCK TABLES `region` WRITE;
/*!40000 ALTER TABLE `region` DISABLE KEYS */;
INSERT INTO `region` VALUES (1,'ကချင်ပြည်နယ်'),(2,'ကရင်ပြည်နယ်'),(3,'ကယားပြည်နယ်'),(4,'ချင်းပြည်နယ်'),(5,'ရခိုင်ပြည်နယ်'),(6,'ရှမ်းပြည်နယ်'),(7,'မွန်ပြည်နယ်'),(8,'ရန်ကုန်တိုင်း'),(9,'မန္တလေးတိုင်း'),(10,'စစ်ကိုင်းတိုင်း'),(11,'မကွေးတိုင်း'),(12,'ပဲခူးတိုင်း'),(13,'တနင်္သာရီတိုင်း'),(14,'ဧရာဝတီတိုင်း');
/*!40000 ALTER TABLE `region` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role` (
  `id` tinyint NOT NULL AUTO_INCREMENT,
  `name` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'admin'),(2,'STUDENT_EXAM_MARK_ENTRY'),(3,'HOSTEL_ATTENDANCE_ENTRY'),(4,'ATTENDANCE_ENTRY');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `student`
--

DROP TABLE IF EXISTS `student`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `student` (
  `id` int NOT NULL AUTO_INCREMENT,
  `reg_no` varchar(10) DEFAULT '''-''',
  `reg_seq_no` int DEFAULT NULL,
  `reg_date` varchar(30) DEFAULT '''-''',
  `class_id` tinyint DEFAULT NULL,
  `name` varchar(300) DEFAULT '''-''',
  `dob` varchar(30) DEFAULT '''-''',
  `sex` varchar(5) DEFAULT '''-''',
  `nationality` varchar(300) DEFAULT '''-''',
  `nrc` varchar(300) DEFAULT '''-''',
  `father_name` varchar(300) DEFAULT '''-''',
  `mother_name` varchar(300) DEFAULT '''-''',
  `address` varchar(300) DEFAULT '''-''',
  `picture_path` varchar(300) NOT NULL DEFAULT '''-''',
  `monestery_id` tinyint DEFAULT NULL,
  `region_id` int DEFAULT NULL,
  `arrival` tinyint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `monestery_id` (`monestery_id`),
  KEY `class_id` (`class_id`),
  KEY `fk_region_id` (`region_id`),
  CONSTRAINT `fk_region_id` FOREIGN KEY (`region_id`) REFERENCES `region` (`id`),
  CONSTRAINT `student_ibfk_1` FOREIGN KEY (`monestery_id`) REFERENCES `monestery` (`id`),
  CONSTRAINT `student_ibfk_2` FOREIGN KEY (`class_id`) REFERENCES `class` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=56 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student`
--

LOCK TABLES `student` WRITE;
/*!40000 ALTER TABLE `student` DISABLE KEYS */;
INSERT INTO `student` VALUES (45,'ရမ-၁',1,NULL,19,'ကျောင်းသား ၁','-','ကျား','-','၁၁၁၁၁၁','အဖ ၁','အမိ ၁','၁','profile_137e3c67702c412fa46dcf918b43ce89_.png',8,14,1),(46,'ရမ-၂',2,NULL,19,'ကျောင်းသား 2','-','ကျား','-','222222','အဖ 2','အမိ 2','2','profile_f06091d936ba89c5d9dd4909d6e80648_.png',8,14,1),(47,'ရမ-၃',3,NULL,19,'ကျောင်းသား 3','3','ကျား','-','333333','အဖ3','အမိ 3','3','profile_fbf6520608a3ccae14fb3af14f737fb1_.jpg',8,14,1),(48,'ရမ-၄',4,NULL,19,'ကျောင်းသား 4','-','ကျား','4','444444','အဖ 4','အမိ4','4','profile_d9d0a9f18f101ab1be3bd04eaddd34d0_.jpg',8,14,1),(49,'ရမ-၅',5,NULL,19,'ကျောင်းသား 5','5','ကျား','5','5','အဖ 5','အမိ 5','5','profile_fcac040c60a38ab9c3ac96cfe74bed14_.jpg',8,14,1),(50,'ရမ-၆',6,NULL,20,'ကျောင်းသား 6','-','မ','6','666666','အဖ 6','အမိ 6','6','profile_db83e64e3f967ba799a193d914fa872d_.jpg',9,14,1),(51,'ရမ-၇',7,NULL,20,'ကျောင်းသား 7','7','မ','7','777777','အဖ 7','အမိ 7','7','profile_e0ecbaefb1f3d0e4796f7719ced88eb8_.png',9,14,1),(52,'ရမ-၈',8,NULL,20,'ကျောင်းသား 8','8','မ','8','8888888','အဖ 8','အမိ 8','8','profile_06d0d2169fd84a3f119ab891f41646a1_.png',9,14,1),(53,'ရမ-၉',9,NULL,20,'9','9','မ','9','999999','အဖ 9','အမိ 9','9','profile_f0a09bcef1b8152e238e2be3db598ee1_.jpg',9,14,1),(54,'ရမ-၁၀',10,NULL,20,'ကျောင်းသား 10','10','မ','10','0000010','အဖ 10','အမိ 10','10','profile_ccb63a42eab8223c71b6c16e10af8fe8_.jpg',9,14,1),(55,'ရမ-၁၁',11,NULL,21,'ကျောင်းသား ၁၁','၂၂','ကျား','-','၁၁/၁၁၁၁၁၁၁၁','အဖ ၁၁','အမိ ၁၁','၁၁','profile_e8187d83e33431bff314f2103ff4f097_.jpg',10,14,1);
/*!40000 ALTER TABLE `student` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `student_class`
--

DROP TABLE IF EXISTS `student_class`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `student_class` (
  `id` int NOT NULL AUTO_INCREMENT,
  `class_id` smallint DEFAULT NULL,
  `student_id` int DEFAULT NULL,
  `roll_no_in` varchar(10) DEFAULT NULL,
  `roll_no_out` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `student_id` (`student_id`,`class_id`),
  KEY `class_id` (`class_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student_class`
--

LOCK TABLES `student_class` WRITE;
/*!40000 ALTER TABLE `student_class` DISABLE KEYS */;
/*!40000 ALTER TABLE `student_class` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `student_exam`
--

DROP TABLE IF EXISTS `student_exam`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `student_exam` (
  `id` int NOT NULL AUTO_INCREMENT,
  `mark` varchar(10) DEFAULT NULL,
  `exam_detail_id` int DEFAULT NULL,
  `attendence_id` int DEFAULT NULL,
  `pass` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `exam_detail_id` (`exam_detail_id`),
  KEY `attendence_id` (`attendence_id`),
  CONSTRAINT `student_exam_ibfk_1` FOREIGN KEY (`exam_detail_id`) REFERENCES `exam_detail` (`exam_detail_id`),
  CONSTRAINT `student_exam_ibfk_2` FOREIGN KEY (`exam_detail_id`) REFERENCES `exam_detail` (`exam_detail_id`),
  CONSTRAINT `student_exam_ibfk_3` FOREIGN KEY (`attendence_id`) REFERENCES `attendence` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=98 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student_exam`
--

LOCK TABLES `student_exam` WRITE;
/*!40000 ALTER TABLE `student_exam` DISABLE KEYS */;
INSERT INTO `student_exam` VALUES (8,'12',8,135,0),(9,'15',9,135,0),(10,'50',10,135,0),(11,'5',11,145,1),(12,'5',12,145,1),(13,'21',13,145,0),(14,'20',14,155,0),(15,'18',15,155,0),(16,'40',16,155,0),(17,'17',8,136,0),(18,'13',9,136,0),(19,'50',10,136,0),(20,'5',11,146,1),(21,'5',12,146,1),(22,'20',13,146,1),(23,'18',14,156,0),(24,'20',15,156,0),(25,'30',16,156,1),(26,'10',8,137,1),(27,'10',9,137,1),(28,'60',10,137,0),(29,'5',11,147,1),(30,'5',12,147,1),(31,'26',13,147,0),(32,'19',14,157,0),(33,'10',15,157,1),(34,'56',16,157,0),(35,'10',8,138,1),(36,'10',9,138,1),(37,'60',10,138,0),(38,'5',11,148,1),(39,'5',12,148,1),(40,'30',13,148,0),(41,'18',14,158,0),(42,'20',15,158,0),(43,'30',16,158,1),(44,'20',8,139,0),(45,'20',9,139,0),(46,'57',10,139,0),(47,'9',11,149,0),(48,'9',12,149,0),(49,'18',13,149,1),(50,'19',14,159,0),(51,'20',15,159,0),(52,'57',16,159,0),(53,'9',8,140,1),(54,'9',9,140,1),(55,'60',10,140,0),(56,'10',11,150,0),(57,'10',12,150,0),(58,'39',13,150,0),(59,'20',14,160,0),(60,'20',15,160,0),(61,'54',16,160,0),(62,'10',8,141,1),(63,'17',9,141,0),(64,'28',10,141,1),(65,'4',11,151,1),(66,'9',12,151,0),(67,'38',13,151,0),(68,'20',14,161,0),(69,'20',15,161,0),(70,'54',16,161,0),(71,'10',8,142,1),(72,'20',9,142,0),(73,'58',10,142,0),(74,'10',11,152,0),(75,'10',12,152,0),(76,'19',13,152,1),(77,'10',14,162,1),(78,'10',15,162,1),(79,'20',16,162,1),(80,'9',8,143,1),(81,'9',9,143,1),(82,'60',10,143,0),(83,'10',11,153,0),(84,'10',12,153,0),(85,'12',13,153,1),(86,'20',14,163,0),(87,'20',15,163,0),(88,'54',16,163,0),(89,'9',8,144,1),(90,'9',9,144,1),(91,'19',10,144,1),(92,'3',11,154,1),(93,'3',12,154,1),(94,'10',13,154,1),(95,'16',14,164,0),(96,'18',15,164,0),(97,'22',16,164,1);
/*!40000 ALTER TABLE `student_exam` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `student_exam_moderate`
--

DROP TABLE IF EXISTS `student_exam_moderate`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `student_exam_moderate` (
  `id` int NOT NULL AUTO_INCREMENT,
  `mark` tinyint DEFAULT NULL,
  `exam_detail_id` int DEFAULT NULL,
  `attendence_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `exam_detail_id` (`exam_detail_id`),
  KEY `attendence_id` (`attendence_id`),
  CONSTRAINT `student_exam_moderate_ibfk_1` FOREIGN KEY (`exam_detail_id`) REFERENCES `exam_detail` (`exam_detail_id`),
  CONSTRAINT `student_exam_moderate_ibfk_2` FOREIGN KEY (`attendence_id`) REFERENCES `attendence` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student_exam_moderate`
--

LOCK TABLES `student_exam_moderate` WRITE;
/*!40000 ALTER TABLE `student_exam_moderate` DISABLE KEYS */;
INSERT INTO `student_exam_moderate` VALUES (1,28,16,158);
/*!40000 ALTER TABLE `student_exam_moderate` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `subject`
--

DROP TABLE IF EXISTS `subject`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `subject` (
  `id` smallint NOT NULL AUTO_INCREMENT,
  `name` varchar(200) DEFAULT NULL,
  `subject_type_id` tinyint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `subject_type_id` (`subject_type_id`),
  CONSTRAINT `subject_ibfk_2` FOREIGN KEY (`subject_type_id`) REFERENCES `subject_type` (`id`),
  CONSTRAINT `subject_ibfk_4` FOREIGN KEY (`subject_type_id`) REFERENCES `subject_type` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `subject`
--

LOCK TABLES `subject` WRITE;
/*!40000 ALTER TABLE `subject` DISABLE KEYS */;
INSERT INTO `subject` VALUES (27,'သဒ္ဒါ',16),(28,'သင်္ဂဟ',16),(29,'ကျမ်းရင်း',16),(30,'သဒ္ဒါ',17),(31,'သင်္ဂဟ',17),(32,'ကျမ်းရင်း',17),(33,'သဒ္ဒါ',18),(34,'သင်္ဂဟ',18),(35,'ကျမ်းရင်း',18);
/*!40000 ALTER TABLE `subject` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `subject_type`
--

DROP TABLE IF EXISTS `subject_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `subject_type` (
  `id` tinyint NOT NULL AUTO_INCREMENT,
  `name` varchar(200) DEFAULT NULL,
  `grade_id` tinyint DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `subject_type`
--

LOCK TABLES `subject_type` WRITE;
/*!40000 ALTER TABLE `subject_type` DISABLE KEYS */;
INSERT INTO `subject_type` VALUES (16,'ဝိနည်း',11),(17,'အင်္ဂုတ္တိုရ်',11),(18,'ဓမ္မပဒ',11),(19,'ဝိနည်း',12),(20,'အင်္ဂုတ္တိုရ်',12),(21,'ဓမ္မပဒ',12);
/*!40000 ALTER TABLE `subject_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_table`
--

DROP TABLE IF EXISTS `user_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_table` (
  `id` tinyint NOT NULL AUTO_INCREMENT,
  `first_name` varchar(70) DEFAULT NULL,
  `last_name` varchar(70) DEFAULT NULL,
  `user_name` varchar(100) DEFAULT NULL,
  `password` varchar(500) DEFAULT NULL,
  `role_id` tinyint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `role_id` (`role_id`),
  CONSTRAINT `user_table_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_table`
--

LOCK TABLES `user_table` WRITE;
/*!40000 ALTER TABLE `user_table` DISABLE KEYS */;
INSERT INTO `user_table` VALUES (2,'Ye Yint','Aung','yya','M@hawiharra2021',1),(8,'Ye','Aung','yya@gmail.com','#@$her.Yya2497',1),(9,'Mg Mg','Myint','Examuser#123','#@$her.Exam123',2);
/*!40000 ALTER TABLE `user_table` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-03-11 16:21:45
