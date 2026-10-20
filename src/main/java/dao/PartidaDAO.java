package dao;

import conexion.ConexionBBDD;
import modelo.Partida;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PartidaDAO {

    public List<Partida> listarPartidas() throws SQLException {
        List<Partida> lista = new ArrayList<>();
        String sql = "SELECT p.*, j.nombre AS nombre_juego FROM partida p JOIN juego j ON p.id_juego = j.id WHERE p.activa = TRUE";

        try (Connection con = ConexionBBDD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Partida partida = new Partida(
                        rs.getInt("id"), rs.getInt("id_juego"), rs.getString("nombre_juego"),
                        rs.getDate("fecha"), rs.getTime("hora"), rs.getInt("jugadores_deseados"),
                        rs.getInt("plazas_libres"), rs.getString("ganadoras")
                );
                partida.setJugadorasInscritas(obtenerJugadorasPartida(partida.getId()));
                lista.add(partida);
            }
        }
        return lista;
    }

    private List<String> obtenerJugadorasPartida(int idPartida) throws SQLException {
        List<String> jugadoras = new ArrayList<>();
        String sql = "SELECT COALESCE(s.nombre, pj.nombre_invitada) AS jugadora FROM partida_jugadora pj LEFT JOIN socia s ON pj.dni_socia = s.dni WHERE pj.id_partida = ?";

        try (Connection con = ConexionBBDD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idPartida);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) jugadoras.add(rs.getString("jugadora"));
            }
        }
        return jugadoras;
    }

    public void inscribirSociaEnPartida(int idPartida, String dniSocia) throws SQLException {
        String sqlCheck = "SELECT plazas_libres FROM partida WHERE id = ?";
        String sqlInscribir = "INSERT INTO partida_jugadora (id_partida, dni_socia) VALUES (?, ?)";
        String sqlUpdatePlazas = "UPDATE partida SET plazas_libres = plazas_libres - 1 WHERE id = ?";

        try (Connection con = ConexionBBDD.getConexion()) {
            con.setAutoCommit(false); // Transacción manual

            try (PreparedStatement psCheck = con.prepareStatement(sqlCheck)) {
                psCheck.setInt(1, idPartida);
                ResultSet rs = psCheck.executeQuery();
                if (rs.next() && rs.getInt("plazas_libres") <= 0) {
                    con.rollback();
                    throw new SQLException("No quedan plazas libres en esta partida.");
                }
            }

            try (PreparedStatement psIns = con.prepareStatement(sqlInscribir);
                 PreparedStatement psUpd = con.prepareStatement(sqlUpdatePlazas)) {

                psIns.setInt(1, idPartida);
                psIns.setString(2, dniSocia);
                psIns.executeUpdate();

                psUpd.setInt(1, idPartida);
                psUpd.executeUpdate();

                con.commit();
            } catch (SQLException e) {
                con.rollback();
                throw e;
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

    // UPDATE: Registrar resultado o ganadoras
    public void registrarGanadoras(int idPartida, String ganadoras) throws SQLException {
        String sql = "UPDATE partida SET ganadoras = ? WHERE id = ? AND activa = TRUE";
        try (Connection con = ConexionBBDD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, ganadoras);
            ps.setInt(2, idPartida);
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

}
