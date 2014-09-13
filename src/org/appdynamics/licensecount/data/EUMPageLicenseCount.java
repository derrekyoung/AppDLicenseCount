/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.appdynamics.licensecount.data;

import org.appdynamics.appdrestapi.data.MetricDatas;
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
    private long expectedYear;
    private ArrayList<EUMPageLicenseRange> pageLicenseRange=new ArrayList<EUMPageLicenseRange>();
    
    public EUMPageLicenseCount(){}
    
    
    public EUMPageLicenseCount(String pageName, String pagePath){
        this.pageName=pageName;
        this.pagePath=pagePath;
    }
    
    public void populateEUMPages(TimeRange totalTimeRange, ArrayList<TimeRange> timeRanges, 
            RESTAccess access, String applicationName){
        
    }
 
    
    public long getRollUpValue(MetricDatas mds){
        
        if(mds != null && mds.getSingleRollUpMetricValue() != null)
            return mds.getSingleRollUpMetricValue().getValue();
        
        return 0;
    }
    
    
}
