/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.appdynamics.licensecount.data;

import org.appdynamics.licensecount.data.*;
import org.appdynamics.appdrestapi.RESTAccess;
import org.appdynamics.appdrestapi.data.*;
import org.appdynamics.appdrestapi.util.*;
import org.appdynamics.appdrestapi.resources.s;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import java.util.logging.Logger;
import java.util.logging.Level;
import static org.appdynamics.licensecount.data.LicenseCount.licenseRound;

/**
 *
 * @author gilbert.solorzano
 */
public class CountAgents {
    private static Logger logger=Logger.getLogger(CountAgents.class.getName());

    public CountAgents(){}
    
    /*
        This is the customer level count :: 
        dotNetKeys needs to start here
        machineKeys needs to start here
        master DotNetMap, which is all of the other nodes
    */
    public void countCustomerLicenses(CustomerLicenseCount cc, 
            HashMap<String,HashMap<Integer,
                    ArrayList<Node>>> dotNetMap){
        
        if(s.debugLevel >= 2) 
            logger.log(Level.INFO,new StringBuilder().append("Initiating count of licenses.").toString());
        //This is going to count the licenses
        ArrayList<String> dotNetKeys=new ArrayList<String>();
        ArrayList<String> machineKeys=new ArrayList<String>();
        
        /*
        countTierLicenses(ApplicationLicenseCount ac, 
            ArrayList<TimeRange> timeRanges, 
            HashMap<String,HashMap<Integer,ArrayList<Node>>> dotNetMap, 
            ArrayList<String> dotNetKeys,
            ArrayList<String> machineKeys)
        */
        for(ApplicationLicenseCount aCount: cc.getApplications().values()){
            countAppLicenses(aCount,cc.getTimeRanges(),dotNetMap,dotNetKeys,machineKeys);
        }
        
      
        
        for(int i=0; i < cc.getTimeRanges().size(); i++){
            CustomerLicenseRange aRange = new CustomerLicenseRange();
            aRange.setStart(cc.getTimeRanges().get(i).getStart());
            aRange.setEnd(cc.getTimeRanges().get(i).getEnd());
            aRange.setName(aRange.createName());
            
            for(ApplicationLicenseCount tCount:cc.getApplications().values()){
                TierLicenseRange tRange= tCount.getAppLicenseRange().get(i);
                aRange.iisCount+=tRange.getIisCount();
                aRange.javaCount+=tRange.getJavaCount();
                aRange.nodeJSCount+=tRange.getNodeJSCount();
                aRange.machineCount+=tRange.getMachineCount();
                aRange.phpCount+=tRange.getPhpCount();
                aRange.totalCount+=tRange.getTotalCount();
                aRange.iisInternalCount+=tRange.getIisInternalCount();
                aRange.webserverCount+=tRange.getWebserverCount();
                aRange.nativeSDKCount+=tRange.getNativeSDKCount();
                
            }
            //logger.log(Level.INFO, "Value of iisCount " + aRange.iisCount);
            // We need to round the license up.
            aRange.iisCount=licenseRound(aRange.iisCount);
            aRange.totalCount=licenseRound(aRange.totalCount);
            if(aRange.nodeJSCount > 0)
                aRange.totalCount=licenseRound(aRange.iisCount)
                        +licenseRound(aRange.getNodeJSCount()/10)
                        +aRange.phpCount
                        +aRange.getJavaCount()
                        +aRange.getMachineCount()
                        +aRange.getWebserverCount();
            cc.getCustomerRangeValues().add(aRange);
        }
        
        for(CustomerLicenseRange tRange:cc.getCustomerRangeValues()){
            cc.getTotalRangeValue().iisCount+=tRange.iisCount;
            cc.getTotalRangeValue().javaCount+=tRange.javaCount;
            cc.getTotalRangeValue().phpCount+=tRange.phpCount;
            cc.getTotalRangeValue().nodeJSCount+=tRange.nodeJSCount;
            cc.getTotalRangeValue().machineCount+=tRange.machineCount;
            cc.getTotalRangeValue().totalCount+=tRange.totalCount;
            cc.getTotalRangeValue().iisInternalCount+=tRange.iisInternalCount;
            cc.getTotalRangeValue().webserverCount+=tRange.webserverCount;
            cc.getTotalRangeValue().nativeSDKCount+=tRange.nativeSDKCount;
        }
        
        
        
    }
    
