package io.mendirl.jhlite.pagination.infrastructure.primary;

import static io.mendirl.jhlite.BeanValidationAssertions.*;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

import io.mendirl.jhlite.UnitTest;
import io.mendirl.jhlite.pagination.domain.JhLiteApplicationPageable;

@UnitTest
class RestJhLiteApplicationPageableTest {

  @Test
  void shouldConvertToDomain() {
    JhLiteApplicationPageable pageable = pageable().toPageable();

    assertThat(pageable.page()).isEqualTo(1);
    assertThat(pageable.pageSize()).isEqualTo(15);
  }

  @Test
  void shouldNotValidateWithPageUnderZero() {
    RestJhLiteApplicationPageable pageable = pageable();
    pageable.setPage(-1);

    assertThatBean(pageable).hasInvalidProperty("page");
  }

  @Test
  void shouldNotValidateWithSizeAtZero() {
    RestJhLiteApplicationPageable pageable = pageable();
    pageable.setPageSize(0);

    assertThatBean(pageable).hasInvalidProperty("pageSize").withParameter("value", 1L);
  }

  @Test
  void shouldNotValidateWithPageSizeOverHundred() {
    RestJhLiteApplicationPageable pageable = pageable();
    pageable.setPageSize(101);

    assertThatBean(pageable).hasInvalidProperty("pageSize");
  }

  private RestJhLiteApplicationPageable pageable() {
    return new RestJhLiteApplicationPageable(1, 15);
  }
}
