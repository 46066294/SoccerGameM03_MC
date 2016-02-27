import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mat on 22/02/2016.
 */
public class Equipo {

    private String nombre = "";
    private String estadio = "";
    private List<Jugador> jugadores;
    private Entrenador entrenador;

    public void cambioLiga(){

    }

    public void addJugador(Jugador e){
        jugadores.add(e);
    }

    //constr
    public Equipo() {}

    public Equipo(String nombre, String estadio, Entrenador entrenador) {
        this.nombre = nombre;
        this.estadio = estadio;
        this.jugadores = new ArrayList<Jugador>();
        this.entrenador = entrenador;
    }

    //getters-setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEstadio() {
        return estadio;
    }

    public void setEstadio(String estado) {
        this.estadio = estado;
    }

    public List<Jugador> getJugadores() {
        return jugadores;
    }

    public void setJugadores(List<Jugador> jugadores) {
        this.jugadores = jugadores;
    }

    public Entrenador getEntrenador() {
        return entrenador;
    }

    public void setEntrenador(Entrenador entrenador) {
        this.entrenador = entrenador;
    }

    public String toStringEquipo(){
        String jug = "";
        for(int cont = 0; cont < jugadores.size(); cont++){
            //jug = jug + "\n" + jugadores.get(cont).toString();
            jug = jug + "\n" + jugadores.get(cont).toStringJugador();
        }
        return
                "Equipo: " +
                        "nombre = " + nombre + "\n" +
                        "estadio = " + estadio + "\n" +
                        "\nJugadores:\t" +
                        jug;
    }

    public String toStringEquipoLiga() {
        String jug = "";
        for(int cont = 0; cont < jugadores.size(); cont++){
            jug = jug + "\n" + jugadores.get(cont).toStringJugadorLiga();
        }
        return jug;
    }
}
