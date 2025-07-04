package com.mycompany.myapp.shared.kipe.application;

import com.mycompany.myapp.shared.error.domain.Assert;
import com.mycompany.myapp.shared.generation.domain.ExcludeFromGeneratedCodeCoverage;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
class AccessEvaluator {

  private static final Logger log = LoggerFactory.getLogger(AccessEvaluator.class);

  private final AccessChecker<?> defaultEvaluator;
  private final Map<Class<?>, AccessChecker<?>> evaluators;

  @ExcludeFromGeneratedCodeCoverage(reason = "Ignored log conditional")
  public AccessEvaluator(List<AccessChecker<?>> checkers) {
    evaluators = new ConcurrentHashMap<>(checkers.stream().collect(Collectors.toMap(this::getCheckerResourceClass, Function.identity())));

    if (log.isInfoEnabled()) {
      log.info("Authorized types: {}", evaluators.keySet().stream().map(Class::getName).collect(Collectors.joining(", ")));
    }

    defaultEvaluator = evaluators.get(Object.class);
    Assert.notNull("defaultEvaluator", defaultEvaluator);
  }

  private Class<?> getCheckerResourceClass(AccessChecker<?> checker) {
    Class<?> checkerClass = checker.getClass();

    return (Class<?>) ((ParameterizedType) streamParameterizedTypes(checkerClass)
        .filter(type -> ((ParameterizedType) type).getRawType().equals(AccessChecker.class))
        .findFirst()
        .orElseThrow()).getActualTypeArguments()[0];
  }

  private Stream<Type> streamParameterizedTypes(Class<?> checkerClass) {
    return Arrays.stream(checkerClass.getGenericInterfaces()).filter(ParameterizedType.class::isInstance);
  }

  public boolean can(Authentication authentication, String action, Object item) {
    if (item == null) {
      return defaultEvaluator.canOnObject(authentication, action, null);
    }

    return evaluators.computeIfAbsent(item.getClass(), this::getDefaultChecker).canOnObject(authentication, action, item);
  }

  private AccessChecker<?> getDefaultChecker(Class<?> itemClass) {
    Class<?> matchingClass = evaluators
      .keySet()
      .stream()
      .filter(key -> !Object.class.equals(key))
      .filter(key -> key.isAssignableFrom(itemClass))
      .findFirst()
      .orElse(Object.class);

    log.info("Using {} evaluator for {}", matchingClass, itemClass);

    return evaluators.get(matchingClass);
  }
}
