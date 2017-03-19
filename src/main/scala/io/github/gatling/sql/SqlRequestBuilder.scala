package io.github.gatling.sql

import io.gatling.commons.validation.Validation
import io.gatling.core.action.Action
import io.gatling.core.action.builder.ActionBuilder
import io.gatling.core.session.Expression
import io.gatling.core.structure.ScenarioContext
import java.sql.Statement

class SqlRequestBuilderFactory(queryName: String) {

  def execute(statement: Statement) = new SqlRequestBuilder(statement)

  def execute(query: Expression[String]) = PlainRequestBuilder(queryName, query)
}

class SqlRequestBuilder(statement: Statement) extends ActionBuilder {

  override def build(ctx: ScenarioContext, next: Action): Action = ???
}

case class PlainRequestBuilder(queryName: String, query: Expression[String]) extends ActionBuilder {

  override def build(ctx: ScenarioContext, next: Action): Action = {
    val sqlProtocol = ctx.protocolComponentsRegistry.components(SqlProtocol.SqlProtocolKey).sqlProtocol
    new SqlRequestAction(
      queryName,
      next, ctx.system, ctx.coreComponents.statsEngine, sqlProtocol, query)
  }
}