package com.aks.lendit.requesthandler;

import java.util.HashMap;
import java.util.Map;

import com.aks.lendit.exception.InternalException;
import com.aks.lendit.objects.Response;

public abstract class RequestHandler {
	private static HashMap<String, RequestHandler> handlerMap = new HashMap<String, RequestHandler>();
	
	static {
		handlerMap.put("postitem", new PostItemHandler());
	}
	public static Response handleRequest(String reqType, Map<String, String> reqMap) throws InternalException {
		RequestHandler requestHandler = handlerMap.get(reqType);
		return requestHandler.executeRequest(reqMap);
	}
	
	public abstract Response executeRequest(Map<String, String> reqMap) throws InternalException;	
}