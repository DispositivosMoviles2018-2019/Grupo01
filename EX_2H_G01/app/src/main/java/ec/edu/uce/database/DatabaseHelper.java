package ec.edu.uce.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "optativa.db";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_USUARIO);
        db.execSQL(SQL_CREATE_VEHICULO);
        db.execSQL(SQL_CREATE_RESERVA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_USUARIO);
        db.execSQL(SQL_DELETE_VEHICULO);
        db.execSQL(SQL_DELETE_RESERVA);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    private static final String SQL_CREATE_USUARIO =
            "CREATE TABLE " + Tablas.USUARIO.NOMBRE_TABLA
                    + " ("
                    + Tablas.USUARIO._ID + " INTEGER PRIMARY KEY, "
                    + Tablas.USUARIO.COL_USUARIO + " TEXT UNIQUE, "
                    + Tablas.USUARIO.COL_CLAVE + " TEXT"
                    + ")";;

    private static final String SQL_DELETE_USUARIO =
            "DROP TABLE IF EXISTS " + Tablas.USUARIO.NOMBRE_TABLA;


    private static final String SQL_CREATE_VEHICULO =
            "CREATE TABLE " + Tablas.VEHICULO.NOMBRE_TABLA
                    + " ("
                    + Tablas.VEHICULO._ID + " INTEGER PRIMARY KEY, "
                    + Tablas.VEHICULO.COL_PLACA + " TEXT UNIQUE, "
                    + Tablas.VEHICULO.COL_MARCA + " TEXT, "
                    + Tablas.VEHICULO.COL_FECHA + " TEXT, "
                    + Tablas.VEHICULO.COL_COSTO + " REAL, "
                    + Tablas.VEHICULO.COL_MATRICULADO + " INTEGER, "
                    + Tablas.VEHICULO.COL_COLOR + " INTEGER, "
                    + Tablas.VEHICULO.COL_FOTO + " BLOB, "
                    + Tablas.VEHICULO.COL_ESTADO + " INTEGER, "
                    + Tablas.VEHICULO.COL_TIPO + " TEXT"
                    + ")";

    private static final String SQL_DELETE_VEHICULO =
            "DROP TABLE IF EXISTS " + Tablas.VEHICULO.NOMBRE_TABLA;


    private static final String SQL_CREATE_RESERVA =
            "CREATE TABLE " + Tablas.RESERVA.NOMBRE_TABLA
                    + " ("
                    + Tablas.RESERVA._ID + " INTEGER PRIMARY KEY, "
                    + Tablas.RESERVA.COL_NUMERO + " INTEGER UNIQUE, "
                    + Tablas.RESERVA.COL_EMAIL + " TEXT, "
                    + Tablas.RESERVA.COL_CELULAR + " TEXT, "
                    + Tablas.RESERVA.COL_PRESTAMO + " TEXT, "
                    + Tablas.RESERVA.COL_ENTREGA + " TEXT, "
                    + Tablas.RESERVA.COL_VALOR + " REAL, "
                    + Tablas.VEHICULO.COL_PLACA + " TEXT"
                    + ")";

    private static final String SQL_DELETE_RESERVA =
            "DROP TABLE IF EXISTS " + Tablas.VEHICULO.NOMBRE_TABLA;
}
