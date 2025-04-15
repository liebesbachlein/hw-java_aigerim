package space.service.auth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import space.entity.Session;
import space.entity.User;
import space.repository.SessionRepo;
import space.repository.UserRepo;
import space.util.DuplicateUserException;
import space.util.InvalidSessionException;
import space.util.RepositoryException;
import space.util.UserTokenPair;

import javax.security.auth.login.CredentialException;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class AuthSessionService {
    private final UserRepo userRepo;
    private final SessionRepo sessionRepo;

    @Autowired
    public AuthSessionService(UserRepo userRepo, SessionRepo sessionRepo) {
        this.userRepo = userRepo;
        this.sessionRepo = sessionRepo;
    }

    public UserTokenPair login(String email, String password)
            throws RepositoryException, CredentialException {
        User user = userRepo.findByEmail(email).orElseThrow(CredentialException::new);
        if (user.getPassword().equals(EncryptionService.decode(password))) {
            Session session = generateSession(user);
            return new UserTokenPair(user, session);
        } else throw new CredentialException();
    }

    public UserTokenPair register(String name, String email, String password)
            throws RepositoryException, DuplicateUserException {
        if (userRepo.findByEmail(email).isPresent()) {
            throw new DuplicateUserException();
        } else {
            User user = userRepo.save(
                    new User(0, name, email,
                            EncryptionService.encode(password),
                            User.Role.USER_ROLE));
            Session session = generateSession(user);
            return new UserTokenPair(user, session);
        }
    }

    public User auth(String sessionId) throws InvalidSessionException {
        return getValidSession(sessionId).getUser();
    }

    public void logoutUser(String sessionId) throws RepositoryException {
        invalidateSession(sessionId);
    }

    public void logoutUserFromAllDevices(String sessionId) throws RepositoryException {
        Optional<Session> session = sessionRepo.findById(sessionId);
        if(session.isPresent()) invalidateAllSessions(session.get().getUser().getEmail());
    }

    public boolean isAdmin(User user) {
        return user.getRole() == User.Role.ADMIN_ROLE;
    }

    private Session getValidSession(String id) throws InvalidSessionException {
        return sessionRepo.findById(id).orElseThrow(InvalidSessionException::new);
    }

    private Session generateSession(User user) throws RepositoryException {
        return sessionRepo.save(new Session(null, user));
    }

    private void invalidateSession(String id) throws RepositoryException {
        sessionRepo.delete(id);
    }

    private void invalidateAllSessions(String email) throws RepositoryException {
        sessionRepo.deleteInBatchByEmail(email);
    }
}
