package met.cs673.team1.controller;

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

    @PostMapping(value = "/addCategory", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ExpenseCategory> addCategory(@RequestBody ExpenseCategory category) {
        ExpenseCategory categoryWithId = categoryService.save(category);
        return new ResponseEntity<>(categoryWithId, HttpStatus.CREATED);
    }
}
