package de.zottig.clean.web.dto;

public class RoleDto {

	private String name;

	public RoleDto() {
		super();
	}

	public RoleDto(final String name) {
		super();
		this.name = name;
	}

	//

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}
}