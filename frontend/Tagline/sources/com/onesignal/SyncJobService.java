package com.onesignal;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.support.annotation.RequiresApi;
import com.onesignal.OneSignalSyncServiceUtils;

@RequiresApi(api = 21)
public class SyncJobService extends JobService {
    public SyncJobService() {
    }

    public boolean onStartJob(JobParameters jobParameters) {
        OneSignalSyncServiceUtils.SyncRunnable syncRunnable;
        new OneSignalSyncServiceUtils.LollipopSyncRunnable(this, jobParameters);
        OneSignalSyncServiceUtils.doBackgroundSync(this, syncRunnable);
        return true;
    }

    public boolean onStopJob(JobParameters jobParameters) {
        JobParameters jobParameters2 = jobParameters;
        return OneSignalSyncServiceUtils.stopSyncBgThread();
    }
}
