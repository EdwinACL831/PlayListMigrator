package com.example.playlistmigrator;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class BackgroundTask<Result> implements Callable<Result> {
    protected abstract void postExecute(Result result);

    protected void executeTask() {
        // create the thread and the executor in charge of running the task in the new thread
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        // first parameter of supplyAsync is the code to be run in the thread
        // second parameter is the thread which is handled by the executorService
        CompletableFuture<Result> ret = CompletableFuture.supplyAsync(() -> {
            try {
                return this.call();
            } catch (Exception e) {
                if(e.getMessage() != null){
                    Log.e(this.getClass().getSimpleName(), e.getMessage());
                }
                throw new RuntimeException(e);
            }
        }, executorService);
        // this is after starting the new thread to process the task
        CompletableFuture.allOf(ret).thenRun(() -> {
            postExecute(ret.join());
            executorService.shutdown();
        });
    }
}
