package neu.northeastern.cs5200.hw3.model;

import javax.persistence.Entity;

@Entity(name = "HtmlWidget")
public class HtmlWidget extends Widget {

	private String html;

	// Getters and Setters

	public String getHtml() {
		return html;
	}

	public void setHtml(String html) {
		this.html = html;
	}

	public HtmlWidget() {
	}

	public HtmlWidget(String name, String text, int order) {
		setName(name);
		setText(text);
		setWidgetOrder(order);
	
	}

}
