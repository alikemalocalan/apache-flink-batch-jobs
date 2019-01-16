package com.github.alikemalocalan.flink

import org.apache.flink.api.common.operators.Order
import org.apache.flink.api.scala._
import org.apache.flink.util.Collector


case class ProductCount(productId: Int, count: Int)

case class EventCount(eventName: String, count: Int)

case class Event(eventName: String, productId: Int)

case class FulfilledUser(userId: Int, count: Int)

object JobTransform {

  def uniqProductView(userActivity: DataSet[UserAction]): DataSet[ProductCount] = userActivity
    //.filter(_.eventName.equals("view"))
    .map(data => (data.productId, data.userId))
    .groupBy(_._1)
    .reduceGroup { (in, out: Collector[ProductCount]) => {
      val distinctList = in.toList.distinct
      val productID = distinctList.head._1
      out.collect(ProductCount(productID, distinctList.size))
    }
    }

  def productViewByUser(userActivity: DataSet[UserAction], userID: Int): DataSet[ProductCount] =
    userActivity
      .filter(_.userId == userID)
      .filter(_.eventName.equals("view"))
      .map(event => ProductCount(event.productId, 1)).distinct()


  def uniqEvent(userActivity: DataSet[UserAction]): DataSet[EventCount] =
    userActivity
      .map(data => (data.eventName, data.userId))
      .groupBy(_._1)
      .reduceGroup { (in, out: Collector[EventCount]) => {
        val distinctList = in.toList.distinct
        val eventName = distinctList.head._1
        out.collect(EventCount(eventName, distinctList.size))
      }
      }

  //.map(data => EventCount(data.eventName, data.count))


  def allEventForUser(userActivity: DataSet[UserAction], userID: Int): DataSet[Event] = {
    userActivity
      .filter(_.userId == userID)
      .map(events => Event(events.eventName, events.productId))
  }

  def topUsers(userActivity: DataSet[UserAction]): DataSet[FulfilledUser] = userActivity
    .groupBy(_.userId)
    .reduceGroup { (in, out: Collector[Option[FulfilledUser]]) => {
      import scalaz.Scalaz._
      val events = in.toList
      val eventTypeSize = events.map(_.eventName).distinct.size
      if (eventTypeSize == 4) {
        val eventCount = events.map(event => Map(event.eventName -> 1)).suml.minBy(_._2)._2
        out.collect(Some(FulfilledUser(events.head.userId, eventCount)))
      } else out.collect(None)
    }
    }.filter(_.isDefined)
    .map(_.get)
    .setParallelism(1)
    .sortPartition(_.count, Order.DESCENDING)
    .first(5)
}
