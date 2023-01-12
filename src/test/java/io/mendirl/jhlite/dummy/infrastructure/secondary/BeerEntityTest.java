package io.mendirl.jhlite.dummy.infrastructure.secondary;

import static org.assertj.core.api.Assertions.*;
import static io.mendirl.jhlite.dummy.domain.beer.BeersFixture.*;

import org.junit.jupiter.api.Test;
import io.mendirl.jhlite.UnitTest;

@UnitTest
class BeerEntityTest {

  @Test
  void shouldConvertFromAndToDomain() {
    assertThat(BeerEntity.from(beer()).toDomain()).usingRecursiveComparison().isEqualTo(beer());
  }
}
