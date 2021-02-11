package org.osmdroid.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import org.jose4j.jwk.RsaJsonWebKey;

public class GEMFFile {
    private static final int FILE_COPY_BUFFER_SIZE = 1024;
    private static final long FILE_SIZE_LIMIT = 1073741824;
    private static final int TILE_SIZE = 256;
    private static final int U32_SIZE = 4;
    private static final int U64_SIZE = 8;
    private static final int VERSION = 4;
    private int mCurrentSource;
    private final List<String> mFileNames;
    private final List<Long> mFileSizes;
    private final List<RandomAccessFile> mFiles;
    private final String mLocation;
    private final List<GEMFRange> mRangeData;
    private boolean mSourceLimited;
    private final LinkedHashMap<Integer, String> mSources;

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public GEMFFile(File pLocation) throws FileNotFoundException, IOException {
        this(pLocation.getAbsolutePath());
    }

    public GEMFFile(String pLocation) throws FileNotFoundException, IOException {
        List<RandomAccessFile> list;
        List<String> list2;
        List<GEMFRange> list3;
        List<Long> list4;
        LinkedHashMap<Integer, String> linkedHashMap;
        new ArrayList();
        this.mFiles = list;
        new ArrayList();
        this.mFileNames = list2;
        new ArrayList();
        this.mRangeData = list3;
        new ArrayList();
        this.mFileSizes = list4;
        new LinkedHashMap<>();
        this.mSources = linkedHashMap;
        this.mSourceLimited = false;
        this.mCurrentSource = 0;
        this.mLocation = pLocation;
        openFiles();
        readHeader();
    }

    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public GEMFFile(java.lang.String r35, java.util.List<java.io.File> r36) throws java.io.FileNotFoundException, java.io.IOException {
        /*
            r34 = this;
            r2 = r34
            r3 = r35
            r4 = r36
            r28 = r2
            r28.<init>()
            r28 = r2
            java.util.ArrayList r29 = new java.util.ArrayList
            r33 = r29
            r29 = r33
            r30 = r33
            r30.<init>()
            r0 = r29
            r1 = r28
            r1.mFiles = r0
            r28 = r2
            java.util.ArrayList r29 = new java.util.ArrayList
            r33 = r29
            r29 = r33
            r30 = r33
            r30.<init>()
            r0 = r29
            r1 = r28
            r1.mFileNames = r0
            r28 = r2
            java.util.ArrayList r29 = new java.util.ArrayList
            r33 = r29
            r29 = r33
            r30 = r33
            r30.<init>()
            r0 = r29
            r1 = r28
            r1.mRangeData = r0
            r28 = r2
            java.util.ArrayList r29 = new java.util.ArrayList
            r33 = r29
            r29 = r33
            r30 = r33
            r30.<init>()
            r0 = r29
            r1 = r28
            r1.mFileSizes = r0
            r28 = r2
            java.util.LinkedHashMap r29 = new java.util.LinkedHashMap
            r33 = r29
            r29 = r33
            r30 = r33
            r30.<init>()
            r0 = r29
            r1 = r28
            r1.mSources = r0
            r28 = r2
            r29 = 0
            r0 = r29
            r1 = r28
            r1.mSourceLimited = r0
            r28 = r2
            r29 = 0
            r0 = r29
            r1 = r28
            r1.mCurrentSource = r0
            r28 = r2
            r29 = r3
            r0 = r29
            r1 = r28
            r1.mLocation = r0
            java.util.LinkedHashMap r28 = new java.util.LinkedHashMap
            r33 = r28
            r28 = r33
            r29 = r33
            r29.<init>()
            r5 = r28
            r28 = r4
            java.util.Iterator r28 = r28.iterator()
            r6 = r28
        L_0x009d:
            r28 = r6
            boolean r28 = r28.hasNext()
            if (r28 == 0) goto L_0x01f9
            r28 = r6
            java.lang.Object r28 = r28.next()
            java.io.File r28 = (java.io.File) r28
            r7 = r28
            java.util.LinkedHashMap r28 = new java.util.LinkedHashMap
            r33 = r28
            r28 = r33
            r29 = r33
            r29.<init>()
            r8 = r28
            r28 = r7
            java.io.File[] r28 = r28.listFiles()
            r9 = r28
            r28 = r9
            r0 = r28
            int r0 = r0.length
            r28 = r0
            r10 = r28
            r28 = 0
            r11 = r28
        L_0x00d1:
            r28 = r11
            r29 = r10
            r0 = r28
            r1 = r29
            if (r0 >= r1) goto L_0x01e9
            r28 = r9
            r29 = r11
            r28 = r28[r29]
            r12 = r28
            r28 = r12
            java.lang.String r28 = r28.getName()     // Catch:{ NumberFormatException -> 0x01a6 }
            int r28 = java.lang.Integer.parseInt(r28)     // Catch:{ NumberFormatException -> 0x01a6 }
            java.util.LinkedHashMap r28 = new java.util.LinkedHashMap
            r33 = r28
            r28 = r33
            r29 = r33
            r29.<init>()
            r13 = r28
            r28 = r12
            java.io.File[] r28 = r28.listFiles()
            r14 = r28
            r28 = r14
            r0 = r28
            int r0 = r0.length
            r28 = r0
            r15 = r28
            r28 = 0
            r16 = r28
        L_0x010f:
            r28 = r16
            r29 = r15
            r0 = r28
            r1 = r29
            if (r0 >= r1) goto L_0x01d2
            r28 = r14
            r29 = r16
            r28 = r28[r29]
            r17 = r28
            r28 = r17
            java.lang.String r28 = r28.getName()     // Catch:{ NumberFormatException -> 0x01ad }
            int r28 = java.lang.Integer.parseInt(r28)     // Catch:{ NumberFormatException -> 0x01ad }
            java.util.LinkedHashMap r28 = new java.util.LinkedHashMap
            r33 = r28
            r28 = r33
            r29 = r33
            r29.<init>()
            r18 = r28
            r28 = r17
            java.io.File[] r28 = r28.listFiles()
            r19 = r28
            r28 = r19
            r0 = r28
            int r0 = r0.length
            r28 = r0
            r20 = r28
            r28 = 0
            r21 = r28
        L_0x014d:
            r28 = r21
            r29 = r20
            r0 = r28
            r1 = r29
            if (r0 >= r1) goto L_0x01b8
            r28 = r19
            r29 = r21
            r28 = r28[r29]
            r22 = r28
            r28 = r22
            java.lang.String r28 = r28.getName()     // Catch:{ NumberFormatException -> 0x01b4 }
            r29 = 0
            r30 = r22
            java.lang.String r30 = r30.getName()     // Catch:{ NumberFormatException -> 0x01b4 }
            r31 = 46
            int r30 = r30.indexOf(r31)     // Catch:{ NumberFormatException -> 0x01b4 }
            java.lang.String r28 = r28.substring(r29, r30)     // Catch:{ NumberFormatException -> 0x01b4 }
            int r28 = java.lang.Integer.parseInt(r28)     // Catch:{ NumberFormatException -> 0x01b4 }
            r28 = r18
            r29 = r22
            java.lang.String r29 = r29.getName()
            r30 = 0
            r31 = r22
            java.lang.String r31 = r31.getName()
            r32 = 46
            int r31 = r31.indexOf(r32)
            java.lang.String r29 = r29.substring(r30, r31)
            int r29 = java.lang.Integer.parseInt(r29)
            java.lang.Integer r29 = java.lang.Integer.valueOf(r29)
            r30 = r22
            java.lang.Object r28 = r28.put(r29, r30)
        L_0x01a3:
            int r21 = r21 + 1
            goto L_0x014d
        L_0x01a6:
            r28 = move-exception
            r13 = r28
        L_0x01a9:
            int r11 = r11 + 1
            goto L_0x00d1
        L_0x01ad:
            r28 = move-exception
            r18 = r28
        L_0x01b0:
            int r16 = r16 + 1
            goto L_0x010f
        L_0x01b4:
            r28 = move-exception
            r23 = r28
            goto L_0x01a3
        L_0x01b8:
            r28 = r13
            java.lang.Integer r29 = new java.lang.Integer
            r33 = r29
            r29 = r33
            r30 = r33
            r31 = r17
            java.lang.String r31 = r31.getName()
            r30.<init>(r31)
            r30 = r18
            java.lang.Object r28 = r28.put(r29, r30)
            goto L_0x01b0
        L_0x01d2:
            r28 = r8
            r29 = r12
            java.lang.String r29 = r29.getName()
            int r29 = java.lang.Integer.parseInt(r29)
            java.lang.Integer r29 = java.lang.Integer.valueOf(r29)
            r30 = r13
            java.lang.Object r28 = r28.put(r29, r30)
            goto L_0x01a9
        L_0x01e9:
            r28 = r5
            r29 = r7
            java.lang.String r29 = r29.getName()
            r30 = r8
            java.lang.Object r28 = r28.put(r29, r30)
            goto L_0x009d
        L_0x01f9:
            java.util.LinkedHashMap r28 = new java.util.LinkedHashMap
            r33 = r28
            r28 = r33
            r29 = r33
            r29.<init>()
            r6 = r28
            java.util.LinkedHashMap r28 = new java.util.LinkedHashMap
            r33 = r28
            r28 = r33
            r29 = r33
            r29.<init>()
            r7 = r28
            r28 = 0
            r8 = r28
            r28 = r5
            java.util.Set r28 = r28.keySet()
            java.util.Iterator r28 = r28.iterator()
            r9 = r28
        L_0x0223:
            r28 = r9
            boolean r28 = r28.hasNext()
            if (r28 == 0) goto L_0x0262
            r28 = r9
            java.lang.Object r28 = r28.next()
            java.lang.String r28 = (java.lang.String) r28
            r10 = r28
            r28 = r6
            r29 = r10
            java.lang.Integer r30 = new java.lang.Integer
            r33 = r30
            r30 = r33
            r31 = r33
            r32 = r8
            r31.<init>(r32)
            java.lang.Object r28 = r28.put(r29, r30)
            r28 = r7
            java.lang.Integer r29 = new java.lang.Integer
            r33 = r29
            r29 = r33
            r30 = r33
            r31 = r8
            r30.<init>(r31)
            r30 = r10
            java.lang.Object r28 = r28.put(r29, r30)
            int r8 = r8 + 1
            goto L_0x0223
        L_0x0262:
            java.util.ArrayList r28 = new java.util.ArrayList
            r33 = r28
            r28 = r33
            r29 = r33
            r29.<init>()
            r9 = r28
            r28 = r5
            java.util.Set r28 = r28.keySet()
            java.util.Iterator r28 = r28.iterator()
            r10 = r28
        L_0x027b:
            r28 = r10
            boolean r28 = r28.hasNext()
            if (r28 == 0) goto L_0x05c3
            r28 = r10
            java.lang.Object r28 = r28.next()
            java.lang.String r28 = (java.lang.String) r28
            r11 = r28
            r28 = r5
            r29 = r11
            java.lang.Object r28 = r28.get(r29)
            java.util.LinkedHashMap r28 = (java.util.LinkedHashMap) r28
            java.util.Set r28 = r28.keySet()
            java.util.Iterator r28 = r28.iterator()
            r12 = r28
        L_0x02a1:
            r28 = r12
            boolean r28 = r28.hasNext()
            if (r28 == 0) goto L_0x05c1
            r28 = r12
            java.lang.Object r28 = r28.next()
            java.lang.Integer r28 = (java.lang.Integer) r28
            r13 = r28
            java.util.LinkedHashMap r28 = new java.util.LinkedHashMap
            r33 = r28
            r28 = r33
            r29 = r33
            r29.<init>()
            r14 = r28
            java.util.TreeSet r28 = new java.util.TreeSet
            r33 = r28
            r28 = r33
            r29 = r33
            r30 = r5
            r31 = r11
            java.lang.Object r30 = r30.get(r31)
            java.util.LinkedHashMap r30 = (java.util.LinkedHashMap) r30
            r31 = r13
            java.lang.Object r30 = r30.get(r31)
            java.util.LinkedHashMap r30 = (java.util.LinkedHashMap) r30
            java.util.Set r30 = r30.keySet()
            r29.<init>(r30)
            java.util.Iterator r28 = r28.iterator()
            r15 = r28
        L_0x02e7:
            r28 = r15
            boolean r28 = r28.hasNext()
            if (r28 == 0) goto L_0x0382
            r28 = r15
            java.lang.Object r28 = r28.next()
            java.lang.Integer r28 = (java.lang.Integer) r28
            r16 = r28
            java.util.ArrayList r28 = new java.util.ArrayList
            r33 = r28
            r28 = r33
            r29 = r33
            r29.<init>()
            r17 = r28
            r28 = r5
            r29 = r11
            java.lang.Object r28 = r28.get(r29)
            java.util.LinkedHashMap r28 = (java.util.LinkedHashMap) r28
            r29 = r13
            java.lang.Object r28 = r28.get(r29)
            java.util.LinkedHashMap r28 = (java.util.LinkedHashMap) r28
            r29 = r16
            java.lang.Object r28 = r28.get(r29)
            java.util.LinkedHashMap r28 = (java.util.LinkedHashMap) r28
            java.util.Set r28 = r28.keySet()
            java.util.Iterator r28 = r28.iterator()
            r18 = r28
        L_0x032a:
            r28 = r18
            boolean r28 = r28.hasNext()
            if (r28 == 0) goto L_0x0345
            r28 = r18
            java.lang.Object r28 = r28.next()
            java.lang.Integer r28 = (java.lang.Integer) r28
            r19 = r28
            r28 = r17
            r29 = r19
            boolean r28 = r28.add(r29)
            goto L_0x032a
        L_0x0345:
            r28 = r17
            int r28 = r28.size()
            if (r28 != 0) goto L_0x034e
            goto L_0x02e7
        L_0x034e:
            r28 = r17
            java.util.Collections.sort(r28)
            r28 = r14
            r29 = r17
            boolean r28 = r28.containsKey(r29)
            if (r28 != 0) goto L_0x0370
            r28 = r14
            r29 = r17
            java.util.ArrayList r30 = new java.util.ArrayList
            r33 = r30
            r30 = r33
            r31 = r33
            r31.<init>()
            java.lang.Object r28 = r28.put(r29, r30)
        L_0x0370:
            r28 = r14
            r29 = r17
            java.lang.Object r28 = r28.get(r29)
            java.util.List r28 = (java.util.List) r28
            r29 = r16
            boolean r28 = r28.add(r29)
            goto L_0x02e7
        L_0x0382:
            java.util.LinkedHashMap r28 = new java.util.LinkedHashMap
            r33 = r28
            r28 = r33
            r29 = r33
            r29.<init>()
            r15 = r28
            r28 = r14
            java.util.Set r28 = r28.keySet()
            java.util.Iterator r28 = r28.iterator()
            r16 = r28
        L_0x039b:
            r28 = r16
            boolean r28 = r28.hasNext()
            if (r28 == 0) goto L_0x0456
            r28 = r16
            java.lang.Object r28 = r28.next()
            java.util.List r28 = (java.util.List) r28
            r17 = r28
            java.util.TreeSet r28 = new java.util.TreeSet
            r33 = r28
            r28 = r33
            r29 = r33
            r30 = r14
            r31 = r17
            java.lang.Object r30 = r30.get(r31)
            java.util.Collection r30 = (java.util.Collection) r30
            r29.<init>(r30)
            r18 = r28
            java.util.ArrayList r28 = new java.util.ArrayList
            r33 = r28
            r28 = r33
            r29 = r33
            r29.<init>()
            r19 = r28
            r28 = r18
            java.lang.Object r28 = r28.first()
            java.lang.Integer r28 = (java.lang.Integer) r28
            int r28 = r28.intValue()
            r20 = r28
        L_0x03df:
            r28 = r20
            r29 = r18
            java.lang.Object r29 = r29.last()
            java.lang.Integer r29 = (java.lang.Integer) r29
            int r29 = r29.intValue()
            r30 = 1
            int r29 = r29 + 1
            r0 = r28
            r1 = r29
            if (r0 >= r1) goto L_0x0442
            r28 = r18
            java.lang.Integer r29 = new java.lang.Integer
            r33 = r29
            r29 = r33
            r30 = r33
            r31 = r20
            r30.<init>(r31)
            boolean r28 = r28.contains(r29)
            if (r28 == 0) goto L_0x0422
            r28 = r19
            java.lang.Integer r29 = new java.lang.Integer
            r33 = r29
            r29 = r33
            r30 = r33
            r31 = r20
            r30.<init>(r31)
            boolean r28 = r28.add(r29)
        L_0x041f:
            int r20 = r20 + 1
            goto L_0x03df
        L_0x0422:
            r28 = r19
            int r28 = r28.size()
            if (r28 <= 0) goto L_0x041f
            r28 = r15
            r29 = r17
            r30 = r19
            java.lang.Object r28 = r28.put(r29, r30)
            java.util.ArrayList r28 = new java.util.ArrayList
            r33 = r28
            r28 = r33
            r29 = r33
            r29.<init>()
            r19 = r28
            goto L_0x041f
        L_0x0442:
            r28 = r19
            int r28 = r28.size()
            if (r28 <= 0) goto L_0x0454
            r28 = r15
            r29 = r17
            r30 = r19
            java.lang.Object r28 = r28.put(r29, r30)
        L_0x0454:
            goto L_0x039b
        L_0x0456:
            r28 = r15
            java.util.Set r28 = r28.keySet()
            java.util.Iterator r28 = r28.iterator()
            r16 = r28
        L_0x0462:
            r28 = r16
            boolean r28 = r28.hasNext()
            if (r28 == 0) goto L_0x05bf
            r28 = r16
            java.lang.Object r28 = r28.next()
            java.util.List r28 = (java.util.List) r28
            r17 = r28
            java.util.TreeSet r28 = new java.util.TreeSet
            r33 = r28
            r28 = r33
            r29 = r33
            r30 = r17
            r29.<init>(r30)
            r18 = r28
            java.util.TreeSet r28 = new java.util.TreeSet
            r33 = r28
            r28 = r33
            r29 = r33
            r30 = r14
            r31 = r17
            java.lang.Object r30 = r30.get(r31)
            java.util.Collection r30 = (java.util.Collection) r30
            r29.<init>(r30)
            r19 = r28
            org.osmdroid.util.GEMFFile$GEMFRange r28 = new org.osmdroid.util.GEMFFile$GEMFRange
            r33 = r28
            r28 = r33
            r29 = r33
            r30 = r2
            r31 = 0
            r29.<init>(r30, r31)
            r20 = r28
            r28 = r20
            r29 = r13
            r0 = r29
            r1 = r28
            r1.zoom = r0
            r28 = r20
            r29 = r6
            r30 = r11
            java.lang.Object r29 = r29.get(r30)
            java.lang.Integer r29 = (java.lang.Integer) r29
            r0 = r29
            r1 = r28
            r1.sourceIndex = r0
            r28 = r20
            r29 = r19
            java.lang.Object r29 = r29.first()
            java.lang.Integer r29 = (java.lang.Integer) r29
            r0 = r29
            r1 = r28
            r1.xMin = r0
            r28 = r20
            r29 = r19
            java.lang.Object r29 = r29.last()
            java.lang.Integer r29 = (java.lang.Integer) r29
            r0 = r29
            r1 = r28
            r1.xMax = r0
            r28 = r18
            java.lang.Object r28 = r28.first()
            java.lang.Integer r28 = (java.lang.Integer) r28
            int r28 = r28.intValue()
            r21 = r28
        L_0x04f5:
            r28 = r21
            r29 = r18
            java.lang.Object r29 = r29.last()
            java.lang.Integer r29 = (java.lang.Integer) r29
            int r29 = r29.intValue()
            r30 = 1
            int r29 = r29 + 1
            r0 = r28
            r1 = r29
            if (r0 >= r1) goto L_0x05ab
            r28 = r18
            java.lang.Integer r29 = new java.lang.Integer
            r33 = r29
            r29 = r33
            r30 = r33
            r31 = r21
            r30.<init>(r31)
            boolean r28 = r28.contains(r29)
            if (r28 == 0) goto L_0x054b
            r28 = r20
            r0 = r28
            java.lang.Integer r0 = r0.yMin
            r28 = r0
            if (r28 != 0) goto L_0x053a
            r28 = r20
            r29 = r21
            java.lang.Integer r29 = java.lang.Integer.valueOf(r29)
            r0 = r29
            r1 = r28
            r1.yMin = r0
        L_0x053a:
            r28 = r20
            r29 = r21
            java.lang.Integer r29 = java.lang.Integer.valueOf(r29)
            r0 = r29
            r1 = r28
            r1.yMax = r0
        L_0x0548:
            int r21 = r21 + 1
            goto L_0x04f5
        L_0x054b:
            r28 = r20
            r0 = r28
            java.lang.Integer r0 = r0.yMin
            r28 = r0
            if (r28 == 0) goto L_0x0548
            r28 = r9
            r29 = r20
            boolean r28 = r28.add(r29)
            org.osmdroid.util.GEMFFile$GEMFRange r28 = new org.osmdroid.util.GEMFFile$GEMFRange
            r33 = r28
            r28 = r33
            r29 = r33
            r30 = r2
            r31 = 0
            r29.<init>(r30, r31)
            r20 = r28
            r28 = r20
            r29 = r13
            r0 = r29
            r1 = r28
            r1.zoom = r0
            r28 = r20
            r29 = r6
            r30 = r11
            java.lang.Object r29 = r29.get(r30)
            java.lang.Integer r29 = (java.lang.Integer) r29
            r0 = r29
            r1 = r28
            r1.sourceIndex = r0
            r28 = r20
            r29 = r19
            java.lang.Object r29 = r29.first()
            java.lang.Integer r29 = (java.lang.Integer) r29
            r0 = r29
            r1 = r28
            r1.xMin = r0
            r28 = r20
            r29 = r19
            java.lang.Object r29 = r29.last()
            java.lang.Integer r29 = (java.lang.Integer) r29
            r0 = r29
            r1 = r28
            r1.xMax = r0
            goto L_0x0548
        L_0x05ab:
            r28 = r20
            r0 = r28
            java.lang.Integer r0 = r0.yMin
            r28 = r0
            if (r28 == 0) goto L_0x05bd
            r28 = r9
            r29 = r20
            boolean r28 = r28.add(r29)
        L_0x05bd:
            goto L_0x0462
        L_0x05bf:
            goto L_0x02a1
        L_0x05c1:
            goto L_0x027b
        L_0x05c3:
            r28 = 0
            r10 = r28
            r28 = r6
            java.util.Set r28 = r28.keySet()
            java.util.Iterator r28 = r28.iterator()
            r11 = r28
        L_0x05d3:
            r28 = r11
            boolean r28 = r28.hasNext()
            if (r28 == 0) goto L_0x05f6
            r28 = r11
            java.lang.Object r28 = r28.next()
            java.lang.String r28 = (java.lang.String) r28
            r12 = r28
            r28 = r10
            r29 = 8
            r30 = r12
            int r30 = r30.length()
            int r29 = r29 + r30
            int r28 = r28 + r29
            r10 = r28
            goto L_0x05d3
        L_0x05f6:
            r28 = 12
            r29 = r10
            int r28 = r28 + r29
            r29 = r9
            int r29 = r29.size()
            r30 = 32
            int r29 = r29 * 32
            int r28 = r28 + r29
            r29 = 4
            int r28 = r28 + 4
            r0 = r28
            long r0 = (long) r0
            r28 = r0
            r11 = r28
            r28 = r9
            java.util.Iterator r28 = r28.iterator()
            r13 = r28
        L_0x061b:
            r28 = r13
            boolean r28 = r28.hasNext()
            if (r28 == 0) goto L_0x0696
            r28 = r13
            java.lang.Object r28 = r28.next()
            org.osmdroid.util.GEMFFile$GEMFRange r28 = (org.osmdroid.util.GEMFFile.GEMFRange) r28
            r14 = r28
            r28 = r14
            r29 = r11
            java.lang.Long r29 = java.lang.Long.valueOf(r29)
            r0 = r29
            r1 = r28
            r1.offset = r0
            r28 = r14
            r0 = r28
            java.lang.Integer r0 = r0.xMin
            r28 = r0
            int r28 = r28.intValue()
            r15 = r28
        L_0x0649:
            r28 = r15
            r29 = r14
            r0 = r29
            java.lang.Integer r0 = r0.xMax
            r29 = r0
            int r29 = r29.intValue()
            r30 = 1
            int r29 = r29 + 1
            r0 = r28
            r1 = r29
            if (r0 >= r1) goto L_0x0695
            r28 = r14
            r0 = r28
            java.lang.Integer r0 = r0.yMin
            r28 = r0
            int r28 = r28.intValue()
            r16 = r28
        L_0x066f:
            r28 = r16
            r29 = r14
            r0 = r29
            java.lang.Integer r0 = r0.yMax
            r29 = r0
            int r29 = r29.intValue()
            r30 = 1
            int r29 = r29 + 1
            r0 = r28
            r1 = r29
            if (r0 >= r1) goto L_0x0692
            r28 = r11
            r30 = 12
            long r28 = r28 + r30
            r11 = r28
            int r16 = r16 + 1
            goto L_0x066f
        L_0x0692:
            int r15 = r15 + 1
            goto L_0x0649
        L_0x0695:
            goto L_0x061b
        L_0x0696:
            r28 = r11
            r13 = r28
            java.io.RandomAccessFile r28 = new java.io.RandomAccessFile
            r33 = r28
            r28 = r33
            r29 = r33
            r30 = r3
            java.lang.String r31 = "rw"
            r29.<init>(r30, r31)
            r15 = r28
            r28 = r15
            r29 = 4
            r28.writeInt(r29)
            r28 = r15
            r29 = 256(0x100, float:3.59E-43)
            r28.writeInt(r29)
            r28 = r15
            r29 = r6
            int r29 = r29.size()
            r28.writeInt(r29)
            r28 = r6
            java.util.Set r28 = r28.keySet()
            java.util.Iterator r28 = r28.iterator()
            r16 = r28
        L_0x06d1:
            r28 = r16
            boolean r28 = r28.hasNext()
            if (r28 == 0) goto L_0x070d
            r28 = r16
            java.lang.Object r28 = r28.next()
            java.lang.String r28 = (java.lang.String) r28
            r17 = r28
            r28 = r15
            r29 = r6
            r30 = r17
            java.lang.Object r29 = r29.get(r30)
            java.lang.Integer r29 = (java.lang.Integer) r29
            int r29 = r29.intValue()
            r28.writeInt(r29)
            r28 = r15
            r29 = r17
            int r29 = r29.length()
            r28.writeInt(r29)
            r28 = r15
            r29 = r17
            byte[] r29 = r29.getBytes()
            r28.write(r29)
            goto L_0x06d1
        L_0x070d:
            r28 = r15
            r29 = r9
            int r29 = r29.size()
            r28.writeInt(r29)
            r28 = r9
            java.util.Iterator r28 = r28.iterator()
            r16 = r28
        L_0x0720:
            r28 = r16
            boolean r28 = r28.hasNext()
            if (r28 == 0) goto L_0x07ab
            r28 = r16
            java.lang.Object r28 = r28.next()
            org.osmdroid.util.GEMFFile$GEMFRange r28 = (org.osmdroid.util.GEMFFile.GEMFRange) r28
            r17 = r28
            r28 = r15
            r29 = r17
            r0 = r29
            java.lang.Integer r0 = r0.zoom
            r29 = r0
            int r29 = r29.intValue()
            r28.writeInt(r29)
            r28 = r15
            r29 = r17
            r0 = r29
            java.lang.Integer r0 = r0.xMin
            r29 = r0
            int r29 = r29.intValue()
            r28.writeInt(r29)
            r28 = r15
            r29 = r17
            r0 = r29
            java.lang.Integer r0 = r0.xMax
            r29 = r0
            int r29 = r29.intValue()
            r28.writeInt(r29)
            r28 = r15
            r29 = r17
            r0 = r29
            java.lang.Integer r0 = r0.yMin
            r29 = r0
            int r29 = r29.intValue()
            r28.writeInt(r29)
            r28 = r15
            r29 = r17
            r0 = r29
            java.lang.Integer r0 = r0.yMax
            r29 = r0
            int r29 = r29.intValue()
            r28.writeInt(r29)
            r28 = r15
            r29 = r17
            r0 = r29
            java.lang.Integer r0 = r0.sourceIndex
            r29 = r0
            int r29 = r29.intValue()
            r28.writeInt(r29)
            r28 = r15
            r29 = r17
            r0 = r29
            java.lang.Long r0 = r0.offset
            r29 = r0
            long r29 = r29.longValue()
            r28.writeLong(r29)
            goto L_0x0720
        L_0x07ab:
            r28 = r9
            java.util.Iterator r28 = r28.iterator()
            r16 = r28
        L_0x07b3:
            r28 = r16
            boolean r28 = r28.hasNext()
            if (r28 == 0) goto L_0x0877
            r28 = r16
            java.lang.Object r28 = r28.next()
            org.osmdroid.util.GEMFFile$GEMFRange r28 = (org.osmdroid.util.GEMFFile.GEMFRange) r28
            r17 = r28
            r28 = r17
            r0 = r28
            java.lang.Integer r0 = r0.xMin
            r28 = r0
            int r28 = r28.intValue()
            r18 = r28
        L_0x07d3:
            r28 = r18
            r29 = r17
            r0 = r29
            java.lang.Integer r0 = r0.xMax
            r29 = r0
            int r29 = r29.intValue()
            r30 = 1
            int r29 = r29 + 1
            r0 = r28
            r1 = r29
            if (r0 >= r1) goto L_0x0875
            r28 = r17
            r0 = r28
            java.lang.Integer r0 = r0.yMin
            r28 = r0
            int r28 = r28.intValue()
            r19 = r28
        L_0x07f9:
            r28 = r19
            r29 = r17
            r0 = r29
            java.lang.Integer r0 = r0.yMax
            r29 = r0
            int r29 = r29.intValue()
            r30 = 1
            int r29 = r29 + 1
            r0 = r28
            r1 = r29
            if (r0 >= r1) goto L_0x0871
            r28 = r15
            r29 = r11
            r28.writeLong(r29)
            r28 = r5
            r29 = r7
            r30 = r17
            r0 = r30
            java.lang.Integer r0 = r0.sourceIndex
            r30 = r0
            java.lang.Object r29 = r29.get(r30)
            java.lang.Object r28 = r28.get(r29)
            java.util.LinkedHashMap r28 = (java.util.LinkedHashMap) r28
            r29 = r17
            r0 = r29
            java.lang.Integer r0 = r0.zoom
            r29 = r0
            java.lang.Object r28 = r28.get(r29)
            java.util.LinkedHashMap r28 = (java.util.LinkedHashMap) r28
            r29 = r18
            java.lang.Integer r29 = java.lang.Integer.valueOf(r29)
            java.lang.Object r28 = r28.get(r29)
            java.util.LinkedHashMap r28 = (java.util.LinkedHashMap) r28
            r29 = r19
            java.lang.Integer r29 = java.lang.Integer.valueOf(r29)
            java.lang.Object r28 = r28.get(r29)
            java.io.File r28 = (java.io.File) r28
            long r28 = r28.length()
            r20 = r28
            r28 = r15
            r29 = r20
            r0 = r29
            int r0 = (int) r0
            r29 = r0
            r28.writeInt(r29)
            r28 = r11
            r30 = r20
            long r28 = r28 + r30
            r11 = r28
            int r19 = r19 + 1
            goto L_0x07f9
        L_0x0871:
            int r18 = r18 + 1
            goto L_0x07d3
        L_0x0875:
            goto L_0x07b3
        L_0x0877:
            r28 = 1024(0x400, float:1.435E-42)
            r0 = r28
            byte[] r0 = new byte[r0]
            r28 = r0
            r16 = r28
            r28 = r13
            r17 = r28
            r28 = 0
            r19 = r28
            r28 = r9
            java.util.Iterator r28 = r28.iterator()
            r20 = r28
        L_0x0891:
            r28 = r20
            boolean r28 = r28.hasNext()
            if (r28 == 0) goto L_0x0a0f
            r28 = r20
            java.lang.Object r28 = r28.next()
            org.osmdroid.util.GEMFFile$GEMFRange r28 = (org.osmdroid.util.GEMFFile.GEMFRange) r28
            r21 = r28
            r28 = r21
            r0 = r28
            java.lang.Integer r0 = r0.xMin
            r28 = r0
            int r28 = r28.intValue()
            r22 = r28
        L_0x08b1:
            r28 = r22
            r29 = r21
            r0 = r29
            java.lang.Integer r0 = r0.xMax
            r29 = r0
            int r29 = r29.intValue()
            r30 = 1
            int r29 = r29 + 1
            r0 = r28
            r1 = r29
            if (r0 >= r1) goto L_0x0a0d
            r28 = r21
            r0 = r28
            java.lang.Integer r0 = r0.yMin
            r28 = r0
            int r28 = r28.intValue()
            r23 = r28
        L_0x08d7:
            r28 = r23
            r29 = r21
            r0 = r29
            java.lang.Integer r0 = r0.yMax
            r29 = r0
            int r29 = r29.intValue()
            r30 = 1
            int r29 = r29 + 1
            r0 = r28
            r1 = r29
            if (r0 >= r1) goto L_0x0a09
            r28 = r5
            r29 = r7
            r30 = r21
            r0 = r30
            java.lang.Integer r0 = r0.sourceIndex
            r30 = r0
            java.lang.Object r29 = r29.get(r30)
            java.lang.Object r28 = r28.get(r29)
            java.util.LinkedHashMap r28 = (java.util.LinkedHashMap) r28
            r29 = r21
            r0 = r29
            java.lang.Integer r0 = r0.zoom
            r29 = r0
            java.lang.Object r28 = r28.get(r29)
            java.util.LinkedHashMap r28 = (java.util.LinkedHashMap) r28
            r29 = r22
            java.lang.Integer r29 = java.lang.Integer.valueOf(r29)
            java.lang.Object r28 = r28.get(r29)
            java.util.LinkedHashMap r28 = (java.util.LinkedHashMap) r28
            r29 = r23
            java.lang.Integer r29 = java.lang.Integer.valueOf(r29)
            java.lang.Object r28 = r28.get(r29)
            java.io.File r28 = (java.io.File) r28
            long r28 = r28.length()
            r24 = r28
            r28 = r17
            r30 = r24
            long r28 = r28 + r30
            r30 = 1073741824(0x40000000, double:5.304989477E-315)
            int r28 = (r28 > r30 ? 1 : (r28 == r30 ? 0 : -1))
            if (r28 <= 0) goto L_0x09f6
            r28 = r15
            r28.close()
            int r19 = r19 + 1
            java.io.RandomAccessFile r28 = new java.io.RandomAccessFile
            r33 = r28
            r28 = r33
            r29 = r33
            java.lang.StringBuilder r30 = new java.lang.StringBuilder
            r33 = r30
            r30 = r33
            r31 = r33
            r31.<init>()
            r31 = r3
            java.lang.StringBuilder r30 = r30.append(r31)
            java.lang.String r31 = "-"
            java.lang.StringBuilder r30 = r30.append(r31)
            r31 = r19
            java.lang.StringBuilder r30 = r30.append(r31)
            java.lang.String r30 = r30.toString()
            java.lang.String r31 = "rw"
            r29.<init>(r30, r31)
            r15 = r28
            r28 = 0
            r17 = r28
        L_0x097b:
            java.io.FileInputStream r28 = new java.io.FileInputStream
            r33 = r28
            r28 = r33
            r29 = r33
            r30 = r5
            r31 = r7
            r32 = r21
            r0 = r32
            java.lang.Integer r0 = r0.sourceIndex
            r32 = r0
            java.lang.Object r31 = r31.get(r32)
            java.lang.Object r30 = r30.get(r31)
            java.util.LinkedHashMap r30 = (java.util.LinkedHashMap) r30
            r31 = r21
            r0 = r31
            java.lang.Integer r0 = r0.zoom
            r31 = r0
            java.lang.Object r30 = r30.get(r31)
            java.util.LinkedHashMap r30 = (java.util.LinkedHashMap) r30
            r31 = r22
            java.lang.Integer r31 = java.lang.Integer.valueOf(r31)
            java.lang.Object r30 = r30.get(r31)
            java.util.LinkedHashMap r30 = (java.util.LinkedHashMap) r30
            r31 = r23
            java.lang.Integer r31 = java.lang.Integer.valueOf(r31)
            java.lang.Object r30 = r30.get(r31)
            java.io.File r30 = (java.io.File) r30
            r29.<init>(r30)
            r26 = r28
            r28 = r26
            r29 = r16
            r30 = 0
            r31 = 1024(0x400, float:1.435E-42)
            int r28 = r28.read(r29, r30, r31)
            r27 = r28
        L_0x09d2:
            r28 = r27
            r29 = -1
            r0 = r28
            r1 = r29
            if (r0 == r1) goto L_0x0a00
            r28 = r15
            r29 = r16
            r30 = 0
            r31 = r27
            r28.write(r29, r30, r31)
            r28 = r26
            r29 = r16
            r30 = 0
            r31 = 1024(0x400, float:1.435E-42)
            int r28 = r28.read(r29, r30, r31)
            r27 = r28
            goto L_0x09d2
        L_0x09f6:
            r28 = r17
            r30 = r24
            long r28 = r28 + r30
            r17 = r28
            goto L_0x097b
        L_0x0a00:
            r28 = r26
            r28.close()
            int r23 = r23 + 1
            goto L_0x08d7
        L_0x0a09:
            int r22 = r22 + 1
            goto L_0x08b1
        L_0x0a0d:
            goto L_0x0891
        L_0x0a0f:
            r28 = r15
            r28.close()
            r28 = r2
            r28.openFiles()
            r28 = r2
            r28.readHeader()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.osmdroid.util.GEMFFile.<init>(java.lang.String, java.util.List):void");
    }

    public void close() throws IOException {
        for (RandomAccessFile file : this.mFiles) {
            file.close();
        }
    }

    private void openFiles() throws FileNotFoundException {
        File file;
        Object obj;
        File file2;
        StringBuilder sb;
        Object obj2;
        new File(this.mLocation);
        File base = file;
        new RandomAccessFile(base, RsaJsonWebKey.PRIME_FACTOR_OTHER_MEMBER_NAME);
        boolean add = this.mFiles.add(obj);
        boolean add2 = this.mFileNames.add(base.getPath());
        int i = 0;
        while (true) {
            i++;
            new StringBuilder();
            new File(sb.append(this.mLocation).append("-").append(i).toString());
            File nextFile = file2;
            if (nextFile.exists()) {
                new RandomAccessFile(nextFile, RsaJsonWebKey.PRIME_FACTOR_OTHER_MEMBER_NAME);
                boolean add3 = this.mFiles.add(obj2);
                boolean add4 = this.mFileNames.add(nextFile.getPath());
            } else {
                return;
            }
        }
    }

    private void readHeader() throws IOException {
        GEMFRange gEMFRange;
        Object obj;
        Object obj2;
        Throwable th;
        StringBuilder sb;
        Throwable th2;
        StringBuilder sb2;
        RandomAccessFile baseFile = this.mFiles.get(0);
        for (RandomAccessFile file : this.mFiles) {
            boolean add = this.mFileSizes.add(Long.valueOf(file.length()));
        }
        int version = baseFile.readInt();
        if (version != 4) {
            Throwable th3 = th2;
            new StringBuilder();
            new IOException(sb2.append("Bad file version: ").append(version).toString());
            throw th3;
        }
        int tile_size = baseFile.readInt();
        if (tile_size != 256) {
            Throwable th4 = th;
            new StringBuilder();
            new IOException(sb.append("Bad tile size: ").append(tile_size).toString());
            throw th4;
        }
        int sourceCount = baseFile.readInt();
        for (int i = 0; i < sourceCount; i++) {
            int sourceIndex = baseFile.readInt();
            int sourceNameLength = baseFile.readInt();
            byte[] nameData = new byte[sourceNameLength];
            int read = baseFile.read(nameData, 0, sourceNameLength);
            new String(nameData);
            new Integer(sourceIndex);
            Object put = this.mSources.put(obj2, obj);
        }
        int num_ranges = baseFile.readInt();
        for (int i2 = 0; i2 < num_ranges; i2++) {
            new GEMFRange(this, (C15941) null);
            GEMFRange rs = gEMFRange;
            rs.zoom = Integer.valueOf(baseFile.readInt());
            rs.xMin = Integer.valueOf(baseFile.readInt());
            rs.xMax = Integer.valueOf(baseFile.readInt());
            rs.yMin = Integer.valueOf(baseFile.readInt());
            rs.yMax = Integer.valueOf(baseFile.readInt());
            rs.sourceIndex = Integer.valueOf(baseFile.readInt());
            rs.offset = Long.valueOf(baseFile.readLong());
            boolean add2 = this.mRangeData.add(rs);
        }
    }

    public String getName() {
        return this.mLocation;
    }

    public LinkedHashMap<Integer, String> getSources() {
        return this.mSources;
    }

    public void selectSource(int i) {
        Object obj;
        int pSource = i;
        new Integer(pSource);
        if (this.mSources.containsKey(obj)) {
            this.mSourceLimited = true;
            this.mCurrentSource = pSource;
        }
    }

    public void acceptAnySource() {
        this.mSourceLimited = false;
    }

    public Set<Integer> getZoomLevels() {
        Set<Integer> set;
        new TreeSet();
        Set<Integer> zoomLevels = set;
        for (GEMFRange rs : this.mRangeData) {
            boolean add = zoomLevels.add(rs.zoom);
        }
        return zoomLevels;
    }

    public InputStream getInputStream(int i, int i2, int i3) {
        GEMFInputStream gEMFInputStream;
        ByteArrayOutputStream byteArrayOutputStream;
        InputStream inputStream;
        GEMFRange rs;
        int pX = i;
        int pY = i2;
        int pZ = i3;
        GEMFRange range = null;
        Iterator<GEMFRange> it = this.mRangeData.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            rs = it.next();
            if (pZ == rs.zoom.intValue() && pX >= rs.xMin.intValue() && pX <= rs.xMax.intValue() && pY >= rs.yMin.intValue() && pY <= rs.yMax.intValue()) {
                if (!this.mSourceLimited || rs.sourceIndex.intValue() == this.mCurrentSource) {
                    range = rs;
                }
            }
        }
        range = rs;
        if (range == null) {
            return null;
        }
        try {
            int numY = (range.yMax.intValue() + 1) - range.yMin.intValue();
            long offset = (((long) (((pX - range.xMin.intValue()) * numY) + (pY - range.yMin.intValue()))) * 12) + range.offset.longValue();
            RandomAccessFile baseFile = this.mFiles.get(0);
            baseFile.seek(offset);
            long dataOffset = baseFile.readLong();
            int dataLength = baseFile.readInt();
            RandomAccessFile pDataFile = this.mFiles.get(0);
            int index = 0;
            if (dataOffset > this.mFileSizes.get(0).longValue()) {
                int fileListCount = this.mFileSizes.size();
                while (index < fileListCount - 1) {
                    if (dataOffset <= this.mFileSizes.get(index).longValue()) {
                        break;
                    }
                    dataOffset -= this.mFileSizes.get(index).longValue();
                    index++;
                }
                pDataFile = this.mFiles.get(index);
            }
            pDataFile.seek(dataOffset);
            new GEMFInputStream(this, this.mFileNames.get(index), dataOffset, dataLength);
            GEMFInputStream stream = gEMFInputStream;
            new ByteArrayOutputStream();
            ByteArrayOutputStream byteBuffer = byteArrayOutputStream;
            byte[] buffer = new byte[1024];
            while (stream.available() > 0) {
                int len = stream.read(buffer);
                if (len > 0) {
                    byteBuffer.write(buffer, 0, len);
                }
            }
            InputStream inputStream2 = inputStream;
            new ByteArrayInputStream(byteBuffer.toByteArray());
            return inputStream2;
        } catch (IOException e) {
            IOException iOException = e;
            return null;
        }
    }

    private class GEMFRange {
        Long offset;
        Integer sourceIndex;
        final /* synthetic */ GEMFFile this$0;
        Integer xMax;
        Integer xMin;
        Integer yMax;
        Integer yMin;
        Integer zoom;

        private GEMFRange(GEMFFile gEMFFile) {
            this.this$0 = gEMFFile;
        }

        /* JADX INFO: this call moved to the top of the method (can break code semantics) */
        /* synthetic */ GEMFRange(GEMFFile x0, C15941 r7) {
            this(x0);
            C15941 r2 = r7;
        }

        public String toString() {
            Object[] objArr = new Object[7];
            objArr[0] = this.sourceIndex;
            Object[] objArr2 = objArr;
            objArr2[1] = this.zoom;
            Object[] objArr3 = objArr2;
            objArr3[2] = this.xMin;
            Object[] objArr4 = objArr3;
            objArr4[3] = this.xMax;
            Object[] objArr5 = objArr4;
            objArr5[4] = this.yMin;
            Object[] objArr6 = objArr5;
            objArr6[5] = this.yMax;
            Object[] objArr7 = objArr6;
            objArr7[6] = this.offset;
            return String.format("GEMF Range: source=%d, zoom=%d, x=%d-%d, y=%d-%d, offset=0x%08X", objArr7);
        }
    }

