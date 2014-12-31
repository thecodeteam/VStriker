package  com.emccode.vstriker.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

//@author Sanjeev Chauhan

public class Account {
	private final StringProperty name;
	private final StringProperty location;
	
	//Default constructor
	public Account() {
		this(null, null);
	}
	
	//Constructor with initial data
	public Account(String name, String location) {
		this.name = new SimpleStringProperty(name);
		this.location = new SimpleStringProperty(location);
	}
	
	public String getName() {
		return name.get();
	}
	
	public void setName(String name) {
		this.name.set(name);
	}
	
	public StringProperty nameProperty() {
		return name;
	}
	
	public String getLocation() {
		return location.get();
	}
	
	public void setLocation(String location) {
		this.location.set(location);
	}
	
	public StringProperty locationProperty() {
		return location;
	}
}
