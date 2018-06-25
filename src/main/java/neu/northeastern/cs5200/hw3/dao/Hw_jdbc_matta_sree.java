package neu.northeastern.cs5200.hw3.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import neu.northeastern.cs5200.hw3.model.Developer;
import neu.northeastern.cs5200.hw3.model.HeadingWidget;
import neu.northeastern.cs5200.hw3.model.HtmlWidget;
import neu.northeastern.cs5200.hw3.model.ImageWidget;
import neu.northeastern.cs5200.hw3.model.Page;
import neu.northeastern.cs5200.hw3.model.PriviledgeType;
import neu.northeastern.cs5200.hw3.model.RoleType;
import neu.northeastern.cs5200.hw3.model.Website;
import neu.northeastern.cs5200.hw3.model.Widget;
import neu.northeastern.cs5200.hw3.model.YouTubeWidget;

@RestController
public class Hw_jdbc_matta_sree {
	
	@RequestMapping(value = "/api/insert/all", method = RequestMethod.GET)
	private String insertAllData() {
		insertDevelopersAndUsers();
		insertWebsites();
		insertPages();
		insertWidgets();
		insertRoles();
		insertPriviledges();
		return "Inserted all Developer, Websites, Pages, Widgets, Roles, Priviledges. Please check database to see respective data";
		
	}
	/**
	 * Create developers and users. Insert into the correct tables depending on the
	 * type
	 * 
	 * @param args
	 */
	@RequestMapping(value = "/api/insert/developers", method = RequestMethod.GET)
	private String insertDevelopersAndUsers() {
		Developer d1 = new Developer(12, null, "Alice", "Wonder", "alice", "alice", "alice@wonder.com", "4321rewq");
		Developer d2 = new Developer(23, null, "Bob", "Marley", "bob", "bob", "bob@marley.com", "5432trew");
		Developer d3 = new Developer(34, null, "Charles", "Garcia", "charlie", "charlie", "chuch@garcia.com",
				"6543ytre");
		Developer d4 = new Developer(45, null, "Dan", "Martin", "dan", "dan", "dan@martin.com", "7654fda");
		Developer d5 = new Developer(56, null, "Ed", "Karaz", "ed", "alice", "ed@kar.com", "5678dfgh");

		int devsInserted = 0;
		if (DeveloperDao.getInstance().createDeveloper(d1) == 1) {
			devsInserted++;
		}
		if (DeveloperDao.getInstance().createDeveloper(d2) == 1) {
			devsInserted++;
		}
		if (DeveloperDao.getInstance().createDeveloper(d3) == 1) {
			devsInserted++;
		}
		if (DeveloperDao.getInstance().createDeveloper(d4) == 1) {
			devsInserted++;
		}
		if (DeveloperDao.getInstance().createDeveloper(d5) == 1) {
			devsInserted++;
		}

		if (devsInserted >= 1) {
			return "Records of developers inserted successfully, please check database";
		} else {
			return "Developer records could not be inserted";
		}
	}

