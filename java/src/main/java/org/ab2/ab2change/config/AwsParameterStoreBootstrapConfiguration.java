package org.ab2.ab2change.config;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.secretsmanager.AWSSecretsManager;
import com.amazonaws.services.simplesystemsmanagement.AWSSimpleSystemsManagement;
import com.amazonaws.services.simplesystemsmanagement.AWSSimpleSystemsManagementClientBuilder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.aws.paramstore.AwsParamStoreProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(AwsParamStoreProperties.class)
@ConditionalOnClass({ AWSSimpleSystemsManagement.class })
@ConditionalOnProperty(prefix = AwsParamStoreProperties.CONFIG_PREFIX, name = "enabled", matchIfMissing = true)
public class AwsParameterStoreBootstrapConfiguration {
    public static final String REGION = "ap-southeast-1";

    @Bean
    @ConditionalOnMissingBean
    AWSSimpleSystemsManagement simpleSystemsManagementClient() {
        AWSSimpleSystemsManagement simpleSystemsManagementClient =
        AWSSimpleSystemsManagementClientBuilder.standard()
                .withRegion(REGION).build();
        return simpleSystemsManagementClient;
    }
}
