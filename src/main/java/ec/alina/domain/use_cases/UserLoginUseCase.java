package ec.alina.domain.use_cases;

import ec.alina.domain.models.Session;
import ec.alina.domain.models.User;
import ec.alina.domain.repositories.SessionRepository;
import ec.alina.domain.repositories.UserRepository;
import ec.alina.domain.validations.exceptions.InvalidEmailOrPasswordException;

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
