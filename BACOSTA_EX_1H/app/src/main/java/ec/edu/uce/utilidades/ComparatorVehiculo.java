package ec.edu.uce.utilidades;

import java.util.Comparator;

import ec.edu.uce.modelo.Vehiculo;

public class ComparatorVehiculo {
    public static Comparator<Vehiculo> getCompByPlaca() {
        return new Comparator<Vehiculo>() {
            @Override
            public int compare(Vehiculo o1, Vehiculo o2) {
                return o2.getPlaca().compareToIgnoreCase(o1.getPlaca());
            }
        };
    }
}
