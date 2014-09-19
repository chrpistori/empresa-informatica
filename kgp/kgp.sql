SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci ;
CREATE SCHEMA IF NOT EXISTS `kgp` DEFAULT CHARACTER SET latin1 ;
USE `mydb` ;
USE `kgp` ;

-- -----------------------------------------------------
-- Table `kgp`.`cliente`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `kgp`.`cliente` (
  `id` INT(10) NOT NULL AUTO_INCREMENT ,
  `nome` VARCHAR(50) NULL DEFAULT NULL ,
  `tipo_pessoa` VARCHAR(20) NULL DEFAULT NULL ,
  `logradouro` VARCHAR(20) NULL DEFAULT NULL ,
  `endereco` VARCHAR(50) NULL DEFAULT NULL ,
  `numero` INT(5) NULL DEFAULT NULL ,
  `cep` VARCHAR(10) NULL DEFAULT NULL ,
  `bairro` VARCHAR(30) NULL DEFAULT NULL ,
  `telefone_1` VARCHAR(15) NULL DEFAULT NULL ,
  `telefone_2` VARCHAR(15) NULL DEFAULT NULL ,
  `contato` VARCHAR(50) NULL DEFAULT NULL ,
  `email` VARCHAR(50) NULL DEFAULT NULL ,
  `data_registro` DATE NULL DEFAULT NULL ,
  `ativo` BIT(1) NULL DEFAULT NULL ,
  `conta` INT(10) NULL DEFAULT NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB
AUTO_INCREMENT = 6
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `kgp`.`usuario`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `kgp`.`usuario` (
  `id` INT(10) NOT NULL AUTO_INCREMENT ,
  `email` VARCHAR(50) NULL DEFAULT NULL ,
  `telefone_1` VARCHAR(15) NULL DEFAULT NULL ,
  `telefone_2` VARCHAR(15) NULL DEFAULT NULL ,
  `nome` VARCHAR(50) NULL DEFAULT NULL ,
  `usuario` VARCHAR(15) NULL DEFAULT NULL ,
  `senha` VARCHAR(40) NULL DEFAULT NULL ,
  `data_registro` DATE NULL DEFAULT NULL ,
  `perfil` INT(11) NULL DEFAULT NULL ,
  `conta` INT(10) NULL DEFAULT NULL ,
  `ativo` BIT(1) NULL DEFAULT NULL ,
  `criador` INT(10) NULL DEFAULT NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB
AUTO_INCREMENT = 21
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `kgp`.`projeto`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `kgp`.`projeto` (
  `id` INT(10) NOT NULL AUTO_INCREMENT ,
  `id_cliente` INT(10) NOT NULL ,
  `id_gerente` INT(10) NOT NULL ,
  `nome` VARCHAR(50) NULL DEFAULT NULL ,
  `descricao` VARCHAR(300) NULL DEFAULT NULL ,
  `dataInicio` DATE NULL DEFAULT NULL ,
  `data_termino` DATE NULL DEFAULT NULL ,
  `data_registro` DATE NULL DEFAULT NULL ,
  `ativo` BIT(1) NULL DEFAULT NULL ,
  `conta` INT(10) NULL DEFAULT NULL ,
  `criador` INT(10) NULL DEFAULT NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `projeto_ibfk_1` (`id_cliente` ASC) ,
  INDEX `projeto_ibfk_2` (`id_gerente` ASC) ,
  CONSTRAINT `projeto_ibfk_1`
    FOREIGN KEY (`id_cliente` )
    REFERENCES `kgp`.`cliente` (`id` ),
  CONSTRAINT `projeto_ibfk_2`
    FOREIGN KEY (`id_gerente` )
    REFERENCES `kgp`.`usuario` (`id` ))
ENGINE = InnoDB
AUTO_INCREMENT = 14
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `kgp`.`iteracao`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `kgp`.`iteracao` (
  `id` INT(10) NOT NULL AUTO_INCREMENT ,
  `id_projeto` INT(10) NOT NULL ,
  `nome` VARCHAR(30) NULL DEFAULT NULL ,
  `limite_tarefas` INT(10) NULL DEFAULT NULL ,
  `data_inicio` DATE NULL DEFAULT NULL ,
  `data_termino` DATE NULL DEFAULT NULL ,
  `data_registro` DATE NULL DEFAULT NULL ,
  `ativo` BIT(1) NULL DEFAULT NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `id_projeto` (`id_projeto` ASC) ,
  CONSTRAINT `iteracao_ibfk_1`
    FOREIGN KEY (`id_projeto` )
    REFERENCES `kgp`.`projeto` (`id` ))
ENGINE = InnoDB
AUTO_INCREMENT = 5
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `kgp`.`burndown`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `kgp`.`burndown` (
  `id` INT(10) NOT NULL AUTO_INCREMENT ,
  `dia` INT(2) NULL DEFAULT NULL ,
  `mes` INT(2) NULL DEFAULT NULL ,
  `horas_pendentes` INT(5) NULL DEFAULT NULL ,
  `id_iteracao` INT(10) NOT NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `id_iteracao` (`id_iteracao` ASC) ,
  CONSTRAINT `burndown_ibfk_1`
    FOREIGN KEY (`id_iteracao` )
    REFERENCES `kgp`.`iteracao` (`id` ))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `kgp`.`cfd`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `kgp`.`cfd` (
  `id` INT(10) NOT NULL AUTO_INCREMENT ,
  `dia` INT(2) NULL DEFAULT NULL ,
  `mes` INT(2) NULL DEFAULT NULL ,
  `qtde_tarefa` INT(5) NULL DEFAULT NULL ,
  `situacao` INT(1) NULL DEFAULT NULL ,
  `id_projeto` INT(10) NOT NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `id_projeto` (`id_projeto` ASC) ,
  CONSTRAINT `cfd_ibfk_1`
    FOREIGN KEY (`id_projeto` )
    REFERENCES `kgp`.`projeto` (`id` ))
ENGINE = InnoDB
AUTO_INCREMENT = 5
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `kgp`.`tarefa`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `kgp`.`tarefa` (
  `id` INT(10) NOT NULL AUTO_INCREMENT ,
  `id_iteracao` INT(10) NOT NULL ,
  `id_usuario` INT(10) NULL DEFAULT NULL ,
  `situacao` INT(10) NULL DEFAULT NULL ,
  `prioridade` INT(10) NULL DEFAULT NULL ,
  `nome` VARCHAR(50) NULL DEFAULT NULL ,
  `descricao` VARCHAR(300) NULL DEFAULT NULL ,
  `duracao` INT(10) NULL DEFAULT NULL ,
  `horas_pendentes` INT(10) NULL DEFAULT NULL ,
  `horas_trabalhadas` INT(10) NULL DEFAULT NULL ,
  `data_registro` DATE NULL DEFAULT NULL ,
  `ativo` BIT(1) NULL DEFAULT NULL ,
  `conta` INT(10) NULL DEFAULT NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `id_iteracao` (`id_iteracao` ASC) ,
  INDEX `usuario_ibfk_1` (`id_usuario` ASC) ,
  CONSTRAINT `tarefa_ibfk_1`
    FOREIGN KEY (`id_iteracao` )
    REFERENCES `kgp`.`iteracao` (`id` ),
  CONSTRAINT `usuario_ibfk_1`
    FOREIGN KEY (`id_usuario` )
    REFERENCES `kgp`.`usuario` (`id` ))
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `kgp`.`usuario_projeto`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `kgp`.`usuario_projeto` (
  `id` INT(10) NOT NULL AUTO_INCREMENT ,
  `id_usuario` INT(10) NOT NULL ,
  `id_projeto` INT(10) NOT NULL ,
  `data_registro` DATE NULL DEFAULT NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `id_usuario` (`id_usuario` ASC) ,
  INDEX `id_projeto` (`id_projeto` ASC) ,
  CONSTRAINT `usuario_projeto_ibfk_1`
    FOREIGN KEY (`id_usuario` )
    REFERENCES `kgp`.`usuario` (`id` ),
  CONSTRAINT `usuario_projeto_ibfk_2`
    FOREIGN KEY (`id_projeto` )
    REFERENCES `kgp`.`projeto` (`id` ))
ENGINE = InnoDB
AUTO_INCREMENT = 7
DEFAULT CHARACTER SET = latin1;



SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
