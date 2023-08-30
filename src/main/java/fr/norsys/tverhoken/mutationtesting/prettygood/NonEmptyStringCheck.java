package fr.norsys.tverhoken.mutationtesting.prettygood;

import java.util.Optional;

public interface NonEmptyStringCheck {

    default boolean check(String toCheck) {
        return Optional.ofNullable(toCheck)
                .filter(value -> !value.isEmpty())
                .isPresent();
    }
}
