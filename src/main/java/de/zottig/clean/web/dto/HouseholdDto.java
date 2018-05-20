package de.zottig.clean.web.dto;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class HouseholdDto {
	@NotNull
	@Size(min = 5, message = "{size.household.name}")
	private String householdname;

	@NotNull
	private List<GroupDto> members;

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("HouseholdDto [householdname=").append(householdname).append("]");
		return builder.toString();
	}

	public String getHouseholdname() {
		return householdname;
	}

	public void setHouseholdname(String householdname) {
		this.householdname = householdname;
	}

	public List<GroupDto> getMembers() {
		return members;
	}

	public void setMembers(List<GroupDto> members) {
		this.members = members;
	}

}
