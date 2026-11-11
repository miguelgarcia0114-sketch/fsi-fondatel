-- Datos iniciales para probar FondaTel
-- Ejecutar DESPUÉS de fondatel.sql (base de datos `fondatel`)
-- Usuarios de prueba: admin / mesero / cocinero / cajero  (NIP: 1234)

INSERT INTO `empleados` (`codigo`, `nombre`, `puesto`, `turno`, `nom_usr`, `nip`, `estadoe`) VALUES
('E001', 'Administrador', 'Administrador', 'Matutino', 'admin', '1234', 'Activo'),
('E002', 'Mesero de prueba', 'Mesero', 'Matutino', 'mesero', '1234', 'Activo'),
('E003', 'Cocinero de prueba', 'Cocinero', 'Matutino', 'cocinero', '1234', 'Activo'),
('E004', 'Cajero de prueba', 'Cajero', 'Matutino', 'cajero', '1234', 'Activo');

INSERT INTO `clientes` (`codigoc`, `nombrec`, `rfc`) VALUES
('C001', 'Público en general', NULL);
