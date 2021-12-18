package com.asoulfan.platform.user;

import java.nio.charset.StandardCharsets;

import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.powermock.modules.junit4.PowerMockRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import junit.framework.TestCase;

/**
 * @author asuka
 * @since 2021/08/24
 */
@Ignore
@RunWith(PowerMockRunner.class)
public abstract class BaseMockTest extends TestCase {
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    protected EasyRandom random = new EasyRandom(new EasyRandomParameters()
            .seed(123L)
            .objectPoolSize(100)
            .randomizationDepth(3)
            .charset(StandardCharsets.UTF_8)
            .stringLengthRange(1, 50)
            .collectionSizeRange(1, 10)
            .scanClasspathForConcreteTypes(true)
            .overrideDefaultInitialization(false)
            .ignoreRandomizationErrors(true));

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

}
