package com.wavenger.mapdbtest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.mapdb.DB;
import org.mapdb.DBMaker;

import java.io.Serializable;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void test(boolean useTransactions, boolean useExpire) {

        DBMaker.Maker maker = DBMaker.tempFileDB();
        if (!useTransactions) {
            maker.transactionDisable();
        }

        DB db = maker.make();
        DB.HTreeMapMaker mapMaker = db.hashMapCreate("test");
        if (useExpire) {
            mapMaker.expireAfterWrite(1, TimeUnit.DAYS);
        }
        Map<String, Pojo> map = mapMaker.make();

        long start = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            map.put(UUID.randomUUID().toString(), new Pojo(UUID.randomUUID().toString(), UUID.randomUUID().toString()));
        }
        long end = System.currentTimeMillis();
        String report = "Took: " + (end - start) + " with useTransactions: " + useTransactions + " and useExpire: " + useExpire;
        System.out.println(report);
        db.commit();
        db.close();
    }

    public static void main(String[] args) {
        test(false, false);
        test(false, true);
        test(true, false);
        test(true, true);

    }

    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Pojo implements Serializable {
        String one;
        String two;
    }
}
