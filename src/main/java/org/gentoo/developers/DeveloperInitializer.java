package org.gentoo.developers;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * Created by johu on 03.04.15.
 */
@Slf4j
@Component
public class DeveloperInitializer {

    public static final String KEY = "developers";

    @Autowired
    public DeveloperInitializer(DeveloperRepository repository, MongoOperations operations) throws IOException {
        if (repository.count() != 0) {
            log.info("repository already initialized count: {}", repository.count());
            return;
        }

        List<Developer> devs = read();
        log.info("importing '{}' developer into MongoDB", devs.size());
        repository.save(devs);
        log.info("imported developers: {}", repository.count());
    }

    public static List<Developer> read() throws IOException {
        File file = new ClassPathResource("/gentoo-developers.json").getFile();

        ObjectMapper mapper = new ObjectMapper();
        Developers developers = mapper.readValue(file, Developers.class);
        if (developers.isEmpty()) {
            return  Collections.emptyList();
        }

        return developers.get(KEY) == null ? Collections.emptyList() : developers.get(KEY);
    }
}
