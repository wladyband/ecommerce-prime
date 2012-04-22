SET FOREIGN_KEY_CHECKS=0;

CREATE DATABASE `ecommerce`
  CHARACTER SET `latin1`
  COLLATE `latin1_swedish_ci`;

USE `ecommerce`;

/* Tables */
CREATE TABLE `admin` (
  `id`       int AUTO_INCREMENT NOT NULL,
  `usuario`  varchar(20),
  `senha`    varchar(20),
  PRIMARY KEY (`id`)
) ENGINE = InnoDB;

CREATE TABLE `categorias` (
  `id`        int AUTO_INCREMENT NOT NULL,
  `cat_nome`  varchar(30),
  PRIMARY KEY (`id`)
) ENGINE = InnoDB;

CREATE TABLE `itens_pedido` (
  `ped_id`   int,
  `prod_id`  int,
  `qtd`      int,
  `preco`    double(10,2)
) ENGINE = InnoDB;

CREATE TABLE `pedidos` (
  `id`          int AUTO_INCREMENT NOT NULL,
  `usuario_id`  int,
  `cc_nome`     varchar(50),
  `cc_tipo`     int,
  `cc_numero`   varchar(20),
  `cc_m_exp`    tinyint(11),
  `cc_a_exp`    int,
  `data_ped`    datetime,
  PRIMARY KEY (`id`)
) ENGINE = InnoDB;

CREATE TABLE `produtos` (
  `id`         int AUTO_INCREMENT NOT NULL,
  `prod_nome`  varchar(40),
  `cat_id`     int,
  `preco`      double(10,2),
  `desconto`   double(4,2),
  `desc_peq`   varchar(400),
  `desc_gd`    text,
  `imagem`     varchar(100),
  PRIMARY KEY (`id`)
) ENGINE = InnoDB;

CREATE TABLE `usuarios` (
  `id`        int AUTO_INCREMENT NOT NULL,
  `nome`      varchar(50),
  `email`     varchar(100),
  `senha`     varchar(20),
  `endereco`  varchar(200),
  `bairro`    varchar(50),
  `cidade`    varchar(50),
  `estado`    char(2),
  `cep`       char(9),
  PRIMARY KEY (`id`)
) ENGINE = InnoDB;

/* Indexes */
CREATE INDEX `fk_pedido`
  ON `itens_pedido`
  (`ped_id`);

CREATE INDEX `fk_produto`
  ON `itens_pedido`
  (`prod_id`);

CREATE INDEX `fk_usuarios`
  ON `pedidos`
  (`usuario_id`);

CREATE INDEX `fk_categorias`
  ON `produtos`
  (`cat_id`);

