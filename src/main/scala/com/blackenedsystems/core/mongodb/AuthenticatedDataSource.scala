package com.blackenedsystems.core.mongodb

import grizzled.slf4j._

import com.mongodb.casbah._
import com.mongodb.casbah.commons.conversions.scala._
import commons.MongoDBObject

/**
 * @author Alan Tibbetts
 * @since 24/3/11 11:14 AM
 */

class AuthenticatedDataSource(host: String, ip: Int, databaseName: String, userName: String, password: String) extends Logging {

  RegisterJodaTimeConversionHelpers()

  val mongoConn = MongoConnection(host, ip)
  val mongoDB = mongoConn(databaseName)

  if (!mongoDB.authenticate(userName, password)) {
    throw new Exception("Cannot authenticate.  Login failed.")
  }

  info("Initialised dataSource: host: " + host + ", ip: " + ip + ", database: " + databaseName + ", user: " + userName)

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