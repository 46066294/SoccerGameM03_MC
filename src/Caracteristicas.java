/**
 * Created by Mat on 22/02/2016.
 */
public class Caracteristicas {

    private int agilidad;
    private int fuerza;
    private int velocidad;
    private int pase;
    private int penalti;

    /*
    public boolean aumentarCaracteristicas(){
        return ;
    }*/

    //constr
    public Caracteristicas(){}

    public Caracteristicas(int agilidad, int fuerza, int velocidad, int pase, int penalti) {
        this.agilidad = agilidad;
        this.fuerza = fuerza;
        this.velocidad = velocidad;
        this.pase = pase;
        this.penalti = penalti;
    }

    //getters-setters

    public int getAgilidad() {
        return agilidad;
    }

    public void setAgilidad(int agilidad) {
        this.agilidad = agilidad;
    }

    public int getFuerza() {
        return fuerza;
    }

    public void setFuerza(int fuerza) {
        this.fuerza = fuerza;
    }

    public int getVelocidad() {
        return velocidad;
    }

    public void setVelocidad(int velocidad) {
        this.velocidad = velocidad;
    }

    public int getPase() {
        return pase;
    }

    public void setPase(int pase) {
        this.pase = pase;
    }

    public int getPenalti() {
        return penalti;
    }

    public void setPenalti(int penalti) {
        this.penalti = penalti;
    }


}
