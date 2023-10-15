package met.cs673.team1.controller;

import jakarta.validation.constraints.NotEmpty;
import java.util.List;
import met.cs673.team1.domain.entity.ExpenseCategory;
import met.cs673.team1.service.ExpenseCategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ExpenseCategoryController {

    private final ExpenseCategoryService categoryService;

    public ExpenseCategoryController(ExpenseCategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping(value = "/addCategories", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ExpenseCategory>> addCategories(@NotEmpty @RequestBody List<String> categories) {
        List<ExpenseCategory> categoriesWithIds = categoryService.saveAll(categories);
        return new ResponseEntity<>(categoriesWithIds, HttpStatus.CREATED);
    }
}
