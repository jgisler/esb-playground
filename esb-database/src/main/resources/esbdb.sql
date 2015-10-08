-- MySQL Script generated by MySQL Workbench
-- Thu Oct  8 13:57:08 2015
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema esbdb
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `esbdb` ;

-- -----------------------------------------------------
-- Schema esbdb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `esbdb` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
USE `esbdb` ;

-- -----------------------------------------------------
-- Table `esbdb`.`business`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `esbdb`.`business` ;

CREATE TABLE IF NOT EXISTS `esbdb`.`business` (
  `business_id` INT(32) UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(128) NOT NULL,
  `description` VARCHAR(256) NOT NULL,
  PRIMARY KEY (`business_id`))
ENGINE = InnoDB;

CREATE UNIQUE INDEX `business_id_UNIQUE` ON `esbdb`.`business` (`business_id` ASC);

CREATE UNIQUE INDEX `business_name_UNIQUE` ON `esbdb`.`business` (`name` ASC);


-- -----------------------------------------------------
-- Table `esbdb`.`service`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `esbdb`.`service` ;

CREATE TABLE IF NOT EXISTS `esbdb`.`service` (
  `service_id` INT(32) UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(128) NOT NULL,
  `description` VARCHAR(256) NULL,
  `business_id` INT(32) UNSIGNED NOT NULL,
  PRIMARY KEY (`service_id`),
  CONSTRAINT `service2business_fk`
    FOREIGN KEY (`business_id`)
    REFERENCES `esbdb`.`business` (`business_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE UNIQUE INDEX `service_id_UNIQUE` ON `esbdb`.`service` (`service_id` ASC);

CREATE UNIQUE INDEX `name_business_UNIQUE` ON `esbdb`.`service` (`business_id` ASC, `name` ASC);

CREATE INDEX `name_idx` ON `esbdb`.`service` (`name` ASC);

CREATE INDEX `business_id_idx` ON `esbdb`.`service` (`business_id` ASC);


-- -----------------------------------------------------
-- Table `esbdb`.`endpoint`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `esbdb`.`endpoint` ;

CREATE TABLE IF NOT EXISTS `esbdb`.`endpoint` (
  `endpoint_id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `key` VARCHAR(45) NULL,
  `value` VARCHAR(256) NULL,
  `service_id` INT(32) UNSIGNED NOT NULL,
  PRIMARY KEY (`endpoint_id`),
  CONSTRAINT `service_id_fk`
    FOREIGN KEY (`service_id`)
    REFERENCES `esbdb`.`service` (`service_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE UNIQUE INDEX `endpoint_id_UNIQUE` ON `esbdb`.`endpoint` (`endpoint_id` ASC);

CREATE UNIQUE INDEX `key_service_UNQ` ON `esbdb`.`endpoint` (`service_id` ASC, `key` ASC);


-- -----------------------------------------------------
-- Table `esbdb`.`credential`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `esbdb`.`credential` ;

CREATE TABLE IF NOT EXISTS `esbdb`.`credential` (
  `credential_id` INT(32) UNSIGNED NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(128) NOT NULL,
  `password` VARCHAR(256) NOT NULL,
  `endpoint_id` INT(32) UNSIGNED NOT NULL,
  PRIMARY KEY (`credential_id`),
  CONSTRAINT `credential2endpoint`
    FOREIGN KEY (`endpoint_id`)
    REFERENCES `esbdb`.`endpoint` (`endpoint_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE UNIQUE INDEX `credential_id_UNIQUE` ON `esbdb`.`credential` (`credential_id` ASC);

CREATE INDEX `credential2endpoint` ON `esbdb`.`credential` (`endpoint_id` ASC);

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;