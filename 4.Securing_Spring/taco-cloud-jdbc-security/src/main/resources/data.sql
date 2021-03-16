delete from Taco_Order_Tacos;
delete from Taco_Ingredients;
delete from Taco;
delete from Taco_Order;
delete from Ingredient;
delete from Users;
delete from Authorities;

insert into Ingredient (id, name, type)
 values ('1', 'Orange', 'FRUIT');
insert into Ingredient (id, name, type)
 values ('2', 'Banana', 'FRUIT');
insert into Ingredient (id, name, type)
 values ('3', 'French', 'CHEESE');
insert into Ingredient (id, name, type)
 values ('4', 'Italian', 'CHEESE');
insert into Ingredient (id, name, type)
 values ('5', 'Sliced', 'PATATOES');
insert into Ingredient (id, name, type)
 values ('6', 'Roasted', 'PATATOES');
insert into Ingredient (id, name, type)
 values ('7', 'Pork', 'MEAT');
insert into Ingredient (id, name, type)
 values ('8', 'Chicken', 'MEAT');
 insert into Ingredient (id, name, type)
 values ('9', 'French', 'CHEESE');

  insert into Users (username, password, enabled)
 values ('pesho', '1234', 1);
   insert into Users (username, password, enabled)
 values ('gosho', '1234', 1);

   insert into Authorities (username, authority)
 values ('gosho', 'ROLE_USER');
    insert into Authorities (username, authority)
 values ('pesho', 'ROLE_USER');