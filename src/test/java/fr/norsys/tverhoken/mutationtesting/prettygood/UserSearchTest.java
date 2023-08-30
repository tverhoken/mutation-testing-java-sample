package fr.norsys.tverhoken.mutationtesting.prettygood;

import fr.norsys.tverhoken.mutationtesting.prettygood.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserSearchTest {

    private SearchUsersService searchUsersService;
    private UsersDatasource usersDatasource;

    @BeforeEach
    void setup() throws Exception {
        usersDatasource = mock(UsersDatasource.class);

        searchUsersService = new SearchUsersService(usersDatasource);
    }

    @Test
    @DisplayName("Should fail when no search criteria are provided.")
    void failWhenNoCriteriaProvided() {
        assertThatThrownBy(() -> searchUsersService.search(new SearchUsersCommand()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Missing required search criteria");
    }

    @Test
    @DisplayName("Should fail when empty email criteria is provided.")
    void failWhenEmptyEmailProvided() {
        assertThatThrownBy(() -> searchUsersService.search(new SearchUsersCommand("")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Provided email is empty");
    }

    @Test
    @DisplayName("Should return a matching users when valid email criteria is provided.")
    void emailMatchingUsers() {
        var expectedUsers = List.of(new User("valid-email", "User", "WITH_VALID_EMAIL"));

        when(usersDatasource.findByEmail("valid-email")).thenReturn(expectedUsers);

        assertThat(searchUsersService.search(new SearchUsersCommand("valid-email"))).isEqualTo(expectedUsers);
    }

    @Test
    @DisplayName("Should fail when empty firstname criteria and no lastname criteria are provided.")
    void failWhenEmptyFirstnameProvided() {
        assertThatThrownBy(() -> searchUsersService.search(new SearchUsersCommand(new IdentityUserSearchCommand("", null))))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Missing required search criteria");
    }

    @Test
    @DisplayName("Should fail when no firstname criteria and empty lastname criteria are provided.")
    void failWhenEmptyLastnameProvided() {
        assertThatThrownBy(() -> searchUsersService.search(new SearchUsersCommand(new IdentityUserSearchCommand(null, ""))))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Missing required search criteria");
    }

    @Test
    @DisplayName("Should fail when empty firstname criteria and empty lastname criteria are provided.")
    void failWhenEmptyFirstnameAndLastnameProvided() {
        assertThatThrownBy(() -> searchUsersService.search(new SearchUsersCommand(new IdentityUserSearchCommand("", ""))))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Provided firstname and/or lastname empty");
    }

    @Test
    @DisplayName("Should fail when not empty firstname criteria and empty lastname criteria are provided.")
    void failWhenNotEmptyFirstnameAndLastnameProvided() {
        assertThatThrownBy(() -> searchUsersService.search(new SearchUsersCommand(new IdentityUserSearchCommand("firstname", ""))))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Provided firstname and/or lastname empty");
    }

    @Test
    @DisplayName("Should fail when empty firstname criteria and not empty lastname criteria are provided.")
    void failWhenEmptyFirstnameAndNotLastnameProvided() {
        assertThatThrownBy(() -> searchUsersService.search(new SearchUsersCommand(new IdentityUserSearchCommand("", "lastname"))))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Provided firstname and/or lastname empty");
    }

    @Test
    @DisplayName("Should return matching users when valid identity criteria are provided.")
    void identityMatchingUsers() {
        var expectedUsers = List.of(new User("email", "firstname", "lastname"));

        when(usersDatasource.findByIdentity("firstname", "lastname")).thenReturn(expectedUsers);

        assertThat(searchUsersService.search(new SearchUsersCommand(new IdentityUserSearchCommand("firstname", "lastname")))).isEqualTo(expectedUsers);
    }

    @Test
    @DisplayName("Should return email matching users when all valid criterias are provided.")
    void emailMatchingUsersWhenAllCriteria() {
        var expectedUsers = List.of(new User("valid-email", "User", "WITH_VALID_EMAIL"));

        when(usersDatasource.findByEmail("valid-email")).thenReturn(expectedUsers);

        assertThat(searchUsersService.search(new SearchUsersCommand("valid-email", new IdentityUserSearchCommand("firstname", "lastname")))).isEqualTo(expectedUsers);
    }

}
