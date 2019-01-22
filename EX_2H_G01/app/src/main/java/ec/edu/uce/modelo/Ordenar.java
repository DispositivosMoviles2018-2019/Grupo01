package ec.edu.uce.modelo;

import java.io.Serializable;

public class Ordenar implements Serializable {
    private String atributo;
    private boolean asd;

    public Ordenar() {
    }

    public Ordenar(String atributo, boolean asd) {
        this.atributo = atributo;
        this.asd = asd;
    }

    public String getAtributo() {
        return atributo;
    }

    public void setAtributo(String atributo) {
        this.atributo = atributo;
    }

    public boolean isAsd() {
        return asd;
    }

    public void setAsd(boolean asd) {
        this.asd = asd;
    }
}
