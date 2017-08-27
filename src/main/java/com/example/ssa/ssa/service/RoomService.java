package com.example.ssa.ssa.service;

import com.example.ssa.ssa.component.util.SsaUtil;
import com.example.ssa.ssa.component.util.UrlUtil;
import com.example.ssa.ssa.domain.model.Room;
import com.example.ssa.ssa.domain.model.RoomDetail;
import com.example.ssa.ssa.enums.RoomRole;
import com.example.ssa.ssa.domain.mapper.AccountJoinRoomMapper;
import com.example.ssa.ssa.domain.mapper.OnetimeKeyMapper;
import com.example.ssa.ssa.domain.mapper.RoomMapper;
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
    private OnetimeKeyMapper onetimeKeyMapper;

    /**
     * ユーザーのルーム一覧を取得
     */
    public List<Room> loadJoinList(Long accountId) {
        return roomMapper.loadList(accountId);
    }

    /**
     * ルームを新規作成
     */
    @Transactional
    public Long createRoom(String roomName, Long accountId) {
        Room room = Room.builder().roomName(roomName).build();
        roomMapper.insert(room);
        accountJoinRoomMapper.insert(accountId, room.getRoomId(), RoomRole.OWNER.getCode());
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
     *
     * @param roomId ルームID
     * @return 有効なルームありならtrue、なければfalse
     */
    public boolean isValid(long roomId) {
        return roomMapper.isValidById(roomId);
    }

    /**
     * ルーム招待URL発行
     *
     * @param roomId    ルームID
     * @param accountId 会員ID
     * @return 招待URL
     */
    public String createInviteLink(long roomId, long accountId) {
        String randomKey = ssaUtil.createHashedString();
        // ランダムキー登録
        onetimeKeyMapper.insert(roomId, accountId, randomKey);
        return urlUtil.getBaseUrl() + "/room/inviteJoin/" + randomKey;
    }

    /**
     * 招待によるルーム参加処理
     */
    public Long inviteJoin(String onetimeKey, long accountId) {
        // ワンタイムキーが有効かチェック
        Long roomId = onetimeKeyMapper.isValid(onetimeKey);

        // ルーム参加処理
        if (roomId != null && !isJoined(accountId, roomId)) {
            accountJoinRoomMapper.insert(accountId, roomId, RoomRole.MEMBER.getCode());
            onetimeKeyMapper.updateUsedFlag(onetimeKey);
        }
        return roomId;
    }

    /**
     * ルームに参加しているかどうか
     */
    public boolean isJoined(long accountId, long roomId) {
        return accountJoinRoomMapper.isJoined(accountId, roomId);
    }

}
