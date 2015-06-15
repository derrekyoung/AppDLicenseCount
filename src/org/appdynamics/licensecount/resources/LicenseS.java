/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.appdynamics.licensecount.resources;

import org.appdynamics.licensecount.resources.OPTS;

import java.util.ArrayList;

/**
 *
 * @author gilbert.solorzano
 * Last Update : 20140202 Dot Net license count
 * 
 */
public class LicenseS extends OPTS{
    
    
    public static final String VERSION_L="version";
    public static final String VERSION_S="v";
    public static final boolean VERSION_R=false;
    public static final boolean VERSION_A=false;
    public static final String VERSION_D="Prints out the version of the tool";
    public static final String VERSION_V="1.0.4";
    
    public static final String FILENAME_L="file";
    public static final String FILENAME_S="f";
    public static final String FILENAME_D="Optional : This is going to be the file name that is going to be created. Default is <AccountName>_LicenseCount.xlsx.";
    public static final boolean FILENAME_R=false;
    public static final boolean FILENAME_A=true;
    
    public static final String INTERVAL_L="interval";
    public static final String INTERVAL_S="i";
    public static final String INTERVAL_D="Optional : This is going to be the number of days we go back and run. Default is going back 7 days from midnight to midnight.  ";
    public static final boolean INTERVAL_R=false;
    public static final boolean INTERVAL_A=true;
    
    public static final String CFG_FILE_L="config";
    public static final String CFG_FILE_S="C";
    public static final String CFG_FILE_D="";
    public static final boolean CFG_FILE_R=false;
    public static final boolean CFG_FILE_A=true;
    
    // Uptime : default 25%
    public static final String UPTIME_L="uptime";
    public static final String UPTIME_S="U";
    public static final String UPTIME_D="Optional : The amount of uptime necessary for an agent to be up so that it is counted. Default value is .70 (70%)";
    public static final boolean UPTIME_R=false;
    public static final boolean UPTIME_A=true;
    
    //This will be added to limit the number of apps reported.
    public static final String APPS_L="apps";
    public static final String APPS_S="A";
    public static final String APPS_D="Optional : The applications that you want to report on, comma delimited list.";
    public static final boolean APPS_R=false;
    public static final boolean APPS_A=true;
    public static ArrayList<String> APPS_V=new ArrayList<String>();
    //
    public static final String NOW_L="now";
    public static final String NOW_S="n";
    public static final String NOW_D="Include current agent count";
    public static final boolean NOW_R=false;
    public static final boolean NOW_A=false;
    public static boolean NOW_V=false;
    
    //Log File
    
    public static final String LOGFILE_L="logfile";
    public static final String LOGFILE_S="L";
    public static final String LOGFILE_D="Path to the log file";
    public static final boolean LOGFILE_R=false;
    public static final boolean LOGFILE_A=true;
    public static String LOGFILE_V="";
    
    
    public static final String USAGE="java -jar AppDynamicsLC.jar";
    public static final String FILE_ENDING="_LicenseCount.xlsx";
    
    
    
    public static String FILENAME_V="Customer1";
    public static int INTERVAL_V=7;
    
    
    /** Excel Strings **/
    public static final String LICENSE_SUMMARY="License Summary";
    public static final String TIER_SUMMARY="Tier Summary";
    public static final String HOURLY_TIER_SUMMARY="Hourly Tier Summary";
    public static final String NODE_INFO_SUMMARY="Node Info Summary";
    public static final String TIERS_WITH_NO_NODES="Tiers With No Nodes";
    public static final String DOTNET_NODE_MAP="DotNet Node Map";
    public static final String WINDOWS_HOST="Windows Host";
    public static final String WINDOWS_MAPPING="Windows Mapping";
    
    public static final String CUSTOMER_NAME="Cutomer Name";
    public static final String APPLICATION_NAME="Application Name";
    public static final String TIER_NAME="Tier Name";
    public static final String NODE_NAME="Node Name";
    public static final String AGENT_TYPE="Agent Type";
    public static final String TIER_TYPE="Tier Type";
    public static final String TIER_AGENT_TYPE="Tier Agent Type";
    public static final String TIER_ID="Tier ID";
  
    
    public static final String TOTAL_AGENT_COUNT="Total Agent Count";
    public static final String JAVA_AGENT_COUNT="Java Agent Count";
    public static final String DOTNET_AGENT_COUNT="DotNet Agent Count";
    public static final String PHP_AGENT_COUNT="PHP Agent Count";
    public static final String NODEJS_AGENT_COUNT="NodeJS Agent Count";
    public static final String MACHINE_AGENT_COUNT="Machine Agent Count";
    public static final String APPLICATION_AGENT_COUNT="Application Agent Count";
    
    public static final String AGENT_TYPE_CHK_PHP="PHP";
    public static final String AGENT_TYPE_CHK_IIS="IIS";
    public static final String AGENT_TYPE_CHK_NODEJS="nodejs";
    
    public static final String AGENT_NAME_JAVA="Java Agent";
    public static final String AGENT_NAME_PHP="PHP Agent";
    public static final String AGENT_NAME_NODEJS="NodeJS Agent";
    public static final String AGENT_NAME_DOTNET="DotNet Agent";
    public static final String AGENT_NAME_MACHINE_AGENT="Machine Agent";
    public static final String NONE="None";
    public static final String PRESENT="Present";
    public static final String DESCRIPTION="Description";
    
    public static final String AGENT_TYPE_OTHER="Other";
    public static final String AGENT_NAME_OTHER_AGENT="Other";
    
    public static final String EUM_PAGES_PATH="End User Experience|Base Pages";
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
    public static final String LICENSE_COUNTS="license-counts";
    public static final String LICENSE_COUNT="license-count";
    public static final String SERVER_CONFIG="server-config";
    public static final String CONTROLLER="controller";
    public static final String ACCOUNT="account";
    public static final String USER="user";
    public static final String PASSWORD="password";
    public static final String OPTIONAL_OPTIONS="optional-options";
    public static final String APPLICATIONS="applications";
    public static final String INTERVAL="interval";
    public static final String UPTONOW="upToNow";
    public static final String FILENAME="fileName";
    public static final String UPTIME="uptime";
    public static final String LOGFILE="logFile";
    public static final String DEBUG_LEVEL="debugLevel";
    
    //Base Pages query
    public static final String BASE_PAGES="End User Experience|Base Pages";
    
    public static final String L1="\n    ";
    public static final String L2="\n\t";
    public static final String L3="\n\t    ";
    public static final String L4="\n\t\t";
    public static final String L5="\n\t\t    ";
    public static final String L6="\n\t\t\t";
    public static final String VE=" = ";
    
    public static int licenseRound(double licenseC){
        String numString = new Double(licenseC).toString();
        if(numString.contains(".")){
            String decimalValue = numString.split("\\.")[1];
            String integerValue = numString.split("\\.")[0];
            try{
                Double decimalDouble = new Double(decimalValue);
                Integer value = new Integer(integerValue);
                if(decimalDouble > 0) return (value + 1);
            }catch(Exception e){
                
            }
        }
        
        return new Double(licenseC).intValue();
    }
    
}
