package org.gentoo.developers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertNotNull;

/**
 * Created by johu on 31.03.15.
 */
public class DeserializeJsonTest {

    @Before
    public void setup() {
    }

    @Test
    public void testRead() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        File f = new File(getClass().getResource("/gentoo-developers.json").getFile());
        assertNotNull(f);

        Developers developers = mapper.readValue(f, Developers.class);
        assertNotNull(developers);
    }
}
