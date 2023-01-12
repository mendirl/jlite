package io.mendirl.jhlite.pagination.infrastructure.secondary;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.function.Function;

import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import io.mendirl.jhlite.UnitTest;
import io.mendirl.jhlite.error.domain.MissingMandatoryValueException;
import io.mendirl.jhlite.pagination.domain.JhLiteApplicationPage;
import io.mendirl.jhlite.pagination.domain.JhLiteApplicationPageable;

@UnitTest
class JhLiteApplicationPagesTest {

  @Test
  void shouldNotBuildPageableFromNullJhLiteApplicationPageable() {
    assertThatThrownBy(() -> JhLiteApplicationPages.from(null))
      .isExactlyInstanceOf(MissingMandatoryValueException.class)
      .hasMessageContaining("pagination");
  }

  @Test
  void shouldBuildPageableFromJhLiteApplicationPageable() {
    Pageable pagination = JhLiteApplicationPages.from(pagination());

    assertThat(pagination.getPageNumber()).isEqualTo(2);
    assertThat(pagination.getPageSize()).isEqualTo(15);
    assertThat(pagination.getSort()).isEqualTo(Sort.unsorted());
  }

  @Test
  void shouldNotBuildWithoutSort() {
    assertThatThrownBy(() -> JhLiteApplicationPages.from(pagination(), null))
      .isExactlyInstanceOf(MissingMandatoryValueException.class)
      .hasMessageContaining("sort");
  }

  @Test
  void shouldBuildPageableFromJhLiteApplicationPageableAndSort() {
    Pageable pagination = JhLiteApplicationPages.from(pagination(), Sort.by("dummy"));

    assertThat(pagination.getPageNumber()).isEqualTo(2);
    assertThat(pagination.getPageSize()).isEqualTo(15);
    assertThat(pagination.getSort()).isEqualTo(Sort.by("dummy"));
  }

  private JhLiteApplicationPageable pagination() {
    return new JhLiteApplicationPageable(2, 15);
  }

  @Test
  void shouldNotConvertFromSpringPageWithoutSpringPage() {
    assertThatThrownBy(() -> JhLiteApplicationPages.from(null, source -> source))
      .isExactlyInstanceOf(MissingMandatoryValueException.class)
      .hasMessageContaining("springPage");
  }

  @Test
  void shouldNotConvertFromSpringPageWithoutMapper() {
    assertThatThrownBy(() -> JhLiteApplicationPages.from(springPage(), null))
      .isExactlyInstanceOf(MissingMandatoryValueException.class)
      .hasMessageContaining("mapper");
  }

  @Test
  void shouldConvertFromSpringPage() {
    JhLiteApplicationPage<String> page = JhLiteApplicationPages.from(springPage(), Function.identity());

    assertThat(page.content()).containsExactly("test");
    assertThat(page.currentPage()).isEqualTo(2);
    assertThat(page.pageSize()).isEqualTo(10);
    assertThat(page.totalElementsCount()).isEqualTo(30);
  }

  private PageImpl<String> springPage() {
    return new PageImpl<>(List.of("test"), PageRequest.of(2, 10), 30);
  }
}
