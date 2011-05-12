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

package com.blackenedsystems.core

import org.scalatest.junit.JUnitSuite
import org.junit.Assert._
import org.junit.Test

/**
 * @author Alan Tibbetts
 * @since 12/5/11 2:40 PM
 */

class MenuTest extends JUnitSuite {

  @Test def constructor_ok() {
    val menu = new Menu("mnu-home", "Home")
    assertEquals("Key", "mnu-home", menu.key)
    assertEquals("Display Order", 999, menu.displayOrder)
    assertEquals("Default Name", "Home", menu.defaultName)
    assertEquals("Number of MenuItems", 0, menu.menuItems.size)
  }

  @Test def update_displayOrder() {
    val menu = new Menu("mnu-home", "Home")
    assertEquals("Display Order", 999, menu.displayOrder)
    menu.displayOrder = 10
    assertEquals("Display Order", 10, menu.displayOrder)
  }

  @Test def defaultName_ok() {
    val menu = new Menu("mnu-home", "Home")
    assertNotNull(menu.defaultName)
    assertEquals("Home", menu.defaultName)
  }

  @Test def add_menuitem_ok() {
    val menu = new Menu("mnu-home", "Home")
    assertEquals("Number of MenuItems", 0, menu.menuItems.size)
    menu.addMenuItem(new MenuItem("", ""))
    assertEquals("Number of MenuItems", 1, menu.menuItems.size)
  }

  @Test def add_multiple_menuitems_ok() {
    val menu = new Menu("mnu-home", "Home")
    assertEquals("Number of MenuItems", 0, menu.menuItems.size)
    menu.addMenuItem(new MenuItem("item-1","Item 1"))
    menu.addMenuItem(new MenuItem("item-2","Item 2"))
    assertEquals("Number of MenuItems", 2, menu.menuItems.size)
  }

  @Test def add_duplicate_menuitems() {
    val menu = new Menu("mnu-home", "Home")
    assertEquals("Number of MenuItems", 0, menu.menuItems.size)
    menu.addMenuItem(new MenuItem("item-1", "Item 1"))
    menu.addMenuItem(new MenuItem("item-1", "Item 1"))
    assertEquals("Number of MenuItems", 1, menu.menuItems.size)
  }
}