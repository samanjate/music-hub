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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import neu.northeastern.cs5200.hw3.model.Developer;

public class DeveloperDao {

	private static final String URL = "jdbc:mysql://localhost:3306/hw3_db";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "root";

	private static final Logger LOGGER = LoggerFactory.getLogger(DeveloperDao.class);

	public static DeveloperDao instance = null;

	public static DeveloperDao getInstance() {
		if (instance == null) {
			instance = new DeveloperDao();
		}
		return instance;
	}

	private DeveloperDao() {
	}

	/**
	 * inserts properties in developer instance parameter in tables Developer and
	 * Person
	 *
	 * @param developer
	 * @return
	 */
	public int createDeveloper(Developer developer) {
		int result = 0;
		String sql = "INSERT INTO person (id,first_name,last_name,username,password,email,developer_key,dtype,dob) VALUES (?,?,?,?,?,?,?,?,?)";

		try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
				PreparedStatement statement = connection.prepareStatement(sql)) {
			Class.forName("com.mysql.jdbc.Driver");
			statement.setInt(1, developer.getId());
			statement.setString(2, developer.getFirstName());
			statement.setString(3, developer.getLastName());
			statement.setString(4, developer.getUsername());
			statement.setString(5, developer.getPassword());
			statement.setString(6, developer.getEmail());
			statement.setString(7, developer.getDeveloperKey());
			statement.setString(8, "Developer");

			if (developer.getDob() != null) {
				statement.setDate(9, new java.sql.Date(developer.getDob().getDate()));
			} else {
				statement.setNull(9, Types.DATE);
			}

			result = statement.executeUpdate();

			connection.close();

		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}

		return result;
	}

	/**
	 * returns all joined records from Developer and Person tables as a Collection
	 * of Developer instances
	 * 
	 * @return
	 */
	public Collection<Developer> findAllDevelopers() {
		Collection<Developer> developers = new ArrayList<>();
		String sql = "SELECT * FROM person where dtype='Developer'";

		try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
				PreparedStatement statement = connection.prepareStatement(sql);
				ResultSet results = statement.executeQuery(sql);) {
			Class.forName("com.mysql.jdbc.Driver");
			while (results.next()) {
				Developer d = new Developer();
				d.setId(results.getInt("id"));
				d.setDob(results.getDate("dob"));
				d.setEmail(results.getString("email"));
				d.setFirstName(results.getString("first_name"));
				d.setLastName(results.getString("last_name"));
				d.setPassword(results.getString("password"));
				d.setUsername(results.getString("username"));
				d.setDeveloperKey(results.getString("developer_key"));
				d.setWebsites(WebsiteDao.getInstance().findWebsitesForDeveloper(results.getInt("id")));

				developers.add(d);
			}
			connection.close();

		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}

		return developers;

	}

	/**
	 * returns a joined record from Developer and Person tables whose id field is
	 * equal to the developerId parameter
	 * 
	 * @param username
	 * @return
	 */
	public Developer findDeveloperById(int developerId) {
		Developer developer = new Developer();
		ResultSet results = null;
		String sql = "SELECT * FROM person where dtype='Developer' and id=" + developerId;

		try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
				PreparedStatement statement = connection.prepareStatement(sql)) {
			Class.forName("com.mysql.jdbc.Driver");
			results = statement.executeQuery(sql);
			while (results.next()) {
				developer.setId(results.getInt("id"));
				developer.setDob(results.getDate("dob"));
				developer.setEmail(results.getString("email"));
				developer.setFirstName(results.getString("first_name"));
				developer.setLastName(results.getString("last_name"));
				developer.setPassword(results.getString("password"));
				developer.setUsername(results.getString("username"));
				developer.setDeveloperKey(results.getString("developer_key"));
				break;
			}
			connection.close();

		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}

		return developer;
	}

	/**
	 * returns a joined record from Developer and Person tables whose username field
	 * matches the parameter
	 * 
	 * @param username
	 * @return
	 */
	public Developer findDeveloperByUsername(String username) {
		Developer developer = new Developer();
		ResultSet results = null;
		String sql = "SELECT * FROM person where dtype='Developer' and username like '" + username + "'";

		try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
				PreparedStatement statement = connection.prepareStatement(sql)) {
			Class.forName("com.mysql.jdbc.Driver");
			results = statement.executeQuery(sql);
			while (results.next()) {
				developer.setId(results.getInt("id"));
				developer.setDob(results.getDate("dob"));
				developer.setEmail(results.getString("email"));
				developer.setFirstName(results.getString("first_name"));
				developer.setLastName(results.getString("last_name"));
				developer.setPassword(results.getString("password"));
				developer.setUsername(results.getString("username"));
				developer.setDeveloperKey(results.getString("developer_key"));
				break;
			}
			connection.close();

		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}

		return developer;

	}

	/**
	 * returns a joined record from Developer and Person tables whose username and
	 * password fields match the parameters
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	public Developer findDeveloperByCredentials(String username, String password) {
		Developer developer = new Developer();
		ResultSet results = null;
		String sql = "SELECT * FROM person where dtype='Developer' and username like '" + username
				+ "' and password like '" + password + "'";

		try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
				PreparedStatement statement = connection.prepareStatement(sql)) {
			Class.forName("com.mysql.jdbc.Driver");
			results = statement.executeQuery(sql);
			while (results.next()) {
				developer.setId(results.getInt("id"));
				developer.setDob(results.getDate("dob"));
				developer.setEmail(results.getString("email"));
				developer.setFirstName(results.getString("first_name"));
				developer.setLastName(results.getString("last_name"));
				developer.setPassword(results.getString("password"));
				developer.setUsername(results.getString("username"));
				developer.setDeveloperKey(results.getString("developer_key"));
				break;
			}
			connection.close();

		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}

		return developer;
	}

	/**
	 * updates records in Developer and Person tables whose id field is equal to
	 * developerId parameter. New record field values are set to the values in the
	 * developer instance parameter
	 * 
	 * @param developerId
	 * @param developer
	 * @return
	 */
	public int updateDeveloper(int developerId, Developer developer) {

		int result = 0;
		String sql = "UPDATE person set first_name=?,last_name=?,username=?,password=?,email=?,developer_key=?,dob=? where id="
				+ developerId;

		try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
				PreparedStatement statement = connection.prepareStatement(sql)) {
			Class.forName("com.mysql.jdbc.Driver");
			statement.setString(1, developer.getFirstName());
			statement.setString(2, developer.getLastName());
			statement.setString(3, developer.getUsername());
			statement.setString(4, developer.getPassword());
			statement.setString(5, developer.getEmail());
			statement.setString(6, developer.getDeveloperKey());
			if (developer.getDob() != null) {
				statement.setDate(7, new java.sql.Date(developer.getDob().getDate()));
			} else {
				statement.setNull(7, Types.DATE);
			}
			result = statement.executeUpdate();

			connection.close();

		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}

		return result;
	}

	/**
	 * deletes records from Developer and Person tables whose id field is equal to
	 * developerId parameter
	 * 
	 * @param developerId
	 * @return
	 */
	int deleteDeveloper(int developerId) {
		int result = 0;
		String sql = "DELETE from person where id=" + developerId;

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
