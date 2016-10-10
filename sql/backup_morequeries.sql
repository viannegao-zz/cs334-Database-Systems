-- Q1.
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

-- select E.empid, firstname, lastname, landLine, cellLine
--from Employees as E 
--    full outer join (land_phone as L
  --  full outer join cell_phone as C using (empid)) 
    --     using (empid);

         
-- Q2. Write a query to determine which salesperson had the highest total 
-- amount of sales for each promotion. 
with max_sale (promo, amount) as
     (select promo, max (amount)
      from Promotions as P, Sales as S
      where saledate >= startdate and saledate <= enddate
      group by promo)
select P.promo, S.salesperson, S.amount
from Promotions as P left outer join max_sale using (promo) 
                     left outer join Sales as S
                         on (saledate >= startdate 
                             and saledate <= enddate
                             and S.amount = max_sale.amount)
order by P.promo asc;
     

-- Q3. 
