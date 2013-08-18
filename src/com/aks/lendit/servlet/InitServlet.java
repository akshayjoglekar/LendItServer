package com.aks.lendit.servlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;

import com.aks.lendit.config.ConfigManager;

public class InitServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(InitServlet.class);
	protected static final Object objForSync = new Object();
	protected static boolean bInitializedConfigServlet = false;
       
	@Override
	public void init(ServletConfig config) throws ServletException {
		try {
			super.init(config);
			if (!bInitializedConfigServlet) {
				synchronized (objForSync) {
					if (!bInitializedConfigServlet) {
						String strConfigFile = getServletContext().getInitParameter("CONFIG_FILE");
						String strContextFilePath = getServletContext().getInitParameter("CONTEXT_FILE_PATH");
						String strContextFileName = getServletContext().getInitParameter("CONTEXT_FILE_NAME");
						System.out.println("----------------------------------------------------\n");
						System.out.println("Initializing ConfigManager with " + strConfigFile + " ....");
						ConfigManager.getInstance().init(strConfigFile, strContextFilePath, strContextFileName);
						System.out.println("Initialized ConfigManager.");
						logger.info("Initialized ConfigManager.");
						bInitializedConfigServlet = true;
					}
				}
			}
		} catch (Exception ex) {
			logger.error("ERROR ERROR ERROR ", ex);
			ex.printStackTrace();
			throw new ServletException(ex);
		}
	}
}