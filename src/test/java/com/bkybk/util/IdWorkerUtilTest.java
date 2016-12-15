package com.bkybk.util;

import java.util.HashSet;
import java.util.Set;

import com.hospital.util.IdWorkerUtil;

public class IdWorkerUtilTest {
 
    static class IdWorkThread implements Runnable {
        private Set<Long> set;
        private IdWorkerUtil IdWorkerUtil;
 
        public IdWorkThread(Set<Long> set, IdWorkerUtil IdWorkerUtil) {
            this.set = set;
            this.IdWorkerUtil = IdWorkerUtil;
        }
 
        @Override
        public void run() {
            while (true) {
                long id = IdWorkerUtil.nextId();
                if (!set.add(id)) {
                    System.out.println("duplicate:" + id);
                }
            }
        }
    }
 
    public static void main(String[] args) {
        Set<Long> set = new HashSet<Long>();
        final IdWorkerUtil IdWorkerUtil1 = new IdWorkerUtil(0, 0);
        final IdWorkerUtil IdWorkerUtil2 = new IdWorkerUtil(1, 0);
        Thread t1 = new Thread(new IdWorkThread(set, IdWorkerUtil1));
        Thread t2 = new Thread(new IdWorkThread(set, IdWorkerUtil2));
        t1.setDaemon(true);
        t2.setDaemon(true);
        t1.start();
        t2.start();
        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}