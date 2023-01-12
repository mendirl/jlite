package io.mendirl.jhlite.dummy.infrastructure.secondary;

import static org.assertj.core.api.Assertions.*;
import static io.mendirl.jhlite.dummy.domain.BeersIdentityFixture.*;
import static io.mendirl.jhlite.dummy.domain.beer.BeersFixture.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import io.mendirl.jhlite.IntegrationTest;

@Transactional
@IntegrationTest
class JpaBeersRepositoryIntTest {

  @Autowired
  private JpaBeersRepository beers;

  @Test
  void shouldSaveAndGetBeer() {
    beers.saveAndFlush(BeerEntity.from(beer()));

    assertThat(beers.findById(cloackOfFeathersId().get()).get().toDomain()).usingRecursiveComparison().isEqualTo(beer());
  }
}
