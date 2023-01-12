package io.mendirl.jhlite.dummy.infrastructure.primary.beer;

import static io.mendirl.jhlite.BeanValidationAssertions.*;
import static org.assertj.core.api.Assertions.*;

import io.mendirl.jhlite.UnitTest;
import io.mendirl.jhlite.JsonHelper;
import io.mendirl.jhlite.dummy.domain.beer.BeersFixture;
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
