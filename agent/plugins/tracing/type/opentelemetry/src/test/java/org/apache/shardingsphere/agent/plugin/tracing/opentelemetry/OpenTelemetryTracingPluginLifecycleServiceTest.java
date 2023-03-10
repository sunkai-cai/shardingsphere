/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.shardingsphere.agent.plugin.tracing.opentelemetry;

import io.opentelemetry.api.GlobalOpenTelemetry;
import org.apache.shardingsphere.agent.api.PluginConfiguration;
import org.apache.shardingsphere.test.util.PropertiesBuilder;
import org.apache.shardingsphere.test.util.PropertiesBuilder.Property;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public final class OpenTelemetryTracingPluginLifecycleServiceTest {
    
    private final OpenTelemetryTracingPluginLifecycleService pluginLifecycleService = new OpenTelemetryTracingPluginLifecycleService();
    
    @AfterEach
    public void close() {
        pluginLifecycleService.close();
        GlobalOpenTelemetry.resetForTest();
    }
    
    @Test
    public void assertStart() {
        pluginLifecycleService.start(new PluginConfiguration(null, 0, null,
                PropertiesBuilder.build(new Property("otel.resource.attributes", "service.name=shardingsphere-agent"), new Property("otel.traces.exporter", "zipkin"))), true);
        assertNotNull(GlobalOpenTelemetry.getTracerProvider());
        assertNotNull(GlobalOpenTelemetry.getTracer("shardingsphere-agent"));
    }
}
