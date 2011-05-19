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

package com.blackenedsystems.core.integrationtests

import com.blackenedsystems.core.dao.CountryDaoComponent
import com.blackenedsystems.core.dao.MenuDaoComponent
import com.blackenedsystems.core.mongodb._
import com.blackenedsystems.core.services.CountryServiceComponent
import com.blackenedsystems.core.services.MenuServiceComponent

/**
 * DependencyInjection/IoC using the 'cake pattern' as detailed in the article by Jonas Boner:
 * http://jonasboner.com/2008/10/06/real-world-scala-dependency-injection-di.html
 *
 * @author Alan Tibbetts
 * @since 24/3/11 11:00 AM
 */
object TestingComponentRegistry
        extends CountryServiceComponent
        with CountryDaoComponent
        with MenuServiceComponent
        with MenuDaoComponent {

  //TODO: how to replicate Spring's propertyplaceholder?  Then I can remove this hard-coded nonsense.
  val dataSource = new AuthenticatedDataSource("localhost", 27017, "blackened-core-test", "blackened", "testpassword")

  val countryDao = new CountryDao(dataSource)
  val countryService = new CountryService

  val menuDao = new MenuDao(dataSource)
  val menuService = new MenuService
}