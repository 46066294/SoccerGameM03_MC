import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mat on 22/02/2016.
 */
public class Liga {

    private String nombre = "";
    private int categoria;
    private String patrocinador = "";
    private List<Equipo> equipos;

    public void cambioPatrocinador(){

    }

    //constr
    public Liga() {}

    public Liga(String nombre, int categoria, String patrocinador) {
        this.nombre = nombre;
        this.categoria = categoria;
        this.patrocinador = patrocinador;
        this.equipos = new ArrayList<Equipo>();
    }

    //getters-setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCategoria() {
        return categoria;
    }

    public void setCategoria(int categoria) {
        this.categoria = categoria;
    }

    public String getPatrocinador() {
        return patrocinador;
    }

    public void setPatrocinador(String patrocinador) {
        this.patrocinador = patrocinador;
    }

    public List<Equipo> getEquipos() {
        return equipos;
    }

    public void setEquipos(List<Equipo> equipos) {
        this.equipos = equipos;
    }

    public void addEquipo(Equipo equipo) {
        equipos.add(equipo);
    }
}
