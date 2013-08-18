package com.aks.lendit.util;

import org.apache.log4j.Logger;

import com.aks.lendit.config.ConfigManager;

public class HostValidator {
	private Logger logger = Logger.getLogger(HostValidator.class);
	private String[] ipAddressList = null;
	private String strIpAddressList = "";
	
	public HostValidator() {
		strIpAddressList = ConfigManager.getInstance().getProperty("valid_remote_ip", "");
		ipAddressList = strIpAddressList.split("\\|");
	}
	
	public boolean validateRemoteHost(String remoteHost) {
		try {
			if(null == ipAddressList || 0 == ipAddressList.length || true == StringUtil.isNull(remoteHost)) {
				logger.error("[FAIL] IP Addresss List and remoteHost have to be not null - ipAddressList : " + ipAddressList + ", remoteHost=" + remoteHost);
				return false;
			}
			
			for(String ipAddress: ipAddressList) {
				if(ipAddress.startsWith("*")) {
					if(remoteHost.endsWith(ipAddress.replace("*", ""))) {
						return true;
					}
				}			
				if(ipAddress.endsWith("*")) {
					if(remoteHost.startsWith(ipAddress.replace("*", ""))) {
						return true;
					}
				}
				if(true == remoteHost.equals(ipAddress)) {
					return true;
				}
			}
		} catch (Exception e) {
			logger.error("[FAIL] Error while validating remote host - ipAddressList : " + ipAddressList + ", remoteHost=" + remoteHost);
			return false;
		}		
		logger.error("[FAIL] IP Addresss List and remoteHost dont match - ipAddressList : " + ipAddressList + ", remoteHost=" + remoteHost);
		return false;
	}	
	
	public String getStrIpAddressList() {
		return strIpAddressList;
	}

	public static void main(String[] args) {
		HostValidator hv = new HostValidator();
		System.out.println(hv.validateRemoteHost("192.168.0.0"));
	}
	
}
