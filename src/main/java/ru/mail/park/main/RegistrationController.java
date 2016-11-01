package ru.mail.park.main;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.mail.park.exceptions.ErrorResponse;
import ru.mail.park.dataSets.UserDataSet;
import ru.mail.park.services.impl.AccountServiceImpl;
import ru.mail.park.services.impl.SessionServiceImpl;

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
    private final SessionServiceImpl sessionService;

    @Autowired
    public RegistrationController(AccountServiceImpl accountService,
                                  SessionServiceImpl sessionService) {
        this.accountService = accountService;
        this.sessionService = sessionService;
    }

    @RequestMapping(path = "/session", method = RequestMethod.GET)
    public ResponseEntity sessionCheck(HttpSession session) {

        final UserDataSet user = sessionService.getUser(session);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponse(HttpStatus.UNAUTHORIZED.toString(), ErrorResponse.NOT_LOGGED_IN_MSG));
        }
        return ok(new SuccessResponse(user.getId()));
    }

    @RequestMapping(path = "/scoreboard", method = RequestMethod.GET)
    public ResponseEntity score(@RequestParam("limit") String limit) {
        return ok(accountService.score(limit));
    }

    @RequestMapping(path = "/registration", method = RequestMethod.POST)
    public ResponseEntity registration(@RequestBody @Valid UserDataRequest body, HttpSession session) {

        final String login = body.getLogin();
        final String password = body.getPassword();

        UserDataSet user = accountService.getUserByLogin(login);

        if (user != null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new ErrorResponse(HttpStatus.FORBIDDEN.toString(), ErrorResponse.USER_ALREADY_EXISTS_MSG));
        }
        user = new UserDataSet(login, password);

        final Long id = accountService.addUser(user);
        if (id == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.toString(), ErrorResponse.SERVER_ERROR_MSG));
        }
        sessionService.addUser(session, user);
        return ok(new SuccessResponse(id));
    }

    @RequestMapping(path = "/auth", method = RequestMethod.POST)
    public ResponseEntity auth(@RequestBody @Valid UserDataRequest body, HttpSession session) {


        final String login = body.getLogin();
        final String password = body.getPassword();

        final UserDataSet user = accountService.getUserByLogin(login);

        if (user == null || !user.getPassword().equals(password)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponse(HttpStatus.UNAUTHORIZED.toString(), ErrorResponse.AUTHORIZATION_ERROR_MSG));
        }

        sessionService.addUser(session, user);
        return ok(new SuccessResponse(user.getId()));
    }

    @RequestMapping(path = "/logout", method = RequestMethod.DELETE)
    public ResponseEntity logout(HttpSession session) {

        final UserDataSet user = sessionService.getUser(session);
        Util.println(session.getId());

        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponse(HttpStatus.UNAUTHORIZED.toString(), ErrorResponse.NOT_LOGGED_IN_MSG));
        }

        sessionService.delUser(session);
        return ok(new SuccessResponse(user.getId()));
    }

    @RequestMapping(path = "/user/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getUser(@PathVariable("id") long id, HttpSession session) {
        final UserDataSet loggedInUser = sessionService.getUser(session);

        if (loggedInUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponse(HttpStatus.UNAUTHORIZED.toString(), ErrorResponse.NOT_LOGGED_IN_MSG));
        }
        final UserDataSet user = accountService.getUserById(id);
        if (user == null) {
            return ResponseEntity
                    .ok(new ErrorResponse(HttpStatus.NO_CONTENT.toString(), ErrorResponse.USER_NOT_EXIST));
        }
        return ResponseEntity.ok(user);
    }

    @RequestMapping(path = "/user", method = RequestMethod.PUT)
    public ResponseEntity<?> updateUser(@RequestBody @Valid UserDataRequest body, HttpSession session) {
        final UserDataSet user = sessionService.getUser(session);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponse(HttpStatus.UNAUTHORIZED.toString(), ErrorResponse.NOT_LOGGED_IN_MSG));
        }

        final UserDataSet newUserData = new UserDataSet(body.getLogin(), body.getPassword());
        newUserData.setId(user.getId());
        newUserData.setMaxScore(user.getMaxScore());
        accountService.updateUser(newUserData);

        return ResponseEntity.ok(new SuccessResponse(newUserData.getId()));
    }

    @RequestMapping(path = "/user", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteUser(HttpSession session) {
        final UserDataSet user = sessionService.getUser(session);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponse(HttpStatus.UNAUTHORIZED.toString(), ErrorResponse.NOT_LOGGED_IN_MSG));
        }

        accountService.deleteUser(user.getId());
        return ResponseEntity.ok(new SuccessResponse(user.getId()));
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
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String login;
        @JsonInclude(JsonInclude.Include.NON_DEFAULT)
        private long id;

        private SuccessResponse(String login) {
            this.login = login;
        }

        private SuccessResponse(long id) {
            this.id = id;
        }

        public String getLogin() {
            return login;
        }

        public long getId() {
            return id;
        }
    }
}
