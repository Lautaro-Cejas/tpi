-- Creamos la base de datos
CREATE DATABASE IF NOT EXISTS empresa;

-- La usamos
USE empresa;

-- Tabla Legajo
CREATE TABLE IF NOT EXISTS legajo (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,  -- Clave primaria auto incremental
    eliminado BOOLEAN DEFAULT 0,           -- Baja lógica
    nro_legajo VARCHAR(20) NOT NULL UNIQUE,
    categoria VARCHAR(30),
    estado ENUM('ACTIVO', 'INACTIVO') NOT NULL,
    fecha_alta DATE,
    observaciones VARCHAR(255)
);

-- Tabla Empleado
CREATE TABLE IF NOT EXISTS empleado (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    eliminado BOOLEAN DEFAULT 0,
    nombre VARCHAR(80) NOT NULL,
    apellido VARCHAR(80) NOT NULL,
    dni VARCHAR(15) NOT NULL UNIQUE,
    email VARCHAR(120),
    fecha_ingreso DATE,
    area VARCHAR(50),
    legajo_id BIGINT UNIQUE,
    FOREIGN KEY (legajo_id) REFERENCES legajo(id)
);

-- Insertamos legajos
INSERT IGNORE INTO legajo (nro_legajo, categoria, estado, fecha_alta, observaciones)
VALUES 
('L001', 'Administrativo', 'ACTIVO', '2023-01-10', 'Empleado nuevo'),
('L002', 'Gerente', 'INACTIVO', '2022-04-15', 'En licencia');

-- Insertamos empleados vinculando los legajos
INSERT IGNORE INTO empleado (nombre, apellido, dni, email, fecha_ingreso, area, legajo_id)
VALUES 
('Juan', 'Pérez', '12345678', 'juan@empresa.com', '2023-01-12', 'Administración', 1),
('Ana', 'López', '87654321', 'ana@empresa.com', '2022-04-20', 'Gerencia', 2);