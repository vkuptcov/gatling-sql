package io.github.gatling.sql

import akka.actor.ActorSystem
import io.gatling.commons.stats.{KO, OK}
import io.gatling.commons.util.ClockSingleton.nowMillis
import io.gatling.core.action.{Action, ExitableAction}
import io.gatling.core.session.{Expression, Session}
import io.gatling.core.stats.StatsEngine
import io.gatling.core.stats.message.ResponseTimings
import scala.util.control.NonFatal

class SqlRequestAction(val name: String, val next: Action, system: ActorSystem, val statsEngine: StatsEngine, protocol: SqlProtocol, query: Expression[String]) extends ExitableAction {

  override def execute(session: Session): Unit = {
    val start = nowMillis
    try {
      val st = protocol.dataSource.getConnection.createStatement()
      val q = query(session).get
      st.execute(q)
      statsEngine.logResponse(session, name, ResponseTimings(start, nowMillis), OK, None, None, Nil)
      next ! session.markAsSucceeded
    } catch {
      case NonFatal(e) ⇒
        statsEngine.logResponse(session, name, ResponseTimings(start, nowMillis), KO, None, Some("Error: " + e.getMessage), Nil)
        next ! session.markAsFailed
    }
  }
}
