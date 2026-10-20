package metadatos;

import conexion.ConexionBBDD;

import java.sql.*;

public class GestorMetadatos {
    public static void mostrarMetadatosBaseDatos() {
        try (Connection con = ConexionBBDD.getConexion()) {
            DatabaseMetaData meta = con.getMetaData();

            System.out.println("==================================================");
            System.out.println("METADATOS DE LA BASE DE DATOS");
            System.out.println("==================================================");
            System.out.println("SGBD Nombre: " + meta.getDatabaseProductName());
            System.out.println("SGBD Versión: " + meta.getDatabaseProductVersion());
            System.out.println("Driver Nombre: " + meta.getDriverName());
            System.out.println("URL Conexión: " + meta.getURL());
            System.out.println("Usuario: " + meta.getUserName());
            System.out.println("--------------------------------------------------");

            ResultSet tablas = meta.getTables(null, null, "%", new String[]{"TABLE"});
            while (tablas.next()) {
                String nombreTabla = tablas.getString("TABLE_NAME");
                System.out.println("\nTABLA: " + nombreTabla);

                ResultSet pk = meta.getPrimaryKeys(null, null, nombreTabla);
                while (pk.next()) {
                    System.out.println("  [Clave Primaria]: " + pk.getString("COLUMN_NAME"));
                }

                ResultSet columnas = meta.getColumns(null, null, nombreTabla, "%");
                while (columnas.next()) {
                    System.out.printf("  - Columna: %-15s | Tipo: %-10s | Nulo: %s%n",
                            columnas.getString("COLUMN_NAME"),
                            columnas.getString("TYPE_NAME"),
                            columnas.getString("IS_NULLABLE"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener metadatos: " + e.getMessage());
        }
    }
}
