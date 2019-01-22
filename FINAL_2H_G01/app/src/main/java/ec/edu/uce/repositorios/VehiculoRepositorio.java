package ec.edu.uce.repositorios;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.BaseColumns;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import ec.edu.uce.componentes.CustomException;
import ec.edu.uce.componentes.Funcion;
import ec.edu.uce.database.DatabaseHelper;
import ec.edu.uce.database.Tablas.VEHICULO;
import ec.edu.uce.modelo.Vehiculo;

public class VehiculoRepositorio implements InterfazCRUD<Vehiculo, String> {

    private DateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;
    private ContentValues values;

    public VehiculoRepositorio(Context context) {
        databaseHelper = new DatabaseHelper(context);
    }

    @Override
    public String crear(Vehiculo vehiculo) {
        database = databaseHelper.getWritableDatabase();
        values = new ContentValues();
        values.put(VEHICULO.COL_PLACA, vehiculo.getPlaca());
        values.put(VEHICULO.COL_MARCA, vehiculo.getMarca());
        values.put(VEHICULO.COL_FECHA, sdf.format(vehiculo.getFechaFabricacion()));
        values.put(VEHICULO.COL_COSTO, vehiculo.getCosto());
        values.put(VEHICULO.COL_MATRICULADO, vehiculo.getMatriculado());
        values.put(VEHICULO.COL_COLOR, vehiculo.getColor());
        values.put(VEHICULO.COL_FOTO, convertBitmatToByteArray(vehiculo.getFoto()));
        values.put(VEHICULO.COL_ESTADO, vehiculo.getEstado());
        values.put(VEHICULO.COL_TIPO, vehiculo.getTipo());

        long idNuevoVehiculo = database.insert(VEHICULO.NOMBRE_TABLA, null, values);

        if (idNuevoVehiculo != -1) {
            return "Vehiculo " + vehiculo.getPlaca() + " guardado correctamente";
        } else {
            throw new CustomException("Error al guardar el vehiculo " + vehiculo.getPlaca());
        }
    }

    @Override
    public String actualizar(String placa, Vehiculo vehiculo) {
        database = databaseHelper.getWritableDatabase();
        values = new ContentValues();
        values.put(VEHICULO.COL_PLACA, vehiculo.getPlaca());
        values.put(VEHICULO.COL_MARCA, vehiculo.getMarca());
        values.put(VEHICULO.COL_FECHA, sdf.format(vehiculo.getFechaFabricacion()));
        values.put(VEHICULO.COL_COSTO, vehiculo.getCosto());
        values.put(VEHICULO.COL_MATRICULADO, vehiculo.getMatriculado());
        values.put(VEHICULO.COL_COLOR, vehiculo.getColor());
        values.put(VEHICULO.COL_FOTO, convertBitmatToByteArray(vehiculo.getFoto()));
        values.put(VEHICULO.COL_ESTADO, vehiculo.getEstado());
        values.put(VEHICULO.COL_TIPO, vehiculo.getTipo());

        String selection = VEHICULO.COL_PLACA + " = ?";
        String[] selectionArgs = {placa};

        long filasActualizadas = database.update(VEHICULO.NOMBRE_TABLA, values, selection, selectionArgs);

        if (filasActualizadas > 0) {
            return "Vehiculo " + placa + " actualizado correctamente";
        } else {
            throw new CustomException("Error al actualizar el vehiculo " + placa);
        }
    }

    @Override
    public String borrar(String placa) {
        database = databaseHelper.getWritableDatabase();
        String selection = VEHICULO.COL_PLACA + " = ?";
        String[] selectionArgs = {placa};

        int filasEliminadas = database.delete(VEHICULO.NOMBRE_TABLA, selection, selectionArgs);

        if (filasEliminadas > 0) {
            return "Vehiculo " + placa + " eliminado correctamente";
        } else {
            throw new CustomException("Error al borrar el vehiculo " + placa);
        }
    }

    @Override
    public <F> List<Vehiculo> buscarPorParametro(Funcion<Vehiculo, F> atributo, F parametro) {
        List<Vehiculo> resultado = new ArrayList<>();

        for (Vehiculo element : listar()) {
            if (atributo.apply(element).equals(parametro)) {
                resultado.add(element);
            }
        }

        return resultado;
    }

    @Override
    public List<Vehiculo> listar() {
        List<Vehiculo> vehiculos = new ArrayList<>();
        database = databaseHelper.getReadableDatabase();
        String[] projection = {
                BaseColumns._ID,
                VEHICULO.COL_PLACA,
                VEHICULO.COL_MARCA,
                VEHICULO.COL_FECHA,
                VEHICULO.COL_COSTO,
                VEHICULO.COL_MATRICULADO,
                VEHICULO.COL_COLOR,
                VEHICULO.COL_FOTO,
                VEHICULO.COL_ESTADO,
                VEHICULO.COL_TIPO
        };

        Cursor cursor = database.query(
                VEHICULO.NOMBRE_TABLA,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        while (cursor.moveToNext()) {
            Vehiculo vehiculo = new Vehiculo();
            vehiculo.setPlaca(cursor.getString(cursor.getColumnIndex(VEHICULO.COL_PLACA)));
            vehiculo.setMarca(cursor.getString(cursor.getColumnIndex(VEHICULO.COL_MARCA)));
            vehiculo.setCosto(cursor.getDouble(cursor.getColumnIndex(VEHICULO.COL_COSTO)));
            vehiculo.setMatriculado(cursor.getInt(cursor.getColumnIndex(VEHICULO.COL_MATRICULADO)) != 0);
            vehiculo.setColor(cursor.getString(cursor.getColumnIndex(VEHICULO.COL_COLOR)));
            vehiculo.setFoto(convertByteArrayToBitmat(cursor.getBlob(cursor.getColumnIndex(VEHICULO.COL_FOTO))));
            vehiculo.setEstado(cursor.getInt(cursor.getColumnIndex(VEHICULO.COL_ESTADO)) != 0);
            vehiculo.setTipo(cursor.getString(cursor.getColumnIndex(VEHICULO.COL_TIPO)));

            try {
                vehiculo.setFechaFabricacion(sdf.parse(cursor.getString(cursor.getColumnIndex(VEHICULO.COL_FECHA))));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            vehiculos.add(vehiculo);
        }
        cursor.close();

        return vehiculos;
    }

    public void close() {
        databaseHelper.close();
    }
    
    private byte[] convertBitmatToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, bos);
        return bos.toByteArray();
    }

    private Bitmap convertByteArrayToBitmat(byte[] bytes) {
        return BitmapFactory.decodeByteArray(bytes , 0, bytes.length);
    }
}
