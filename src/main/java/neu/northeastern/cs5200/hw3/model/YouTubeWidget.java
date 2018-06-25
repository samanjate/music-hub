package neu.northeastern.cs5200.hw3.model;

import javax.persistence.Entity;

@Entity(name = "YouTubeWidget")
public class YouTubeWidget extends Widget{

	private String url;
	private Boolean shareble;
	private Boolean expandable;
	
	// Getters and Setters

	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Boolean getShareble() {
		return shareble;
	}
	public void setShareble(Boolean shareble) {
		this.shareble = shareble;
	}
	public Boolean getExpandable() {
		return expandable;
	}
	public void setExpandable(Boolean expandable) {
		this.expandable = expandable;
	}
	
	
public YouTubeWidget() {}
	
	public YouTubeWidget(String name, int order, int width, int height, String url) {
		setName(name);
		setWidgetOrder(order);
		setWidth(width);
		setHeight(height);
		setUrl(url);
	}
	
}
