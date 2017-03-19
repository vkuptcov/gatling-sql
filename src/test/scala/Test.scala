import io.gatling.core.Predef.{Simulation, scenario, _}
import io.github.gatling.sql.Predef._
import io.github.gatling.sql.SqlProtocol
import org.postgresql.ds.PGSimpleDataSource
import scala.concurrent.duration._
import scala.util.Random

class Test extends Simulation {

  val dataSource = new PGSimpleDataSource()
  dataSource.setUrl("jdbc:postgresql://localhost:5432/gocd")
  dataSource.setUser("postgres")
  dataSource.setPassword("password")

  val sqlProtocol = SqlProtocol(dataSource)

  val scn = scenario("test")
    .exec { s ⇒
      s.set("rand", Random.nextInt())
    }
    .exec(
      sql("test").execute("SELECT 1 + ${rand}")
    )

  setUp(scn.inject(rampUsersPerSec(10) to 100 during (1 seconds)))
    .protocols(sqlProtocol)
}
