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

package com.blackenedsystems.core.mongodb

import grizzled.slf4j._

import com.mongodb.casbah._
import com.mongodb.casbah.commons.conversions.scala._
import commons.MongoDBObject

/**
 * @author Alan Tibbetts
 * @since 24/3/11 11:14 AM
 */

class AuthenticatedDataSource(host: String, port: Int, databaseName: String, userName: String, password: String) extends Logging {

  RegisterJodaTimeConversionHelpers()

  val mongoConn = MongoConnection(host, port)
  val mongoDB = mongoConn(databaseName)

  if (!mongoDB.authenticate(userName, password)) {
    throw new Exception("Cannot authenticate.  Login failed.")
  }

  info("Initialised dataSource: host: " + host + ", port: " + port + ", database: " + databaseName + ", user: " + userName)

  def getConnection = mongoDB

  def getCollection(collectionName: String) = mongoDB(collectionName)

  /**
   * Deletes all documents in the specified collection.
   */
  def removeCollection(collectionName: String) = {
    val coll = getCollection(collectionName)
    coll.remove(MongoDBObject())
  }
}