You're right! Let me explain how to use all the different URLs (endpoints) with examples:
1. GET All Users
   URL: http://localhost:8080/api/users
   Method: GET
   This returns all users in JSON format.
2. GET User by ID
   URL: http://localhost:8080/api/users/1
   Method: GET
   This gets the user with ID = 1.
3. CREATE New User
   URL: http://localhost:8080/api/users
   Method: POST
   Content-Type: application/json

Body (JSON):
{
"name": "Alice Brown",
"email": "alice@example.com"
}
4. UPDATE User
   URL: http://localhost:8080/api/users/1
   Method: PUT
   Content-Type: application/json

Body (JSON):
{
"name": "Updated Name",
"email": "updated@example.com"
}
5. DELETE User
   URL: http://localhost:8080/api/users/1
   Method: DELETE
6. SEARCH Users by Name
   URL: http://localhost:8080/api/users/search?name=john
   Method: GET
   This finds users whose names contain "john".
7. GET User Count
   URL: http://localhost:8080/api/users/count
   Method: GET
   How to test these:
   Using Browser (only GET requests):

http://localhost:8080/api/users - see all users
http://localhost:8080/api/users/1 - see user with ID 1
http://localhost:8080/api/users/search?name=john - search for "john"
http://localhost:8080/api/users/count - get total count

Using Postman or curl (for POST, PUT, DELETE):

For POST/PUT: set method, add JSON body
For DELETE: just set method to DELETE

The key difference is the HTTP method (GET, POST, PUT, DELETE) and sometimes adding JSON data in the request body.



Swagger UI: http://localhost:8080/swagger-ui.html
OpenAPI JSON: http://localhost:8080/v3/api-docs