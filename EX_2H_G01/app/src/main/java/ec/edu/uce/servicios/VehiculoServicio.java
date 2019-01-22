package ec.edu.uce.servicios;

import android.content.Context;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import ec.edu.uce.componentes.CustomException;
import ec.edu.uce.componentes.Funcion;
import ec.edu.uce.modelo.Vehiculo;
import ec.edu.uce.repositorios.VehiculoRepositorio;

public class VehiculoServicio {

    private VehiculoRepositorio vehiculoRepositorio;
    private Context context;

    public VehiculoServicio(Context context) {
        this.vehiculoRepositorio = new VehiculoRepositorio(context);
        this.context = context;
    }

    public void crear(Vehiculo vehiculo) {
        if (buscarPorPlaca(vehiculo.getPlaca()) == null) {
            vehiculoRepositorio.crear(vehiculo);
        } else {
            throw new CustomException("Ya existe un vehiculo con la placa " + vehiculo.getPlaca());
        }
    }

    public void actualizar(String placa, Vehiculo vehiculo) {
        if (placa.equalsIgnoreCase(vehiculo.getPlaca())) {
            vehiculoRepositorio.actualizar(placa, vehiculo);
        } else {
            if (buscarPorPlaca(vehiculo.getPlaca()) == null) {
                vehiculoRepositorio.actualizar(placa, vehiculo);
            } else {
                throw new CustomException("Ya existe un vehiculo con la placa " + vehiculo.getPlaca());
            }
        }
    }

    public Vehiculo buscarPorPlaca(String placa) {
        List<Vehiculo> usuarios = vehiculoRepositorio.buscarPorParametro(new Funcion<Vehiculo, String>() {
            @Override
            public String apply(Vehiculo input) {
                return input.getPlaca();
            }
        }, placa);

        return usuarios.isEmpty()? null : usuarios.get(0);
    }

    public List<Vehiculo> listarTodos() {
        return vehiculoRepositorio.listar();
    }

    public void borrar(Vehiculo vehiculo) {
        vehiculoRepositorio.borrar(vehiculo.getPlaca());
    }

    public void close() {
        vehiculoRepositorio.close();
    }
}
