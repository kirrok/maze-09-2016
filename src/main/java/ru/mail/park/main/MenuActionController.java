package ru.mail.park.main;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.mail.park.exceptions.ErrorMsg;

import java.util.List;
import java.util.Map;

import static org.springframework.http.ResponseEntity.ok;

/**
 * Created by kirrok on 05.11.16.
 */

@RestController
public class MenuActionController extends AbstractAccountController {

    @RequestMapping(path = "/api/scoreboard", method = RequestMethod.GET)
    public ResponseEntity<?> score(@RequestParam("limit") String limit) {
        final List<Map<String, Object>> scoreboard = accountService.score(limit);
        if (scoreboard == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorMsg(ErrorMsg.SERVER_ERROR_MSG));
        }
        return ok(accountService.score(limit));
    }
}
