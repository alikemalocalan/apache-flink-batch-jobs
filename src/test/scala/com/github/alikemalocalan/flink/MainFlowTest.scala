package com.github.alikemalocalan.flink

import org.apache.flink.api.scala.ExecutionEnvironment
import org.junit.rules.TemporaryFolder
import org.junit.{Rule, Test}


class MainFlowTest {

  //  val miniClusterResource: MiniClusterResourceConfiguration = new MiniClusterResourceConfiguration.Builder()
  //    .setNumberTaskManagers(1)
  //    .setNumberSlotsPerTaskManager(1)
  //    .build()

  //@ClassRule
  //val testEnv: TestEnvironment = new MiniClusterWithClientResource(miniClusterResource)


  val _temporaryFolder = new TemporaryFolder

  @Test
  def testMain(): Unit = {
    import org.apache.flink.util.FileUtils

    val inputFile = TEMPORARY_FOLDER.newFile
    val outputPath = TEMPORARY_FOLDER.newFile
    FileUtils.writeFileUtf8(inputFile, CsvExampleData.caseCSV())
    try {
      val env = ExecutionEnvironment.getExecutionEnvironment
      MainFlow.jobExecution(env, inputFile.getAbsolutePath, outputPath.toURI.toString)
    }
    catch {
      case e: Throwable => e.printStackTrace()
    }
    finally try {
      println(outputPath.list())
      FileUtils.deleteDirectory(TEMPORARY_FOLDER.getRoot)
    }
  }

  @Rule
  def TEMPORARY_FOLDER: TemporaryFolder = _temporaryFolder

}
