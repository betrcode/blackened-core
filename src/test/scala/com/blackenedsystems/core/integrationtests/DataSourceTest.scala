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

import com.blackenedsystems.core.mongodb.AuthenticatedDataSource
import org.scalatest.junit.JUnitSuite
import org.junit.Test
import org.junit.Assert._
import com.mongodb.casbah.commons.MongoDBObject


/**
 * Integration tests that ensure the MongoDB data source does what is expected of it.
 *
 * @author Alan Tibbetts
 * @since 24/3/11 11:26 AM
 */

class DataSourceTest extends JUnitSuite {

  @Test def connection_ok() {
    val dataSource = new AuthenticatedDataSource("localhost", 27017, "blackened-core-test", "blackened", "testpassword")
  }

  @Test
  def connection_fails_authentication() {
    try {
      val dataSource = new AuthenticatedDataSource("localhost", 27017, "blackened-core-test", "blackened", "wrong")
      fail("Should have thrown an exception")
    } catch {
      case ex: Exception => // Do Nothing
    }
  }

  @Test def executeQuery_ok() {
    val dataSource = new AuthenticatedDataSource("localhost", 27017, "blackened-core-test", "blackened", "testpassword")

    val q = MongoDBObject("username" -> "junit")
    val user = dataSource.getCollection("JunitUser").findOne(q)
    user match {
      case Some(x) => assertEquals("JUnit User", x.get("name"))
      case None => fail("Could not find JUnitUser")
    }
  }

}