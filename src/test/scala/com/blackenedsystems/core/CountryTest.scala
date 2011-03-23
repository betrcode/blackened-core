package com.blackenedsystems.core

import org.scalatest.junit.JUnitSuite
import org.junit.Assert._
import org.junit.Test

/**
 * @author Alan Tibbetts
 * @since 23/3/11 11:18 AM
 */

class CountryTest extends JUnitSuite {

  @Test def constructor_ok() {
    val country = new Country("GB", "GBR", "826", "United Kingdom")
    assertEquals("GB", country.isoCode2)
    assertEquals("GBR", country.isoCode3)
    assertEquals("826", country.isoCodeNumeric)
    assertEquals(999, country.displayOrder)
    assertNotNull(country.getDefaultName())
    assertEquals("United Kingdom", country.getDefaultName())
  }

  @Test def update_displayOrder() {
    val country = new Country("GB", "GBR", "826", "United Kingdom")
    assertEquals(999, country.displayOrder)
    country.displayOrder = 10
    assertEquals(10, country.displayOrder)
  }

  @Test def defaultName_ok() {
    val country = new Country("GB", "GBR", "826", "United Kingdom")
    assertNotNull(country.getDefaultName())
    assertEquals("United Kingdom", country.getDefaultName())
  }

  @Test def getName_german_falls_back_to_default_language() {
    val country = new Country("GB", "GBR", "826", "United Kingdom")
    assertEquals("United Kingdom", country.getName("de"))
  }

  @Test def getName_german_and_english_ok() {
    val country = new Country("DE", "DEU", "276", "Germany")
    country.addName("de", "Deutschland")
    assertEquals("Germany", country.getName(CoreObject.DefaultLanguage))
    assertEquals("Deutschland", country.getName("de"))
  }

  @Test def getName_using_uppercase_languageCode() {
    val country = new Country("DE", "DEU", "276", "Germany")
    assertEquals("Germany", country.getName(CoreObject.DefaultLanguage))
  }

  @Test def getShortName_german_falls_back_to_default_language() {
    val country = new Country("GB", "GBR", "826", "United Kingdom")
    country.addShortName("UK")
    assertEquals("Short Name should match name in default language", "UK", country.getShortName("de"))
  }

  @Test def getShortName_german_and_english_ok() {
    val country = new Country("DE", "DEU", "276", "Germany")
    country.addName("de", "Deutschland")
    assertEquals("Short Name should match name in default language", "Germany", country.getShortName(CoreObject.DefaultLanguage))
    assertEquals("Short Name should match name in German", "Deutschland", country.getShortName("de"))
  }

  @Test def getShortName_using_uppercase_languageCode() {
    val country = new Country("DE", "DEU", "276", "Germany")
    assertEquals("Short Name should match name in default language", "Germany", country.getShortName(CoreObject.DefaultLanguage))
  }

  @Test def getShortName_no_names_no_infinte_loop() {
    val country = new Country("GB", "GBR", "826", "")
    assertEquals("Should not be a short name for this country", "", country.getShortName("de"))
  }

  @Test def getLanguageList_names() {
    val country = new Country("DE", "DEU", "276", "Germany")
    country.addName("de", "Deutschland")
    val ll = country.getNameLanguages()
    assertNotNull("Should return an empty list, never null", ll)
    assertEquals("Names added for en and de", 2, ll.size)
  }

  @Test def getLanguageList_shortNames_empty() {
    val country = new Country("DE", "DEU", "276", "Germany")
    country.addName("de", "Deutschland")
    val ll = country.getShortNameLanguages()
    assertNotNull("Should return an empty list, never null", ll)
    assertEquals("No short names added", 0, ll.size)
  }

  @Test def getLanguageList_shortNames_uk_only() {
    val country = new Country("DE", "DEU", "276", "Germany")
    country.addName("de", "Deutschland")
    country.addShortName("UK")
    val ll = country.getShortNameLanguages()
    assertNotNull("Should return an empty list, never null", ll)
    assertEquals("Only 'UK' short name added", 1, ll.size)
  }

  @Test def constructor_long_name_trimmed() {
    val country = new Country("CC", "CCC", "123", "CountryWithAStupidlyLongNameOrToPutItAnotherWayACompletelyUnreasonableName")
    assertEquals("Name should have been trimmed to 50 chars(ish)", "CountryWithAStupidlyLongNameOrToPutItAnotherWa...", country.getDefaultName)
  }

  @Test def addName_long_name_trimmed() {
    val country = new Country("CC", "CCC", "123", "Country")
    country.addName("CountryWithAStupidlyLongNameOrToPutItAnotherWayACompletelyUnreasonableName")
    assertEquals("Name should have been trimmed to 50 chars(ish)", "CountryWithAStupidlyLongNameOrToPutItAnotherWa...", country.getDefaultName)
  }

  @Test def addShortName_long_shortname_trimmed() {
    val country = new Country("CC", "CCC", "123", "Country")
    country.addShortName("CountryWithAStupidlyLongShortName")
    assertEquals("Short Name should have been trimmed to 20 chars(ish)", "CountryWithAStup...", country.getShortName(CoreObject.DefaultLanguage))
  }
}

