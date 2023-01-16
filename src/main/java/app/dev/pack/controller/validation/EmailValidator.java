package app.dev.pack.controller.validation;

import org.springframework.stereotype.Service;

import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class EmailValidator implements Predicate<String> {

    private static final Pattern VALID_EMAIL_REGEX =
            Pattern.compile("^[a-zA-Z0-9._]+@gmail.com$", Pattern.CASE_INSENSITIVE);

    @Override
    public boolean test(String email) {
        Matcher matcher = VALID_EMAIL_REGEX.matcher(email);
        return matcher.find();
    }
}
