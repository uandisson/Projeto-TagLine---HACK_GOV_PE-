package org.osmdroid.tileprovider.modules;

import java.util.concurrent.ThreadFactory;

public class ConfigurablePriorityThreadFactory implements ThreadFactory {
    private final String mName;
    private final int mPriority;

    public ConfigurablePriorityThreadFactory(int pPriority, String pName) {
        this.mPriority = pPriority;
        this.mName = pName;
    }

    public Thread newThread(Runnable pRunnable) {
        Thread thread;
        new Thread(pRunnable);
        Thread thread2 = thread;
        thread2.setPriority(this.mPriority);
        if (this.mName != null) {
            thread2.setName(this.mName);
        }
        return thread2;
    }
}
