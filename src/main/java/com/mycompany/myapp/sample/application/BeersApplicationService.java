package com.mycompany.myapp.sample.application;

import com.mycompany.myapp.sample.domain.BeerId;
import com.mycompany.myapp.sample.domain.beer.Beer;
import com.mycompany.myapp.sample.domain.beer.BeerToCreate;
import com.mycompany.myapp.sample.domain.beer.Beers;
import com.mycompany.myapp.sample.domain.beer.BeersCreator;
import com.mycompany.myapp.sample.domain.beer.BeersRemover;
import com.mycompany.myapp.sample.domain.beer.BeersRepository;
import org.springframework.security.access.prepost.PreAuthorize;
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

  @PreAuthorize("can('create', #beerToCreate)")
  public Beer create(BeerToCreate beerToCreate) {
    return creator.create(beerToCreate);
  }

  @PreAuthorize("can('remove', #beer)")
  public void remove(BeerId beer) {
    remover.remove(beer);
  }

  public Beers catalog() {
    return beers.catalog();
  }
}
