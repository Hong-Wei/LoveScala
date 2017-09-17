package LearnMapped

import net.liftweb._
import net.liftweb.db.StandardDBVendor
import net.liftweb.http._
import net.liftweb.mapper.{By, DB, Schemifier}
import net.liftweb.util._


/**
  * A class that's instantiated early and run.  It allows the application
  * to modify lift's environment
  */
object Boot extends App{ 
    if (!DB.jndiJdbcConnAvailable_?) {
      sys.props.put("h2.implicitRelativePath", "true") 
      val vendor =
        new StandardDBVendor(Props.get("db.driver") openOr "org.h2.Driver",
                             Props.get("db.url") openOr
                               "jdbc:h2:lift_proto.db;AUTO_SERVER=TRUE",
                             Props.get("db.user"),
                             Props.get("db.password"))

      LiftRules.unloadHooks.append(vendor.closeAllConnections_! _)

      DB.defineConnectionManager(util.DefaultConnectionIdentifier, vendor)
    }

    // Use Lift's Mapper ORM to populate the database
    // you don't need to use Mapper to use Lift... use
    // any ORM you want
    Schemifier.schemify(true, Schemifier.infoF _, User)
  User.create.lastName("hongwei").firstName("zhang").saveMe()
  println(User.find(By(User.lastName, "hongwei")))
  
  

    
}
