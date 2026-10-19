USE asociacion_juegos;

INSERT INTO asociacion (cif, nombre, razon_social, direccion, telefono, email, fecha_apertura) VALUES
('G01987654', 'Asociación Ludika Vitoria', 'Asociación Cultural Ludika Vitoria-Gasteiz', 'Calle Cuchillería 45, 01001 Vitoria-Gasteiz', '945123456', 'contacto@ludikagasteiz.org', '2018-05-15');

INSERT INTO socia (dni, nombre, apellidos, telefono, edad, email) VALUES
('12345678A', 'Amaia', 'Etxebarria Goikoetxea', '600111222', 29, 'amaia.etxeba@email.com'),
('87654321B', 'Mikel', 'Gomez Ruiz', '655333444', 35, 'mikel.gomez@email.com');

INSERT INTO juego (nombre, autores, descripcion, min_jugadores, max_jugadores, duracion_estimada, tematica, tipo, ubicacion, estado, propietario_tipo, propietario_dni, fecha_alta) VALUES
('Catan', 'Klaus Teuber', 'Juego de negociación y estrategia', 3, 4, 75, 'Estrategia', 'competitivo', 'Estantería A1', 'disponible', 'asociacion', NULL, '2020-01-10'),
('Gloomhaven', 'Isaac Childres', 'Juego de mazmorreo cooperativo', 1, 4, 120, 'Fantasía', 'cooperativo', 'Estantería B2', 'disponible', 'socia', '12345678A', '2022-03-20');

INSERT INTO partida (id_juego, fecha, hora, jugadores_deseados, plazas_libres) VALUES
(1, '2026-11-01', '18:00:00', 4, 2);

INSERT INTO partida_jugadora (id_partida, dni_socia) VALUES
(1, '12345678A'),
(1, '87654321B');