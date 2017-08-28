package com.example.ssa.ssa.service;

import com.example.ssa.ssa.domain.mapper.PlanMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
public class PlanService {
    @Autowired
    private PlanMapper planMapper;

    /**
     * 予定作成
     */
    public void create(long roomId,
                       String title,
                       LocalDateTime startDateTime,
                       LocalDateTime endDateTime,
                       String memo,
                       long accountId) {
        planMapper.insert(roomId, title, startDateTime, endDateTime, memo, accountId);
    }
}
