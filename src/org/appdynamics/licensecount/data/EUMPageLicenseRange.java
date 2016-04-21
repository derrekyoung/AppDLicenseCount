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
/**
 *
 * @author gilbert.solorzano
 * 
 * 
 * This is going to be a roll up count
 */
public class EUMPageLicenseRange extends TimeRange{
    private long pageCounts;
    
    
    public EUMPageLicenseRange(){}

    public long getPageCounts() {
        return pageCounts;
    }

    public void setPageCounts(long pageCounts) {
        this.pageCounts = pageCounts;
    }
    

    
    @Override
    public String toString(){
        StringBuilder bud=new StringBuilder();
        bud.append(name).append("\n");
        bud.append("\t\tPage counts ").append(pageCounts).append("\n");
        bud.append("\t\tStart time ").append(getDate(start)).append(" :: ").append(start).append("\n");
        bud.append("\t\tEnd time ").append(getDate(end)).append(" :: ").append(end).append("\n");
        return bud.toString();
    }
}
