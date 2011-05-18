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

import org.scalatest.junit.JUnitSuite
import grizzled.slf4j.Logging
import com.blackenedsystems.core.ioc.CoreComponentRegistry
import com.blackenedsystems.core.Menu
import org.junit.{Test, Before}
import org.junit.Assert._

/**
 * @author Alan Tibbetts
 * @since 18/5/11 4:49 PM
 */

class MenuServiceTest extends JUnitSuite with Logging {

  val menuService = CoreComponentRegistry.menuService
  val homeMenuKey: String = "mnu-home"

  @Before def setUp() {
    CoreComponentRegistry.dataSource.removeCollection(menuService.collectionName)

    val menu = new Menu(homeMenuKey, "Home")
    menu.addName("SE", "Hem")
    menuService.save(menu)
  }

  @Test def find_home_ok() {
    val menu = menuService.findByKey(homeMenuKey)
    validateHomeMenu(menu)
  }

  @Test def find_home_mixed_case_key_ok() {
    val menu = menuService.findByKey("MNU-Home")
    validateHomeMenu(menu)
  }

  private def validateHomeMenu(menu: Option[Menu]) {
    menu match {
      case Some(m) =>
        assertEquals("Default Name", "Home", m.defaultName)
        assertEquals("Name (Swedish)", "Hem", m.getName("SE"))

      case None => fail("Did not find the home menu")
    }

  }
}