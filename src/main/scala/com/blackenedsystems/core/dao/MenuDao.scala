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

package com.blackenedsystems.core.dao

import com.blackenedsystems.core.mongodb.AuthenticatedDataSource
import grizzled.slf4j.Logging
import org.bson.types.ObjectId
import com.mongodb.casbah.commons.MongoDBObject
import com.blackenedsystems.core.Menu
import com.mongodb.{DBObject, BasicDBObject}

/**
 * @author Alan Tibbetts
 * @since 18/5/11 4:12 PM
 */

trait MenuDaoComponent {

  val menuDao: MenuDao

  class MenuDao(dataSource: AuthenticatedDataSource) extends MongoDbDao with Logging {

    def collectionName = "menu"

    def find(id: String): Option[Menu] = {
      val collection = dataSource.getCollection(collectionName)
      val dbObj = collection.findOne(MongoDBObject("_id" -> new ObjectId(id)))
      dbObj match {
        case Some(d) => Some(Menu(d.asInstanceOf[BasicDBObject]))
        case None => None
      }
    }

    def findByKey(key: String): Option[Menu] = {
      val collection = dataSource.getCollection(collectionName)
      val dbObj = collection.findOne(MongoDBObject("key" -> key.toLowerCase))
      dbObj match {
        case Some(d) => Some(Menu(d.asInstanceOf[BasicDBObject]))
        case None => None
      }
    }

    /**
     * Inserts (where _id is null) or Updates the given Menu object
     */
    def save(menu: Menu): Menu = {
      val collection = dataSource.getCollection(collectionName)
      val menuDBObject: DBObject = menu.asDBObject
      if (menu.id == null) {
        val wr = collection.save(menuDBObject)
        menu.id = menuDBObject.get("_id").asInstanceOf[ObjectId].toString
      } else {
        val wr = collection.update(MongoDBObject("_id" -> new ObjectId(menu.id)), menuDBObject)
      }
      menu
    }
  }

}