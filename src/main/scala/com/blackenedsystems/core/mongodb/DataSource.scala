package com.blackenedsystems.core.mongodb

import com.mongodb.casbah._
import com.mongodb.casbah.commons.Imports._
import com.mongodb.casbah.commons.conversions.scala._


/**
 * @author Alan Tibbetts
 * @since 24/3/11 11:14 AM
 */

class DataSource(host: String, ip: Int, databaseName: String, userName: String, password: String) {

  RegisterJodaTimeConversionHelpers()

  val mongoConn = MongoConnection(host, ip)
  val mongoDB = mongoConn(databaseName)

  if (!mongoDB.authenticate(userName, password)) {
    throw new Exception("Cannot authenticate.  Login failed.")
  }

  def getConnection() = mongoDB

  def getCollection(collectionName: String) = mongoDB(collectionName)
}