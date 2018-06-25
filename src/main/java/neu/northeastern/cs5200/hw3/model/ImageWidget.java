package neu.northeastern.cs5200.hw3.model;

import javax.persistence.Entity;

@Entity(name = "ImageWidget")
public class ImageWidget extends Widget{

	private String src;

	// Getters and Setters

	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}
	 
	public ImageWidget() {}
	
	public ImageWidget(String name, int order, int width, int height, String src) {
		setName(name);
		setWidgetOrder(order);
		setWidth(width);
		setHeight(height);
		setSrc(src);
	}
	
}
