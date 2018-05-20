package de.zottig.clean.service;

import de.zottig.clean.persistence.model.Household;
import de.zottig.clean.web.error.HouseholdAlreadyExistException;

public interface IHouseholdService {

	Household registerNewHousehold(final String householdname) throws HouseholdAlreadyExistException;

	boolean isHouseholdExists(String householdname);
}