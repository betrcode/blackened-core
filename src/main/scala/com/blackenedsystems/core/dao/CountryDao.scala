package com.blackenedsystems.core.dao

import grizzled.slf4j._
import com.mongodb.casbah.commons.MongoDBObject

import com.blackenedsystems.core.Country
import com.blackenedsystems.core.mongodb.DataSource

/**
 * @author Alan Tibbetts
 * @since 28/3/11 3:37 PM
 */

trait CountryDaoComponent {

  val countryDao: CountryDao
  val collectionName: String = "Country"

  class CountryDao(dataSource: DataSource) extends Logging {
    def create(country: Country) = info("Persisting a Country object")

    def update(country: Country) = info("Updating a Country object")

    def findByIsoCode2(isoCode2: String) = {
      val collection = dataSource.getCollection(collectionName)
      collection.findOne(MongoDBObject("isoCode2" -> isoCode2))
    }
  }

}