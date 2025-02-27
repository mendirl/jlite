package com.mycompany.myapp.sample.domain;

import java.util.UUID;
import com.mycompany.myapp.shared.error.domain.Assert;

public record BeerId(UUID id) {
  public BeerId {
    Assert.notNull("id", id);
  }

  public static BeerId newId() {
    return new BeerId(UUID.randomUUID());
  }

  public UUID get() {
    return id();
  }
}
