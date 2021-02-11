package org.locationtech.jts.p006io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StreamTokenizer;
import java.util.ArrayList;
import java.util.List;
import org.locationtech.jts.geom.Geometry;

/* renamed from: org.locationtech.jts.io.WKBHexFileReader */
public class WKBHexFileReader {
    private static final int MAX_LOOKAHEAD = 1000;
    private int count;
    private File file;
    private int limit;
    private int offset;
    private Reader reader;
    private WKBReader wkbReader;

    public WKBHexFileReader(File file2, WKBReader wkbReader2) {
        this.file = null;
        this.count = 0;
        this.limit = -1;
        this.offset = 0;
        this.file = file2;
        this.wkbReader = wkbReader2;
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public WKBHexFileReader(java.lang.String r9, org.locationtech.jts.p006io.WKBReader r10) {
        /*
            r8 = this;
            r0 = r8
            r1 = r9
            r2 = r10
            r3 = r0
            java.io.File r4 = new java.io.File
            r7 = r4
            r4 = r7
            r5 = r7
            r6 = r1
            r5.<init>(r6)
            r5 = r2
            r3.<init>((java.io.File) r4, (org.locationtech.jts.p006io.WKBReader) r5)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.locationtech.jts.p006io.WKBHexFileReader.<init>(java.lang.String, org.locationtech.jts.io.WKBReader):void");
    }

    public WKBHexFileReader(Reader reader2, WKBReader wkbReader2) {
        this.file = null;
        this.count = 0;
        this.limit = -1;
        this.offset = 0;
        this.reader = reader2;
        this.wkbReader = wkbReader2;
    }

    public void setLimit(int limit2) {
        int i = limit2;
        this.limit = i;
    }

    public void setOffset(int offset2) {
        int i = offset2;
        this.offset = i;
    }

    /* JADX INFO: finally extract failed */
    public List read() throws IOException, ParseException {
        BufferedReader bufferedReader;
        BufferedReader bufferedReader2;
        Reader reader2;
        if (this.file != null) {
            new FileReader(this.file);
            this.reader = reader2;
        }
        this.count = 0;
        try {
            BufferedReader bufferedReader3 = bufferedReader;
            new BufferedReader(this.reader);
            bufferedReader2 = bufferedReader3;
            List read = read(bufferedReader2);
            bufferedReader2.close();
            this.reader.close();
            return read;
        } catch (Throwable th) {
            Throwable th2 = th;
            this.reader.close();
            throw th2;
        }
    }

    private List read(BufferedReader bufferedReader) throws IOException, ParseException {
        List list;
        BufferedReader bufferedReader2 = bufferedReader;
        new ArrayList();
        List geoms = list;
        while (!isAtEndOfFile(bufferedReader2) && !isAtLimit(geoms)) {
            String line = bufferedReader2.readLine().trim();
            if (line.length() != 0) {
                Geometry g = this.wkbReader.read(WKBReader.hexToBytes(line));
                if (this.count >= this.offset) {
                    boolean add = geoms.add(g);
                }
                this.count++;
            }
        }
        return geoms;
    }

    private boolean isAtLimit(List list) {
        List geoms = list;
        if (this.limit < 0) {
            return false;
        }
        if (geoms.size() < this.limit) {
            return false;
        }
        return true;
    }

    private boolean isAtEndOfFile(BufferedReader bufferedReader) throws IOException {
        StreamTokenizer tokenizer;
        BufferedReader bufferedReader2 = bufferedReader;
        bufferedReader2.mark(1000);
        new StreamTokenizer(bufferedReader2);
        if (tokenizer.nextToken() == -1) {
            return true;
        }
        bufferedReader2.reset();
        return false;
    }
}
