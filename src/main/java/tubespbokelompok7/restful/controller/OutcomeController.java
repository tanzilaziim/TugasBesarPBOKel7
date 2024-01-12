package tubespbokelompok7.restful.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import tubespbokelompok7.restful.entity.User;
import tubespbokelompok7.restful.model.*;
import tubespbokelompok7.restful.service.OutcomeService;

@RestController

public class OutcomeController {
    @Autowired
    private OutcomeService outcomeService;

    @PostMapping(
            path = "/api/outcome",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<OutcomeResponse> create(User user, @RequestBody CreateOutcomeRequest request) {
        OutcomeResponse outcomeResponse = outcomeService.create(user, request);
        return WebResponse.<OutcomeResponse>builder().data(outcomeResponse).build();
    }

    @GetMapping(
            path = "/api/outcome/{outcomeId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<OutcomeResponse> get(User user, @PathVariable("outcomeId") String outcomeId) {
        OutcomeResponse outcomeResponse = outcomeService.get(user, outcomeId);
        return WebResponse.<OutcomeResponse>builder().data(outcomeResponse).build();
    }

    @PutMapping(
            path = "/api/outcome/{outcomeId}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<OutcomeResponse> update(User user,
                                              @RequestBody UpdateOutcomeRequest request,
                                              @PathVariable("outcomeId") String outcomeId){
        request.setId(outcomeId);
        OutcomeResponse outcomeResponse = outcomeService.update(user, request);
        return WebResponse.<OutcomeResponse>builder().data(outcomeResponse).build();
    }

    @DeleteMapping(
            path = "/api/outcome/{outcomeId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<String> delete(User user, @PathVariable("outcomeId") String outcomeId){
        outcomeService.delete(user, outcomeId);
        return WebResponse.<String>builder().data("OK").build();
    }
}