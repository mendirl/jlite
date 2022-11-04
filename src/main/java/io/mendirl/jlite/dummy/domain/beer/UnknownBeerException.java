package io.mendirl.jlite.dummy.domain.beer;

import io.mendirl.jlite.dummy.domain.BeerId;

class UnknownBeerException extends RuntimeException {

  public UnknownBeerException(BeerId id) {
    super("Beer " + id.get() + " is unknown");
  }
}
