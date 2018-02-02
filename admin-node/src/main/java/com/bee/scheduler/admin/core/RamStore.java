package com.bee.scheduler.admin.core;


import com.bee.scheduler.admin.model.Notification;
import com.bee.scheduler.core.job.JobComponent;

import java.util.*;

/**
 * @author weiwei 内存存储容器
 */
public class RamStore {
    // Job Map<jobClass，JobDefinition>
    public static Map<String, JobComponent> jobs = new HashMap<String, JobComponent>();
    // 通知
    public static List<Notification> notifications = Collections.synchronizedList(new ArrayList<Notification>());

    public static synchronized void addNotification(Notification n) {
        int maxSize = 5;
        if (notifications.size() == maxSize) {
            notifications.remove(0);
        }
        notifications.add(n);
    }
}
