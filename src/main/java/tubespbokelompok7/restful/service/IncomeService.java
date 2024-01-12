package tubespbokelompok7.restful.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;
import tubespbokelompok7.restful.entity.Income;
import tubespbokelompok7.restful.entity.User;
import tubespbokelompok7.restful.model.CreateIncomeRequest;
import tubespbokelompok7.restful.model.IncomeResponse;
import tubespbokelompok7.restful.model.UpdateIncomeRequest;
import tubespbokelompok7.restful.repository.IncomeRepository;

import java.util.List;
import java.util.UUID;
@Service
public class IncomeService {

    @Autowired
    private IncomeRepository incomeRepository;

    @Autowired
    private ValidationService validationService;


    @Transactional
    public IncomeResponse create(User user, CreateIncomeRequest request){
        validationService.validate(request);

        Income income = new Income();
        income.setId(UUID.randomUUID().toString());
        income.setBalance(request.getBalance());
        income.setItem_name(request.getItem_name());
        income.setPrice(request.getPrice());
        income.setAmount(request.getAmount());
        income.setUser(user);

        incomeRepository.save(income);
        return toIncomeResponse(income);
    }

    private IncomeResponse toIncomeResponse(Income income){
        return IncomeResponse.builder()
                .id(income.getId())
                .balance(income.getBalance())
                .item_name(income.getItem_name())
                .price(income.getPrice())
                .amount(income.getAmount())
                .build();
    }
    @Transactional(readOnly = true)
    public IncomeResponse get(User user, String id){
        Income income = incomeRepository.findFirstByUserAndId(user, id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Income data not found"));
        return toIncomeResponse(income);
    }

    @Transactional
    public IncomeResponse update(User user, UpdateIncomeRequest request){
        validationService.validate(request);

        Income income = incomeRepository.findFirstByUserAndId(user, request.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Income data not found"));

        income.setBalance(request.getBalance());
        income.setItem_name(request.getItem_name());
        income.setPrice(request.getPrice());
        income.setAmount(request.getAmount());
        incomeRepository.save(income);

        return toIncomeResponse(income);
    }

    @Transactional
    public void delete(User user, String incomeId){
        Income income = incomeRepository.findFirstByUserAndId(user, incomeId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Income data not found"));

        incomeRepository.delete(income);
    }

}
