package met.cs673.team1.service;

import java.time.LocalDate;
import java.util.List;
import met.cs673.team1.domain.dto.IncomeDto;
import met.cs673.team1.domain.entity.Income;
import met.cs673.team1.domain.entity.User;
import met.cs673.team1.mapper.IncomeMapper;
import met.cs673.team1.repository.IncomeRepository;
import org.springframework.stereotype.Service;

/**
 * Service class providing processing of income data before persistence.
 */
@Service
public class IncomeService {

    private final IncomeRepository incomeRepository;

    private final IncomeMapper incomeMapper;

    private final UserService userService;

    public IncomeService(IncomeRepository incomeRepository,
                         UserService userService,
                         IncomeMapper incomeMapper) {
        this.incomeRepository = incomeRepository;
        this.incomeMapper = incomeMapper;
        this.userService = userService;
    }

    /**
     * Add an income to the database
     * @param incomeDto data transfer object representing the income of a user
     */
    public IncomeDto addIncome(IncomeDto incomeDto) {
        Income income = incomeMapper.incomeDtoToIncome(incomeDto);
        Income savedIncome = incomeRepository.save(income);
        return incomeMapper.incomeToIncomeDto(savedIncome);
    }

    public List<IncomeDto> findAllByUserId(Integer id) {
        List<Income> incomes = incomeRepository.findAllByUserUserId(id);
        return incomes.stream().map(incomeMapper::incomeToIncomeDto).toList();
    }

    public List<IncomeDto> findAllByUserIdAndDateRange(Integer id, LocalDate start, LocalDate end) {
        List<Income> incomes = incomeRepository.findAllByUserUserIdAndDateBetween(id, start, end);
        return incomes.stream().map(incomeMapper::incomeToIncomeDto).toList();
    }

    /**
     * Find all expenses for a user by searching with their username
     * @param username username used to query the database
     * @return List of data transfer objects representing all incomes for the user
     */
    public List<IncomeDto> findAllByUsername(String username) {
        User u = userService.findUserEntityByUsername(username);
        return findAllByUserId(u.getUserId());
    }

    public List<IncomeDto> findAllByUsernameAndDateRange(String username, LocalDate start, LocalDate end) {
        User u = userService.findUserEntityByUsername(username);
        List<Income> incomes = incomeRepository.findAllByUserUserIdAndDateBetween(u.getUserId(), start, end);
        return incomes.stream().map(incomeMapper::incomeToIncomeDto).toList();
    }

}
