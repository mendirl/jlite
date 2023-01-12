package io.mendirl.jhlite.dummy.domain.order;

import io.mendirl.jhlite.dummy.domain.Amount;
import io.mendirl.jhlite.error.domain.Assert;

public record BeerOrderLine(OrderedBeer beer, int quantity) {
  public BeerOrderLine {
    Assert.notNull("beer", beer);
    Assert.field("quantity", quantity).min(1);
  }

  Amount amount() {
    return beer().unitPrice().times(quantity());
  }
}
