package com.blackenedsystems.core

/**
 * @author Alan Tibbetts
 * @since 22/3/11 5:26 PM
 */

class Country(val isoCode2: String, val isoCode3: String, val isoCodeNumeric: String, defaultName: String) extends CoreObject {

  addName(CoreObject.DefaultLanguage, defaultName)

}