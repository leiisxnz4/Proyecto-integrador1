package models;

public class Estudiante {
    private int id;
    private String nombre;
    private int edad;
    private String grupo;
    private String tutor;
    private String telefono;
    private String matricula;
    private String condicionesMedicas;

    public Estudiante() {}

    public Estudiante(int id, String nombre, int edad, String grupo, String tutor, String telefono, String matricula, String condicionesMedicas) {
        this.id = id;
        this.nombre = nombre;
        this.edad = edad;
        this.grupo = grupo;
        this.tutor = tutor;
        this.telefono = telefono;
        this.matricula = matricula;
        this.condicionesMedicas = condicionesMedicas;
    }

    // Getters y setters ↓
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public int getEdad() { return edad; }
    public void setEdad(int edad) { this.edad = edad; }

    public String getGrupo() { return grupo; }
    public void setGrupo(String grupo) { this.grupo = grupo; }

    public String getTutor() { return tutor; }
    public void setTutor(String tutor) { this.tutor = tutor; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getMatricula() { return matricula; }
    public void setMatricula(String matricula) { this.matricula = matricula; }

    public String getCondicionesMedicas() { return condicionesMedicas; }
    public void setCondicionesMedicas(String condicionesMedicas) { this.condicionesMedicas = condicionesMedicas; }
}