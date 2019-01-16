package com.github.alikemalocalan.flink

object CsvExampleData {

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

  def caseCSV(): String = {
    "date|productId|eventName|userId\n" +
      "1535816823|496|view|13\n" +
      "1536392928|496|add|13\n" +
      "1536272308|644|click|16\n" +
      "1535903776|644|click|16\n" +
      "1536757406|644|remove|86\n" +
      "1536685863|618|add|33"
  }

}
