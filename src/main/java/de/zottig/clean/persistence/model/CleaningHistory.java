package de.zottig.clean.persistence.model;

import java.time.LocalDateTime;

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

import org.apache.commons.lang3.builder.EqualsBuilder;

import de.zottig.clean.persistence.LocalDateTimeConverter;

@Entity
public class CleaningHistory {

	@Id
	@Column(unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotNull
	private String taskname;

	@NotNull
	private String action;

	@NotNull
	@Convert(converter = LocalDateTimeConverter.class)
	private LocalDateTime timestamp;

	@NotNull
	private int complexity;

	@ManyToOne(cascade = CascadeType.ALL, targetEntity = Member.class)
	@JoinColumn(name = "member_id")
	@NotNull
	private Member member;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTaskname() {
		return taskname;
	}

	public void setTaskname(String taskname) {
		this.taskname = taskname;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public int getComplexity() {
		return complexity;
	}

	public void setComplexity(int complexity) {
		this.complexity = complexity;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((action == null) ? 0 : action.hashCode());
		result = prime * result + complexity;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((member == null) ? 0 : member.hashCode());
		result = prime * result
				+ ((taskname == null) ? 0 : taskname.hashCode());
		result = prime * result
				+ ((timestamp == null) ? 0 : timestamp.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj == this) {
			return true;
		}
		if (obj.getClass() != getClass()) {
			return false;
		}
		CleaningHistory rhs = (CleaningHistory) obj;
		return new EqualsBuilder().appendSuper(super.equals(obj))
				.append(action, rhs.action).append(complexity, rhs.complexity)
				.append(id, rhs.id).append(member, rhs.member)
				.append(taskname, rhs.taskname).append(timestamp, rhs.timestamp)
				.isEquals();
	}

	@Override
	public String toString() {
		return "CleaningHistory [id=" + id + ", taskname=" + taskname
				+ ", action=" + action + ", timestamp=" + timestamp
				+ ", complexity=" + complexity + ", member=" + member + "]";
	}

}