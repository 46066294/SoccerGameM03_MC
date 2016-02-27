
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.query.Query;

import java.io.File;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Mat on 23/02/2016.
 */
public class SoccerMain {
    public static void main(String[] args) throws Exception {
        System.out.println("EjerciciosM03 ::Marc Cano:: db4o");
        Scanner input = new Scanner(System.in);

        ObjectContainer db = null;
        try {
            //db = Db4o.openFile("persons.data"); //deprecated
            //db = Db4oEmbedded.OpenFile(Db4oEmbedded.NewConfiguration(), YapFileName);

            /*
            Person brian = new Person("Brian", "Goetz", 39);
            db.store(brian);
            db.commit();
            // Find all the Brians
            ObjectSet objBrian = db.queryByExample(new Person("Brian", null, 0));

            while (objBrian.hasNext())
                System.out.println(objBrian.next());
             */

            //db = Db4oEmbedded.openFile("soccerData.db");

            db = DataConnection.getInstance();
            String menu = "";
            boolean on = true;//condicio de sortida del programa

            ////////////////////////////////////////////////////////////////////////////////

            File file = new File("soccerData.db");
            //System.out.println(file.length());
            if(file.length() < 1500){
                //public Jugador(String dni, String nombre, String apellido, double altura)
                Jugador jugador1 = new Jugador("12345678Q", "Primero", "Apellido1", 2000);
                Jugador jugador2 = new Jugador("87654321A", "Segundo", "Apellido2", 1950);
                Jugador jugador3 = new Jugador("3333333", "Tercero", "Apellido3", 1833);
                Jugador jugador4 = new Jugador("4444444", "Cuarto", "Apellido4", 1944);
                Jugador jugador5 = new Jugador("555555555", "Quinto", "Apellido5", 1955);


                //public Entrenador(String nombre, int anyosExperiencia)
                Entrenador entrenadorBarza = new Entrenador("Luis Enrique", 20);
                Entrenador entrenadorMandril = new Entrenador("Zidane", 3);

                //public Equipo(String nombre, String estado, Entrenador entrenador)
                Equipo equipo1 = new Equipo("Barça", "Camp Nou", entrenadorBarza);
                Equipo equipo2 = new Equipo("Mandril", "Bernabeu", entrenadorMandril);

                //public Liga(String nombre, int categoria, String patrocinador)
                Liga liga1 = new Liga("1a Division", 1, "Telecinco");
                Liga liga2 = new Liga("2a Division", 2, "tve2");

                //-----------------------------------------------------------

                equipo1.addJugador(jugador1);
                jugador2.getCaracteristicas().setFuerza(6);
                equipo1.addJugador(jugador2);
                equipo1.addJugador(jugador3);
                equipo2.addJugador(jugador4);
                equipo2.addJugador(jugador5);

                //db.store(equipo1);
                //db.store(equipo2);

                liga1.addEquipo(equipo1);
                liga1.addEquipo(equipo2);

                db.store(liga1);
                db.commit();
            }
            ////////////////////////////////////////////////////////////////////////////////

            while (on) {
                System.out.println("\n");
                System.out.println("MENU:");
                System.out.println("1--> Jugadores de un equipo solicitado");
                System.out.println("2--> Jugadores de dos equipos solicitados utilizando una consulta SODA");
                System.out.println("3--> Los jugadores de un equipo que tenga una Fuerza menor o igual que 5");
                System.out.println("4--> Jugadores pertenecientes a una Liga");
                System.out.println("5--> Características de un jugador dado");
                System.out.println("6--> Jugadores que pertenece a un entrenador dado");
                System.out.println("7--> Equipos de una liga en concreta");
                System.out.println("0--> Salir del programa");
                System.out.println(" ");
                menu = input.nextLine();

                switch (menu) {
                    case "0": {
                        System.out.println("\n...salir");
                        on = false;
                        break;
                    }

                    case "1": {
                        jugadoresDeUnEquipoSolicitado(db, input);
                        break;
                    }

                    case "2": {
                        jugadoresSoda(db, input);
                        break;
                    }

                    case "3": {
                        fuerza5(db, input);
                        break;
                    }

                    case "4": {
                        jugadoresEnLiga(db, input);
                        break;
                    }

                    case "5": {
                        caractJugador(db, input);
                        break;
                    }

                    case "6": {
                        jugadoresPertenecenEntrenador(db, input);
                        break;
                    }

                    case "7": {
                        equiposDeUnaLiga(db, input);
                        break;
                    }

                    default: {
                        System.out.println("\n...entrada de menu incorrecta\n");
                        break;
                    }

                }//switch

            }
        } catch(Exception e){
            e.getStackTrace();
        }
        finally {
            db.close();
            input.close();
        }

    }//main




    private static void jugadoresDeUnEquipoSolicitado(ObjectContainer db, Scanner input) {
        System.out.println("Elige equipo:\n");
        String str = input.nextLine();

        ObjectSet<Equipo> result = db.queryByExample(new Equipo(str, null, null));

        while(result.hasNext()){
            System.out.println(result.next().toStringEquipo());
        }
    }//jugadoresDeUnEquipoSolicitado


