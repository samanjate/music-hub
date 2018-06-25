package neu.northeastern.cs5200.hw3.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import neu.northeastern.cs5200.hw3.model.PriviledgeType;

public class PriviledgeDao {
	private static final String URL = "jdbc:mysql://localhost:3306/hw3_db";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "root";

	private static final Logger LOGGER = LoggerFactory.getLogger(DeveloperDao.class);

	public static PriviledgeDao instance = null;

	public static PriviledgeDao getInstance() {
		if (instance == null) {
			instance = new PriviledgeDao();
		}
		return instance;
	}

	private PriviledgeDao() {
	}

	/**
	 * insert priviledges: create, read, update, delete
	 */
	public int insertPrivildegType(PriviledgeType priviledge) {
		int result = 0;
		String sql = "INSERT INTO priviledge_type (id,priviledge_name) VALUES (?,?)";

		try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
				PreparedStatement statement = connection.prepareStatement(sql)) {
			Class.forName("com.mysql.jdbc.Driver");
			statement.setInt(1, priviledge.getId());
			statement.setString(2, priviledge.getPriviledgeName());
			result = statement.executeUpdate();

			connection.close();

		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}

		return result;
	}

	/**
	 * inserts into table Priviledge a record that assigns a developer whose id is
	 * developerId, the priviledge with priviledgeId, to the website with websiteId
	 * 
	 * @param developerId
	 * @param websiteId
	 * @param priviledgeId
	 * @return
	 */
	public int assignWebsitePriviledge(int developerId, int websiteId, int priviledgeId) {
		int result = 0;
		String sql = "INSERT INTO priviledge (priviledge_type_id,developer_id,website_id) VALUES (?,?,?)";

		try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
				PreparedStatement statement = connection.prepareStatement(sql)) {
			Class.forName("com.mysql.jdbc.Driver");
			statement.setInt(1, priviledgeId);
			statement.setInt(2, developerId);
			statement.setInt(3, websiteId);

			result = statement.executeUpdate();

			connection.close();

		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}

		return result;
	}

	/**
	 * inserts into table Priviledge a record that assigns a developer whose id is
	 * developerId, the priviledge with priviledgeId, to the page with pageId
	 * 
	 * @param developerId
	 * @param pageId
	 * @param priviledgeId
	 * @return
	 */
	public int assignPagePriviledge(int developerId, int pageId, int priviledgeId) {
		int result = 0;
		String sql = "INSERT INTO priviledge (priviledge_type_id,developer_id,page_id) VALUES (?,?,?)";

		try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
				PreparedStatement statement = connection.prepareStatement(sql)) {
			Class.forName("com.mysql.jdbc.Driver");
			statement.setInt(1, priviledgeId);
			statement.setInt(2, developerId);
			statement.setInt(3, pageId);

			result = statement.executeUpdate();

			connection.close();

		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}

		return result;
	}

	/**
	 * deletes from table Priviledge a record that removes priviledgeId from
	 * developerId, on websiteId
	 * 
	 * @param developerId
	 * @param websiteId
	 * @param priviledgeId
	 * @return
	 */
	public int deleteWebsitePriviledge(int developerId, int websiteId, int priviledgeId) {
		int result = 0;
		String sql = "DELETE from priviledge where developer_id=" + developerId+" and website_id="+websiteId+" and priviledge_type_id="+priviledgeId;

		try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
				PreparedStatement statement = connection.prepareStatement(sql)) {
			Class.forName("com.mysql.jdbc.Driver");
			result = statement.executeUpdate();

			connection.close();

		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}

		return result;

	}
	
	/**
	 * deletes from table Priviledge a record that removes priviledgeId from
	 * developerId, on websiteId
	 * 
	 * @param websiteId
	 * @return
	 */
	public int deleteWebsitePriviledgeByWebsiteId(int websiteId) {
		int result = 0;
		String sql = "DELETE from priviledge where website_id="+websiteId;

		try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
				PreparedStatement statement = connection.prepareStatement(sql)) {
			Class.forName("com.mysql.jdbc.Driver");
			result = statement.executeUpdate();

			connection.close();

		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}

		return result;

	}

	/**
	 * deletes from table priviledge a record that removes priviledgeId from
	 * developerId, on pageId
	 * 
	 * @param developerId
	 * @param pageId
	 * @param priviledgeId
	 */
	public int deletePagePriviledge(int developerId, int pageId, int priviledgeId) {
		int result = 0;
		String sql = "DELETE from priviledge where developer_id=" + developerId+" and page_id="+pageId+" and priviledge_type_id="+priviledgeId;

		try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
				PreparedStatement statement = connection.prepareStatement(sql)) {
			Class.forName("com.mysql.jdbc.Driver");
			result = statement.executeUpdate();

			connection.close();

		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}

		return result;

	}
	
	/**
	 * deletes from table priviledge a record that removes priviledgeId from
	 * developerId, on pageId
	 * 
	 * @param pageId
	 */
	public int deletePagePriviledgeByPageId(int pageId) {
		int result = 0;
		String sql = "DELETE from priviledge where page_id="+pageId;

		try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
				PreparedStatement statement = connection.prepareStatement(sql)) {
			Class.forName("com.mysql.jdbc.Driver");
			result = statement.executeUpdate();

			connection.close();

		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}

		return result;

	}

}
