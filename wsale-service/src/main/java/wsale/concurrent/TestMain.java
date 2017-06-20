package wsale.concurrent;

import jb.service.impl.CompletionFactory;

/**
 * Created by john on 16/8/7.
 */
public class TestMain {
    public static void main(String[] args) {
        final CompletionService completionService = CompletionFactory.initCompletion();
        for (int i = 0;i<10;i++){
            completionService.submit(new Task<String,String>("1"){
                @Override
                public String call() throws Exception {
                    System.out.println(Thread.currentThread()+"第一级");
                    return null;
                }
                protected void set(String r,String v){
                    completionService.submit(new Task<String,String>("2") {
                        @Override
                        public String call() throws Exception {
                            System.out.println(Thread.currentThread()+"第二级");
                            return null;
                        }
                    });
                }
            });
        }
        completionService.sync();
    }
}
