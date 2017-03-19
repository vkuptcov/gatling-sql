package io.github.gatling.sql

import io.gatling.core.session.Expression


object Predef {

  def sql(queryName: String) = new SqlRequestBuilderFactory(queryName)
}
