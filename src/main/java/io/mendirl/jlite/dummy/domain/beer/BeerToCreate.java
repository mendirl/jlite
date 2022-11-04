package io.mendirl.jlite.dummy.domain.beer;

import io.mendirl.jlite.dummy.domain.Amount;
import io.mendirl.jlite.dummy.domain.BeerId;
import io.mendirl.jlite.error.domain.Assert;

public record BeerToCreate(BeerName name, Amount unitPrice) {
  public BeerToCreate {
    Assert.notNull("name", name);
    Assert.notNull("unitPrice", unitPrice);
  }

  public Beer create() {
    return Beer.builder().id(BeerId.newId()).name(name()).unitPrice(unitPrice()).sellingState(BeerSellingState.SOLD).build();
  }
}