	/**
	 * Create websites for developers
	 * 
	 * @param args
	 */
	@RequestMapping(value = "/api/insert/websites", method = RequestMethod.GET)
	private String insertWebsites() {
		Date currDate = new Date(System.currentTimeMillis());
		Website w1 = new Website(123, "Facebook", "an online social media and social networking service", currDate,
				currDate, 1234234);
		Website w2 = new Website(234, "Twitter", "an online news and social networking service", currDate, currDate,
				4321543);
		Website w3 = new Website(345, "Wikipedia", "a free online encyclopedia", currDate, currDate, 3456654);
		Website w4 = new Website(456, "CNN", "an American basic cable and satellite television news channel\r\n" + "",
				currDate, currDate, 6543345);
		Website w5 = new Website(567, "CNET",
				"an American media website that publishes reviews, news, articles, blogs, podcasts and videos on technology and consumer electronics",
				currDate, currDate, 5433455);
		Website w6 = new Website(678, "Gizmodo", "\r\n" + "\r\n"
				+ "a design, technology, science and science fiction website that also writes articles on politics\r\n"
				+ "", currDate, currDate, 4322345);

		int websitesInserted = 0;
		if (WebsiteDao.getInstance().createWebsiteForDeveloper(12, w1) == 1) {
			websitesInserted++;
		}
		if (WebsiteDao.getInstance().createWebsiteForDeveloper(23, w2) == 1) {
			websitesInserted++;
		}
		if (WebsiteDao.getInstance().createWebsiteForDeveloper(34, w3) == 1) {
			websitesInserted++;
		}
		if (WebsiteDao.getInstance().createWebsiteForDeveloper(12, w4) == 1) {
			websitesInserted++;
		}
		if (WebsiteDao.getInstance().createWebsiteForDeveloper(23, w5) == 1) {
			websitesInserted++;
		}
		if (WebsiteDao.getInstance().createWebsiteForDeveloper(34, w6) == 1) {
			websitesInserted++;
		}

		if (websitesInserted >= 1) {
			return "Records of websites inserted successfully, please check database";
		} else {
			return "Website records could not be inserted";
		}
	}

	/**
	 * Create websites for developers
	 * 
	 * @param args
	 */
	@RequestMapping(value = "/api/insert/pages", method = RequestMethod.GET)
	private String insertPages() {
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
		Date created = new Date();
		Date updated = new Date();
		try {
			created = dateformat.parse("2018-05-04");
			updated = dateformat.parse("2018-06-24");

		} catch (ParseException e) {
			e.printStackTrace();
		}

		Page p1 = new Page(123, "Home", "Landing page", created, updated, 123434);
		Page p2 = new Page(234, "About", "Website description", created, updated, 234545);
		Page p3 = new Page(345, "Contact", "Addresses, phones, and contact info", created, updated, 345656);
		Page p4 = new Page(456, "Preferences", "Where users can configure their preferences", created, updated, 456776);
		Page p5 = new Page(567, "Profile", "Users can configure their personal information", created, updated, 567878);

		int pagesInserted = 0;
		if (PageDao.getInstance().createPageForWebsite(567, p1) == 1) {
			pagesInserted++;
		}
		if (PageDao.getInstance().createPageForWebsite(678, p2) == 1) {
			pagesInserted++;
		}
		if (PageDao.getInstance().createPageForWebsite(345, p3) == 1) {
			pagesInserted++;
		}
		if (PageDao.getInstance().createPageForWebsite(456, p4) == 1) {
			pagesInserted++;
		}
		if (PageDao.getInstance().createPageForWebsite(567, p5) == 1) {
			pagesInserted++;
		}

		if (pagesInserted >= 1) {
			return "Records of pages inserted successfully, please check database";
		} else {
			return "Page records could not be inserted";
		}
	}

	/**
	 * Create websites for developers
	 * 
	 * @param args
	 */
	@RequestMapping(value = "/api/insert/widgets", method = RequestMethod.GET)
	private String insertWidgets() {

		HeadingWidget hw1 = new HeadingWidget("head123", "Welcome", 0);
		HtmlWidget hw2 = new HtmlWidget("post234", "<p>Lorem</p>", 0);
		HeadingWidget hw3 = new HeadingWidget("head345", "Hi", 1);
		HtmlWidget hw4 = new HtmlWidget("intro456", "<h1>Hi</h1>", 2);
		ImageWidget hw5 = new ImageWidget("image345", 3, 50, 100, "/img/567.png");
		YouTubeWidget hw6 = new YouTubeWidget("video456", 0, 400, 300, "https://youtu.be/h67VX51QXiQ");

		int widgetsInserted = 0;
		if (WidgetDao.getInstance().createWidgetForPage(123, hw1) == 1) {
			widgetsInserted++;
		}
		if (WidgetDao.getInstance().createWidgetForPage(234, hw2) == 1) {
			widgetsInserted++;
		}
		if (WidgetDao.getInstance().createWidgetForPage(345, hw3) == 1) {
			widgetsInserted++;
		}
		if (WidgetDao.getInstance().createWidgetForPage(345, hw4) == 1) {
			widgetsInserted++;
		}
		if (WidgetDao.getInstance().createWidgetForPage(345, hw5) == 1) {
			widgetsInserted++;
		}
		if (WidgetDao.getInstance().createWidgetForPage(456, hw6) == 1) {
			widgetsInserted++;
		}
		if (widgetsInserted >= 1) {
			return "Records of widgets inserted successfully, please check database";
		} else {
			return "Widget records could not be inserted";
		}
	}

