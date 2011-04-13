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

package com.blackenedsystems.core.services

import com.blackenedsystems.core.dao.CountryDaoComponent
import com.blackenedsystems.core.Country

import grizzled.slf4j._

/**
 * @author Alan Tibbetts
 * @since 28/3/11 3:42 PM
 */

trait CountryServiceComponent { this: CountryDaoComponent =>

  val countryService: CountryService

  class CountryService extends Logging {

    def save(country: Country) = countryDao.save(country)

    def findByIsoCode2(isoCode2: String) = countryDao.findByIsoCode2(isoCode2)

    def find(id: String) = countryDao.find(id)

    def findAll() = countryDao.findAll()
  }

}