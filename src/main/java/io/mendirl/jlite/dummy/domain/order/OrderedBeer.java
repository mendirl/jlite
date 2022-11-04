package io.mendirl.jlite.dummy.domain.order;

import io.mendirl.jlite.dummy.domain.Amount;
import io.mendirl.jlite.dummy.domain.BeerId;
import io.mendirl.jlite.error.domain.Assert;

public record OrderedBeer(BeerId beer, Amount unitPrice) {
  public OrderedBeer {
    Assert.notNull("beer", beer);
    Assert.notNull("unitPrice", unitPrice);
  }
}
