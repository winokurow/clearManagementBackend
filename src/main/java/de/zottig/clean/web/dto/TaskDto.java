package de.zottig.clean.web.dto;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class TaskDto {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotNull
	private String group;

	@NotNull
	private String name;

	@NotNull
	private String description;

	@NotNull
	private String shedule;

	@NotNull
	private Date nextRun;

	@NotNull
	private int priority;

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

	public Date getNextRun() {
		return nextRun;
	}

	public void setNextRun(Date nextRun) {
		this.nextRun = nextRun;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
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
		if (!tasks.equals(tasks.name)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		final StringBuilder builder = new StringBuilder();
		builder.append("Task [name=").append(name).append("]").append("Task [group=").append(group).append("]")
				.append("[id=").append(id).append("]").append("[description=").append(description).append("]")
				.append("[shedule=").append(shedule).append("]").append("[nextRun=").append(df.format(nextRun))
				.append("]").append("[priority=").append(priority).append("]").append("[id=").append(id).append("]");
		return builder.toString();
	}

}