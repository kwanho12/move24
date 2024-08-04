package com.move24.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.move24.request.JoinRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void joinTest() throws Exception {
        JoinRequest request = new JoinRequest();
        request.setMemberId("user1");
        request.setPassword("1234");
        request.setName("관호");
        request.setGender("MALE");
        request.setMail("sksmss123@gmail.com");
        request.setPhoneNumber("01012345678");
        request.setAddress("서울시 양산길 33");

        String json = objectMapper.writeValueAsString(request);

        // Mock MultipartFile for the request part
        MockMultipartFile jsonRequest = new MockMultipartFile(
                "request",
                null,
                MediaType.APPLICATION_JSON_VALUE,
                json.getBytes()
        );

        // Mock MultipartFile
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "testFile.jpeg",
                MediaType.IMAGE_JPEG_VALUE,
                "test file".getBytes()
        );

        // expected
        mockMvc.perform(multipart("/api/join")
                        .file(jsonRequest)
                        .file(file)
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .characterEncoding("UTF-8"))
                .andExpect(status().isOk())
                .andDo(print());
    }
}