package modelo;

import java.util.Date;

public class Juego {
    private int id;
    private String nombre;
    private String autores;
    private String descripcion;
    private int minJugadores;
    private int maxJugadores;
    private int duracionEstimada;
    private String tematica;
    private String tipo;
    private String ubicacion;
    private String estado;
    private String propietarioTipo;
    private String propietarioDni;
    private Date fechaAlta;

    public Juego(int id, String nombre, String autores, String descripcion, int minJugadores,
                 int maxJugadores, int duracionEstimada, String tematica, String tipo,
                 String ubicacion, String estado, String propietarioTipo, String propietarioDni, Date fechaAlta) {
        this.id = id;
        this.nombre = nombre;
        this.autores = autores;
        this.descripcion = descripcion;
        this.minJugadores = minJugadores;
        this.maxJugadores = maxJugadores;
        this.duracionEstimada = duracionEstimada;
        this.tematica = tematica;
        this.tipo = tipo;
        this.ubicacion = ubicacion;
        this.estado = estado;
        this.propietarioTipo = propietarioTipo;
        this.propietarioDni = propietarioDni;
        this.fechaAlta = fechaAlta;
    }

    // Getters y Setters
    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public String getAutores() { return autores; }
    public String getDescripcion() { return descripcion; }
    public int getMinJugadores() { return minJugadores; }
    public int getMaxJugadores() { return maxJugadores; }
    public int getDuracionEstimada() { return duracionEstimada; }
    public String getTematica() { return tematica; }
    public String getTipo() { return tipo; }
    public String getUbicacion() { return ubicacion; }
    public String getEstado() { return estado; }
    public String getPropietarioTipo() { return propietarioTipo; }
    public String getPropietarioDni() { return propietarioDni; }
    public Date getFechaAlta() { return fechaAlta; }

    @Override
    public String toString() {
        return String.format("[%d] %s | Autores: %s | Jugadores: %d-%d | Temp: %d min | Tema: %s | Estado: %s | Prop: %s (%s)",
                id, nombre, autores, minJugadores, maxJugadores, duracionEstimada, tematica, estado, propietarioTipo,
                propietarioDni != null ? propietarioDni : "Asociación");
    }
}
