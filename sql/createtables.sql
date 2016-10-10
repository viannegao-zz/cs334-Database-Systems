CREATE TABLE enroll
	(sid int,
	 grade numeric,
	 dname varchar(50),
	 cno numeric(3,0),
	 sectno numeric(1,0),
	 primary key (sid, dname, cno, sectno))
	 
CREATE TABLE student
	(sid int,
	 sname varchar(50),
	 sex char(1),
	 age int,
	 year int,
	 gpa numeric,
	 primary key (sid))

#2
SELECT sname
FROM student
WHERE gpa = (SELECT MIN (gpa) from student);
	 
CREATE TABLE dept
	(dname varchar(50),
	 numphds int,
	 primary key (dname))
	 
CREATE TABLE prof
	(pname varchar(50),
	 dname varchar(50),
	 primary key (pname))
	 
＃1
SELECT pname
FROM prof, dept
WHERE numphds < 50 and prof.dname = dept.dname



CREATE TABLE course
	(cno numeric(3,0),
	 cname varchar(50),
	 dname varchar(50),
	 primary key (cno, dname))

CREATE TABLE major
	(dname varchar(50),
	 sid int,
	 primary key (dname, sid))
	 
CREATE TABLE section
	(dname varchar(50),
	 cno numeric(3,0),
	 sectno numeric(1,0),
	 pname varchar(50),
	 primary key(dname, cno, sectno))
