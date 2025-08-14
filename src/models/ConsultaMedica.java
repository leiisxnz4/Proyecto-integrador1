package models;

import java.time.LocalDate;

public class ConsultaMedica {
    private int id;
    private String matricula;
    private String nombreEstudiante;
    private String motivo;
    private String signosVitales;
    private String medicamentos;
    private String observaciones;
    private LocalDate fecha;

    // Constructor vacío
    public ConsultaMedica() {}

    // Constructor completo con ID
    public ConsultaMedica(int id, String matricula, String motivo, String signosVitales,
                          String medicamentos, String observaciones, LocalDate fecha) {
        this.id = id;
        this.matricula = matricula;
        this.motivo = motivo;
        this.signosVitales = signosVitales;
        this.medicamentos = medicamentos;
        this.observaciones = observaciones;
        this.fecha = fecha;
    }

    // Constructor sin ID (para guardar nueva consulta)
    public ConsultaMedica(String matricula, String motivo, String signosVitales,
                          String medicamentos, String observaciones, LocalDate fecha) {
        this.matricula = matricula;
        this.motivo = motivo;
        this.signosVitales = signosVitales;
        this.medicamentos = medicamentos;
        this.observaciones = observaciones;
        this.fecha = fecha;
    }

    // Getters
    public int getId() { return id; }
    public String getMatricula() { return matricula; }
    public String getNombreEstudiante() { return nombreEstudiante; }
    public String getMotivo() { return motivo; }
    public String getSignosVitales() { return signosVitales; }
    public String getMedicamentos() { return medicamentos; }
    public String getObservaciones() { return observaciones; }
    public LocalDate getFecha() { return fecha; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setMatricula(String matricula) { this.matricula = matricula; }
    public void setNombreEstudiante(String nombreEstudiante) { this.nombreEstudiante = nombreEstudiante; }
    public void setMotivo(String motivo) { this.motivo = motivo; }
    public void setSignosVitales(String signosVitales) { this.signosVitales = signosVitales; }
    public void setMedicamentos(String medicamentos) { this.medicamentos = medicamentos; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
}