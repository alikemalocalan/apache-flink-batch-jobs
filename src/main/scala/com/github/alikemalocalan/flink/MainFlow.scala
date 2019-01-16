package com.github.alikemalocalan.flink


import com.github.alikemalocalan.flink.JobTransform._
import org.apache.flink.api.java.utils.ParameterTool
import org.apache.flink.api.scala._
import org.slf4j.{Logger, LoggerFactory}


object MainFlow {
  val logger: Logger = LoggerFactory.getLogger(this.getClass)

  def main(args: Array[String]): Unit = {

    val params: ParameterTool = ParameterTool.fromArgs(args)
    val env = ExecutionEnvironment.getExecutionEnvironment
    env.getConfig.setGlobalJobParameters(params)

    if (params.get("inputcsv", "").isEmpty && params.get("inputcsv", "").isEmpty) {
      sys.error("must be '--inputcsv' param and '--outputpath' param ")
    } else {
      jobExecution(env, params.get("inputcsv"), params.get("outputpath")).execute("Scala Flink Example")
    }

  }

  def jobExecution(env: ExecutionEnvironment, input_csv: String, outputPath: String): ExecutionEnvironment = {
    val userActivity: DataSet[UserAction] = env.readCsvFile[UserAction](
      filePath = input_csv,
      fieldDelimiter = "|",
      ignoreFirstLine = true
    )

    // 1- Unique Product View counts by ProductId
    val resultUniqProductView = uniqProductView(userActivity)
      .writeAsCsv(outputPath + "jobResult_1", fieldDelimiter = "|")

    // 2- Unique Event counts
    val resultUniqEvent = uniqEvent(userActivity)
      .writeAsCsv(outputPath + "jobResult_2", fieldDelimiter = "|")


    // 3- Top 5 Users who fulfilled all the events (view,add,remove,click)
    val resultTopUsers = topUsers(userActivity)
      .writeAsCsv(outputPath + "jobResult_3", fieldDelimiter = "|")

    // 4- All events of #UserId : 47
    val resultAllEventForUser = allEventForUser(userActivity, 47)
      .writeAsCsv(outputPath + "jobResult_4", fieldDelimiter = "|")

    // 5- Product Views of #UserId : 47
    val resultProductView = productViewByUser(userActivity, 47)
      .writeAsCsv(outputPath + "jobResult_5", fieldDelimiter = "|")

    env
  }

}
