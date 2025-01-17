package com.mycompany.myapp.sample.domain.order;

import com.mycompany.myapp.sample.domain.Amount;
import com.mycompany.myapp.shared.error.domain.Assert;

public record BeerOrderLine(OrderedBeer beer, int quantity) {
  public BeerOrderLine {
    Assert.notNull("beer", beer);
    Assert.field("quantity", quantity).min(1);
  }

  Amount amount() {
    return beer().unitPrice().times(quantity());
  }
}
