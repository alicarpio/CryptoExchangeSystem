package ec.alina.domain.use_cases;

import ec.alina.domain.models.Session;
import ec.alina.domain.models.User;
import ec.alina.domain.repositories.SessionRepository;
import ec.alina.domain.repositories.UserRepository;

public class UserLoginUseCase {
    private final UserRepository users;
    private final SessionRepository sessions;

    public UserLoginUseCase(UserRepository users, SessionRepository sessions) {
        this.users = users;
        this.sessions = sessions;
    }

    public Session invoke(String email, String password) {
        User user = users.findByEmail(email);

        if (user == null | !user.getPassword().equals(password)) {
            throw new IllegalArgumentException("Invalid email or password");
        }

        Session session = new Session(user);
        sessions.save(session);

        return session;
    }
}
