package org.gentoo.developers;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.mongodb.core.MongoOperations;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

/**
 * Created by johu on 03.04.15.
 */
@Slf4j
@RunWith(MockitoJUnitRunner.class)
public class DeveloperInitializerTest {

    @Mock
    private DeveloperRepository repository;
    @Mock
    private MongoOperations operations;

    @Test
    public void initExisting() {
        when(repository.count()).thenReturn(1l);
        new DeveloperInitializer(repository, operations);
        verify(repository, never()).save(any(Developer.class));
    }

    @Test
    public void init() {
        when(repository.count()).thenReturn(0l);
        new DeveloperInitializer(repository, operations);
        verify(repository, atLeast(1)).save(any(Developer.class));
    }

}
