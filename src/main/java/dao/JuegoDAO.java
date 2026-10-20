package dao;

import conexion.ConexionBBDD;
import modelo.Juego;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JuegoDAO {

    public List<Juego> listarJuegos() throws SQLException {
        List<Juego> juegos = new ArrayList<>();
        String sql = "SELECT * FROM juego WHERE activo = TRUE";

        try (Connection con = ConexionBBDD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                juegos.add(new Juego(rs.getInt("id"), rs.getString("nombre"), rs.getString("autores"),
                        rs.getString("descripcion"), rs.getInt("min_jugadores"), rs.getInt("max_jugadores"),
                        rs.getInt("duracion_estimada"), rs.getString("tematica"), rs.getString("tipo"),
                        rs.getString("ubicacion"), rs.getString("estado"), rs.getString("propietario_tipo"),
                        rs.getString("propietario_dni"), rs.getDate("fecha_alta")));
            }
            return juegos;
        }
    }

    public void insertarJuego(Juego j) throws SQLException {
        String sql = "INSERT INTO juego (nombre, autores, descripcion, min_jugadores, max_jugadores, duracion_estimada, tematica, tipo, ubicacion, estado, propietario_tipo, propietario_dni, fecha_alta) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = ConexionBBDD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, j.getNombre());
            ps.setString(2, j.getAutores());
            ps.setString(3, j.getDescripcion());
            ps.setInt(4, j.getMinJugadores());
            ps.setInt(5, j.getMaxJugadores());
            ps.setInt(6, j.getDuracionEstimada());
            ps.setString(7, j.getTematica());
            ps.setString(8, j.getTipo());
            ps.setString(9, j.getUbicacion());
            ps.setString(10, j.getEstado());
            ps.setString(11, j.getPropietarioTipo());
            if (j.getPropietarioDni() != null && !j.getPropietarioDni().isEmpty()) {
                ps.setString(12, j.getPropietarioDni());
            } else {
                ps.setNull(12, Types.VARCHAR);
            }
            ps.setDate(13, (Date) j.getFechaAlta());

            ps.executeUpdate();
        }
    }

    public void bajaLogicaJuego(int id) throws SQLException {
        String sql = "UPDATE juego SET activo = FALSE WHERE id = ?";
        try (Connection con = ConexionBBDD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }


}
