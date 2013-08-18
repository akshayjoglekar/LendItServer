package com.aks.lendit.requesthandler;

import java.util.Map;

import com.aks.lendit.dao.LendItDao;
import com.aks.lendit.exception.InternalException;
import com.aks.lendit.objects.Response;

public class PostItemHandler extends RequestHandler {
	private String REQ_USERNAME = "username";
	private String REQ_ITEMNAME = "itemname";

	@Override
	public Response executeRequest(Map<String, String> reqMap) throws InternalException {
		String username = reqMap.get(REQ_USERNAME);
		String itemname = reqMap.get(REQ_ITEMNAME);
		LendItDao.insertNewItem(username, itemname);
		Response response = new Response("0", "Success");
		return response;
	}

}
