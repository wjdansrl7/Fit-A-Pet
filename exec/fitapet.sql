-- MySQL dump 10.13  Distrib 9.1.0, for Linux (x86_64)
--
-- Host: localhost    Database: fitapet
-- ------------------------------------------------------
-- Server version       9.1.0

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0                                                                                                     */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `diet`
--

DROP TABLE IF EXISTS `diet`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `diet` (
  `calorie` double DEFAULT NULL,
  `carbo` double DEFAULT NULL,
  `carbo_ratio` double DEFAULT NULL,
  `cholesterol` double DEFAULT NULL,
  `cholesterol_ratio` double DEFAULT NULL,
  `fat` double DEFAULT NULL,
  `fat_ratio` double DEFAULT NULL,
  `protein` double DEFAULT NULL,
  `protein_ratio` double DEFAULT NULL,
  `saturated_fat` double DEFAULT NULL,
  `saturated_fat_ratio` double DEFAULT NULL,
  `sodium` double DEFAULT NULL,
  `sodium_ratio` double DEFAULT NULL,
  `sugar` double DEFAULT NULL,
  `sugar_ratio` double DEFAULT NULL,
  `trans_fat` double DEFAULT NULL,
  `trans_fat_ratio` double DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `deleted_at` datetime(6) DEFAULT NULL,
  `diet_id` bigint NOT NULL AUTO_INCREMENT,
  `modified_at` datetime(6) DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`diet_id`),
  KEY `FKro8td081sdv1r1btel5d3lkw5` (`user_id`),
  CONSTRAINT `FKro8td081sdv1r1btel5d3lkw5` FOREIGN KEY (`user_id`) REFERENCES `                                                                                                    user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `diet`
--

LOCK TABLES `diet` WRITE;
/*!40000 ALTER TABLE `diet` DISABLE KEYS */;
/*!40000 ALTER TABLE `diet` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `guild`
--

DROP TABLE IF EXISTS `guild`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `guild` (
  `created_at` datetime(6) DEFAULT NULL,
  `deleted_at` datetime(6) DEFAULT NULL,
  `guild_id` bigint NOT NULL AUTO_INCREMENT,
  `guild_leader_user_id` bigint DEFAULT NULL,
  `modified_at` datetime(6) DEFAULT NULL,
  `guild_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`guild_id`),
  KEY `FKaiglqlhcad5kveb24d7tjy4m` (`guild_leader_user_id`),
  CONSTRAINT `FKaiglqlhcad5kveb24d7tjy4m` FOREIGN KEY (`guild_leader_user_id`)                                                                                                     REFERENCES `user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `guild`
--

LOCK TABLES `guild` WRITE;
/*!40000 ALTER TABLE `guild` DISABLE KEYS */;
/*!40000 ALTER TABLE `guild` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `guild_quest`
--

DROP TABLE IF EXISTS `guild_quest`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `guild_quest` (
  `guild_id` bigint DEFAULT NULL,
  `guild_quest_id` bigint NOT NULL AUTO_INCREMENT,
  `quest_id` bigint DEFAULT NULL,
  PRIMARY KEY (`guild_quest_id`),
  UNIQUE KEY `UKgrmoea8ydskd6n4wdsmerc1vo` (`guild_id`),
  KEY `FKdxovpfbj3btlhvsd5jf9lu985` (`quest_id`),
  CONSTRAINT `FKc9i3fvqbw50yi2lesgvwuq888` FOREIGN KEY (`guild_id`) REFERENCES                                                                                                     `guild` (`guild_id`),
  CONSTRAINT `FKdxovpfbj3btlhvsd5jf9lu985` FOREIGN KEY (`quest_id`) REFERENCES                                                                                                     `quest` (`quest_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `guild_quest`
--

