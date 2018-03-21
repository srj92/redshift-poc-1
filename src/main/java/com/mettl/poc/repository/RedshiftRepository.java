package com.mettl.poc.repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.mettl.poc.config.db.RedshiftDataSource;

@Repository
public class RedshiftRepository {

	private static final Logger LOGGER = LoggerFactory.getLogger(RedshiftRepository.class);

	@Autowired
	@Qualifier("accessKey")
	private String accessKey;

	@Autowired
	@Qualifier("secretKey")
	private String secretKey;

	public void copyToCandidateResultStaging(String fileName) {
		Connection conn = null;
		Statement stmt = null;
		String copyQuerySql = "copy staging_candidate_result from 's3://redshift-poc-mettl/candidate_result_dump/"
				+ fileName + "'\n" + "access_key_id '" + accessKey + "'\n" + "secret_access_key '" + secretKey + "'\n"
				+ "delimiter ',' GZIP\n" + "NULL as 'NULL'\n" + "EMPTYASNULL\n" + "timeformat 'YYYY-MM-DDTHH:MI:SS';";

		try {
			conn = RedshiftDataSource.createNewConnection();
			stmt = conn.createStatement();
			stmt.executeUpdate(copyQuerySql);

			stmt.close();
			conn.close();
		} catch (SQLException sqlEx) {
			LOGGER.error("Exception: " + sqlEx);
		} catch (ClassNotFoundException e) {
			LOGGER.error("Exception: " + e);
		} finally {
			RedshiftDataSource.cleanUp(conn, stmt);
		}
	}

	public void clearStagingTable(String tableName, String tableTemplate) {
		Connection conn = null;
		Statement stmt = null;

		String dropSql = "drop table " + tableName + ";";
		String createLikeSql = "CREATE TABLE " + tableName + "(like " + tableTemplate + ");";

		try {
			conn = RedshiftDataSource.createNewConnection();
			stmt = conn.createStatement();
			stmt.executeUpdate(dropSql);
			stmt.executeUpdate(createLikeSql);
			stmt.close();
			conn.close();
		} catch (SQLException sqlEx) {
			LOGGER.error("Exception: " + sqlEx);
		} catch (ClassNotFoundException e) {
			LOGGER.error("Exception: " + e);
		} finally {
			RedshiftDataSource.cleanUp(conn, stmt);
		}
	}

	public void upsertFromStagingCandidateResult() {
		Connection conn = null;
		Statement stmt = null;
		String deleteSql = "DELETE FROM candidate_result " + "USING staging_candidate_result \n"
				+ "	WHERE staging_candidate_result.candidate_instance_id = candidate_result.candidate_instance_id;";

		String insertSql = "INSERT INTO candidate_result \n" + "SELECT * FROM staging_candidate_result;";

		try {
			conn = RedshiftDataSource.createNewConnection();
			stmt = conn.createStatement();
			stmt.executeUpdate(deleteSql);
			stmt.executeUpdate(insertSql);
			stmt.close();
			conn.close();
		} catch (SQLException sqlEx) {
			LOGGER.error("Exception: " + sqlEx);
		} catch (ClassNotFoundException e) {
			LOGGER.error("Exception: " + e);
		} finally {
			RedshiftDataSource.cleanUp(conn, stmt);
		}
	}

	public void copyToTagKey(String fileName) {

		Connection conn = null;
		Statement stmt = null;
		String copyQuerySql = "copy staging_tag_key from 's3://redshift-poc-mettl/tag_key_dump/" + fileName + "'\n"
				+ "access_key_id '" + accessKey + "'\n" + "secret_access_key '" + secretKey + "'\n"
				+ "delimiter ',' GZIP\n" + "NULL as 'NULL'\n" + "EMPTYASNULL\n" + "timeformat 'YYYY-MM-DDTHH:MI:SS';";

		try {
			conn = RedshiftDataSource.createNewConnection();
			stmt = conn.createStatement();
			stmt.executeUpdate(copyQuerySql);

			stmt.close();
			conn.close();
		} catch (SQLException sqlEx) {
			LOGGER.error("Exception: " + sqlEx);
		} catch (ClassNotFoundException e) {
			LOGGER.error("Exception: " + e);
		} finally {
			RedshiftDataSource.cleanUp(conn, stmt);
		}
	}

	public void insertFromStagingTagKey() {
		Connection conn = null;
		Statement stmt = null;
		String deleteSql = "DELETE FROM staging_tag_key " + "USING tag_key \n"
				+ "	WHERE staging_tag_key.key_name = tag_key.key_name;";

		String insertSql = "INSERT INTO tag_key (key_name) \n" + "SELECT key_name FROM staging_tag_key;";

		try {
			conn = RedshiftDataSource.createNewConnection();
			stmt = conn.createStatement();
			stmt.executeUpdate(deleteSql);
			stmt.executeUpdate(insertSql);
			stmt.close();
			conn.close();
		} catch (SQLException sqlEx) {
			LOGGER.error("Exception: " + sqlEx);
		} catch (ClassNotFoundException e) {
			LOGGER.error("Exception: " + e);
		} finally {
			RedshiftDataSource.cleanUp(conn, stmt);
		}
	}

	public Integer fetchKeyIdByKeyName(String keyName) {
		
		Integer keyId = -1;
		if (StringUtils.isEmpty(keyName)) {
			return keyId;
		}
		keyName = keyName.replace("'", "''");
		
		Connection conn = null;
		Statement stmt = null;
		String selectSql = "select key_id from tag_key where key_name = '" + keyName + "';";
		try {
			
			conn = RedshiftDataSource.getInstance().getConnection();
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(selectSql);
			
			while (rs.next()) {
				keyId = rs.getInt("key_id");
			}
			stmt.close();
			conn.close();
		} catch (SQLException sqlEx) {
			LOGGER.error("Exception: " + sqlEx);
		} finally {
			RedshiftDataSource.cleanUp(conn, stmt);
		}
		return keyId;
	}

}