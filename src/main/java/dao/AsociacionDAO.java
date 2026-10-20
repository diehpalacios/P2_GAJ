package dao;

import conexion.ConexionBBDD;
import modelo.Asociacion;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class AsociacionDAO {

    public static Asociacion obtenerDatosAsociacion() throws Exception {
        String sql = "SELECT cif, nombre, razon_social, direccion, telefono, email, fecha_apertura FROM asociacion LIMIT 1";
        Asociacion asociacion = null;

        try (Connection conn = ConexionBBDD.getConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                asociacion = new Asociacion(
                        rs.getString("cif"),
                        rs.getString("nombre"),
                        rs.getString("razon_social"),
                        rs.getString("direccion"),
                        rs.getString("telefono"),
                        rs.getString("email"),
                        rs.getDate("fecha_apertura") != null ? rs.getDate("fecha_apertura").toLocalDate() : null
                );
            }
        }

        return asociacion;
    }
}
