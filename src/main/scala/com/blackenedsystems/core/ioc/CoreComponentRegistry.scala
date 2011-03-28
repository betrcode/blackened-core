package com.blackenedsystems.core.ioc

import scala.collection.immutable.Map

import com.blackenedsystems.core.dao._
import com.blackenedsystems.core.services._
import com.blackenedsystems.core.mongodb._

/**
 * @author Alan Tibbetts
 * @since 24/3/11 11:00 AM
 */
object CoreComponentRegistry extends CountryServiceComponent with CountryDaoComponent {

  val dataSource = new DataSource ("localhost", 27017, "blackened-test", "blackened", "testpassword")

  val countryDao = new CountryDao(dataSource)
  val countryService = new CountryService
}