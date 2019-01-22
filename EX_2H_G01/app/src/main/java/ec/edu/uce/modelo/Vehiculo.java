package ec.edu.uce.modelo;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Date;

public class Vehiculo implements Serializable {

    private String placa;
    private String marca;
    private Date fechaFabricacion;
    private Double costo;
    private Boolean matriculado;
    private String color;
    private Bitmap foto;
    private Boolean estado;
    private String tipo;

    public Vehiculo() {
    }

    public Vehiculo(String placa, String marca, Date fechaFabricacion, Double costo, Boolean matriculado, String color, Bitmap foto, Boolean estado, String tipo) {
        this.placa = placa;
        this.marca = marca;
        this.fechaFabricacion = fechaFabricacion;
        this.costo = costo;
        this.matriculado = matriculado;
        this.color = color;
        this.foto = foto;
        this.estado = estado;
        this.tipo = tipo;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public Date getFechaFabricacion() {
        return fechaFabricacion;
    }

    public void setFechaFabricacion(Date fechaFabricacion) {
        this.fechaFabricacion = fechaFabricacion;
    }

    public Double getCosto() {
        return costo;
    }

    public void setCosto(Double costo) {
        this.costo = costo;
    }

    public Boolean getMatriculado() {
        return matriculado;
    }

    public void setMatriculado(Boolean matriculado) {
        this.matriculado = matriculado;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Bitmap getFoto() {
        return foto;
    }

    public void setFoto(Bitmap foto) {
        this.foto = foto;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return "Vehiculo{" +
                "placa='" + placa + '\'' +
                ", marca='" + marca + '\'' +
                ", fechaFabricacion=" + fechaFabricacion +
                ", costo=" + costo +
                ", matriculado=" + matriculado +
                ", color='" + color + '\'' +
                ", foto=" + foto +
                ", estado=" + estado +
                ", tipo='" + tipo + '\'' +
                '}';
    }
}
