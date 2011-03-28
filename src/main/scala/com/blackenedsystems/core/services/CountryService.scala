package com.blackenedsystems.core.services

import com.blackenedsystems.core.dao.CountryDaoComponent
import com.blackenedsystems.core.Country

import grizzled.slf4j._
import com.mongodb.casbah.commons.MongoDBObject


/**
 * @author Alan Tibbetts
 * @since 28/3/11 3:42 PM
 */

trait CountryServiceComponent { this: CountryDaoComponent =>

  val countryService: CountryService

  class CountryService extends Logging {

    def create(country: Country) = countryDao.create(country)

    def update(country: Country) = countryDao.update(country)

    def findByIsoCode2(isoCode2: String) = countryDao.findByIsoCode2(isoCode2)
  }

}