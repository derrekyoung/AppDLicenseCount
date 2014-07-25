/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.appdynamics.licensecount.conf;
import org.appdynamics.licensecount.resources.LicenseS;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.util.Set;
import java.util.HashSet;

/**
 *
 * @author gilbert.solorzano
 */
public class ReadConfig {
    
    public ReadConfig(){}
    public ReadConfig(String filePath){init(filePath);}
    
    private void init(String filePath){
        BufferedInputStream configFile = null;
        //metricLog.info("Starting the logging on metric ");
        try{
            
            configFile = new BufferedInputStream(new FileInputStream(filePath));
            
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(configFile);
           
            //Element serverConfigXML = (Element)doc.getElementsByTagName(ExportS.SERVER_CONFIG).item(0);
            Element licenseCount = (Element)doc.getElementsByTagName(LicenseS.LICENSE_COUNT).item(0);
            
            setServerInfo((Element)licenseCount.getElementsByTagName(LicenseS.SERVER_CONFIG).item(0));
            
            setLicenseCount((Element)licenseCount.getElementsByTagName(LicenseS.OPTIONAL_OPTIONS).item(0));

            //appDMetricExConf.setMetricCollections(setMetricExportInfo((Element)doc.getElementsByTagName(ExportS.METRIC_COLLECTIONS).item(0)));
            
            //appDMetricExConf.setMetricOutput(setMetricOutput((Element)doc.getElementsByTagName(ExportS.METRIC_OUTPUT).item(0)));

        }catch(Exception e){
            System.out.println(e.getMessage());
        }finally{
            try{ configFile.close();}catch(Exception e){}
        }
        
        
    }
    
  /*
     * <!--> XML Strings <-->
     * <license-counts>
   <license-count>
    <server-config> 
        <controller Port="443" useSSL="true">familysearch.saas.appdynamics.com</controller>
        <account>familysearch</account>
        <user>fsrestclient</user>
        <password>BFm81Etzrdvd7rcwDhWfMIyG9TbwKJR+</password>
    </server-config>
    <optional-options>
        <applications/> 
        <interval upToNow="false"/>
        <fileName/>
        <upTime/>
        <logFile debugLevel="0"/>
    </optional-options>
    </license-count>
</license-counts>
     * 
     */
    
    private ServerConfig setServerInfo(Element serverXML) throws Exception{
        ServerConfig serverConfig = new ServerConfig();
        // serverConfigXML.getElementsByTagName(ExportS.INTERVAL).item(0).getTextContent();
        Account account = new Account();
        account.setAccount(serverXML.getElementsByTagName(LicenseS.ACCOUNT).item(0).getTextContent());
        LicenseS.ACCOUNT_V=serverXML.getElementsByTagName(LicenseS.ACCOUNT).item(0).getTextContent();
        
        account.setUser(serverXML.getElementsByTagName(LicenseS.USER).item(0).getTextContent());
        LicenseS.USERNAME_V=serverXML.getElementsByTagName(LicenseS.USER).item(0).getTextContent();
        
        account.setPasswd(serverXML.getElementsByTagName(LicenseS.PASSWORD).item(0).getTextContent());
        serverConfig.setAccount(account);
        
        Controller controller=new Controller();
        controller.setFqdn(serverXML.getElementsByTagName(LicenseS.CONTROLLER).item(0).getTextContent());
        controller.setPort(serverXML.getElementsByTagName(LicenseS.CONTROLLER).item(0).getAttributes().getNamedItem(LicenseS.PORT_L).getTextContent());
        controller.setUseSSL(getBoolean(serverXML.getElementsByTagName(LicenseS.CONTROLLER).item(0).getAttributes().getNamedItem(LicenseS.SSL_L).getTextContent()));
        serverConfig.setController(controller);

        
        return serverConfig;
    }
    
    private void setLicenseCount(Element licCount) throws Exception{
        
    }
    
    
    public boolean getBoolean(String value){
        return Boolean.valueOf(value).booleanValue();
    }
    
    public int stringToInt(String value, int defaultVal){
        
        try{
            return new Integer(value).intValue();
        }catch(Exception e){}
        
        return defaultVal;
    }
    
}
