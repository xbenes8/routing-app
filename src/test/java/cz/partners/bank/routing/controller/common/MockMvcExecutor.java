package cz.partners.bank.routing.controller.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

/**
 * Helper class which operates over MockMvc and performs http methods over it.
 * With some helping transformations
 *
 * @author Zdenek Benes
 */
@Component
@Getter
public class MockMvcExecutor {

    @Autowired
    protected WebApplicationContext wac;
    @Autowired
    protected ObjectMapper objectMapper;

    private MockMvc mockMvc;

    public void init() {
        mockMvc = MockMvcBuilders.webAppContextSetup(this.wac)
            .build();
    }

    public MvcResult getMethod(String url) throws Exception {
        MockHttpServletRequestBuilder requestBuilder = get(url)
            .contentType(MediaType.APPLICATION_JSON);
        return mockMvc.perform(requestBuilder).andReturn();
    }

    public boolean isResponseOk(MvcResult result) {
        return HttpStatus.OK.value() == result.getResponse().getStatus();
    }

    public <T> T readResponse(MvcResult result, Class<T> type) throws java.io.IOException {
        String contentAsString = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        return objectMapper.readValue(contentAsString, type);
    }

    public boolean isBadRequest(MvcResult result) {
        return HttpStatus.BAD_REQUEST.value() == result.getResponse().getStatus();
    }

}
