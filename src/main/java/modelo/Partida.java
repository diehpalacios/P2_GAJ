package modelo;

import java.sql.Time;
import java.util.Date;
import java.util.List;

public class Partida {
    private int id;
    private int idJuego;
    private String nombreJuego;
    private Date fecha;
    private Time hora;
    private int jugadoresDeseados;
    private int plazasLibres;
    private String ganadoras;
    private List<String> jugadorasInscritas;

    public Partida(int id, int idJuego, String nombreJuego,
                   Date fecha, Time hora, int jugadoresDeseados, int plazasLibres,
                   String ganadoras) {
        this.id = id;
        this.idJuego = idJuego;
        this.nombreJuego = nombreJuego;
        this.fecha = fecha;
        this.hora = hora;
        this.jugadoresDeseados = jugadoresDeseados;
        this.plazasLibres = plazasLibres;
        this.ganadoras = ganadoras;
    }

    // Getters & Setters
    public int getId() { return id; }
    public int getIdJuego() { return idJuego; }
    public String getNombreJuego() { return nombreJuego; }
    public Date getFecha() { return fecha; }
    public Time getHora() { return hora; }
    public int getJugadoresDeseados() { return jugadoresDeseados; }
    public int getPlazasLibres() { return plazasLibres; }
    public String getGanadoras() { return ganadoras; }
    public List<String> getJugadorasInscritas() { return jugadorasInscritas; }
    public void setJugadorasInscritas(List<String> jugadorasInscritas) { this.jugadorasInscritas = jugadorasInscritas; }

    @Override
    public String toString() {
        return String.format("Partida #%d | Juego: %s | Fecha: %s %s | Plazas Libres: %d/%d | Ganadoras: %s",
                id, nombreJuego, fecha, hora, plazasLibres, jugadoresDeseados, ganadoras);
    }
}
