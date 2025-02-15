package android.support.p000v4.app;

import android.arch.lifecycle.ViewModelStore;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.p000v4.util.C1625SimpleArrayMap;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.List;

/* renamed from: android.support.v4.app.FragmentController */
public class FragmentController {
    private final FragmentHostCallback<?> mHost;

    public static FragmentController createController(FragmentHostCallback<?> callbacks) {
        FragmentController fragmentController;
        new FragmentController(callbacks);
        return fragmentController;
    }

    private FragmentController(FragmentHostCallback<?> callbacks) {
        this.mHost = callbacks;
    }

    public FragmentManager getSupportFragmentManager() {
        return this.mHost.getFragmentManagerImpl();
    }

    @Deprecated
    public LoaderManager getSupportLoaderManager() {
        Throwable th;
        Throwable th2 = th;
        new UnsupportedOperationException("Loaders are managed separately from FragmentController, use LoaderManager.getInstance() to obtain a LoaderManager.");
        throw th2;
    }

    @Nullable
    public Fragment findFragmentByWho(String who) {
        return this.mHost.mFragmentManager.findFragmentByWho(who);
    }

    public int getActiveFragmentsCount() {
        return this.mHost.mFragmentManager.getActiveFragmentCount();
    }

    public List<Fragment> getActiveFragments(List<Fragment> list) {
        List<Fragment> list2 = list;
        return this.mHost.mFragmentManager.getActiveFragments();
    }

    public void attachHost(Fragment parent) {
        this.mHost.mFragmentManager.attachController(this.mHost, this.mHost, parent);
    }

    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        return this.mHost.mFragmentManager.onCreateView(parent, name, context, attrs);
    }

    public void noteStateNotSaved() {
        this.mHost.mFragmentManager.noteStateNotSaved();
    }

    public Parcelable saveAllState() {
        return this.mHost.mFragmentManager.saveAllState();
    }

    @Deprecated
    public void restoreAllState(Parcelable state, List<Fragment> nonConfigList) {
        FragmentManagerNonConfig fragmentManagerNonConfig;
        new FragmentManagerNonConfig(nonConfigList, (List<FragmentManagerNonConfig>) null, (List<ViewModelStore>) null);
        this.mHost.mFragmentManager.restoreAllState(state, fragmentManagerNonConfig);
    }

    public void restoreAllState(Parcelable state, FragmentManagerNonConfig nonConfig) {
        this.mHost.mFragmentManager.restoreAllState(state, nonConfig);
    }

    @Deprecated
    public List<Fragment> retainNonConfig() {
        FragmentManagerNonConfig nonconf = this.mHost.mFragmentManager.retainNonConfig();
        return nonconf != null ? nonconf.getFragments() : null;
    }

    public FragmentManagerNonConfig retainNestedNonConfig() {
        return this.mHost.mFragmentManager.retainNonConfig();
    }

    public void dispatchCreate() {
        this.mHost.mFragmentManager.dispatchCreate();
    }

    public void dispatchActivityCreated() {
        this.mHost.mFragmentManager.dispatchActivityCreated();
    }

    public void dispatchStart() {
        this.mHost.mFragmentManager.dispatchStart();
    }

    public void dispatchResume() {
        this.mHost.mFragmentManager.dispatchResume();
    }

    public void dispatchPause() {
        this.mHost.mFragmentManager.dispatchPause();
    }

    public void dispatchStop() {
        this.mHost.mFragmentManager.dispatchStop();
    }

    @Deprecated
    public void dispatchReallyStop() {
    }

    public void dispatchDestroyView() {
        this.mHost.mFragmentManager.dispatchDestroyView();
    }

    public void dispatchDestroy() {
        this.mHost.mFragmentManager.dispatchDestroy();
    }

    public void dispatchMultiWindowModeChanged(boolean isInMultiWindowMode) {
        this.mHost.mFragmentManager.dispatchMultiWindowModeChanged(isInMultiWindowMode);
    }

    public void dispatchPictureInPictureModeChanged(boolean isInPictureInPictureMode) {
        this.mHost.mFragmentManager.dispatchPictureInPictureModeChanged(isInPictureInPictureMode);
    }

    public void dispatchConfigurationChanged(Configuration newConfig) {
        this.mHost.mFragmentManager.dispatchConfigurationChanged(newConfig);
    }

    public void dispatchLowMemory() {
        this.mHost.mFragmentManager.dispatchLowMemory();
    }

    public boolean dispatchCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        return this.mHost.mFragmentManager.dispatchCreateOptionsMenu(menu, inflater);
    }

    public boolean dispatchPrepareOptionsMenu(Menu menu) {
        return this.mHost.mFragmentManager.dispatchPrepareOptionsMenu(menu);
    }

    public boolean dispatchOptionsItemSelected(MenuItem item) {
        return this.mHost.mFragmentManager.dispatchOptionsItemSelected(item);
    }

    public boolean dispatchContextItemSelected(MenuItem item) {
        return this.mHost.mFragmentManager.dispatchContextItemSelected(item);
    }

    public void dispatchOptionsMenuClosed(Menu menu) {
        this.mHost.mFragmentManager.dispatchOptionsMenuClosed(menu);
    }

    public boolean execPendingActions() {
        return this.mHost.mFragmentManager.execPendingActions();
    }

    @Deprecated
    public void doLoaderStart() {
    }

    @Deprecated
    public void doLoaderStop(boolean retain) {
    }

    @Deprecated
    public void doLoaderRetain() {
    }

    @Deprecated
    public void doLoaderDestroy() {
    }

    @Deprecated
    public void reportLoaderStart() {
    }

    @Deprecated
    public C1625SimpleArrayMap<String, LoaderManager> retainLoaderNonConfig() {
        return null;
    }

    @Deprecated
    public void restoreLoaderNonConfig(C1625SimpleArrayMap<String, LoaderManager> simpleArrayMap) {
    }

    @Deprecated
    public void dumpLoaders(String prefix, FileDescriptor fd, PrintWriter writer, String[] args) {
    }
}
