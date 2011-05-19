package com.blackenedsystems.core.ioc

import org.scalatest.junit.JUnitSuite
import org.junit.Assert._
import org.junit.Test
import org.scalatest.Ignore

/**
 * @author Alan Tibbetts
 * @since 28/3/11 3:49 PM
 */
class ComponentRegistryTest extends JUnitSuite with MockedComponentRegistry {

  @Test def services_ok() {
    assertNotNull(countryService)
    assertNotNull(menuService)
  }

  @Test def daos_ok() {
    assertNotNull(countryDao)
    assertNotNull(menuDao)
  }
}