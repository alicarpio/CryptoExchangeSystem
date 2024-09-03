package io.github.alicarpio.domain.use_cases;

import io.github.alicarpio.domain.models.Session;
import io.github.alicarpio.domain.models.User;
import io.github.alicarpio.domain.repositories.SessionRepository;
import io.github.alicarpio.domain.repositories.UserRepository;
import io.github.alicarpio.domain.validations.exceptions.InvalidEmailOrPasswordException;

public class UserLoginUseCase {
    private final UserRepository users;
    private final SessionRepository sessions;

    public UserLoginUseCase(UserRepository users, SessionRepository sessions) {
        this.users = users;
        this.sessions = sessions;
    }

    public Boolean invoke(String email, String password) throws InvalidEmailOrPasswordException {
        User user = users.findByEmail(email);

        if (user == null || !user.getPassword().equals(password)) {
            throw new InvalidEmailOrPasswordException();
        }

        Session session = new Session(user);
        sessions.save(session);
        return true;
    }
}
