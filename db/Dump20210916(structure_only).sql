CREATE DATABASE  IF NOT EXISTS `final_web_project` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `final_web_project`;
-- MySQL dump 10.13  Distrib 8.0.26, for Linux (x86_64)
--
-- Host: localhost    Database: final_web_project
-- ------------------------------------------------------
-- Server version	8.0.26-0ubuntu0.20.04.2

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
-- Table structure for table `announcement_categories`
--

DROP TABLE IF EXISTS `announcement_categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `announcement_categories` (
  `category_id` int NOT NULL AUTO_INCREMENT,
  `category_description` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`category_id`),
  UNIQUE KEY `category_description_UNIQUE` (`category_description`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `announcement_images`
--

DROP TABLE IF EXISTS `announcement_images`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `announcement_images` (
  `record_id` int NOT NULL AUTO_INCREMENT,
  `announcement_id` int NOT NULL,
  `image_id` int NOT NULL,
  PRIMARY KEY (`record_id`),
  KEY `fk_annoncement_images_announcements_announcement_id_idx` (`announcement_id`),
  KEY `fk_annoncement_images_images_image_id_idx` (`image_id`),
  CONSTRAINT `fk_annoncement_images_announcements_announcement_id` FOREIGN KEY (`announcement_id`) REFERENCES `announcements` (`announcement_id`),
  CONSTRAINT `fk_annoncement_images_images_image_id` FOREIGN KEY (`image_id`) REFERENCES `images` (`image_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `announcement_statuses`
--

DROP TABLE IF EXISTS `announcement_statuses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `announcement_statuses` (
  `status_id` int NOT NULL AUTO_INCREMENT,
  `status_description` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`status_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `announcements`
--

