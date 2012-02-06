/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.ivory.listener;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import org.apache.ivory.util.RuntimeProperties;
import org.apache.ivory.util.StartupProperties;
import org.apache.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ContextStartupListener implements ServletContextListener {

    private static Logger LOG = Logger.getLogger(ContextStartupListener.class);

    private static final String BUILD_PROPERTIES = "ivory-buildinfo.properties";

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        //Initialize Startup and runtime properties instance for use
        LOG.info("Initializing startup properties ...");
        StartupProperties.get();

        showStartupInfo();

        LOG.info("Initializing runtime properties ...");
        RuntimeProperties.get();
    }

    private void showStartupInfo() {
        StringBuilder buffer = new StringBuilder();
        buffer.append("\n############################################");
        buffer.append("############################################");
        buffer.append("\n                               Ivory Server (STARTUP)");
        buffer.append("\n");
        Properties buildProperties = new Properties();
        try {
            buildProperties.load(getClass().getClassLoader().
                        getResourceAsStream(BUILD_PROPERTIES));
            for (Map.Entry entry : buildProperties.entrySet()) {
                buffer.append('\n').append('\t').append(entry.getKey()).
                        append(":\t").append(entry.getValue());
            }
        } catch (Throwable e) {
            buffer.append("*** Unable to get build info ***");
        }
        buffer.append("\n############################################");
        buffer.append("############################################");
        LOG.info(buffer);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        StringBuilder buffer = new StringBuilder();
        buffer.append("\n############################################");
        buffer.append("\n         Ivory Server (SHUTDOWN)            ");
        buffer.append("\n############################################");
        LOG.info(buffer);
    }
}