-- Q1.Write a query to produce a list of employees, one person per line, with one column for the land
-- line phone number, and one column for the cell phone number. Use a NULL value if the employee
-- is missing a phone number.

create view land_phone (empid, landLine) as
            (select empid, phonenumber
             from Phones
             where phonetype = 'home'
             );

create view cell_phone (empid, cellLine) as
            (select empid, phonenumber
             from Phones
             where phonetype = 'cell'
             );
             
select E.empid, firstname, lastname, landLine, cellLine
from land_phone as L
    right outer join Employees as E on L.empid = E.empid
    left outer join cell_phone as C on C.empid = E.empid;

         
-- Q2. Write a query to determine which salesperson had the highest total 
-- amount of sales for each promotion. 


create view sum_sale (promo, salesperson, amount) as
      select P.promo, salesperson, sum (amount) as amount
      from Promotions as P, Sales as S
      where saledate between startdate and enddate
      group by P.promo, salesperson;
      
with max_sum_sale (promo, amount) as
     (select promo, max (amount)
      from sum_sale as S1
      group by promo)
select P.promo, S2.salesperson, S2.amount
from max_sum_sale as M join sum_sale as S2 
                           using (promo, amount)
                       right outer join Promotions as P
                           using (promo)
order by P.promo asc;


--Q3. Write a query that produces a list of baby names, and the maximum number of overlapping sessions
-- for the babysitter during that session.
create view overlap_sessions (baby, sitter, start_time, overlap) as
      select S1.baby, S1.sitter, S1.start_time, count (S2.baby)
      from Sessions as S1, Sessions as S2
      where S1.sitter = S2.sitter
            and S1.start_time between S2.start_time and S2.end_time 
      group by S1.baby, S1.sitter, S1.start_time;

select S.baby, max (overlap)
from Sessions as S, overlap_sessions as O
where O.start_time between S.start_time and S.end_time
     and S.sitter = O.sitter
group by S.baby;

                            