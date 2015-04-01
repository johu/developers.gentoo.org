package org.gentoo.developers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by johu on 31.03.15.
 */
public class DeserializeJsonTest {

    private static final Logger LOG = LoggerFactory.getLogger(DeserializeJsonTest.class);

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
            LOG.debug(dev.toString());
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
