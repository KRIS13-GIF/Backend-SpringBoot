# Backend Application 


1.E-Commerce  

Overview 

1.You will create 3 tables under the names (POST, FAVORITES, USERS). 

2.For each implementation create the services. 

3.Create for each implementation controllers under a unique name 

 

USERS (id, username, firstName, lastName, email, password, address, role, createdAt, deleted) role is ENUM with 2 types (USER, ADMIN) 

 

POST (id, title, description, address (get this from user), user(one to many, one user can have many posts), created At, status) status is ENUM with 2 types (PENDING, APPROVED) 

 

FAVOURITES (post (many to one), user (many to one))  

 

Description 

Endpoints for USERS: 

Create user (its initial role will be USER) 

Edit user. 

Delete user. 

 

Endpoints for POST:(all By User) 

Create post (It's status will always be PENDING first time created) . 

Edit post (Only if its status is PENDING) 

Delete post (No status restriction) (don’t delete it from database just change the Boolean value deleted to true). 

Change status (APPROVED) (Only user with role ADMIN can change its status) 

Create one user from backend with role ADMIN (hint: CommandLiner). 

Search for specific post (At least 2 properties) 

Retrieve all posts. (Connected to a specific user, make pagination if possible) 

 

Endpoints for FAVOURITES:(all by User) 

Add to favourites. 

Delete from favourites. 

Get all favourites
