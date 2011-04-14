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

import grizzled.slf4j._

import com.mongodb.casbah.commons.MongoDBObject

import com.blackenedsystems.core.Country
import com.blackenedsystems.core.mongodb.AuthenticatedDataSource
import org.bson.types.ObjectId
import com.mongodb.{DBObject, BasicDBObject}

/**
 * @author Alan Tibbetts
 * @since 28/3/11 3:37 PM
 */

trait CountryDaoComponent {

  val countryDao: CountryDao

  class CountryDao(dataSource: AuthenticatedDataSource) extends Logging {

    /**
     * Inserts (where _id is null) or Updates the given Country object
     */
    def save(country: Country): Country = {
      val collection = dataSource.getCollection(CountryDao.collectionName)
      val countryDBObject: DBObject = country.asDBObject
      if (country.id == null) {
        val wr = collection.save(countryDBObject)
        country.id = countryDBObject.get("_id").asInstanceOf[ObjectId].toString
      } else {
        val wr = collection.update(MongoDBObject("_id" -> new ObjectId(country.id)), countryDBObject)
      }
      country
    }

    def findByIsoCode2(isoCode2: String): Option[Country] = {
      val collection = dataSource.getCollection(CountryDao.collectionName)
      val dbObj = collection.findOne(MongoDBObject("iso2" -> isoCode2.toUpperCase))
      dbObj match {
        case Some(d) => Some(Country(d.asInstanceOf[BasicDBObject]))
        case None => None
      }
    }

    def find(id: String): Option[Country] = {
      val collection = dataSource.getCollection(CountryDao.collectionName)
      val dbObj = collection.findOne(MongoDBObject("_id" -> new ObjectId(id)))
      dbObj match {
        case Some(d) => Some(Country(d.asInstanceOf[BasicDBObject]))
        case None => None
      }
    }

    def findAll(): Set[Country] = {
      val collection = dataSource.getCollection(CountryDao.collectionName)
      var countries = Set[Country]()
      for( x <- collection.find()) {
        countries += Country(x.asInstanceOf[BasicDBObject])
      }
      countries
    }
  }

  object CountryDao {
    val collectionName: String = "Country"
  }

}