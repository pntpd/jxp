package com.web.jxp.dashboard;
import com.web.jxp.base.Base;
import static com.web.jxp.common.Common.print;
import javax.net.ssl.*; 
import java.io.*; 
import java.net.URL; 
import java.security.*; 
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate; 
import org.json.JSONObject;
 
public class Ticket extends Base
{ 
    public static void trustAllCerts() throws NoSuchAlgorithmException, KeyManagementException
    {
        TrustManager[] trustAllCerts = new TrustManager[] {new X509TrustManager()
        {
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
               return null;
            }
            public void checkClientTrusted(X509Certificate[] certs, String authType)
            {
            }
            public void checkServerTrusted(X509Certificate[] certs, String authType) {
            }
        }
        };

        // Install the all-trusting trust manager
        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, trustAllCerts, new java.security.SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        // Create all-trusting host name verifier
        HostnameVerifier allHostsValid = new HostnameVerifier()
        {
           public boolean verify(String hostname, SSLSession session)
           {
               return true;
           }
        };        
        HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
    }
    public String getUrl() throws KeyManagementException, NoSuchAlgorithmException 
    { 
        String iframeurlval = "";
        String username = "administrator";
        trustAllCerts();
        String xrfkey = "0123456789abcdef"; //Xrfkey to prevent cross-site issues 
        String host = "analytics.planetcp.com"; //Enter the Qlik Sense Server hostname here 
        String vproxy = "qlikticket"; //Enter the prefix for the virtual proxy configured in Qlik Sense Steps Step 1 
        try 
        {
            String certFolder = getMainPath("dashboard_ticket"); //This is a folder reference to the location of the jks files used for securing ReST communication 
            print(this,"-------------------------------------------------------------"+certFolder);
            String proxyCert = certFolder + "client_cert.jks"; //Reference to the client jks file which includes the client certificate with private key 
            String proxyCertPass="secret"; //This is the password to access the Java Key Store information 
            String rootCert = certFolder + "root.jks"; //Reference to the root certificate for the client cert. Required in this example because Qlik Sense certs are used. 
            String rootCertPass = "secret"; //This is the password to access the Java Key Store information
            /************** END Certificate Acquisition **************/

            /************** BEGIN Certificate configuration for use in connection **************/
            KeyStore ks = KeyStore.getInstance("JKS");
            ks.load(new FileInputStream(new File(proxyCert)), proxyCertPass.toCharArray()); 
            KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm()); 
            kmf.init(ks, proxyCertPass.toCharArray()); 
            SSLContext context = SSLContext.getInstance("SSL"); 
            KeyStore ksTrust = KeyStore.getInstance("JKS"); 
            ksTrust.load(new FileInputStream(rootCert), rootCertPass.toCharArray());
            
            print(this,ksTrust.aliases().nextElement()+"-------------------------------------------------------------");
            
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm()); 
            tmf.init(ksTrust); 
            context.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null); 
            SSLSocketFactory sslSocketFactory = context.getSocketFactory();
            /************** END Certificate configuration for use in connection **************/


            /************** BEGIN HTTPS Connection **************/
         
             print(this,"Browsing to: " + "https://" + host + ":4243/qps/" + vproxy + "/ticket?xrfkey=" + xrfkey);
            URL url = new URL("https://" + host + ":4243/qps/" + vproxy + "/ticket?xrfkey=" + xrfkey); 
            HttpsURLConnection connection = (HttpsURLConnection ) url.openConnection(); 
            connection.setSSLSocketFactory(sslSocketFactory); 
            connection.setRequestProperty("x-qlik-xrfkey", xrfkey); connection.setDoOutput(true); 
            connection.setDoInput(true); 
            connection.setRequestProperty("Content-Type","application/json"); 
            connection.setRequestProperty("Accept", "application/json"); 
            connection.setRequestMethod("POST");
            /************** BEGIN JSON Message to Qlik Sense Proxy API **************/


            String body = "{ 'UserId':'"+username+"','UserDirectory':'PNT-S001',";
            body+= "'Attributes': [],"; body+= "}"; 
            /************** END JSON Message to Qlik Sense Proxy API **************/


            OutputStreamWriter wr= new OutputStreamWriter(connection.getOutputStream()); 
            wr.write(body); 
            wr.flush(); //Get the response from the QPS 
            BufferedReader	in = new BufferedReader(new InputStreamReader(connection.getInputStream())); 
            StringBuilder builder = new StringBuilder(); 
            String inputLine; 
            while ((inputLine = in.readLine()) != null) 
            { 
                builder.append(inputLine); 
            } 
            in.close(); 
            String data = builder.toString(); 
            print(this,"data :: " +data);
            builder.setLength(0);
            JSONObject jobj = new JSONObject(data);
            if(jobj != null && jobj.has("Ticket"))
                iframeurlval = jobj.getString("Ticket");
        } 
        catch (KeyStoreException e) { e.printStackTrace(); } 
        catch (IOException e) { e.printStackTrace(); } 
        catch (CertificateException e) { e.printStackTrace(); } 
        catch (NoSuchAlgorithmException e) { e.printStackTrace(); } 
        catch (UnrecoverableKeyException e) { e.printStackTrace(); } 
        catch (KeyManagementException e) { e.printStackTrace(); } 
        return iframeurlval;
    } 
}