package fr.norsys.tverhoken.mutationtesting;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CalculatorTest {

    @Test
    @DisplayName("Should perform an addition with provided args.")
    public void addition0and0() {
        assertThat(new Calculator().addition(0, 0)).isEqualTo(0);
    }

}
