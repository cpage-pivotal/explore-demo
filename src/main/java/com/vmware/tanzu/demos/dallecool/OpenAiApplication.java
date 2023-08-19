/*
 * Copyright (c) 2023 VMware, Inc. or its affiliates
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.vmware.tanzu.demos.dallecool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.web.exchanges.HttpExchangeRepository;
import org.springframework.boot.actuate.web.exchanges.InMemoryHttpExchangeRepository;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@ConfigurationPropertiesScan
public class OpenAiApplication {
    private static final Logger LOGGER = LoggerFactory.getLogger(OpenAiApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(OpenAiApplication.class, args);
    }

    @Autowired
    private CompletionService completionService;
    @Autowired
    private DalleImageGeneratorService dalleImageGeneratorService;

    @Bean
    CommandLineRunner onStart(OpenAiConfiguration openai) {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                LOGGER.info("Using OpenAI base URL: {}", openai.api());
                LOGGER.info("Loaded OpenAI key: {}", openai.key().replaceAll(".", "*"));
            }
        };
    }

    @Bean
    public HttpExchangeRepository httpExchangeRepository() {
        return new InMemoryHttpExchangeRepository();
    }
}
