
    create table gamePosition (
        id int4 not null,
        i int4 not null,
        j int4 not null,
        oPosition boolean not null,
        xPosition boolean not null,
        gameRefId_gameid int4,
        primary key (id)
    );

    create table gameRecord (
        gameid int4 not null,
        endTime timestamp,
        isAI boolean not null,
        isTie boolean not null,
        savedAt timestamp,
        startTime timestamp,
        loserName_id int4,
        player1_id int4,
        player2_id int4,
        winnerName_id int4,
        primary key (gameid)
    );

    create table gameUser (
        id int4 not null,
        email varchar(255),
        password varchar(255),
        username varchar(255),
        primary key (id)
    );

    create table users (
        id int4 not null,
        enabled boolean not null,
        password varchar(255),
        username varchar(255),
        primary key (id)
    );

    alter table gamePosition 
        add constraint FK_33il4tkr0d328uk36w6fs2d7k 
        foreign key (gameRefId_gameid) 
        references gameRecord;

    alter table gameRecord 
        add constraint FK_e4tjibriv7bu6nby77h8m8uo3 
        foreign key (loserName_id) 
        references gameUser;

    alter table gameRecord 
        add constraint FK_792skvofa5xhr0wv2hk5q88od 
        foreign key (player1_id) 
        references gameUser;

    alter table gameRecord 
        add constraint FK_nerbrl9ie7xpqct21ovpa247f 
        foreign key (player2_id) 
        references gameUser;

    alter table gameRecord 
        add constraint FK_6c2ag8vy16m5fsqnkyemyj0wl 
        foreign key (winnerName_id) 
        references gameUser;

    create sequence hibernate_sequence;