    // Thsi is 
    private void countAppLicenses(ApplicationLicenseCount ac, 
            ArrayList<TimeRange> timeRanges, 
            HashMap<String,HashMap<Integer,ArrayList<Node>>> dotNetMap, 
            ArrayList<String> dotNetKeys,
            ArrayList<String> machineKeys){
        if(s.debugLevel >= 2) 
            logger.log(Level.INFO,new StringBuilder().append("Begin application level tier license count.").toString());

        /*
         * We have host with .Net agents that exist in multiple agents, we are going to get a map of all first.
         */
               
        /*
         * This is where we are going identify the countable agents
        public void countNodeLicenses(TierLicenseCount tc, 
            ArrayList<TimeRange> timeRanges,
            HashMap<String,HashMap<Integer,ArrayList<Node>>> dotNetMapTemp, 
            ArrayList<String> dotNetKeys, 
            ArrayList<String> machineKeys)
        
         */
        //logger.log(Level.INFO,"Starting the nodeLicense count.");
        for(TierLicenseCount tCount: ac.getTierLicenses().values()){
            countNodeLicenses(tCount,timeRanges,dotNetMap,dotNetKeys,machineKeys);
        }
        
        for(int i=0; i < timeRanges.size(); i++){
            ApplicationLicenseRange aRange = new ApplicationLicenseRange();
            aRange.setStart(timeRanges.get(i).getStart());
            aRange.setEnd(timeRanges.get(i).getEnd());
            aRange.setName(aRange.createName());
            
            for(TierLicenseCount tCount:ac.getTierLicenses().values()){
                TierLicenseRange tRange= tCount.getTierLicenseRange().get(i);
                aRange.iisCount+=tRange.getIisCount();
                aRange.javaCount+=tRange.getJavaCount();
                aRange.nodeJSCount+=tRange.getNodeJSCount();
                aRange.machineCount+=tRange.getMachineCount();
                aRange.phpCount+=tRange.getPhpCount();
                aRange.totalCount+=tRange.getTotalCount();
                aRange.iisInternalCount+=tRange.iisInternalCount;
                aRange.webserverCount+=tRange.webserverCount;
                aRange.nativeSDKCount+=tRange.nativeSDKCount;
                // This will insure that nodejs is properly counted.
                
            }
            if(aRange.getNodeJSCount() > 0)  
                    aRange.totalCount= (aRange.getTotalCount() - aRange.getNodeJSCount()) + licenseRound(aRange.getNodeJSCount()/10);
            ac.getAppLicenseRange().add(aRange);
        }
        
        // This is going to get the tier counts:
        ArrayList<TimeRange> hourlyTimeRanges=TimeRangeHelper.getHourlyTimeRanges(ac.getTotalRangeValue().getStart(), ac.getTotalRangeValue().getEnd());
        for(int i = 0; i < hourlyTimeRanges.size(); i++){
            AppHourLicenseRange app = new AppHourLicenseRange(hourlyTimeRanges.get(i));
            for(TierLicenseCount tCount:ac.getTierLicenses().values()){
                for(TierHourLicenseRange tRange: tCount.getTierHourLicenseRange()){
                    if(app.withIn(tRange)){
                        app.appAgent+=tRange.appAgent;
                        app.machineAgent+=tRange.machineAgent;
                    }
                }
            }
            ac.getAppHourLicenseRange().add(app);
        }
        
        
        for(ApplicationLicenseRange tRange:ac.getAppLicenseRange()){
            ac.getTotalRangeValue().iisCount+=tRange.iisCount;
            ac.getTotalRangeValue().javaCount+=tRange.javaCount;
            ac.getTotalRangeValue().phpCount+=tRange.phpCount;
            ac.getTotalRangeValue().nodeJSCount+=tRange.nodeJSCount;
            ac.getTotalRangeValue().machineCount+=tRange.machineCount;
            ac.getTotalRangeValue().totalCount+=tRange.totalCount;
            ac.getTotalRangeValue().iisInternalCount+=tRange.iisInternalCount;
            ac.getTotalRangeValue().webserverCount+=tRange.webserverCount;
            ac.getTotalRangeValue().nativeSDKCount+=tRange.nativeSDKCount;
        }
        
    }
    
