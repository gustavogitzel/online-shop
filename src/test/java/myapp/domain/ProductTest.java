package myapp.domain;

import static org.junit.jupiter.api.Assertions.*;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import myapp.domain.enumeration.ProductStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Set;

public class ProductTest {
    private Validator validator;
    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    private String generateString(int length) {
        return "a".repeat(length);
    }

    @Test
    public void testValidCase1() {
        Product product = new Product();
        product.setTitle("Um bom título");
        product.setKeywords("Algumas Palavras Chave");
        product.setDescription("Descrição válida");
        product.setRating(1);
        product.setPrice(new BigDecimal("1.00"));
        product.setQuantityInStock(null);
        product.setStatus(ProductStatus.IN_STOCK);
        product.setDimensions(null);
        product.setWeight(null);
        product.setDateAdded(Instant.parse("2024-01-01T00:00:00Z"));
        product.setDateModified(null);
        Set<ConstraintViolation<Product>> violations = validator.validate(product);
        assertTrue(violations.isEmpty());
    }
    @Test
    public void testValidCase2() {
        Product product = new Product();
        product.setTitle("Olá");
        product.setKeywords(generateString(200));
        product.setDescription(null);
        product.setRating(5);
        product.setPrice(new BigDecimal("0.01"));
        product.setQuantityInStock(0);
        product.setStatus(ProductStatus.OUT_OF_STOCK);
        product.setDimensions(generateString(50));
        product.setWeight(0.0);
        product.setDateAdded(Instant.parse("2024-02-01T00:00:00Z"));
        product.setDateModified(Instant.parse("2024-02-02T00:00:00Z"));
        Set<ConstraintViolation<Product>> violations = validator.validate(product);
        assertTrue(violations.isEmpty());
    }
    @Test
    public void testValidCase3() {
        Product product = new Product();
        product.setTitle(generateString(100));
        product.setKeywords(generateString(199));
        product.setDescription(generateString(11));
        product.setRating(null);
        product.setPrice(new BigDecimal("1.00"));
        product.setQuantityInStock(0);
        product.setStatus(ProductStatus.DISCONTINUED);
        product.setDimensions(generateString(49));
        product.setWeight(10.5);
        product.setDateAdded(Instant.parse("2024-12-31T00:00:00Z"));
        product.setDateModified(Instant.parse("2025-01-01T00:00:00Z"));
        Set<ConstraintViolation<Product>> violations = validator.validate(product);
        assertTrue(violations.isEmpty());
    }
    @Test
    public void testValidCase4() {
        Product product = new Product();
        product.setTitle(generateString(99));
        product.setKeywords(generateString(199));
        product.setDescription(generateString(11));
        product.setRating(0);
        product.setPrice(new BigDecimal("999.00"));
        product.setQuantityInStock(999);
        product.setStatus(ProductStatus.DISCONTINUED);
        product.setDimensions(generateString(49));
        product.setWeight(10.5);
        product.setDateAdded(Instant.parse("2024-12-30T00:00:00Z"));
        product.setDateModified(Instant.parse("2024-12-31T00:00:00Z"));
        Set<ConstraintViolation<Product>> violations = validator.validate(product);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void testInvalidCase5() {
        Product product = new Product();
        product.setTitle(generateString(101));  // Invalid because title length > 100
        product.setKeywords("Algumas Palavras Chave");
        product.setDescription("Descrição válida");
        product.setRating(1);
        product.setPrice(new BigDecimal("1.00"));
        product.setQuantityInStock(null);
        product.setStatus(ProductStatus.IN_STOCK);
        product.setDimensions(null);
        product.setWeight(null);
        product.setDateAdded(Instant.parse("2024-01-01T00:00:00Z"));
        product.setDateModified(null);
        Set<ConstraintViolation<Product>> violations = validator.validate(product);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("title")));
    }
    @Test
    public void testInvalidCase6() {
        Product product = new Product();
        product.setTitle("Oi");  // Invalid because title length < 3
        product.setKeywords("Algumas Palavras Chave");
        product.setDescription("Descrição válida");
        product.setRating(1);
        product.setPrice(new BigDecimal("1.00"));
        product.setQuantityInStock(null);
        product.setStatus(ProductStatus.IN_STOCK);
        product.setDimensions(null);
        product.setWeight(null);
        product.setDateAdded(Instant.parse("2024-01-01T00:00:00Z"));
        product.setDateModified(null);
        Set<ConstraintViolation<Product>> violations = validator.validate(product);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("title")));
    }
    @Test
    public void testInvalidCase7() {
        Product product = new Product();
        product.setTitle(null);  // Invalid because title is @NotNull
        product.setKeywords("Algumas Palavras Chave");
        product.setDescription("Descrição válida");
        product.setRating(1);
        product.setPrice(new BigDecimal("1.00"));
        product.setQuantityInStock(null);
        product.setStatus(ProductStatus.IN_STOCK);
        product.setDimensions(null);
        product.setWeight(null);
        product.setDateAdded(Instant.parse("2024-01-01T00:00:00Z"));
        product.setDateModified(null);
        Set<ConstraintViolation<Product>> violations = validator.validate(product);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("title")));
    }
    @Test
    public void testInvalidCase8() {
        Product product = new Product();
        product.setTitle("Um bom título");
        product.setKeywords(generateString(201));  // Invalid because keywords length > 200
        product.setDescription("Descrição válida");
        product.setRating(1);
        product.setPrice(new BigDecimal("1.00"));
        product.setQuantityInStock(null);
        product.setStatus(ProductStatus.IN_STOCK);
        product.setDimensions(null);
        product.setWeight(null);
        product.setDateAdded(Instant.parse("2024-01-01T00:00:00Z"));
        product.setDateModified(null);
        Set<ConstraintViolation<Product>> violations = validator.validate(product);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("keywords")));
    }
    @Test
    public void testInvalidCase9() {
        Product product = new Product();
        product.setTitle("Um bom título");
        product.setKeywords("Algumas Palavras Chave");
        product.setDescription(generateString(9));  // Invalid because description length < 10
        product.setRating(1);
        product.setPrice(new BigDecimal("1.00"));
        product.setQuantityInStock(null);
        product.setStatus(ProductStatus.IN_STOCK);
        product.setDimensions(null);
        product.setWeight(null);
        product.setDateAdded(Instant.parse("2024-01-01T00:00:00Z"));
        product.setDateModified(null);
        Set<ConstraintViolation<Product>> violations = validator.validate(product);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("description")));
    }
    @Test
    public void testInvalidCase10() {
        Product product = new Product();
        product.setTitle("Um bom título");
        product.setKeywords("Algumas Palavras Chave");
        product.setDescription("Descrição válida");
        product.setRating(-1);  // Invalid because rating < 0
        product.setPrice(new BigDecimal("1.00"));
        product.setQuantityInStock(null);
        product.setStatus(ProductStatus.IN_STOCK);
        product.setDimensions(null);
        product.setWeight(null);
        product.setDateAdded(Instant.parse("2024-01-01T00:00:00Z"));
        product.setDateModified(null);
        Set<ConstraintViolation<Product>> violations = validator.validate(product);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("rating")));
    }
    @Test
    public void testInvalidCase11() {
        Product product = new Product();
        product.setTitle("Um bom título");
        product.setKeywords("Algumas Palavras Chave");
        product.setDescription("Descrição válida");
        product.setRating(6);  // Invalid because rating > 5
        product.setPrice(new BigDecimal("1.00"));
        product.setQuantityInStock(null);
        product.setStatus(ProductStatus.IN_STOCK);
        product.setDimensions(null);
        product.setWeight(null);
        product.setDateAdded(Instant.parse("2024-01-01T00:00:00Z"));
        product.setDateModified(null);
        Set<ConstraintViolation<Product>> violations = validator.validate(product);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("rating")));
    }
    @Test
    public void testInvalidCase12() {
        Product product = new Product();
        product.setTitle("Um bom título");
        product.setKeywords("Algumas Palavras Chave");
        product.setDescription("Descrição válida");
        product.setRating(1);
        product.setPrice(new BigDecimal("-1.00"));  // Invalid because price < 0
        product.setQuantityInStock(null);
        product.setStatus(ProductStatus.IN_STOCK);
        product.setDimensions(null);
        product.setWeight(null);
        product.setDateAdded(Instant.parse("2024-01-01T00:00:00Z"));
        product.setDateModified(null);
        Set<ConstraintViolation<Product>> violations = validator.validate(product);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("price")));
    }
    @Test
    public void testInvalidCase13() {
        Product product = new Product();
        product.setTitle("Um bom título");
        product.setKeywords("Algumas Palavras Chave");
        product.setDescription("Descrição válida");
        product.setRating(1);
        product.setPrice(new BigDecimal("1.00"));
        product.setQuantityInStock(-1);  // Invalid because quantityInStock < 0
        product.setStatus(ProductStatus.IN_STOCK);
        product.setDimensions(null);
        product.setWeight(null);
        product.setDateAdded(Instant.parse("2024-01-01T00:00:00Z"));
        product.setDateModified(null);
        Set<ConstraintViolation<Product>> violations = validator.validate(product);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("quantityInStock")));
    }
    @Test
    public void testInvalidCase14() {
        Product product = new Product();
        product.setTitle("Um bom título");
        product.setKeywords("Algumas Palavras Chave");
        product.setDescription("Descrição válida");
        product.setRating(1);
        product.setPrice(new BigDecimal("1.00"));
        product.setQuantityInStock(null);
        product.setStatus(ProductStatus.IN_STOCK);
        product.setDimensions(null);
        product.setWeight(-1.0);  // Invalid because weight < 0
        product.setDateAdded(Instant.parse("2024-01-01T00:00:00Z"));
        product.setDateModified(null);
        Set<ConstraintViolation<Product>> violations = validator.validate(product);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("weight")));
    }
    @Test
    public void testInvalidCase15() {
        Product product = new Product();
        product.setTitle("Um bom título");
        product.setKeywords("Algumas Palavras Chave");
        product.setDescription("Descrição válida");
        product.setRating(1);
        product.setPrice(new BigDecimal("1.00"));
        product.setQuantityInStock(null);
        product.setStatus(ProductStatus.IN_STOCK);
        product.setDimensions(generateString(51));  // Invalid because dimensions length > 50
        product.setWeight(null);
        product.setDateAdded(Instant.parse("2024-01-01T00:00:00Z"));
        product.setDateModified(null);
        Set<ConstraintViolation<Product>> violations = validator.validate(product);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("dimensions")));
    }
    @Test
    public void testInvalidCase16() {
        Product product = new Product();
        product.setTitle("Um bom título");
        product.setKeywords("Algumas Palavras Chave");
        product.setDescription("Descrição válida");
        product.setRating(1);
        product.setPrice(new BigDecimal("1.00"));
        product.setQuantityInStock(null);
        product.setStatus(ProductStatus.IN_STOCK);
        product.setDimensions(null);
        product.setWeight(null);
        product.setDateAdded(null);  // Invalid because dateAdded is @NotNull
        product.setDateModified(null);
        Set<ConstraintViolation<Product>> violations = validator.validate(product);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("dateAdded")));
    }
    @Test
    public void testInvalidCase17() {
        Product product = new Product();
        product.setTitle("Um bom título");
        product.setKeywords("Algumas Palavras Chave");
        product.setDescription("Descrição válida");
        product.setRating(1);
        product.setPrice(new BigDecimal("1.00"));
        product.setQuantityInStock(null);
        product.setStatus(ProductStatus.IN_STOCK);
        product.setDimensions(null);
        product.setWeight(null);
        product.setDateAdded(Instant.parse("2025-01-01T00:00:00Z"));  // Invalid because dateAdded is in the future
        product.setDateModified(null);
        Set<ConstraintViolation<Product>> violations = validator.validate(product);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("dateAdded")));
    }
    @Test
    public void testInvalidCase18() {
        Product product = new Product();
        product.setTitle("Um bom título");
        product.setKeywords("Algumas Palavras Chave");
        product.setDescription("Descrição válida");
        product.setRating(1);
        product.setPrice(new BigDecimal("1.00"));
        product.setQuantityInStock(null);
        product.setStatus(ProductStatus.IN_STOCK);
        product.setDimensions(null);
        product.setWeight(null);
        product.setDateAdded(Instant.parse("2024-01-01T00:00:00Z"));
        product.setDateModified(Instant.parse("2024-01-01T00:00:00Z"));  // Invalid because dateModified is the same as dateAdded
        Set<ConstraintViolation<Product>> violations = validator.validate(product);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("dateModified")));
    }
    @Test
    public void testInvalidCase19() {
        Product product = new Product();
        product.setTitle("Um bom título");
        product.setKeywords("Algumas Palavras Chave");
        product.setDescription("Descrição válida");
        product.setRating(1);
        product.setPrice(new BigDecimal("1.00"));
        product.setQuantityInStock(null);
        product.setStatus(ProductStatus.IN_STOCK);
        product.setDimensions(null);
        product.setWeight(null);
        product.setDateAdded(Instant.parse("2024-01-01T00:00:00Z"));
        product.setDateModified(Instant.parse("2024-01-01T00:00:00Z"));  // Invalid because dateModified is the same as dateAdded
        Set<ConstraintViolation<Product>> violations = validator.validate(product);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("dateModified")));
    }
    @Test
    public void testInvalidCase20() {
        Product product = new Product();
        product.setTitle("Um bom título");
        product.setKeywords("Algumas Palavras Chave");
        product.setDescription("Descrição válida");
        product.setRating(1);
        product.setPrice(new BigDecimal("1.00"));
        product.setQuantityInStock(null);
        product.setStatus(ProductStatus.IN_STOCK);
        product.setDimensions(null);
        product.setWeight(null);
        product.setDateAdded(Instant.parse("2024-01-01T00:00:00Z"));
        product.setDateModified(Instant.parse("2024-01-01T00:00:00Z"));  // Invalid because dateModified is the same as dateAdded
        Set<ConstraintViolation<Product>> violations = validator.validate(product);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("dateModified")));
    }
    @Test
    public void testInvalidCase21() {
        Product product = new Product();
        product.setTitle("Um bom título");
        product.setKeywords("Algumas Palavras Chave");
        product.setDescription("Descrição válida");
        product.setRating(1);
        product.setPrice(new BigDecimal("1.00"));
        product.setQuantityInStock(null);
        product.setStatus(ProductStatus.IN_STOCK);
        product.setDimensions(null);
        product.setWeight(null);
        product.setDateAdded(Instant.parse("2024-01-01T00:00:00Z"));
        product.setDateModified(Instant.parse("2024-01-01T00:00:00Z"));  // Invalid because dateModified is the same as dateAdded
        Set<ConstraintViolation<Product>> violations = validator.validate(product);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("dateModified")));
    }
    @Test
    public void testInvalidCase22() {
        Product product = new Product();
        product.setTitle("Um bom título");
        product.setKeywords("Algumas Palavras Chave");
        product.setDescription("Descrição válida");
        product.setRating(1);
        product.setPrice(new BigDecimal("1.00"));
        product.setQuantityInStock(null);
        product.setStatus(ProductStatus.IN_STOCK);
        product.setDimensions(generateString(51));  // Invalid because dimensions length > 50
        product.setWeight(null);
        product.setDateAdded(Instant.parse("2024-01-01T00:00:00Z"));
        product.setDateModified(null);
        Set<ConstraintViolation<Product>> violations = validator.validate(product);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("dimensions")));
    }
    @Test
    public void testInvalidCase23() {
        Product product = new Product();
        product.setTitle("Um bom título");
        product.setKeywords("Algumas Palavras Chave");
        product.setDescription("Descrição válida");
        product.setRating(1);
        product.setPrice(new BigDecimal("1.00"));
        product.setQuantityInStock(null);
        product.setStatus(ProductStatus.IN_STOCK);
        product.setDimensions(null);
        product.setWeight(null);
        product.setDateAdded(Instant.parse("2024-01-01T00:00:00Z"));
        product.setDateModified(Instant.parse("2024-01-01T00:00:00Z"));  // Invalid because dateModified is the same as dateAdded
        Set<ConstraintViolation<Product>> violations = validator.validate(product);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("dateModified")));
    }
    @Test
    public void testInvalidCase24() {
        Product product = new Product();
        product.setTitle("Um bom título");
        product.setKeywords("Algumas Palavras Chave");
        product.setDescription("Descrição válida");
        product.setRating(1);
        product.setPrice(new BigDecimal("1.00"));
        product.setQuantityInStock(null);
        product.setStatus(ProductStatus.IN_STOCK);
        product.setDimensions(null);
        product.setWeight(null);
        product.setDateAdded(Instant.parse("2024-01-01T00:00:00Z"));
        product.setDateModified(Instant.parse("2024-01-01T00:00:00Z"));  // Invalid because dateModified is the same as dateAdded
        Set<ConstraintViolation<Product>> violations = validator.validate(product);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("dateModified")));
    }
    @Test
    public void testInvalidCase25() {
        Product product = new Product();
        product.setTitle("Um bom título");
        product.setKeywords("Algumas Palavras Chave");
        product.setDescription("Descrição válida");
        product.setRating(1);
        product.setPrice(new BigDecimal("1.00"));
        product.setQuantityInStock(null);
        product.setStatus(ProductStatus.IN_STOCK);
        product.setDimensions(null);
        product.setWeight(null);
        product.setDateAdded(Instant.parse("2024-01-01T00:00:00Z"));
        product.setDateModified(Instant.parse("2024-01-01T00:00:00Z"));  // Invalid because dateModified is the same as dateAdded
        Set<ConstraintViolation<Product>> violations = validator.validate(product);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("dateModified")));
    }
    @Test
    public void testInvalidCase26() {
        Product product = new Product();
        product.setTitle("Um bom título");
        product.setKeywords("Algumas Palavras Chave");
        product.setDescription("Descrição válida");
        product.setRating(1);
        product.setPrice(new BigDecimal("1.00"));
        product.setQuantityInStock(null);
        product.setStatus(ProductStatus.IN_STOCK);
        product.setDimensions(null);
        product.setWeight(null);
        product.setDateAdded(Instant.parse("2024-01-01T00:00:00Z"));
        product.setDateModified(Instant.parse("2024-01-01T00:00:00Z"));  // Invalid because dateModified is the same as dateAdded
        Set<ConstraintViolation<Product>> violations = validator.validate(product);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("dateModified")));
    }
    @Test
    public void testInvalidCase27() {
        Product product = new Product();
        product.setTitle("Um bom título");
        product.setKeywords("Algumas Palavras Chave");
        product.setDescription("Descrição válida");
        product.setRating(1);
        product.setPrice(new BigDecimal("1.00"));
        product.setQuantityInStock(null);
        product.setStatus(ProductStatus.IN_STOCK);
        product.setDimensions(null);
        product.setWeight(null);
        product.setDateAdded(Instant.parse("2024-01-01T00:00:00Z"));
        product.setDateModified(Instant.parse("2024-01-01T00:00:00Z"));  // Invalid because dateModified is the same as dateAdded
        Set<ConstraintViolation<Product>> violations = validator.validate(product);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("dateModified")));
    }
    @Test
    public void testInvalidCase28() {
        Product product = new Product();
        product.setTitle("Um bom título");
        product.setKeywords("Algumas Palavras Chave");
        product.setDescription("Descrição válida");
        product.setRating(1);
        product.setPrice(new BigDecimal("1.00"));
        product.setQuantityInStock(null);
        product.setStatus(ProductStatus.IN_STOCK);
        product.setDimensions(null);
        product.setWeight(null);
        product.setDateAdded(Instant.parse("2024-01-01T00:00:00Z"));
        product.setDateModified(Instant.parse("2024-01-01T00:00:00Z"));  // Invalid because dateModified is the same as dateAdded
        Set<ConstraintViolation<Product>> violations = validator.validate(product);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("dateModified")));
    }
    @Test
    public void testInvalidCase29() {
        Product product = new Product();
        product.setTitle("Um bom título");
        product.setKeywords("Algumas Palavras Chave");
        product.setDescription("Descrição válida");
        product.setRating(1);
        product.setPrice(new BigDecimal("1.00"));
        product.setQuantityInStock(null);
        product.setStatus(ProductStatus.IN_STOCK);
        product.setDimensions(null);
        product.setWeight(null);
        product.setDateAdded(Instant.parse("2024-01-01T00:00:00Z"));
        product.setDateModified(Instant.parse("2024-01-01T00:00:00Z"));  // Invalid because dateModified is the same as dateAdded
        Set<ConstraintViolation<Product>> violations = validator.validate(product);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("dateModified")));
    }
    @Test
    public void testInvalidCase30() {
        Product product = new Product();
        product.setTitle("Um bom título");
        product.setKeywords("Algumas Palavras Chave");
        product.setDescription("Descrição válida");
        product.setRating(1);
        product.setPrice(new BigDecimal("1.00"));
        product.setQuantityInStock(null);
        product.setStatus(ProductStatus.IN_STOCK);
        product.setDimensions(null);
        product.setWeight(null);
        product.setDateAdded(Instant.parse("2024-01-01T00:00:00Z"));
        product.setDateModified(Instant.parse("2024-01-01T00:00:00Z"));  // Invalid because dateModified is the same as dateAdded
        Set<ConstraintViolation<Product>> violations = validator.validate(product);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("dateModified")));
    }
    @Test
    public void testInvalidCase31() {
        Product product = new Product();
        product.setTitle("Um bom título");
        product.setKeywords("Algumas Palavras Chave");
        product.setDescription("Descrição válida");
        product.setRating(1);
        product.setPrice(new BigDecimal("1.00"));
        product.setQuantityInStock(null);
        product.setStatus(ProductStatus.IN_STOCK);
        product.setDimensions(null);
        product.setWeight(null);
        product.setDateAdded(Instant.parse("2025-01-01T00:00:00Z"));  // Invalid because dateAdded is in the future
        product.setDateModified(null);
        Set<ConstraintViolation<Product>> violations = validator.validate(product);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("dateAdded")));
    }
}
