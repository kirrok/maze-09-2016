package ru.mail.park.java.models;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.test.context.junit4.SpringRunner;
import ru.mail.park.models.User;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by kirrok on 04.11.16.
 */
@JsonTest
@RunWith(SpringRunner.class)
public class UserTest {

    public static final int MAX_SCORE = 121;
    public static final int ID = 96;
    public static final String PASSWORD = "somePassword";
    public static final String LOGIN = "someLogin";


    @Autowired
    private JacksonTester<User> json;

    @Test
    public void testSerialize() throws IOException {
        final User user = new User(LOGIN, PASSWORD);
        user.setId(ID);
        user.setMaxScore(MAX_SCORE);


        assertThat(this.json.write(user)).hasJsonPathStringValue("login");
        assertThat(this.json.write(user)).hasJsonPathNumberValue("maxScore");
        assertThat(this.json.write(user)).hasJsonPathNumberValue("id");

        assertThat(this.json.write(user)).extractingJsonPathStringValue("login").isEqualTo(LOGIN);
        assertThat(this.json.write(user)).extractingJsonPathNumberValue("maxScore").isEqualTo(MAX_SCORE);
        assertThat(this.json.write(user)).extractingJsonPathNumberValue("id").isEqualTo(ID);

        assertThat(this.json.write(user)).doesNotHaveJsonPathValue("password");
    }

    @Test
    public void testDeserialize() throws IOException {
        final String content = "{\"login\":\"someLogin\",\"password\":\"somePassword\",\"maxScore\": 121,\"id\":96}";

        final User user = new User(LOGIN, PASSWORD);
        user.setId(ID);
        user.setMaxScore(MAX_SCORE);

        assertThat(this.json.parse(content)).isEqualTo(user);

        assertThat(this.json.parseObject(content).getId()).isEqualTo(ID);
        assertThat(this.json.parseObject(content).getMaxScore()).isEqualTo(MAX_SCORE);
        assertThat(this.json.parseObject(content).getPassword()).isEqualTo(PASSWORD);
        assertThat(this.json.parseObject(content).getLogin()).isEqualTo(LOGIN);
    }

}