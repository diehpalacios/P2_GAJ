CREATE DATABASE IF NOT EXISTS asociacion_juegos;
USE asociacion_juegos;

-- 1. Datos de la Asociación
CREATE TABLE IF NOT EXISTS asociacion (
    cif VARCHAR(10) PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    razon_social VARCHAR(150) NOT NULL,
    direccion VARCHAR(200) NOT NULL,
    telefono VARCHAR(15) NOT NULL,
    email VARCHAR(100) NOT NULL,
    fecha_apertura DATE NOT NULL
);

-- 2. Socias
CREATE TABLE IF NOT EXISTS socia (
    dni VARCHAR(15) PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    apellidos VARCHAR(100) NOT NULL,
    telefono VARCHAR(15) NOT NULL,
    edad INT NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    activa BOOLEAN DEFAULT TRUE
);

-- 3. Juegos de Mesa
CREATE TABLE IF NOT EXISTS juego (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    autores VARCHAR(150) NOT NULL,
    descripcion TEXT,
    min_jugadores INT NOT NULL,
    max_jugadores INT NOT NULL,
    duracion_estimada INT NOT NULL,
    tematica VARCHAR(50) NOT NULL,
    tipo ENUM('competitivo', 'cooperativo') NOT NULL,
    ubicacion VARCHAR(100) NOT NULL,
    estado ENUM('disponible', 'prestado', 'en reparacion') DEFAULT 'disponible',
    propietario_tipo ENUM('asociacion', 'socia') NOT NULL,
    propietario_dni VARCHAR(15) NULL,
    fecha_alta DATE NOT NULL,
    activo BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (propietario_dni) REFERENCES socia(dni) ON DELETE SET NULL ON UPDATE CASCADE
);

-- 4. Partidas
CREATE TABLE IF NOT EXISTS partida (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_juego INT NOT NULL,
    fecha DATE NOT NULL,
    hora TIME NOT NULL,
    jugadores_deseados INT NOT NULL,
    plazas_libres INT NOT NULL,
    ganadoras VARCHAR(200) DEFAULT 'Sin finalizar',
    activa BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (id_juego) REFERENCES juego(id) ON DELETE CASCADE ON UPDATE CASCADE
);

-- 5. Inscripciones a Partidas
CREATE TABLE IF NOT EXISTS partida_jugadora (
    id_partida INT NOT NULL,
    dni_socia VARCHAR(15) NULL,
    nombre_invitada VARCHAR(100) NULL,
    FOREIGN KEY (id_partida) REFERENCES partida(id) ON DELETE CASCADE,
    FOREIGN KEY (dni_socia) REFERENCES socia(dni) ON DELETE CASCADE ON UPDATE CASCADE
);