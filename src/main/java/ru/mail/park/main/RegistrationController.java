package ru.mail.park.main;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mail.park.exceptions.ErrorMsg;
import ru.mail.park.models.User;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import static org.springframework.http.ResponseEntity.ok;

/**
 * Created by kirrok on 06/09/16.
 */

@RestController
public class RegistrationController extends AbstractAccountController {
    private final String USER_ID = "id";

    @RequestMapping(path = "/api/session", method = RequestMethod.GET)
    public ResponseEntity<ResponseBody> sessionCheck(HttpSession session) {
        final Long selfId = (Long)session.getAttribute(USER_ID);
        if (selfId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ResponseBody(ErrorMsg.NOT_LOGGED_IN_MSG));
        }
        return ok(new ResponseBody(selfId));
    }

    @RequestMapping(path = "/api/registration", method = RequestMethod.POST)
    public ResponseEntity<ResponseBody> registration(@RequestBody @Valid UserDataRequest body, HttpSession session) {
        final String login = body.getLogin();
        final String password = body.getPassword();
        User user = accountService.getUserByLogin(login);

        if (user != null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new ResponseBody(ErrorMsg.USER_ALREADY_EXISTS_MSG));
        }
        user = new User(login, password);
        final Long id = accountService.addUser(user);

        if (id == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseBody(ErrorMsg.SERVER_ERROR_MSG));
        }
        session.setAttribute(USER_ID, id);

        return ok(new ResponseBody(id));
    }

    @RequestMapping(path = "/api/auth", method = RequestMethod.POST)
    public ResponseEntity<ResponseBody> auth(@RequestBody @Valid UserDataRequest body, HttpSession session) {
        final String login = body.getLogin();

        final User user = accountService.getUserByLogin(login);

        if (user == null || !accountService.passwordIsCorrect(user, body.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ResponseBody(ErrorMsg.AUTHORIZATION_ERROR_MSG));
        }
        session.setAttribute(USER_ID, user.getId());

        return ok(new ResponseBody(user.getId()));
    }

    @RequestMapping(path = "/api/logout", method = RequestMethod.DELETE)
    public ResponseEntity<ResponseBody> logout(HttpSession session) {
        final Long selfId = (Long) session.getAttribute(USER_ID);
        if (selfId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ResponseBody(ErrorMsg.NOT_LOGGED_IN_MSG));
        }
        session.removeAttribute(USER_ID);
        return ok(new ResponseBody(selfId));
    }

    @RequestMapping(path = "/api/user/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getUser(@PathVariable(USER_ID) long userId, HttpSession session) {
        final Long selfId = (Long) session.getAttribute(USER_ID);
        if (selfId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ResponseBody(ErrorMsg.NOT_LOGGED_IN_MSG));
        }
        final User user = accountService.getUserById(userId);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body(new ResponseBody(ErrorMsg.USER_NOT_EXIST));
        }
        return ResponseEntity.ok(user);
    }

    @RequestMapping(path = "/api/user", method = RequestMethod.PUT)
    public ResponseEntity<ResponseBody> updateUser(@RequestBody @Valid UserDataRequest body, HttpSession session) {
        final Long selfId = (Long) session.getAttribute(USER_ID);
        if (selfId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ResponseBody(ErrorMsg.NOT_LOGGED_IN_MSG));
        }

        final User newUserData = new User(body.getLogin(),body.getPassword());

        newUserData.setId(selfId);
        accountService.updateUser(newUserData);

        return ResponseEntity.ok(new ResponseBody(selfId));
    }

    @RequestMapping(path = "/api/user", method = RequestMethod.DELETE)
    public ResponseEntity<ResponseBody> deleteUser(HttpSession session) {
        final Long selfId = (Long) session.getAttribute(USER_ID);
        if (selfId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ResponseBody(ErrorMsg.NOT_LOGGED_IN_MSG));
        }

        accountService.deleteUser(selfId);
        session.removeAttribute(USER_ID);

        return ResponseEntity.ok(new ResponseBody(selfId));
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

    private static final class ResponseBody {
        @JsonInclude(JsonInclude.Include.NON_DEFAULT)
        private long id;
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String msg;

        private ResponseBody(long id) {
            this.id = id;
        }

        private ResponseBody(String msg) {
            this.msg = msg;
        }

        public long getId() {
            return id;
        }

        public String getMsg() {
            return msg;
        }
    }
}
