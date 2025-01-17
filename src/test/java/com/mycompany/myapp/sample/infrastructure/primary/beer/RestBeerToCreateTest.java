package com.mycompany.myapp.sample.infrastructure.primary.beer;

import static org.assertj.core.api.Assertions.*;
import static com.mycompany.myapp.BeanValidationAssertions.*;

import org.junit.jupiter.api.Test;
import com.mycompany.myapp.JsonHelper;
import com.mycompany.myapp.UnitTest;
import com.mycompany.myapp.sample.domain.beer.BeersFixture;

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
