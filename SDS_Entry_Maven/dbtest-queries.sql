--Answer 1
select sum(claimed_charge) from document where status like 'EXPORTED%';
--Answer 2
select insured_name, insured_address, claimed_charge from document d
inner join batch b on d.id = b.id
where 1=1
and d.status  = "TO_REPRICE"
and b.customer_id in(1,2);