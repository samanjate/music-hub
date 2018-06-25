package neu.northeastern.cs5200.hw3.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import neu.northeastern.cs5200.hw3.model.HeadingWidget;
import neu.northeastern.cs5200.hw3.model.HtmlWidget;
import neu.northeastern.cs5200.hw3.model.ImageWidget;
import neu.northeastern.cs5200.hw3.model.Widget;
import neu.northeastern.cs5200.hw3.model.YouTubeWidget;

public class WidgetDao {

	private static final String URL = "jdbc:mysql://localhost:3306/hw3_db";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "root";

	private static final Logger LOGGER = LoggerFactory.getLogger(WidgetDao.class);

	public static WidgetDao instance = null;

	public static WidgetDao getInstance() {
		if (instance == null) {
			instance = new WidgetDao();
		}
		return instance;
	}

	private WidgetDao() {
	}

	/**
	 * inserts properties in widget instance parameter into the Widget table. The
	 * widget's pageId foreign key refer to Page table primary key id whose value is
	 * equal to the pageId parameter
	 * 
	 * @param pageId
	 * @param widget
	 * @return
	 */
	public int createWidgetForPage(int pageId, Widget widget) {
		int result = 0;
		String sql = "INSERT INTO widget (name,width,height,css_class,css_style,text,dtype,size,html,src,url,shareble,expandable,widget_order,page_id) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
				PreparedStatement statement = connection.prepareStatement(sql)) {
			Class.forName("com.mysql.jdbc.Driver");
			statement.setString(1, widget.getName());
			statement.setInt(2, widget.getWidth());
			statement.setInt(3, widget.getHeight());

			if (widget.getCssClass() == null) {
				statement.setNull(4, Types.VARCHAR);
			} else {
				statement.setString(4, widget.getCssClass());
			}

			if (widget.getCssStyle() == null) {
				statement.setNull(5, Types.VARCHAR);
			} else {
				statement.setString(5, widget.getCssStyle());
			}

			if (widget.getText() == null) {
				statement.setNull(6, Types.VARCHAR);
			} else {
				statement.setString(6, widget.getText());
			}

			if (widget instanceof HeadingWidget) {
				statement.setString(7, "heading");
				if (((HeadingWidget) widget).getSize() == 0) {
					statement.setNull(8, Types.INTEGER);
				} else {
					statement.setInt(8, ((HeadingWidget) widget).getSize());
				}

				statement.setNull(9, Types.VARCHAR);
				statement.setNull(10, Types.VARCHAR);
				statement.setNull(11, Types.VARCHAR);
				statement.setNull(12, Types.BOOLEAN);
				statement.setNull(13, Types.BOOLEAN);

			}
			if (widget instanceof HtmlWidget) {

				statement.setString(7, "html");
				statement.setNull(8, Types.INTEGER);

				if (((HtmlWidget) widget).getHtml() == null) {
					statement.setNull(9, Types.VARCHAR);
				} else {
					statement.setString(9, ((HtmlWidget) widget).getHtml());
				}

				statement.setNull(10, Types.VARCHAR);
				statement.setNull(11, Types.VARCHAR);
				statement.setNull(12, Types.BOOLEAN);
				statement.setNull(13, Types.BOOLEAN);
			}
			if (widget instanceof ImageWidget) {

				statement.setString(7, "image");
				statement.setNull(8, Types.INTEGER);
				statement.setNull(9, Types.VARCHAR);

				if (((ImageWidget) widget).getSrc() == null) {
					statement.setNull(10, Types.VARCHAR);
				} else {
					statement.setString(10, ((ImageWidget) widget).getSrc());
				}

				statement.setNull(11, Types.VARCHAR);
				statement.setNull(12, Types.BOOLEAN);
				statement.setNull(13, Types.BOOLEAN);
			}
			if (widget instanceof YouTubeWidget) {

				statement.setString(7, "youtube");
				statement.setInt(8, Types.VARCHAR);
				statement.setNull(9, Types.VARCHAR);
				statement.setNull(10, Types.VARCHAR);

				if (((YouTubeWidget) widget).getUrl() == null) {
					statement.setNull(11, Types.VARCHAR);
				} else {
					statement.setString(11, ((YouTubeWidget) widget).getUrl());
				}

				if (((YouTubeWidget) widget).getShareble() == null) {
					statement.setNull(12, Types.BOOLEAN);
				} else {
					statement.setBoolean(12, ((YouTubeWidget) widget).getShareble());
				}

				if (((YouTubeWidget) widget).getExpandable() == null) {
					statement.setNull(13, Types.BOOLEAN);
				} else {
					statement.setBoolean(13, ((YouTubeWidget) widget).getExpandable());
				}

			}
			statement.setInt(14, widget.getWidgetOrder());
			statement.setInt(15, pageId);

			result = statement.executeUpdate();

			connection.close();

		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}

