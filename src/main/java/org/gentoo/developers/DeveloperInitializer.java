package org.gentoo.developers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Component;

/**
 * Created by johu on 03.04.15.
 */
@Slf4j
@Component
public class DeveloperInitializer {

    @Autowired
    public DeveloperInitializer(DeveloperRepository repository, MongoOperations operations) {
        if (repository.count() != 0) {
            return;
        }

        Developer developer = new Developer();
        developer.setNick("johu");
        developer.setJoined("1970/01/01");
        developer.setLat(1d);
        developer.setLon(1d);
        developer.setLoc("somewhere");
        developer.setRoles("something");
        repository.save(developer);
    }

}
