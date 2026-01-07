package webserver.view;

import static org.assertj.core.api.Assertions.assertThat;

import http.response.ModelAttributes;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ViewResolverTest {

    @Nested
    class ResolveView {

        @DisplayName("뷰 이름을 기반으로 정적 컨텐츠를 반환할 수 있다")
        @Test
        void resolve() throws IOException {
            ViewResolver viewResolver = new ViewResolver();
            byte[] bytes = Objects.requireNonNull(
                    getClass().getClassLoader()
                            .getResourceAsStream("static/test.txt")
            ).readAllBytes();
            String expectedValue = new String(bytes);

            View view = viewResolver.resolveStaticFileByName("/test.txt");
            String actual = new String(view.getContent(), "UTF-8");

            assertThat(actual).isEqualTo(expectedValue);
        }

        @DisplayName("모델과 뷰 이름을 기반으로 동적 컨텐츠를 반환할 수 있다")
        @Test
        void resolveDynamicView() throws IOException {
            ViewResolver viewResolver = new ViewResolver();
            String expectedValue = UUID.randomUUID().toString();
            ModelAttributes modelAttributes = new ModelAttributes(Map.of("test", expectedValue));

            View view = viewResolver.resolveStaticFileWithModelAttributes("/dynamicView.txt", modelAttributes);
            String actual = new String(view.getContent(), "UTF-8").trim();

            assertThat(actual).isEqualTo(expectedValue);
        }
    }
}
