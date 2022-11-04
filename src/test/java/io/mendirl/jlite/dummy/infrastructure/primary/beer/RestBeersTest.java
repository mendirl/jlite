package io.mendirl.jlite.dummy.infrastructure.primary.beer;

import static org.assertj.core.api.Assertions.*;

import io.mendirl.jlite.JsonHelper;
import io.mendirl.jlite.UnitTest;
import io.mendirl.jlite.dummy.domain.beer.Beers;
import io.mendirl.jlite.dummy.domain.beer.BeersFixture;
import java.util.List;
import org.junit.jupiter.api.Test;

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
