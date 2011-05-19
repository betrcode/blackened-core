/*
 * Copyright 2011 Blackened Systems
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.blackenedsystems.core.integrationtests

import grizzled.slf4j.Logging
import org.scalatest.junit.JUnitSuite
import org.junit.Assert._
import com.blackenedsystems.core.Country
import org.junit.{Before, Test}

/**
 * Integration tests for the <code>CountryService</code>.
 *
 * @author Alan Tibbetts
 * @since 31/3/11 12:46 PM
 */

class CountryServiceTest extends JUnitSuite with Logging {

  val countryService = TestingComponentRegistry.countryService

  @Before def setUp() {
    TestingComponentRegistry.dataSource.removeCollection(countryService.collectionName)

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

  @Test def insert_sweden_ok() {
    val country = new Country("SE", "SWE", "123", "Sweden")
    countryService.save(country)
    assertNotNull("ID should have been set during persistence", country.id)

    val sweden = countryService.find(country.id)
    sweden match {
      case Some(sw) => assertEquals("Original and retrieved Sweden should be the same", country, sw)
      case None => fail("Could not find country (SE)")
    }

    val allCountries = countryService.findAll()
    assertEquals("Should be two countries now", 2, allCountries.size)
  }

  @Test def update_uk_ok() {
    var allCountries = countryService.findAll()
    assertEquals("Should only be one country", 1, allCountries.size)

    val country = countryService.findByIsoCode2("GB")
    country match {
      case Some(uk) =>
        uk.addName("FR", "Royaume-Uni")
        val savedUK = countryService.save(uk)
        assertEquals("IDs should be the same", uk.id, savedUK.id)
        assertEquals("Names should be in English, Swedish and French", 3, savedUK.nameLanguages.size)

      case None => fail("Could not find country (GB)")
    }

    allCountries = countryService.findAll()
    assertEquals("Should still only be one country", 1, allCountries.size)
  }


  private def validateUK(country: Option[Country]) {
    country match {
      case Some(uk) =>
        assertNotNull("UK should have been added by the setUp process", uk)
        assertNotNull("ID should have been set by Mongo", uk.id)
        assertEquals("Invalid isocode3", "GBR", uk.isoCode3)
        assertEquals("Invalid numeric isocode", "826", uk.isoCodeNumeric)
        assertEquals("Default name incorrect", "United Kingdom", uk.defaultName)
        assertEquals("Names should be in English and Swedish", 2, uk.nameLanguages.size)
        assertEquals("English Translation", "United Kingdom", uk.getName("en"))
        assertEquals("Swedish Translation", "Storbritannien", uk.getName("se"))
        assertEquals("German Translation returns English (default)", "United Kingdom", uk.getName("de"))
        assertEquals("Default display order", 999, uk.displayOrder)
        assertTrue("Created before now", uk.updateTime.isBeforeNow)

      case None => fail("Did not find country (GB)")
    }

  }
}