/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.appdynamics.licensecount.data;
import org.appdynamics.licensecount.resources.LicenseS;
import org.appdynamics.appdrestapi.util.TimeRange;
import org.appdynamics.appdrestapi.data.MetricDatas;
import org.appdynamics.appdrestapi.RESTAccess;

import java.util.HashMap;
import org.appdynamics.appdrestapi.data.MetricValue;
import org.appdynamics.appdrestapi.data.MetricValues;
/**
 *
 * @author gilbert.solorzano
 * 
 * 
 * This is going to be a roll up count
 */
public class EUMPageLicenseRange extends TimeRange{
    protected long count=0;
    protected long value;
    protected MetricValues metricValues=new MetricValues();
    protected boolean countAsLicense=false;
    
   
    public EUMPageLicenseRange(){}

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    public boolean isCountAsLicense() {
        return countAsLicense;
    }

    public void setCountAsLicense(boolean countAsLicense) {
        this.countAsLicense = countAsLicense;
    }

    public double getUptimePercentageForPage(){
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
        this.metricValues = metricValues;
    }
    
    

    @Override
    public String toString(){
        StringBuilder bud=new StringBuilder();
        bud.append("Time range\n");
        bud.append("\t\tStart time ").append(getDate(start)).append(" :: ").append(start).append("\n");
        bud.append("\t\tEnd time ").append(getDate(end)).append(" :: ").append(end).append("\n");
        bud.append("\t\tCount as license ").append(countAsLicense).append("\n");
        bud.append("\t\tPercentage of uptime ").append(value).append("\n");
        return bud.toString();
    }
}
