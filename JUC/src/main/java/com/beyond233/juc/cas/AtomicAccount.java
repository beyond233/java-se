package com.beyond233.juc.cas;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * CAS乐观锁思想实现无锁并发、无阻塞并发
 *
 * @author beyond233
 * @version 1.0
 * @since 2020-06-19 16:15
 */
public interface AtomicAccount<T> {
    /**
     * 获取余额
     *
     * @return T 余额
     */
    T balance();

    /**
     * 扣减余额
     *
     * @param amount 需要减少的值
     */
    void withDraw(T amount);
}

class IntegerAtomicAccount implements AtomicAccount<Integer> {
    /**
     * 余额
     */
    private AtomicInteger balance;

    public IntegerAtomicAccount(Integer balance) {
        this.balance = new AtomicInteger(balance);
    }

    /**
     * 获取余额
     */
    @Override
    public Integer balance() {
        return balance.get();
    }

    /**
     * 修改余额
     *
     * @param amount 需要减少的值
     */
    @Override
    public void withDraw(Integer amount) {
        balance.updateAndGet(balance -> balance - amount);
        System.out.println(balance.get());
    }
}

class BigDecimalAtomicAccount implements AtomicAccount<BigDecimal> {
    private AtomicReference<BigDecimal> balance;

    BigDecimalAtomicAccount(BigDecimal balance) {
        this.balance = new AtomicReference<>(balance);
    }

    @Override
    public BigDecimal balance() {
        return balance.get();
    }

    @Override
    public void withDraw(BigDecimal amount) {
//        BigDecimal prev, next;
//        do {
//            prev = get();
//            next = prev.subtract(amount);
//        } while (!balance.compareAndSet(prev, next));
        balance.updateAndGet(balance -> balance().subtract(amount));
    }
}

class CasTest {
    public static void main(String[] args) {
//        Account<Integer> account = new IntegerAccountImpl(1000);
        AtomicAccount<BigDecimal> atomicAccount = new BigDecimalAtomicAccount(new BigDecimal(1000));

        ArrayList<Thread> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            list.add(new Thread(() -> atomicAccount.withDraw(new BigDecimal(10))));
        }
        //当前时间
        long start = System.nanoTime();
        list.forEach(Thread::start);
        list.forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        //结束时间
        long end = System.nanoTime();

        System.out.println("余额：" + atomicAccount.balance());
        System.out.println("耗时：" + (end - start) / 1000_000 + "ms");
    }
}