    class GEMFInputStream extends InputStream {
        RandomAccessFile raf;
        int remainingBytes;
        final /* synthetic */ GEMFFile this$0;

        GEMFInputStream(GEMFFile this$02, String filePath, long offset, int length) throws IOException {
            RandomAccessFile randomAccessFile;
            this.this$0 = this$02;
            new RandomAccessFile(filePath, RsaJsonWebKey.PRIME_FACTOR_OTHER_MEMBER_NAME);
            this.raf = randomAccessFile;
            this.raf.seek(offset);
            this.remainingBytes = length;
        }

        public int available() {
            return this.remainingBytes;
        }

        public void close() throws IOException {
            this.raf.close();
        }

        public boolean markSupported() {
            return false;
        }

        public int read(byte[] buffer, int offset, int i) throws IOException {
            int length = i;
            int read = this.raf.read(buffer, offset, length > this.remainingBytes ? this.remainingBytes : length);
            this.remainingBytes -= read;
            return read;
        }

        public int read() throws IOException {
            Throwable th;
            if (this.remainingBytes > 0) {
                this.remainingBytes--;
                return this.raf.read();
            }
            Throwable th2 = th;
            new IOException("End of stream");
            throw th2;
        }

        public long skip(long j) {
            long j2 = j;
            return 0;
        }
    }
}