LOCK TABLES `guild_quest` WRITE;
/*!40000 ALTER TABLE `guild_quest` DISABLE KEYS */;
/*!40000 ALTER TABLE `guild_quest` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `health`
--

DROP TABLE IF EXISTS `health`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `health` (
  `sleep_time` int DEFAULT NULL,
  `step_cnt` int DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `deleted_at` datetime(6) DEFAULT NULL,
  `health_id` bigint NOT NULL AUTO_INCREMENT,
  `modified_at` datetime(6) DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`health_id`),
  KEY `FKjvfubxdf6wu55f20gpipfrrte` (`user_id`),
  CONSTRAINT `FKjvfubxdf6wu55f20gpipfrrte` FOREIGN KEY (`user_id`) REFERENCES `                                                                                                    user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `health`
--

LOCK TABLES `health` WRITE;
/*!40000 ALTER TABLE `health` DISABLE KEYS */;
/*!40000 ALTER TABLE `health` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `map`
--

DROP TABLE IF EXISTS `map`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `map` (
  `guild_id` bigint DEFAULT NULL,
  `guild_position` bigint DEFAULT NULL,
  `map_id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`map_id`),
  KEY `FKld6b9d0taka5j6ig4lvmsp5lg` (`guild_id`),
  KEY `FK6gmfcxbbakt3hloxov5e314t4` (`user_id`),
  CONSTRAINT `FK6gmfcxbbakt3hloxov5e314t4` FOREIGN KEY (`user_id`) REFERENCES `                                                                                                    user` (`user_id`),
  CONSTRAINT `FKld6b9d0taka5j6ig4lvmsp5lg` FOREIGN KEY (`guild_id`) REFERENCES                                                                                                     `guild` (`guild_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `map`
--

LOCK TABLES `map` WRITE;
/*!40000 ALTER TABLE `map` DISABLE KEYS */;
/*!40000 ALTER TABLE `map` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `personal_quest`
--

DROP TABLE IF EXISTS `personal_quest`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `personal_quest` (
  `quest_status` bit(1) NOT NULL,
  `personal_quest_id` bigint NOT NULL AUTO_INCREMENT,
  `quest_id` bigint DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`personal_quest_id`),
  KEY `FKmmcpkdixa3rtbieepl1s34orb` (`quest_id`),
  KEY `FKs4p4fhgjltu1vxr7j6g71hrk4` (`user_id`),
  CONSTRAINT `FKmmcpkdixa3rtbieepl1s34orb` FOREIGN KEY (`quest_id`) REFERENCES                                                                                                     `quest` (`quest_id`),
  CONSTRAINT `FKs4p4fhgjltu1vxr7j6g71hrk4` FOREIGN KEY (`user_id`) REFERENCES `                                                                                                    user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `personal_quest`
--

LOCK TABLES `personal_quest` WRITE;
/*!40000 ALTER TABLE `personal_quest` DISABLE KEYS */;
/*!40000 ALTER TABLE `personal_quest` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pet`
--

DROP TABLE IF EXISTS `pet`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pet` (
  `evolution_level` int DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `deleted_at` datetime(6) DEFAULT NULL,
  `modified_at` datetime(6) DEFAULT NULL,
  `pet_id` bigint NOT NULL AUTO_INCREMENT,
  `pet_status` enum('ADULT','EGG','SUBADULT') DEFAULT NULL,
  `pet_type` enum('BELUGA','CHINCHILLA','LION','WEASEL','WHALE') DEFAULT NULL,
  PRIMARY KEY (`pet_id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_                                                                                                    ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pet`
--

LOCK TABLES `pet` WRITE;
/*!40000 ALTER TABLE `pet` DISABLE KEYS */;
INSERT INTO `pet` VALUES (10,'2024-11-19 10:40:07.642341',NULL,'2024-11-19 10:4                                                                                                    0:07.642341',1,'EGG','BELUGA'),(20,'2024-11-19 10:40:07.656457',NULL,'2024-11-1                                                                                                    9 10:40:07.656457',2,'SUBADULT','BELUGA'),(30,'2024-11-19 10:40:07.658202',NULL                                                                                                    ,'2024-11-19 10:40:07.658202',3,'ADULT','BELUGA'),(10,'2024-11-19 10:40:07.6598                                                                                                    15',NULL,'2024-11-19 10:40:07.659815',4,'EGG','CHINCHILLA'),(20,'2024-11-19 10:                                                                                                    40:07.661536',NULL,'2024-11-19 10:40:07.661536',5,'SUBADULT','CHINCHILLA'),(30,                                                                                                    '2024-11-19 10:40:07.663119',NULL,'2024-11-19 10:40:07.663119',6,'ADULT','CHINC                                                                                                    HILLA'),(10,'2024-11-19 10:40:07.664659',NULL,'2024-11-19 10:40:07.664659',7,'E                                                                                                    GG','LION'),(20,'2024-11-19 10:40:07.666287',NULL,'2024-11-19 10:40:07.666287',                                                                                                    8,'SUBADULT','LION'),(30,'2024-11-19 10:40:07.668224',NULL,'2024-11-19 10:40:07                                                                                                    .668224',9,'ADULT','LION'),(10,'2024-11-19 10:40:07.669809',NULL,'2024-11-19 10                                                                                                    :40:07.669809',10,'EGG','WEASEL'),(20,'2024-11-19 10:40:07.671402',NULL,'2024-1                                                                                                    1-19 10:40:07.671402',11,'SUBADULT','WEASEL'),(30,'2024-11-19 10:40:07.672994',                                                                                                    NULL,'2024-11-19 10:40:07.672994',12,'ADULT','WEASEL'),(10,'2024-11-19 10:40:07                                                                                                    .674566',NULL,'2024-11-19 10:40:07.674566',13,'EGG','WHALE'),(20,'2024-11-19 10                                                                                                    :40:07.676089',NULL,'2024-11-19 10:40:07.676089',14,'SUBADULT','WHALE'),(30,'20                                                                                                    24-11-19 10:40:07.677845',NULL,'2024-11-19 10:40:07.677845',15,'ADULT','WHALE')                                                                                                    ;
/*!40000 ALTER TABLE `pet` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pet_book`
--

DROP TABLE IF EXISTS `pet_book`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pet_book` (
  `issue_egg` bit(1) NOT NULL,
  `pet_exp` int DEFAULT NULL,
  `pet_level` int DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `deleted_at` datetime(6) DEFAULT NULL,
  `modified_at` datetime(6) DEFAULT NULL,
  `pet_book_id` bigint NOT NULL AUTO_INCREMENT,
  `pet_id` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  `pet_nickname` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`pet_book_id`),
  KEY `FKscpvbmh0236tpoc0pdca7lp3` (`pet_id`),
  KEY `FKbtcujp4l6smphbtq27gwlw3jg` (`user_id`),
  CONSTRAINT `FKbtcujp4l6smphbtq27gwlw3jg` FOREIGN KEY (`user_id`) REFERENCES `                                                                                                    user` (`user_id`),
  CONSTRAINT `FKscpvbmh0236tpoc0pdca7lp3` FOREIGN KEY (`pet_id`) REFERENCES `pe                                                                                                    t` (`pet_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pet_book`
--

LOCK TABLES `pet_book` WRITE;
/*!40000 ALTER TABLE `pet_book` DISABLE KEYS */;
/*!40000 ALTER TABLE `pet_book` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `quest`
--

DROP TABLE IF EXISTS `quest`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `quest` (
  `quest_id` bigint NOT NULL AUTO_INCREMENT,
  `quest_reward` bigint NOT NULL,
  `quest_content` varchar(255) DEFAULT NULL,
  `quest_name` varchar(255) DEFAULT NULL,
  `quest_category` enum('DIET','SLEEP','WALK') DEFAULT NULL,
  `quest_tier` enum('EASY','HARD','NORMAL') DEFAULT NULL,
  `quest_type` enum('GROUP','PERSONAL') DEFAULT NULL,
  PRIMARY KEY (`quest_id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_                                                                                                    ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `quest`
--

LOCK TABLES `quest` WRITE;
/*!40000 ALTER TABLE `quest` DISABLE KEYS */;
INSERT INTO `quest` VALUES (1,200,'5000걸음','즐거운 산책','WALK','EASY','PERSO                                                                                                    NAL'),(2,200,'10000걸음','좀 더 멀리','WALK','NORMAL','PERSONAL'),(3,200,'15000                                                                                                    걸음','걸어서 세계속으로','WALK','HARD','PERSONAL'),(4,200,'7시간','개운한 시작                                                                                                    ','SLEEP','EASY','PERSONAL'),(5,200,'8시간','깊은 숙면','SLEEP','NORMAL','PERSO                                                                                                    NAL'),(6,200,'9시간','살아계신가요?','SLEEP','HARD','PERSONAL'),(7,200,'탄단지1                                                                                                    ','밥은 먹고 하자','DIET','EASY','PERSONAL'),(8,200,'탄단지2','잘 먹는 사람','D                                                                                                    IET','NORMAL','PERSONAL'),(9,200,'탄단지3','식단 관리의 신','DIET','HARD','PERS                                                                                                    ONAL'),(10,300,'5000걸음','즐거운 산책','WALK','EASY','GROUP'),(11,300,'10000걸                                                                                                    음','좀 더 멀리','WALK','NORMAL','GROUP'),(12,300,'15000걸음','걸어서 세계속으                                                                                                    로','WALK','HARD','GROUP'),(13,300,'7시간','개운한 시작','SLEEP','EASY','GROUP'                                                                                                    ),(14,300,'8시간','깊은 숙면','SLEEP','NORMAL','GROUP'),(15,300,'9시간','살아계                                                                                                    신가요?','SLEEP','HARD','GROUP'),(16,300,'탄단지1','밥은 먹고 하자','DIET','EAS                                                                                                    Y','GROUP'),(17,300,'탄단지2','잘 먹는 사람','DIET','NORMAL','GROUP'),(18,300,'                                                                                                    탄단지3','식단 관리의 신','DIET','HARD','GROUP');
/*!40000 ALTER TABLE `quest` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `created_at` datetime(6) DEFAULT NULL,
  `deleted_at` datetime(6) DEFAULT NULL,
  `modified_at` datetime(6) DEFAULT NULL,
  `pet_main_id` bigint DEFAULT NULL,
  `user_id` bigint NOT NULL AUTO_INCREMENT,
  `provider` varchar(255) DEFAULT NULL,
  `provider_id` varchar(255) DEFAULT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  `user_unique_name` varchar(255) DEFAULT NULL,
  `role` enum('ADMIN','USER') DEFAULT NULL,
  `user_tier` enum('EASY','HARD','NORMAL') DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_quest_status`
--

DROP TABLE IF EXISTS `user_quest_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_quest_status` (
  `quest_status` bit(1) DEFAULT NULL,
  `guild_quest_id` bigint DEFAULT NULL,
  `quest_status_id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`quest_status_id`),
  KEY `FK9spauhvd355mo4plmq2rkxsv` (`guild_quest_id`),
  KEY `FK3m61ydwkiev5wp6in0vrcn3kj` (`user_id`),
  CONSTRAINT `FK3m61ydwkiev5wp6in0vrcn3kj` FOREIGN KEY (`user_id`) REFERENCES `                                                                                                    user` (`user_id`),
  CONSTRAINT `FK9spauhvd355mo4plmq2rkxsv` FOREIGN KEY (`guild_quest_id`) REFERE                                                                                                    NCES `guild_quest` (`guild_quest_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_quest_status`
--

LOCK TABLES `user_quest_status` WRITE;
/*!40000 ALTER TABLE `user_quest_status` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_quest_status` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
