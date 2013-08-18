package com.aks.lendit.exception;

import com.aks.lendit.objects.Response;

public class LendItException extends Exception {
	protected Response response = null;
	
	public LendItException(String errorMsg) {
		super(errorMsg);
	}

	public Response getResponse() {
		return response;
	}

	public void setResponse(Response response) {
		this.response = response;
	}	
	
}
