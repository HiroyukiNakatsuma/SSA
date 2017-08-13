package com.example.ssa.ssa.service;

import com.example.ssa.ssa.component.util.SsaUtil;
import com.example.ssa.ssa.component.util.UrlUtil;
import com.example.ssa.ssa.domain.Room;
import com.example.ssa.ssa.domain.RoomDetail;
import com.example.ssa.ssa.mapper.AccountJoinRoomMapper;
import com.example.ssa.ssa.mapper.OnetimeKeyRepository;
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
    @Autowired
    private OnetimeKeyRepository onetimeKeyRepository;

    /**
     * ユーザーのルーム一覧を取得
     */
    public List<Room> loadJoinList(Long accountId) {
        return roomMapper.loadList(accountId);
    }

    /**
     * ルーム作成
     */
    @Transactional
    public Long createRoom(String roomName, Long accountId) {
        Room room = Room.builder().roomName(roomName).build();
        roomMapper.insert(room);
        accountJoinRoomMapper.insert(accountId, room.getRoomId());
        return room.getRoomId();
    }

    /**
     * ルーム詳細取得
     */
    public RoomDetail loadDetail(Long roomId) {
        return roomMapper.selectDetailById(roomId);
    }

    /**
     * 有効なルームが存在するかチェック
     */
    public boolean isValid(long roomId) {
        return roomMapper.isValidById(roomId);
    }

    /**
     * ルーム招待URL発行
     */
    public String createInviteLink(long roomId, long accountId) {
        String randomKey = ssaUtil.createHashedString();
        // ランダムキー登録
        onetimeKeyRepository.insert(roomId, accountId, randomKey);

        return urlUtil.getBaseUrl() + "/room/inviteJoin/" + randomKey;
    }

}
