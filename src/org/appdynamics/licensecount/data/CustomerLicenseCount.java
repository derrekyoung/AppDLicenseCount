/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.appdynamics.licensecount.data;

import org.appdynamics.appdrestapi.RESTAccess;
import org.appdynamics.appdrestapi.resources.s;
import org.appdynamics.licensecount.actions.ThreadExecutor;
import org.appdynamics.licensecount.resources.LicenseS;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 *
 * @author soloink
 */
public class CustomerLicenseCount extends LicenseCount{
    private static Logger logger=Logger.getLogger(CustomerLicenseCount.class.getName());
    
    private int totalCount=0;
    private String name;
    private HashMap<Integer,ApplicationLicenseCount> applications=new HashMap<Integer,ApplicationLicenseCount>();
    private ArrayList<CustomerLicenseRange> customerRangeValues=new ArrayList<CustomerLicenseRange>();
    private HashMap<String, ArrayList<ApplicationLicenseCount>> groupings=new HashMap<String, ArrayList<ApplicationLicenseCount>>();
    private CustomerLicenseRange totalRangeValue;
    
    private ArrayList<org.appdynamics.appdrestapi.util.TimeRange> timeRanges=new ArrayList<org.appdynamics.appdrestapi.util.TimeRange>();
    private org.appdynamics.appdrestapi.util.TimeRange totalRange;
    
    public CustomerLicenseCount(){super();}
    
    public CustomerLicenseCount(String name){super();this.name=name;}
    
    
    public void addApplication(ApplicationLicenseCount app){
        if(s.debugLevel >= 2) 
            logger.log(Level.INFO,new StringBuilder().append("Adding application ").append(app.getApplicationName()).toString());
        applications.put(app.getApplicationId(),app);
        //if(s.debugLevel > 0) System.out.println("\tAdding " + app.getApplicationName() + " id " +app.getApplicationId()+" size " + applications.size());
    }
    
    /*
     *  This function will start the population of the nodes and tiers across the app.
     * 
     */
    public void populateApplications(RESTAccess access,int interval){
        
        if(s.debugLevel >= 2) 
            logger.log(Level.INFO,new StringBuilder().append("Creating time range for interval ").append(interval).toString());
        // Should we make the total timer range an avg of usage?
        timeRanges=getTimeRanges(interval);
        totalRange=getTimeRange(interval);
        
        
        totalRangeValue = new CustomerLicenseRange("Custumer Total");
        totalRangeValue.setStart(totalRange.getStart());
        totalRangeValue.setEnd(totalRange.getEnd());
        
        
        for(ApplicationLicenseCount app: applications.values()){
            logger.log(Level.INFO,new StringBuilder().append("\tPopulating application ").append(app.getApplicationName()).toString());
            app.populateLicense(access.getNodesForApplication(app.getApplicationId()), access, timeRanges,totalRange);
        }
        
 
        /*
        
        */
    }
    

    
    public void countTierLicenses(){
        if(s.debugLevel >= 2) 
            logger.log(Level.INFO,new StringBuilder().append("Initiating count of licenses.").toString());
        //This is going to count the licenses
        for(ApplicationLicenseCount tCount: applications.values()){
            tCount.countTierLicenses(timeRanges);
        }
        
        for(int i=0; i < timeRanges.size(); i++){
            CustomerLicenseRange aRange = new CustomerLicenseRange();
            aRange.setStart(timeRanges.get(i).getStart());
            aRange.setEnd(timeRanges.get(i).getEnd());
            aRange.setName(aRange.createName());
            
            for(ApplicationLicenseCount tCount:applications.values()){
                TierLicenseRange tRange= tCount.getAppLicenseRange().get(i);
                aRange.iisCount+=tRange.getIisCount();
                aRange.javaCount+=tRange.getJavaCount();
                aRange.nodeJSCount+=tRange.getNodeJSCount();
                aRange.machineCount+=tRange.getMachineCount();
                aRange.phpCount+=tRange.getPhpCount();
                aRange.totalCount+=tRange.getTotalCount();
                aRange.iisInternalCount+=tRange.getIisInternalCount();
                
            }
            //logger.log(Level.INFO, "Value of iisCount " + aRange.iisCount);
            // We need to round the license up.
            aRange.iisCount=licenseRound(aRange.iisCount);
            aRange.totalCount=licenseRound(aRange.totalCount);
            customerRangeValues.add(aRange);
        }
        
        for(CustomerLicenseRange tRange:customerRangeValues){
            totalRangeValue.iisCount+=tRange.iisCount;
            totalRangeValue.javaCount+=tRange.javaCount;
            totalRangeValue.phpCount+=tRange.phpCount;
            totalRangeValue.nodeJSCount+=tRange.nodeJSCount;
            totalRangeValue.machineCount+=tRange.machineCount;
            totalRangeValue.totalCount+=tRange.totalCount;
            totalRangeValue.iisInternalCount+=tRange.iisInternalCount;
        }
        
        
        
    }

