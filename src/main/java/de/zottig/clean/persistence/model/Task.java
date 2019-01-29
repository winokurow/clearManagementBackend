package de.zottig.clean.persistence.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import de.zottig.clean.persistence.LocalDateTimeConverter;

@Entity
public class Task {

	@Id
	@Column(unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotNull
	private String groupname;

	@NotNull
	private String name;

	@NotNull
	private String description;

	@NotNull
	private String shedule;

	@NotNull
	@Convert(converter = LocalDateTimeConverter.class)
	private LocalDateTime nextRun;

	@NotNull
	private int priority;

	@NotNull
	private int complexity;

	@ManyToOne(cascade = CascadeType.ALL, targetEntity = Household.class)
	@JoinColumn(name = "household_id")
	@NotNull
	private Household household;

	@NotNull
	private int initial;

	private String room;

	public Task() {
		super();
	}

	public Task(final String name) {
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

	public Household getHousehold() {
		return household;
	}

	public void setHousehold(Household household) {
		this.household = household;
	}

	public int getInitial() {
		return initial;
	}

	public void setInitial(int initial) {
		this.initial = initial;
	}

	public String getRoom() {
		return room;
	}

	public void setRoom(String room) {
		this.room = room;
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

		final Task tasks = (Task) obj;
		if (!name.equals(tasks.name) || !household.equals(tasks.household)) {
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
				.append(complexity).append("]").append("[household=")
				.append(household).append("]").append("[id=").append(id)
				.append("]").append("[initial=").append(initial).append("]")
				.append("[room=").append(room).append("]");
		return builder.toString();
	}

}