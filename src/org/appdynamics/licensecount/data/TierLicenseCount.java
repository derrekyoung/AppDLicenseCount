/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.appdynamics.licensecount.data;

import org.appdynamics.licensecount.actions.*;
import org.appdynamics.appdrestapi.RESTAccess;
import org.appdynamics.appdrestapi.data.*;
import org.appdynamics.appdrestapi.util.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import org.appdynamics.appdrestapi.resources.s;

import java.util.logging.Logger;
import java.util.logging.Level;

/**
 *
 * @author soloink
 * 
 * When using a range the tier is going to calculate the range values,
 * that means that every time we calculate the values, the tierLicenseCount
 * has to be reinitialized. 
 * 
 * 
 */
public class TierLicenseCount extends LicenseCount{
    private static Logger logger=Logger.getLogger(TierLicenseCount.class.getName());
    private String name;
    private String tierAgentType;
    private int tierId;
    private int nodeCount=0;

    private ArrayList<NodeLicenseCount> nodeLicenseCount=new ArrayList<NodeLicenseCount>();
    private ArrayList<TierLicenseRange> tierLicenseRange=new ArrayList<TierLicenseRange>();
    private ArrayList<TierHourLicenseRange> tierHourLicenseRange=new ArrayList<TierHourLicenseRange>();
    //private TierLicenseRange totalRangeValue;
    
    public TierLicenseCount(){super();}
    
    public TierLicenseCount(String name){super();this.name=name;}
    
    public TierLicenseCount(String name, String tierType, int id){
        super();
        this.name=name;
        this.tierAgentType=tierType;
        this.tierId=id;
    }
    
    public void addNode(Node node){
        nodeCount++;
        nodeLicenseCount.add(new NodeLicenseCount(node));
    }
    
    // We return the node so that we can work with it.
    public NodeLicenseCount addNodeRange(Node node){
        
        NodeLicenseCount nodeL=null;
        try{
            nodeL=new NodeLicenseCount(node);
            nodeCount++;
            nodeLicenseCount.add(nodeL);
        }catch(Exception e){
            StringBuilder bud =new StringBuilder();
            bud.append("Exception occurred while attempting to get node information for ").append(node.toString()).append("\n\t\tNode will not be counted.");
            logger.log(Level.SEVERE, bud.toString());
        }    
        return nodeL;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNodeCount() {
        return nodeCount;
    }

    public void setNodeCount(int nodeCount) {
        this.nodeCount = nodeCount;
    }

    public ArrayList<NodeLicenseCount> getNodeLicenseCount() {
        return nodeLicenseCount;
    }

    public void setNodeLicenseCount(ArrayList<NodeLicenseCount> nodeLicenseCount) {
        this.nodeLicenseCount = nodeLicenseCount;
    }

    public String getTierAgentType() {
        return tierAgentType;
    }

    public void setTierAgentType(String tierAgentType) {
        this.tierAgentType = tierAgentType;
    }

    public int getTierId() {
        return tierId;
    }

    public void setTierId(int tierId) {
        this.tierId = tierId;
    }
    
    
    
    public void updateLicenseWeight(double weight, Node node){
        for(NodeLicenseCount nodeLic: getNodeLicenseCount()){
            if(nodeLic.getNode().getId() == node.getId()) nodeLic.setLicWeight(weight);
        }
    }
    
    public void populateNodeLicenseRange(TimeRange totalTimeRange, ArrayList<TimeRange> timeRanges, 
            RESTAccess access, String applicationName){
        
        if(s.debugLevel >= 2) 
            logger.log(Level.INFO,new StringBuilder().append("Populating tier ").append(name).append(" license count for application ").append(applicationName).toString());
        
        //totalRangeValue=new TierLicenseRange("Tier Total Count");
        //totalRangeValue.setStart(totalTimeRange.getStart());totalRangeValue.setEnd(totalTimeRange.getEnd());
        
        /*
         * This is going to get the nodes to count all of the licenses.
         * 
         * This is a good point to thread out
         */
        ThreadExecutor execNodes = new ThreadExecutor(8);
        
        for(NodeLicenseCount nodeL:nodeLicenseCount){
            //public NodeExecutor(NodeLicenseCount nodeLic,RESTAccess access, 
            //String appName, TimeRange totalTimeRange, 
            //ArrayList<TimeRange> timeRanges, String tierAgentType)
            NodeExecutor nodeExec = new NodeExecutor(nodeL, access, applicationName, totalTimeRange, timeRanges, tierAgentType);
            execNodes.getExecutor().execute(nodeExec);
            //nodeL.populateNodeLicenseRange(totalTimeRange, timeRanges, access, applicationName, tierAgentType);
        }
        execNodes.getExecutor().shutdown();
        execNodes.shutdown();
        
        MetricDatas tierAppAgents=access.getRESTMetricQuery(0, applicationName, name, totalTimeRange.getStart(), totalTimeRange.getEnd());
        MetricDatas tierMachineAgents=access.getRESTMetricQuery(1, applicationName, name, totalTimeRange.getStart(), totalTimeRange.getEnd());
        
        //This call doesn't do anything --delete
        getMetricValues(tierAppAgents).getMetricValue();
        
        // This is serial
        ArrayList<TimeRange> hourlyTimeRanges=TimeRangeHelper.getHourlyTimeRanges(totalTimeRange.getStart(), totalTimeRange.getEnd());
        for(TimeRange hourRange: hourlyTimeRanges){
            TierHourLicenseRange tr=new TierHourLicenseRange(hourRange);
            tr.createName();
            
            for(MetricValue mv:getMetricValues(tierAppAgents).getMetricValue()){
                if(tr.withIn(mv.getStartTimeInMillis())) tr.getAppMetricValues().getMetricValue().add(mv);
            }
            
            for(MetricValue mv:getMetricValues(tierMachineAgents).getMetricValue()){
                if(tr.withIn(mv.getStartTimeInMillis())) tr.getMachineMetricValues().getMetricValue().add(mv);
            }
            tr.countAgents();
            tierHourLicenseRange.add(tr);
        }
        
        
    }
    
    public String getNodeKey(TierLicenseRange tRange, String machineName){
        StringBuilder bud = new StringBuilder();
        bud.append(tRange.getStart()).append("-").append(tRange.getEnd()).append("-").append(machineName);        
        return bud.toString();
    }
    
    
    public ArrayList<TierLicenseRange> getTierLicenseRange() {
        return tierLicenseRange;
    }

    public void setTierLicenseCount(ArrayList<TierLicenseRange> tierLicenseRange) {
        this.tierLicenseRange = tierLicenseRange;
    }


    public ArrayList<TierHourLicenseRange> getTierHourLicenseRange() {
        return tierHourLicenseRange;
    }

    public void setTierHourLicenseRange(ArrayList<TierHourLicenseRange> tierHourLicenseRange) {
        this.tierHourLicenseRange = tierHourLicenseRange;
    }
    

    
    @Override
    public String toString(){
        StringBuilder bud=new StringBuilder();
        bud.append("Tier Name: ").append(name).append("\n");
        bud.append("Tier Node Count: ").append(nodeCount).append("\n");
        //bud.append(totalRangeValue.toString());
        bud.append("---------------------------------------------------------------------\n");
        for(TierLicenseRange tRange: tierLicenseRange){
            bud.append(tRange.toString());
        }
        for(TierHourLicenseRange tRange: tierHourLicenseRange){
            bud.append(tRange.toString());
        }
        return bud.toString();
    }
    
    
    
}
