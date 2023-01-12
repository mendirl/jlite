package io.mendirl.jhlite.dummy.infrastructure.secondary;

import java.util.Optional;
import org.springframework.stereotype.Repository;
import io.mendirl.jhlite.dummy.domain.BeerId;
import io.mendirl.jhlite.dummy.domain.beer.Beer;
import io.mendirl.jhlite.dummy.domain.beer.BeerSellingState;
import io.mendirl.jhlite.dummy.domain.beer.Beers;
import io.mendirl.jhlite.dummy.domain.beer.BeersRepository;
import io.mendirl.jhlite.error.domain.Assert;

@Repository
class SpringDataBeersRepository implements BeersRepository {

  private final JpaBeersRepository beers;

  public SpringDataBeersRepository(JpaBeersRepository beers) {
    this.beers = beers;
  }

  @Override
  public void save(Beer beer) {
    Assert.notNull("beer", beer);

    beers.save(BeerEntity.from(beer));
  }

  @Override
  public Beers catalog() {
    return new Beers(beers.findBySellingState(BeerSellingState.SOLD).stream().map(BeerEntity::toDomain).toList());
  }

  @Override
  public Optional<Beer> get(BeerId beer) {
    Assert.notNull("beer", beer);

    return beers.findById(beer.get()).map(BeerEntity::toDomain);
  }
}
