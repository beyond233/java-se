package com.beyond233.juc.cas;

import com.beyond233.juc.util.Sleeper;

import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * AtomicStampedReference解决ABA问题: 内置的stamp充当版本号作用，用来记录修改次数
 *
 * @author beyond233
 * @date 2021/1/29 15:30
 */
public class AtomicStampedAccount implements AtomicAccount<Integer> {
    private AtomicStampedReference<Integer> balance;

    AtomicStampedAccount(Integer balance) {
        this.balance = new AtomicStampedReference<>(balance, 0);
    }

    @Override
    public Integer balance() {
        return balance.getReference();
    }

    @Override
    public void withDraw(Integer amount) {
        Integer prev, next, prevStamp, nextStamp;
        do {
            prev = balance();
            next = prev - amount;
            prevStamp = balance.getStamp();
            nextStamp = prevStamp + 1;
        } while (!balance.compareAndSet(prev, next, prevStamp, nextStamp));

    }

    public void other() {
        new Thread(() -> withDraw(10)).start();
        new Thread(() -> withDraw(-10)).start();
        Sleeper.sleep(1);
        System.out.println("balance=" + balance() + " stamp=" + balance.getStamp());

    }


    public static void main(String[] args) {
        AtomicAccount<Integer> account = new AtomicStampedAccount(100);
        ((AtomicStampedAccount) account).other();
    }
}


