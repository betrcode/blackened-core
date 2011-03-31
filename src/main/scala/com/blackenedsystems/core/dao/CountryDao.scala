package com.blackenedsystems.core.dao

import grizzled.slf4j._
import com.mongodb.casbah.commons.MongoDBObject

import com.blackenedsystems.core.Country
import com.blackenedsystems.core.mongodb.AuthenticatedDataSource
import com.mongodb.BasicDBObject


/**
 * @author Alan Tibbetts
 * @since 28/3/11 3:37 PM
 */

trait CountryDaoComponent {

  val countryDao: CountryDao

  class CountryDao(dataSource: AuthenticatedDataSource) extends Logging {

    def save(country: Country): Country = {
      val collection = dataSource.getCollection(CountryDao.collectionName)
      val wr = collection.save(country.asDBObject)
      country
    }

    def findByIsoCode2(isoCode2: String): Country = {
      val collection = dataSource.getCollection(CountryDao.collectionName)
      val dbObj = collection.findOne(MongoDBObject("iso2" -> isoCode2.toUpperCase))
      dbObj match {
        case Some(d) => Country(d.asInstanceOf[BasicDBObject])
        case None => null
      }
    }
  }

  object CountryDao {
    val collectionName: String = "Country"
  }

}