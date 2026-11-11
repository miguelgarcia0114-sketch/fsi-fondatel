-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 29-10-2026 a las 23:16:11
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `fondatel`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `alimentos`
--

CREATE TABLE `alimentos` (
  `idalimentos` int(11) NOT NULL,
  `nombrea` varchar(100) NOT NULL,
  `descripcion` varchar(255) NOT NULL,
  `precio` float NOT NULL,
  `foto` longblob NOT NULL,
  `categoria` varchar(45) NOT NULL COMMENT 'Entrada, Plato principal, Postre, Bebida, Vegano, Lite',
  `estadoa` varchar(45) NOT NULL COMMENT 'Disponible, Agotado.'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `clientes`
--

CREATE TABLE `clientes` (
  `codigoc` varchar(20) NOT NULL,
  `nombrec` varchar(100) NOT NULL,
  `rfc` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `empleados`
--

CREATE TABLE `empleados` (
  `codigo` varchar(20) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `puesto` varchar(45) NOT NULL COMMENT 'Administrador, Cocinero, Mesero, Cajero, Lava platos.',
  `turno` varchar(45) NOT NULL COMMENT 'Matutino, Vespertino, Mixto.',
  `nom_usr` varchar(45) NOT NULL,
  `nip` varchar(45) NOT NULL,
  `estadoe` varchar(45) NOT NULL COMMENT 'Activo, Inactivo.'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `mesas`
--

CREATE TABLE `mesas` (
  `numero` int(11) NOT NULL,
  `estadom` varchar(45) NOT NULL COMMENT 'Ocupado, Reservado, Libre.',
  `capacidad` int(11) NOT NULL COMMENT 'Número de sillas.'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ordenes`
--

CREATE TABLE `ordenes` (
  `folio` int(11) NOT NULL,
  `fecha_hora` datetime NOT NULL,
  `observaciones` varchar(255) NOT NULL DEFAULT 'N/A',
  `estadoo` varchar(45) NOT NULL COMMENT 'Ordenado, Preparando, Entregado, Devuelto, Cancelar, Pagado',
  `silla` int(11) NOT NULL,
  `clientes_codigoc` varchar(20) NOT NULL,
  `mesas_numero` int(11) NOT NULL,
  `alimentos_idalimentos` int(11) NOT NULL,
  `empleados_codigo_mesero` varchar(20) NOT NULL,
  `empleados_codigo_cocinero` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `alimentos`
--
ALTER TABLE `alimentos`
  ADD PRIMARY KEY (`idalimentos`);

--
-- Indices de la tabla `clientes`
--
ALTER TABLE `clientes`
  ADD PRIMARY KEY (`codigoc`);

--
-- Indices de la tabla `empleados`
--
ALTER TABLE `empleados`
  ADD PRIMARY KEY (`codigo`),
  ADD UNIQUE KEY `nom_usr_UNIQUE` (`nom_usr`);

--
-- Indices de la tabla `mesas`
--
ALTER TABLE `mesas`
  ADD PRIMARY KEY (`numero`);

--
-- Indices de la tabla `ordenes`
--
ALTER TABLE `ordenes`
  ADD PRIMARY KEY (`folio`),
  ADD KEY `fk_ordenes_clientes_idx` (`clientes_codigoc`),
  ADD KEY `fk_ordenes_mesas1_idx` (`mesas_numero`),
  ADD KEY `fk_ordenes_alimentos1_idx` (`alimentos_idalimentos`),
  ADD KEY `fk_ordenes_empleados1_idx` (`empleados_codigo_mesero`),
  ADD KEY `fk_ordenes_empleados2_idx` (`empleados_codigo_cocinero`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `alimentos`
--
ALTER TABLE `alimentos`
  MODIFY `idalimentos` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `ordenes`
--
ALTER TABLE `ordenes`
  MODIFY `folio` int(11) NOT NULL AUTO_INCREMENT;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `ordenes`
--
ALTER TABLE `ordenes`
  ADD CONSTRAINT `fk_ordenes_alimentos1` FOREIGN KEY (`alimentos_idalimentos`) REFERENCES `alimentos` (`idalimentos`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_ordenes_clientes` FOREIGN KEY (`clientes_codigoc`) REFERENCES `clientes` (`codigoc`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_ordenes_empleados1` FOREIGN KEY (`empleados_codigo_mesero`) REFERENCES `empleados` (`codigo`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_ordenes_empleados2` FOREIGN KEY (`empleados_codigo_cocinero`) REFERENCES `empleados` (`codigo`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_ordenes_mesas1` FOREIGN KEY (`mesas_numero`) REFERENCES `mesas` (`numero`) ON DELETE NO ACTION ON UPDATE NO ACTION;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
