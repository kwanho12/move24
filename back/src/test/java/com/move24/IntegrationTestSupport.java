package com.move24;

import com.move24.common.utils.ImageUtil;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public abstract class IntegrationTestSupport {

    @MockBean
    protected ImageUtil imageUtil;
}
