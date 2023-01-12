package io.mendirl.jhlite.dummy.domain.beer;

import io.mendirl.jhlite.dummy.domain.BeerId;

class UnknownBeerException extends RuntimeException {

  public UnknownBeerException(BeerId id) {
    super("Beer " + id.get() + " is unknown");
  }
}
