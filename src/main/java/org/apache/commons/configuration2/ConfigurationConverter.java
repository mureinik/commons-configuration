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

package org.apache.commons.configuration2;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;

/**
 * Configuration converter. Helper class to convert between Configuration,
 * Map, and standard Properties.
 *
 * @author <a href="mailto:mpoeschl@marmot.at">Martin Poeschl</a>
 * @version $Revision$, $Date$
 */
public final class ConfigurationConverter
{
    /**
     * Private constructor prevents instances from being created.
     */
    private ConfigurationConverter()
    {
        // to prevent instantiation...
    }

    /**
     * Convert a standard Properties class into a configuration class.
     *
     * @param props properties object to convert
     * @return Configuration configuration created from the Properties
     */
    public static Configuration getConfiguration(Properties props)
    {
        return new MapConfiguration(props);
    }

    /**
     * Convert a Configuration class into a Properties class. List properties
     * are joined into a string using the delimiter of the configuration if it
     * extends AbstractConfiguration, and a comma otherwise.
     *
     * @param config Configuration object to convert
     * @return Properties created from the Configuration
     */
    public static Properties getProperties(Configuration config)
    {
        Properties props = new Properties();

        char delimiter = (config instanceof AbstractConfiguration)
            ? ((AbstractConfiguration) config).getListDelimiter() : ',';

        Iterator<String> keys = config.getKeys();
        while (keys.hasNext())
        {
            String key = keys.next();
            List<?> list = config.getList(key);

            // turn the list into a string
            props.setProperty(key, StringUtils.join(list.iterator(), delimiter));
        }

        return props;
    }

    /**
     * Convert a Configuration class into a Map class.
     *
     * @param config Configuration object to convert
     * @return Map created from the Configuration
     */
    public static Map<String, Object> getMap(Configuration config)
    {
        return new ConfigurationMap(config);
    }

}