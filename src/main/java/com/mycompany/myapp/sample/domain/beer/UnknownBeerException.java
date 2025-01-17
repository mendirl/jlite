package com.mycompany.myapp.sample.domain.beer;

import com.mycompany.myapp.sample.domain.BeerId;

class UnknownBeerException extends RuntimeException {

  public UnknownBeerException(BeerId id) {
    super("Beer " + id.get() + " is unknown");
  }
}
