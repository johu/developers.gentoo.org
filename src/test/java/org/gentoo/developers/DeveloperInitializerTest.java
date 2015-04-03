package org.gentoo.developers;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.mongodb.core.MongoOperations;

import java.io.IOException;
import java.util.List;

import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

/**
 * Created by johu on 03.04.15.
 */
@Slf4j
@RunWith(MockitoJUnitRunner.class)
public class DeveloperInitializerTest {

    private static List<Developer> devs;
    @Mock
    private DeveloperRepository repository;
    @Mock
    private MongoOperations operations;

    @BeforeClass
    public static void setup() throws IOException {
         devs = DeveloperInitializer.read();
    }

    @Test
    public void initExisting() throws IOException {
        when(repository.count()).thenReturn(1l);
        new DeveloperInitializer(repository, operations);
        verify(repository, never()).save(any(List.class));
    }

    @Test
    public void init() throws IOException {
        when(repository.count()).thenReturn(0l);
        new DeveloperInitializer(repository, operations);
        verify(repository, atLeast(1)).save(any(List.class));
    }

    @Test
    public void read() throws IOException {
        assertTrue(CollectionUtils.isNotEmpty(devs));
    }

    @Test
    public void validate() {
        assertNotNull(devs);
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
