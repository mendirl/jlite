package io.mendirl.jhlite.dummy.domain.beer;

import io.mendirl.jhlite.dummy.domain.Amount;
import io.mendirl.jhlite.dummy.domain.BeerId;
import io.mendirl.jhlite.error.domain.Assert;

public record BeerToCreate(BeerName name, Amount unitPrice) {
  public BeerToCreate {
    Assert.notNull("name", name);
    Assert.notNull("unitPrice", unitPrice);
  }

  public Beer create() {
    return Beer.builder().id(BeerId.newId()).name(name()).unitPrice(unitPrice()).sellingState(BeerSellingState.SOLD).build();
  }
}
