package fr.norsys.tverhoken.mutationtesting.prettygood;

import java.util.List;

public interface UsersDatasource {
    List<User> findByEmail(String email);

    List<User> findByIdentity(String firstname, String lastname);
}
