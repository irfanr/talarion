package com.mascova.talarion.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.mascova.talarion.Application;
import com.mascova.talarion.domain.Group;
import com.mascova.talarion.domain.PersistentToken;
import com.mascova.talarion.domain.User;
import com.mascova.talarion.repository.PersistentTokenRepository;
import com.mascova.talarion.repository.UserRepository;
import com.mascova.talarion.service.util.RandomUtil;

/**
 * Test class for the UserResource REST controller.
 *
 * @see UserService
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
@Transactional
public class UserServiceTest {

  @Inject
  private PersistentTokenRepository persistentTokenRepository;

  @Inject
  private UserRepository userRepository;

  @Inject
  private UserService userService;

  @Test
  public void testRemoveOldPersistentTokens() {
    User admin = userRepository.findOneByLogin("admin").get();
    int existingCount = persistentTokenRepository.findByUser(admin).size();
    generateUserToken(admin, "1111-1111", new LocalDate());
    LocalDate now = new LocalDate();
    generateUserToken(admin, "2222-2222", now.minusDays(32));
    assertThat(persistentTokenRepository.findByUser(admin)).hasSize(existingCount + 2);
    userService.removeOldPersistentTokens();
    assertThat(persistentTokenRepository.findByUser(admin)).hasSize(existingCount + 1);
  }

  @Test
  public void assertThatUserMustExistToResetPassword() {

    Optional<User> maybeUser = userService.requestPasswordReset("john.doe@localhost");
    assertThat(maybeUser.isPresent()).isFalse();

    maybeUser = userService.requestPasswordReset("admin@localhost");
    assertThat(maybeUser.isPresent()).isTrue();

    assertThat(maybeUser.get().getEmail()).isEqualTo("admin@localhost");
    assertThat(maybeUser.get().getResetDate()).isNotNull();
    assertThat(maybeUser.get().getResetKey()).isNotNull();

  }

  @Test
  public void assertThatResetKeyMustNotBeOlderThan24Hours() {

    Group group = new Group();
    group.setId(new Long(1));
    User user = userService.createUserInformation("johndoe", "johndoe", "John", "Doe",
        "john.doe@localhost", "en-US", group);

    DateTime daysAgo = DateTime.now().minusHours(25);
    String resetKey = RandomUtil.generateResetKey();
    user.setActivated(true);
    user.setResetDate(daysAgo);
    user.setResetKey(resetKey);

    userRepository.save(user);

    Optional<User> maybeUser = userService.completePasswordReset("johndoe2", user.getResetKey());

    assertThat(maybeUser.isPresent()).isFalse();

    userRepository.delete(user);

  }

  @Test
  public void assertThatResetKeyMustBeValid() {

    Group group = new Group();
    group.setId(new Long(1));
    User user = userService.createUserInformation("johndoe", "johndoe", "John", "Doe",
        "john.doe@localhost", "en-US", group);

    DateTime daysAgo = DateTime.now().minusHours(25);
    user.setActivated(true);
    user.setResetDate(daysAgo);
    user.setResetKey("1234");

    userRepository.save(user);

    Optional<User> maybeUser = userService.completePasswordReset("johndoe2", user.getResetKey());

    assertThat(maybeUser.isPresent()).isFalse();

    userRepository.delete(user);

  }

  @Test
  public void assertThatUserCanResetPassword() {

    Group group = new Group();
    group.setId(new Long(1));
    User user = userService.createUserInformation("johndoe", "johndoe", "John", "Doe",
        "john.doe@localhost", "en-US", group);

    String oldPassword = user.getPassword();

    DateTime daysAgo = DateTime.now().minusHours(2);
    String resetKey = RandomUtil.generateResetKey();
    user.setActivated(true);
    user.setResetDate(daysAgo);
    user.setResetKey(resetKey);

    userRepository.save(user);

    Optional<User> maybeUser = userService.completePasswordReset("johndoe2", user.getResetKey());

    assertThat(maybeUser.isPresent()).isTrue();
    assertThat(maybeUser.get().getResetDate()).isNull();
    assertThat(maybeUser.get().getResetKey()).isNull();
    assertThat(maybeUser.get().getPassword()).isNotEqualTo(oldPassword);

    userRepository.delete(user);

  }

  @Test
  public void testFindNotActivatedUsersByCreationDateBefore() {
    userService.removeNotActivatedUsers();
    DateTime now = new DateTime();
    List<User> users = userRepository.findAllByActivatedIsFalseAndCreatedDateBefore(now
        .minusDays(3));
    assertThat(users).isEmpty();
  }

  private void generateUserToken(User user, String tokenSeries, LocalDate localDate) {
    PersistentToken token = new PersistentToken();
    token.setSeries(tokenSeries);
    token.setUser(user);
    token.setTokenValue(tokenSeries + "-data");
    token.setTokenDate(localDate);
    token.setIpAddress("127.0.0.1");
    token.setUserAgent("Test agent");
    persistentTokenRepository.saveAndFlush(token);
  }
}
