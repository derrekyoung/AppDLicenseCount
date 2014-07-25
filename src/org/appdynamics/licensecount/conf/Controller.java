/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.appdynamics.licensecount.conf;

import org.appdynamics.licensecount.resources.LicenseS;

/**
 *
 * @author gilbert.solorzano
 */
public class Controller {
    private String fqdn;
    private String port;
    private boolean useSSL;
    
    public Controller(){}

    public String getFqdn() {
        return fqdn;
    }

    public void setFqdn(String fqdn) {
        this.fqdn = fqdn;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public boolean isUseSSL() {
        return useSSL;
    }

    public void setUseSSL(boolean useSSL) {
        this.useSSL = useSSL;
    }
    
    @Override
    public String toString(){
        StringBuilder bud = new StringBuilder();
        bud.append(LicenseS.L2).append(LicenseS.CONTROLLER).append(LicenseS.VE).append(fqdn);
        bud.append(LicenseS.L3).append(LicenseS.PORT_L).append(LicenseS.VE).append(port);
        bud.append(LicenseS.L3).append(LicenseS.SSL_L).append(LicenseS.VE).append(useSSL);
        return bud.toString();
    }
    
}
