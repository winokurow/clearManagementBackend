package de.zottig.clean.web.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;

import lombok.Data;

@Data
public class TaskDto {

	private Long id;

	@NotNull
	private String groupname;

	@NotNull
	private String name;

	@NotNull
	private String description;

	@NotNull
	@Pattern(message = "Period Format is false", regexp = "([-+]?)P(?:([-+]?[0-9]+)Y)?(?:([-+]?[0-9]+)M)?(?:([-+]?[0-9]+)W)?(?:([-+]?[0-9]+)D)?")
	private String shedule;

	@NotNull
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	private LocalDateTime nextRun;

	@NotNull
	private int priority;

	@NotNull
	private int complexity;

	private String room;

	private String assignedTo;
	
	
}