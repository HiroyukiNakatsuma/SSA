package com.example.ssa.ssa.service;

import com.example.ssa.ssa.domain.mapper.PlanMapper;
import com.example.ssa.ssa.domain.model.CalendarWithPlan;
import com.example.ssa.ssa.domain.model.Day;
import javassist.tools.web.BadHttpRequest;
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
        // ルームの存在チェック
        // ルームに参加しているかチェック
        if (!roomService.isValid(roomId) || !roomService.isJoined(accountId, roomId)) {
            throw new BadHttpRequest();
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
                            planMapper.selectListOfDay(roomId, targetDate)));
                    targetDate.plusDays(1);
                });
        return new CalendarWithPlan(now.getMonth(), days);
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
                       long accountId) {
        planMapper.insert(roomId, title, startDate, startTime, endDate, endTime, memo, accountId);
    }
}
