package ec.edu.uce.modelo;

import java.io.Serializable;
import java.util.Date;

public class Reserva implements Serializable {

    private Integer numero;
    private String email;
    private String celular;
    private Date fechaPrestamo;
    private Date fechaEntrega;
    private Double valor;
    private Vehiculo vehiculo;

    public Reserva() {
    }

    public Reserva(Integer numero, String email, String celular, Date fechaPrestamo, Date fechaEntrega, Double valor, Vehiculo vehiculo) {
        this.numero = numero;
        this.email = email;
        this.celular = celular;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaEntrega = fechaEntrega;
        this.valor = valor;
        this.vehiculo = vehiculo;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public Date getFechaPrestamo() {
        return fechaPrestamo;
    }

    public void setFechaPrestamo(Date fechaPrestamo) {
        this.fechaPrestamo = fechaPrestamo;
    }

    public Date getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(Date fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }

    @Override
    public String toString() {
        return "Reserva{" +
                "numero=" + numero +
                ", email='" + email + '\'' +
                ", celular='" + celular + '\'' +
                ", fechaPrestamo=" + fechaPrestamo +
                ", fechaEntrega=" + fechaEntrega +
                ", valor=" + valor +
                ", vehiculo=" + vehiculo +
                '}';
    }
}
