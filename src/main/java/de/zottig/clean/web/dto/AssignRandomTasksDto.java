package de.zottig.clean.web.dto;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class AssignRandomTasksDto {

	@NotNull
	private int member;

	@NotNull
	private int minimalTotalComplexity;

}