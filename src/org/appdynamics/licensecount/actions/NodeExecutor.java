/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.appdynamics.licensecount.actions;

import org.appdynamics.licensecount.data.NodeLicenseCount;
import org.appdynamics.licensecount.data.NodeLicenseRange;
import org.appdynamics.appdrestapi.RESTAccess;
import org.appdynamics.appdrestapi.resources.s;
import org.appdynamics.appdrestapi.data.*;
import org.appdynamics.appdrestapi.util.TimeRange;


import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author gilbert.solorzano
 * 
 * TimeRange totalTimeRange, ArrayList<TimeRange> timeRanges, RESTAccess access, 
            String applicationName, String tierAgentType
 * 
 */
public class NodeExecutor implements Runnable{
    
    private static Logger logger=Logger.getLogger(NodeExecutor.class.getName());
    private NodeLicenseCount nodeLic;
    private RESTAccess access;
    private TimeRange totalTimeRange;
    private ArrayList<TimeRange> timeRanges=new ArrayList<TimeRange>();
    private String appName;
    private String tierAgentType;
    
    /*
     * We need to insure that we have all of the information required.
     * 
     */
    public NodeExecutor(NodeLicenseCount nodeLic,RESTAccess access, 
            String appName, TimeRange totalTimeRange, 
            ArrayList<TimeRange> timeRanges, String tierAgentType){
        
        this.nodeLic=nodeLic;
        this.access=access;
        this.appName=appName;
        this.tierAgentType=tierAgentType;
        this.timeRanges=timeRanges;
        this.totalTimeRange=totalTimeRange;
    }
    
    @Override 
    public void run(){
        //logger.log(Level.INFO, "Starting executor!");
        MetricDatas mDatas= 
                access.getRESTMetricQuery(nodeLic.getQueryType(), appName, nodeLic.getNode().getTierName(), nodeLic.getNode().getName(), 
                totalTimeRange.getStart(), totalTimeRange.getEnd());
        //logger.log(Level.INFO, "Executed query!");
        //What happens when this is null ?
        //nodeLic.setTotalRangeValue(new NodeLicenseRange("Total Node Count"));
        nodeLic.getTotalRangeValue().setStart(totalTimeRange.getStart());
        nodeLic.getTotalRangeValue().setEnd(totalTimeRange.getEnd());
        nodeLic.getTotalRangeValue().setMetricValues(nodeLic.getMetricValues(mDatas));
        
        
        if(nodeLic.getType() == 5 && timeRanges.size() > 0){
 
                if( nodeLic.checkIfNotJava(access, appName, timeRanges.get(0).getStart(), timeRanges.get(0).getEnd()) ){ 
                    if(s.debugLevel >= 2) 
                            logger.log(Level.INFO,new StringBuilder().append("\n\tIt is not java").toString());
                    boolean fnd=false;
                    if(nodeLic.checkIfPHP(tierAgentType)){ nodeLic.setType(2);fnd=true;}
                    if(nodeLic.checkIfNodeJs(tierAgentType)){ nodeLic.setType(3);fnd=true;}
                    if(nodeLic.checkIfDotNet(tierAgentType)){nodeLic.setType(1);fnd=true;}
                    if(!fnd)nodeLic.setType(2);
                }else{
                    nodeLic.setType(0);
                }
        }
        
        for(TimeRange tRange:timeRanges){
            
            
            NodeLicenseRange nodeR = new NodeLicenseRange();
            nodeR.setStart(tRange.getStart());nodeR.setEnd(tRange.getEnd());
            nodeR.setName(nodeR.createName());
            for(MetricValue val: nodeLic.getTotalRangeValue().getMetricValues().getMetricValue()){
                if(nodeR.withIn(val.getStartTimeInMillis())) nodeR.getMetricValues().getMetricValue().add(val);
            }
            nodeLic.getRangeValues().add(nodeR);
        }
        
        
    }
    
    
}
