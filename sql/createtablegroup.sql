CREATE TABLE IF NOT EXISTS StudentGroup ( 
                    id BIGINT  GENERATED BY DEFAULT AS IDENTITY NOT NULL ,  
                    number INT NOT NULL,  
                    facultyname VARCHAR(45) NULL ,  
                    PRIMARY KEY(id) );
					
