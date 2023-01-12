package io.mendirl.jhlite.pagination.infrastructure.secondary;

import java.util.function.Function;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import io.mendirl.jhlite.error.domain.Assert;
import io.mendirl.jhlite.pagination.domain.JhLiteApplicationPage;
import io.mendirl.jhlite.pagination.domain.JhLiteApplicationPageable;

public final class JhLiteApplicationPages {

  private JhLiteApplicationPages() {}

  public static Pageable from(JhLiteApplicationPageable pagination) {
    return from(pagination, Sort.unsorted());
  }

  public static Pageable from(JhLiteApplicationPageable pagination, Sort sort) {
    Assert.notNull("pagination", pagination);
    Assert.notNull("sort", sort);

    return PageRequest.of(pagination.page(), pagination.pageSize(), sort);
  }

  public static <S, T> JhLiteApplicationPage<T> from(Page<S> springPage, Function<S, T> mapper) {
    Assert.notNull("springPage", springPage);
    Assert.notNull("mapper", mapper);

    return JhLiteApplicationPage
      .builder(springPage.getContent().parallelStream().map(mapper).toList())
      .currentPage(springPage.getNumber())
      .pageSize(springPage.getSize())
      .totalElementsCount(springPage.getTotalElements())
      .build();
  }
}
