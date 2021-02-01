package com.beyond233.juc.cas;

import com.beyond233.juc.util.Sleeper;

import java.util.concurrent.atomic.AtomicMarkableReference;

/**
 * AtomicMarkableReference
 *
 * @author beyond233
 * @date 2021/1/29 16:23
 */
public class AtomicMarkableAccount implements AtomicAccount<Integer> {
    private AtomicMarkableReference<Integer> balance = new AtomicMarkableReference<>(100, false);

    @Override
    public Integer balance() {
        return balance.getReference();
    }

    @Override
    public void withDraw(Integer amount) {
        if (balance.isMarked()) {
            System.out.println("已修改过了！");
        }
        int prev, next;
        do {
            prev = balance();
            next = prev - amount;
        } while (balance.compareAndSet(prev, next, false, true));
    }

    public static void main(String[] args) {
        AtomicMarkableAccount account = new AtomicMarkableAccount();
        new Thread(() -> account.withDraw(1)).start();
        new Thread(() -> account.withDraw(1)).start();
        new Thread(() -> account.withDraw(1)).start();

        Sleeper.sleep(1);
        System.out.println("balance=" + account.balance());
    }

}