/* Foreign Keys */
ALTER TABLE `itens_pedido`
  ADD CONSTRAINT `fk_pedido`
  FOREIGN KEY (`ped_id`)
    REFERENCES `pedidos`(`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE;

ALTER TABLE `itens_pedido`
  ADD CONSTRAINT `fk_produto`
  FOREIGN KEY (`prod_id`)
    REFERENCES `produtos`(`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE;

ALTER TABLE `pedidos`
  ADD CONSTRAINT `fk_usuarios`
  FOREIGN KEY (`usuario_id`)
    REFERENCES `usuarios`(`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE;

ALTER TABLE `produtos`
  ADD CONSTRAINT `fk_categorias`
  FOREIGN KEY (`cat_id`)
    REFERENCES `categorias`(`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE;

/* Data */
INSERT INTO `admin` (`id`, `usuario`, `senha`) VALUES (1, 'admin', 'admin');
COMMIT;

INSERT INTO `categorias` (`id`, `cat_nome`) VALUES (1, 'Informática');
INSERT INTO `categorias` (`id`, `cat_nome`) VALUES (2, 'Telefonia');
INSERT INTO `categorias` (`id`, `cat_nome`) VALUES (3, 'Livraria');
INSERT INTO `categorias` (`id`, `cat_nome`) VALUES (7, 'Eletrodomésticos');
INSERT INTO `categorias` (`id`, `cat_nome`) VALUES (8, 'Usados');
INSERT INTO `categorias` (`id`, `cat_nome`) VALUES (11, 'Eletrônicos');
INSERT INTO `categorias` (`id`, `cat_nome`) VALUES (15, 'teste');
COMMIT;

INSERT INTO `itens_pedido` (`ped_id`, `prod_id`, `qtd`, `preco`) VALUES (14, 1, 2, 12.5);
INSERT INTO `itens_pedido` (`ped_id`, `prod_id`, `qtd`, `preco`) VALUES (15, 1, 2, 12.5);
INSERT INTO `itens_pedido` (`ped_id`, `prod_id`, `qtd`, `preco`) VALUES (16, 1, 2, 12.5);
INSERT INTO `itens_pedido` (`ped_id`, `prod_id`, `qtd`, `preco`) VALUES (16, 2, 1, 16.5);
INSERT INTO `itens_pedido` (`ped_id`, `prod_id`, `qtd`, `preco`) VALUES (17, 1, 1, 68.4);
INSERT INTO `itens_pedido` (`ped_id`, `prod_id`, `qtd`, `preco`) VALUES (17, 2, 10, 763);
INSERT INTO `itens_pedido` (`ped_id`, `prod_id`, `qtd`, `preco`) VALUES (18, 1, 1, 68.4);
INSERT INTO `itens_pedido` (`ped_id`, `prod_id`, `qtd`, `preco`) VALUES (19, 2, 1, 76.3);
INSERT INTO `itens_pedido` (`ped_id`, `prod_id`, `qtd`, `preco`) VALUES (20, 1, 2, 136.8);
INSERT INTO `itens_pedido` (`ped_id`, `prod_id`, `qtd`, `preco`) VALUES (20, 2, 4, 305.2);
INSERT INTO `itens_pedido` (`ped_id`, `prod_id`, `qtd`, `preco`) VALUES (21, 2, 1, 76.3);
INSERT INTO `itens_pedido` (`ped_id`, `prod_id`, `qtd`, `preco`) VALUES (22, 2, 1, 76.3);
INSERT INTO `itens_pedido` (`ped_id`, `prod_id`, `qtd`, `preco`) VALUES (24, 1, 10, 684);
INSERT INTO `itens_pedido` (`ped_id`, `prod_id`, `qtd`, `preco`) VALUES (26, 2, 1, 76.3);
INSERT INTO `itens_pedido` (`ped_id`, `prod_id`, `qtd`, `preco`) VALUES (27, 1, 1, 68.4);
INSERT INTO `itens_pedido` (`ped_id`, `prod_id`, `qtd`, `preco`) VALUES (29, 1, 2, 12.5);
INSERT INTO `itens_pedido` (`ped_id`, `prod_id`, `qtd`, `preco`) VALUES (29, 2, 1, 16.5);
INSERT INTO `itens_pedido` (`ped_id`, `prod_id`, `qtd`, `preco`) VALUES (30, 1, 2, 12.5);
INSERT INTO `itens_pedido` (`ped_id`, `prod_id`, `qtd`, `preco`) VALUES (30, 2, 1, 16.5);
INSERT INTO `itens_pedido` (`ped_id`, `prod_id`, `qtd`, `preco`) VALUES (31, 1, 1, 68.4);
INSERT INTO `itens_pedido` (`ped_id`, `prod_id`, `qtd`, `preco`) VALUES (31, 2, 1, 76.3);
INSERT INTO `itens_pedido` (`ped_id`, `prod_id`, `qtd`, `preco`) VALUES (32, 1, 1, 68.4);
INSERT INTO `itens_pedido` (`ped_id`, `prod_id`, `qtd`, `preco`) VALUES (32, 2, 3, 228.9);
INSERT INTO `itens_pedido` (`ped_id`, `prod_id`, `qtd`, `preco`) VALUES (33, 1, 1, 68.4);
INSERT INTO `itens_pedido` (`ped_id`, `prod_id`, `qtd`, `preco`) VALUES (33, 2, 3, 76.3);
COMMIT;

INSERT INTO `pedidos` (`id`, `usuario_id`, `cc_nome`, `cc_tipo`, `cc_numero`, `cc_m_exp`, `cc_a_exp`, `data_ped`) VALUES (11, 1, NULL, 0, NULL, 0, 0, '2008-04-15 01:52:17');
INSERT INTO `pedidos` (`id`, `usuario_id`, `cc_nome`, `cc_tipo`, `cc_numero`, `cc_m_exp`, `cc_a_exp`, `data_ped`) VALUES (12, 1, NULL, 0, NULL, 0, 0, '2008-04-15 02:36:42');
INSERT INTO `pedidos` (`id`, `usuario_id`, `cc_nome`, `cc_tipo`, `cc_numero`, `cc_m_exp`, `cc_a_exp`, `data_ped`) VALUES (13, 1, NULL, 0, NULL, 0, 0, '2008-04-15 02:41:38');
INSERT INTO `pedidos` (`id`, `usuario_id`, `cc_nome`, `cc_tipo`, `cc_numero`, `cc_m_exp`, `cc_a_exp`, `data_ped`) VALUES (14, 1, NULL, 0, NULL, 0, 0, '2008-04-15 02:44:54');
INSERT INTO `pedidos` (`id`, `usuario_id`, `cc_nome`, `cc_tipo`, `cc_numero`, `cc_m_exp`, `cc_a_exp`, `data_ped`) VALUES (15, 1, NULL, 0, NULL, 0, 0, '2008-04-15 02:46:32');
INSERT INTO `pedidos` (`id`, `usuario_id`, `cc_nome`, `cc_tipo`, `cc_numero`, `cc_m_exp`, `cc_a_exp`, `data_ped`) VALUES (16, 1, NULL, 0, NULL, 0, 0, '2008-04-15 02:47:59');
INSERT INTO `pedidos` (`id`, `usuario_id`, `cc_nome`, `cc_tipo`, `cc_numero`, `cc_m_exp`, `cc_a_exp`, `data_ped`) VALUES (17, 1, 'Eds', 1, '123456', 11, 2008, '2008-04-15 02:54:24');
INSERT INTO `pedidos` (`id`, `usuario_id`, `cc_nome`, `cc_tipo`, `cc_numero`, `cc_m_exp`, `cc_a_exp`, `data_ped`) VALUES (18, 1, 'Edson Gonçalves', 1, '11225345687', 11, 2012, '2008-04-15 03:21:19');
INSERT INTO `pedidos` (`id`, `usuario_id`, `cc_nome`, `cc_tipo`, `cc_numero`, `cc_m_exp`, `cc_a_exp`, `data_ped`) VALUES (19, 1, 'teste', 3, '1212121212', 11, 2008, '2008-04-15 03:36:15');
INSERT INTO `pedidos` (`id`, `usuario_id`, `cc_nome`, `cc_tipo`, `cc_numero`, `cc_m_exp`, `cc_a_exp`, `data_ped`) VALUES (20, 1, 'Teste', 2, '123456789', 4, 2007, '2008-04-15 04:49:42');
INSERT INTO `pedidos` (`id`, `usuario_id`, `cc_nome`, `cc_tipo`, `cc_numero`, `cc_m_exp`, `cc_a_exp`, `data_ped`) VALUES (21, NULL, 'Eds', 2, '12121212121', 11, 2007, '2008-04-15 23:24:06');
INSERT INTO `pedidos` (`id`, `usuario_id`, `cc_nome`, `cc_tipo`, `cc_numero`, `cc_m_exp`, `cc_a_exp`, `data_ped`) VALUES (22, 4, 'Tsss', 1, '11111111', 11, 2007, '2008-04-15 23:32:54');
INSERT INTO `pedidos` (`id`, `usuario_id`, `cc_nome`, `cc_tipo`, `cc_numero`, `cc_m_exp`, `cc_a_exp`, `data_ped`) VALUES (23, 5, 'TEste', 1, 'Teste', 11, 2008, '2008-04-15 00:00:00');
INSERT INTO `pedidos` (`id`, `usuario_id`, `cc_nome`, `cc_tipo`, `cc_numero`, `cc_m_exp`, `cc_a_exp`, `data_ped`) VALUES (24, 6, '123', 1, '12222222', 11, 2012, '2008-04-16 02:38:08');
INSERT INTO `pedidos` (`id`, `usuario_id`, `cc_nome`, `cc_tipo`, `cc_numero`, `cc_m_exp`, `cc_a_exp`, `data_ped`) VALUES (25, 7, 'Teste', 1, '11111111', 11, 2007, '2008-04-16 02:39:07');
INSERT INTO `pedidos` (`id`, `usuario_id`, `cc_nome`, `cc_tipo`, `cc_numero`, `cc_m_exp`, `cc_a_exp`, `data_ped`) VALUES (26, 6, 'TEste', 2, '123', 11, 2008, '2008-04-16 02:53:25');
INSERT INTO `pedidos` (`id`, `usuario_id`, `cc_nome`, `cc_tipo`, `cc_numero`, `cc_m_exp`, `cc_a_exp`, `data_ped`) VALUES (27, 6, 'Tess', 2, '123456789', 11, 2007, '2008-04-16 02:57:01');
INSERT INTO `pedidos` (`id`, `usuario_id`, `cc_nome`, `cc_tipo`, `cc_numero`, `cc_m_exp`, `cc_a_exp`, `data_ped`) VALUES (28, 6, 'teste', 1, '123456789', 11, 2007, '2008-04-16 03:04:35');
INSERT INTO `pedidos` (`id`, `usuario_id`, `cc_nome`, `cc_tipo`, `cc_numero`, `cc_m_exp`, `cc_a_exp`, `data_ped`) VALUES (29, 1, NULL, 0, NULL, 0, 0, '2008-04-19 16:58:10');
INSERT INTO `pedidos` (`id`, `usuario_id`, `cc_nome`, `cc_tipo`, `cc_numero`, `cc_m_exp`, `cc_a_exp`, `data_ped`) VALUES (30, 1, NULL, 0, NULL, 0, 0, '2008-04-21 05:32:42');
INSERT INTO `pedidos` (`id`, `usuario_id`, `cc_nome`, `cc_tipo`, `cc_numero`, `cc_m_exp`, `cc_a_exp`, `data_ped`) VALUES (31, 1, 'Teste', 2, '1234567890', 11, 2007, '2008-04-26 08:00:32');
INSERT INTO `pedidos` (`id`, `usuario_id`, `cc_nome`, `cc_tipo`, `cc_numero`, `cc_m_exp`, `cc_a_exp`, `data_ped`) VALUES (32, 1, '1234567890', 1, '1234-4567', 11, 2007, '2008-04-27 19:50:09');
INSERT INTO `pedidos` (`id`, `usuario_id`, `cc_nome`, `cc_tipo`, `cc_numero`, `cc_m_exp`, `cc_a_exp`, `data_ped`) VALUES (33, 1, 'Edson', 2, '1234-1234-1234-1234', 11, 2012, '2008-04-28 05:08:20');
COMMIT;

INSERT INTO `produtos` (`id`, `prod_nome`, `cat_id`, `preco`, `desconto`, `desc_peq`, `desc_gd`, `imagem`) VALUES (1, 'Dominando Eclipse', 1, 76, 10, 'Aprenda a trabalhar com o Eclipse IDE.', 'Dominando Eclipse é um livro que define o trabalho com o Eclipse.', 'eclipse13102006021049.jpg');
INSERT INTO `produtos` (`id`, `prod_nome`, `cat_id`, `preco`, `desconto`, `desc_peq`, `desc_gd`, `imagem`) VALUES (2, 'Livro NetBeans IDE 6', 3, 109, 30, 'Desenvolvendo aplicações Web com NetBeans IDE 6', 'Desenvolvendo aplicações Web com NetBeans IDE 6', 'd_netbeans6_p.jpg');
COMMIT;

INSERT INTO `usuarios` (`id`, `nome`, `email`, `senha`, `endereco`, `bairro`, `cidade`, `estado`, `cep`) VALUES (1, 'Edson Gonçalves', 'edson@integrator.com.br', 'integrator', 'Rua de Tal', 'Vila Tal', 'Santo André', 'SP', '09233-450');
INSERT INTO `usuarios` (`id`, `nome`, `email`, `senha`, `endereco`, `bairro`, `cidade`, `estado`, `cep`) VALUES (3, 'Edson', 'teste@integrator.com.br', 'ddddd', 'Rua de Ral', 'Bairro Tal', 'São Paulo', 'SP', '09233-550');
INSERT INTO `usuarios` (`id`, `nome`, `email`, `senha`, `endereco`, `bairro`, `cidade`, `estado`, `cep`) VALUES (4, 'Edson', 'integrator@integrator.com.br', 'integrator', 'Rua de Tal', 'supertal', 'soa ', 'SP', '05888888');
INSERT INTO `usuarios` (`id`, `nome`, `email`, `senha`, `endereco`, `bairro`, `cidade`, `estado`, `cep`) VALUES (5, 'Teste', 'teste@teste.com', '123', 'a', 's', 'a', 'SP', '08540700');
INSERT INTO `usuarios` (`id`, `nome`, `email`, `senha`, `endereco`, `bairro`, `cidade`, `estado`, `cep`) VALUES (6, 'Ronaldo Total 90', 'ronaldo', 'integrator', '1234', 'teeeq', 'yihhgh', 'SP', '055454544');
INSERT INTO `usuarios` (`id`, `nome`, `email`, `senha`, `endereco`, `bairro`, `cidade`, `estado`, `cep`) VALUES (7, 'Barbara', 'barbara', '123456', 'Rua Tal', 'Tal', 'Tal', 'SP', '055454544');
INSERT INTO `usuarios` (`id`, `nome`, `email`, `senha`, `endereco`, `bairro`, `cidade`, `estado`, `cep`) VALUES (8, 'Teste', 'ee', '123', 'tal', 'tal', 'tal', 'SP', '05454545');
COMMIT;

