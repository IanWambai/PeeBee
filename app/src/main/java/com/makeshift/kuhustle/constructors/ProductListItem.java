package com.makeshift.kuhustle.constructors;

public class ProductListItem {
	private String name;
	private String description;
	private String price;
	private int quantity;
	private String imageUrl;
	private String id;
	private int threshold;

	public ProductListItem(String name, String description, String price, int quantity, String imageUrl, String id, int threshold) {
		this.name = name;
		this.description = description;
		this.setPrice(price);
		this.setQuantity(quantity);
		this.imageUrl = imageUrl;
		this.setId(id);
		this.threshold = threshold;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getThreshold() {
		return threshold;
	}

	public void setThreshold(int threshold) {
		this.threshold = threshold;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
}