package com.mycompany.myapp.shared.kipe.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import com.mycompany.myapp.UnitTest;

@UnitTest
class ActionTest {

  @Test
  void shouldGetActionAsToString() {
    assertThat(new Action("act")).hasToString("act");
  }
}
