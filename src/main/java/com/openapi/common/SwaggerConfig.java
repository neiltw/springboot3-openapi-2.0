package com.openapi.common;

import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.models.*;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.NumberSchema;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.parameters.Parameter;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.ArrayList;
import java.util.List;


/**
 * @author neillin
 * @since 2022/10/02 - 9:34 AM
 */
@Configuration
@EnableWebMvc
public class SwaggerConfig  {

    @Value("${app.version}")
    private String apVersion;

    /**
     * default Parameters "X-Auth-Token","userId","version"  header 參數
     * @return List<OperationCustomizer>
     */
    private List<OperationCustomizer> setDefaultHeader(){
        List<OperationCustomizer>  operationCustomizers = new ArrayList<>();
        //default header type
        String[] header = {"X-Auth-Token","userId","version"};
        String[] headerName = {"授權驗証","user_id","版本號"};

        for (int i = 0; i < header.length; i++) {
            String setHeader = header[i];
            String setHeaderName = headerName[i];
            OperationCustomizer operationCustomizer = new OperationCustomizer() {
                @Override
                public Operation customize(Operation operation, HandlerMethod handlerMethod) {
                    Parameter customHeaderVersion = new Parameter();

                    if(setHeader.equals("userId")) {
                        customHeaderVersion.in(ParameterIn.HEADER.toString())
                                .name(setHeader)
                                .description(setHeaderName)
                                .schema(new NumberSchema()) //long
                                .required(false);
                    } else {
                        customHeaderVersion.in(ParameterIn.HEADER.toString())
                                .name(setHeader)
                                .description(setHeaderName)
                                .schema(new StringSchema()) //string
                                .required(false);
                    }
                    operation.addParametersItem(customHeaderVersion);
                    return operation;
                }
            };
            operationCustomizers.add(operationCustomizer);
        }
        return operationCustomizers;
    }

    /**
     * @return GroupedOpenApi
     */
    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("OpenApi") //顯示在Select a definition 可以分層，
                .pathsToMatch("/**") //全部 controller
                .build()
                .addAllOperationCustomizer(this.setDefaultHeader());
    }


    @Bean
    public OpenAPI springShopOpenAPI(){
        return new OpenAPI()
                .components(new Components())
                .info(new Info()
                        .title("OpenApi-API服務")
                        .version(apVersion)
                        .description("API服務"))
                .externalDocs(new ExternalDocumentation()
                        .description("Spring-boot v3 + OpenAPI 3")
                        .url("https://springdoc.org/v2") );
    }



}