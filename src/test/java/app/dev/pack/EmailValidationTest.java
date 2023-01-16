package app.dev.pack;

import app.dev.pack.controller.validation.EmailValidator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class EmailValidationTest {

    @Autowired
    private EmailValidator emailValidator;

    String pattern1 = "email_test@gmail.com";
    String pattern2 = "email.test@gmail.com";
    String pattern3 = "email-test@gmail.com";
    String pattern4 = "email1234@gmail.com";
    String pattern5 = "EmailTest/@gmail.com";

    @Test
    void EmailTest1() {
        boolean isValidEmail = emailValidator.test(pattern1);
        assertTrue(isValidEmail);
    }
    @Test
    void EmailTest2() {
        boolean isValidEmail = emailValidator.test(pattern2);
        assertTrue(isValidEmail);
    }
    @Test
    void EmailTest3() {
        boolean isValidEmail = emailValidator.test(pattern3);
        assertTrue(isValidEmail);
    }
    @Test
    void EmailTest4() {
        boolean isValidEmail = emailValidator.test(pattern4);
        assertTrue(isValidEmail);
    }
    @Test
    void EmailTest5() {
        boolean isValidEmail = emailValidator.test(pattern5);
        assertTrue(isValidEmail);
    }
}
