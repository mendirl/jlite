package io.mendirl.jhlite.pagination.infrastructure.primary;

import static org.assertj.core.api.Assertions.*;
import static io.mendirl.jhlite.pagination.domain.JhLiteApplicationPagesFixture.*;

import java.util.function.Function;

import org.junit.jupiter.api.Test;

import io.mendirl.jhlite.UnitTest;
import io.mendirl.jhlite.JsonHelper;
import io.mendirl.jhlite.error.domain.MissingMandatoryValueException;

@UnitTest
class RestJhLiteApplicationPageTest {

  @Test
  void shouldNotConvertWithoutSourcePage() {
    assertThatThrownBy(() -> RestJhLiteApplicationPage.from(null, source -> "test")).isExactlyInstanceOf(MissingMandatoryValueException.class);
  }

  @Test
  void shouldNotConvertWithoutMappingFunction() {
    assertThatThrownBy(() -> RestJhLiteApplicationPage.from(page(), null)).isExactlyInstanceOf(MissingMandatoryValueException.class);
  }

  @Test
  void shouldMapFromDomainPage() {
    RestJhLiteApplicationPage<String> page = RestJhLiteApplicationPage.from(page(), Function.identity());

    assertThat(page.getContent()).containsExactly("test");
    assertThat(page.getCurrentPage()).isEqualTo(2);
    assertThat(page.getPageSize()).isEqualTo(10);
    assertThat(page.getTotalElementsCount()).isEqualTo(21);
    assertThat(page.getPagesCount()).isEqualTo(3);
  }

  @Test
  void shouldGetPageCountForPageLimit() {
    RestJhLiteApplicationPage<String> page = RestJhLiteApplicationPage.from(pageBuilder().totalElementsCount(3).pageSize(3).build(), Function.identity());

    assertThat(page.getPagesCount()).isEqualTo(1);
  }

  @Test
  void shouldSerializeToJson() {
    assertThat(JsonHelper.writeAsString(RestJhLiteApplicationPage.from(page(), Function.identity()))).isEqualTo(json());
  }

  private String json() {
    return """
        {"content":["test"],\
        "currentPage":2,\
        "pageSize":10,\
        "totalElementsCount":21,\
        "pagesCount":3,\
        "hasPrevious":true,\
        "hasNext":false\
        }\
        """;
  }
}
