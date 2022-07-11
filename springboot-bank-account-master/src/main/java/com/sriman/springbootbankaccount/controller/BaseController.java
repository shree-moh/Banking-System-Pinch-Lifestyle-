package com.sriman.springbootbankaccount.controller;

import com.sriman.springbootbankaccount.service.AccountService;
import com.sriman.springbootbankaccount.service.TransactionsService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;


public class BaseController {
    protected static final long ACCOUNT_ID = 1L;
    protected final Log logger = LogFactory.getLog(getClass());
    @Autowired
    AccountService accountService;

    @Autowired
    TransactionsService transactionsService;
}
