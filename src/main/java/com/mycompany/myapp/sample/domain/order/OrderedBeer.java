package com.mycompany.myapp.sample.domain.order;

import com.mycompany.myapp.sample.domain.Amount;
import com.mycompany.myapp.sample.domain.BeerId;
import com.mycompany.myapp.shared.error.domain.Assert;

public record OrderedBeer(BeerId beer, Amount unitPrice) {
  public OrderedBeer {
    Assert.notNull("beer", beer);
    Assert.notNull("unitPrice", unitPrice);
  }
}
