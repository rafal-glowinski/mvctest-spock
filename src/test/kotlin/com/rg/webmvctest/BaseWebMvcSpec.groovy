package com.rg.webmvctest

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch

class BaseWebMvcSpec extends Specification {

    @Autowired
    protected MockMvc mvc

    ResultActions doRequest(MockHttpServletRequestBuilder requestBuilder) throws Exception {
        return mvc.perform(requestBuilder).andDo(MockMvcResultHandlers.print())
    }

    ResultActions asyncResult(MvcResult mvcResult) {
        mvc.perform(asyncDispatch(mvcResult))
                .andDo(MockMvcResultHandlers.print())
    }
}