DROP TABLE IF EXISTS `announcements`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `announcements` (
  `announcement_id` int NOT NULL AUTO_INCREMENT,
  `owner_id` int NOT NULL,
  `title` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `price` decimal(12,2) DEFAULT '0.00',
  `primary_image` int DEFAULT '1',
  `description` longtext COLLATE utf8_unicode_ci NOT NULL,
  `publication_date` timestamp NOT NULL,
  `announcement_status_id` int NOT NULL DEFAULT '1',
  `announcement_category_id` int NOT NULL DEFAULT '10',
  PRIMARY KEY (`announcement_id`),
  KEY `fk_announcements_users_user_id_idx` (`owner_id`),
  KEY `fk_announcements_announcement_statuses_status_id_idx` (`announcement_status_id`),
  KEY `fk_announcements_announcement_categories_category_id_idx` (`announcement_category_id`),
  CONSTRAINT `fk_announcements_announcement_categories_category_id` FOREIGN KEY (`announcement_category_id`) REFERENCES `announcement_categories` (`category_id`),
  CONSTRAINT `fk_announcements_announcement_statuses_status_id` FOREIGN KEY (`announcement_status_id`) REFERENCES `announcement_statuses` (`status_id`),
  CONSTRAINT `fk_announcements_users_user_id` FOREIGN KEY (`owner_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `dialog_types`
--

DROP TABLE IF EXISTS `dialog_types`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `dialog_types` (
  `type_id` int NOT NULL AUTO_INCREMENT,
  `type_description` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`type_id`),
  UNIQUE KEY `type_description_UNIQUE` (`type_description`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `dialogs`
--

DROP TABLE IF EXISTS `dialogs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `dialogs` (
  `dialog_id` int NOT NULL AUTO_INCREMENT,
  `type_id` int NOT NULL DEFAULT '1',
  `announcement_id` int DEFAULT NULL,
  PRIMARY KEY (`dialog_id`),
  KEY `fk_dialogs_announcements_announcement_id_idx` (`announcement_id`),
  KEY `fk_dialogs_dialog_types_type_id_idx` (`type_id`),
  CONSTRAINT `fk_dialogs_announcements_announcement_id` FOREIGN KEY (`announcement_id`) REFERENCES `announcements` (`announcement_id`),
  CONSTRAINT `fk_dialogs_dialog_types_type_id` FOREIGN KEY (`type_id`) REFERENCES `dialog_types` (`type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `images`
--

DROP TABLE IF EXISTS `images`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `images` (
  `image_id` int NOT NULL AUTO_INCREMENT,
  `upload_data` timestamp NOT NULL,
  `uploaded_by` int NOT NULL,
  `bin_image` mediumblob NOT NULL,
  PRIMARY KEY (`image_id`),
  KEY `fk_images_users_user_id_idx` (`uploaded_by`),
  CONSTRAINT `fk_images_users_user_id` FOREIGN KEY (`uploaded_by`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `messages`
--

DROP TABLE IF EXISTS `messages`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `messages` (
  `message_id` int NOT NULL AUTO_INCREMENT,
  `dialog_id` int NOT NULL,
  `sender_id` int NOT NULL,
  `sent_time` timestamp NOT NULL,
  `text_content` longtext COLLATE utf8_unicode_ci,
  `graphic_content` tinyint NOT NULL,
  `image_id` int DEFAULT NULL,
  PRIMARY KEY (`message_id`),
  KEY `fk_messages_users_user_id_sender_idx` (`sender_id`),
  KEY `fk_messages_images_image_id_idx` (`image_id`),
  KEY `fk_messages_dialogs_dialog_id_idx` (`dialog_id`),
  CONSTRAINT `fk_messages_dialogs_dialog_id` FOREIGN KEY (`dialog_id`) REFERENCES `dialogs` (`dialog_id`),
  CONSTRAINT `fk_messages_images_image_id` FOREIGN KEY (`image_id`) REFERENCES `images` (`image_id`),
  CONSTRAINT `fk_messages_users_user_id_sender` FOREIGN KEY (`sender_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_dialogs`
--

DROP TABLE IF EXISTS `user_dialogs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_dialogs` (
  `record_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `dialog_id` int NOT NULL,
  `visible` tinyint NOT NULL DEFAULT '1',
  PRIMARY KEY (`record_id`),
  KEY `fk_user_dialogs_users_user_id_idx` (`user_id`),
  KEY `fk_user_dialogs_dialogs_dialog_id_idx` (`dialog_id`),
  CONSTRAINT `fk_user_dialogs_dialogs_dialog_id` FOREIGN KEY (`dialog_id`) REFERENCES `dialogs` (`dialog_id`),
  CONSTRAINT `fk_user_dialogs_users_user_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_roles`
--

DROP TABLE IF EXISTS `user_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_roles` (
  `role_id` int NOT NULL AUTO_INCREMENT,
  `role_description` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`role_id`),
  UNIQUE KEY `role_description_UNIQUE` (`role_description`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_statuses`
--

DROP TABLE IF EXISTS `user_statuses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_statuses` (
  `status_id` int NOT NULL AUTO_INCREMENT,
  `status_description` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`status_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `first_name` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `last_name` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `login` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `password` varchar(60) CHARACTER SET big5 COLLATE big5_chinese_ci NOT NULL,
  `email` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `phone` varchar(12) COLLATE utf8_unicode_ci NOT NULL,
  `role_id` int NOT NULL DEFAULT '1',
  `status_id` int NOT NULL DEFAULT '1',
  `about` longtext COLLATE utf8_unicode_ci,
  `profile_image_id` int DEFAULT '3',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `login_UNIQUE` (`login`),
  UNIQUE KEY `phone_UNIQUE` (`phone`),
  UNIQUE KEY `email_UNIQUE` (`email`),
  KEY `fk_users_images_image_id_idx` (`profile_image_id`),
  KEY `fk_users_user_roles_role_id_idx` (`role_id`),
  KEY `fk_users_user_statuses_status_id_idx` (`status_id`),
  CONSTRAINT `fk_users_images_image_id` FOREIGN KEY (`profile_image_id`) REFERENCES `images` (`image_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_users_user_roles_role_id` FOREIGN KEY (`role_id`) REFERENCES `user_roles` (`role_id`),
  CONSTRAINT `fk_users_user_statuses_status_id` FOREIGN KEY (`status_id`) REFERENCES `user_statuses` (`status_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-09-16 14:42:13