    public static Logger getLogger() {
        return logger;
    }

    public static void setLogger(Logger logger) {
        CustomerLicenseCount.logger = logger;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HashMap<Integer, ApplicationLicenseCount> getApplications() {
        return applications;
    }

    public void setApplications(HashMap<Integer, ApplicationLicenseCount> applications) {
        this.applications = applications;
    }

    public ArrayList<CustomerLicenseRange> getCustomerRangeValues() {
        return customerRangeValues;
    }

    public void setCustomerRangeValues(ArrayList<CustomerLicenseRange> customerRangeValues) {
        this.customerRangeValues = customerRangeValues;
    }

    public CustomerLicenseRange getTotalRangeValue() {
        return totalRangeValue;
    }

    public void setTotalRangeValue(CustomerLicenseRange totalRangeValue) {
        this.totalRangeValue = totalRangeValue;
    }

    public ArrayList<org.appdynamics.appdrestapi.util.TimeRange> getTimeRanges() {
        return timeRanges;
    }

    public void setTimeRanges(ArrayList<org.appdynamics.appdrestapi.util.TimeRange> timeRanges) {
        this.timeRanges = timeRanges;
    }

    public org.appdynamics.appdrestapi.util.TimeRange getTotalRange() {
        return totalRange;
    }

    public void setTotalRange(org.appdynamics.appdrestapi.util.TimeRange totalRange) {
        this.totalRange = totalRange;
    }

    public void aggregateByGroup(HashMap<Integer,ApplicationLicenseCount> applications) {
        String line = null;
        try {
            FileReader fileReader = new FileReader(LicenseS.GROUP_V);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            logger.log(Level.INFO, "Found " + LicenseS.GROUP_V);

            
            while((line = bufferedReader.readLine()) != null) {
                
                String[] tokens = line.split(":");
                String group = tokens[0];
                String[] appsString = tokens[1].split(",");
                //add logic for dealing with apps with *
                ArrayList<ApplicationLicenseCount> apps = new ArrayList<ApplicationLicenseCount>(); 
                int segmentSize = 0;
                String[] appSegment = null;
                
                for (String gApp:appsString) {
                    logger.log(Level.INFO, "This is the app I'm looking for " + gApp);
                    if (gApp.contains("*")) {
                            appSegment = gApp.split("\\*", -1);
                            segmentSize = appSegment.length;
                    }
                    // need to get app id
                    for(Entry<Integer, ApplicationLicenseCount> entry : applications.entrySet() ) {
                        if (gApp.contains("*")) {
                            if ( entry.getValue().getApplicationName().startsWith(appSegment[0]) ) {
                                for ( int i=1; i<=segmentSize-1; i++ ) {
                                    if ( i == segmentSize-1 ) { //reached end of the search string
                                        if ( entry.getValue().getApplicationName().contains(appSegment[i])) {
                                            apps.add(entry.getValue());
                                        }
                                        break;
                                    } if ( !entry.getValue().getApplicationName().contains(appSegment[i])) { //DOES NOT contain value
                                        break;
                                    }
                                }
                            }
                        } else if ( gApp.equals(entry.getValue().getApplicationName()) ) {
                            apps.add(entry.getValue());
                            break;
                        } 
                    } 
                }
                groupings.put(group, apps);
            }
            bufferedReader.close();
        } catch (IOException ex) {
            logger.log(Level.WARNING, "Cannot read " + LicenseS.GROUP_V + " now exiting.");
            System.exit(0);
        }
    }   
    
    public HashMap<String, ArrayList<ApplicationLicenseCount>> getGroupings() {
        return groupings;
    }    
    
    @Override
    public String toString(){
        StringBuilder bud = new StringBuilder();
        bud.append("\nCustomer Name: ").append(name).append("\n");
        bud.append("Total Application Count: ").append(applications.size()).append("\n");
        //bud.append(totalRangeValue.toString());
        bud.append("----------------------- Customer Time Range ------------------------------\n");
        for(CustomerLicenseRange cRange: customerRangeValues){
            bud.append(cRange.toString());
        }
        
        for(ApplicationLicenseCount app : applications.values()){
            bud.append("------------------- Applications -------------------------------------\n");
            bud.append(app.toString());
        }

        
        return bud.toString();
    }
            
    
    
}
