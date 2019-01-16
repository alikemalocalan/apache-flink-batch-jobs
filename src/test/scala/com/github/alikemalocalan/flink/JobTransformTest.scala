package com.github.alikemalocalan.flink

import org.apache.flink.api.scala._
import org.scalatest.{FunSuite, Matchers}

class JobTransformTest extends FunSuite with Matchers {


  val env: ExecutionEnvironment = ExecutionEnvironment.getExecutionEnvironment
  env.setParallelism(1)

  test("allEventForUser") {
    val userActivity = env.fromCollection(CsvExampleData.exampleCsvList)
    val result: List[Event] = JobTransform.allEventForUser(userActivity, 47).collect().toList
    result should contain theSameElementsAs List(
      Event("add", 618),
      Event("click", 618),
      Event("remove", 618),
      Event("view", 618))
  }

  test("topUsers") {
    val userActivity = env.fromCollection(CsvExampleData.exampleCsvList)
    val result = JobTransform.topUsers(userActivity).collect().toList
    result should contain theSameElementsAs List(FulfilledUser(47, 1))
  }

  test("uniqProductView") {
    val userActivity = env.fromCollection(CsvExampleData.exampleCsvList)
    val result = JobTransform.uniqProductView(userActivity).collect().toList
    result should contain theSameElementsAs List(
      ProductCount(618, 1),
      ProductCount(496, 1),
      ProductCount(644, 2))
  }

  test("uniqEvent") {
    val userActivity = env.fromCollection(CsvExampleData.exampleCsvList)
    val result = JobTransform.uniqEvent(userActivity).collect().toList
    result should contain theSameElementsAs List(
      EventCount("add", 2),
      EventCount("remove", 2),
      EventCount("click", 2),
      EventCount("view", 2))
  }

  test("productViewByUser") {
    val userActivity = env.fromCollection(CsvExampleData.exampleCsvList)
    val result = JobTransform.productViewByUser(userActivity, 47).collect().toList
    result should contain theSameElementsAs List(ProductCount(618, 1))
  }

}
