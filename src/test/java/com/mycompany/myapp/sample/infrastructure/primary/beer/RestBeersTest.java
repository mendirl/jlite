package com.mycompany.myapp.sample.infrastructure.primary.beer;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.Test;
import com.mycompany.myapp.JsonHelper;
import com.mycompany.myapp.UnitTest;
import com.mycompany.myapp.sample.domain.beer.Beers;
import com.mycompany.myapp.sample.domain.beer.BeersFixture;

@UnitTest
class RestBeersTest {

  @Test
  void shouldSerializeToJson() {
    assertThat(JsonHelper.writeAsString(RestBeers.from(new Beers(List.of(BeersFixture.beer()))))).isEqualTo(json());
  }

  private String json() {
    return "{\"beers\":[" + RestBeerTest.json() + "]}";
  }
}
