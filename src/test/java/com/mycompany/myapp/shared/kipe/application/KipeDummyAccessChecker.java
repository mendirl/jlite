package com.mycompany.myapp.shared.kipe.application;

import org.springframework.stereotype.Component;
import com.mycompany.myapp.shared.kipe.domain.KipeDummy;

@Component
class KipeDummyAccessChecker implements AccessChecker<KipeDummy> {

  @Override
  public boolean can(AccessContext<KipeDummy> access) {
    return access.authentication() != null && access.element().value().equals("authorized");
  }
}
