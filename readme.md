# Shopping List / To-Do List / Tasks List

A Shopping / To-Do / Tasks List with a RESTful API, Spring +Angular, Layered Architecture

##  Tools and Technologies

- Java 8 as OOP language
- Spring Boot 2 to run Spring Web Application
- Hibernate 5 ORM for data mapping
- MySQL database
- Maven
- IntelliJ IDEA
- REST API
- Postman
- Angular
- HTML
- CSS
- TypeScript
- JUnit
- Mockito

## Database configuration

Project uses an in-memory database MySQL

login: root

password: root

Application works on localhost:8080

## Status of the Project

Project is: in progress

## Layered Architecture

| Layer | Source |
|--|--|
| JavaBean domains| [domain folder](src/main/java/almielka/shoppinglist/domain) |
|Repositories| [repository folder](src/main/java/almielka/shoppinglist/repository) |
|Services| [service folder](src/main/java/almielka/shoppinglist/service) |
| REST API Controllers | [controller folder](src/main/java/almielka/shoppinglist/controller) |

## Functionality

____
### Board Management
____

###	Add Board:

URL: http://localhost:8080/boards

HTTP-method: POST

Data:
- Unique Board title

RequestBody:
```JSON
{
    "title": "Task List"
}
``` 

### Get Board by ID:
URL: http://localhost:8080/boards/3

HTTP-method: GET

### Get List Boards by Title containing:
URL: http://localhost:8080/boards/search/li

HTTP-method: GET

### Get All Boards by Title ASC:
URL: http://localhost:8080/boards/

HTTP-method: GET

### Edit Board by ID:
URL: http://localhost:8080/boards/3

HTTP-method: PUT

Data:
- Unique board title

RequestBody:
```JSON
{
    "title": "Update Task List"
}
```

### Remove Board by ID:
URL: http://localhost:8080/boards/3

HTTP-method: DELETE

____
### Category Management
____

### Add Category:
URL: http://localhost:8080/categories

HTTP-method: POST

Data:
- Unique category title
- Category color (optional)

RequestBody:
```JSON
{
	"title": "New Category",
	"color": "#222222"
}	
```

### Get Category by ID:
URL: http://localhost:8080/categories/2

HTTP-method: GET

### Get List Categories by Title containing:
URL: http://localhost:8080/categories/search/at

HTTP-method: GET

### Get All Categories by Title ASC:
URL: http://localhost:8080/categories/

HTTP-method: GET

### Edit Category by ID:
URL: http://localhost:8080/categories/3

HTTP-method: PUT

Data:
- Unique category title
- Category color (optional)

RequestBody:
```JSON
{
	"title": "Update Category3",
	"color": "#333333"
}
```

### Remove Category by ID:
URL: http://localhost:8080/categories/6

HTTP-method: DELETE

____
### Task Management
____

### Add Task:
URL: http://localhost:8080/tasks

HTTP-method: POST

Data:
- Unique task title
- Task description (optional)
- Task amount (optional)
- board_id
- category_id

RequestBody:
```JSON
{
	"title": "New Task2",
	"description": "Task description",
	"category": {
		"id": "5"
	},
	"board": {
		"id": "4"
	}
}
```

### Get Task by ID:
URL: http://localhost:8080/tasks/5

HTTP-method: GET

### Get List Tasks by Title containing:
URL: http://localhost:8080/tasks/search/as

HTTP-method: GET

### Get All Tasks by Title ASC:
URL: http://localhost:8080/tasks/

HTTP-method: GET

### Edit Task by ID:
URL: http://localhost:8080/tasks/5

HTTP-method: PUT

Data:
- Unique task title
- Task description (optional)
- Task amount (optional)
- completed (optional)
- board_id
- category_id

RequestBody:
```JSON
{
	"title": "New Task2",
	"description": "Task description",
	"completed": "true",
	"category": {
		"id": "5"
	},
	"board": {
		"id": "1"
	}
}
```

### Remove Task by ID:
URL: http://localhost:8080/tasks/6

HTTP-method: DELETE

____
### General Statistic
____

### Get Statistic
URL: http://localhost:8080/statistics
