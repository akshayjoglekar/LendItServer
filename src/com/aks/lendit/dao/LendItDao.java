package com.aks.lendit.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import org.apache.log4j.Logger;

import com.aks.lendit.config.ConfigManager;
import com.aks.lendit.exception.InternalException;

public class LendItDao {
	private static Logger logger = Logger.getLogger(LendItDao.class);
	
	public static void insertNewItem(String username, String itemname) throws InternalException {
		PreparedStatement statement = null;
		Connection connection = null;
		try {
			connection = ConfigManager.getInstance().getDatabaseConnection();
			String insertSql = "INSERT INTO TB_ITEM" +
					" (SEQNO, USERNAME, ITEMNAME)" +
					" VALUES" +
					" (NULL, ?, ?)";
			statement = connection.prepareStatement(insertSql);			
			long startTime = System.currentTimeMillis();
			logger.info("Executing database insert - " + insertSql + ", username=" + username + ",itemname=" + itemname);
			int i = 1;
			statement.setString(i++, username);
			statement.setString(i++, itemname);
			statement.executeUpdate();
			logger.info("DONE. Executed database insert. Timetaken = " + (System.currentTimeMillis() - startTime) + " ms");
		} catch (Exception e) {
			logger.error("Error while saving to database, username=" + username + ",itemname=" + itemname, e);
			throw new InternalException("Error while saving to database, username=" + username + ",itemname=" + itemname);
		} finally {
			try {
				if(statement != null) {
					statement.close();
				}
				if(connection != null) {
					connection.close();
				}
			} catch(Exception e) {}
		}
	}
}
