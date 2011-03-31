package com.blackenedsystems.core

import com.mongodb.casbah.commons.MongoDBObject
import com.mongodb._

/**
 * Models a physical country, as defined by its various isocodes.
 *
 * @author Alan Tibbetts
 * @since 22/3/11 5:26 PM
 */

class Country(val isoCode2: String, val isoCode3: String, val isoCodeNumeric: String, defaultName: String) extends CoreObject {

  addName(CoreObject.DefaultLanguage, defaultName)

  /**
   * Converts the current object to a (Mongo)DBObject.
   */
  def asDBObject: DBObject = {
    val builder = MongoDBObject.newBuilder
    builder += "iso2" -> isoCode2.toUpperCase
    builder += "iso3" -> isoCode3.toUpperCase
    builder += "isoNum" -> isoCodeNumeric

    addCoreProperties(builder)

    builder.result()
  }
}


object Country {

  def apply(dbObject: BasicDBObject): Country = {

    val country = new Country(dbObject.getString("iso2"), dbObject.getString("iso3"), dbObject.getString("isoNum"), "")
    country.addCoreProperties(dbObject)

    country

  }
}