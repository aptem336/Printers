import javax.annotation.sql.DataSourceDefinition;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@DataSourceDefinition(name = "java:global/jdbc/db",
        className = "org.apache.derby.jdbc.EmbeddedDriver",
        url = "jdbc:derby:D:\\printers;create=true"
)
public class DataSourceProducer {
    @Produces
    @PersistenceContext(unitName = "pu")
    private EntityManager em;
}
