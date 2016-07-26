/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.appdynamics.licensecount.data;


import org.appdynamics.appdrestapi.data.MetricValue;
import org.appdynamics.appdrestapi.data.MetricValues;
import org.appdynamics.appdrestapi.util.TimeRange;

/**
 *
 * @author soloink
 * 
 *  20160627 Adding the machine agent count to the timerange 
 */
public class NodeLicenseRange extends TimeRange{
    protected int count=0;
    protected int macCount=0;
    protected double value;
    protected MetricValues metricValues=new MetricValues();
    protected MetricValues machineMetricValues = new MetricValues();
    protected boolean countAsLicense=false;
    
    public NodeLicenseRange(){super();}

    public NodeLicenseRange(String name){super(name);}

    public MetricValues getMachineMetricValues() {
        return machineMetricValues;
    }

    public void setMachineMetricValues(MetricValues machineMetricValues) {
        if(machineMetricValues != null) this.machineMetricValues = machineMetricValues;
    }
    
    
    
    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public boolean isCountAsLicense() {
        return countAsLicense;
    }

    public void setCountAsLicense(boolean countAsLicense) {
        this.countAsLicense = countAsLicense;
    }

    public double getUptimePercentageForNode(){
        value=0;
        int rCount=0;
        for(MetricValue mValue: metricValues.getMetricValue()){
            value+=mValue.getValue();
            rCount++;
        }

        if(rCount > 0) value=value/rCount;        
        return value;
    }

    public MetricValues getMetricValues() {
        return metricValues;
    }

    public void setMetricValues(MetricValues metricValues) {
        if(metricValues != null)
             this.metricValues = metricValues;
    }

    public int getMacCount() {
        return macCount;
    }

    public void setMacCount(int macCount) {
        this.macCount = macCount;
    }
    
    

    @Override
    public String toString(){
        StringBuilder bud=new StringBuilder();
        bud.append("Time range\n");
        bud.append("\t\tStart time ").append(getDate(start)).append(" :: ").append(start).append("\n");
        bud.append("\t\tEnd time ").append(getDate(end)).append(" :: ").append(end).append("\n");
        bud.append("\t\tCount as app license ").append(countAsLicense).append("\n");
        bud.append("\t\tPercentage of uptime ").append(value).append("\n");
        return bud.toString();
    }
           
}
