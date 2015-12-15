/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.appdynamics.licensecount.data;

import org.appdynamics.appdrestapi.util.TimeRange;
import org.appdynamics.licensecount.resources.LicenseS;

/**
 *
 * @author soloink
 */
public class TierLicenseRange extends TimeRange{
    protected double javaCount, phpCount, iisCount, iisInternalCount, nodeJSCount, machineCount, 
            totalCount, tierAppAgentCount, tierMachineAgentCount, webserverCount, nativeSDKCount;

    public TierLicenseRange(){super();}

    public TierLicenseRange(String name){super(name);}
    
    public double getJavaCount() {
        return javaCount;
    }

    public void setJavaCount(int javaCount) {
        this.javaCount = javaCount;
    }

    public double getPhpCount() {
        return phpCount;
    }

    public void setPhpCount(int phpCount) {
        this.phpCount = phpCount;
    }

    public double getIisCount() {
        return iisCount;
    }

    public void setIisCount(int iisCount) {
        this.iisCount = iisCount;
    }

    public double getNodeJSCount() {
        return nodeJSCount;
    }

    public double getWebserverCount() {
        return webserverCount;
    }

    public void setWebserverCount(double webserverCount) {
        this.webserverCount = webserverCount;
    }

    public double getNativeSDKCount() {
        return nativeSDKCount;
    }

    public void setNativeSDKCount(double nativeSDKCount) {
        this.nativeSDKCount = nativeSDKCount;
    }
    
    public String getNodeJSCount_C(){
        double val1 = nodeJSCount/10;
        return new StringBuilder().append(LicenseS.licenseRound(val1))
                .append("(").append(new Double(nodeJSCount).intValue())
                .append(")").toString();
    }
    
    public String getNodeJSCount_TA(){
        double val1 = nodeJSCount/10;
        return new StringBuilder().append(val1).append("(")
                .append(new Double(nodeJSCount).intValue())
                .append(")").toString();
    }

    public void setNodeJSCount(int nodeJSCount) {
        this.nodeJSCount = nodeJSCount;
    }

    public double getMachineCount() {
        return machineCount;
    }

    public void setMachineCount(int machineCount) {
        this.machineCount = machineCount;
    }

    public double getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public double getIisInternalCount() {
        return iisInternalCount;
    }

    public void setIisInternalCount(double iisInternalCount) {
        this.iisInternalCount = iisInternalCount;
    }
    
    

    public double getTierAppAgentCount() {
        return tierAppAgentCount;
    }

    public void setTierAppAgentCount(int tierAppAgentCount) {
        this.tierAppAgentCount = tierAppAgentCount;
    }

    public double getTierMachineAgentCount() {
        return tierMachineAgentCount;
    }

    public void setTierMachineAgentCount(int tierMachineAgentCount) {
        this.tierMachineAgentCount = tierMachineAgentCount;
    }
    
    public String getDotNetCount(){
        StringBuilder bud = new StringBuilder();
        bud.append(new Double(iisCount).intValue()).append(" (")
                .append(new Double(iisInternalCount).intValue()).append(")");
        return bud.toString();
    }
    
    @Override
    public String toString(){
        StringBuilder bud=new StringBuilder();
        bud.append(name).append("\n");
        bud.append("\t\tTotal count ").append(totalCount).append("\n");
        bud.append("\t\tJava agent count ").append(javaCount).append("\n");
        bud.append("\t\tDotNet agent count ").append(iisCount).append("\n");
        bud.append("\t\tPHP agent count ").append(phpCount).append("\n");
        bud.append("\t\tNodeJS agent count ").append(nodeJSCount).append("\n");
        bud.append("\t\tWebServer agent count ").append(webserverCount).append("\n");
        bud.append("\t\tNative SDK agent count ").append(nativeSDKCount).append("\n");
        bud.append("\t\tMachine agent count ").append(machineCount).append("\n");
        bud.append("\t\tStart time ").append(getDate(start)).append(" :: ").append(start).append("\n");
        bud.append("\t\tEnd time ").append(getDate(end)).append(" :: ").append(end).append("\n");
        return bud.toString();
    }
    
    
    
}