	/**
	 * create Roles for Developer and Domain Objects
	 */
	@RequestMapping(value = "/api/insert/roles", method = RequestMethod.GET)
	private String insertRoles() {
		RoleType owner = new RoleType(1, "owner");
		RoleType admin = new RoleType(2, "admin");
		RoleType writer = new RoleType(3, "writer");
		RoleType editor = new RoleType(4, "editor");
		RoleType reviewer = new RoleType(5, "reviewer");

		RoleDao.getInstance().insertRoleType(owner);
		RoleDao.getInstance().insertRoleType(admin);
		RoleDao.getInstance().insertRoleType(writer);
		RoleDao.getInstance().insertRoleType(editor);
		RoleDao.getInstance().insertRoleType(reviewer);

		// website: Facebook
		RoleDao.getInstance().assignWebsiteRole(12, 123, 1);
		RoleDao.getInstance().assignWebsiteRole(23, 123, 4);
		RoleDao.getInstance().assignWebsiteRole(34, 123, 2);

		// website: Twitter
		RoleDao.getInstance().assignWebsiteRole(23, 234, 1);
		RoleDao.getInstance().assignWebsiteRole(34, 234, 4);
		RoleDao.getInstance().assignWebsiteRole(12, 234, 2);

		// website: Wikipedia
		RoleDao.getInstance().assignWebsiteRole(34, 345, 1);
		RoleDao.getInstance().assignWebsiteRole(12, 345, 4);
		RoleDao.getInstance().assignWebsiteRole(23, 345, 2);

		// website: CNN
		RoleDao.getInstance().assignWebsiteRole(12, 456, 1);
		RoleDao.getInstance().assignWebsiteRole(23, 456, 4);
		RoleDao.getInstance().assignWebsiteRole(34, 456, 2);

		// website: CNET
		RoleDao.getInstance().assignWebsiteRole(23, 567, 1);
		RoleDao.getInstance().assignWebsiteRole(34, 567, 4);
		RoleDao.getInstance().assignWebsiteRole(12, 567, 2);

		// website: Gizmodo
		RoleDao.getInstance().assignWebsiteRole(34, 678, 1);
		RoleDao.getInstance().assignWebsiteRole(12, 678, 4);
		RoleDao.getInstance().assignWebsiteRole(23, 678, 2);

		// Assign Page Role

		// Page: Home
		RoleDao.getInstance().assignPageRole(12, 123, 4);
		RoleDao.getInstance().assignPageRole(23, 123, 5);
		RoleDao.getInstance().assignPageRole(34, 123, 3);

		// Page: About
		RoleDao.getInstance().assignPageRole(23, 234, 4);
		RoleDao.getInstance().assignPageRole(34, 234, 5);
		RoleDao.getInstance().assignPageRole(12, 234, 3);

		// Page: Contact
		RoleDao.getInstance().assignPageRole(34, 345, 4);
		RoleDao.getInstance().assignPageRole(12, 345, 5);
		RoleDao.getInstance().assignPageRole(23, 345, 3);

		// Page: Preferences
		RoleDao.getInstance().assignPageRole(12, 456, 4);
		RoleDao.getInstance().assignPageRole(23, 456, 5);
		RoleDao.getInstance().assignPageRole(34, 456, 3);

		// Page: Profile
		RoleDao.getInstance().assignPageRole(23, 567, 4);
		RoleDao.getInstance().assignPageRole(34, 567, 5);
		RoleDao.getInstance().assignPageRole(12, 567, 3);

		return "Roles inserted successfully, please check database";
	}

