package fr.norsys.tverhoken.mutationtesting.prettygood;

public class IdentityUserSearchCommand implements NonEmptyStringCheck {
    private String firstname;
    private String lastname;

    public IdentityUserSearchCommand(String firstname, String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public boolean hasIdentityCriterias() {
        return firstname != null && lastname != null;
    }

    public boolean isValid() {
        return this.check(firstname) && this.check(lastname);
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }
}
