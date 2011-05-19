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
import org.junit.{Test, Before}
import org.junit.Assert._
import com.blackenedsystems.core.{MenuItem, Menu}

/**
 * @author Alan Tibbetts
 * @since 18/5/11 4:49 PM
 */

class MenuServiceTest extends JUnitSuite with Logging {

  val menuService = TestingComponentRegistry.menuService
  val homeMenuKey: String = "mnu-home"

  @Before def setUp() {
    TestingComponentRegistry.dataSource.removeCollection(menuService.collectionName)

    val menu = new Menu(homeMenuKey, "Home")
    menu.addName("SE", "Hem")

    val item1 = new MenuItem("mi-item1", "Item 1")
    val subItem1 = new MenuItem("mi-subitem1", "Sub Item 1")
    item1.addSubItem(subItem1)
    menu.addMenuItem(item1)

    val item2 = new MenuItem("mi-item2", "Item 2")
    menu.addMenuItem(item2)

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
        assertEquals("Number of Menu Items", 2, m.menuItems.size)

        val item1 = m.getMenuItem("mi-item1")
        item1 match {
          case Some(mi) =>
            assertEquals("MenuItem1 Default Name", "Item 1", mi.defaultName)
            assertEquals("Number of Sub Items", 1, mi.subItems.size)

          case None => fail("Did not find item 1")
        }

        val item2 = m.getMenuItem("mi-item2")
        item2 match {
          case Some(mi) =>
            assertEquals("MenuItem2 Default Name", "Item 2", mi.defaultName)
            assertEquals("Number of Sub Items", 0, mi.subItems.size)

          case None => fail("Did not find item 2")
        }

      case None => fail("Did not find the home menu")
    }

  }
}