		return result;

	}

	/**
	 * returns all records from Widget table as a Collection of Widget instances
	 * 
	 * @return
	 */
	public Collection<Widget> findAllWidgets() {
		Collection<Widget> widgets = new ArrayList<>();
		String sql = "SELECT * FROM widget";

		try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
				PreparedStatement statement = connection.prepareStatement(sql);
				ResultSet results = statement.executeQuery(sql);) {
			Class.forName("com.mysql.jdbc.Driver");
			while (results.next()) {
				if (results.getString("dtype").equals("heading")) {
					HeadingWidget w = new HeadingWidget();
					w.setSize(results.getInt("size"));

					w.setId(results.getInt("id"));
					w.setName(results.getString("name"));
					w.setWidth(results.getInt("width"));
					w.setHeight(results.getInt("height"));
					w.setCssClass(results.getString("css_class"));
					w.setCssStyle(results.getString("css_style"));
					w.setText(results.getString("text"));
					w.setWidgetOrder(results.getInt("widget_order"));
					
					widgets.add(w);
				} else if (results.getString("dtype").equals("html")) {
					HtmlWidget w = new HtmlWidget();
					w.setHtml(results.getString("html"));

					w.setId(results.getInt("id"));
					w.setName(results.getString("name"));
					w.setWidth(results.getInt("width"));
					w.setHeight(results.getInt("height"));
					w.setCssClass(results.getString("css_class"));
					w.setCssStyle(results.getString("css_style"));
					w.setText(results.getString("text"));
					widgets.add(w);

				} else if (results.getString("dtype").equals("image")) {
					ImageWidget w = new ImageWidget();
					w.setSrc(results.getString("src"));

					w.setId(results.getInt("id"));
					w.setName(results.getString("name"));
					w.setWidth(results.getInt("width"));
					w.setHeight(results.getInt("height"));
					w.setCssClass(results.getString("css_class"));
					w.setCssStyle(results.getString("css_style"));
					w.setText(results.getString("text"));
					widgets.add(w);

				} else if (results.getString("dtype").equals("youtube")) {
					YouTubeWidget w = new YouTubeWidget();
					w.setUrl(results.getString("url"));
					w.setShareble(results.getBoolean("shareble"));
					w.setExpandable(results.getBoolean("expandable"));

					w.setId(results.getInt("id"));
					w.setName(results.getString("name"));
					w.setWidth(results.getInt("width"));
					w.setHeight(results.getInt("height"));
					w.setCssClass(results.getString("css_class"));
					w.setCssStyle(results.getString("css_style"));
					w.setText(results.getString("text"));
					widgets.add(w);
				}

			}
			connection.close();

		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}

		return widgets;

	}

	/**
	 * returns a record from Widget table whose id field is equal to the widgetId
	 * parameter
	 * 
	 * @param widgetId
	 * @return
	 */
	public Widget findWidgetById(int widgetId) {
		String sql = "SELECT * FROM widget where id=" + widgetId;

		try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
				PreparedStatement statement = connection.prepareStatement(sql);
				ResultSet results = statement.executeQuery(sql);) {
			Class.forName("com.mysql.jdbc.Driver");
			if (results.next()) {
				if (results.getString("dtype").equals("heading")) {
					HeadingWidget w = new HeadingWidget();
					w.setSize(results.getInt("size"));

					w.setId(results.getInt("id"));
					w.setName(results.getString("name"));
					w.setWidth(results.getInt("width"));
					w.setHeight(results.getInt("height"));
					w.setCssClass(results.getString("css_class"));
					w.setCssStyle(results.getString("css_style"));
					w.setText(results.getString("text"));
					w.setWidgetOrder(results.getInt("widget_order"));

					connection.close();
					return w;
				} else if (results.getString("dtype").equals("html")) {
					HtmlWidget w = new HtmlWidget();
					w.setHtml(results.getString("html"));

					w.setId(results.getInt("id"));
					w.setName(results.getString("name"));
					w.setWidth(results.getInt("width"));
					w.setHeight(results.getInt("height"));
					w.setCssClass(results.getString("css_class"));
					w.setCssStyle(results.getString("css_style"));
					w.setText(results.getString("text"));
					w.setWidgetOrder(results.getInt("widget_order"));
					connection.close();
					return w;

				} else if (results.getString("dtype").equals("image")) {
					ImageWidget w = new ImageWidget();
					w.setSrc(results.getString("src"));

					w.setId(results.getInt("id"));
					w.setName(results.getString("name"));
					w.setWidth(results.getInt("width"));
					w.setHeight(results.getInt("height"));
					w.setCssClass(results.getString("css_class"));
					w.setCssStyle(results.getString("css_style"));
					w.setText(results.getString("text"));
					w.setWidgetOrder(results.getInt("widget_order"));

					connection.close();
					return w;

				} else if (results.getString("dtype").equals("youtube")) {
					YouTubeWidget w = new YouTubeWidget();
					w.setUrl(results.getString("url"));
					w.setShareble(results.getBoolean("shareble"));
					w.setExpandable(results.getBoolean("expandable"));

					w.setId(results.getInt("id"));
					w.setName(results.getString("name"));
					w.setWidth(results.getInt("width"));
					w.setHeight(results.getInt("height"));
					w.setCssClass(results.getString("css_class"));
					w.setCssStyle(results.getString("css_style"));
					w.setText(results.getString("text"));
					w.setWidgetOrder(results.getInt("widget_order"));

					connection.close();
					return w;
				}

			}

		} catch (Exception e) {
			LOGGER.error(e.getMessage());

		}
		return null;
	}

	/**
	 * returns all records from Widget table as a Collection of Widget instances
	 * whose pageId is equal to the pageId parameter
	 * 
	 * @param pageId
	 * @return
	 */
	public Collection<Widget> findWidgetsForPage(int pageId) {
		Collection<Widget> widgets = new ArrayList<>();
		String sql = "SELECT * FROM widget where page_id=" + pageId;

		try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
				PreparedStatement statement = connection.prepareStatement(sql);
				ResultSet results = statement.executeQuery(sql);) {
			Class.forName("com.mysql.jdbc.Driver");
			while (results.next()) {
				if (results.getString("dtype").equals("heading")) {
					HeadingWidget w = new HeadingWidget();
					w.setSize(results.getInt("size"));
					w.setWidgetOrder(results.getInt("widget_order"));

					w.setId(results.getInt("id"));
					w.setName(results.getString("name"));
					w.setWidth(results.getInt("width"));
					w.setHeight(results.getInt("height"));
					w.setCssClass(results.getString("css_class"));
					w.setCssStyle(results.getString("css_style"));
					w.setText(results.getString("text"));
					widgets.add(w);
				} else if (results.getString("dtype").equals("html")) {
					HtmlWidget w = new HtmlWidget();
					w.setHtml(results.getString("html"));
					w.setWidgetOrder(results.getInt("widget_order"));

					w.setId(results.getInt("id"));
					w.setName(results.getString("name"));
					w.setWidth(results.getInt("width"));
					w.setHeight(results.getInt("height"));
					w.setCssClass(results.getString("css_class"));
					w.setCssStyle(results.getString("css_style"));
					w.setText(results.getString("text"));
					widgets.add(w);

				} else if (results.getString("dtype").equals("image")) {
					ImageWidget w = new ImageWidget();
					w.setSrc(results.getString("src"));

					w.setWidgetOrder(results.getInt("widget_order"));

					w.setId(results.getInt("id"));
					w.setName(results.getString("name"));
					w.setWidth(results.getInt("width"));
					w.setHeight(results.getInt("height"));
					w.setCssClass(results.getString("css_class"));
					w.setCssStyle(results.getString("css_style"));
					w.setText(results.getString("text"));
					widgets.add(w);

				} else if (results.getString("dtype").equals("youtube")) {
					YouTubeWidget w = new YouTubeWidget();
					w.setUrl(results.getString("url"));
					w.setShareble(results.getBoolean("shareble"));
					w.setExpandable(results.getBoolean("expandable"));
					w.setWidgetOrder(results.getInt("widget_order"));

					w.setId(results.getInt("id"));
					w.setName(results.getString("name"));
					w.setWidth(results.getInt("width"));
					w.setHeight(results.getInt("height"));
					w.setCssClass(results.getString("css_class"));
					w.setCssStyle(results.getString("css_style"));
					w.setText(results.getString("text"));
					widgets.add(w);
				}

			}
			connection.close();

		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}

		return widgets;

	}

	/**
	 * updates record in Widget table whose id field is equal to widgetId parameter.
	 * New record field values are set to the values in the widget instance
	 * parameter
	 * 
	 * @param widgetId
	 * @param widget
	 * @return
	 */
	public int updateWidget(int widgetId, Widget widget) {
		int result = 0;
		String sql = "UPDATE widget set name=?,width=?,height=?,css_class=?,css_style=?,text=?,dtype=?,size=?,html=?,src=?,url=?,shareble=?,expandable=?,widget_order=?,page_id=?)";
		try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
				PreparedStatement statement = connection.prepareStatement(sql)) {
			Class.forName("com.mysql.jdbc.Driver");
			statement.setString(1, widget.getName());
			statement.setInt(2, widget.getWidth());
			statement.setInt(3, widget.getHeight());

			if (widget.getCssClass() == null) {
				statement.setNull(4, Types.VARCHAR);
			} else {
				statement.setString(4, widget.getCssClass());
			}

			if (widget.getCssStyle() == null) {
				statement.setNull(5, Types.VARCHAR);
			} else {
				statement.setString(5, widget.getCssStyle());
			}

			if (widget.getText() == null) {
				statement.setNull(6, Types.VARCHAR);
			} else {
				statement.setString(6, widget.getText());
			}

			if (widget instanceof HeadingWidget) {
				statement.setString(7, "heading");
				if (((HeadingWidget) widget).getSize() == 0) {
					statement.setNull(8, Types.INTEGER);
				} else {
					statement.setInt(8, ((HeadingWidget) widget).getSize());
				}

				statement.setNull(9, Types.VARCHAR);
				statement.setNull(10, Types.VARCHAR);
				statement.setNull(11, Types.VARCHAR);
				statement.setNull(12, Types.BOOLEAN);
				statement.setNull(13, Types.BOOLEAN);

			}
			if (widget instanceof HtmlWidget) {

				statement.setString(7, "html");
				statement.setNull(8, Types.INTEGER);

				if (((HtmlWidget) widget).getHtml() == null) {
					statement.setNull(9, Types.VARCHAR);
				} else {
					statement.setString(9, ((HtmlWidget) widget).getHtml());
				}

				statement.setNull(10, Types.VARCHAR);
				statement.setNull(11, Types.VARCHAR);
				statement.setNull(12, Types.BOOLEAN);
				statement.setNull(13, Types.BOOLEAN);
			}
			if (widget instanceof ImageWidget) {

				statement.setString(7, "image");
				statement.setNull(8, Types.INTEGER);
				statement.setNull(9, Types.VARCHAR);

				if (((ImageWidget) widget).getSrc() == null) {
					statement.setNull(10, Types.VARCHAR);
				} else {
					statement.setString(10, ((ImageWidget) widget).getSrc());
				}

				statement.setNull(11, Types.VARCHAR);
				statement.setNull(12, Types.BOOLEAN);
				statement.setNull(13, Types.BOOLEAN);
			}
			if (widget instanceof YouTubeWidget) {

				statement.setString(7, "youtube");
				statement.setInt(8, Types.VARCHAR);
				statement.setNull(9, Types.VARCHAR);
				statement.setNull(10, Types.VARCHAR);

				if (((YouTubeWidget) widget).getUrl() == null) {
					statement.setNull(11, Types.VARCHAR);
				} else {
					statement.setString(11, ((YouTubeWidget) widget).getUrl());
				}

				if (((YouTubeWidget) widget).getShareble() == null) {
					statement.setNull(12, Types.BOOLEAN);
				} else {
					statement.setBoolean(12, ((YouTubeWidget) widget).getShareble());
				}

				if (((YouTubeWidget) widget).getExpandable() == null) {
					statement.setNull(13, Types.BOOLEAN);
				} else {
					statement.setBoolean(13, ((YouTubeWidget) widget).getExpandable());
				}

			}
			statement.setInt(14, widget.getWidgetOrder());
			statement.setInt(15, widget.getPage().getId());

			result = statement.executeUpdate();

			connection.close();

		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}

		return result;

	}

	/**
	 * deletes record from Widget table whose id field is equal to widgetId
	 * parameter
	 * 
	 * @param widgetId
	 * @return
	 */
	public int deleteWidget(int widgetId) {
		int result = 0;
		String sql = "DELETE from widget where id=" + widgetId;

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
