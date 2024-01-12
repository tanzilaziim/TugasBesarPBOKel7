package tubespbokelompok7.restful.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tubespbokelompok7.restful.entity.User;
import tubespbokelompok7.restful.repository.IncomeRepository;
import tubespbokelompok7.restful.repository.OutcomeRepository;

@Service
public class BalanceService {

        @Autowired
        private IncomeRepository incomeRepository;

        @Autowired
        private OutcomeRepository outcomeRepository;

        public double calculateBalance(User user) {
            double totalIncome = incomeRepository.sumIncome(user);
            double totalOutcome = outcomeRepository.sumOutcome(user);
            return totalIncome - totalOutcome;
        }


}
