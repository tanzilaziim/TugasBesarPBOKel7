package tubespbokelompok7.restful.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import tubespbokelompok7.restful.entity.Outcome;
import tubespbokelompok7.restful.entity.Outcome;
import tubespbokelompok7.restful.entity.User;
import tubespbokelompok7.restful.model.CreateOutcomeRequest;
import tubespbokelompok7.restful.model.OutcomeResponse;
import tubespbokelompok7.restful.model.OutcomeResponse;
import tubespbokelompok7.restful.model.UpdateOutcomeRequest;
import tubespbokelompok7.restful.repository.OutcomeRepository;

import java.util.UUID;

@Service
public class OutcomeService {

    @Autowired
    private OutcomeRepository outcomeRepository;

    @Autowired
    private ValidationService validationService;

    @Transactional
    public OutcomeResponse create(User user, CreateOutcomeRequest request){
        validationService.validate(request);

        Outcome outcome = new Outcome();
        outcome.setId(UUID.randomUUID().toString());
        outcome.setBalance(request.getBalance());
        outcome.setOutcome_name(request.getOutcome_name());
        outcome.setOutcome_total(request.getOutcome_total());
        outcome.setUser(user);

        outcomeRepository.save(outcome);
        return toOutcomeResponse(outcome);
    }

    private OutcomeResponse toOutcomeResponse(Outcome outcome){
        return OutcomeResponse.builder()
                .id(outcome.getId())
                .balance(outcome.getBalance())
                .outcome_name(outcome.getOutcome_name())
                .outcome_total(outcome.getOutcome_total())
                .build();
    }
    @Transactional(readOnly = true)
    public OutcomeResponse get(User user, String id){
        Outcome outcome = outcomeRepository.findFirstByUserAndId(user, id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Outcome data not found"));
        return toOutcomeResponse(outcome);
    }

    @Transactional
    public OutcomeResponse update(User user, UpdateOutcomeRequest request){
        validationService.validate(request);

        Outcome outcome = outcomeRepository.findFirstByUserAndId(user, request.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Outcome data not found"));

        outcome.setBalance(request.getBalance());
        outcome.setOutcome_name(request.getOutcome_name());
        outcome.setOutcome_total(request.getOutcome_total());
        outcomeRepository.save(outcome);

        return toOutcomeResponse(outcome);
    }

    @Transactional
    public void delete(User user, String outcomeId){
        Outcome outcome = outcomeRepository.findFirstByUserAndId(user, outcomeId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Outcome data not found"));

        outcomeRepository.delete(outcome);
    }
}
