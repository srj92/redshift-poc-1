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

import com.mettl.poc.builder.RedshiftQueryBuilder;
import com.mettl.poc.config.RedshiftDataSource;
import com.mettl.poc.model.CandidateReport;
import com.mettl.poc.model.TagValue;

@Repository
public class RedshiftRepository {

	private static final Logger LOGGER = LoggerFactory.getLogger(RedshiftRepository.class);

	@Autowired
	@Qualifier("accessKey")
	private String accessKey;

	@Autowired
	@Qualifier("secretKey")
	private String secretKey;
	
	@Autowired
	private RedshiftQueryBuilder queryBuilder;

	public void copyToCandidateResultStaging(String fileName) {
		Connection conn = null;
		Statement stmt = null;
		String copyQuerySql = "copy staging_candidate_result from 's3://redshift-poc-mettl/candidate_result_dump/"
				+ fileName + "'\n" + "access_key_id '" + accessKey + "'\n" + "secret_access_key '" + secretKey + "'\n"
				+ "delimiter ',' GZIP\n" + "NULL as 'NULL'\n" + "EMPTYASNULL\n" + "timeformat 'YYYY-MM-DDTHH:MI:SS';";

		try {
			conn = RedshiftDataSource.getInstance().getConnection();
			stmt = conn.createStatement();
			stmt.executeUpdate(copyQuerySql);

			stmt.close();
			conn.close();
		} catch (SQLException sqlEx) {
			LOGGER.error("Exception: " + sqlEx);
		} finally {
			RedshiftDataSource.cleanUp(stmt);
		}
	}

	public void clearStagingTable(String tableName, String tableTemplate) {
		Connection conn = null;
		Statement stmt = null;

		String dropSql = "drop table " + tableName + ";";
		String createLikeSql = "CREATE TABLE " + tableName + "(like " + tableTemplate + ");";

		try {
			conn = RedshiftDataSource.getInstance().getConnection();
			stmt = conn.createStatement();
			stmt.executeUpdate(dropSql);
			stmt.executeUpdate(createLikeSql);
			stmt.close();
			conn.close();
		} catch (SQLException sqlEx) {
			LOGGER.error("Exception: " + sqlEx);
		} finally {
			RedshiftDataSource.cleanUp(stmt);
		}
	}

	public void upsertFromStagingCandidateResult() {
		Connection conn = null;
		Statement stmt = null;
		String deleteSql = "DELETE FROM candidate_result " + "USING staging_candidate_result \n"
				+ "	WHERE staging_candidate_result.candidate_instance_id = candidate_result.candidate_instance_id;";

		String insertSql = "INSERT INTO candidate_result \n" + "SELECT * FROM staging_candidate_result;";

		try {
			conn = RedshiftDataSource.getInstance().getConnection();
			stmt = conn.createStatement();
			stmt.executeUpdate(deleteSql);
			stmt.executeUpdate(insertSql);
			stmt.close();
			conn.close();
		} catch (SQLException sqlEx) {
			LOGGER.error("Exception: " + sqlEx);
		} finally {
			RedshiftDataSource.cleanUp(stmt);
		}
	}

	public void copyToStagingTagKey(String fileName) {
		Connection conn = null;
		Statement stmt = null;
		String copyQuerySql = "copy staging_tag_key from 's3://redshift-poc-mettl/tag_key_dump/" + fileName + "'\n"
				+ "access_key_id '" + accessKey + "'\n" + "secret_access_key '" + secretKey + "'\n"
				+ "delimiter ',' GZIP\n" + "NULL as 'NULL'\n" + "EMPTYASNULL\n" + "timeformat 'YYYY-MM-DDTHH:MI:SS';";

		try {
			conn = RedshiftDataSource.getInstance().getConnection();
			stmt = conn.createStatement();
			stmt.executeUpdate(copyQuerySql);

			stmt.close();
			conn.close();
		} catch (SQLException sqlEx) {
			LOGGER.error("Exception: " + sqlEx);
		} finally {
			RedshiftDataSource.cleanUp(stmt);
		}
	}

	public void insertFromStagingTagKey() {
		Connection conn = null;
		Statement stmt = null;
		String deleteSql = "DELETE FROM staging_tag_key " + "USING tag_key \n"
				+ "	WHERE staging_tag_key.key_name = tag_key.key_name;";

		String insertSql = "INSERT INTO tag_key (key_name) \n" + "SELECT key_name FROM staging_tag_key;";

		try {
			conn = RedshiftDataSource.getInstance().getConnection();
			stmt = conn.createStatement();
			stmt.executeUpdate(deleteSql);
			stmt.executeUpdate(insertSql);
			stmt.close();
			conn.close();
		} catch (SQLException sqlEx) {
			LOGGER.error("Exception: " + sqlEx);
		} finally {
			RedshiftDataSource.cleanUp(stmt);
		}
	}

