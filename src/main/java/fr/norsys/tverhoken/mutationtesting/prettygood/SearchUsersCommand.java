package fr.norsys.tverhoken.mutationtesting.prettygood;

import java.util.Optional;

public class SearchUsersCommand implements NonEmptyStringCheck {
    private IdentityUserSearchCommand identityUserSearchCommand;
    private String email;

    public SearchUsersCommand() {}
    public SearchUsersCommand(String email) {
        this.email = email;
    }

    public SearchUsersCommand(IdentityUserSearchCommand identityUserSearchCommand) {
        this.identityUserSearchCommand = identityUserSearchCommand;
    }

    public SearchUsersCommand(String email, IdentityUserSearchCommand identityUserSearchCommand) {
        this.email = email;
        this.identityUserSearchCommand = identityUserSearchCommand;
    }

    public boolean isEmailCriteriaValid() {
        return this.check(email);
    }

    public boolean hasEmailCriteria() {
        return email != null;
    }

    public String getEmail() {
        return this.email;
    }

    public boolean hasIdentityCriteria() {
        return Optional.ofNullable(identityUserSearchCommand)
                .map(IdentityUserSearchCommand::hasIdentityCriterias)
                .orElse(false);
    }

    public boolean isIdentityCriteriaValid() {
        return identityUserSearchCommand.isValid();
    }

    public String getIdentityFirstname() {
        return identityUserSearchCommand.getFirstname();
    }

    public String getIdentityLastname() {
        return identityUserSearchCommand.getLastname();
    }
}
