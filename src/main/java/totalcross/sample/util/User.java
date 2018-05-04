package totalcross.sample.util;

public class User {

	private String name;
	private String phone;
	private String mail;
	private String password;
	
	public User() {
		
	}
	
	public User(String name, String phone, String mail, String password) {
		super();
		this.name = name;
		this.phone = phone;
		this.mail = mail;
		this.password = password;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}
