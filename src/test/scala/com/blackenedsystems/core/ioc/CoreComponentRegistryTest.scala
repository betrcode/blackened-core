package com.blackenedsystems.core.ioc

import org.scalatest.junit.JUnitSuite
import org.junit.Assert._
import org.junit.{Ignore, Test}

/**
 * @author Alan Tibbetts
 * @since 28/3/11 3:49 PM
 */

@Ignore
class CoreComponentRegistryTest extends JUnitSuite {

  @Test def countryService_ok() {
    val countryService = CoreComponentRegistry.countryService
    assertNotNull(countryService)
  }

}