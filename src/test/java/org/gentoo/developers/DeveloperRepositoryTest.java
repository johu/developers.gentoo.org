package org.gentoo.developers;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;


/**
 * Created by johu on 03.04.15.
 */
@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DeveloperApplication.class)
public class DeveloperRepositoryTest {

    private static final String ID = "johu";

    @Autowired
    private DeveloperRepository repository;

    @Before
    @After
    public void cleanup() {
        repository.deleteAll();
    }

    @Test
    public void findByNickNull() {
        assertNull(repository.findByNick(null));
    }

    @Test
    public void findByNickEmpty() {
        assertNull(repository.findByNick(""));
    }

    @Test
    public void findByNickNotExists() {
        createDeveloper();

        assertNull(repository.findByNick(StringUtils.reverse(ID)));
    }

    @Test
    public void findByNick() {
        createDeveloper();

        Developer result = repository.findByNick(ID);
        log.debug("result: {}", result);
        assertNotNull(result);
        assertEquals(ID, result.getNick());
    }

    private void createDeveloper() {
        Developer developer = new Developer();
        developer.setNick(ID);
        repository.save(developer);
    }
}
