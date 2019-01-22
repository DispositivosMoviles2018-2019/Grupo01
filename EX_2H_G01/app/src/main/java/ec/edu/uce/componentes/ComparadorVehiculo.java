package ec.edu.uce.componentes;

import java.util.Comparator;

import ec.edu.uce.modelo.Vehiculo;

public class ComparadorVehiculo {


    public static Comparator<Vehiculo> getCompPorCosto(boolean asc) {
        if (asc) {
            return new Comparator<Vehiculo>() {
                @Override
                public int compare(Vehiculo o1, Vehiculo o2) {
                    return o1.getCosto().compareTo(o2.getCosto());
                }
            };
        } else {
            return new Comparator<Vehiculo>() {
                @Override
                public int compare(Vehiculo o1, Vehiculo o2) {
                    return o2.getCosto().compareTo(o1.getCosto());
                }
            };
        }
    }
}
