import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.config.EmbeddedConfiguration;

/**Clase con la que nos conectaremos a la BDOO
 * Solo se creara una instancia de la base de datos
 * para gestionarla mejor
 * Created by Mat on 24/02/2016.
 */
public class DataConnection {
    private static DataConnection INSTANCE = null;
    private final String PATH = "soccerData.db";

    private static ObjectContainer db;

    private synchronized static void createInstance(){
        if(INSTANCE == null){
            INSTANCE = new DataConnection();
            INSTANCE.performConnection();
        }
    }

    private void performConnection() {
        EmbeddedConfiguration config = Db4oEmbedded.newConfiguration();
        db = Db4oEmbedded.openFile(config, PATH);
    }

    public static ObjectContainer getInstance(){
        if(INSTANCE == null)
            createInstance();
        return db;
    }

    public void closeConnection(){
        db.close();
    }

}
