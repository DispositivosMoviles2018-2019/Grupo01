package ec.edu.uce.servicios;

import android.content.Context;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import ec.edu.uce.componentes.CustomException;
import ec.edu.uce.componentes.Funcion;
import ec.edu.uce.modelo.Reserva;
import ec.edu.uce.modelo.Vehiculo;
import ec.edu.uce.repositorios.ReservaRepositorio;
import ec.edu.uce.repositorios.VehiculoRepositorio;

public class ReservaServicio {


    private DateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");


    private ReservaRepositorio reservaRepositorio;
    private VehiculoServicio vehiculoServicio;
    private VehiculoRepositorio vehiculoRepositorio;
    private Context context;


    public ReservaServicio(Context context) {
        this.reservaRepositorio = new ReservaRepositorio(context);
        this.context = context;
    }


    public void crear(Reserva reserva) {
        System.out.println("Entre al crear reserva");
        System.out.println(reserva.getPlaca());
        String plaquita = reserva.getPlaca();
//        Vehiculo alquilar = vehiculoServicio.buscarPorPlaca(plaquita);
//                Vehiculo editar = new Vehiculo(alquilar.getPlaca(), alquilar.getMarca()
//                        , alquilar.getFechaFabricacion(), alquilar.getCosto()
//                        , alquilar.getMatriculado(), alquilar.getColor(), alquilar.getFoto()
//                        , false, alquilar.getTipo());
                reservaRepositorio.crear(reserva);
//                vehiculoRepositorio.actualizar(reserva.getPlaca(), editar);


    }


//    public void actualizar(String placa, Vehiculo vehiculo) {
//        if (placa.equalsIgnoreCase(vehiculo.getPlaca())) {
//            vehiculoRepositorio.actualizar(placa, vehiculo);
//        } else {
//            if (buscarPorPlaca(vehiculo.getPlaca()) == null) {
//                vehiculoRepositorio.actualizar(placa, vehiculo);
//            } else {
//                throw new CustomException("Ya existe un vehiculo con la placa " + vehiculo.getPlaca());
//            }
//        }
//    }


    public Reserva buscarPorNumReserva(String numReserva) {
        List<Reserva> usuarios = reservaRepositorio.buscarPorParametro(new Funcion<Reserva, String>() {
            @Override
            public String apply(Reserva input) {
                return input.getNumReserva().toString();
            }
        }, numReserva);

        return usuarios.isEmpty() ? null : usuarios.get(0);
    }



    public List<Reserva> listarTodos() {
        return reservaRepositorio.listar();
    }

    public void borrar(Reserva reserva) {
        reservaRepositorio.borrar(reserva.getNumReserva().toString());
    }

    public void close() {
        vehiculoRepositorio.close();
    }
}
