insert into account(name,balance,create_Date,expiry_date) values('account1',2000,trunc(sysdate)-5,null);
insert into account(name,balance,create_Date,expiry_date) values('account2',1000,trunc(sysdate)-5,null);
insert into account(name,balance,create_Date,expiry_date) values('account3',1500,trunc(sysdate)-4,null);
insert into account(name,balance,create_Date,expiry_date) values('account4',2500,trunc(sysdate)-4,null);
insert into account(name,balance,create_Date,expiry_date) values('account5',5000,trunc(sysdate)-3,null);
insert into account(name,balance,create_Date,expiry_date) values('account6',6000,trunc(sysdate)-3,null);
--
insert into TRANSFER(id,src_acc,dest_acc,amount,process_timestamp) values(1,'account1','account2',300,CURRENT_TIMESTAMP());
insert into TRANSFER(id,src_acc,dest_acc,amount,process_timestamp) values(2,'account4','account2',500,CURRENT_TIMESTAMP());
insert into TRANSFER(id,src_acc,dest_acc,amount,process_timestamp) values(3,'account6','account5',500,CURRENT_TIMESTAMP());
insert into TRANSFER(id,src_acc,dest_acc,amount,process_timestamp) values(4,'account5','account3',1000,CURRENT_TIMESTAMP()); 