package io.mendirl.jlite.error.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import io.mendirl.jlite.UnitTest;

@UnitTest
class MissingMandatoryValueExceptionTest {

  public static final String FIELD = "field";

  @Test
  void shouldGetExceptionForBlankValue() {
    MissingMandatoryValueException exception = MissingMandatoryValueException.forBlankValue(FIELD);

    assertThat(exception.getMessage()).isEqualTo("The field \"field\" is mandatory and wasn't set (blank)");
  }

  @Test
  void shouldGetExceptionForNullValue() {
    MissingMandatoryValueException exception = MissingMandatoryValueException.forNullValue(FIELD);

    assertThat(exception.getMessage()).isEqualTo("The field \"field\" is mandatory and wasn't set (null)");
  }

  @Test
  void shouldGetExceptionForEmptyCollection() {
    MissingMandatoryValueException exception = MissingMandatoryValueException.forEmptyValue(FIELD);

    assertThat(exception.getMessage()).isEqualTo("The field \"field\" is mandatory and wasn't set (empty)");
  }
}
