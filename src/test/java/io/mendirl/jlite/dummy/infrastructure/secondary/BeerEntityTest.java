package io.mendirl.jlite.dummy.infrastructure.secondary;

import static org.assertj.core.api.Assertions.*;
import static io.mendirl.jlite.dummy.domain.beer.BeersFixture.*;

import org.junit.jupiter.api.Test;
import io.mendirl.jlite.UnitTest;

@UnitTest
class BeerEntityTest {

  @Test
  void shouldConvertFromAndToDomain() {
    assertThat(BeerEntity.from(beer()).toDomain()).usingRecursiveComparison().isEqualTo(beer());
  }
}
