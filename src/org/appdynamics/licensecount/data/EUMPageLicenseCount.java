/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.appdynamics.licensecount.data;

import org.appdynamics.appdrestapi.data.MetricDatas;
import org.appdynamics.appdrestapi.data.MetricValue;
import org.appdynamics.appdrestapi.RESTAccess;
import org.appdynamics.appdrestapi.util.TimeRange;
import org.appdynamics.appdrestapi.resources.s;

import java.util.logging.Logger;
import java.util.logging.Level;

import java.util.ArrayList;

/**
 *
 * @author gilbert.solorzano
 * 
 *  This is where we going to provide all of the information for the pages. The interval will be very important.
 * 
 */
public class EUMPageLicenseCount {
    private static Logger logger=Logger.getLogger(EUMPageLicenseCount.class.getName());
    private String pageName;
    private String pagePath;
    private long pageRequestInTR;
    private EUMPageLicenseRange totalRangeValue ;
    private ArrayList<EUMPageLicenseRange> pageLicenseRange=new ArrayList<EUMPageLicenseRange>();
    
    public EUMPageLicenseCount(){}
    
    
    public EUMPageLicenseCount(String pageName, String pagePath){
        this.pageName=pageName;
        this.pagePath=pagePath;
    }

    
    public long getRollUpValue(MetricDatas mds){
        
        if(mds != null && mds.getSingleRollUpMetricValue() != null)
            return mds.getSingleRollUpMetricValue().getValue();
        
        return 0;
    }

    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    public String getPagePath() {
        return pagePath;
    }

    public void setPagePath(String pagePath) {
        this.pagePath = pagePath;
    }

    public long getPageRequestInTR() {
        return pageRequestInTR;
    }

    public void setPageRequestInTR(long pageRequestInTR) {
        this.pageRequestInTR = pageRequestInTR;
    }

    
    public ArrayList<EUMPageLicenseRange> getPageLicenseRange() {
        return pageLicenseRange;
    }

    public void setPageLicenseRange(ArrayList<EUMPageLicenseRange> pageLicenseRange) {
        this.pageLicenseRange = pageLicenseRange;
    }

    public EUMPageLicenseRange getTotalRangeValue() {
        return totalRangeValue;
    }

    public void setTotalRangeValue(EUMPageLicenseRange totalRangeValue) {
        this.totalRangeValue = totalRangeValue;
    }
    
    /*
        This is going to interate through all of the ranges and add up all of the counts for that particular 
        range.
    */
    public void countEUMPageLicenseRange(){
        long tempTotalCount=0;
        
        for(EUMPageLicenseRange nodeR:pageLicenseRange){
            
            for(MetricValue met:nodeR.getMetricValues().getMetricValue()){
                nodeR.setValue(nodeR.getValue() + met.getCount());
            }
            tempTotalCount+=nodeR.getValue();
        }
        
        if(totalRangeValue == null){ logger.log(Level.WARNING,"totalRangeValue is null: " + toString());}
        else{
            totalRangeValue.setValue(tempTotalCount);
            totalRangeValue.setCountAsLicense(true);
        }
        
    }
    
}
