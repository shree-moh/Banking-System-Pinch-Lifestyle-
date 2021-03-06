package com.sriman.springbootbankaccount.controller;

import com.sriman.springbootbankaccount.domain.Account;
import com.sriman.springbootbankaccount.domain.AccountTransaction;
import com.sriman.springbootbankaccount.domain.TransactionType;
import com.sriman.springbootbankaccount.domain.UserTransaction;
import com.sriman.springbootbankaccount.util.AccountUtils;
import com.sriman.springbootbankaccount.util.StandardJsonResponse;
import com.sriman.springbootbankaccount.util.StandardJsonResponseImpl;
import org.apache.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;


@RestController
@RequestMapping(value = "/withdrawal")
public class WithdrawalController extends BaseController {

    private static final double MAX_WITHDRAWAL_PER_TRANSACTION = 20000; // $20k
    private static final double MAX_WITHDRAWAL_PER_DAY = 50000; // $50k
    private static final int MAX_WITHDRAWAL_TRANSACTIONS_PER_DAY = 3;

    @PostMapping("/")
    public @ResponseBody
    StandardJsonResponse makeWithDrawal(@RequestBody UserTransaction userTransaction) {

        StandardJsonResponse jsonResponse = new StandardJsonResponseImpl();

        try {

            double total = 0;

            // check balance
            double balance = accountService.findById(ACCOUNT_ID).get().getAmount();
            if (userTransaction.getAmount() > balance) {
                jsonResponse.setSuccess(false, "Error", "You have insufficient funds");
                jsonResponse.setHttpResponseCode(HttpStatus.SC_NOT_ACCEPTABLE);
                return jsonResponse;
            }


            // check maximum limit withdrawal for the day has been reached
            List<AccountTransaction> withdrawals = transactionsService.findByDateBetweenAndType(AccountUtils.getStartOfDay(new Date()),
                    AccountUtils.getEndOfDay(new Date()), TransactionType.WITHDRAWAL.getId());

            if (withdrawals.size() > 0) {
                for (AccountTransaction accountTransaction : withdrawals) {
                    total += accountTransaction.getAmount();
                }
                if (total + userTransaction.getAmount() > MAX_WITHDRAWAL_PER_DAY) {
                    jsonResponse.setSuccess(false, "Error", "Withdrawal per day should not be more than $50K");
                    jsonResponse.setHttpResponseCode(HttpStatus.SC_NOT_ACCEPTABLE);
                    return jsonResponse;
                }
            }

            // Check whether the amount being withdrawn exceeds the MAX_WITHDRAWAL_PER_TRANSACTION
            if (userTransaction.getAmount() > MAX_WITHDRAWAL_PER_TRANSACTION) {
                jsonResponse.setSuccess(false, "Error", "Exceeded Maximum Withdrawal Per Transaction");
                jsonResponse.setHttpResponseCode(HttpStatus.SC_NOT_ACCEPTABLE);
                return jsonResponse;
            }

            // check whether transactions exceeds the max allowed per day
            if (withdrawals.size() < MAX_WITHDRAWAL_TRANSACTIONS_PER_DAY) {
                AccountTransaction accountTransaction = new AccountTransaction(TransactionType.WITHDRAWAL.getId(), userTransaction.getAmount(), new Date());
                double amount = transactionsService.save(accountTransaction).getAmount();

                Account account = accountService.findById(ACCOUNT_ID).get();
                double newBalance = account.getAmount() - amount;
                account.setAmount(newBalance);
                accountService.save(account);

                jsonResponse.setSuccess(true, "", "Withdrawal sucessfully Transacted");
                jsonResponse.setHttpResponseCode(HttpStatus.SC_OK);

            } else {
                jsonResponse.setSuccess(false, "Error", "Maximum Withdrawal transactions for the day Exceeded");
                jsonResponse.setHttpResponseCode(HttpStatus.SC_NOT_ACCEPTABLE);
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
