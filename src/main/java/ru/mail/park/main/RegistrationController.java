package ru.mail.park.main;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.mail.park.exceptions.ErrorResponse;
import ru.mail.park.models.User;
import ru.mail.park.services.impl.AccountServiceImpl;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import static org.springframework.http.ResponseEntity.ok;

/**
 * Created by Solovyev on 06/09/16.
 */

@Transactional
@RestController
public class RegistrationController {
    private final AccountServiceImpl accountService;

    private final String USER_ID = "id";

    @Autowired
    public RegistrationController(AccountServiceImpl accountService) {
        this.accountService = accountService;
    }

    @RequestMapping(path = "/api/session", method = RequestMethod.GET)
    public ResponseEntity sessionCheck(HttpSession session) {
        final Long selfId = (Long) session.getAttribute(USER_ID);
        if (selfId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponse(HttpStatus.UNAUTHORIZED.toString(), ErrorResponse.NOT_LOGGED_IN_MSG));
        }
        return ok(new SuccessResponse(selfId));
    }

    @RequestMapping(path = "/api/scoreboard", method = RequestMethod.GET)
    public ResponseEntity score(@RequestParam("limit") String limit) {
        return ok(accountService.score(limit));
    }

    @RequestMapping(path = "/api/registration", method = RequestMethod.POST)
    public ResponseEntity registration(@RequestBody @Valid UserDataRequest body, HttpSession session) {
        final String login = body.getLogin();
        final String password = body.getPassword();

        User user = accountService.getUserByLogin(login);

        if (user != null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new ErrorResponse(HttpStatus.FORBIDDEN.toString(), ErrorResponse.USER_ALREADY_EXISTS_MSG));
        }
        user = new User(login, password);

        final Long id = accountService.addUser(user);
        if (id == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.toString(), ErrorResponse.SERVER_ERROR_MSG));
        }
        session.setAttribute(USER_ID, id.toString());

        return ok(new SuccessResponse(id));
    }

    @RequestMapping(path = "/api/auth", method = RequestMethod.POST)
    public ResponseEntity auth(@RequestBody @Valid UserDataRequest body, HttpSession session) {
        final String login = body.getLogin();
        final String password = body.getPassword();

        final User user = accountService.getUserByLogin(login);

        if (user == null || !user.getPassword().equals(password)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponse(HttpStatus.UNAUTHORIZED.toString(), ErrorResponse.AUTHORIZATION_ERROR_MSG));
        }
        session.setAttribute(USER_ID, user.getId());

        return ok(new SuccessResponse(user.getId()));
    }

    @RequestMapping(path = "/api/logout", method = RequestMethod.DELETE)
    public ResponseEntity logout(HttpSession session) {
        final Long selfId = (Long)session.getAttribute(USER_ID);
        if (selfId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponse(HttpStatus.UNAUTHORIZED.toString(), ErrorResponse.NOT_LOGGED_IN_MSG));
        }
        session.removeAttribute(USER_ID);
        return ok(new SuccessResponse(selfId));
    }

    @RequestMapping(path = "/api/user/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getUser(@PathVariable("id") long userId, HttpSession session) {
        final Long selfId = (Long) session.getAttribute(USER_ID);
        if (selfId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponse(HttpStatus.UNAUTHORIZED.toString(), ErrorResponse.NOT_LOGGED_IN_MSG));
        }
        final User user = accountService.getUserById(userId);
        if (user == null) {
            return ResponseEntity
                    .ok(new ErrorResponse(HttpStatus.NO_CONTENT.toString(), ErrorResponse.USER_NOT_EXIST));
        }
        return ResponseEntity.ok(user);
    }

    @RequestMapping(path = "/api/user", method = RequestMethod.PUT)
    public ResponseEntity<?> updateUser(@RequestBody @Valid UserDataRequest body, HttpSession session) {
        final Long selfId = (Long) session.getAttribute(USER_ID);
        if (selfId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponse(HttpStatus.UNAUTHORIZED.toString(), ErrorResponse.NOT_LOGGED_IN_MSG));
        }
        final User newUserData = new User(body.getLogin(), body.getPassword());

        newUserData.setId(selfId);
        accountService.updateUser(newUserData);

        return ResponseEntity.ok(new SuccessResponse(selfId));
    }

    @RequestMapping(path = "/api/user", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteUser(HttpSession session) {
        final Long selfId = (Long) session.getAttribute(USER_ID);
        if (selfId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponse(HttpStatus.UNAUTHORIZED.toString(), ErrorResponse.NOT_LOGGED_IN_MSG));
        }

        accountService.deleteUser(selfId);
        session.removeAttribute(USER_ID);

        return ResponseEntity.ok(new SuccessResponse(selfId));
    }

    private static final class UserDataRequest {
        @NotNull
        private String login;
        @NotNull
        private String password;

        private UserDataRequest() {
        }

        public String getLogin() {
            return login;
        }

        public String getPassword() {
            return password;
        }
    }

    private static final class SuccessResponse {
        private long id;

        private SuccessResponse(long id) {
            this.id = id;
        }

        public long getId() {
            return id;
        }
    }
}
