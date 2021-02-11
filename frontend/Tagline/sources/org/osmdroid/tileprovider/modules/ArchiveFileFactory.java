package org.osmdroid.tileprovider.modules;

import android.os.Build;
import android.util.Log;
import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.jose4j.jwx.HeaderParameterNames;
import org.osmdroid.api.IMapView;

public class ArchiveFileFactory {
    static Map<String, Class<? extends IArchiveFile>> extensionMap;

    public ArchiveFileFactory() {
    }

    static {
        Map<String, Class<? extends IArchiveFile>> map;
        new HashMap();
        extensionMap = map;
        Class<? extends IArchiveFile> put = extensionMap.put(HeaderParameterNames.ZIP, ZipFileArchive.class);
        if (Build.VERSION.SDK_INT >= 10) {
            Class<? extends IArchiveFile> put2 = extensionMap.put("sqlite", DatabaseFileArchive.class);
            Class<? extends IArchiveFile> put3 = extensionMap.put("mbtiles", MBTilesFileArchive.class);
            Class<? extends IArchiveFile> put4 = extensionMap.put("gemf", GEMFFileArchive.class);
        }
    }

    public static boolean isFileExtensionRegistered(String extension) {
        return extensionMap.containsKey(extension);
    }

    public static void registerArchiveFileProvider(Class<? extends IArchiveFile> provider, String fileExtension) {
        Class<? extends IArchiveFile> put = extensionMap.put(fileExtension, provider);
    }

    public static IArchiveFile getArchiveFile(File file) {
        StringBuilder sb;
        StringBuilder sb2;
        StringBuilder sb3;
        File pFile = file;
        String extension = pFile.getName();
        if (extension.contains(".")) {
            try {
                extension = extension.substring(extension.lastIndexOf(".") + 1);
            } catch (Exception e) {
                Exception exc = e;
            }
        }
        Class<? extends IArchiveFile> aClass = extensionMap.get(extension.toLowerCase());
        if (aClass != null) {
            try {
                IArchiveFile provider = (IArchiveFile) aClass.newInstance();
                provider.init(pFile);
                return provider;
            } catch (InstantiationException e2) {
                new StringBuilder();
                int e3 = Log.e(IMapView.LOGTAG, sb3.append("Error initializing archive file provider ").append(pFile.getAbsolutePath()).toString(), e2);
            } catch (IllegalAccessException e4) {
                new StringBuilder();
                int e5 = Log.e(IMapView.LOGTAG, sb2.append("Error initializing archive file provider ").append(pFile.getAbsolutePath()).toString(), e4);
            } catch (Exception e6) {
                new StringBuilder();
                int e7 = Log.e(IMapView.LOGTAG, sb.append("Error opening archive file ").append(pFile.getAbsolutePath()).toString(), e6);
            }
        }
        return null;
    }

    public static Set<String> getRegisteredExtensions() {
        Set<String> set;
        new HashSet();
        Set<String> r = set;
        boolean addAll = r.addAll(extensionMap.keySet());
        return r;
    }
}
