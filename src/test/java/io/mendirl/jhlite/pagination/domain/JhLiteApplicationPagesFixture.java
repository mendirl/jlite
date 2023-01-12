package io.mendirl.jhlite.pagination.domain;

import java.util.List;

import io.mendirl.jhlite.pagination.domain.JhLiteApplicationPage.JhLiteApplicationPageBuilder;

public final class JhLiteApplicationPagesFixture {

  private JhLiteApplicationPagesFixture() {}

  public static JhLiteApplicationPage<String> page() {
    return pageBuilder().build();
  }

  public static JhLiteApplicationPageBuilder<String> pageBuilder() {
    return JhLiteApplicationPage.builder(List.of("test")).currentPage(2).pageSize(10).totalElementsCount(21);
  }
}
