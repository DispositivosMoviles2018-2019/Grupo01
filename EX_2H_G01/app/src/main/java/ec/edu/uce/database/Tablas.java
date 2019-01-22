package ec.edu.uce.database;

import android.provider.BaseColumns;

public final class Tablas {

    private Tablas() {
    }

    public static class USUARIO implements BaseColumns {
        public static final String NOMBRE_TABLA = "usuario";
        public static final String COL_USUARIO = "usuario";
        public static final String COL_CLAVE = "clave";
    }

    public static class VEHICULO implements BaseColumns {
        public static final String NOMBRE_TABLA = "vehiculo";
        public static final String COL_PLACA = "placa";
        public static final String COL_MARCA = "marca";
        public static final String COL_FECHA = "fechaFabricacion";
        public static final String COL_COSTO = "costo";
        public static final String COL_MATRICULADO = "matriculado";
        public static final String COL_COLOR = "color";
        public static final String COL_FOTO = "foto";
        public static final String COL_ESTADO = "estado";
        public static final String COL_TIPO = "tipo";
    }

    public static class RESERVA implements BaseColumns {
        public static final String NOMBRE_TABLA = "reserva";
        public static final String COL_NUMERO = "numero";
        public static final String COL_EMAIL = "email";
        public static final String COL_CELULAR = "celular";
        public static final String COL_PRESTAMO = "fechaPrestamo";
        public static final String COL_ENTREGA = "fechaEntrega";
        public static final String COL_VALOR = "valor";
        public static final String COL_PLACA = "placa";
    }
}
