package com.example.ssa.ssa.domain.mapper;

import com.example.ssa.ssa.domain.model.Plan;
import com.example.ssa.ssa.domain.model.PlanDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Mapper
public interface PlanMapper {

    List<Plan> selectListOfDay(@Param("roomId") long roomId,
                               @Param("targetDate") LocalDate targetDate);

    void insert(@Param("roomId") long roomId,
                @Param("title") String title,
                @Param("startDate") LocalDate startDate,
                @Param("startTime") LocalTime startTime,
                @Param("endDate") LocalDate endDate,
                @Param("endTime") LocalTime endTime,
                @Param("memo") String memo,
                @Param("createdAccountId") long createdAccountId);

    boolean selectExistsById(@Param("planId") long planId);

    boolean selectExistsByAccountJoinRoom(@Param("planId") long planId, @Param("accountId") long accountId);

    PlanDetail selectDetailById(@Param("planId") long planId);
}
