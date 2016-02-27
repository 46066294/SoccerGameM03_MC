
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

            db = DataConnection.getInstance();
            String menu = "";
            boolean on = true;//condicio de sortida del programa

            //Se crean los siguientes datos predefinidos para almacenarlos en la bbdd

            File file = new File("soccerData.db");

            //Los siguientes datos solo se crearan una vez
            if(file.length() < 1500){
                //public Jugador(String dni, String nombre, String apellido, double altura)
                Jugador jugador1 = new Jugador("12345678Q", "Primero", "Apellido1", 2000);
                Jugador jugador2 = new Jugador("87654321A", "Segundo", "Apellido2", 1950);
                Jugador jugador3 = new Jugador("3333333", "Tercero", "Apellido3", 1833);
                Jugador jugador4 = new Jugador("444444444", "Cuarto", "Apellido4", 1944);
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

                jugador1.getCaracteristicas().setFuerza(6);
                equipo1.addJugador(jugador1);
                jugador2.getCaracteristicas().setFuerza(6);
                equipo1.addJugador(jugador2);
                //jugador3.getCaracteristicas().setFuerza(6);
                equipo1.addJugador(jugador3);
                equipo2.addJugador(jugador4);
                equipo2.addJugador(jugador5);

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
                System.out.println("8--> Crear jugadores");
                System.out.println("0--> Salir del programa");
                System.out.println(" ");
                menu = input.nextLine();

                switch (menu) {
                    case "0": {
                        System.out.println("\n...salir");
                        //db.close();
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

        if(!result.hasNext()){
            try {
                throw new Exception("\n...el equipo no existe o no tiene jugadores");
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("...elige opcion de menu:");
            }
        }
        else{
            while(result.hasNext()){
                System.out.println(result.next().toStringEquipo());
            }
        }

    }//jugadoresDeUnEquipoSolicitado


    private static void jugadoresSoda(ObjectContainer db, Scanner input) {
        System.out.println("Entra equipo 1:");
        String equipo1 = input.nextLine();

        System.out.println("Entra equipo 2:");
        String equipo2 = input.nextLine();

        ObjectSet<Equipo> debuger1 = db.queryByExample(new Equipo(equipo1, null, null));
        ObjectSet<Equipo> debuger2 = db.queryByExample(new Equipo(equipo2, null, null));

        if(debuger1.hasNext() && debuger2.hasNext()){
            Query q = db.query();
            q.constrain(new Equipo(equipo1, null, null));
            ObjectSet<Equipo> result = q.execute();
            System.out.println(result.next().toStringEquipo());

            System.out.println("\n");

            Query q1 = db.query();
            q1.constrain(new Equipo(equipo2, null, null));
            ObjectSet<Equipo> result1 = q1.execute();
            System.out.println(result1.next().toStringEquipo());

        }
        else{
            try {
                throw new Exception("\n...los equipos no existen o no tienen jugadores");
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("...elige opcion de menu:");
            }
        }


    }//jugadoresSoda


    private static void fuerza5(ObjectContainer db, Scanner input) {
        System.out.println("Entra equipo:");
        String equipo = input.nextLine();

        ObjectSet<Equipo> result = db.queryByExample(new Equipo(equipo, null, null));

        if(!result.hasNext()){
            try {
                throw new Exception("\n...el equipo no existe o no tiene jugadores");
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("...elige opcion de menu:");
            }
        }
        else{
            List<Jugador> jugadores = result.get(0).getJugadores();

            int i = 0;
            boolean flag = false;
            while(i < jugadores.size()) {
                if(jugadores.get(i).getCaracteristicas().getFuerza() <= 5){
                    flag = true;
                    System.out.print(jugadores.get(i).getNombre() + " fuerza: " +
                            jugadores.get(i).getCaracteristicas().getFuerza() +"\n");
                }

                i++;
            }
            if(!flag)
                System.out.println("...no hay jugadores con fuerza inferior o igual a 5");
        }

    }//fuerza5


    private static void jugadoresEnLiga(ObjectContainer db, Scanner input) {
        System.out.println("Entra liga:");
        String liga = input.nextLine();

        ObjectSet<Liga> result = db.queryByExample(new Liga(liga, 0, null));

        if(!result.hasNext()){
            try {
                throw new Exception("...la liga no existe");
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("...entra opcion de menu");
            }
        }
        else{
            List<Equipo> equipos = result.get(0).getEquipos();

            if(equipos.isEmpty()){
                try {
                    throw new Exception("...no hay equipos en esta liga");
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("...entra opcion de menu");
                }
            }
            else{
                int i = 0;
                while(i < equipos.size()) {
                    System.out.println(equipos.get(i).toStringEquipoLiga());
                    i++;
                }
            }
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