    //0:Java, 1:IIS, 2:PHP, 3:NodeJS, 4 Machine Agent
    private void countNodeLicenses(TierLicenseCount tc, 
            ArrayList<TimeRange> timeRanges,
            HashMap<String,HashMap<Integer,ArrayList<Node>>> dotNetMapTemp, 
            ArrayList<String> dotNetKeys, 
            ArrayList<String> machineKeys){
        
   
        logger.log(Level.INFO,new StringBuilder().append("Starting tier level license count for tier ").append(tc.getName()).toString());
        
        for(NodeLicenseCount nodeL:tc.getNodeLicenseCount()){
            nodeL.countNodeLicenseRange(s.percentageThreshold);  
        } 
        
        for(int i=0; i < timeRanges.size(); i++){
            TierLicenseRange tRange = new TierLicenseRange();
            tRange.setStart(timeRanges.get(i).getStart());
            tRange.setEnd(timeRanges.get(i).getEnd());
            tRange.setName(tRange.createName());
           String tempKey=null;
            ArrayList<String> found=new ArrayList<String>();
            for(NodeLicenseCount node:tc.getNodeLicenseCount()){
                if(node.getRangeValues().size() > i && node.getRangeValues().get(i).isCountAsLicense()){
                    if(s.debugLevel >= 2)   logger.log(Level.INFO,new StringBuilder().append("\t\tCounting node type ").append(node.getType()).toString());
                    switch(node.getType()){
                        case 1:
                            //We don't do anything for now, this is will be added up later.
                            //logger.log(Level.INFO,"Adding DotNet " + node.getLicWeight());
                            
                            StringBuilder bud = new StringBuilder();
                            bud.append("\n\tAdding .Net node ").append(node.getNode().getName()).append("\n\tiisCount orig value ").append(tRange.getIisCount());
                            tempKey = getNodeKey(tRange,node.getMachineName());
                            
                            if(dotNetMapTemp.containsKey(node.getMachineName()) 
                                    && !found.contains(node.getMachineName())
                                    && ! dotNetKeys.contains(tempKey) ){
                                tRange.iisCount++;
                                tRange.totalCount++;
                                found.add(node.getMachineName());//dotNetMapTemp.remove(node.getMachineName());
                                dotNetKeys.add(tempKey);
                                bud.append("\n----------------------------------------\n start:").append(tRange.getStart()).append(" -- ").append(tRange.getEnd());
                                bud.append(" :: ").append(node.getNode().getTierName());
                            }
                            bud.append(" and iisInternalCount orig value ").append(tRange.iisInternalCount);
                            tRange.iisInternalCount++;
                            bud.append("\n\tiisCount new value ").append(tRange.iisCount).append(" and iisInternalCount new value ").append(tRange.iisInternalCount);

                            
                            break;
                        case 2:
                            //We don't do anything for now, this will be added up later
                            //logger.log(Level.INFO,"Adding PHP " + node.getLicWeight());
                            tRange.phpCount++;//=node.getLicWeight();
                            tRange.totalCount++;//=node.getLicWeight();
                            break;

                        case 3:
                            tRange.nodeJSCount++;
                            tRange.totalCount++;
                            break;
                        case 4:
                                // If it exists within the dotNet then it already exists
                                tempKey = getNodeKey(tRange,node.getMachineName());
                                if( !dotNetMapTemp.containsKey(node.getMachineName()) && !machineKeys.contains(tempKey)){
                                    machineKeys.add(tempKey);
                                    tRange.machineCount++;
                                    tRange.totalCount++;
                                }
                            break;
                        case 6:
                            tRange.webserverCount++;
                            tRange.totalCount++;
                            break;
                        case 7:
                            tRange.nativeSDKCount++;
                            tRange.totalCount++;
                            break;
                        default:
                            tRange.javaCount++;
                            tRange.totalCount++;
                            break;
                    }
                    
                }
            }
            tc.getTierLicenseRange().add(tRange);
        }
    }
    
    private String getNodeKey(TierLicenseRange tRange, String machineName){
        StringBuilder bud = new StringBuilder();
        bud.append(tRange.getStart()).append("-").append(tRange.getEnd()).append("-").append(machineName);        
        return bud.toString();
    }
}
