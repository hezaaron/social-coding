package org.onlinetest.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Formula;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "user")
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@NotEmpty
	@Column(name="first_name", nullable=false)
	private String firstName;
	
	@NotEmpty
	@Column(name="last_name", nullable=false)
	private String lastName;
	
	@Generated(value=GenerationTime.ALWAYS)
	@Formula(value="concat(firstName, '', lastName)")
	@Column(name="full_name", nullable=false)
	private String fullName;
	
	@NotEmpty
	@Column(name="email", nullable=false)
	private String email;

	@NotEmpty
	@Column(name = "user_name", unique=true, nullable=false)
	private String userName;

	@NotEmpty
	@Column(name = "user_password", nullable=false)
	private String password;

	@NotEmpty
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name="user_user_profile", joinColumns={@JoinColumn(name="user_id")},
				inverseJoinColumns={@JoinColumn(name="user_profile_id")})
	private Set<UserProfile> userProfiles = new HashSet<UserProfile>();

	
	public Integer getId() {
		return this.id;
	}

	public void setId(final Integer id) {
		this.id = id;
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

	public String getFullName() {
		return fullName;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String userPassword) {
		this.password = userPassword;
	}
	
	public Set<UserProfile> getUserProfiles() {
		return userProfiles;
	}

	public void setUserProfiles(Set<UserProfile> userProfiles) {
		this.userProfiles = userProfiles;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof User)) {
			return false;
		}
		User other = (User) obj;
		if (id != null) {
			if (!id.equals(other.id)) {
				return false;
			}
		}
		if (userName != null) {
			if (!userName.equals(other.userName)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((userName == null) ? 0 : userName.hashCode());
		return result;
	}

	@Override
	public String toString() {
		return "User [id="+id+ ", userName="+userName+ ", password="+password+ ", firstName="+firstName+
				", lastName="+lastName+ ", email="+email+"]";
	}

}