package com.example.ssa.ssa.service;

import com.example.ssa.ssa.component.util.SsaUtil;
import com.example.ssa.ssa.component.util.UrlUtil;
import com.example.ssa.ssa.domain.Room;
import com.example.ssa.ssa.domain.RoomDetail;
import com.example.ssa.ssa.mapper.AccountJoinRoomMapper;
import com.example.ssa.ssa.mapper.RoomMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class RoomService {
    @Autowired
    private RoomMapper roomMapper;
    @Autowired
    private AccountJoinRoomMapper accountJoinRoomMapper;
    @Autowired
    private UrlUtil urlUtil;
    @Autowired
    private SsaUtil ssaUtil;

    public List<Room> loadList(Long accountId) {
        return roomMapper.loadList(accountId);
    }

    @Transactional
    public Long createRoom(String roomName, Long accountId) {
        Room room = Room.builder().roomName(roomName).build();
        roomMapper.insert(room);
        accountJoinRoomMapper.insert(accountId, room.getRoomId());
        return room.getRoomId();
    }

    public RoomDetail loadDetail(Long roomId) {
        return roomMapper.selectDetailById(roomId);
    }

    public boolean isValid(long roomId) {
        return roomMapper.isValidById(roomId);
    }

    public String createInviteLink() {
        return urlUtil.getBaseUrl() + "/room/inviteJoin/" + ssaUtil.createHashedString();
    }

}
