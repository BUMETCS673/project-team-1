package met.cs673.team1.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import met.cs673.team1.domain.dto.IncomeDto;
import met.cs673.team1.domain.entity.Income;
import met.cs673.team1.domain.entity.User;
import met.cs673.team1.exception.UserNotFoundException;
import met.cs673.team1.mapper.IncomeMapper;
import met.cs673.team1.repository.IncomeRepository;
import met.cs673.team1.repository.UserRepository;
import org.springframework.stereotype.Service;

/**
 * Service class providing processing of income data before persistence.
 */
@Service
public class IncomeService {

    private final IncomeRepository incomeRepository;

    private final UserRepository userRepository;

    private final IncomeMapper incomeMapper;

    public IncomeService(IncomeRepository incomeRepository,
                         UserRepository userRepository,
                         IncomeMapper incomeMapper) {
        this.incomeRepository = incomeRepository;
        this.userRepository = userRepository;
        this.incomeMapper = incomeMapper;
    }

    /**
     * Add an income to the database
     * @param incomeDto data transfer object representing the income of a user
     */
    public void addIncome(IncomeDto incomeDto) {
        Income income = incomeMapper.incomeDtoToIncome(incomeDto);
        incomeRepository.save(income);
    }

    /**
     * Find all expenses for a user by searching with their username
     * @param username username used to query the database
     * @return List of data transfer objects representing all incomes for the user
     */
    public List<IncomeDto> findAllByUsername(String username) {
        Optional<User> optUser = userRepository.findByUsername(username);
        if (optUser.isEmpty()) {
            throw new UserNotFoundException(String.format("User with username '%s' not found", username));
        }
        List<Income> income = incomeRepository.findAllByUserUserId(optUser.get().getUserId());
        return income.stream().map(incomeMapper::incomeToIncomeDto).collect(Collectors.toList());
    }
}
