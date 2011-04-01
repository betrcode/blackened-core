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

  private var _id: String = _

  addName(CoreObject.DefaultLanguage, defaultName)

  def this(id: String, iso2: String, iso3: String, isoNum: String, defName: String) {
    this (iso2, iso3, isoNum, defName)
    _id = id
  }

  def id = _id

  def id_= (id: String) {
    _id = id
  }

  def canEqual(other: Any): Boolean = other.isInstanceOf[Country]

  override def equals(other: Any): Boolean = {
    other match {
      case that: Country =>
        (that canEqual this) &&
        isoCode2 == that.isoCode2
      case _ => false
    }
  }

  override def hashCode: Int =
    41 * (41 + isoCode2.hashCode)


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

    val country = new Country(dbObject.getString("_id"), dbObject.getString("iso2"), dbObject.getString("iso3"), dbObject.getString("isoNum"), "")
    country.addCoreProperties(dbObject)

    country

  }
}