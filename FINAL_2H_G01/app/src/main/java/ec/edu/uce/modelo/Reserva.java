package ec.edu.uce.modelo;

import java.io.Serializable;
import java.util.Date;

public class Reserva implements Serializable {

    private String placa;
    private String email;
    private String celular;
    private Integer valor;
    private Date fechaPrestamo;
    private Date fechaEntrega;
    private Integer numReserva;

    public Reserva() {
    }

    public Reserva(String placa, String email, String celular, Integer valor, Date fechaPrestamo, Date fechaEntrega, Integer numReserva) {
        this.placa = placa;
        this.email = email;
        this.celular = celular;
        this.valor = valor;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaEntrega = fechaEntrega;
        this.numReserva = numReserva;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
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

    public Integer getValor() {
        return valor;
    }

    public void setValor(Integer valor) {
        this.valor = valor;
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

    public Integer getNumReserva() {
        return numReserva;
    }

    public void setNumReserva(Integer numReserva) {
        this.numReserva = numReserva;
    }

    @Override
    public String toString() {
        return "Reserva{" +
                "placa='" + placa + '\'' +
                ", email='" + email + '\'' +
                ", celular='" + celular + '\'' +
                ", valor=" + valor +
                ", fechaPrestamo=" + fechaPrestamo +
                ", fechaEntrega=" + fechaEntrega +
                ", numReserva=" + numReserva +
                '}';
    }
}
