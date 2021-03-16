create table if not exists Ingredient (
    id varchar(4) primary key not null,
    name varchar(25) not null,
    type varchar(10) not null
);

create table if not exists Taco (
    id bigint identity,
    name varchar(50) not null,
    createdAt timestamp not null
);

create table if not exists Taco_Ingredients (
    taco bigint not null,
    ingredient varchar(4) not null,
    primary key(taco, ingredient),
    foreign key(taco) references Taco(id),
    foreign key(ingredient) references Ingredient(id)
);

create table if not exists Taco_Order (
    id bigint identity,
    deliveryName varchar(50) not null,
    street varchar(50) not null,
    address varchar(50) not null,
    city varchar(20) not null,
    ccNumber varchar(16) not null,
    ccExpiration varchar(5) not null,
    ccCCV varchar(3) not null,
    placedAt timestamp not null
);

create table if not exists Taco_Order_Tacos (
    tacoOrder bigint not null,
    taco bigint not null,
    primary key(tacoOrder, taco),
    foreign key(tacoOrder) references Taco_Order(id),
    foreign key(taco) references Taco(id)
);