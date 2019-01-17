package com.github.alikemalocalan.flink


object CsvExampleData {

  def toCsvStr: String = {
    val stringBuilder = new StringBuilder("date|productId|eventName|userId\n")
    exampleCsvList
      .map(userAction => s"${userAction.date}|${userAction.productId}|${userAction.eventName}|${userAction.userId}\n")
      .foreach(stringBuilder.append)
    stringBuilder.toString()
  }

  def exampleCsvList: List[UserAction] = List[UserAction](
    UserAction(1535816824, 496, "view", 13),
    UserAction(1535816823, 496, "view", 13),
    UserAction(1536392928, 496, "add", 13),
    UserAction(1536272308, 644, "click", 16),
    UserAction(1535903776, 644, "click", 16),
    UserAction(1536757406, 644, "remove", 86),
    UserAction(1536685863, 618, "add", 47),
    UserAction(1536685863, 618, "remove", 47),
    UserAction(1536685863, 618, "click", 47),
    UserAction(1536685863, 618, "view", 47)
  )
}
