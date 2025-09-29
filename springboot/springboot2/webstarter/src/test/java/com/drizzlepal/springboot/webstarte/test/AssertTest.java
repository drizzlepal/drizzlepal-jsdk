package com.drizzlepal.springboot.webstarte.test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AssertTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void test() throws Exception {
        this.mockMvc.perform(get("/test4xx").param("name", "names")).andDo(print())
                .andExpect(status().is4xxClientError()).andExpect(content().string(containsString("非法参数")));
    }

    @Test
    public void test2() throws Exception {
        this.mockMvc.perform(get("/test5xx").param("name", "name")).andDo(print())
                .andExpect(status().is5xxServerError()).andExpect(content().string(containsString("localizedMessage")));
    }

}
