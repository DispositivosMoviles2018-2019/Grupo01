package ec.edu.uce.componentes;

public interface Funcion<IN, OUT> {
    OUT apply(IN input);
}
