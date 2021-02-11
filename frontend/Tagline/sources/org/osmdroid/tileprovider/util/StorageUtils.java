package org.osmdroid.tileprovider.util;

import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import com.microsoft.appcenter.Constants;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.UUID;

public class StorageUtils {
    public static final String EXTERNAL_SD_CARD = "externalSdCard";
    public static final String SD_CARD = "sdCard";
    private static final String TAG = "StorageUtils";

    public StorageUtils() {
    }

    public static class StorageInfo {
        String displayName = "";
        public final int display_number;
        public long freeSpace = 0;
        public final boolean internal;
        public final String path;
        public boolean readonly;

        public StorageInfo(String str, boolean z, boolean z2, int i) {
            StringBuilder sb;
            StringBuilder sb2;
            File file;
            StringBuilder sb3;
            File file2;
            String path2 = str;
            boolean internal2 = z;
            boolean readonly2 = z2;
            int display_number2 = i;
            this.path = path2;
            this.internal = internal2;
            this.display_number = display_number2;
            if (Build.VERSION.SDK_INT >= 9) {
                new File(path2);
                this.freeSpace = file2.getFreeSpace();
            }
            if (!readonly2) {
                new StringBuilder();
                new File(sb3.append(path2).append(File.separator).append(UUID.randomUUID().toString()).toString());
                File f = file;
                try {
                    boolean createNewFile = f.createNewFile();
                    boolean delete = f.delete();
                    this.readonly = false;
                } catch (Throwable th) {
                    Throwable th2 = th;
                    this.readonly = true;
                }
            } else {
                this.readonly = readonly2;
            }
            new StringBuilder();
            StringBuilder res = sb;
            if (internal2) {
                StringBuilder append = res.append("Internal SD card");
            } else if (display_number2 > 1) {
                new StringBuilder();
                StringBuilder append2 = res.append(sb2.append("SD card ").append(display_number2).toString());
            } else {
                StringBuilder append3 = res.append("SD card");
            }
            if (readonly2) {
                StringBuilder append4 = res.append(" (Read only)");
            }
            this.displayName = res.toString();
        }

        public String getDisplayName() {
            return this.displayName;
        }

        public void setDisplayName(String val) {
            String str = val;
            this.displayName = str;
        }
    }

