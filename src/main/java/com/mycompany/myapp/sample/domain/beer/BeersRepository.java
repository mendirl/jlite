package com.mycompany.myapp.sample.domain.beer;

import java.util.Optional;
import com.mycompany.myapp.sample.domain.BeerId;

public interface BeersRepository {
  void save(Beer beer);

  Beers catalog();

  Optional<Beer> get(BeerId beer);
}
