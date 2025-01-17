package com.mycompany.myapp.sample.domain.beer;

import static org.assertj.core.api.Assertions.*;
import static com.mycompany.myapp.sample.domain.BeersIdentityFixture.*;
import static com.mycompany.myapp.sample.domain.beer.BeersFixture.*;

import java.util.List;
import org.junit.jupiter.api.Test;
import com.mycompany.myapp.UnitTest;

@UnitTest
class BeersTest {

  @Test
  void shouldSortBeersByNames() {
    Beer anteMeridiem = Beer.builder()
      .id(anteMeridiemId())
      .name(new BeerName("Ante Meridiem"))
      .unitPrice(anteMeridiemUnitPrice())
      .sellingState(BeerSellingState.SOLD)
      .build();

    Beers beers = new Beers(List.of(beer(), anteMeridiem));

    assertThat(beers.get()).usingRecursiveFieldByFieldElementComparator().containsExactly(anteMeridiem, beer());
  }
}
