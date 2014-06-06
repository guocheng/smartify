SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema smartify
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `smartify` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
USE `smartify` ;

-- -----------------------------------------------------
-- Table `smartify`.`hub`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `smartify`.`hub` ;

CREATE TABLE IF NOT EXISTS `smartify`.`hub` (
  `id` CHAR(17) NOT NULL,
  `hardware_ver` VARCHAR(30) NULL,
  `firmware_ver` VARCHAR(30) NULL,
  `software_ver` VARCHAR(30) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `smartify`.`gadget`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `smartify`.`gadget` ;

CREATE TABLE IF NOT EXISTS `smartify`.`gadget` (
  `id` CHAR(17) NOT NULL,
  `hub_id` CHAR(17) NOT NULL,
  `type` VARCHAR(30) NULL,
  `user_defined_name` VARCHAR(30) NULL,
  `availability` VARCHAR(10) NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_gadget_hub_idx` (`hub_id` ASC),
  CONSTRAINT `fk_gadget_hub`
    FOREIGN KEY (`hub_id`)
    REFERENCES `smartify`.`hub` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `smartify`.`phone`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `smartify`.`phone` ;

CREATE TABLE IF NOT EXISTS `smartify`.`phone` (
  `id` VARCHAR(20) NOT NULL,
  `hub_id` CHAR(17) NOT NULL,
  `model` VARCHAR(30) NULL,
  `manufacturer` VARCHAR(30) NULL,
  `os` VARCHAR(30) NULL,
  `os_ver` VARCHAR(30) NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_phone_hub1_idx` (`hub_id` ASC),
  CONSTRAINT `fk_phone_hub1`
    FOREIGN KEY (`hub_id`)
    REFERENCES `smartify`.`hub` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `smartify`.`job`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `smartify`.`job` ;

CREATE TABLE IF NOT EXISTS `smartify`.`job` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `hub_id` CHAR(17) NOT NULL,
  `result` VARCHAR(30) NULL,
  `error_msg` VARCHAR(100) NULL,
  `start_time` DATETIME NULL,
  `end_time` DATETIME NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_job_hub1_idx` (`hub_id` ASC),
  CONSTRAINT `fk_job_hub1`
    FOREIGN KEY (`hub_id`)
    REFERENCES `smartify`.`hub` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `smartify`.`event`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `smartify`.`event` ;

CREATE TABLE IF NOT EXISTS `smartify`.`event` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `sender_id` VARCHAR(30) NULL,
  `sender_type` VARCHAR(30) NULL,
  `receiver_id` VARCHAR(30) NULL,
  `receiver_type` VARCHAR(30) NULL,
  `body` VARCHAR(100) NULL,
  `event_time` DATETIME NULL,
  `job_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_event_job1_idx` (`job_id` ASC),
  CONSTRAINT `fk_event_job1`
    FOREIGN KEY (`job_id`)
    REFERENCES `smartify`.`job` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `smartify`.`gadget_log`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `smartify`.`gadget_log` ;

CREATE TABLE IF NOT EXISTS `smartify`.`gadget_log` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `access_time` DATETIME NULL,
  `command_type` VARCHAR(100) NULL,
  `connection_type` VARCHAR(30) NULL,
  `trigger_id` VARCHAR(30) NULL,
  `trigger_type` VARCHAR(30) NULL,
  `gadget_id` CHAR(17) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_gadget_log_gadget1_idx` (`gadget_id` ASC),
  CONSTRAINT `fk_gadget_log_gadget1`
    FOREIGN KEY (`gadget_id`)
    REFERENCES `smartify`.`gadget` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `smartify`.`hub_log`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `smartify`.`hub_log` ;

CREATE TABLE IF NOT EXISTS `smartify`.`hub_log` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `access_time` DATETIME NULL,
  `command_type` VARCHAR(100) NULL,
  `connection_type` VARCHAR(30) NULL,
  `hub_id` CHAR(17) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_hub_log_hub1_idx` (`hub_id` ASC),
  CONSTRAINT `fk_hub_log_hub1`
    FOREIGN KEY (`hub_id`)
    REFERENCES `smartify`.`hub` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `smartify`.`test`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `smartify`.`test` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL,
  `time` DATETIME NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
