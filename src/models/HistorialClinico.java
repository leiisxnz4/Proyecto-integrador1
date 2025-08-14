package models;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;

public class HistorialClinico {
    private final String tipo;
    private final LocalDate fecha;
    private final String detalle;

    public HistorialClinico(String tipo, String fechaStr, String detalle) {
        this.tipo = tipo;
        LocalDate tempFecha;
        try {
            tempFecha = (fechaStr != null && !fechaStr.isEmpty())
                ? LocalDate.parse(fechaStr.substring(0, 10), DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                : null;
        } catch (DateTimeParseException e) {
            tempFecha = null;
        }
        this.fecha = tempFecha;
        this.detalle = detalle;
    }

    public String getTipo() {
        return tipo;
    }

    public String getFecha() {
        return (fecha != null)
            ? fecha.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
            : "Fecha desconocida";
    }

    public LocalDate getFechaLocal() {
        return fecha;
    }

    public String getDetalle() {
        return detalle;
    }

    public String getIcono() {
        return switch (tipo) {
            case "Consulta" -> "🩺";
            case "Cita" -> "📅";
            case "Recomendación" -> "📝";
            case "Medicamento" -> "💊";
            default -> "❓";
        };
    }

    @Override
    public String toString() {
        return tipo + " | " + getFecha() + " | " + detalle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HistorialClinico that)) return false;
        return tipo.equals(that.tipo) &&
               Objects.equals(fecha, that.fecha) &&
               Objects.equals(detalle, that.detalle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tipo, fecha, detalle);
    }
}