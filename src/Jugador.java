/**Clase que representa un jugador de futbol
 * Created by Mat on 22/02/2016.
 */
public class Jugador {

    private String dni = "";
    private String nombre = "";
    private String apellido = "";
    private double altura = 0.0;
    private Caracteristicas caracteristicas;
    private Equipo equipo;


    public void traspaso(int indexActual, Equipo nuevoEquipo){
        equipo.seRetiraJugador(indexActual);
        setEquipo(nuevoEquipo);
        nuevoEquipo.addJugador(this);
    }

    //constr

    public Jugador(){}

    public Jugador(String dni, String nombre, String apellido, double altura) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.altura = altura;
        this.caracteristicas = new Caracteristicas();
    }

    //getters-setters

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public double getAltura() {
        return altura;
    }

    public void setAltura(double altura) {
        this.altura = altura;
    }

    public Caracteristicas getCaracteristicas() {
        return caracteristicas;
    }

    public void setCaracteristicas(Caracteristicas caracteristicas) {
        this.caracteristicas = caracteristicas;
    }

    public Equipo getEquipo() {
        return equipo;
    }

    public void setEquipo(Equipo equipo) {
        this.equipo = equipo;
    }


    public String toStringJugador(){
        return
                "\nJugador: " +
                        "nombre = " + nombre + " " +
                        "apellido = " + apellido + " " +
                        "dni = " + dni + " " +
                        "altura = " + altura + " " +
                        "\n\tCaracteristicas:\n\t" +
                        "\tagilidad " + caracteristicas.getAgilidad() + "\n\t" +
                        "\tfuerza " + caracteristicas.getFuerza() + "\n\t" +
                        "\tvelocidad " + caracteristicas.getVelocidad() + "\n\t" +
                        "\tpase " + caracteristicas.getPase() + "\n\t" +
                        "\tpenalti " + caracteristicas.getPenalti();
    }

    public String toStringJugadorLiga(){
        return
                "\nJugador: " +
                        "nombre = " + nombre + " " +
                        "apellido = " + apellido + " " +
                        "dni = " + dni;
    }

}
