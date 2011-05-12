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
 * @since 12/5/11 3:13 PM
 */

class MenuItemTest extends JUnitSuite {

  @Test def constructor_ok() {
    val menuitem = new MenuItem("item-one", "Item 1")
    assertEquals("Key", "item-one", menuitem.key)
    assertEquals("Display Order", 999, menuitem.displayOrder)
    assertEquals("Default Name", "Item 1", menuitem.defaultName)
    assertEquals("Number of Sub-MenuItems", 0, menuitem.subItems.size)
  }

  @Test def update_displayOrder() {
    val menuitem = new MenuItem("item-one", "Item 1")
    assertEquals("Display Order", 999, menuitem.displayOrder)
    menuitem.displayOrder = 10
    assertEquals("Display Order", 10, menuitem.displayOrder)
  }

  @Test def defaultName_ok() {
    val menuitem = new MenuItem("item-one", "Item 1")
    assertNotNull(menuitem.defaultName)
    assertEquals("Item 1", menuitem.defaultName)
  }
  
  @Test def add_subitem_ok() {
    val menuitem = new MenuItem("item-one", "Item 1")
    assertEquals("Number of SubItems", 0, menuitem.subItems.size)
    menuitem.addSubItem(new MenuItem("", ""))
    assertEquals("Number of SubItems", 1, menuitem.subItems.size)
  }

  @Test def add_multiple_menuitems_ok() {
    val menuitem = new MenuItem("item-one", "Item 1")
    assertEquals("Number of SubItems", 0, menuitem.subItems.size)
    menuitem.addSubItem(new MenuItem("sub-item-1","Item 1"))
    menuitem.addSubItem(new MenuItem("sub-item-2","Item 2"))
    assertEquals("Number of SubItems", 2, menuitem.subItems.size)
  }

  @Test def add_duplicate_menuitems() {
    val menuitem = new MenuItem("item-one", "Item 1")
    assertEquals("Number of SubItems", 0, menuitem.subItems.size)
    menuitem.addSubItem(new MenuItem("sub-item-1", "Item 1"))
    menuitem.addSubItem(new MenuItem("sub-item-1", "Item 1"))
    assertEquals("Number of SubItems", 1, menuitem.subItems.size)
  }
}