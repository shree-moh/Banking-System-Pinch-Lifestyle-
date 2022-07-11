package com.sriman.springbootbankaccount.controller;

import com.sriman.springbootbankaccount.domain.Account;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(BalanceController.class)
public class BalanceControllerTests extends BaseControllerTests {

    @Autowired
    private MockMvc mvc;

    @Test
    public void testGetBalance() throws Exception {
        given(this.accountService.findById(1L))
                .willReturn(Optional.of(new Account(400)));
        this.mvc.perform(get("/balance/").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andExpect(content().json("{\"success\":true,\"messages\":{},\"errors\":{},\"data\":{\"balance\":\"$400.0\"},\"httpResponseCode\":200}"));
    }

}