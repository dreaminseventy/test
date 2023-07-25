package com.example.helloworld;

import java.util.concurrent.Flow;

    public class ReactiveProcessor implements Flow.Processor<String,String>{
        private Flow.Subscription subscription;
        private Flow.Subscriber<String> subscriber;
        @Override
        public void subscribe(Flow.Subscriber subscriber) {
            this.subscriber = subscriber;
            //在订阅是向上游请求数据
            subscriber.onSubscribe(subscription);
        }
        @Override
        public void onSubscribe(Flow.Subscription subscription) {
            this.subscription = subscription;
            // 请求订阅数据
            this.subscription.request(1);
        }
        @Override
        public void onNext(String item) {
            // 处理数据，并将结果发送给下游
            System.out.println("processor接收数据:"+item);
            subscriber.onNext(item);
            // 请求订阅下一个数据
            subscription.request(10);
        }
        @Override
        public void onError(Throwable throwable) {
            System.err.println("Error occurred: " + throwable.getMessage());
        }
        @Override
        public void onComplete() {
            System.out.println("Processing completed");
        }
    }

