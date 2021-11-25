create table actions
(
    Action_Name       varchar(27)                                         not null,
    Responding_Action enum ('BAN', 'MUTE', 'KICK', 'WARN') default 'WARN' not null,
    Default_Duration  time                                                null,
    Usage_Permission  varchar(27)                                         null,
    Display_Name      text                                                null,
    constraint Actions_Action_Name_uindex
        unique (Action_Name),
    constraint Actions_Usage_Permission_uindex
        unique (Usage_Permission)
);

alter table actions
    add primary key (Action_Name);

