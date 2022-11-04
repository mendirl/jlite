package io.mendirl.jlite.dummy.application;

import io.mendirl.jlite.dummy.domain.BeerId;
import io.mendirl.jlite.dummy.domain.beer.Beer;
import io.mendirl.jlite.dummy.domain.beer.BeerToCreate;
import io.mendirl.jlite.dummy.domain.beer.Beers;
import io.mendirl.jlite.dummy.domain.beer.BeersCreator;
import io.mendirl.jlite.dummy.domain.beer.BeersRemover;
import io.mendirl.jlite.dummy.domain.beer.BeersRepository;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

@Service
public class BeersApplicationService {

  private final BeersRepository beers;
  private final BeersCreator creator;
  private final BeersRemover remover;

  public BeersApplicationService(BeersRepository beers) {
    this.beers = beers;

    creator = new BeersCreator(beers);
    remover = new BeersRemover(beers);
  }

  @Transactional
  @PreAuthorize("hasRole('ADMIN')")
  public Beer create(BeerToCreate beerToCreate) {
    return creator.create(beerToCreate);
  }
  
  @Transactional
  @PreAuthorize("hasRole('ADMIN')")
  public void remove(BeerId beer) {
    remover.remove(beer);
  }

  @Transactional(readOnly = true)
  public Beers catalog() {
    return beers.catalog();
  }

}
