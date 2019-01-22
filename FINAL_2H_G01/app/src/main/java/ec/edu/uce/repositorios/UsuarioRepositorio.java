package ec.edu.uce.repositorios;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import ec.edu.uce.componentes.CustomException;
import ec.edu.uce.componentes.Funcion;
import ec.edu.uce.database.DatabaseHelper;
import ec.edu.uce.database.Tablas.USUARIO;
import ec.edu.uce.modelo.Usuario;

public class UsuarioRepositorio implements InterfazCRUD<Usuario, String> {

    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;
    private ContentValues values;

    public UsuarioRepositorio(Context context) {
        databaseHelper = new DatabaseHelper(context);
    }

    @Override
    public String crear(Usuario usuario) {
        database = databaseHelper.getWritableDatabase();
        values = new ContentValues();
        values.put(USUARIO.COL_USUARIO, usuario.getUsuario());
        values.put(USUARIO.COL_CLAVE, usuario.getCalve());

        long idNuevoUsuario = database.insert(USUARIO.NOMBRE_TABLA, null, values);

        if (idNuevoUsuario != -1) {
            return "Usuario " + usuario.getUsuario() + " guardado correctamente";
        } else {
            throw new CustomException("Error al guardar el usuario " + usuario.getUsuario());
        }
    }

    @Override
    public String actualizar(String id, Usuario obj) {
        return null;
    }

    @Override
    public String borrar(String id) {
        return null;
    }

    @Override
    public <F> List<Usuario> buscarPorParametro(Funcion<Usuario, F> atributo, F parametro) {
        List<Usuario> resultado = new ArrayList<>();

        for (Usuario element : listar()) {
            if (atributo.apply(element).equals(parametro)) {
                resultado.add(element);
            }
        }
        return resultado;
    }

    @Override
    public List<Usuario> listar() {
        List<Usuario> usuarios = new ArrayList<>();
        database = databaseHelper.getReadableDatabase();
        String[] projection = {
                BaseColumns._ID,
                USUARIO.COL_USUARIO,
                USUARIO.COL_CLAVE
        };

        Cursor cursor = database.query(
                USUARIO.NOMBRE_TABLA,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        while (cursor.moveToNext()) {
            Usuario usuario = new Usuario();
            usuario.setUsuario(cursor.getString(cursor.getColumnIndex(USUARIO.COL_USUARIO)));
            usuario.setCalve(cursor.getString(cursor.getColumnIndex(USUARIO.COL_CLAVE)));
            usuarios.add(usuario);
        }
        cursor.close();

        return usuarios;
    }

    public void close() {
        databaseHelper.close();
    }
}
