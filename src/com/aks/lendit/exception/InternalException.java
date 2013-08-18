package com.aks.lendit.exception;

import com.aks.lendit.objects.Response;

public class InternalException extends LendItException {

	public InternalException(String errorMsg) {
		super(errorMsg);
		response = new Response("1","Internal error");
	}
}