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

import com.blackenedsystems.core.dao.MenuDaoComponent
import grizzled.slf4j._
import com.blackenedsystems.core.Menu

/**
 * @author Alan Tibbetts
 * @since 18/5/11 4:29 PM
 */

trait MenuServiceComponent { this: MenuDaoComponent =>

  val menuService: MenuService

  class MenuService extends Logging {
    def collectionName = menuDao.collectionName

    def find(id: String) = menuDao.find(id)

    def findByKey(key: String)  = menuDao.findByKey(key)

    def save(menu: Menu) = menuDao.save(menu)

  }

}

