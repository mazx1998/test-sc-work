package com.app;

import com.app.model.ProvidedService;
import com.app.model.Role;
import com.app.model.SeasonService;
import com.app.model.User;
import com.app.repository.ProvidedServiceRepository;
import com.app.repository.RoleRepository;
import com.app.repository.SeasonServiceRepository;
import com.app.repository.UserRepository;
import com.app.service.ProvidedServicesService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.Assert;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Максим Зеленский
 */

@SpringBootTest
class ProvidedServicesServiceTest {

    private ProvidedServicesService providedServicesService;
    private RoleRepository roleRepository;
    private UserRepository userRepository;
    private SeasonServiceRepository seasonServiceRepository;
    private ProvidedServiceRepository providedServiceRepository;

    @Autowired
    ProvidedServicesServiceTest(ProvidedServicesService providedServicesService, RoleRepository roleRepository,
                                UserRepository userRepository, SeasonServiceRepository seasonServiceRepository,
                                ProvidedServiceRepository providedServiceRepository) {
        this.providedServicesService = providedServicesService;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.seasonServiceRepository = seasonServiceRepository;
        this.providedServiceRepository = providedServiceRepository;
    }

    @Test
    void findAllTest() {
        // Arrange role
        Role role = new Role();
        role.setName("TEST_ROLE");
        roleRepository.save(role);
        // Arrange user
        User testUser = new User();
        testUser.setEmail("test@mail.ru");
        testUser.setLogin("testUser");
        testUser.setPassword("password");
        testUser.setFirstName("first name");
        testUser.setFamilyName("family name");
        testUser.setRoles(Collections.singletonList(roleRepository.findByName("TEST_ROLE")));
        userRepository.save(testUser);
        //Arrange service
        Timestamp currentDate = new Timestamp(System.currentTimeMillis());
        SeasonService seasonService = new SeasonService();
        seasonService.setName("service");
        seasonService.setUsageLimit(100);
        seasonService.setUsed(0);
        seasonService.setStartDate(currentDate);// current date
        seasonService.setEndDate(new Timestamp(System.currentTimeMillis() + 7884000000L));
        seasonServiceRepository.save(seasonService);
        //Create provided services
        List<ProvidedService> providedServiceList = new LinkedList<>();
        for (int i = 0; i < 10; i++) {
            ProvidedService providedService = new ProvidedService();
            providedService.setSerialNumber(i);
            providedService.setProvisionDate(null);
            providedService.setCreationDate(currentDate);
            providedService.setUser(testUser);
            providedService.setService(seasonService);
            providedServiceList.add(providedService);
            providedServiceRepository.save(providedService);
        }


        List<ProvidedService> providedServices = providedServicesService.findAll(PageRequest.of(0, 2));
        Integer providedServicesSize = providedServices.size();
        Assert.isTrue(providedServices.size() == 2, "Found provided services list must have size of 2 but was " + providedServicesSize);

        providedServices = providedServicesService.findAll(PageRequest.of(0, 10));
        Assert.isTrue(providedServices.size() == 10, "Found provided services list must have size of 10 but was " + providedServicesSize);

        providedServices = providedServicesService.findAll(PageRequest.of(1, 10));
        Assert.isTrue(providedServices.size() == 0, "Found provided services list must have size of 0 but was " + providedServicesSize);


        providedServiceRepository.deleteAll(providedServiceList);
        seasonServiceRepository.delete(seasonService);
        userRepository.delete(testUser);
        roleRepository.delete(role);
    }

