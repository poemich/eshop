package id.ac.ui.cs.advprog.eshop.config;

import java.util.Locale;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.AbstractView;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
public class StubViewResolverConfig {

    @Bean
    public ViewResolver stubViewResolver() {
        return (viewName, locale) -> new AbstractView() {
            @Override
            protected void renderMergedOutputModel(Map<String, Object> model,
                                                     HttpServletRequest request,
                                                     HttpServletResponse response) throws Exception {
                // stub: do nothing
            }
        };
    }
}
