package io.mendirl.jhlite.dummy.domain.order;

import io.mendirl.jhlite.dummy.domain.Amount;
import io.mendirl.jhlite.dummy.domain.BeerId;
import io.mendirl.jhlite.error.domain.Assert;

public record OrderedBeer(BeerId beer, Amount unitPrice) {
  public OrderedBeer {
    Assert.notNull("beer", beer);
    Assert.notNull("unitPrice", unitPrice);
  }
}