	public Integer fetchKeyIdByKeyName(String keyName) {
		Integer keyId = -1;
		if (StringUtils.isEmpty(keyName)) {
			return keyId;
		}

		Connection conn = null;
		Statement stmt = null;
		String selectSql = "select key_id from tag_key where key_name = '" + keyName.replace("'", "''") + "';";
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
			RedshiftDataSource.cleanUp(stmt);
		}
		return keyId;
	}

	public void copyToStagingTagValue(String fileName) {
		Connection conn = null;
		Statement stmt = null;
		String copyQuerySql = "copy staging_tag_value from 's3://redshift-poc-mettl/tag_value_dump/" + fileName + "'\n"
				+ "access_key_id '" + accessKey + "'\n" + "secret_access_key '" + secretKey + "'\n"
				+ "delimiter ',' GZIP\n" + "NULL as 'NULL'\n" + "EMPTYASNULL\n" + "timeformat 'YYYY-MM-DDTHH:MI:SS';";

		try {
			conn = RedshiftDataSource.getInstance().getConnection();
			stmt = conn.createStatement();
			stmt.executeUpdate(copyQuerySql);

			stmt.close();
			conn.close();
		} catch (SQLException sqlEx) {
			LOGGER.error("Exception: " + sqlEx);
		} finally {
			RedshiftDataSource.cleanUp(stmt);
		}
	}

	public void insertFromStagingTagKVMap() {
		Connection conn = null;
		Statement stmt = null;
		String deleteSql = "DELETE FROM tag_key_value_mapping " + "USING staging_tag_key_value_mapping \n"
				+ "	WHERE tag_key_value_mapping.candidate_instance_id = staging_tag_key_value_mapping.candidate_instance_id "
				+ " and tag_key_value_mapping.key_id = staging_tag_key_value_mapping.key_id "
				+ " and tag_key_value_mapping.value_id = staging_tag_key_value_mapping.value_id "
				+ " and tag_key_value_mapping.tag_type = staging_tag_key_value_mapping.tag_type;";

		String insertSql = "INSERT INTO tag_key_value_mapping (candidate_instance_id, key_id, value_id, tag_type) \n"
				+ "SELECT candidate_instance_id, key_id, value_id, tag_type FROM staging_tag_key_value_mapping;";

		try {
			conn = RedshiftDataSource.getInstance().getConnection();
			stmt = conn.createStatement();
			stmt.executeUpdate(deleteSql);
			stmt.executeUpdate(insertSql);
			stmt.close();
			conn.close();
		} catch (SQLException sqlEx) {
			LOGGER.error("Exception: " + sqlEx);
		} finally {
			RedshiftDataSource.cleanUp(stmt);
		}
	}

	public int fetchValueId(TagValue tagValue) {
		Integer valueId = -1;
		if (tagValue == null || tagValue.getKeyId() == null) {
			return valueId;
		}
		String valueName = tagValue.getValueName();
		if (tagValue.getValueName() != null) {
			valueName = tagValue.getValueName().replace("'", "''");
		}

		Connection conn = null;
		Statement stmt = null;
		String selectSql = "select value_id from tag_value where value_name = '" + valueName + "' and key_id = "
				+ tagValue.getKeyId() + ";";
		try {
			conn = RedshiftDataSource.getInstance().getConnection();
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(selectSql);

			while (rs.next()) {
				valueId = rs.getInt("value_id");
			}
			stmt.close();
			conn.close();
		} catch (SQLException sqlEx) {
			LOGGER.error("Exception: " + sqlEx);
		} finally {
			RedshiftDataSource.cleanUp(stmt);
		}
		return valueId;
	}

	public void insertFromStagingTagValue() {

		Connection conn = null;
		Statement stmt = null;
		String deleteSql = "DELETE FROM staging_tag_value " + "USING tag_value \n"
				+ "	WHERE staging_tag_value.value_name = tag_value.value_name and staging_tag_value.key_id = tag_value.key_id;";

		String insertSql = "INSERT INTO tag_value (value_name,key_id) \n"
				+ "SELECT value_name, key_id FROM staging_tag_value;";

		try {
			conn = RedshiftDataSource.getInstance().getConnection();
			stmt = conn.createStatement();
			stmt.executeUpdate(deleteSql);
			stmt.executeUpdate(insertSql);
			stmt.close();
			conn.close();
		} catch (SQLException sqlEx) {
			LOGGER.error("Exception: " + sqlEx);
		} finally {
			RedshiftDataSource.cleanUp(stmt);
		}

	}

	public void copyToStagingTagKVMapping(String fileName) {
		Connection conn = null;
		Statement stmt = null;
		String copyQuerySql = "copy staging_tag_key_value_mapping from 's3://redshift-poc-mettl/tag_key_value_mapping_dump/"
				+ fileName + "'\n" + "access_key_id '" + accessKey + "'\n" + "secret_access_key '" + secretKey + "'\n"
				+ "delimiter ',' GZIP\n" + "NULL as 'NULL'\n" + "EMPTYASNULL\n" + "timeformat 'YYYY-MM-DDTHH:MI:SS';";

		try {
			conn = RedshiftDataSource.getInstance().getConnection();
			stmt = conn.createStatement();
			stmt.executeUpdate(copyQuerySql);

			stmt.close();
			conn.close();
		} catch (SQLException sqlEx) {
			LOGGER.error("Exception: " + sqlEx);
		} finally {
			RedshiftDataSource.cleanUp(stmt);
		}
	}

	public void updateCandidateReport(CandidateReport cr) {
		Connection conn = null;
		Statement stmt = null;
		String sql = queryBuilder.buildCandidateReportUpdateSql(cr);
		System.out.println(" ---> " + sql);
		try {
			conn = RedshiftDataSource.getInstance().getConnection();
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
			conn.close();
		} catch (SQLException sqlEx) {
			LOGGER.error("Exception: " + sqlEx);
		} finally {
			RedshiftDataSource.cleanUp(stmt);
		}
	}

}