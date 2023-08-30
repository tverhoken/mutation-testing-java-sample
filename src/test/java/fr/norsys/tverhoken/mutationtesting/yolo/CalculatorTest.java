package fr.norsys.tverhoken.mutationtesting.yolo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

public class CalculatorTest {

    @Test
    @DisplayName("Should perform an addition with provided args.")
    public void addition0and0() {
        assertThat(new Calculator().addition(0, 0)).isEqualTo(0);
    }

    @ParameterizedTest
    @CsvSource({"0,0,0", "1,1,2", "2,2,4"})
    @DisplayName("Should perform an addition and return expected result when provided args are.")
    void additionTest(int arg1, int arg2, int expectedResults) throws Exception {
        assertThat(new Calculator().addition(arg1, arg2)).isEqualTo(expectedResults);
    }

}
