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

import scala.collection.mutable.Map
import scala.collection.immutable.List

import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import com.mongodb.casbah.commons.{MongoDBList, MongoDBObjectBuilder}
import com.mongodb.{BasicDBList, BasicDBObject}
import collection.JavaConversions._

/**
 * Models core properties that are applicable to all other objects within the system.
 *
 * @author Alan Tibbetts
 * @since 23/3/11 11:07 AM
 */

abstract class CoreObject {

  var displayOrder = 999
  var updateTime: DateTime = new DateTime(DateTimeZone.UTC)
  var names = Map[String, Names]()

  /**
   * Returns the name of this object in the default language, or an empty string if
   * it does not exist.
   */
  def defaultName: String = {
    names.get(CoreObject.DefaultLanguage) match {
      case Some(x) => x.name
      case None => ""
    }
  }

  /**
   * Returns the name of this object in the required language, or the default language if
   * the translation for the requested language does not exist.
   */
  def getName(languageCode: String): String = {
    val lc = languageCode.toLowerCase
    findNames(lc, CoreObject.CheckDefaultLanguage) match {
      case Some(x) => x.name
      case None => ""
    }
  }

  /**
   * Adds a name using the default language. If a name already exists for the language, it
   * will be overwritten.
   */
  def addName(name: String) {
    addName(CoreObject.DefaultLanguage, name)
  }

  /**
   * Adds a name using the supplied language.  If a name already exists for the language, it
   * will be overwritten.
   *
   * Null or empty names will be ignored
   */
  def addName(languageCode: String, name: String) {
    val lc = languageCode.toLowerCase
    if (name != null && !name.isEmpty) {
      val n = findOrCreateNames(lc)
      n.name = name
    }
  }

  def getShortName(languageCode: String): String = {
    val lc = languageCode.toLowerCase
    findNames(lc, !CoreObject.CheckDefaultLanguage) match {
      case Some(x) => matchShortName(lc, x)
      case None => if (lc == CoreObject.DefaultLanguage) "" else getShortName(CoreObject.DefaultLanguage)
    }
  }

  private def matchShortName(languageCode: String, name: Names): String = {
    name.shortName match {
      case null =>
        name.name match {
          case null => getName(languageCode)
          case _ => name.name
        }
      case _ => name.shortName
    }
  }

  /**
   * Adds a short name using the default language. If a short name already exists for the language, it
   * will be overwritten.
   */
  def addShortName(shortName: String) {
    addShortName(CoreObject.DefaultLanguage, shortName)
  }


  /**
   * Adds a short name using the supplied language.  If a short name already exists for the language, it
   * will be overwritten.
   *
   * If the name for the language we are using is null or empty, it will be set to the given short name.
   */
  def addShortName(languageCode: String, shortName: String) {
    val lc = languageCode.toLowerCase
    if (shortName != null && !shortName.isEmpty) {
      val n = findOrCreateNames(lc)
      n.shortName = shortName
      if (n.name == null || n.name.isEmpty) {
        n.name = shortName
      }
    }
  }

  private def findNames(languageCode: String, checkForDefault: Boolean): Option[Names] = {
    names.get(languageCode) match {
      case Some(x) => Some(x)
      case None =>
        if (checkForDefault) {
          names.get(CoreObject.DefaultLanguage) match {
            case Some(x) => Some(x)
            case None => None
          }
        } else {
          None
        }

    }
  }

  private def findOrCreateNames(languageCode: String): Names = {
    names.get(languageCode) match {
      case Some(x) => x
      case None => createName(languageCode)
    }
  }

  private def createName(languageCode: String): Names = {
    val newName = new Names(languageCode)
    names.put(newName.languageCode, newName)
    newName
  }

  /**
   * Returns a list of language codes for which we have valid names.
   */
  def nameLanguages: List[String] = {
    var languageList = List[String]()
    names.foreach {
      elem =>
        val name = elem._2
        if (name.name != null && !name.name.isEmpty) {
          languageList = name.languageCode :: languageList
        }
    }
    languageList
  }

  /**
   * Returns a list of language codes for which we have valid short names.
   */
  def shortNameLanguages: List[String] = {
    var languageList = List[String]()
    names.foreach {
      elem =>
        val name = elem._2
        if (name.shortName != null && !name.shortName.isEmpty) {
          languageList = name.languageCode :: languageList
        }
    }
    languageList
  }

  /**
   * Adds the various CoreObject properties to the given (Mongo)DBObjectBuilder.
   */
  def addCoreProperties(builder: MongoDBObjectBuilder) = {
    builder += "dispOrd" -> displayOrder
    builder += "updTime" -> updateTime

    val listBuilder = MongoDBList.newBuilder
    names.foreach {
      elem =>
        val n = elem._2
        listBuilder += n.asDBObject
    }

    builder += "names" -> listBuilder.result()
  }

  /**
   * Extracts the various CoreObject properties from the given (Mongo)BasicDBObject.
   */
  def addCoreProperties(dbObject: BasicDBObject) {

    displayOrder = dbObject.getInt("dispOrd")
    updateTime = dbObject.get("updTime").asInstanceOf[DateTime]

    val names = dbObject.get("names").asInstanceOf[BasicDBList]
    names.foreach(dbObject => {
      val dbObj = dbObject.asInstanceOf[BasicDBObject]
      val name = new Names(dbObj.getString("lc"), dbObj.getString("name"), dbObj.getString("shortName"))
      addName(name.languageCode, name.name)
      addShortName(name.languageCode, name.shortName)
    })
  }
}


object CoreObject {

  val DefaultLanguage: String = "en"
  val CheckDefaultLanguage: Boolean = true
}