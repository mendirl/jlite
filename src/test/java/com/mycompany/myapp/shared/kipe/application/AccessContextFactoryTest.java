package com.mycompany.myapp.shared.kipe.application;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.Authentication;
import com.mycompany.myapp.UnitTest;

@UnitTest
class AccessContextFactoryTest {

  @Test
  void shouldGetNullElementAccessContextFromNullElement() {
    assertThat(AccessContextFactory.of(mock(Authentication.class), "action", null)).isExactlyInstanceOf(NullElementAccessContext.class);
  }

  @Test
  void shouldGetElementAccessContextFromActualElement() {
    assertThat(AccessContextFactory.of(mock(Authentication.class), "action", "element")).isExactlyInstanceOf(ElementAccessContext.class);
  }
}
