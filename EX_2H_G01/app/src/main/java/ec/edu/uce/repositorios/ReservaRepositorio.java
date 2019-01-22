package ec.edu.uce.repositorios;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import ec.edu.uce.componentes.CustomException;
import ec.edu.uce.componentes.Funcion;
import ec.edu.uce.database.DatabaseHelper;
import ec.edu.uce.database.Tablas;
import ec.edu.uce.database.Tablas.RESERVA;
import ec.edu.uce.modelo.Reserva;
import ec.edu.uce.modelo.Vehiculo;

public class ReservaRepositorio implements InterfazCRUD<Reserva, Integer>{

    private DateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

    private VehiculoRepositorio vehiculoRepositorio;
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;
    private ContentValues values;

    public ReservaRepositorio(Context context) {
        databaseHelper = new DatabaseHelper(context);
        vehiculoRepositorio = new VehiculoRepositorio(context);
    }

    @Override
    public String crear(Reserva reserva) {
        database = databaseHelper.getWritableDatabase();
        values = new ContentValues();
        values.put(RESERVA.COL_NUMERO, reserva.getNumero());
        values.put(RESERVA.COL_EMAIL, reserva.getEmail());
        values.put(RESERVA.COL_CELULAR, reserva.getCelular());
        values.put(RESERVA.COL_ENTREGA, sdf.format(reserva.getFechaEntrega()));
        values.put(RESERVA.COL_PRESTAMO, sdf.format(reserva.getFechaPrestamo()));
        values.put(RESERVA.COL_VALOR, reserva.getValor());
        values.put(RESERVA.COL_PLACA, reserva.getVehiculo().getPlaca());

        long idNuevaReserva = database.insert(RESERVA.NOMBRE_TABLA, null, values);

        if (idNuevaReserva != -1) {
            return "Reserva " + reserva.getNumero() + " guardada correctamente";
        } else {
            throw new CustomException("Error al guardar la reserva " + reserva.getNumero());
        }
    }

    @Override
    public String actualizar(Integer id, Reserva reserva) {
        return null;
    }

    @Override
    public String borrar(Integer id) {
        return null;
    }

    @Override
    public <F> List<Reserva> buscarPorParametro(Funcion<Reserva, F> atributo, F parametro) {
        List<Reserva> resultado = new ArrayList<>();

        for (Reserva element : listar()) {
            if (atributo.apply(element).equals(parametro)) {
                resultado.add(element);
            }
        }

        return resultado;
    }

    @Override
    public List<Reserva> listar() {
        List<Reserva> reservas = new ArrayList<>();
        database = databaseHelper.getReadableDatabase();
        String[] projection = {
                BaseColumns._ID,
                RESERVA.COL_NUMERO,
                RESERVA.COL_EMAIL,
                RESERVA.COL_CELULAR,
                RESERVA.COL_PRESTAMO,
                RESERVA.COL_ENTREGA,
                RESERVA.COL_VALOR,
                RESERVA.COL_PLACA
        };

        Cursor cursor = database.query(
                RESERVA.NOMBRE_TABLA,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        while (cursor.moveToNext()) {
            Reserva reserva = new Reserva();
            reserva.setNumero(cursor.getInt(cursor.getColumnIndex(RESERVA.COL_NUMERO)));
            reserva.setEmail(cursor.getString(cursor.getColumnIndex(RESERVA.COL_EMAIL)));
            reserva.setCelular(cursor.getString(cursor.getColumnIndex(RESERVA.COL_CELULAR)));
            reserva.setValor(cursor.getDouble(cursor.getColumnIndex(RESERVA.COL_VALOR)));

            List<Vehiculo> vehiculos = vehiculoRepositorio.buscarPorParametro(new Funcion<Vehiculo, String>() {
                @Override
                public String apply(Vehiculo input) {
                    return input.getPlaca();
                }
            }, cursor.getString(cursor.getColumnIndex(RESERVA.COL_PLACA)));
            reserva.setVehiculo(vehiculos.isEmpty()? new Vehiculo() : vehiculos.get(0));

            try {
                reserva.setFechaPrestamo(sdf.parse(cursor.getString(cursor.getColumnIndex(RESERVA.COL_PRESTAMO))));
                reserva.setFechaEntrega(sdf.parse(cursor.getString(cursor.getColumnIndex(RESERVA.COL_ENTREGA))));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            reservas.add(reserva);
        }
        cursor.close();

        System.out.println("RESERVAS:" + reservas.toString());

        return reservas;
    }


    public void close() {
        databaseHelper.close();
    }
}
