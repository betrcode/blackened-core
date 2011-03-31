package com.blackenedsystems.core.ioc

import com.blackenedsystems.core.dao._
import com.blackenedsystems.core.services._
import com.blackenedsystems.core.mongodb._

/**
 * @author Alan Tibbetts
 * @since 24/3/11 11:00 AM
 */
object CoreComponentRegistry extends CountryServiceComponent with CountryDaoComponent {

  val dataSource = new AuthenticatedDataSource ("localhost", 27017, "blackened-core-dev", "blackened", "devpassword")

  val countryDao = new CountryDao(dataSource)
  val countryService = new CountryService
}