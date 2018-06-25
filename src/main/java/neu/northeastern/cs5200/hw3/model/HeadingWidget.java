package neu.northeastern.cs5200.hw3.model;

import javax.persistence.Entity;

@Entity(name = "HeadingWidget")
public class HeadingWidget extends Widget {

	private int size;

	// Getters and Setters

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public HeadingWidget() {

	}

	public HeadingWidget(String name, String text, int order) {
		setName(name);
		setText(text);
		setWidgetOrder(order);
	}

}
