package com.blackenedsystems.core.ioc

import com.blackenedsystems.core.services.{MenuServiceComponent, CountryServiceComponent}
import com.blackenedsystems.core.dao.{MenuDaoComponent, CountryDaoComponent}
import org.mockito.Mockito._
import com.blackenedsystems.core.mongodb.AuthenticatedDataSource

/**
 * DependencyInjection/IoC using the 'cake pattern' as detailed in the article by Jonas Boner:
 * http://jonasboner.com/2008/10/06/real-world-scala-dependency-injection-di.html
 *
 * @author Alan Tibbetts
 * @since 24/3/11 11:00 AM
 */
trait MockedComponentRegistry
        extends CountryServiceComponent
        with CountryDaoComponent
        with MenuServiceComponent
        with MenuDaoComponent {

  val dataSource = null   //TODO: Should be a Mock !!!

  val countryDao = new CountryDao(dataSource)
  val countryService = new CountryService

  val menuDao = new MenuDao(dataSource)
  val menuService = new MenuService
}