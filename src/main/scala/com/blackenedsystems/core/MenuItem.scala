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

import scala.collection.JavaConversions._
import scala.collection.immutable.{Set, Map}
import com.mongodb.casbah.commons.MongoDBList
import com.mongodb.{BasicDBList, BasicDBObject, DBObject}

/**
 * @author Alan Tibbetts
 * @since 11/5/11 9:41 PM
 */

class MenuItem(val key: String, defaultName: String) extends CoreObject {

  var subItems = Set[MenuItem]()

  addName(CoreObject.DefaultLanguage, defaultName)

  def addSubItem(subItem: MenuItem) {
    subItems += subItem
  }

  /**
   * Converts the current object to a (Mongo)DBObject.
   */
  def asDBObject: DBObject = {
    val menuItemBuilder = MongoDBList.newBuilder
    subItems.foreach {
      subItem =>
        menuItemBuilder += subItem.asDBObject
    }

    super.asDbObject(Map[String, Any](
      "key" -> key,
      "subItems" -> menuItemBuilder.result()))
  }

  def canEqual(other: Any): Boolean = other.isInstanceOf[MenuItem]

  override def equals(other: Any): Boolean = {
    other match {
      case that: MenuItem =>
        (that canEqual this) && key == that.key
      case _ => false
    }
  }

  override def hashCode: Int =
    41 * (41 + key.hashCode)
}

object MenuItem {

  def apply(dbObject: BasicDBObject): MenuItem = {
    val menuItem = new MenuItem(dbObject.getString("key"), "")
    val subItems = dbObject.get("subItems").asInstanceOf[BasicDBList]
    subItems.foreach { item =>
      menuItem.addSubItem(MenuItem(item.asInstanceOf[BasicDBObject]))
    }
    menuItem.addCoreProperties(dbObject)
    menuItem
  }
}