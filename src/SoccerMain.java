
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.query.Query;
import java.io.File;
import java.util.List;
import java.util.Scanner;

/**Programa que gestiona una base de datos orientada
 * a objetos con nombre soccerData.db
 * Funcionalidades y consultas:
 *
 * 1--> Jugadores de un equipo solicitado
 * 2--> Jugadores de dos equipos solicitados utilizando una consulta SODA
 * 3--> Los jugadores de un equipo que tenga una Fuerza menor o igual que 5
 * 4--> Jugadores pertenecientes a una Liga
 * 5--> Características de un jugador dado
 * 6--> Jugadores que pertenece a un entrenador dado
 * 7--> Equipos de una liga en concreta
 * 8--> Crear jugadores para Barça
 * 9--> Eliminar base de datos y salir
 * 10--> Visualizar equipos
 * 11--> About
 * 0--> Salir del programa
 *
 * Created by Mat on 23/02/2016.
 */
public class SoccerMain {

    public static void main(String[] args) throws Exception {
        ejecucion1();
    }//main

    /**
     * Logica y gestion del programa
     */
    private static void ejecucion1(){
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
                Jugador jugador1 = new Jugador("12345678Q", "Primero", "Apellido1", 2000);
                Jugador jugador2 = new Jugador("87654321A", "Segundo", "Apellido2", 1950);
                Jugador jugador3 = new Jugador("3333333", "Tercero", "Apellido3", 1833);
                Jugador jugador4 = new Jugador("444444444", "Cuarto", "Apellido4", 1944);
                Jugador jugador5 = new Jugador("555555555", "Quinto", "Apellido5", 1955);

                Entrenador entrenadorBarza = new Entrenador("Luis Enrique", 20);
                Entrenador entrenadorMandril = new Entrenador("Zidane", 3);

                Equipo equipo1 = new Equipo("Barça", "Camp Nou", entrenadorBarza);
                Equipo equipo2 = new Equipo("Madrid", "Bernabeu", entrenadorMandril);

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
                System.out.println(" 1--> Jugadores de un equipo solicitado");
                System.out.println(" 2--> Jugadores de dos equipos solicitados utilizando una consulta SODA");
                System.out.println(" 3--> Los jugadores de un equipo que tenga una Fuerza menor o igual que 5");
                System.out.println(" 4--> Jugadores pertenecientes a una Liga");
                System.out.println(" 5--> Características de un jugador dado");
                System.out.println(" 6--> Jugadores que pertenece a un entrenador dado");
                System.out.println(" 7--> Equipos de una liga en concreta");
                System.out.println(" 8--> Crear jugadores para Barça");
                System.out.println(" 9--> Eliminar base de datos y salir");
                System.out.println("10--> Visualizar equipos");
                System.out.println("11--> About");
                System.out.println(" 0--> Salir del programa");
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

                    }case "8": {
                        crearJugadores(db, input);
                        break;
                    }

                    case "9": {
                        db.close();
                        on = eliminarDB(file);
                        break;
                    }

                    case "10": {
                        visualizarEquipos(db);
                        break;
                    }

                    case "11": {
                        about();
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
    }

    /**
     * Documento de informacion al profesor
     */
    private static void about() {
        System.out.println("INFO :: EjerciciosM03 ::Marc Cano:: db4o" +
                "\n-El programa carga, (solo una vez) unos datos por defecto para poder manipular " +
                "\nla aplicación desde el inicio de su ejecución." +
                "\n\n-El desarrollo del ejercicio trata de ajustarse lo maximo posible " +
                "\nal enunciado del mismo." +
                "\n\n-Existen metodos desarrollados, que no se usan. Eso es debido a que " +
                "\nen el resultado del ejercicio, no se pide su ejecucion y tampoco hay " +
                "\nnecesidad de implementar dichos metodoss para realizar las consultas requeridas." +
                "\n\n-El hecho de crear un jugador se ha desarrollado con la intencion " +
                "\nde demostrar que se sabe crear un jugador (opcion 8).");
    }

    /**
     * Consulta los jugadores que hay en un equipo solicitado
     * @param db database
     * @param input
     */
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


    /**
     * Consulta Jugadores de dos equipos solicitados utilizando una consulta SODA
     * @param db Database
     * @param input
     */
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


    /**
     * Consulta Los jugadores de un equipo que tenga una Fuerza menor o igual que 5
     * @param db Database
     * @param input
     */
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


    /**
     * Consulta Jugadores pertenecientes a una Liga dada
     * @param db Database
     * @param input
     */
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

