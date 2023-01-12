package io.mendirl.jhlite.account.domain;

import static io.mendirl.jhlite.useridentity.domain.UsersIdentitiesFixture.*;

import java.util.List;

import io.mendirl.jhlite.authentication.domain.Role;

public final class AccountsFixture {

  private AccountsFixture() {
  }

  public static Account account() {
    return Account.builder()
        .username(username())
        .firstname(firstname())
        .lastname(lastname())
        .email(email())
        .roles(List.of(Role.ADMIN.key()))
        .build();
  }

}