    @Test
    void findByUserTest(){
        // Arrange role
        Role role = new Role();
        role.setName("TEST_ROLE");
        roleRepository.save(role);
        // Arrange users
        User firstUser = new User();
        firstUser.setEmail("test@mail.ru");
        firstUser.setLogin("firstUser");
        firstUser.setPassword("password");
        firstUser.setFirstName("first name");
        firstUser.setFamilyName("family name");
        firstUser.setRoles(Collections.singletonList(roleRepository.findByName("TEST_ROLE")));
        User secondUser = new User();
        secondUser.setEmail("second@mail.ru");
        secondUser.setLogin("secondUser");
        secondUser.setPassword("second");
        secondUser.setFirstName("second");
        secondUser.setFamilyName("second");
        secondUser.setRoles(Collections.singletonList(roleRepository.findByName("TEST_ROLE")));
        userRepository.save(firstUser);
        userRepository.save(secondUser);
        //Arrange service
        Timestamp currentDate = new Timestamp(System.currentTimeMillis());
        SeasonService seasonService = new SeasonService();
        seasonService.setName("service");
        seasonService.setUsageLimit(100);
        seasonService.setUsed(0);
        seasonService.setStartDate(currentDate);// current date
        seasonService.setEndDate(new Timestamp(System.currentTimeMillis() + 7884000000L));
        seasonServiceRepository.save(seasonService);
        //Create provided services for first user
        List<ProvidedService> firstServiceList = new LinkedList<>();
        for (int i = 0; i < 5; i++) {
            ProvidedService providedService = new ProvidedService();
            providedService.setSerialNumber(i);
            providedService.setProvisionDate(null);
            providedService.setCreationDate(currentDate);
            providedService.setUser(firstUser);
            providedService.setService(seasonService);
            firstServiceList.add(providedService);
            providedServiceRepository.save(providedService);
        }
        //Create provided services for second user
        List<ProvidedService> secondServiceList = new LinkedList<>();
        for (int i = 0; i < 10; i++) {
            ProvidedService providedService = new ProvidedService();
            providedService.setSerialNumber(i);
            providedService.setProvisionDate(null);
            providedService.setCreationDate(currentDate);
            providedService.setUser(secondUser);
            providedService.setService(seasonService);
            secondServiceList.add(providedService);
            providedServiceRepository.save(providedService);
        }

        List<ProvidedService> providedServices = providedServicesService.findByUser(
                firstUser,
                PageRequest.of(0, 10));
        Integer providedServicesSize = providedServices.size();
        Assert.isTrue(providedServices.size() == 5, "Found provided services list must have size of 5 but was " + providedServicesSize);

        providedServices = providedServicesService.findByUser(
                secondUser,
                PageRequest.of(0, 10));
        providedServicesSize = providedServices.size();
        Assert.isTrue(providedServices.size() == 10, "Found provided services list must have size of 10 but was " + providedServicesSize);

        providedServiceRepository.deleteAll(firstServiceList);
        providedServiceRepository.deleteAll(secondServiceList);
        seasonServiceRepository.delete(seasonService);
        userRepository.delete(firstUser);
        userRepository.delete(secondUser);
        roleRepository.delete(role);
    }

    @Test
    void create() {
        // Arrange role
        Role role = new Role();
        role.setName("TEST_ROLE");
        roleRepository.save(role);
        // Arrange user
        User testUser = new User();
        testUser.setEmail("test@mail.ru");
        testUser.setLogin("testUser");
        testUser.setPassword("password");
        testUser.setFirstName("first name");
        testUser.setFamilyName("family name");
        testUser.setRoles(Collections.singletonList(roleRepository.findByName("TEST_ROLE")));
        userRepository.save(testUser);
        //Arrange service
        Timestamp currentDate = new Timestamp(System.currentTimeMillis());
        SeasonService seasonService = new SeasonService();
        seasonService.setName("service");
        seasonService.setUsageLimit(100);
        seasonService.setUsed(0);
        seasonService.setStartDate(currentDate);// current date
        seasonService.setEndDate(new Timestamp(System.currentTimeMillis() + 7884000000L));
        seasonServiceRepository.save(seasonService);
        //Create provided service
        ProvidedService providedService = new ProvidedService();
        providedService.setSerialNumber(1);
        providedService.setProvisionDate(null);
        providedService.setCreationDate(currentDate);
        providedService.setUser(testUser);
        providedService.setService(seasonService);
        ProvidedService createdProvidedService = providedServicesService.create(providedService);

        Assert.isTrue(createdProvidedService != null, "Created provided service must not be null");

        providedServiceRepository.delete(createdProvidedService);
        seasonServiceRepository.delete(seasonService);
        userRepository.delete(testUser);
        roleRepository.delete(role);
    }

    @Test
    void findById() {
        // Arrange role
        Role role = new Role();
        role.setName("TEST_ROLE");
        roleRepository.save(role);
        // Arrange user
        User testUser = new User();
        testUser.setEmail("test@mail.ru");
        testUser.setLogin("testUser");
        testUser.setPassword("password");
        testUser.setFirstName("first name");
        testUser.setFamilyName("family name");
        testUser.setRoles(Collections.singletonList(roleRepository.findByName("TEST_ROLE")));
        userRepository.save(testUser);
        //Arrange service
        Timestamp currentDate = new Timestamp(System.currentTimeMillis());
        SeasonService seasonService = new SeasonService();
        seasonService.setName("service");
        seasonService.setUsageLimit(100);
        seasonService.setUsed(0);
        seasonService.setStartDate(currentDate);// current date
        seasonService.setEndDate(new Timestamp(System.currentTimeMillis() + 7884000000L));
        seasonServiceRepository.save(seasonService);
        //Create provided service
        ProvidedService providedService = new ProvidedService();
        providedService.setSerialNumber(1);
        providedService.setProvisionDate(null);
        providedService.setCreationDate(currentDate);
        providedService.setUser(testUser);
        providedService.setService(seasonService);
        providedServiceRepository.save(providedService);

        ProvidedService foundProvidedService = providedServicesService.findById(providedService.getId());

        Assert.isTrue(foundProvidedService != null, "Found provided service must not be null");

        providedServiceRepository.delete(providedService);
        seasonServiceRepository.delete(seasonService);
        userRepository.delete(testUser);
        roleRepository.delete(role);
    }
}
