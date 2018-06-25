package neu.northeastern.cs5200.hw3.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import neu.northeastern.cs5200.hw3.model.Page;
import neu.northeastern.cs5200.hw3.model.Website;

public class PageDao {
	private static final String URL = "jdbc:mysql://localhost:3306/hw3_db";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "root";

	private static final Logger LOGGER = LoggerFactory.getLogger(PageDao.class);

	public static PageDao instance = null;

	public static PageDao getInstance() {
		if (instance == null) {
			instance = new PageDao();
		}
		return instance;
	}

	private PageDao() {
	}

	/**
	 * inserts properties in page instance parameter into the Page table. The page's
	 * websiteId foreign key refer to Website table primary key id whose value is
	 * equal to the websiteId parameter
	 * 
	 * @param websiteId
	 * @param page
	 * @return
	 */

	public int createPageForWebsite(int websiteId, Page page) {
		int result = 0;
		String sql = "INSERT INTO page (id,title,description,created,updated,views,website_id) VALUES (?,?,?,?,?,?,?)";
		String assignmentDue = "2018-06-24";
		String courseStart = "2018-05-04";
		try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
				PreparedStatement statement = connection.prepareStatement(sql)) {
			Class.forName("com.mysql.jdbc.Driver");
			statement.setInt(1, page.getId());
			statement.setString(2, page.getTitle());
			statement.setString(3, page.getDescription());
			statement.setDate(4, Date.valueOf(courseStart));
			statement.setDate(5, Date.valueOf(assignmentDue));
			statement.setInt(6, page.getViews());
			statement.setInt(7, websiteId);

			result = statement.executeUpdate();

			statement.close();
			connection.close();

		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}

		return result;

	}

	/**
	 * returns all records from Page table as a Collection of Page instances
	 * 
	 * @return
	 */
	public Collection<Page> findAllPages() {

		Collection<Page> pages = new ArrayList<>();
		ResultSet results = null;
		String sql = "SELECT * FROM page";

		try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
				PreparedStatement statement = connection.prepareStatement(sql)) {
			Class.forName("com.mysql.jdbc.Driver");
			results = statement.executeQuery(sql);
			while (results.next()) {
				Page p = new Page();
				p.setId(results.getInt("id"));
				p.setTitle(results.getString("title"));
				p.setDescription(results.getString("description"));
				p.setWebsite(WebsiteDao.getInstance().findWebsiteById(results.getInt("website_id")));
				p.setViews(results.getInt("views"));
				p.setWidgets(WidgetDao.getInstance().findWidgetsForPage(results.getInt("id")));
				p.setCreated(results.getDate("created"));
				p.setUpdated(results.getDate("updated"));
				pages.add(p);
			}
			statement.close();
			connection.close();

		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}

		return pages;
	}

	/**
	 * returns a record from Page table whose id field is equal to the pageId
	 * parameter
	 * 
	 * @param pageId
	 * @return
	 */
	public Page findPageById(int pageId) {

		ResultSet results = null;
		Page p = new Page();
		String sql = "SELECT * FROM page where id=" + pageId;

		try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
				PreparedStatement statement = connection.prepareStatement(sql)) {
			Class.forName("com.mysql.jdbc.Driver");
			results = statement.executeQuery(sql);
			if (results.next()) {

				p.setId(results.getInt("id"));
				p.setTitle(results.getString("title"));
				p.setDescription(results.getString("description"));
				p.setWebsite(WebsiteDao.getInstance().findWebsiteById(results.getInt("website_id")));
				p.setViews(results.getInt("views"));
				p.setWidgets(WidgetDao.getInstance().findWidgetsForPage(results.getInt("id")));
				p.setCreated(results.getDate("created"));
				p.setUpdated(results.getDate("updated"));
			}
			statement.close();
			connection.close();

		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}

		return p;

	}

	/**
	 * returns a record from Page table whose title field is equal to the page title
	 * parameter
	 * 
	 * @param title
	 * @return
	 */
	public int findPageIdByPageTitle(String title) {

		ResultSet results = null;
		int p=0;
		String sql = "SELECT id FROM page where title='" + title+"'";

		try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
				PreparedStatement statement = connection.prepareStatement(sql)) {
			Class.forName("com.mysql.jdbc.Driver");
			results = statement.executeQuery(sql);
			if (results.next()) {
				p = results.getInt("id");
			}
			statement.close();
			connection.close();

		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}

		return p;

	}

	
	/**
	 * returns all records from Page table as a Collection of Page instances whose
	 * websiteId is equal to the websiteId parameter
	 * 
	 * @param websiteId
	 * @return
	 */
	public Collection<Page> findPagesForWebsite(int websiteId) {

		Collection<Page> pages = new ArrayList<>();
		ResultSet results = null;
		String sql = "SELECT * FROM page where website_id=" + websiteId;

		try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
				PreparedStatement statement = connection.prepareStatement(sql)) {
			Class.forName("com.mysql.jdbc.Driver");
			results = statement.executeQuery(sql);
			while (results.next()) {
				Page p = new Page();
				p.setId(results.getInt("id"));
				p.setTitle(results.getString("title"));
				p.setDescription(results.getString("description"));
				p.setWebsite(WebsiteDao.getInstance().findWebsiteById(results.getInt("website_id")));
				p.setViews(results.getInt("views"));
				p.setWidgets(WidgetDao.getInstance().findWidgetsForPage(results.getInt("id")));
				p.setCreated(results.getDate("created"));
				p.setUpdated(results.getDate("updated"));
				pages.add(p);
			}
			statement.close();
			connection.close();

		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}

		return pages;
	}

	/**
	 * updates record in Page table whose id field is equal to pageId parameter. New
	 * record field values are set to the values in the page instance parameter
	 * 
	 * @param pageId
	 * @param page
	 * @return
	 */
	public int updatePage(int pageId, Page page) {

		int result = 0;
		String sql = "UPDATE page set title=?,description=?,created=?,updated=?,views=?,website_id=? where id="
				+ pageId;

		try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
				PreparedStatement statement = connection.prepareStatement(sql)) {
			Class.forName("com.mysql.jdbc.Driver");
			statement.setString(1, page.getTitle());
			statement.setString(2, page.getDescription());
			statement.setDate(3, new java.sql.Date(page.getCreated().getTime()));
			statement.setDate(4, new java.sql.Date(page.getUpdated().getTime()));
			statement.setInt(5, page.getViews());
			statement.setInt(6, page.getWebsite().getId());

			result = statement.executeUpdate();

			statement.close();
			connection.close();

		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}

		return result;
	}

	/**
	 * deletes record from Page table whose id field is equal to pageId parameter
	 * 
	 * @param pageId
	 * @return
	 */
	public int deletePage(int pageId) {
		int result = 0;
		String sql = "DELETE from page where id=" + pageId;

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
