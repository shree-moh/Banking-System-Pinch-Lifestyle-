package com.sriman.springbootbankaccount.controller;

import com.sriman.springbootbankaccount.domain.Account;
import com.sriman.springbootbankaccount.util.StandardJsonResponse;
import com.sriman.springbootbankaccount.util.StandardJsonResponseImpl;
import org.apache.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Optional;


@RestController
@RequestMapping(value = "/balance")
public class BalanceController extends BaseController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public @ResponseBody
    StandardJsonResponse getBalance() {

        StandardJsonResponse jsonResponse = new StandardJsonResponseImpl();
        HashMap<String, Object> responseData = new HashMap<>();

        try {
            Optional<Account> account = Optional.of(accountService.findById(ACCOUNT_ID).get());

            if (account.isPresent()) {
                responseData.put("balance", "$" + account.get().getAmount());

                jsonResponse.setSuccess(true);
                jsonResponse.setData(responseData);
                jsonResponse.setHttpResponseCode(HttpStatus.SC_OK);
            } else {
                jsonResponse.setSuccess(false, "Resource not found", StandardJsonResponse.RESOURCE_NOT_FOUND_MSG);
                jsonResponse.setHttpResponseCode(HttpStatus.SC_NO_CONTENT);
            }

        } catch (Exception e) {
            logger.error("exception", e);
            jsonResponse.setSuccess(false, StandardJsonResponse.DEFAULT_MSG_TITLE_VALUE, StandardJsonResponse.DEFAULT_MSG_NAME_VALUE);
            jsonResponse.setHttpResponseCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
            return jsonResponse;
        }

        return jsonResponse;
    }

}
