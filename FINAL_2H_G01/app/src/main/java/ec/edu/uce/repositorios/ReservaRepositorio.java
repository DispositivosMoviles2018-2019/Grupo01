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
import java.util.Collection;
import java.util.List;

import ec.edu.uce.componentes.CustomException;
import ec.edu.uce.componentes.Funcion;
import ec.edu.uce.database.DatabaseHelper;
import ec.edu.uce.database.Tablas.RESERVA;
import ec.edu.uce.modelo.Reserva;

public class ReservaRepositorio implements InterfazCRUD<Reserva, String> {

    private DateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");


    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;
    private ContentValues values;

    public ReservaRepositorio(Context context) {
        databaseHelper = new DatabaseHelper(context);
    }

    @Override
    public String crear(Reserva reserva) {
        database = databaseHelper.getWritableDatabase();
        values = new ContentValues();
        values.put(RESERVA.COL_PLACA, reserva.getPlaca());
        values.put(RESERVA.COL_NUMERO_RESERVA, reserva.getNumReserva());
        values.put(RESERVA.COL_EMAIL, reserva.getEmail());
        values.put(RESERVA.COL_CELULAR, reserva.getCelular());
        values.put(RESERVA.COL_VALOR, reserva.getValor());
        values.put(RESERVA.COL_FECHA_PRESTAMO, sdf.format(reserva.getFechaPrestamo()));
        values.put(RESERVA.COL_FECHA_ENTREGA, sdf.format(reserva.getFechaEntrega()));


        long idNuevaReserva = database.insert(RESERVA.NOMBRE_TABLA, null, values);

        if (idNuevaReserva != -1) {
            return "Reserva " + reserva.getNumReserva() + " creada correctamente";
        } else {
            throw new CustomException("Error al crear la reserva: " + reserva.getNumReserva());
        }
    }

    @Override
    public String actualizar(String numReserva, Reserva reserva) {

        return null;
    }


    @Override
    public String borrar(String numReserva) {

        database = databaseHelper.getWritableDatabase();
        String selection = RESERVA.COL_NUMERO_RESERVA + " = ?";
        String[] selectionArgs = {numReserva.toString()};

        int filasEliminadas = database.delete(RESERVA.NOMBRE_TABLA, selection, selectionArgs);

        if (filasEliminadas > 0) {
            return "Reserva " + numReserva + " eliminada correctamente";
        } else {
            throw new CustomException("Error al borrar la reserva " + numReserva);
        }
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
                RESERVA.COL_PLACA,
                RESERVA.COL_NUMERO_RESERVA,
                RESERVA.COL_EMAIL,
                RESERVA.COL_CELULAR,
                RESERVA.COL_VALOR,
                RESERVA.COL_FECHA_PRESTAMO,
                RESERVA.COL_FECHA_ENTREGA,

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
            reserva.setPlaca(cursor.getString(cursor.getColumnIndex(RESERVA.COL_PLACA)));
            reserva.setNumReserva(cursor.getInt(cursor.getColumnIndex(RESERVA.COL_NUMERO_RESERVA)));
            reserva.setEmail(cursor.getString(cursor.getColumnIndex(RESERVA.COL_EMAIL)));
            reserva.setCelular(cursor.getString(cursor.getColumnIndex(RESERVA.COL_CELULAR)));
            reserva.setValor(cursor.getInt(cursor.getColumnIndex(RESERVA.COL_VALOR)));

            try {
                reserva.setFechaPrestamo(sdf.parse(cursor.getString(cursor.getColumnIndex(RESERVA.COL_FECHA_PRESTAMO))));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            try {
                reserva.setFechaEntrega(sdf.parse(cursor.getString(cursor.getColumnIndex(RESERVA.COL_FECHA_ENTREGA))));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            reservas.add(reserva);
        }
        cursor.close();

        return reservas;
    }

    public void close() {
        databaseHelper.close();
    }
}
