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

import com.mongodb.DBObject

import scala.collection.immutable.Set
import com.mongodb.casbah.commons.{MongoDBList, MongoDBObject}

/**
 * @author Alan Tibbetts
 * @since 11/5/11 9:41 PM
 */

class MenuItem (val key: String, defaultName: String) extends CoreObject {

  var subItems = Set[MenuItem]()

  addName(CoreObject.DefaultLanguage, defaultName)

  def addSubItem(subItem: MenuItem) {
    subItems += subItem
  }

  /**
   * Converts the current object to a (Mongo)DBObject.
   */
  def asDBObject: DBObject = {
    val builder = MongoDBObject.newBuilder

    builder += "key" -> key

    val listBuilder = MongoDBList.newBuilder
    subItems.foreach {
      subItem =>
        listBuilder += subItem.asDBObject
    }

    builder += "subItems" -> listBuilder.result()

    addCoreProperties(builder)

    builder.result()
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