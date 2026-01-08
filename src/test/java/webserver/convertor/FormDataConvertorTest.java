package webserver.convertor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import application.dto.request.LoginRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class FormDataConvertorTest {


    @DisplayName("폼 형식의 데이터를 객체로 전환할 수 있다")
    @Test
    void resolve() {
        FormDataConvertor convertor = new FormDataConvertor();

        LoginRequest result = (LoginRequest) convertor.resolveBody("userId=coli&password=pass1234", LoginRequest.class);

        assertAll(
                () -> assertThat(result.getPassword()).isEqualTo("pass1234"),
                () -> assertThat(result.getUserId()).isEqualTo("coli")
        );
    }
}