    /**
     * Consulta las Características de un jugador dado
     * @param db Database
     * @param input
     */
    private static void caractJugador(ObjectContainer db, Scanner input) {
        System.out.println("Entra nombre del Jugador:");
        String nombre = input.nextLine();
        System.out.println("Entra apellido del Jugador:");
        String apellido = input.nextLine();

        ObjectSet<Liga> result = db.queryByExample(Liga.class);
        List<Equipo> equipos = result.get(0).getEquipos();

        int i = 0;
        int j = 0;
        boolean flag = false;
        while(i < equipos.size()) {
            while (j < equipos.get(i).getJugadores().size()){
                Jugador jugador = equipos.get(i).getJugadores().get(j);
                if(jugador.getNombre().equalsIgnoreCase(nombre) &&
                        jugador.getApellido().equalsIgnoreCase(apellido)){
                    flag = true;
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

        if(!flag ){
            try {
                throw new Exception("...no hay jugadores en la base de datos");
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("...entra opcion de menu");

            }
        }
    }//caractJugador


    /**
     * Consulta Jugadores que pertenecen a un entrenador dado
     * @param db Database
     * @param input
     */
    private static void jugadoresPertenecenEntrenador(ObjectContainer db, Scanner input) {
        System.out.println("Entra nombre del Entrenador:");
        String entrenador = input.nextLine();

        ObjectSet<Equipo> result = db.queryByExample(Equipo.class);

        int equipos = result.size();

        if(equipos == 0){
            try {
                throw new Exception("...no hay equipos en la base de datos");
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("...entra opcion de menu");
            }
        }

        boolean flag = false;
        for(int k = 0; k < equipos; k++){
            if(result.get(k).getEntrenador().getNombre().equalsIgnoreCase(entrenador)){
                System.out.println("Entrenador: " + result.get(k).getEntrenador().getNombre());
                System.out.println("Jugadores:\n");
                List<Jugador> jugadores = result.get(k).getJugadores();
                for(int i = 0; i < jugadores.size(); i++){
                    flag = true;
                    System.out.println(jugadores.get(i).getNombre() + " " +
                            jugadores.get(i).getApellido());
                }
                break;
            }
        }

        if(!flag){
            try {
                throw new Exception("...no hay jugadores en la base de datos");
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("...entra opcion de menu");
            }
        }
    }//jugadoresPertenecenEntrenador


    /**
     * Consulta Equipos de una liga en concreta
     * @param db Database
     * @param input
     */
    private static void equiposDeUnaLiga(ObjectContainer db, Scanner input) {
        System.out.println("Entra nombre de la Liga:");
        String liga = input.nextLine();

        ObjectSet<Liga> result = db.queryByExample(Liga.class);

        if(!result.hasNext()){
            try {
                throw new Exception("...no hay ligas en la base de datos");
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("...entra opcion de menu");
            }
        }

        boolean flag = false;
        for(int j = 0; j < result.size(); j++){
            if(liga.equalsIgnoreCase(result.get(j).getNombre())){
                flag = true;
                int cantidadEquipos = result.get(0).getEquipos().size();
                List<Equipo> equipos = result.get(0).getEquipos();
                System.out.println("Equipos:");

                for(int i = 0; i < cantidadEquipos; i++){
                    System.out.println("\t" + equipos.get(i).getNombre());
                }
            }
        }

        if(!flag){
            try {
                throw new Exception("...no hay jugadores en la base de datos");
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("...entra opcion de menu");
            }
        }
    }//equiposDeUnaLiga


    /**
     * Crea jugadores para Barça
     * @param db Database
     * @param input
     */
    private static void crearJugadores(ObjectContainer db, Scanner input) {
        try{
            //Jugador(String dni, String nombre, String apellido, double altura)
            Scanner input2 = new Scanner(System.in);
            Scanner input3 = new Scanner(System.in);
            System.out.println("Entra nombre de jugador:");
            String nomJug = input.nextLine();
            System.out.println("Entra apellido de jugador:");
            String apellidoJug = input.nextLine();
            System.out.println("Entra dni de jugador:");
            String dniJug = input2.nextLine();
            System.out.println("Entra altura de jugador:");
            String alturaJug = input.nextLine();

            if(alturaJug.contains("[A-Z]") || alturaJug.contains("[a-z]")){
                try {
                    throw new Exception("...altura debe ser un numero real");
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("...entra opcion de menu");
                }
            }

            double altJug = Double.parseDouble(alturaJug);

            Jugador jugadorDeUsuario = new Jugador(dniJug, nomJug, apellidoJug, altJug);
            Scanner inputInt = new Scanner(System.in);
            System.out.println("Entra caracteristicas de " +
                    nomJug + " " + apellidoJug + ":");
            System.out.println("-Agilidad:");
            int ag = inputInt.nextInt();
            System.out.println("-Fuerza:");
            int fz = inputInt.nextInt();
            System.out.println("-Velocidad:");
            int vel = inputInt.nextInt();
            System.out.println("-Pase:");
            int pas = inputInt.nextInt();
            System.out.println("-Penalti:");
            int pty = inputInt.nextInt();

            jugadorDeUsuario.getCaracteristicas().setAgilidad(ag);
            jugadorDeUsuario.getCaracteristicas().setFuerza(fz);
            jugadorDeUsuario.getCaracteristicas().setVelocidad(vel);
            jugadorDeUsuario.getCaracteristicas().setPase(pas);
            jugadorDeUsuario.getCaracteristicas().setPenalti(pty);

            db.store(jugadorDeUsuario);

            ObjectSet<Equipo> result = db.queryByExample(new Equipo("Barça", null, null));
            result.get(0).addJugador(jugadorDeUsuario);
            db.commit();
            System.out.println("...jugador introducido");


        }catch (Exception e){
            e.printStackTrace();
        }

    }

    /**
     * Elimina el archivo soccerData.db que contiene
     * todos los datos creados asta el momento
     * @param file archivo que contiene la base de datos
     * @return true = eliminado
     */
    private static boolean eliminarDB(File file) {
        boolean eraseDB = file.delete();
        if(eraseDB){
            System.out.println("...base de datos eliminada");
            return false;
        }
        else
            System.out.println("...no se ha eliminado la base de datos");
        return true;
    }

    /**
     * Visualiza todos los equipos de la base de datos
     * @param db Database
     */
    private static void visualizarEquipos(ObjectContainer db) {
        ObjectSet<Equipo> result = db.queryByExample(Equipo.class);
        System.out.println("\nEquipos:");
        int i = 0;
        while(result.hasNext()){
            System.out.println("\t- " + result.get(i).getNombre());
            i++;
        }

    }

}//SoccerMain class
