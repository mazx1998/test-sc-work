package com.app;

import com.app.model.Role;
import com.app.model.SeasonService;
import com.app.model.User;
import com.app.repository.RoleRepository;
import com.app.repository.SeasonServiceRepository;
import com.app.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

@SpringBootApplication
public class Application {
	private Logger log = LoggerFactory.getLogger(Application.class);

	private final RoleRepository roleRepository;

	private final SeasonServiceRepository seasonServiceRepository;

	private final UserRepository userRepository;

	private final Validator validator;

	@Autowired
	public Application(RoleRepository roleRepository,
					   SeasonServiceRepository seasonServiceRepository,
					   UserRepository userRepository,
					   Validator validator) {
		this.roleRepository = roleRepository;
		this.seasonServiceRepository = seasonServiceRepository;
		this.userRepository = userRepository;
		this.validator = validator;
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void dataBaseInit() {
		initRoles();
		initUsers();
		initSeasonServices();
	}

	private void initRoles(){
		List<Role> roles = roleRepository.findAll();
		if (roles.size() == 0) {
			// User role
			Role role = new Role();
			role.setName("USER");
			roles.add(role);
			// Admin role
			role = new Role();
			role.setName("ADMIN");
			roles.add(role);

			roles.forEach(currentRole -> {
				Set<ConstraintViolation<Role>> violations = validator.validate(currentRole);
				if (violations.size() > 0) {
					log.error("IN initRole: Role validation failed ->");
					violations.forEach(violation -> {
						log.error(violation.getMessage());
					});
				} else {
					roleRepository.save(currentRole);
				}
			});

			log.info("Roles initialization completed");
		}
	}

	private void initUsers() {
		List<User> users = userRepository.findAll();
		if (users.size() == 0) {
			// Create admin
			User user = new User();
			user.setEmail("simple@mail.ru");
			user.setLogin("admin");
			user.setPassword("$2a$10$H1blzaO3FN3PjTpzRJkVh.YZLYmpN.acqFJdmMXGEsNksQ7ijIoFi");
			user.setFirstName("first name");
			user.setFamilyName("family name");
			user.setRoles(roleRepository.findAll());
			users.add(user);
			// Create user
			user = new User();
			user.setEmail("simple@mail.ru");
			user.setLogin("user");
			user.setPassword("$2a$10$H1blzaO3FN3PjTpzRJkVh.YZLYmpN.acqFJdmMXGEsNksQ7ijIoFi");
			user.setFirstName("first name");
			user.setFamilyName("family name");
			user.setRoles(Collections.singletonList(roleRepository.findByName("USER")));
			users.add(user);

			users.forEach(currentUser -> {
				Set<ConstraintViolation<User>> violations = validator.validate(currentUser);
				if (violations.size() > 0) {
					log.error("IN initUsers: User validation failed ->");
					violations.forEach(violation -> {
						log.error(violation.getMessage());
					});
				} else {
					userRepository.save(currentUser);
				}
			});
			log.info("Users initialization completed");
		}
	}

	private void initSeasonServices() {
		List<SeasonService> seasonServices = seasonServiceRepository.findAll();
		if (seasonServices.size() == 0) {
			final Integer USAGE_LIMIT = 6000;
			final String SEASON_SERVICE_NAME = "Выдача охотбилетов единого федерального образца";
			final long THREE_MONTH_MILLISECONDS = 7884000000L;
			final Timestamp START_DATE = new Timestamp(new Date().getTime());
			final Timestamp END_DATE = new Timestamp(new Date().getTime() + THREE_MONTH_MILLISECONDS);

			SeasonService service = new SeasonService();
			service.setName(SEASON_SERVICE_NAME);
			service.setUsageLimit(USAGE_LIMIT);
			service.setUsed(0);
			service.setStartDate(START_DATE);// current date
			service.setEndDate(END_DATE); // current date + 3 month
			seasonServices.add(service);

			seasonServices.forEach(currentService -> {
				Set<ConstraintViolation<SeasonService>> violations = validator.validate(currentService);
				if (violations.size() > 0) {
					log.error("IN initSeasonServices: SeasonServices validation failed ->");
					violations.forEach(violation -> {
						log.error(violation.getMessage());
					});
				} else {
					seasonServiceRepository.save(currentService);
				}
			});

			log.info("Services initialization completed");
		}
	}
}
