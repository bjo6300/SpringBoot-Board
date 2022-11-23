package boardexample.board.learning;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordEncoderTest {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void 패스워드_암호화() throws Exception {
        //given
        String password = "qownsdlf";

        //when
        String encodePassword = passwordEncoder.encode(password);

        //then
        assertThat(encodePassword).startsWith("{");
        assertThat(encodePassword).contains("{bcrypt}");
        assertThat(encodePassword).isNotEqualTo(password);

    }

    @Test
    public void 패스워드_랜덤_암호화() throws Exception {
        //given
        String password = "qownsdlf";

        //when
        String encodePassword = passwordEncoder.encode(password);
        String encodePassword2 = passwordEncoder.encode(password);

        //then
        assertThat(encodePassword).isNotEqualTo(encodePassword2);

    }

    @Test
    public void 암호화된_비밀번호_매치() throws Exception {
        //given
        String password = "qownsdlf";

        //when
        String encodePassword = passwordEncoder.encode(password);

        //then
        assertThat(passwordEncoder.matches(password, encodePassword)).isTrue();

    }
}
