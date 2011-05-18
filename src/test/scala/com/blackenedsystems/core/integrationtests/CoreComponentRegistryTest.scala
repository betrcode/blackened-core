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

import com.blackenedsystems.core.ioc.CoreComponentRegistry
import org.scalatest.junit.JUnitSuite

import org.junit.Test
import org.junit.Assert._
import grizzled.slf4j._

/**
 * Tests that verify the component registry includes all the object we expect.   Detailed
 * functional testing of the various components can be found elsewhere.
 *
 * @author Alan Tibbetts
 * @since 28/3/11 5:31 PM
 */

class CoreComponentRegistryTest extends JUnitSuite with Logging {

  val invalidMongoObjectId: String = "123412341234123412341234"

  @Test def definedObjects_country_related_ok() {
    val countryService = CoreComponentRegistry.countryService
    assertNotNull(countryService)

    val countryDao = CoreComponentRegistry.countryDao
    assertNotNull(countryDao)
  }

  @Test def executeQuery_countryService_ok() {

    val countryService = CoreComponentRegistry.countryService

    val country = countryService.findByIsoCode2("11")
    country match {
      case Some(c) => fail("Should not have found a country")
      case None => // Do Nothing
    }
  }

  @Test def definedObjects_menu_related_ok() {
    val menuService = CoreComponentRegistry.menuService
    assertNotNull(menuService)

    val menuDao = CoreComponentRegistry.menuDao
    assertNotNull(menuDao)
  }

  @Test def executeQuery_menuService_ok() {
    val menuService = CoreComponentRegistry.menuService

    val menu = menuService.find(invalidMongoObjectId)
    menu match {
      case Some(m) => fail("Should not have found a menu")
      case None => // Do Nothing
    }
  }

}