import model.Expendable;
import model.ExpendableEnumeration;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.stream.Stream;

@Singleton
@Startup
public class ExpendableProducer {
    @Inject
    private EntityManager em;

    @PostConstruct
    @Transactional
    private void postConstruct() {
        Stream.of(ExpendableEnumeration.values()).forEach(e -> em.merge(new Expendable(e)));
    }
}
