package com.blackenedsystems.core.integrationtests

import com.blackenedsystems.core.ioc.CoreComponentRegistry
import org.scalatest.junit.JUnitSuite

import org.junit.Test
import org.junit.Assert._

import grizzled.slf4j._

/**
 * @author Alan Tibbetts
 * @since 28/3/11 5:31 PM
 */

class CoreComponentRegistryTest extends JUnitSuite with Logging {

  @Test def executeQuery_countryService_ok() {

    val countryService = CoreComponentRegistry.countryService

    val country = countryService.findByIsoCode2("11")
    assertNotNull(country)
  }

}