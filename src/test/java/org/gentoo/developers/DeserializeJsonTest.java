package org.gentoo.developers;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by johu on 31.03.15.
 */
@Slf4j
public class DeserializeJsonTest {

    private static final String KEY = "developers";

    private Developers developers;

    @Before
    public void setup() throws IOException {
        File file = new File(getClass().getResource("/gentoo-developers.json").getFile());
        assertNotNull(file);

        ObjectMapper mapper = new ObjectMapper();
        developers = mapper.readValue(file, Developers.class);
    }

    @Test
    public void read() throws IOException {
        assertNotNull(developers);
        assertTrue(!developers.isEmpty());
    }

    @Test
    public void validate() {
        List<Developer> devs = developers.get(KEY);
        assertNotNull(devs);
        assertTrue(!devs.isEmpty());

        for (Developer dev : devs) {
            log.debug(dev.toString());
            assertNotNull(dev.getNick());
            assertNotNull(dev.getName());
            assertNotNull(dev.getJoined());
            assertNotNull(dev.getRoles());
            assertNotNull(dev.getLat());
            assertNotNull(dev.getLon());
            assertNotNull(dev.getLoc());
        }
    }
}
