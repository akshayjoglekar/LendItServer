package com.aks.lendit.exception;

import com.aks.lendit.objects.Response;

public class MissingParameterException extends LendItException {
	public MissingParameterException(String parameterName) {
		super("Mandatory parameter missing. Parameter name = " + parameterName);
		response = new Response("1", "Mandatory parameter missing. Parameter name = " + parameterName);
	}
}