create table punishments
(
    Punishment_UID varchar(36)                         not null,
    Subject_UID    varchar(36)                         not null,
    Issuer_UID     varchar(36)                         not null,
    Action_Type    varchar(27)                         not null,
    Date_Issued    timestamp default CURRENT_TIMESTAMP null,
    PunishReason   text                                null,
    Expire_Date    datetime                            null,
    constraint Punishments_Punishment_UID_uindex
        unique (Punishment_UID),
    constraint Action_FK
        foreign key (Action_Type) references actions (Action_Name),
    constraint Users_Issuer_FK
        foreign key (Issuer_UID) references users (Player_UID),
    constraint Users_Subject_FK
        foreign key (Subject_UID) references users (Player_UID)
            on update cascade on delete cascade
);

alter table punishments
    add primary key (Punishment_UID);

create definer = Remote@`%` trigger setExpire
    before insert
    on punishments
    for each row
    IF NEW.Expire_Date IS NULL THEN
        SET NEW.Expire_Date =
                ADDTIME(NOW(), (select (Default_Duration) from actions where Action_Name = NEW.Action_Type));
    END IF;

create definer = Remote@`%` trigger setPlayers
    before insert
    on punishments
    for each row
BEGIN
    IF NEW.Issuer_UID = NEW.Subject_UID THEN
        signal sqlstate '45000'
            SET MESSAGE_TEXT = 'Issuer_UID cannot have the same value as Subject_UID';
    end if;
END;

create definer = Remote@`%` trigger updatePlayers
    before update
    on punishments
    for each row
BEGIN
    IF NEW.Issuer_UID = NEW.Subject_UID THEN
        signal sqlstate '45000'
            SET MESSAGE_TEXT = 'Issuer_UID cannot have the same value as Subject_UID';
    end if;
END;

