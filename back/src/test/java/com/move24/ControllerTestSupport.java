package com.move24;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.move24.domain.driver.controller.DriverController;
import com.move24.domain.driver.service.DriverService;
import com.move24.domain.member.controller.MemberController;
import com.move24.domain.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = {MemberController.class, DriverController.class})
public class ControllerTestSupport {
    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockBean
    protected MemberService memberService;

    @MockBean
    protected DriverService driverService;
}
