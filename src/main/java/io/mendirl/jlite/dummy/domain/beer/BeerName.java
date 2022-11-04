package io.mendirl.jlite.dummy.domain.beer;

import io.mendirl.jlite.error.domain.Assert;

public record BeerName(String name) {
  public BeerName {
    Assert.field("name", name).notBlank().maxLength(255);
  }

  public String get() {
    return name();
  }
}
