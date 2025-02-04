/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.dolphinscheduler.plugin.task.tis;

import org.apache.dolphinscheduler.spi.utils.StringUtils;

import java.util.ResourceBundle;

public class TISConfig {

    private static TISConfig cfg;

    private final String jobTriggerUrl;
    private final String jobTriggerPostBody;
    private final String jobStatusUrl;
    private final String jobStatusPostBody;

    private final String jobLogsFetchUrl;
    private final String jobCancelPostBody;

    public static synchronized TISConfig getInstance() {
        if (cfg == null) {
            cfg = new TISConfig();
        }
        return cfg;
    }

    private TISConfig() {
        ResourceBundle bundle = ResourceBundle.getBundle(TISConfig.class.getPackage().getName().replace(".", "/") + "/config");
        this.jobTriggerUrl = bundle.getString("job.trigger.url");
        this.jobStatusUrl = bundle.getString("job.status.url");
        this.jobTriggerPostBody = bundle.getString("job.trigger.post.body");
        this.jobStatusPostBody = bundle.getString("job.status.post.body");
        this.jobLogsFetchUrl = bundle.getString("job.logs.fetch.url");
        this.jobCancelPostBody = bundle.getString("job.cancel.post.body");
    }

    public String getJobCancelPostBody(int taskId) {
        return String.format(jobCancelPostBody, taskId);
    }

    public String getJobTriggerUrl(String tisHost) {
        checkTisHost(tisHost);
        return String.format(this.jobTriggerUrl, tisHost);
    }

    public String getJobTriggerPostBody() {
        return jobTriggerPostBody;
    }

    public String getJobStatusPostBody(int taskId) {
        return String.format(jobStatusPostBody, taskId);
    }

    public String getJobLogsFetchUrl(String tisHost, String jobName, int taskId) {
        checkTisHost(tisHost);
        return String.format(jobLogsFetchUrl, tisHost, jobName, taskId);
    }

    public String getJobStatusUrl(String tisHost) {
        checkTisHost(tisHost);
        return String.format(this.jobStatusUrl, tisHost);
    }

    private static void checkTisHost(String tisHost) {
        if (StringUtils.isBlank(tisHost)) {
            throw new IllegalArgumentException("param tisHost can not be null");
        }
    }
}
