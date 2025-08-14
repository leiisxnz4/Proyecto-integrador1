package models;

import java.time.LocalDate;
import java.time.LocalTime;

public class CitaMedica {
    private int id;                      // Clave interna
    private String matricula;           // Identificador visible (FK)
    private String nombreEstudiante;    // Campo informativo (no se guarda en DB)
    private LocalDate fecha;
    private LocalTime hora;
    private String motivo;

    // Constructor vacío
    public CitaMedica() {}

    // Constructor completo
    public CitaMedica(int id, String matricula, String nombreEstudiante,
                      LocalDate fecha, LocalTime hora, String motivo) {
        this.id = id;
        this.matricula = matricula;
        this.nombreEstudiante = nombreEstudiante;
        this.fecha = fecha;
        this.hora = hora;
        this.motivo = motivo;
    }

    // Getters y setters ↓
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getNombreEstudiante() {
        return nombreEstudiante;
    }

    public void setNombreEstudiante(String nombreEstudiante) {
        this.nombreEstudiante = nombreEstudiante;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    @Override
    public String toString() {
        return "Cita #" + id + " | " + matricula + " - " + nombreEstudiante +
               " | " + fecha + " " + hora + " | Motivo: " + motivo;
    }
}