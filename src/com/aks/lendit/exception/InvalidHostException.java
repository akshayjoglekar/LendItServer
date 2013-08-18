package com.aks.lendit.exception;

import com.aks.lendit.objects.Response;

public class InvalidHostException extends LendItException {
	public InvalidHostException(String errorMsg) {
		super(errorMsg);
		response = new Response("1", "Invalid source. Please check your IP");
	}
}
