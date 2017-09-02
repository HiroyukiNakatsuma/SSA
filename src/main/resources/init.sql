DROP TABLE IF EXISTS ssa.AccountJoinRoom;

DROP TABLE IF EXISTS ssa.Schedule;

DROP TABLE IF EXISTS ssa.OnetimeKey;

DROP TABLE IF EXISTS ssa.Plan;

DROP TABLE IF EXISTS ssa.Room;

DROP TABLE IF EXISTS ssa.Account;

CREATE TABLE ssa.Account (
    accountId BIGINT(20) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    mailAddress VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(60) NOT NULL,
    nickname VARCHAR(60) NULL DEFAULT NULL
) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin;


CREATE TABLE ssa.Room (
    roomId BIGINT(20) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    roomName VARCHAR(60) NULL,
    deleteFlag BIT(1) NOT NULL DEFAULT b'0',
    createdAt DATETIME NOT NULL,
    updatedAt DATETIME NOT NULL
) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin;


CREATE TABLE ssa.AccountJoinRoom (
    accountJoinRoomId BIGINT(20) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    accountId BIGINT(20) NOT NULL,
    roomId BIGINT(20) NOT NULL,
    roomRole TINYINT NOT NULL,
    FOREIGN KEY(accountId) REFERENCES Account(accountId),
    FOREIGN KEY(roomId) REFERENCES Room(roomId),
    UNIQUE `unique_index`(`accountId`, `roomId`)
) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin;


CREATE TABLE ssa.OnetimeKey (
    onetimeKey VARCHAR(32) NOT NULL PRIMARY KEY,
    roomId BIGINT(20) NOT NULL,
    createdAccountId BIGINT(20) NOT NULL,
    usedFlag BIT(1) NOT NULL DEFAULT b'0',
    createdAt DATETIME NOT NULL,
    updatedAt DATETIME NOT NULL,
    FOREIGN KEY(roomId) REFERENCES Room(roomId),
    FOREIGN KEY(createdAccountId) REFERENCES Account(accountId)
) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin;


CREATE TABLE ssa.Plan (
    planId BIGINT(20) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    roomId BIGINT(20) NULL,
    title VARCHAR(40) NOT NULL,
    startDate DATE NOT NULL,
    startTime TIME NULL DEFAULT NULL,
    endDate DATE NOT NULL,
    endTime TIME NULL DEFAULT NULL,
    memo VARCHAR(100) NULL DEFAULT NULL,
    createdAccountId BIGINT(20) NOT NULL,
    createdAt DATETIME NOT NULL,
    updatedAt DATETIME NOT NULL,
    FOREIGN KEY(roomId) REFERENCES Room(roomId),
    FOREIGN KEY(createdAccountId) REFERENCES Account(accountId)
) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin;


INSERT INTO ssa.Account (mailAddress, password, nickname) VALUES ("ssa@test.com", "$2a$10$orHIcqd9rp4MQFgAmSa23uROJIoGx2x0hWJZkazEj9Ii1BPY7KMba", "testAccount01");
