package com.example.ssa.ssa.service;

import com.example.ssa.ssa.domain.mapper.PlanMapper;
import com.example.ssa.ssa.domain.model.CalendarWithPlan;
import com.example.ssa.ssa.domain.model.Day;
import com.example.ssa.ssa.domain.model.Plan;
import com.example.ssa.ssa.domain.model.PlanDetail;
import com.example.ssa.ssa.exception.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Slf4j
@Service
public class PlanService {
    @Autowired
    private RoomService roomService;
    @Autowired
    private PlanMapper planMapper;

    /**
     * ルームの今月の予定をカレンダーとして取得
     */
    public CalendarWithPlan loadCalendarWithPlan(long roomId, long accountId) throws Exception {
        if (!roomService.isValid(roomId) || !roomService.isJoined(accountId, roomId)) {
            throw new BadRequestException();
        }

        LocalDate now = LocalDate.now();
        LocalDate firstDayOfMonth = now.with(TemporalAdjusters.firstDayOfMonth());
        LocalDate lastDayOfMonth = now.with(TemporalAdjusters.lastDayOfMonth());
        // 表示するカレンダーの最初と最後の日付を取得
        LocalDate firstDateForCalendar = firstDayOfMonth.getDayOfWeek() == DayOfWeek.MONDAY
                ? firstDayOfMonth
                : now.with(TemporalAdjusters.firstInMonth(DayOfWeek.MONDAY)).minusWeeks(1);
        LocalDate endDateForCalendar = lastDayOfMonth.getDayOfWeek() == DayOfWeek.SUNDAY
                ? lastDayOfMonth
                : now.with(TemporalAdjusters.lastInMonth(DayOfWeek.SUNDAY)).plusWeeks(1);
        // 日付ごとに予定を詰めていく
        List<Day> days = new ArrayList<>();
        Stream.iterate(firstDateForCalendar, targetDate -> targetDate.plusDays(1))
                .limit(ChronoUnit.DAYS.between(firstDateForCalendar, endDateForCalendar) + 1)
                .forEach(targetDate -> {
                    days.add(new Day(targetDate,
                            targetDate.isEqual(now),
                            loadListOfDate(roomId, targetDate)));
                    targetDate.plusDays(1);
                });
        return new CalendarWithPlan(now.getMonth(), days);
    }

    /**
     * 指定した日付の予定を取得
     */
    public List<Plan> loadListOfDate(Long roomId, LocalDate targetDate) {
        return planMapper.selectListOfDay(roomId, targetDate);
    }

    /**
     * 予定作成
     */
    @Transactional
    public void create(long roomId,
                       String title,
                       LocalDate startDate,
                       LocalTime startTime,
                       LocalDate endDate,
                       LocalTime endTime,
                       String memo,
                       long accountId) throws Exception {
        if (!roomService.isJoined(accountId, roomId)) {
            throw new BadRequestException();
        }
        planMapper.insert(roomId, title, startDate, startTime, endDate, endTime, memo, accountId);
    }

    /**
     * 予定が妥当かどうか
     * 予定が存在する、かつ、参照可能であれば、trueを返す
     *
     * @param planId    予定ID
     * @param accountId 会員ID
     * @return
     */
    public boolean isValid(long planId, long accountId) {
        return planMapper.selectExistsById(planId) && planMapper.selectExistsByAccountJoinRoom(planId, accountId);
    }

    /**
     * 予定の詳細情報を取得
     *
     * @param planId 予定ID
     * @return 予定詳細情報
     */
    public PlanDetail loadDetail(Long planId) {
        return planMapper.selectDetailById(planId);
    }
}
