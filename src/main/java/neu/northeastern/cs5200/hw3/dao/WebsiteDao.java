package neu.northeastern.cs5200.hw3.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import neu.northeastern.cs5200.hw3.model.Developer;
import neu.northeastern.cs5200.hw3.model.Website;

public class WebsiteDao {
	private static final String URL = "jdbc:mysql://localhost:3306/hw3_db";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "root";

	private static final Logger LOGGER = LoggerFactory.getLogger(WebsiteDao.class);

	public static WebsiteDao instance = null;

	public static WebsiteDao getInstance() {
		if (instance == null) {
			instance = new WebsiteDao();
		}
		return instance;
	}

	private WebsiteDao() {
	}

	/**
	 * inserts properties in website instance parameter into the Website table. The
	 * website's developerId foreign key refer to Developer table primary key id
	 * whose value is equal to the developerId parameter
	 * 
	 * @param developerId
	 * @param website
	 * @return
	 */
	public int createWebsiteForDeveloper(int developerId, Website website) {
		int result = 0;
		String sql = "INSERT INTO website (id,name,description,created,updated,visits,developer_id) VALUES (?,?,?,?,?,?,?)";

		try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
				PreparedStatement statement = connection.prepareStatement(sql)) {
			Class.forName("com.mysql.jdbc.Driver");
			statement.setInt(1, website.getId());
			statement.setString(2, website.getName());
			statement.setString(3, website.getDescription());
			statement.setDate(4, new java.sql.Date(System.currentTimeMillis()));
			statement.setDate(5, new java.sql.Date(System.currentTimeMillis()));
			statement.setInt(6, website.getVisits());
			statement.setInt(7, developerId);

			result = statement.executeUpdate();

			connection.close();

		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}

		return result;
	}

	/**
	 * returns all records from Website table as a Collection of Website instances
	 * 
	 * @return
	 */
	public Collection<Website> findAllWebsites() {

		Collection<Website> websites = new ArrayList<Website>();
		ResultSet results = null;
		String sql = "SELECT * FROM website";

		try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
				PreparedStatement statement = connection.prepareStatement(sql)) {
			Class.forName("com.mysql.jdbc.Driver");
			results = statement.executeQuery(sql);
			while (results.next()) {
				Website w = new Website();
				w.setId(results.getInt("id"));
				w.setName(results.getString("name"));
				w.setDescription(results.getString("description"));
				w.setDeveloper(DeveloperDao.getInstance().findDeveloperById(results.getInt("developer_id")));
				w.setVisits(results.getInt("visits"));
				w.setPages(PageDao.getInstance().findPagesForWebsite(results.getInt("id")));
				w.setCreated(results.getDate("created"));
				w.setUpdated(results.getDate("updated"));
				websites.add(w);
			}
			connection.close();

		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}

		return websites;
	}

	/**
	 * returns all records from Website table as a Collection of Website instances
	 * whose developerId is equal to the developerId parameter
	 * 
	 * @param developerId
	 * @return
	 */
	public Collection<Website> findWebsitesForDeveloper(int developerId) {
		Collection<Website> websites = new ArrayList<Website>();
		ResultSet results = null;
		String sql = "SELECT * FROM website where developer_id=" + developerId;

		try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
				PreparedStatement statement = connection.prepareStatement(sql)) {
			Class.forName("com.mysql.jdbc.Driver");
			results = statement.executeQuery(sql);
			while (results.next()) {
				Website w = new Website();
				w.setId(results.getInt("id"));
				w.setName(results.getString("name"));
				w.setDescription(results.getString("description"));
				w.setDeveloper(DeveloperDao.getInstance().findDeveloperById(results.getInt("developer_id")));
				w.setVisits(results.getInt("visits"));
				w.setPages(PageDao.getInstance().findPagesForWebsite(results.getInt("id")));
				w.setCreated(results.getDate("created"));
				w.setUpdated(results.getDate("updated"));
				websites.add(w);
			}
			connection.close();

		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}

		return websites;
	}

	/**
	 * returns a record from Website table whose id field is equal to the websiteId
	 * parameter
	 * 
	 * @param websiteId
	 * @return
	 */
	public Website findWebsiteById(int websiteId) {
		Website w = new Website();
		ResultSet results = null;
		String sql = "SELECT * FROM website where id=" + websiteId;

		try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
				PreparedStatement statement = connection.prepareStatement(sql)) {
			Class.forName("com.mysql.jdbc.Driver");
			results = statement.executeQuery(sql);
			if (results.next()) {
				w.setId(results.getInt("id"));
				w.setName(results.getString("name"));
				w.setDescription(results.getString("description"));
				w.setDeveloper(DeveloperDao.getInstance().findDeveloperById(results.getInt("developer_id")));
				w.setVisits(results.getInt("visits"));
				w.setPages(PageDao.getInstance().findPagesForWebsite(results.getInt("id")));
				w.setCreated(results.getDate("created"));
				w.setUpdated(results.getDate("updated"));
			}
			connection.close();

		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}

		return w;
	}

	/**
	 * updates record in Website table whose id field is equal to websiteId
	 * parameter. New record field values are set to the values in the website
	 * instance parameter
	 * 
	 * @param websiteId
	 * @param website
	 * @return
	 */
	public int updateWebsite(int websiteId, Website website) {

		int result = 0;
		String sql = "UPDATE website set name=?,description=?,created=?,updated=?,visits=?,developer_id=? where id="
				+ websiteId;

		try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
				PreparedStatement statement = connection.prepareStatement(sql)) {
			Class.forName("com.mysql.jdbc.Driver");
			statement.setString(1, website.getName());
			statement.setString(2, website.getDescription());
			statement.setDate(3, new java.sql.Date(website.getCreated().getTime()));
			statement.setDate(4, new java.sql.Date(website.getUpdated().getTime()));
			statement.setInt(5, website.getVisits());
			statement.setInt(6, website.getDeveloper().getId());

			result = statement.executeUpdate();

			connection.close();

		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}

		return result;
	}

	/**
	 * deletes record from Website table whose id field is equal to websiteId
	 * parameter
	 * 
	 * @param websiteId
	 * @return
	 */
	public int deleteWebsite(int websiteId) {
		int result = 0;
		String sql = "DELETE from website where id=" + websiteId;

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
