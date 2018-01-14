insert into account(name,balance) values('account1',2000);
insert into account(name,balance) values('account2',1000);
insert into account(name,balance) values('account3',1500);
insert into account(name,balance) values('account4',2500);
insert into account(name,balance) values('account5',5000);
insert into account(name,balance) values('account6',6000);
--
insert into TRANSFER(id,src_acc,dest_acc,amount,process_timestamp) values(1,'account1','account2',300,CURRENT_TIMESTAMP());
insert into TRANSFER(id,src_acc,dest_acc,amount,process_timestamp) values(2,'account4','account2',500,CURRENT_TIMESTAMP());
insert into TRANSFER(id,src_acc,dest_acc,amount,process_timestamp) values(3,'account6','account5',500,CURRENT_TIMESTAMP());
insert into TRANSFER(id,src_acc,dest_acc,amount,process_timestamp) values(4,'account5','account3',1000,CURRENT_TIMESTAMP());