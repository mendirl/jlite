package io.mendirl.jhlite.account.infrastructure.secondary;

class UnknownAuthenticationSchemeException extends RuntimeException {

  public UnknownAuthenticationSchemeException() {
    super("Tried to read authentication from an unknown scheme");
  }
}
