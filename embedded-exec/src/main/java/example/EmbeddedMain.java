
package example;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.embeddable.CommandResult;
import org.glassfish.embeddable.CommandRunner;
import org.glassfish.embeddable.Deployer;
import org.glassfish.embeddable.GlassFish;
import org.glassfish.embeddable.GlassFishException;
import org.glassfish.embeddable.GlassFishProperties;
import org.glassfish.embeddable.GlassFishRuntime;

/**
 * EmbeddedGlassfishの起動用クラス
 */
public class EmbeddedMain {
    private static Connection con;
    private static GlassFish glassFish;

    public static void main(String[] args) throws GlassFishException, Exception {
        GlassFishProperties glassFishProperties = new GlassFishProperties();
        glassFishProperties.setPort("http-listener", 8080);

        glassFish = GlassFishRuntime.bootstrap().newGlassFish(glassFishProperties);

        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    glassFish.stop();
                    glassFish.dispose();
                } catch (GlassFishException ex) {
                    Logger.getLogger(EmbeddedMain.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }));
        createSampleData();

        glassFish.start();
        System.out.println("glassfish started");

        CommandRunner runner = glassFish.getCommandRunner();
        //内蔵glassfishに接続プールの作成
        CommandResult result = runner.run("create-jdbc-connection-pool", 
                "--datasourceclassname", "org.h2.jdbcx.JdbcDataSource", 
                "--restype", "javax.sql.DataSource", 
                "--property", "url=jdbc\\:h2\\:mem\\:testdb",
                "TestPool");
        System.out.println(result.getOutput());

        //内蔵glassfishにJDBCリソースの作成
        result = runner.run("create-jdbc-resource", "--connectionpoolid=TestPool", "jdbc/TestPool");
        System.out.println(result.getOutput());

        //デプロイ
        Deployer deployer = glassFish.getDeployer();
        System.out.println(deployer.deploy(new File(args[0]), "--name=embedded", "--contextroot=", "--force=true"));
    }
    public static void shutdown(String[] args){
        Runtime.getRuntime().halt(0);
    }

    /**
     * テスト用初期データの生成
     * @throws Exception 
     */
    private static void createSampleData() throws Exception{
        Class.forName("org.h2.Driver").newInstance();
        con = DriverManager.getConnection("jdbc:h2:mem:testdb");
        con.createStatement().execute("CREATE TABLE CUSTOMER(CUSTOMER_ID bigint primary key  auto_increment, CUSTOMER_NAME varchar(100))");
        con.createStatement().execute("INSERT INTO CUSTOMER VALUES(101, 'HELLO')");
        con.createStatement().execute("INSERT INTO CUSTOMER VALUES(102, 'WORLD')");
        con.createStatement().execute("INSERT INTO CUSTOMER VALUES(103, 'FOO')");
        con.createStatement().execute("INSERT INTO CUSTOMER VALUES(104, 'BAR')");

        
    }
}