    public static List<StorageInfo> getStorageList() {
        List<StorageInfo> list;
        Object obj;
        HashSet hashSet;
        BufferedReader bufferedReader;
        Reader reader;
        Object obj2;
        StringTokenizer stringTokenizer;
        Object obj3;
        Object obj4;
        new ArrayList();
        List<StorageInfo> list2 = list;
        String def_path = "";
        boolean def_path_internal = false;
        String def_path_state = "";
        boolean def_path_readonly = true;
        boolean def_path_available = false;
        try {
            if (Environment.getExternalStorageDirectory() != null) {
                def_path = Environment.getExternalStorageDirectory().getPath();
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
        try {
            def_path_internal = Build.VERSION.SDK_INT >= 9 && !Environment.isExternalStorageRemovable();
        } catch (Throwable th2) {
            th2.printStackTrace();
        }
        try {
            def_path_state = Environment.getExternalStorageState();
        } catch (Throwable th3) {
            th3.printStackTrace();
        }
        try {
            def_path_available = def_path_state.equals("mounted") || def_path_state.equals("mounted_ro");
        } catch (Throwable th4) {
            th4.printStackTrace();
        }
        try {
            def_path_readonly = Environment.getExternalStorageState().equals("mounted_ro");
        } catch (Throwable th5) {
            th5.printStackTrace();
        }
        BufferedReader buf_reader = null;
        try {
            new HashSet();
            HashSet hashSet2 = hashSet;
            new FileReader("/proc/mounts");
            new BufferedReader(reader);
            buf_reader = bufferedReader;
            int cur_display_number = 1;
            int d = Log.d(TAG, "/proc/mounts");
            while (true) {
                String readLine = buf_reader.readLine();
                String line = readLine;
                if (readLine == null) {
                    break;
                }
                int d2 = Log.d(TAG, line);
                if (line.contains("vfat") || line.contains("/mnt")) {
                    new StringTokenizer(line, " ");
                    StringTokenizer tokens = stringTokenizer;
                    String nextToken = tokens.nextToken();
                    String mount_point = tokens.nextToken();
                    if (!hashSet2.contains(mount_point)) {
                        String unused = tokens.nextToken();
                        boolean readonly = Arrays.asList(tokens.nextToken().split(",")).contains("ro");
                        if (mount_point.equals(def_path)) {
                            boolean add = hashSet2.add(def_path);
                            new StorageInfo(def_path, def_path_internal, readonly, -1);
                            list2.add(0, obj4);
                        } else if (line.contains("/dev/block/vold") && !line.contains("/mnt/secure") && !line.contains("/mnt/asec") && !line.contains("/mnt/obb") && !line.contains("/dev/mapper") && !line.contains("tmpfs")) {
                            boolean add2 = hashSet2.add(mount_point);
                            int i = cur_display_number;
                            cur_display_number++;
                            new StorageInfo(mount_point, false, readonly, i);
                            boolean add3 = list2.add(obj3);
                        }
                    }
                }
            }
            if (!hashSet2.contains(def_path) && def_path_available && def_path.length() > 0) {
                new StorageInfo(def_path, def_path_internal, def_path_readonly, -1);
                list2.add(0, obj2);
            }
            if (buf_reader != null) {
                try {
                    buf_reader.close();
                } catch (IOException e) {
                    IOException iOException = e;
                }
            }
        } catch (FileNotFoundException e2) {
            e2.printStackTrace();
            if (buf_reader != null) {
                try {
                    buf_reader.close();
                } catch (IOException e3) {
                    IOException iOException2 = e3;
                }
            }
        } catch (IOException e4) {
            e4.printStackTrace();
            if (buf_reader != null) {
                try {
                    buf_reader.close();
                } catch (IOException e5) {
                    IOException ex = e5;
                }
            }
        } catch (Throwable th6) {
            Throwable th7 = th6;
            if (buf_reader != null) {
                try {
                    buf_reader.close();
                } catch (IOException e6) {
                    IOException iOException3 = e6;
                }
            }
            throw th7;
        }
        for (File next : getAllStorageLocationsRevised()) {
            boolean found = false;
            int i2 = 0;
            while (true) {
                if (i2 >= list2.size()) {
                    break;
                } else if (list2.get(i2).path.equals(next.getAbsolutePath())) {
                    found = true;
                    break;
                } else {
                    i2++;
                }
            }
            if (!found) {
                new StorageInfo(next.getAbsolutePath(), false, false, -1);
                boolean add4 = list2.add(obj);
            }
        }
        return list2;
    }

    public static File getStorage() {
        File file;
        File file2;
        StorageInfo ptr = null;
        List<StorageInfo> storageList = getStorageList();
        for (int i = 0; i < storageList.size(); i++) {
            StorageInfo storageInfo = storageList.get(i);
            if (!storageInfo.readonly) {
                new File(storageInfo.path);
                if (isWritable(file2)) {
                    if (ptr == null) {
                        ptr = storageInfo;
                    } else if (ptr.freeSpace < storageInfo.freeSpace) {
                        ptr = storageInfo;
                    }
                }
            }
        }
        if (ptr != null) {
            new File(ptr.path);
            return file;
        }
        try {
            return Environment.getExternalStorageDirectory();
        } catch (Exception e) {
            Exception exc = e;
            return null;
        }
    }

    public static File getStorage(Context context) {
        File file;
        File file2;
        File file3;
        Context ctx = context;
        StorageInfo ptr = null;
        List<StorageInfo> storageList = getStorageList();
        for (int i = 0; i < storageList.size(); i++) {
            StorageInfo storageInfo = storageList.get(i);
            if (!storageInfo.readonly) {
                new File(storageInfo.path);
                if (isWritable(file3)) {
                    if (ptr == null) {
                        ptr = storageInfo;
                    } else if (ptr.freeSpace < storageInfo.freeSpace) {
                        ptr = storageInfo;
                    }
                }
            }
        }
        if (ptr != null) {
            new File(ptr.path);
            return file2;
        }
        new File(ctx.getDatabasePath("temp.sqlite").getAbsolutePath().replace("temp.sqlite", ""));
        return file;
    }

    public static boolean isAvailable() {
        String state = Environment.getExternalStorageState();
        if ("mounted".equals(state) || "mounted_ro".equals(state)) {
            return true;
        }
        return false;
    }

    public static String getSdCardPath() {
        StringBuilder sb;
        new StringBuilder();
        return sb.append(Environment.getExternalStorageDirectory().getPath()).append("/").toString();
    }

    public static boolean isWritable() {
        if ("mounted".equals(Environment.getExternalStorageState())) {
            return true;
        }
        return false;
    }

    public static boolean isWritable(File file) {
        StringBuilder sb;
        File file2;
        StringBuilder sb2;
        FileOutputStream fileOutputStream;
        StringBuilder sb3;
        File path = file;
        try {
            new StringBuilder();
            new File(sb2.append(path.getAbsolutePath()).append(File.separator).append("osm.tmp").toString());
            File tmp = file2;
            new FileOutputStream(tmp);
            FileOutputStream fos = fileOutputStream;
            fos.write("hi".getBytes());
            fos.close();
            new StringBuilder();
            int i = Log.i(TAG, sb3.append(path.getAbsolutePath()).append(" is writable").toString());
            boolean delete = tmp.delete();
            return true;
        } catch (Throwable th) {
            Throwable th2 = th;
            new StringBuilder();
            int i2 = Log.i(TAG, sb.append(path.getAbsolutePath()).append(" is NOT writable").toString());
            return false;
        }
    }

    public static Map<String, File> getAllStorageLocations() {
        Map<String, File> map;
        List<String> list;
        List<String> list2;
        List<String> list3;
        File file;
        File file2;
        File file3;
        StringBuilder sb;
        StringBuilder sb2;
        StringBuilder sb3;
        File file4;
        Scanner scanner;
        File file5;
        Scanner scanner2;
        new HashMap(10);
        Map<String, File> map2 = map;
        new ArrayList<>(10);
        List<String> mMounts = list;
        new ArrayList<>(10);
        List<String> mVold = list2;
        boolean add = mMounts.add("/mnt/sdcard");
        boolean add2 = mVold.add("/mnt/sdcard");
        try {
            new File("/proc/mounts");
            File mountFile = file5;
            if (mountFile.exists()) {
                new Scanner(mountFile);
                Scanner scanner3 = scanner2;
                while (scanner3.hasNext()) {
                    String line = scanner3.nextLine();
                    if (line.startsWith("/dev/block/vold/")) {
                        String element = line.split(" ")[1];
                        if (!element.equals("/mnt/sdcard")) {
                            boolean add3 = mMounts.add(element);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            new File("/system/etc/vold.fstab");
            File voldFile = file4;
            if (voldFile.exists()) {
                new Scanner(voldFile);
                Scanner scanner4 = scanner;
                while (scanner4.hasNext()) {
                    String line2 = scanner4.nextLine();
                    if (line2.startsWith("dev_mount")) {
                        String element2 = line2.split(" ")[2];
                        if (element2.contains(Constants.COMMON_SCHEMA_PREFIX_SEPARATOR)) {
                            element2 = element2.substring(0, element2.indexOf(Constants.COMMON_SCHEMA_PREFIX_SEPARATOR));
                        }
                        if (!element2.equals("/mnt/sdcard")) {
                            boolean add4 = mVold.add(element2);
                        }
                    }
                }
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        int i = 0;
        while (i < mMounts.size()) {
            if (!mVold.contains(mMounts.get(i))) {
                int i2 = i;
                i--;
                String remove = mMounts.remove(i2);
            }
            i++;
        }
        mVold.clear();
        new ArrayList<>(10);
        List<String> mountHash = list3;
        for (String mount : mMounts) {
            new File(mount);
            File root = file3;
            if (root.exists() && root.isDirectory() && root.canWrite()) {
                File[] list4 = root.listFiles();
                String hash = "[";
                if (list4 != null) {
                    File[] fileArr = list4;
                    int length = fileArr.length;
                    for (int i3 = 0; i3 < length; i3++) {
                        File f = fileArr[i3];
                        new StringBuilder();
                        hash = sb3.append(hash).append(f.getName().hashCode()).append(Constants.COMMON_SCHEMA_PREFIX_SEPARATOR).append(f.length()).append(", ").toString();
                    }
                }
                new StringBuilder();
                String hash2 = sb.append(hash).append("]").toString();
                if (!mountHash.contains(hash2)) {
                    new StringBuilder();
                    String key = sb2.append("sdCard_").append(map2.size()).toString();
                    if (map2.size() == 0) {
                        key = SD_CARD;
                    } else if (map2.size() == 1) {
                        key = EXTERNAL_SD_CARD;
                    }
                    boolean add5 = mountHash.add(hash2);
                    File put = map2.put(key, root);
                }
            }
        }
        mMounts.clear();
        if (map2.isEmpty()) {
            File put2 = map2.put(SD_CARD, Environment.getExternalStorageDirectory());
        }
        if (!map2.containsValue(Environment.getExternalStorageDirectory())) {
            File put3 = map2.put(SD_CARD, Environment.getExternalStorageDirectory());
        }
        String primary_sd = System.getenv("EXTERNAL_STORAGE");
        if (primary_sd != null) {
            new File(primary_sd);
            File t = file2;
            if (t.exists() && !map2.containsValue(t)) {
                File put4 = map2.put(SD_CARD, t);
            }
        }
        String secondary_sd = System.getenv("SECONDARY_STORAGE");
        if (secondary_sd != null) {
            String[] split = secondary_sd.split(File.pathSeparator);
            for (int i4 = 0; i4 < split.length; i4++) {
                new File(split[i4]);
                File t2 = file;
                if (t2.exists() && !map2.containsValue(t2)) {
                    File put5 = map2.put(SD_CARD, t2);
                }
            }
        }
        return map2;
    }

    private static Set<File> getAllStorageLocationsRevised() {
        Set<File> set;
        List<String> list;
        List<String> list2;
        List<String> list3;
        File file;
        StringBuilder sb;
        StringBuilder sb2;
        StringBuilder sb3;
        File file2;
        Scanner scanner;
        File file3;
        Scanner scanner2;
        File file4;
        StringBuilder sb4;
        File file5;
        StringBuilder sb5;
        new HashSet();
        Set<File> map = set;
        String primary_sd = System.getenv("EXTERNAL_STORAGE");
        if (primary_sd != null) {
            new StringBuilder();
            new File(sb5.append(primary_sd).append(File.separator).toString());
            File t = file5;
            if (isWritable(t)) {
                boolean add = map.add(t);
            }
        }
        String secondary_sd = System.getenv("SECONDARY_STORAGE");
        if (secondary_sd != null) {
            String[] split = secondary_sd.split(File.pathSeparator);
            for (int i = 0; i < split.length; i++) {
                new StringBuilder();
                new File(sb4.append(split[i]).append(File.separator).toString());
                File t2 = file4;
                if (isWritable(t2)) {
                    boolean add2 = map.add(t2);
                }
            }
        }
        if (Environment.getExternalStorageDirectory() != null) {
            File t3 = Environment.getExternalStorageDirectory();
            if (isWritable(t3)) {
                boolean add3 = map.add(t3);
            }
        }
        new ArrayList<>(10);
        List<String> mMounts = list;
        new ArrayList<>(10);
        List<String> mVold = list2;
        boolean add4 = mMounts.add("/mnt/sdcard");
        boolean add5 = mVold.add("/mnt/sdcard");
        try {
            new File("/proc/mounts");
            File mountFile = file3;
            if (mountFile.exists()) {
                new Scanner(mountFile);
                Scanner scanner3 = scanner2;
                while (scanner3.hasNext()) {
                    String line = scanner3.nextLine();
                    if (line.startsWith("/dev/block/vold/")) {
                        String element = line.split(" ")[1];
                        if (!element.equals("/mnt/sdcard")) {
                            boolean add6 = mMounts.add(element);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            new File("/system/etc/vold.fstab");
            File voldFile = file2;
            if (voldFile.exists()) {
                new Scanner(voldFile);
                Scanner scanner4 = scanner;
                while (scanner4.hasNext()) {
                    String line2 = scanner4.nextLine();
                    if (line2.startsWith("dev_mount")) {
                        String element2 = line2.split(" ")[2];
                        if (element2.contains(Constants.COMMON_SCHEMA_PREFIX_SEPARATOR)) {
                            element2 = element2.substring(0, element2.indexOf(Constants.COMMON_SCHEMA_PREFIX_SEPARATOR));
                        }
                        if (!element2.equals("/mnt/sdcard")) {
                            boolean add7 = mVold.add(element2);
                        }
                    }
                }
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        int i2 = 0;
        while (i2 < mMounts.size()) {
            if (!mVold.contains(mMounts.get(i2))) {
                int i3 = i2;
                i2--;
                String remove = mMounts.remove(i3);
            }
            i2++;
        }
        mVold.clear();
        new ArrayList<>(10);
        List<String> mountHash = list3;
        for (String mount : mMounts) {
            new File(mount);
            File root = file;
            if (root.exists() && root.isDirectory() && root.canWrite()) {
                File[] list4 = root.listFiles();
                String hash = "[";
                if (list4 != null) {
                    File[] fileArr = list4;
                    int length = fileArr.length;
                    for (int i4 = 0; i4 < length; i4++) {
                        File f = fileArr[i4];
                        new StringBuilder();
                        hash = sb3.append(hash).append(f.getName().hashCode()).append(Constants.COMMON_SCHEMA_PREFIX_SEPARATOR).append(f.length()).append(", ").toString();
                    }
                }
                new StringBuilder();
                String hash2 = sb.append(hash).append("]").toString();
                if (!mountHash.contains(hash2)) {
                    new StringBuilder();
                    String sb6 = sb2.append("sdCard_").append(map.size()).toString();
                    if (map.size() == 0) {
                        String key = SD_CARD;
                    } else if (map.size() == 1) {
                        String key2 = EXTERNAL_SD_CARD;
                    }
                    boolean add8 = mountHash.add(hash2);
                    if (isWritable(root)) {
                        boolean add9 = map.add(root);
                    }
                }
            }
        }
        mMounts.clear();
        return map;
    }
}
