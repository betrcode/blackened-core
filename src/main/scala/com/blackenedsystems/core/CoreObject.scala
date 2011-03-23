package com.blackenedsystems.core

import scala.collection.mutable.Map
import scala.collection.immutable.List

import org.joda.time.DateTime
import org.joda.time.DateTimeZone

/**
 * @author Alan Tibbetts
 * @since 23/3/11 11:07 AM
 */

abstract class CoreObject {

  var displayOrder = 999
  var updateTime = new DateTime(DateTimeZone.UTC)
  var names = Map[String, Names]()

  def getDefaultName(): String = {
    names.get(CoreObject.DefaultLanguage) match {
      case Some(x) => x.name
      case None => ""
    }
  }

  def getName(languageCode: String): String = {
    val lc = languageCode.toLowerCase()
    findNames(lc, CoreObject.CheckDefaultLanguage) match {
      case Some(x) => x.name
      case None => ""
    }
  }

  /**
   * Adds a name using the default language. If a name already exists for the language, it
   * will be overwritten.
   */
  def addName(name: String): Unit = {
    addName(CoreObject.DefaultLanguage, name)
  }

  /**
   * Adds a name using the supplied language.  If a name already exists for the language, it
   * will be overwritten.
   *
   * Null or empty names will be ignored
   */
  def addName(languageCode: String, name: String): Unit = {
    val lc = languageCode.toLowerCase()
    if (name != null && !name.isEmpty()) {
      val n = findOrCreateNames(lc)
      n.name = name
    }
  }

  def getShortName(languageCode: String): String = {
    val lc = languageCode.toLowerCase()
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
  def addShortName(languageCode: String, shortName: String): Unit = {
    val lc = languageCode.toLowerCase()
    if (shortName != null && !shortName.isEmpty()) {
      val n = findOrCreateNames(lc)
      n.shortName = shortName
      if (n.name == null || n.name.isEmpty()) {
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
  def getNameLanguages(): List[String] = {
    var languageList = List[String]()
    names.foreach{
      elem =>
        val name = elem._2
        if (name.name != null && !name.name.isEmpty()) {
          languageList = name.languageCode :: languageList
        }
    }
    languageList
  }

  /**
   * Returns a list of language codes for which we have valid short names.
   */
  def getShortNameLanguages(): List[String] = {
    var languageList = List[String]()
    names.foreach{
      elem =>
        val name = elem._2
        if (name.shortName != null && !name.shortName.isEmpty()) {
          languageList = name.languageCode :: languageList
        }
    }
    languageList
  }
}


object CoreObject {

  val DefaultLanguage: String = "en"
  val CheckDefaultLanguage: Boolean = true
}