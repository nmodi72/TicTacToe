
create sequence hibernate_sequence minvalue 100;

create table gameUser (
        id integer primary key ,
        username varchar(255) unique not null,
        password varchar(255),
        email varchar(255)
        
    );
    

create table gameRecord (
        gameid integer primary key,
	isAI boolean not null,
	player1_id integer references gameUser(id),
	player2_id integer references gameUser(id),
	startTime timestamp,
    endTime timestamp,
    savedAt timestamp,
	isTie boolean,     
	winnerName_id integer references gameUser(id),
	loserName_id integer references gameUser(id) 
    
    );
    
       create table gamePosition (
        id integer primary key,
        i integer not null,
        j integer not null,
        xPosition boolean not null, 
        oPosition boolean not null, 
        gameRefId_gameid integer references gameRecord(gameid)
       
    );
   
    create table authorities (
    username    varchar(255) not null references gameUser(username),
    authority   varchar(255) default 'ROLE_USER'
	);
	
	
CREATE FUNCTION insertdata() returns trigger as $testref$
    BEGIN
    IF (TG_OP='INSERT') THEN
      INSERT INTO authorities (username) values (NEW.username);
      return NEW;
    END IF;
    END;
    $testref$ LANGUAGE plpgsql;
    CREATE TRIGGER testref AFTER INSERT ON gameUser
  	FOR EACH ROW 
  	EXECUTE PROCEDURE insertdata();
  	
   Insert into gameUser Values(1,'cysun','abcd','cysun@localhost.localdomain');


	Insert into gameRecord Values(1,true,1,null,'2014-04-23 13:56:19',null,null,false,null,null);

  

	Insert into gamePosition Values(1,1,1,true,false, 1);
	Insert into gamePosition Values(5,1,2,false,true, 1);
	
	