package de.zottig.clean.web.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import de.zottig.clean.validation.PasswordMatches;
import de.zottig.clean.validation.ValidEmail;
import de.zottig.clean.validation.ValidPassword;

@PasswordMatches
public class GroupDto {
	@NotNull
	@Size(min = 1, message = "{Size.userDto.firstName}")
	private String firstname;

	@NotNull
	@Size(min = 1, message = "{Size.userDto.lastName}")
	private String lastname;

	@ValidPassword
	private String password;

	@NotNull
	@Size(min = 1)
	private String confirmpassword;

	@NotNull
	@ValidEmail
	@Size(min = 1, message = "{Size.userDto.email}")
	private String email;

	@NotNull
	private Boolean isAdmin;

	private Integer role;

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("UserDto [firstName=").append(firstname).append(", lastName=").append(lastname)
				.append(", password=").append(password).append(", matchingPassword=").append(password)
				.append(", email=").append(email).append(", isUsing2FA=").append(", role=").append(role)
				.append(", isAdmin=").append(isAdmin).append("]");
		return builder.toString();
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstName(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastName) {
		this.lastname = lastName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmpassword() {
		return confirmpassword;
	}

	public void setConfirmpassword(String confirmpassword) {
		this.confirmpassword = confirmpassword;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getRole() {
		return role;
	}

	public void setRole(Integer role) {
		this.role = role;
	}

	public Boolean getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(Boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

}
