package com.xu.config.swagger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author Administrator
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Value("${swagger.scanPackage}")
    private String scanPackage;

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .pathMapping("/")
                .select()
                .apis(RequestHandlerSelectors.basePackage(scanPackage))
                .paths(PathSelectors.any())
                .build().apiInfo(new ApiInfoBuilder()
                        .title("后台管理文档")
                        .description(this.description())
                        .version("9.0")
                        .contact(new Contact("xz","","9045@qq.com"))
                        .build());
    }

    private String description() {
        StringBuffer sb = new StringBuffer();
        sb.append("").append("</br>");
        sb.append("状态码表述，{}代表动态参数").append("</br>");
        sb.append("<table class=\"fullwidth parameters\">");
        sb.append("<tr><td>状态码</td><td>描述<td>").append(this.statusTR());
        sb.append("</table>");
        return sb.toString();
    }

    private String statusTR() {
        return  "" +
                "2000 操作成功  \n" +
                "9000 操作失败  \n" +
                "9998 系统异常  \n" +
                "9999 系统异常";
    }
}
