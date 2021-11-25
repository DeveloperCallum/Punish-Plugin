create table reports
(
    Report_UID     varchar(36)          not null
        primary key,
    Reported_UID   varchar(36)          not null,
    Submitter_UID  varchar(36)          not null,
    Reason         text                 not null,
    hasBeenHandled tinyint(1) default 0 null
);

