package com.mycompany.myapp.sample.domain.beer;

import com.mycompany.myapp.sample.domain.Amount;
import com.mycompany.myapp.sample.domain.BeerId;
import com.mycompany.myapp.shared.error.domain.Assert;

public record BeerToCreate(BeerName name, Amount unitPrice) {
  public BeerToCreate {
    Assert.notNull("name", name);
    Assert.notNull("unitPrice", unitPrice);
  }

  public Beer create() {
    return Beer.builder().id(BeerId.newId()).name(name()).unitPrice(unitPrice()).sellingState(BeerSellingState.SOLD).build();
  }
}
