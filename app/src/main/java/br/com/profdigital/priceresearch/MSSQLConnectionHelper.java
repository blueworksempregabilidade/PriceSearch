package br.com.profdigital.priceresearch;

// 0
//            VEJA A PASTA ____SQLExpress____

// credits for Prof. Adriano Domingues - https://www.youtube.com/watch?v=nMvFn60QyKs
// credits for Prof. Leomar Duarte - https://www.youtube.com/watch?v=mr8cbMbhW04&t=7s
// credits for Prof. Leandro Alberti

// WINDOWS FIREWALL https://learn.microsoft.com/en-us/sql/sql-server/install/configure-the-windows-firewall-to-allow-sql-server-access?view=sql-server-ver16
// https://support.norton.com/sp/pt/br/home/current/solutions/v1028220

import android.content.Context;
import android.os.StrictMode;
import android.util.Log;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MSSQLConnectionHelper {

   private static final String TAG = "MSSQLConnectionHelper";

    // SOMEE.com com MSSQLSERVER hospedado em servidor na web - Run OK Emulador - Run OK Device Real
     private static String mStringServerIpName = "dbLoginRegister.mssql.somee.com"; //your_server_name
     private static String mStringUserName = "marcosprofdigita_SQLLogin_1"; //your_username "marcosprofdigita_SQLLogin_1"
    private static String mStringPassword = "mwjac7862d"; //your_password "mwjac7862d"
    private static String mStringDatabase = "dbLoginRegister"; //your_database_name


    // MSSQLSERVER local - Run OK Emulador - Not RUN Device Real
//    private static String mStringServerIpName = "10.0.2.2"; // localhost 169.254.162.65  192.168.56.1    169.254.149.162 ou 192.168.0.16     127.0.0.1-192.168.0.15 your_server_ip - pesquise em sua casa 192.168.0.15 ??
//    private static String mStringUserName = "userApp"; //your_user_name_connect_database
//    private static String mStringPassword = "1234"; //your_password
//    private static String mStringDatabase = "dbLoginRegister"; //your_database_name
//    private static String mStringPort = "1433"; //your_server_port
//    private static String mStringInstance = "SQLEXPRESS";


    private static String mStringConnectionUrl; // objeto da String de Conexao ao Banco

    // método de conexão com o banco de dados
    public  static   Connection getConnection(Context mContext) {
        Connection mConnection = null;

        // atenção para importar jtds-1.3.1.jar  no  site oficial (http://jtds.sourceforge.net/)
        // adcione a dependencia no build.gradle (module)

        try {
            // adicionar política para criação de uma tarefa (thread)
            StrictMode.ThreadPolicy mPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(mPolicy);

            // verificar de driver de conexão foi importado/colado para projeto do app na pasta lib
            Class.forName("net.sourceforge.jtds.jdbc.Driver");

            // abordagem para SOMEE
             mStringConnectionUrl = "jdbc:jtds:sqlserver://" + mStringServerIpName + ";databaseName=" + mStringDatabase + ";user=" + mStringUserName + ";password=" + mStringPassword + ";";

            // abordagem para MSSQLServer Local
            // mStringConnectionUrl = "jdbc:jtds:sqlserver://" + mStringServerIpName + ":" + mStringPort + "/" + mStringDatabase + ";instance=" + mStringInstance + ";user=" + mStringUserName + ";password=" + mStringPassword + ";"; // integratedSecurity=true;"; //;encrypt=true;trustServerCertificate=false;loginTimeout=30;
            // teste mStringConnectionUrl = "jdbc:jtds:sqlserver://10.0.2.2:1433/dbLoginRegister;instance=SQLEXPRESS;user=userApp;password=1234;";

            // abordagem para MYSQL
            // Class.forName("com.mysql.jdbc.Driver");
            //mStringConnectionUrl = "jdbc:mysql://" + mStringServerIpName + ":" + mStringPort + "/" + mStringDatabase +"," + mStringUserName + "," + mStringPassword + ";";

            mConnection = DriverManager.getConnection(mStringConnectionUrl);

            Log.i(TAG, "Connection successful");  // realizada a conexão com sucesso

        } catch (ClassNotFoundException mException) {
            String mMessage = "ClassNotFoundException: " + mException.getMessage();
            Toast.makeText(mContext, mMessage , Toast.LENGTH_LONG).show();
            Log.e(TAG, mMessage);
            mException.printStackTrace();
        } catch (SQLException mException) {
            String mMessage = "SQLException: " + mException.getMessage();
            Toast.makeText(mContext, mMessage, Toast.LENGTH_LONG).show();
            Log.e(TAG, mMessage);
            mException.printStackTrace();
        } catch (Exception mException) {
            String mMessage = "Exception: " + mException.getMessage();
            Toast.makeText(mContext, mMessage , Toast.LENGTH_LONG).show();
            Log.e(TAG, mMessage);
            mException.printStackTrace();
       }

        return mConnection;
    }
}
