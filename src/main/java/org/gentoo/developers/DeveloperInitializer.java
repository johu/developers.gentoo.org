package org.gentoo.developers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.DeserializationProblemHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
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
        mapper.addHandler(new DeserializationProblemHandler() {
            @Override
            public boolean handleUnknownProperty(DeserializationContext ctxt, JsonParser jp, JsonDeserializer<?> deserializer, Object beanOrClass, String propertyName) throws IOException {
                if (!(beanOrClass instanceof Developer)) {
                    return false;
                }

                Developer dev = (Developer) beanOrClass;
                if (StringUtils.equals("lat", propertyName)) {
                    dev.setLatitude(jp.getDoubleValue());
                    return true;
                }

                if (StringUtils.equals("lon", propertyName)) {
                    dev.setLongitude(jp.getDoubleValue());
                    return true;
                }

                if (StringUtils.equals("loc", propertyName)) {
                    dev.setPlace(jp.getValueAsString());
                    return true;
                }

                return false;
            }
        });

        Developers developers = mapper.readValue(file, Developers.class);
        if (developers.isEmpty() || developers.get(KEY) == null) {
            return  Collections.emptyList();
        }

        Developer[] result = developers.get(KEY).toArray(new Developer[0]);
        for (int i = 0; i < result.length; i++) {
            result[i].setLocation(new Point(result[i].getLongitude(),result[i].getLatitude()));
        }

        return Arrays.asList(result);
    }
}
