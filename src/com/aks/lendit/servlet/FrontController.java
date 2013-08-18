package com.aks.lendit.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.aks.lendit.exception.InternalException;
import com.aks.lendit.exception.InvalidHostException;
import com.aks.lendit.exception.LendItException;
import com.aks.lendit.exception.MissingParameterException;
import com.aks.lendit.objects.Response;
import com.aks.lendit.requesthandler.RequestHandler;
import com.aks.lendit.util.HostValidator;
import com.aks.lendit.util.StringUtil;

public class FrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Logger logger = Logger.getLogger(FrontController.class);
	private HostValidator hostValidator = new HostValidator();

	public FrontController() {
		super();        
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		boolean isRemoteHostValid = hostValidator.validateRemoteHost(req.getRemoteHost());
		Response response = null;
		PrintWriter out = res.getWriter();
		Map<String, String> reqMap = getReqMap(req);
		String reqType = reqMap.get("reqtype");
		String timestamp = StringUtil.getNonNull(reqMap.get("timestamp"));
		long lStartTime = System.currentTimeMillis();
		String responseStr = null;
		try {
			if(isRemoteHostValid == false) {
				logger.error("[ERROR] RemoteHost NOT VALID remoteHost=" + req.getRemoteHost() + ", allowedHosts=" + hostValidator.getStrIpAddressList());
				throw new InvalidHostException("RemoteHost NOT VALID remoteHost=" + req.getRemoteHost());				
			} else {
				if(StringUtil.isNull(reqType)) {
					throw new MissingParameterException("reqtype");
				} else {
					response = RequestHandler.handleRequest(reqType, reqMap);
				}
				if(response == null) {
					throw new InternalException("Null response returned from RequestHandler");
				}

			}
			logger.info("[REQ] reqMap=" + reqMap+ ", [RES] timestamp=" + timestamp + ", reqType=" +  reqType + ",ResponseStr=" + responseStr + ",timetaken=" + (System.currentTimeMillis() - lStartTime) + " ms");
		} catch (LendItException e) {
			logger.error("Error while processing request", e);
			response = e.getResponse();			
		}
		responseStr = response.getHttpResponseString();
		out.write(responseStr);
		out.flush();
		out.close();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	private Map<String, String> getReqMap(HttpServletRequest req) {
		Map<String, String> reqMap = new HashMap<String, String>();
		@SuppressWarnings("rawtypes")
		Enumeration names = req.getParameterNames();
		String key = null;
		while (names.hasMoreElements()) {
			key = (String) names.nextElement();
			reqMap.put(key.toLowerCase(), req.getParameter(key).trim());
		}
		return reqMap;
	}	

}