	/**
	 * create Priviledges for Developer and Domain Objects
	 */
	@RequestMapping(value = "/api/insert/priviledges", method = RequestMethod.GET)
	private String insertPriviledges() {
		PriviledgeType create = new PriviledgeType(1, "create");
		PriviledgeType read = new PriviledgeType(2, "read");
		PriviledgeType update = new PriviledgeType(3, "update");
		PriviledgeType delete = new PriviledgeType(4, "delete");

		PriviledgeDao.getInstance().insertPrivildegType(create);
		PriviledgeDao.getInstance().insertPrivildegType(read);
		PriviledgeDao.getInstance().insertPrivildegType(update);
		PriviledgeDao.getInstance().insertPrivildegType(delete);

		// website priviledges: Facebook
		PriviledgeDao.getInstance().assignWebsitePriviledge(12, 123, 1);// create
		PriviledgeDao.getInstance().assignWebsitePriviledge(12, 123, 2);// read
		PriviledgeDao.getInstance().assignWebsitePriviledge(12, 123, 3);// update
		PriviledgeDao.getInstance().assignWebsitePriviledge(12, 123, 4);// delete

		PriviledgeDao.getInstance().assignWebsitePriviledge(23, 123, 2);// read
		PriviledgeDao.getInstance().assignWebsitePriviledge(23, 123, 3);// update

		PriviledgeDao.getInstance().assignWebsitePriviledge(34, 123, 1);// create
		PriviledgeDao.getInstance().assignWebsitePriviledge(34, 123, 2);// read
		PriviledgeDao.getInstance().assignWebsitePriviledge(34, 123, 3);// update
		PriviledgeDao.getInstance().assignWebsitePriviledge(34, 123, 4);// delete

		// website priviledges: Twitter
		PriviledgeDao.getInstance().assignWebsitePriviledge(23, 234, 1);// create
		PriviledgeDao.getInstance().assignWebsitePriviledge(23, 234, 2);// read
		PriviledgeDao.getInstance().assignWebsitePriviledge(23, 234, 3);// update
		PriviledgeDao.getInstance().assignWebsitePriviledge(23, 234, 4);// delete

		PriviledgeDao.getInstance().assignWebsitePriviledge(34, 234, 2);// read
		PriviledgeDao.getInstance().assignWebsitePriviledge(34, 234, 3);// update

		PriviledgeDao.getInstance().assignWebsitePriviledge(12, 234, 1);// create
		PriviledgeDao.getInstance().assignWebsitePriviledge(12, 234, 2);// read
		PriviledgeDao.getInstance().assignWebsitePriviledge(12, 234, 3);// update
		PriviledgeDao.getInstance().assignWebsitePriviledge(12, 234, 4);// delete

		// website priviledges: Wikipedia
		PriviledgeDao.getInstance().assignWebsitePriviledge(34, 345, 1);// create
		PriviledgeDao.getInstance().assignWebsitePriviledge(34, 345, 2);// read
		PriviledgeDao.getInstance().assignWebsitePriviledge(34, 345, 3);// update
		PriviledgeDao.getInstance().assignWebsitePriviledge(34, 345, 4);// delete

		PriviledgeDao.getInstance().assignWebsitePriviledge(12, 345, 2);// read
		PriviledgeDao.getInstance().assignWebsitePriviledge(12, 345, 3);// update

		PriviledgeDao.getInstance().assignWebsitePriviledge(23, 345, 1);// create
		PriviledgeDao.getInstance().assignWebsitePriviledge(23, 345, 2);// read
		PriviledgeDao.getInstance().assignWebsitePriviledge(23, 345, 3);// update
		PriviledgeDao.getInstance().assignWebsitePriviledge(23, 345, 4);// delete

		// website priviledges: CNN
		PriviledgeDao.getInstance().assignWebsitePriviledge(12, 456, 1);// create
		PriviledgeDao.getInstance().assignWebsitePriviledge(12, 456, 2);// read
		PriviledgeDao.getInstance().assignWebsitePriviledge(12, 456, 3);// update
		PriviledgeDao.getInstance().assignWebsitePriviledge(12, 456, 4);// delete

		PriviledgeDao.getInstance().assignWebsitePriviledge(23, 456, 2);// read
		PriviledgeDao.getInstance().assignWebsitePriviledge(23, 456, 3);// update

		PriviledgeDao.getInstance().assignWebsitePriviledge(34, 456, 1);// create
		PriviledgeDao.getInstance().assignWebsitePriviledge(34, 456, 2);// read
		PriviledgeDao.getInstance().assignWebsitePriviledge(34, 456, 3);// update
		PriviledgeDao.getInstance().assignWebsitePriviledge(34, 456, 4);// delete

		// website priviledges: CNET
		PriviledgeDao.getInstance().assignWebsitePriviledge(23, 567, 1);// create
		PriviledgeDao.getInstance().assignWebsitePriviledge(23, 567, 2);// read
		PriviledgeDao.getInstance().assignWebsitePriviledge(23, 567, 3);// update
		PriviledgeDao.getInstance().assignWebsitePriviledge(23, 567, 4);// delete

		PriviledgeDao.getInstance().assignWebsitePriviledge(34, 567, 2);// read
		PriviledgeDao.getInstance().assignWebsitePriviledge(34, 567, 3);// update

		PriviledgeDao.getInstance().assignWebsitePriviledge(12, 567, 1);// create
		PriviledgeDao.getInstance().assignWebsitePriviledge(12, 567, 2);// read
		PriviledgeDao.getInstance().assignWebsitePriviledge(12, 567, 3);// update
		PriviledgeDao.getInstance().assignWebsitePriviledge(12, 567, 4);// delete

		// website priviledges: Gizmodo
		PriviledgeDao.getInstance().assignWebsitePriviledge(34, 678, 1);// create
		PriviledgeDao.getInstance().assignWebsitePriviledge(34, 678, 2);// read
		PriviledgeDao.getInstance().assignWebsitePriviledge(34, 678, 3);// update
		PriviledgeDao.getInstance().assignWebsitePriviledge(34, 678, 4);// delete

		PriviledgeDao.getInstance().assignWebsitePriviledge(12, 678, 2);// read
		PriviledgeDao.getInstance().assignWebsitePriviledge(12, 678, 3);// update

		PriviledgeDao.getInstance().assignWebsitePriviledge(23, 678, 1);// create
		PriviledgeDao.getInstance().assignWebsitePriviledge(23, 678, 2);// read
		PriviledgeDao.getInstance().assignWebsitePriviledge(23, 678, 3);// update
		PriviledgeDao.getInstance().assignWebsitePriviledge(23, 678, 4);// delete

		// Page Priviledges
		// Page: Home
		PriviledgeDao.getInstance().assignPagePriviledge(12, 123, 2);// read
		PriviledgeDao.getInstance().assignPagePriviledge(12, 123, 3);// update

		PriviledgeDao.getInstance().assignPagePriviledge(23, 123, 2);// read

		PriviledgeDao.getInstance().assignPagePriviledge(34, 123, 1);// create
		PriviledgeDao.getInstance().assignPagePriviledge(34, 123, 2);// read
		PriviledgeDao.getInstance().assignPagePriviledge(34, 123, 3);// update

		// Page: About
		PriviledgeDao.getInstance().assignPagePriviledge(23, 234, 2);// read
		PriviledgeDao.getInstance().assignPagePriviledge(23, 234, 3);// update

		PriviledgeDao.getInstance().assignPagePriviledge(34, 234, 2);// read

		PriviledgeDao.getInstance().assignPagePriviledge(12, 234, 1);// create
		PriviledgeDao.getInstance().assignPagePriviledge(12, 234, 2);// read
		PriviledgeDao.getInstance().assignPagePriviledge(12, 234, 3);// update

		// Page: Contact
		PriviledgeDao.getInstance().assignPagePriviledge(34, 345, 2);// read
		PriviledgeDao.getInstance().assignPagePriviledge(34, 345, 3);// update

		PriviledgeDao.getInstance().assignPagePriviledge(12, 345, 2);// read

		PriviledgeDao.getInstance().assignPagePriviledge(23, 345, 1);// create
		PriviledgeDao.getInstance().assignPagePriviledge(23, 345, 2);// read
		PriviledgeDao.getInstance().assignPagePriviledge(23, 345, 3);// update

		// Page: Preferences
		PriviledgeDao.getInstance().assignPagePriviledge(12, 456, 2);// read
		PriviledgeDao.getInstance().assignPagePriviledge(12, 456, 3);// update

		PriviledgeDao.getInstance().assignPagePriviledge(23, 456, 2);// read

		PriviledgeDao.getInstance().assignPagePriviledge(34, 456, 1);// create
		PriviledgeDao.getInstance().assignPagePriviledge(34, 456, 2);// read
		PriviledgeDao.getInstance().assignPagePriviledge(34, 456, 3);// update

		// Page: Profile
		PriviledgeDao.getInstance().assignPagePriviledge(23, 567, 2);// read
		PriviledgeDao.getInstance().assignPagePriviledge(23, 567, 3);// update

		PriviledgeDao.getInstance().assignPagePriviledge(34, 567, 2);// read

		PriviledgeDao.getInstance().assignPagePriviledge(12, 567, 1);// create
		PriviledgeDao.getInstance().assignPagePriviledge(12, 567, 2);// read
		PriviledgeDao.getInstance().assignPagePriviledge(12, 567, 3);// update

		return "Priviledges inserted successfully, please check database";
	}

