package ec.edu.uce.servicios;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ec.edu.uce.componentes.CustomException;
import ec.edu.uce.componentes.Funcion;
import ec.edu.uce.modelo.Reserva;
import ec.edu.uce.repositorios.ReservaRepositorio;

public class ReservaServicio {

    private ReservaRepositorio reservaRepositorio;
    private Context context;

    public ReservaServicio(Context context) {
        this.context = context;
        this.reservaRepositorio = new ReservaRepositorio(context);
    }

    public void crear(Reserva reserva) {
        if (buscarPorNumero(reserva.getNumero()) == null) {
            reservaRepositorio.crear(reserva);
        } else {
            throw new CustomException("Ya existe una reserva con ese numero " + reserva.getNumero());
        }
    }

    public Reserva buscarPorNumero(Integer numero) {
        List<Reserva> usuarios = reservaRepositorio.buscarPorParametro(new Funcion<Reserva, Integer>() {
            @Override
            public Integer apply(Reserva input) {
                return input.getNumero();
            }
        }, numero);

        return usuarios.isEmpty()? null : usuarios.get(0);
    }

    public List<Reserva> listar() {
        return reservaRepositorio.listar();
    }

    public void close() {
        reservaRepositorio.close();
    }

    public Integer getNuevoNumero() {
        Integer numero;
        do {
            numero = randomInt(1000, 9999);
        } while (buscarPorNumero(numero) != null);

        return numero;
    }


    private static int randomInt(int min, int max) {
        Random rand = new Random();
        return rand.nextInt((max - min) + 1) + min;
    }
}
