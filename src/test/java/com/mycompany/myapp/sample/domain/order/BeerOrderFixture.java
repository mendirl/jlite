package com.mycompany.myapp.sample.domain.order;

import static com.mycompany.myapp.sample.domain.BeersIdentityFixture.*;

public final class BeerOrderFixture {

  private BeerOrderFixture() {}

  public static BeerOrder beerOrder() {
    return BeerOrder.builder().add(orderedCloakOfFeather()).add(orderedCloakOfFeather()).add(orderedAnteMeridiem(), 3).build();
  }

  public static OrderedBeer orderedCloakOfFeather() {
    return new OrderedBeer(cloackOfFeathersId(), cloakOfFeatherUnitPrice());
  }

  public static OrderedBeer orderedAnteMeridiem() {
    return new OrderedBeer(cloackOfFeathersId(), anteMeridiemUnitPrice());
  }
}