	/**
	 * Q: Using the DAOs implemented earlier, do the following updates Update widget
	 * 1. Update the relative order of widget head345 on the page so that it's new
	 * order is 3. Note that the other widget's order needs to update as well 
	 * 2. Update page - Append 'CNET - ' to the beginning of all CNET's page titles 
	 * 3. Update roles - Swap Charlie's and Bob's role in CNET's Home page
	 */
	@RequestMapping(value = "/api/all/updates", method = RequestMethod.GET)
	private String allUpdates() {
		return "Successfully updated the records, please check database";
	}
	
	/**
	 * 	Q: Using the DAOs implemented earlier, do the following deletes
	 * 	1. Delete widget - Remove the last widget in the Contact page. The last widget is the one with the highest value in the order field
	 *	2. Delete page - Remove the last updated page in Wikipedia
	 * 	3. Delete website - Remove the CNET web site, as well as all related roles and privileges relating developers to the Website and Pages
	 */
	@RequestMapping(value = "/api/all/deletes", method = RequestMethod.GET)
	private String allDeletes() {
		// 1. Delete widget - Remove the last widget in the Contact page. The last widget is the one with the highest value in the order field
		int contactPageId = PageDao.getInstance().findPageIdByPageTitle("contact");
		Collection<Widget> widgetsPerPage = WidgetDao.getInstance().findWidgetsForPage(contactPageId);
		Widget maxOrderWidget = null;
		int maxOrder = 0;
		for (Widget w: widgetsPerPage) {
			if(maxOrder>= w.getWidgetOrder()) {
				maxOrderWidget = w;
			}
		}
		WidgetDao.getInstance().deleteWidget(maxOrderWidget.getId());
		
		
		// 2. Delete page - Remove the last updated page in Wikipedia
		Collection<Page> pagesOfWikipedia = WebsiteDao.getInstance().findWebsiteByName("Wikipedia").getPages();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Page lastUpdatedPage = null;
		for (Page p: pagesOfWikipedia) {
			if(lastUpdatedPage == null) {
			lastUpdatedPage = p;
			} else
				try {
					if(sdf.parse(p.getUpdated().toString()).after(sdf.parse(lastUpdatedPage.getUpdated().toString()))){
						lastUpdatedPage = p;	
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}
		}
		
		PageDao.getInstance().deletePage(lastUpdatedPage.getId());
		
		return "Successfully deleted the records, please check database";
	}

}
