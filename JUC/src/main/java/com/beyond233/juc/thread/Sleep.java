package com.beyond233.juc.thread;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * <p>��Ŀ�ĵ�: </p>
 *
 * @author beyond233
 * @version 1.0
 * @since 2020-06-09 10:57
 */
@Slf4j(topic = "sleep����")
public class Sleep {
    public static void main(String[] args) {
        Thread t1 = new Thread("t1"){
            @Override
            public void run() {
                log.debug("����˯��ǰ��"+this.getState().toString());
                //�߳�������ΪRUNNABLE״̬��sleep�����������ת��ΪTIMED_WAITING״̬
                try {
                    Thread.sleep(2000);
                    log.debug("˯�߽�����"+this.getState().toString());
                } catch (InterruptedException e) {
                    log.debug("˯�߱���ϣ�"+this.getState().toString());
                    e.printStackTrace();
                }
            }
        };

        t1.start();
        //�����߳�˯��1s��t1�̲߳ſ϶�����˯�ߣ����ܲ�׽����״̬
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //�߳�������ΪRUNNABLE״̬��sleep�����������ת��ΪTIMED_WAITING״̬
        log.debug("t1˯���У�" + t1.getState().toString());
        t1.interrupt();
        log.debug("t1����ϣ�" + t1.getState().toString());

    }
}
