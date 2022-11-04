package io.mendirl.jlite.dummy.infrastructure.primary.beer;

import static io.mendirl.jlite.BeanValidationAssertions.*;
import static org.assertj.core.api.Assertions.*;

import io.mendirl.jlite.UnitTest;
import io.mendirl.jlite.JsonHelper;
import io.mendirl.jlite.dummy.domain.beer.BeersFixture;
import org.junit.jupiter.api.Test;

@UnitTest
class RestBeerToCreateTest {

  @Test
  void shouldDeserializeFromJson() {
    assertThat(JsonHelper.readFromJson(json(), RestBeerToCreate.class).toDomain())
      .usingRecursiveComparison()
      .isEqualTo(BeersFixture.beerToCreate());
  }

  private String json() {
    return """
        {
          "name": "Cloak of feathers",
          "unitPrice": 8.53
        }
        """;
  }

  @Test
  void shouldNotValidateEmptyBean() {
    assertThatBean(new RestBeerToCreate(null, null)).hasInvalidProperty("name").and().hasInvalidProperty("unitPrice");
  }
}
