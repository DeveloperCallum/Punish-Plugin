create table users
(
    Player_UID  varchar(36)                         not null,
    Player_Name varchar(16)                         not null,
    Date_Joined timestamp default CURRENT_TIMESTAMP null,
    Permissions text                                null,
    constraint Users_Player_UID_uindex
        unique (Player_UID)
);

alter table users
    add primary key (Player_UID);

