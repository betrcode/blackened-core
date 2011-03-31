package com.blackenedsystems.core.integrationtests

import grizzled.slf4j.Logging
import org.scalatest.junit.JUnitSuite
import org.junit.Assert._
import com.blackenedsystems.core.ioc.CoreComponentRegistry
import com.blackenedsystems.core.Country
import org.junit.{Before, Test}

/**
 * @author Alan Tibbetts
 * @since 31/3/11 12:46 PM
 */

class CountryServiceTest extends JUnitSuite with Logging {

  val countryService = CoreComponentRegistry.countryService

  @Before def setUp() {
    CoreComponentRegistry.dataSource.removeCollection("Country")

    val country = new Country("GB", "GBR", "826", "United Kingdom")
    country.addName("se", "Storbritannien")
    countryService.save(country)
  }

  @Test def find_uk_ok() {
    val uk = countryService.findByIsoCode2("GB")
    validateUK(uk)
  }

  @Test def find_uk_withLowerCase_ok() {
    val uk = countryService.findByIsoCode2("gb")
    validateUK(uk)
  }

  private def validateUK(uk: Country) {
    assertNotNull("UK should have been added by the setUp process", uk)
    assertEquals("Invalid isocode3", "GBR", uk.isoCode3)
    assertEquals("Invalid numeric isocode", "826", uk.isoCodeNumeric)
    assertEquals("Default name incorrect", "United Kingdom", uk.defaultName)
    assertEquals("Names should be in English and Swedish", 2, uk.nameLanguages.size)
    assertEquals("English Translation", "United Kingdom", uk.getName("en"))
    assertEquals("Swedish Translation", "Storbritannien", uk.getName("se"))
    assertEquals("German Translation returns English (default)", "United Kingdom", uk.getName("de"))
    assertEquals("Default display order", 999, uk.displayOrder)
    assertTrue("Created before now", uk.updateTime.isBeforeNow)
  }
}