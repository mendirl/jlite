package com.mycompany.myapp.sample.infrastructure.primary.beer;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Collection;
import com.mycompany.myapp.sample.domain.beer.Beers;
import com.mycompany.myapp.shared.error.domain.Assert;

@Schema(name = "beers", description = "Some beers")
final class RestBeers {

  private final Collection<RestBeer> beers;

  private RestBeers(Collection<RestBeer> beers) {
    this.beers = beers;
  }

  public static RestBeers from(Beers beers) {
    Assert.notNull("beers", beers);

    return new RestBeers(beers.stream().map(RestBeer::from).toList());
  }

  public Collection<RestBeer> getBeers() {
    return beers;
  }
}
