package io.mendirl.jlite.dummy.domain.order;

import io.mendirl.jlite.dummy.domain.Amount;
import io.mendirl.jlite.error.domain.Assert;

public record BeerOrderLine(OrderedBeer beer, int quantity) {
  public BeerOrderLine {
    Assert.notNull("beer", beer);
    Assert.field("quantity", quantity).min(1);
  }

  Amount amount() {
    return beer().unitPrice().times(quantity());
  }
}
