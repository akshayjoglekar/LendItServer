package com.aks.lendit.util;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class BeanManager {
	private static final Logger logger = Logger.getLogger(BeanManager.class);;
	private static final String CLASSPATH = "CLASSPATH";
	private static final String FILE = "FILE";
	private static final String PARTITION = "\\|";
	
	private String contextReadType = null; 
	private String contextFilePath = null; 
	private String contextFilename = null; 
	
	private ApplicationContext ctx;
	private static BeanManager beanFactory = null;
	
	public static BeanManager getInstance() {
		if(beanFactory == null) {
			beanFactory = new BeanManager();
		}
		return beanFactory;
	}
	
	private BeanManager() {
		
	}	
	   
    public void initialize (String contextReadType, String contextFilePath, String contextFilename) {
    	this.contextReadType = contextReadType;
    	this.contextFilename = contextReadType;
    	this.contextFilePath = contextFilePath;    	
    	
		try {			
			logger.info(new StringBuffer("[INFO] ReadType : ").append(contextReadType).append(", Path : [")
				.append(contextFilePath).append(" | ").append(contextFilename)
			);

			String[] ctxFiles = Helper.isEmpty(contextFilename) ? new String[0] : contextFilename.split(PARTITION);
			String[] ctxPath = new String[ctxFiles.length];

			for (int i = 0; i < ctxFiles.length; i++) {
				ctxPath[i] = contextFilePath.concat(ctxFiles[i].trim());
			}
			
			if (contextReadType == null) {
				ctx = new ClassPathXmlApplicationContext(ctxPath);
			} else if (FILE.equals(contextReadType)) {
				ctx = new FileSystemXmlApplicationContext(ctxPath);
			} else if (CLASSPATH.equals(contextReadType)) {
				ctx = new ClassPathXmlApplicationContext(ctxPath);
			}

		} catch (Exception e) {
			logger.fatal("[ERROR] Spring Context files initialization failed - ", e);
			System.exit(1);
		}    	
    }
	
	public Object getBean(String name) {
		return ctx.getBean(name);
	}
	
	public boolean containsBean(String name) {
		return ctx.containsBean(name);
	}

	public String [] getBeanNamesForType(Class type) {
		return ctx.getBeanNamesForType(type);
	}	
	
	public Map getBeansForType(Class type) {
		return ctx.getBeansOfType(type);
	}
}