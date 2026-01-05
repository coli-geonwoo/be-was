package webserver.view;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Objects;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ViewResolverTest {

    @Nested
    class ResolveView {

//        @DisplayName("뷰 이름을 기반으로 정적 컨텐츠를 반환할 수 있다")
//        @Test
//        void resolve() throws IOException {
//            ViewResolver viewResolver = new ViewResolver();
//            byte[] bytes = Objects.requireNonNull(
//                    getClass().getClassLoader()
//                            .getResourceAsStream("static/test.txt")
//            ).readAllBytes();
//            String expectedValue = new String(bytes);
//
//            View view = viewResolver.resolveStaticFileByName("/test.txt");
//            String actual = new String(view.getContent(), "UTF-8");
//
//            assertThat(actual).isEqualTo(expectedValue);
//        }
    }
}
