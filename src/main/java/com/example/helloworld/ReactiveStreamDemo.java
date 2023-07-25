package com.example.helloworld;

import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;

public class ReactiveStreamDemo {
    public static void main(String[] args) {
//创建一个发布者
        SubmissionPublisher<String> publisher = new SubmissionPublisher<>();
//建立一个处理器
        ReactiveProcessor processor = new ReactiveProcessor();
//发布者与处理器建立关系  (subscribe订阅)
        publisher.subscribe(processor);
        //创建一个最终订阅者
        Flow.Subscriber<String> subscriber = new Flow.Subscriber<>() {
            Flow.Subscription subscription;//保存订阅关系
            @Override
            public void onSubscribe(Flow.Subscription subscription) {
                this.subscription = subscription;
                subscription.request(1);//第一次建立联系需要返回确认
                System.out.println("建立订阅关系");
            }
            @Override
            public void onNext(String item) {
                System.out.println("订阅者接收数据:"+ item);
                /*业务处理*/
                subscription.request(10);//背压10个订阅
            }
            @Override
            public void onError(Throwable throwable) {
                System.out.println("发生错误了~");
            }
            @Override
            public void onComplete() {
                System.out.println("接收完成~");
            }
        };
        //建立订阅者
        processor.subscribe(subscriber);
        //发送数据流
        for (int i = 0; i < 500; i++) {
            System.out.println("发布数据： "+i);
            publisher.submit("bell:"+i);
        }
        //异步进行需要开启线程
        try {
            Thread.currentThread().join(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        subscriber.onComplete();
    }
}
