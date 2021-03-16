create table if not exists Ingredient (
    id varchar(4) primary key not null,
    name varchar(25) not null,
    type varchar(10) not null
);

create table if not exists Taco (
    id bigint identity,
    name varchar(50) not null,
    created_at timestamp not null
);

create table if not exists Taco_Ingredients (
    taco_id bigint not null,
    ingredients_id varchar(4) not null,
    primary key(taco_id, ingredients_id),
    foreign key(taco_id) references Taco(id),
    foreign key(ingredients_id) references Ingredient(id)
);

create table if not exists Taco_Order (
    id bigint identity,
    delivery_name varchar(50) not null,
    street varchar(50) not null,
    address varchar(50) not null,
    city varchar(20) not null,
    cc_number varchar(16) not null,
    cc_expiration varchar(5) not null,
    cc_ccv varchar(3) not null,
    placed_at timestamp not null
);

create table if not exists Taco_Order_Tacos (
    taco_order_id bigint not null,
    taco_id bigint not null,
    primary key(taco_order_id, taco_id),
    foreign key(taco_order_id) references Taco_Order(id),
    foreign key(taco_id) references Taco(id)
);