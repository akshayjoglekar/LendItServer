package com.aks.lendit.objects;

import java.util.HashMap;

import com.aks.lendit.util.Helper;


public class Response {
	private String result = null;
	private String errorMsg = null;
			
	public Response(String result, String errorMsg) {
		super();
		this.result = result;
		this.errorMsg = errorMsg;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	
	public String getHttpResponseString() {
		HashMap<String, String> responseMap = new HashMap<String, String>();
		responseMap.put("Result", result);
		responseMap.put("ErrorMsg", errorMsg);
		return(Helper.getNameValueString(responseMap));
	}
}