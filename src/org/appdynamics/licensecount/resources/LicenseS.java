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
    
    public static final int A_INDEX=8; // This is the agent index
    
    public static final int MIN_REST_MAJ_VER=2;
    public static final int MIN_REST_MIN_VER=5;
    
    
    
    /** Excel Strings **/
    public static final String LICENSE_SUMMARY="License Summary";
    public static final String TIER_SUMMARY="Tier Summary";
    public static final String HOURLY_TIER_SUMMARY="Hourly Tier Summary";
    public static final String HOURLY_NODE_SUMMARY="Hourly Node Summary";
    public static final String NODE_INFO_SUMMARY="Node Info Summary";
    public static final String TIERS_WITH_NO_NODES="Tiers With No Nodes";
    public static final String DOTNET_NODE_MAP="DotNet Node Map";
    public static final String WINDOWS_HOST="Windows Host";
    public static final String WINDOWS_MAPPING="Windows Mapping";
    public static final String BU_LICENSE_SUMMARY = "Business Unit License Summary";
    public static final String EUM_SUMMARY="EUM Summary";
    

    
    public static final String CUSTOMER_NAME="Cutomer Name";
    public static final String APPLICATION_NAME="Application Name";
    public static final String TIER_NAME="Tier Name";
    public static final String NODE_NAME="Node Name";
    public static final String AGENT_TYPE="Agent Type";
    public static final String TIER_TYPE="Tier Type";
    public static final String TIER_AGENT_TYPE="Tier Agent Type";
    public static final String TIER_ID="Tier ID";
    public static final String GROUP_NAME="Group Name";
    public static final String EUM_PAGE_NAME="EUM Page Name";
  
    
    public static final String TOTAL_AGENT_COUNT="Total Agent Count";
    public static final String JAVA_AGENT_COUNT="Java Agent Count";
    public static final String DOTNET_AGENT_COUNT="DotNet Agent Count";
    public static final String PHP_AGENT_COUNT="PHP Agent Count";
    public static final String NODEJS_AGENT_COUNT="NodeJS Agent Count";
    public static final String WEBSERVER_AGENT_COUNT="WebServer Agent Count";
    public static final String NATIVE_SDK_AGENT_COUNT="Native SDK Agent Count";
    public static final String MACHINE_AGENT_COUNT="Machine Agent Count";
    public static final String APPLICATION_AGENT_COUNT="Application Agent Count";
    public static final String NODE_AGENT_TYPE="NODEJS_APP_AGENT";
    
    public static final String AGENT_TYPE_CHK_PHP="PHP";
    public static final String AGENT_TYPE_CHK_IIS="IIS";
    public static final String AGENT_TYPE_CHK_NODEJS="nodejs";
    
    public static final String AGENT_NAME_JAVA="Java Agent";
    public static final String AGENT_NAME_PHP="PHP Agent";
    public static final String AGENT_NAME_NODEJS="NodeJS Agent";
    public static final String AGENT_NAME_DOTNET="DotNet Agent";
    public static final String AGENT_NAME_MACHINE_AGENT="Machine Agent";
    public static final String AGENT_NAME_DB_AGENT="DB Agent";
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
    public static final String GROUPFILE="groupFile";
    
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
