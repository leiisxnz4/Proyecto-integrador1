package models;

public class Medicamento {
    private int id;
    private String nombre;
    private String presentacion;
    private String dosis;
    private int cantidad;

    public Medicamento() {
        // Constructor vacío
    }

    public Medicamento(int id, String nombre, String presentacion, String dosis, int cantidad) {
        this.id = id;
        this.nombre = nombre;
        this.presentacion = presentacion;
        this.dosis = dosis;
        this.cantidad = cantidad;
    }

    // Getters
    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public String getPresentacion() { return presentacion; }
    public String getDosis() { return dosis; }
    public int getCantidad() { return cantidad; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setPresentacion(String presentacion) { this.presentacion = presentacion; }
    public void setDosis(String dosis) { this.dosis = dosis; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }
}