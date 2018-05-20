package de.zottig.clean.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.zottig.clean.persistence.dao.HouseholdRepository;
import de.zottig.clean.persistence.model.Household;
import de.zottig.clean.web.error.HouseholdAlreadyExistException;

@Service
@Transactional
public class HouseholdServiceImpl implements IHouseholdService {

	@Autowired
	private HouseholdRepository repository;

	public static String APP_NAME = "Clean Manager";

	// API

	@Override
	public Household registerNewHousehold(final String householdname) throws HouseholdAlreadyExistException {
		if (isHouseholdExists(householdname)) {
			throw new HouseholdAlreadyExistException("There is a household with that name: " + householdname);
		}
		final Household household = new Household();
		household.setName(householdname);
		return repository.save(household);
	}

	@Override
	public boolean isHouseholdExists(String householdname) {
		return repository.findOneByName(householdname) != null;
	}
}