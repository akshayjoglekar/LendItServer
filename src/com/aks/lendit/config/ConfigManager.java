package com.aks.lendit.config;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Properties;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.log4j.Logger;

import com.aks.lendit.util.BeanManager;

public class ConfigManager {
	private static Logger logger = Logger.getLogger(ConfigManager.class);
    private Properties configProperties = new Properties();    
    private static ConfigManager configManager = null;
    private static BasicDataSource dataSource = null; 
    
    private ConfigManager() {    	
    }
    
    public static ConfigManager getInstance() {
    	if(configManager == null) {
    		configManager = new ConfigManager();
    	}
    	return configManager;
    }

   
    public void init(String configFilename, String contextFilePath, String contextFilename) {
        logger.info("\nInitializing LendIt...");
        try {
            logger.info("Loading properties from " + configFilename);
            FileInputStream fio = new FileInputStream(configFilename);
            configProperties.load(fio);
            fio.close();
            logger.info("Properties loaded from " + configFilename + ", PROPERTIES = " + configProperties);
            
			BeanManager beanManager = BeanManager.getInstance();
			beanManager.initialize("CLASSPATH", contextFilePath, contextFilename);
			dataSource = (BasicDataSource)beanManager.getBean("primary_data_source");
            
        } catch (Exception ex) {
            logger.error("Error while initializing ConfigManager ", ex);
            ex.printStackTrace();
            System.exit(0);
        }
    }

    public String getProperty(String strValue) {
        return configProperties.getProperty(strValue);
    }

    public String getProperty(String strValue, String defaultValue) {
        return configProperties.getProperty(strValue, defaultValue);
    }
    
    public Connection getDatabaseConnection() throws SQLException {
    	return dataSource.getConnection();
    }
}
