package com.kcap.kakaoclassaccountprogram.web.transactionhistory;

import com.kcap.kakaoclassaccountprogram.domain.trsnactionHistory.TransactionHistory;
import com.kcap.kakaoclassaccountprogram.service.transactionhistory.TransactionHistoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Transaction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/transactionhistory")
public class TransactionHistoryController {

    private final TransactionHistoryService transactionHistoryService;

    @GetMapping
    public String getTransactionHistory(Model model) {
        List<TransactionHistory> transactionHistoryList = transactionHistoryService.getTransactionHistoryList();
        model.addAttribute("transactionHistoryList", transactionHistoryList);

        // TODO: 2022/03/06 로그인 한 모임 기준 데이터 불러오기 추가
        return "transactionhistory/list";
    }
}