    private static void jugadoresSoda(ObjectContainer db, Scanner input) {
        System.out.println("Entra equipo 1:");
        String equipo1 = input.nextLine();

        System.out.println("Entra equipo 2:");
        String equipo2 = input.nextLine();

        Query q = db.query();
        q.constrain(new Equipo(equipo1, null, null));
        ObjectSet<Equipo> result = q.execute();
        System.out.println(result.next().toStringEquipo());

        System.out.println("\n");

        Query q1 = db.query();
        q1.constrain(new Equipo(equipo2, null, null));
        ObjectSet<Equipo> result1 = q1.execute();
        System.out.println(result1.next().toStringEquipo());
    }//jugadoresSoda


    private static void fuerza5(ObjectContainer db, Scanner input) {
        System.out.println("Entra equipo:");
        String equipo = input.nextLine();

        ObjectSet<Equipo> result = db.queryByExample(new Equipo(equipo, null, null));
        List<Jugador> jugadores = result.get(0).getJugadores();

        int i = 0;
        while(i < jugadores.size()) {
            if(jugadores.get(i).getCaracteristicas().getFuerza() <= 5){
                System.out.print(jugadores.get(i).getNombre() + " fuerza: " +
                        jugadores.get(i).getCaracteristicas().getFuerza() +"\n");
            }

            i++;
        }
    }//fuerza5


    private static void jugadoresEnLiga(ObjectContainer db, Scanner input) {
        System.out.println("Entra liga:");
        String liga = input.nextLine();

        ObjectSet<Liga> result = db.queryByExample(new Liga(liga, 0, null));
        List<Equipo> equipos = result.get(0).getEquipos();

        int i = 0;
        while(i < equipos.size()) {
            System.out.println(equipos.get(i).toStringEquipoLiga());
            i++;
        }
    }//jugadoresEnLiga

    private static void caractJugador(ObjectContainer db, Scanner input) {
        System.out.println("Entra nombre del Jugador:");
        String nombre = input.nextLine();
        System.out.println("Entra apellido del Jugador:");
        String apellido = input.nextLine();

        ObjectSet<Liga> result = db.queryByExample(Liga.class);
        List<Equipo> equipos = result.get(0).getEquipos();

        int i = 0;
        int j = 0;
        while(i < equipos.size()) {
            while (j < equipos.get(i).getJugadores().size()){
                Jugador jugador = equipos.get(i).getJugadores().get(j);
                if(jugador.getNombre().equalsIgnoreCase(nombre) &&
                        jugador.getApellido().equalsIgnoreCase(apellido)){
                    System.out.println("Caracteristicas de " + jugador.getNombre() + " " +
                            jugador.getApellido());
                    System.out.println("\tagilidad: " + jugador.getCaracteristicas().getAgilidad());
                    System.out.println("\tfuerza: " + jugador.getCaracteristicas().getFuerza());
                    System.out.println("\tvelocidad: " + jugador.getCaracteristicas().getVelocidad());
                    System.out.println("\tpase: " + jugador.getCaracteristicas().getPase());
                    System.out.println("\tpenalti: " + jugador.getCaracteristicas().getPenalti());
                }
                j++;
            }
            j = 0;
            i++;
        }
    }//caractJugador


    private static void jugadoresPertenecenEntrenador(ObjectContainer db, Scanner input) {
        System.out.println("Entra nombre del Entrenador:");
        String entrenador = input.nextLine();

        ObjectSet<Equipo> result = db.queryByExample(Equipo.class);

        int equipos = result.size();
        for(int k = 0; k < equipos; k++){
            if(result.get(k).getEntrenador().getNombre().equalsIgnoreCase(entrenador)){
                System.out.println("Entrenador: " + result.get(k).getEntrenador().getNombre());
                System.out.println("Jugadores:\n");
                List<Jugador> jugadores = result.get(k).getJugadores();
                for(int i = 0; i < jugadores.size(); i++){
                    System.out.println(jugadores.get(i).getNombre() + " " +
                            jugadores.get(i).getApellido());
                }
                break;
            }
        }
    }//jugadoresPertenecenEntrenador


    private static void equiposDeUnaLiga(ObjectContainer db, Scanner input) {
        System.out.println("Entra nombre de la Liga:");
        String liga = input.nextLine();

        ObjectSet<Liga> result = db.queryByExample(Liga.class);

        for(int j = 0; j < result.size(); j++){
            if(liga.equalsIgnoreCase(result.get(j).getNombre())){
                int cantidadEquipos = result.get(0).getEquipos().size();
                List<Equipo> equipos = result.get(0).getEquipos();
                System.out.println("Equipos:");

                for(int i = 0; i < cantidadEquipos; i++){
                    System.out.println("\t" + equipos.get(i).getNombre());
                }
            }
        }
    }//equiposDeUnaLiga

}//SoccerMain class
