package de.zottig.clean.persistence.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class AssignedTask {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne(cascade = CascadeType.ALL, targetEntity = Task.class)
	@JoinColumn(name = "task_id")
	@NotNull
	private Task task;

	@ManyToOne(cascade = CascadeType.ALL, targetEntity = Household.class)
	@JoinColumn(name = "household_id")
	@NotNull
	private Household household;

	@ManyToOne(cascade = CascadeType.ALL, targetEntity = User.class)
	@JoinColumn(name = "user_id")
	private User assignedUser;

	public AssignedTask() {
		super();
	}

	//

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

		final AssignedTask tasks = (AssignedTask) obj;
		if (!task.equals(tasks.task) || !household.equals(tasks.household)
				|| !assignedUser.equals(tasks.assignedUser)) {
			return false;
		}
		return true;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public Household getHousehold() {
		return household;
	}

	public void setHousehold(Household household) {
		this.household = household;
	}

	public User getUser() {
		return assignedUser;
	}

	public void setAssignedUser(User user) {
		this.assignedUser = user;
	}

	@Override
	public String toString() {
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		final StringBuilder builder = new StringBuilder();
		builder.append("Task [task=").append(task).append("]").append("[id=").append(id).append("]").append("[family=")
				.append(household).append("]").append("[assigned user=").append(assignedUser).append("]");
		return builder.toString();
	}
}