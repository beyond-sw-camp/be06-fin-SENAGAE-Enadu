package com.example.batch.common;

import org.springframework.batch.core.ChunkListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.stereotype.Component;

@Component
public class SleepChunkListener implements ChunkListener {

    @Override
    public void afterChunk(ChunkContext context) {
        System.out.println("청크 처리 완료. 2분 대기 중...");
        try {
            Thread.sleep(120000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
