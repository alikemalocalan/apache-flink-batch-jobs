package com.github.alikemalocalan.flink

import com.typesafe.config
import com.typesafe.config.ConfigFactory


trait Config {

  val getConf: config.Config = ConfigFactory.load().getConfig("flink")

  val input_csv: String = getConf.getString("input_csv")
  val output_base_path: String = getConf.getString("output_job_")

}