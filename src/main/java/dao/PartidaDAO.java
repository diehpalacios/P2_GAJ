package dao;

import conexion.ConexionBBDD;
import modelo.Partida;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PartidaDAO {

    public List<Partida> listarPartidas() throws SQLException {
        List<Partida> lista = new ArrayList<>();
        String sql = "SELECT p.id AS partida_id, p.id_juego, p.fecha, p.hora, p.jugadores_deseados, " +
                "p.plazas_libres, p.ganadoras, j.nombre AS nombre_juego " +
                "FROM partida p JOIN juego j ON p.id_juego = j.id " +
                "WHERE p.activa = TRUE";

        try (Connection con = ConexionBBDD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int idPartida = rs.getInt("partida_id");
                Partida partida = new Partida(
                        idPartida,
                        rs.getInt("id_juego"),
                        rs.getString("nombre_juego"),
                        rs.getDate("fecha"),
                        rs.getTime("hora"),
                        rs.getInt("jugadores_deseados"),
                        rs.getInt("plazas_libres"),
                        rs.getString("ganadoras")
                );
                partida.setJugadorasInscritas(obtenerJugadorasPartida(idPartida));
                lista.add(partida);
            }
        }
        return lista;
    }

    private List<String> obtenerJugadorasPartida(int idPartida) throws SQLException {
        List<String> jugadoras = new ArrayList<>();
        String sql = "SELECT COALESCE(CONCAT(s.nombre, ' ', s.apellidos), pj.nombre_invitada, pj.dni_socia) AS jugadora " +
                "FROM partida_jugadora pj " +
                "LEFT JOIN socia s ON UPPER(TRIM(pj.dni_socia)) = UPPER(TRIM(s.dni)) " +
                "WHERE pj.id_partida = ?";

        try (Connection con = ConexionBBDD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idPartida);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String nombre = rs.getString("jugadora");
                    if (nombre != null) {
                        jugadoras.add(nombre);
                    }
                }

            }
        }
        return jugadoras;
    }

    public void inscribirSociaEnPartida(int idPartida, String dniSocia) throws SQLException {
        String sqlCheck = "SELECT plazas_libres FROM partida WHERE id = ? AND activa = TRUE";
        String sqlInscribir = "INSERT INTO partida_jugadora (id_partida, dni_socia) VALUES (?, ?)";
        String sqlUpdatePlazas = "UPDATE partida SET plazas_libres = plazas_libres - 1 WHERE id = ?";

        String dniLimpio = dniSocia.trim();

        try (Connection con = ConexionBBDD.getConexion()) {
            try {
                con.setAutoCommit(false); // Iniciar transacción manual

                // 1. Verificar disponibilidad de plazas
                try (PreparedStatement psCheck = con.prepareStatement(sqlCheck)) {
                    psCheck.setInt(1, idPartida);
                    ResultSet rs = psCheck.executeQuery();
                    if (!rs.next()) {
                        throw new SQLException("La partida ID " + idPartida + " no existe o no está activa.");
                    }
                    if (rs.getInt("plazas_libres") <= 0) {
                        throw new SQLException("No quedan plazas libres en esta partida.");
                    }
                }

                // 2. Insertar registro en tabla intermedia
                try (PreparedStatement psIns = con.prepareStatement(sqlInscribir)) {
                    psIns.setInt(1, idPartida);
                    psIns.setString(2, dniLimpio);
                    psIns.executeUpdate();
                }

                // 3. Descontar vacante
                try (PreparedStatement psUpd = con.prepareStatement(sqlUpdatePlazas)) {
                    psUpd.setInt(1, idPartida);
                    psUpd.executeUpdate();
                }

                con.commit(); // Confirmar cambios
            } catch (SQLException e) {
                con.rollback(); // Revertir en caso de fallo
                throw e;
            } finally {
                con.setAutoCommit(true); // Restablecer modo normal
            }
        }
    }


    // CREATE: Crear nueva partida
    public void crearPartida(int idJuego, java.sql.Date fecha, java.sql.Time hora, int jugadoresDeseados) throws SQLException {
        String sql = "INSERT INTO partida (id_juego, fecha, hora, jugadores_deseados, plazas_libres, activa) VALUES (?, ?, ?, ?, ?, TRUE)";
        try (Connection con = ConexionBBDD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idJuego);
            ps.setDate(2, fecha);
            ps.setTime(3, hora);
            ps.setInt(4, jugadoresDeseados);
            ps.setInt(5, jugadoresDeseados); // Al crearse, todas las plazas están libres
            ps.executeUpdate();
        }
    }

    // DELETE: Borrado lógico / Cancelación de partida
    public void cancelarPartida(int idPartida) throws SQLException {
        String sql = "UPDATE partida SET activa = FALSE WHERE id = ?";
        try (Connection con = ConexionBBDD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idPartida);
            ps.executeUpdate();
        }
    }

    public void editarPartida(int id, java.sql.Date nuevaFecha, java.sql.Time nuevaHora, int nuevosJugadoresDeseados, String ganadoras) throws SQLException {
        String sql = "UPDATE partida SET fecha = ?, hora = ?, jugadores_deseados = ?, ganadoras = ? WHERE id = ? AND activa = TRUE";

        try (Connection con = ConexionBBDD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setDate(1, nuevaFecha);
            ps.setTime(2, nuevaHora);
            ps.setInt(3, nuevosJugadoresDeseados);
            ps.setString(4, ganadoras);
            ps.setInt(5, id);

            ps.executeUpdate();
        }
    }

}
