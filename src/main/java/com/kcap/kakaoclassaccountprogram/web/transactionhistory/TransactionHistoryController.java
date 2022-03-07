package com.kcap.kakaoclassaccountprogram.web.transactionhistory;

import com.kcap.kakaoclassaccountprogram.domain.trsnactionHistory.TransactionHistory;
import com.kcap.kakaoclassaccountprogram.service.transactionhistory.TransactionHistoryService;
import com.kcap.kakaoclassaccountprogram.utils.CommonUtils;
import com.kcap.kakaoclassaccountprogram.web.transactionhistory.dto.TransactionHistoryQueryDto;
import com.kcap.kakaoclassaccountprogram.web.transactionhistory.form.TransactionHistorySearchForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/transactionhistory")
public class TransactionHistoryController {

    private final TransactionHistoryService transactionHistoryService;
    private final CommonUtils commonUtils;

    @GetMapping
    public String getTransactionHistory(Model model) {
        Map<String, Object> map = new HashMap<>();
        map.put("startDateTime", commonUtils.dayCalcLocalDateTime(6));
        map.put("endDateTime", commonUtils.dayCalcLocalDateTime(0));
        map.put("contents", "");

        TransactionHistorySearchForm form =
                new TransactionHistorySearchForm(map);
        TransactionHistoryQueryDto dto = TransactionHistoryQueryDto.builder().form(form).build();
        List<TransactionHistory> transactionHistoryList =
                transactionHistoryService.getTransactionHistoryList(dto);

        model.addAttribute("transactionHistoryList", transactionHistoryList);
        model.addAttribute("form", form);

        // TODO: 2022/03/06 로그인 한 모임 기준 데이터 불러오기 추가
        return "transactionhistory/list";
    }

    @PostMapping
    public String getTransactionHistory(TransactionHistorySearchForm form, Model model) {
        TransactionHistoryQueryDto dto = TransactionHistoryQueryDto.builder().form(form).build();
        List<TransactionHistory> transactionHistoryList =
                transactionHistoryService.getTransactionHistoryList(dto);

        model.addAttribute("transactionHistoryList", transactionHistoryList);
        model.addAttribute("form", form);

        // TODO: 2022/03/06 로그인 한 모임 기준 데이터 불러오기 추가
        return "transactionhistory/list";
    }
}
