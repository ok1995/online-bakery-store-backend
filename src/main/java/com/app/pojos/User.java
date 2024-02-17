package com.app.pojos;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

/*
 * Creates the user_table to save user information
 */

@Entity					// Notify SC to manage the life-cycle of following type
@Table(name = "users")
public class User extends BaseEntity{	
	@Column(name = "first_name",length = 30)
	private String firstName;
	
	@Column(name = "last_name",length = 30)
	private String lastName;
	
	@Column(length = 30,unique = true)
	private String email;
	
	@Column(length = 30,nullable = false)
	private String password;
	
	@Column(length = 15)
	private String phone;
	
	@Enumerated(EnumType.STRING)		// Specifies the Role as String data-type in database 
	private Role role;
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPhone() {
		return phone;
	}
	
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public Role getRole() {
		return role;
	}
	
	public void setRole(Role role) {
		this.role = role;
	}
	
	@Override
	public String toString() {
		return "User [ID=" + getId() + ", password=" + password + ", firstName=" + firstName + ", lastName="
				+ lastName + ", email=" + email + ", phone=" + phone + ", role=" + role + "]";
	}
	
}
