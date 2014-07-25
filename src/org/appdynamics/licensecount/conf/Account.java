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
public class Account {
    private String user;
    private String passwd;
    private String account;
    
    public Account(){}

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
    
    @Override
    public String toString(){
        StringBuilder bud=new StringBuilder();
        bud.append(LicenseS.L2).append(LicenseS.ACCOUNT).append(LicenseS.VE).append(account);
        bud.append(LicenseS.L3).append(LicenseS.USER).append(LicenseS.VE).append(user);
        bud.append(LicenseS.L3).append(LicenseS.PASSWORD).append(LicenseS.VE).append(passwd);
        return bud.toString();
    }
}
