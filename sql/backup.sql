-- 1. Print the names of professors who work in departments that have fewer than 50 PhD students.
select pname
from prof as P, dept as D
where P.dname = D.dname 
	and numphds<50;

-- 2. Print the names of the students with the lowest GPA.
select S1.sname
from student S1
where S1.gpa = (select min(S2.gpa) from student S2);

-- 3. For each Computer Sciences class, print the class number, 
-- section number, and the average gpa of the students enrolled in the class section.
select cno, sectno, avg(gpa) as avg_gpa
from student as S, enroll as E
where S.sid = E.sid and dname = 'Computer Sciences'
group by dname, cno, sectno;

-- 4. Print the names and section numbers of all sections with 
-- more than six students enrolled in them.
select cname, sectno
from course as C, enroll as E
where C.cno = E.cno and C.dname = E.dname
group by C.dname, C.cno, cname, sectno
having (count(sid) > 6);
---check 4
select cname, sectno, count(sid) as studentcount
from course as C, enroll as E
where C.cno = E.cno and C.dname = E.dname
group by C.dname, C.cno, cname, sectno
having count(sid) > 6;

-- 5.Print the name(s) and sid(s) of the student(s) enrolled in the most sections.
with section_count (sid, sectioncount) as
	(select sid, count(*)
	 from enroll as E
	 group by E.sid)
select S.sname, C1.sid
from student as S, section_count as C1
where S.sid = C1.sid 
      and sectioncount = 
      (select max(sectioncount) 
              from section_count C2);

----5 check (wrong)
--select sname, sectioncount.sid, section_count
--from (select sname, S.sid, count(*) as section_count
--	  from enroll as E, student as S
--	  where S.sid = E.sid
--	  group by sname, S.sid) as sectioncount
--group by 
--having section_count = max(section_count);
--
--with section_count (sid, sectioncount) as
--	(select sid, count(*)
--	 from enroll as E
--	 group by sid)
--select sname, C1.sid, sectioncount
--from student as S, section_count as C1
--where S.sid = C1.sid --and sectioncount = (select max(sectioncount) from section_count C2);
--
--select sid
--from student
--where not exists (select sid from enroll where student.sid = enroll.sid);

-- 6. Print the names of departments 
-- that have one or more majors who are under 18 years old.
select M.dname
from major as M, student as S
where M.sid = S.sid and S.age < 18
group by M.dname;

-- 7. Print the names and majors of students who are taking 
-- one of the College Geometry courses.
select S.sname, M.dname
from major as M, student as S, enroll as E, course as C
where S.sid = M.sid 
      and S.sid = E.sid 
      and C.cno = E.cno 
      and C.dname = E.dname 
      and cname like 'College Geometry%';
              
-- 8. For those departments that have no major taking a College Geometry course 
-- print the department name and the number of PhD students in the department.
with dept_with_geometry (dname) as
    (select distinct M.dname
	 from major as M, enroll as E, course as C
     where M.sid = E.sid and C.cno = E.cno and C.dname = E.dname and
    	  cname like 'College Geometry%')
select dname, numphds
from dept as D
where not exists (select DG.dname 
                 from dept_with_geometry as DG where DG.dname = D.dname);
-- or
select dname, numphds
from dept as D
where D.name not in
      (select M.dname
       from major as M, enroll as E, course as C
       where M.sid = E.sid
            and C.cno = E.cno
            and C.dname = E.dname
            and cname like 'College Geometry%')		

                        
-- 9. Print the names of students who are taking both 
-- a Computer Sciences course and a Mathematics course.
(select sname
 from student as S1, enroll as E1
 where S1.sid = E1.sid and E1.dname = 'Computer Sciences')
 intersect
 (select sname
  from student as S2, enroll as E2
  where S2.sid = E2.sid and E2.dname = 'Mathematics');
  
-- or
     
with studentid(sid) as
    (select E1.sid
    from enroll as E1, enroll as E2 
    where E1.sid = E2.sid and E1.dname = 'Mathematics' 
	    and E2.dname = 'Computer Sciences')
select sname
from studentid as D, student as S
where S.sid = D.sid;

--or
select S.sname
from student as S, enroll as E1, enroll as E2
where S.sid = E1.sid and S.sid = E2.sid
      and E1.dname = 'Mathematics' and E2.dname = 'Computer Sciences';

-- 10. Print the age difference between 
-- the oldest and the youngest Computer Sciences major.
select (max (S.age)-min (S.age)) as age_difference
from student as S, major as M
where S.sid = M.sid and dname = 'Computer Sciences';

-- 11.For each department that has one or more majors with a GPA under 1.0, 
-- print the name of the department and the average GPA of its majors.
select M.dname, avg(gpa)
from (select dname
      from major as M, student as S
      where M.sid = S.sid 
      and gpa < 1.0) as low_gpa_dept, major as M, student as S
where low_gpa_dept.dname = M.dname and M.sid = S.sid
group by M.dname;

-- 12. Print the ids, names and GPAs of the students 
-- who are currently taking all the Civil Engineering courses.
with NumCivilEng(count) as
	(select count (*)
	 from course
	 where dname = 'Civil Engineering')
select S.sid, S.sname, S.gpa
from student as S, NumCivilEng as N, (select sid, count(distinct cno) as count
                    from enroll as E
                    where dname = 'Civil Engineering'
                    group by sid) as E2
where S.sid = E2.sid and E2.count = N.count;
--
                 
select S.sid, S.sname, S.gpa
from enroll as E, student as S
where E.sid = S.sid
group by S.sid, S.sname, S.gpa
having count(E.dname = 'Civil Engineering') =
        (select count(*)
         from course as C
         where C.dname = 'Civil Engineering');
         


