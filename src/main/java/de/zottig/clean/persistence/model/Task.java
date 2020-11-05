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
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import de.zottig.clean.persistence.LocalDateTimeConverter;
import lombok.Data;

@Entity
@Data
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

	@OneToMany(cascade = CascadeType.ALL, targetEntity = Member.class)
	@JoinColumn(name = "assignedTo")
	private Member assignedTo;
	
	

}