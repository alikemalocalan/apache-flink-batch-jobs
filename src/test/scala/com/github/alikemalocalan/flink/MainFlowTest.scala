package com.github.alikemalocalan.flink

import java.io.File

import com.github.alikemalocalan.flink.JobTransform._
import org.apache.flink.api.scala._
import org.apache.flink.test.util.AbstractTestBase
import org.apache.flink.util.FileUtils
import org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder
import org.junit.Assert.assertThat
import org.junit.Test
import org.scalatest.Matchers

import scala.collection.JavaConverters.asJavaIterableConverter


class MainFlowTest extends AbstractTestBase with Matchers {

  @Test
  def testMain(): Unit = {


    val inputFile = createTempFile("inputcsv", CsvExampleData.toCsvStr)
    val env = ExecutionEnvironment.getExecutionEnvironment


    val userActivity: DataSet[UserAction] = env.readCsvFile[UserAction](
      filePath = inputFile,
      fieldDelimiter = "|",
      ignoreFirstLine = true
    )

    // 1- Unique Product View counts by ProductId
    val resultUniqProductView: List[ProductCount] = uniqProductView(userActivity).collect().toList

    // 2- Unique Event counts
    val resultUniqEvent = uniqEvent(userActivity).collect().toList

    // 3- Top 5 Users who fulfilled all the events (view,add,remove,click)
    val resultTopUsers = topUsers(userActivity).collect().toList

    // 4- All events of #UserId : 47
    val resultAllEventForUser = allEventForUser(userActivity, 47).collect().toList

    // 5- Product Views of #UserId : 47
    val resultProductView = productViewByUser(userActivity, 47).collect().toList

    FileUtils.deleteDirectory(new File(inputFile))

    assertThat(resultUniqProductView.asJava, containsInAnyOrder(
      ProductCount(618, 1),
      ProductCount(496, 1),
      ProductCount(644, 2)
    ))
    assertThat(resultUniqEvent.asJava, containsInAnyOrder(
      EventCount("add", 2),
      EventCount("remove", 2),
      EventCount("click", 2),
      EventCount("view", 2)
    ))
    assertThat(resultTopUsers.asJava, containsInAnyOrder(FulfilledUser(47, 1)))
    assertThat(resultAllEventForUser.asJava, containsInAnyOrder(
      Event("add", 618),
      Event("remove", 618),
      Event("click", 618),
      Event("view", 618)
    ))
    assertThat(resultProductView.asJava, containsInAnyOrder(ProductCount(618, 1)))

  }

}
