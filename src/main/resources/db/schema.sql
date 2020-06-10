/*@author Anna S. Almielka*/

CREATE TABLE IF NOT EXISTS board (
	id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
	title VARCHAR(60) NOT NULL UNIQUE,
	opened_count BIGINT DEFAULT '0' NOT NULL,
	closed_count BIGINT DEFAULT '0' NOT NULL,
	INDEX (title)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS category (
	id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
	title VARCHAR(60) NOT NULL UNIQUE,
	color VARCHAR(10),
	INDEX (title)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS task (
	id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
	title VARCHAR(30) NOT NULL UNIQUE,
	description VARCHAR(255)  DEFAULT NULL,
	amount INT DEFAULT NULL,
	completed TINYINT(1) DEFAULT '0' NOT NULL,
	created_date DATETIME NOT NULL,
	completed_date DATETIME,
	board_id BIGINT,
	category_id BIGINT,
	INDEX (title),
	INDEX (completed),
	INDEX (completed_date)
) ENGINE=InnoDB;

ALTER TABLE task ADD FOREIGN KEY(board_id) REFERENCES board(id) ON DELETE SET NULL ;
ALTER TABLE task ADD FOREIGN KEY(category_id) REFERENCES category(id) ON DELETE SET NULL;

CREATE TABLE IF NOT EXISTS stat (
	id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
	opened_total BIGINT DEFAULT '0' NOT NULL,
	closed_total BIGINT DEFAULT '0' NOT NULL
) ENGINE=InnoDB;

DELIMITER //

CREATE TRIGGER task_after_insert AFTER INSERT ON task FOR EACH ROW
BEGIN

/*Board not  empty and completed 0*/
IF ((ifnull(NEW.board_id, 0) > 0) AND NEW.completed = 0) THEN
	UPDATE board b SET b.opened_count = (b.opened_count + 1) WHERE b.id = NEW.board_id;
END IF;

/*Board not  empty and completed 1*/
IF( (ifnull(NEW.board_id, 0) > 0) AND NEW.completed = 1) THEN
	UPDATE board b SET b.closed_count = (b.closed_count + 1) WHERE b.id = NEW.board_id;
END IF;

/*general statistics*/
IF(NEW.completed = 0) THEN
	UPDATE stat s SET s.opened_total = ( s.opened_total + 1) WHERE s.id = 1;
ELSE 	
	UPDATE stat s SET s.closed_total = (s.closed_total + 1) WHERE s.id = 1;
END IF;

END //

CREATE TRIGGER task_after_delete AFTER DELETE ON task FOR EACH ROW
BEGIN

/*Board not  empty and completed 0*/
IF((ifnull(OLD.board_id, 0) > 0) AND OLD.completed = 0) THEN
	UPDATE board b SET b.opened_count = (b.opened_count - 1) WHERE b.id = OLD.board_id;
END IF;

/*Board not  empty and completed 1*/
IF((ifnull(OLD.board_id, 0) > 0) AND OLD.completed = 1) THEN
	UPDATE board b SET b.closed_count = (b.closed_count - 1) WHERE b.id = OLD.board_id;
END IF;

/*general statistics*/
IF(OLD.completed = 0) THEN
	UPDATE stat s SET s.opened_total = (s.opened_total - 1) WHERE s.id = 1;
ELSE 	
	UPDATE stat s SET s.closed_total = (s.closed_total - 1) WHERE s.id = 1;
END IF;

END //

CREATE TRIGGER task_after_update AFTER UPDATE ON task FOR EACH ROW
BEGIN

/*Don't change board**/
 IF (ifnull(OLD.board_id, 0) = ifnull(NEW.board_id, 0)) THEN
	/*Change completed to 0*/
	IF(OLD.completed != NEW.completed AND NEW.completed = 0) THEN
		/*update board**/
		UPDATE board b SET b.opened_count = (b.opened_count + 1), b.closed_count = (b.closed_count - 1) WHERE b.id = OLD.board_id;
		
		/*update general statistics*/
		UPDATE stat s SET s.opened_total = (s.opened_total + 1), s.closed_total = (s.closed_total - 1)  WHERE s.id = 1;
	END IF;
	
	/*Change completed to 1*/
	IF(OLD.completed != NEW.completed AND NEW.completed = 1) THEN
		/*update board**/
		UPDATE board b SET b.opened_count = (b.opened_count - 1), b.closed_count = (b.closed_count + 1) WHERE b.id = OLD.board_id;
		
		/*update general statistics*/
		UPDATE stat s SET s.opened_total = (s.opened_total - 1), s.closed_total = (s.closed_total + 1)  WHERE s.id = 1;
		
	END IF;
END IF;

/*Change board**/		
IF(ifnull(OLD.board_id, 0) != ifnull(NEW.board_id, 0)) THEN
	/*Change completed to 0*/
	IF(OLD.completed != NEW.completed AND NEW.completed = 0) THEN
	
		/*update old board**/
		UPDATE board b SET b.closed_count = (b.closed_count - 1) WHERE b.id = OLD.board_id;
		
		/*update new board**/
		UPDATE board b SET b.opened_count = (b.opened_count + 1) WHERE b.id = NEW.board_id;
		
		/*update general statistics*/
		UPDATE stat s SET s.opened_total = (s.opened_total + 1), s.closed_total = (s.closed_total - 1)  WHERE s.id = 1;
	END IF;	
	
	/*Change completed to 1*/
	 IF((OLD.completed != NEW.completed AND NEW.completed = 1) ) THEN
	
		/*update old board**/
		UPDATE board b SET b.opened_count = (b.opened_count - 1) WHERE b.id = OLD.board_id;
		
		/*update new board**/
		UPDATE board b SET b.closed_count = (b.closed_count + 1) WHERE b.id = NEW.board_id;
		
		/*update general statistics*/
	UPDATE stat s SET s.opened_total = (s.opened_total - 1), s.closed_total = (s.closed_total + 1)  WHERE s.id = 1;
	END IF;	

	/*Don't change completed, completed = 0*/
	IF(OLD.completed = NEW.completed AND NEW.completed = 0) THEN
	
		/*update old board**/
		UPDATE board b SET b.opened_count = (b.opened_count - 1) WHERE b.id = OLD.board_id;
		
		/*update new board**/
		UPDATE board b SET b.opened_count = (b.opened_count + 1) WHERE b.id = NEW.board_id;
	END IF;		
		
	/*Don't change completed, completed = 1*/
	IF(OLD.completed = NEW.completed AND NEW.completed = 1) THEN
	
		/*update old board**/
		UPDATE board b SET b.closed_count = (b.closed_count - 1) WHERE b.id = OLD.board_id;
		
		/*update new board**/
		UPDATE board b SET b.closed_count = (b.closed_count + 1) WHERE b.id = NEW.board_id;
		
	END IF;	
END IF;

END //

DELIMITER ;