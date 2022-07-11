package com.sriman.springbootbankaccount.controller;

import com.hendisantika.springbootbankaccount.service.AccountService;
import com.hendisantika.springbootbankaccount.service.TransactionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;


public class BaseControllerTests {

    @Autowired
    protected MockMvc mvc;

    @MockBean
    protected AccountService accountService;

    @MockBean
    protected TransactionsService transactionsService;

}