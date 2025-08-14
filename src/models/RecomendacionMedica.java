package models;

import java.time.LocalDate;

public class RecomendacionMedica {
    private int id;
    private String matricula;
    private LocalDate fecha;
    private String texto;

    public RecomendacionMedica() {}

    public RecomendacionMedica(int id, String matricula, LocalDate fecha, String texto) {
        this.id = id;
        this.matricula = matricula;
        this.fecha = fecha;
        this.texto = texto;
    }

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

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    @Override
    public String toString() {
        return "RecomendacionMedica{" +
                "id=" + id +
                ", matricula='" + matricula + '\'' +
                ", fecha=" + fecha +
                ", texto='" + texto + '\'' +
                '}';
    }
}