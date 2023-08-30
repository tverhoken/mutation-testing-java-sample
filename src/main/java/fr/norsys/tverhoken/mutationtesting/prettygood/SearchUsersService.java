package fr.norsys.tverhoken.mutationtesting.prettygood;

import java.util.List;

public class SearchUsersService {
    private UsersDatasource usersDatasource;

    public SearchUsersService(UsersDatasource usersDatasource) {
        this.usersDatasource = usersDatasource;
    }

    public List<User> search(SearchUsersCommand searchUsersCommand) {
        if (searchUsersCommand.hasEmailCriteria()) {
            if (searchUsersCommand.isEmailCriteriaValid()) {
                return usersDatasource.findByEmail(searchUsersCommand.getEmail());
            }
            throw new IllegalArgumentException("Provided email is empty");
        }
        if (searchUsersCommand.hasIdentityCriteria()) {
            if (searchUsersCommand.isIdentityCriteriaValid()) {
                return usersDatasource.findByIdentity(searchUsersCommand.getIdentityFirstname(), searchUsersCommand.getIdentityLastname());
            }
            throw new IllegalArgumentException("Provided firstname and/or lastname empty");
        }

        throw new IllegalArgumentException("Missing required search criteria");
    }
}
