package tubespbokelompok7.restful.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import tubespbokelompok7.restful.entity.User;
import tubespbokelompok7.restful.model.CreateIncomeRequest;
import tubespbokelompok7.restful.model.UpdateIncomeRequest;
import tubespbokelompok7.restful.model.IncomeResponse;
import tubespbokelompok7.restful.model.WebResponse;
import tubespbokelompok7.restful.service.BalanceService;
import tubespbokelompok7.restful.service.IncomeService;

import java.util.List;

@RestController

public class IncomeController {
    @Autowired
    private IncomeService incomeService;
    private BalanceService balanceService;

    @PostMapping(
            path = "/api/income",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<IncomeResponse> create(User user, @RequestBody CreateIncomeRequest request) {
        IncomeResponse incomeResponse = incomeService.create(user, request);
        return WebResponse.<IncomeResponse>builder().data(incomeResponse).build();
    }

    @GetMapping(
            path = "/api/income/{incomeId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<IncomeResponse> get(User user, @PathVariable("incomeId") String incomeId){
        IncomeResponse incomeResponse = incomeService.get(user, incomeId);
        return WebResponse.<IncomeResponse>builder().data(incomeResponse).build();
    }

    @PutMapping(
            path = "/api/income/{incomeId}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<IncomeResponse> update(User user,
                                              @RequestBody UpdateIncomeRequest request,
                                              @PathVariable("incomeId") String incomeId){
        request.setId(incomeId);
        IncomeResponse incomeResponse = incomeService.update(user, request);
        return WebResponse.<IncomeResponse>builder().data(incomeResponse).build();
    }

    @DeleteMapping(
            path = "/api/income/{incomeId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<String> delete(User user, @PathVariable("incomeId") String incomeId){
        incomeService.delete(user, incomeId);
        return WebResponse.<String>builder().data("OK").build();
    }

}