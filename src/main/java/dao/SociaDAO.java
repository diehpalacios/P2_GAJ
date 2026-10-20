package dao;

import conexion.ConexionBBDD;
import modelo.Socia;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SociaDAO {
    public List<Socia> listarSocias() throws SQLException {
        List<Socia> lista = new ArrayList<>();
        String sql = "SELECT * FROM socia WHERE activa = TRUE";

        try (Connection con = ConexionBBDD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(new Socia(
                        rs.getString("dni"), rs.getString("nombre"), rs.getString("apellidos"),
                        rs.getString("telefono"), rs.getInt("edad"), rs.getString("email")
                ));
            }
        }
        return lista;
    }

    public void insertarSocia(Socia s) throws SQLException {
        String sql = "INSERT INTO socia (dni, nombre, apellidos, telefono, edad, email) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection con = ConexionBBDD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, s.getDni());
            ps.setString(2, s.getNombre());
            ps.setString(3, s.getApellidos());
            ps.setString(4, s.getTelefono());
            ps.setInt(5, s.getEdad());
            ps.setString(6, s.getEmail());
            ps.executeUpdate();
        }
    }

    public void bajaLogicaSocia(String dni) throws SQLException {
        String sql = "UPDATE socia SET activa = FALSE WHERE dni = ?";
        try (Connection con = ConexionBBDD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, dni);
            ps.executeUpdate();
        }
    }
}
