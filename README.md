# PetOasis - Backend API

## 🎯 Project Objective

The aim of this project was to develop a **REST API** using Spring Boot, serving as the backend for the PetOasis application.  
The primary goal was to create a web-based system that connects animal shelters with individuals interested in spending time with animals, such as by taking them for walks.  
The core objective is to **facilitate easier interaction with animals in shelters**.  
The application is designed to handle essential requests related to users, animals, and shelters, and enable **interaction between these core elements** of the system.

## 💡 Key Features

1. **Animal Registration in Shelters**
    - Shelters can share information about their animals to attract potential adopters or individuals willing to spend time with them.
    - Shelters can update the status of their animals, including health and availability.
    
2. **Animal Browsing**
    - Users can browse animals offered by shelters and view detailed information about them.
    - Animals can have comments from other users and various badges associated with them.
    
3. **Scheduling Walks**
    - Currently, the application supports scheduling time with animals exclusively through walks.
    - Animals are available for walks if their health status and availability allow it.

The application is structured in a way that allows easy extension for additional features beyond these core functionalities, serving as a foundation for further development.

## 🧑‍💻 Usage and Testing

The application uses OpenAPI 3.0 for documentation generation, relying on Swagger-UI to provide a graphical interface that effectively illustrates the system's structure, functionalities, and documentation. Each endpoint has been tested individually, as well as through integrated scenarios involving multiple endpoints, using the generated UI.

Once the application is running, the OpenAPI interface is accessible at `/swagger-ui.html`.

## 🚀 Running the Application

### Requirements

- **Java** 17 
- **Maven** (tested with version 3.9.9)
- **Docker** (tested with version 26+)
- **Docker Compose** (tested with v2.27.1-desktop.1)
- **Git**

### Method 1 - Docker (Recommended)

After cloning the repository, navigate to the root directory and run the following commands sequentially in the console:

1. `mvn clean package -DskipTests`
2. `docker compose build`
3. `docker compose up`

This process will set up and run the application along with the required database within containers.  
The Application is running on port 18080.  After successful launch it should be available at [`http://localhost:18080/swagger-ui.html`](http://localhost:18080/swagger-ui.html)

### Method 2 - IntelliJ

⚠️ **Note:** PostgreSQL must be installed locally!

After cloning the source code, navigate to the `application.properties` file and comment out the "docker setup" section while uncommenting the "run from IntelliJ setup". Then configure the parameters according to your local setup.

In PostgreSQL, create a database named `petoasis` or use a different name and update the corresponding value in the `application.properties` file. Ensure that the database credentials (username and password) match your local configuration.
