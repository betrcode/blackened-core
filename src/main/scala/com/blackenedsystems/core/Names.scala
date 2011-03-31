package com.blackenedsystems.core

import com.mongodb.casbah.commons.MongoDBObject
import com.mongodb.DBObject

/**
 * Provides a mechanism for the internationalisation of names associated with an object.
 *
 * @author Alan Tibbetts
 * @since 23/3/11 12:13 PM
 */

class Names(val languageCode: String) {

  private var _name: String = _
  private var _shortName: String = _

  def this(languageCode: String, name: String) {
    this (languageCode)
    _name = validateName(name)
  }

  def this(languageCode: String, name: String, shortName: String) {
    this (languageCode, name)
    _shortName = validateShortName(shortName)
  }

  def name = _name

  def name_=(name: String) {
    _name = validateName(name)
  }

  def shortName = _shortName

  def shortName_=(shortName: String) {
    _shortName = validateShortName(shortName)
  }

  private def validateName(name: String): String = {
    name match {
      case null => null
      case _ =>
        name.length() <= Names.MaxNameLength match {
          case true => name
          case false => name.substring(0, Names.MaxNameLength - 4) + "..."
        }
    }
  }

  private def validateShortName(shortName: String): String = {
    shortName match {
      case null => null
      case _ =>
        shortName.length() <= Names.MaxShortNameLength match {
          case true => shortName
          case false => shortName.substring(0, Names.MaxShortNameLength - 4) + "..."
        }
    }
  }

  def asDBObject: DBObject = {
    val builder = MongoDBObject.newBuilder
    builder += "lc" -> languageCode
    builder += "name" -> name
    builder += "shortName" -> shortName
    builder.result()
  }
}


object Names {

  val MaxNameLength = 50
  val MaxShortNameLength = 20

}
