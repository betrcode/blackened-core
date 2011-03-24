package com.blackenedsystems.core.integrationtests

import com.blackenedsystems.core.mongodb.DataSource
import org.scalatest.junit.JUnitSuite
import org.junit.Test
import org.junit.Assert._
import com.mongodb.casbah.commons.MongoDBObject


/**
 * Integration tests that ensure the MongoDB datasource does what is exepected of it.
 *
 * @author Alan Tibbetts
 * @since 24/3/11 11:26 AM
 */

class DataSourceTest extends JUnitSuite {

  @Test def connection_ok() {
    val dataSource = new DataSource("localhost", 27017, "blackened-test", "blackened", "testpassword")
  }

  @Test
  def connection_fails_authentication() {
    try {
      val dataSource = new DataSource("localhost", 27017, "blackened-test", "blackened", "wrong")
      fail("Should have thrown an exception")
    } catch {
      case ex: Exception => println("okay dokey")
    }
  }

  @Test def executeQuery_ok() {
    val dataSource = new DataSource("localhost", 27017, "blackened-test", "blackened", "testpassword")

    val q = MongoDBObject("username" -> "junit")
    val user = dataSource.getCollection("JunitUser").findOne(q)
    user match {
      case Some(x) => assertEquals("JUnit User", x.get("name"))
      case None => fail("Could not find JUnitUser")
    }
  }

}