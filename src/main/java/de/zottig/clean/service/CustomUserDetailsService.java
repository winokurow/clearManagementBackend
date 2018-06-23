package de.zottig.clean.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import de.zottig.clean.persistence.dao.RoleRepository;
import de.zottig.clean.persistence.dao.UserRepository;
import de.zottig.clean.persistence.model.Role;
import de.zottig.clean.persistence.model.User;
import de.zottig.clean.web.dto.GroupDto;
import de.zottig.clean.web.error.UserAlreadyExistException;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	private final UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	public CustomUserDetailsService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) {
		User user = userRepository.findByEmail(username);
		if (user == null) {
			throw new UsernameNotFoundException(
					String.format("User %s does not exist!", username));
		}
		return new UserRepositoryUserDetails(user);
	}

	public void registerNewUserAccount(final GroupDto accountDto) {
		if (emailExist(accountDto.getEmail())) {
			throw new UserAlreadyExistException(
					"There is an account with that email adress: "
							+ accountDto.getEmail());
		}
		final User user = new User();

		user.setPassword(passwordEncoder.encode(accountDto.getPassword()));
		user.setEmail(accountDto.getEmail());

		List<Role> roles = new ArrayList<>();
		Role member = roleRepository.findByName("ROLE_USER");
		roles.add(member);
		if (accountDto.getIsAdmin()) {
			Role admin = roleRepository.findByName("ROLE_ADMIN");
			roles.add(admin);
		}
		user.setRoles(roles);
		userRepository.save(user);
	}

	public boolean emailExist(final String email) {
		return userRepository.findByEmail(email) != null;
	}

	private static final class UserRepositoryUserDetails extends User
			implements
				UserDetails {

		private static final long serialVersionUID = 1L;

		private UserRepositoryUserDetails(User user) {
			super(user);
		}

		@Override
		public Collection<? extends GrantedAuthority> getAuthorities() {
			return getRoles();
		}

		@Override
		public String getUsername() {
			return getEmail();
		}

		@Override
		public boolean isAccountNonExpired() {
			return true;
		}

		@Override
		public boolean isAccountNonLocked() {
			return true;
		}

		@Override
		public boolean isCredentialsNonExpired() {
			return true;
		}

		@Override
		public boolean isEnabled() {
			return true;
		}

	}

}