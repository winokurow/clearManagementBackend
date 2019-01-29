package de.zottig.clean.web.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;

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

	public String getRoom() {
		return room;
	}

	public void setRoom(String room) {
		this.room = room;
	}

	public TaskDto() {
		super();
	}

	public TaskDto(final String name) {
		super();
		this.name = name;
	}

	//

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getShedule() {
		return shedule;
	}

	public void setShedule(String shedule) {
		this.shedule = shedule;
	}

	public LocalDateTime getNextRun() {
		return nextRun;
	}

	public void setNextRun(LocalDateTime nextRun) {
		this.nextRun = nextRun;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public String getGroupname() {
		return groupname;
	}

	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}

	public int getComplexity() {
		return complexity;
	}

	public void setComplexity(int complexity) {
		this.complexity = complexity;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}

		final TaskDto tasks = (TaskDto) obj;
		if (!id.equals(tasks.id)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		DateTimeFormatter formatter = DateTimeFormatter
				.ofPattern("yyyy-MM-dd HH:mm:ss");
		final StringBuilder builder = new StringBuilder();
		builder.append("Task [name=").append(name).append("]")
				.append("Task [group=").append(groupname).append("]")
				.append("[id=").append(id).append("]").append("[description=")
				.append(description).append("]").append("[shedule=")
				.append(shedule).append("]").append("[nextRun=")
				.append(nextRun.format(formatter)).append("]")
				.append("[priority=").append(priority).append("][complexity=")
				.append(complexity).append("][room=").append(room)
				.append("][id=").append(id).append("]");
		return builder.toString();
	}

}