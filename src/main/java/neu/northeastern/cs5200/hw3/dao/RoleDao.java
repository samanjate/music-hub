package neu.northeastern.cs5200.hw3.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Types;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import neu.northeastern.cs5200.hw3.model.RoleType;

public class RoleDao {

	private static final String URL = "jdbc:mysql://localhost:3306/hw3_db";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "root";

	private static final Logger LOGGER = LoggerFactory.getLogger(DeveloperDao.class);

	public static RoleDao instance = null;

	public static RoleDao getInstance() {
		if (instance == null) {
			instance = new RoleDao();
		}
		return instance;
	}

	private RoleDao() {
	}

	/**
	 * insert roles: owner, admin, writer, editor, reviewer
	 */
	public int insertRoleType(RoleType role) {
		int result = 0;
		String sql = "INSERT INTO role_type (id,role_name) VALUES (?,?)";

		try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
				PreparedStatement statement = connection.prepareStatement(sql)) {
			Class.forName("com.mysql.jdbc.Driver");
			statement.setInt(1, role.getId());
			statement.setString(2, role.getRoleName());
			result = statement.executeUpdate();

			connection.close();

		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}

		return result;
	}

	/**
	 * inserts into table Role a record that assigns a developer whose id is
	 * developerId, the role with roleId, to the website with websiteId
	 * 
	 * @param developerId
	 * @param websiteId
	 * @param roleId
	 */
	public int assignWebsiteRole(int developerId, int websiteId, int roleId) {
		int result = 0;
		String sql = "INSERT INTO role (role_type_id,developer_id,website_id) VALUES (?,?,?)";

		try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
				PreparedStatement statement = connection.prepareStatement(sql)) {
			Class.forName("com.mysql.jdbc.Driver");
			statement.setInt(1, roleId);
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
	 * inserts into table Role a record that assigns a developer whose id is
	 * developerId, the role with roleId, to the page with pageId
	 * 
	 * @param developerId
	 * @param pageId
	 * @param roleId
	 * @return
	 */
	public int assignPageRole(int developerId, int pageId, int roleId) {
		int result = 0;
		String sql = "INSERT INTO role (role_type_id,developer_id,page_id) VALUES (?,?,?)";

		try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
				PreparedStatement statement = connection.prepareStatement(sql)) {
			Class.forName("com.mysql.jdbc.Driver");
			statement.setInt(1, roleId);
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
	 * deletes from table Role a record that removes roleId from developerId, on
	 * websiteId
	 * 
	 * @param developerId
	 * @param websiteId
	 * @param roleId
	 */
	public int deleteWebsiteRole(int developerId, int websiteId, int roleId) {
		int result = 0;
		String sql = "DELETE from role where developer_id=" + developerId+" and website_id="+websiteId+" and role_type_id="+roleId;

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
	 * deletes from table Role a record that removes roleId from developerId, on
	 * websiteId
	 * 
	 * @param developerId
	 * @param websiteId
	 * @param roleId
	 */
	public int deleteWebsiteRoleByWebsiteId(int websiteId) {
		int result = 0;
		String sql = "DELETE from role where website_id="+websiteId;

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
	 * deletes from table Role a record that removes roleId from developerId, on
	 * pageId
	 * 
	 * @param developerId
	 * @param pageId
	 * @param roleId
	 */
	public int deletePageRole(int developerId, int pageId, int roleId) {
		int result = 0;
		String sql = "DELETE from role where developer_id=" + developerId+" and page_id="+pageId+" and role_type_id="+roleId;

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
	 * deletes from table Role a record that removes roleId on
	 * pageId
	 * 
	 * @param developerId
	 * @param pageId
	 * @param roleId
	 */
	public int deletePageRoleByPageId(int pageId) {
		int result = 0;
		String sql = "DELETE from role where page_id="+pageId;

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
