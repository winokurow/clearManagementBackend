package de.zottig.clean.persistence.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "members")
public class Member {

	@Id
	@Column(unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String firstName;

	private String lastName;

	private String email;

	@ManyToOne(cascade = CascadeType.ALL, targetEntity = Household.class)
	@JoinColumn(name = "household_id")
	private Household household;

}