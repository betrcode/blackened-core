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

import com.mongodb._

import casbah.commons.{MongoDBList, MongoDBObject}
import scala.collection.immutable.Set

/**
 * A single menu hierarchy.  Each menu can have a number of items.  These can in turn have sub-items, ad finitum.
 *
 * @author Alan Tibbetts
 * @since 11/5/11 9:30 PM
 */

class Menu(val key: String, defaultName: String) extends CoreObject {

  private var _id: String = _

  var menuItems = Set[MenuItem]()

  addName(CoreObject.DefaultLanguage, defaultName)

  def this(id: String, k: String, dn: String) {
    this (k, dn)
    _id = id
  }

  def id = _id

  def id_=(id: String) {
    _id = id
  }

  def addMenuItem(item: MenuItem) {
    menuItems += item
  }

  /**
   * Converts the current object to a (Mongo)DBObject.
   */
  def asDBObject: DBObject = {
    val builder = MongoDBObject.newBuilder

    builder += "key" -> key

    val listBuilder = MongoDBList.newBuilder
    menuItems.foreach {
      item =>
        listBuilder += item.asDBObject
    }

    builder += "menuItems" -> listBuilder.result()

    addCoreProperties(builder)

    builder.result()
  }

  def canEqual(other: Any): Boolean = other.isInstanceOf[Menu]

  override def equals(other: Any): Boolean = {
    other match {
      case that: Menu =>
        (that canEqual this) && key == that.key
      case _ => false
    }
  }

  override def hashCode: Int =
    41 * (41 + key.hashCode)
}

object Menu {

  def apply(dbObject: BasicDBObject): Menu = {
    val menu = new Menu(dbObject.getString(""), dbObject.getString("key"), "")
    menu.addCoreProperties(dbObject)
    menu
  }
}