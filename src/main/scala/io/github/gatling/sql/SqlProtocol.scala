package io.github.gatling.sql

import akka.actor.ActorSystem
import io.gatling.core.CoreComponents
import io.gatling.core.config.GatlingConfiguration
import io.gatling.core.protocol.{Protocol, ProtocolComponents, ProtocolKey}
import io.gatling.core.session.Session
import javax.sql.DataSource

object SqlProtocol {

  val SqlProtocolKey = new ProtocolKey {

    override type Components = SqlComponents
    override type Protocol = SqlProtocol

    override def protocolClass: Class[io.gatling.core.protocol.Protocol] = classOf[SqlProtocol].asInstanceOf[Class[io.gatling.core.protocol.Protocol]]

    override def defaultProtocolValue(configuration: GatlingConfiguration): SqlProtocol = throw new IllegalStateException("Can't provide a default value for SqlProtocol")

    override def newComponents(system: ActorSystem, coreComponents: CoreComponents): SqlProtocol => SqlComponents = sqlProtocol => SqlComponents(sqlProtocol)
  }
}

case class SqlProtocol(dataSource: DataSource) extends Protocol {


}

case class SqlComponents(sqlProtocol: SqlProtocol) extends ProtocolComponents {

  def onStart: Option[Session => Session] = None

  def onExit: Option[Session => Unit] = None
}