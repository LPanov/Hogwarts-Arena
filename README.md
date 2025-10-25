# ⚡️ Hogwarts Arena ⚡️

**A text-based simulator based on the Harry Potter universe.**

This project was developed as the final requirement for the Spring Fundamentals course by SoftUni, Bulgaria. It demonstrates understanding of object-oriented programming (OOP) principles and data structures.

## 🎯 Project Goal
The primary objective of this project was to design and implement a functional, turn-based game using Spring MVC. Key learning areas included:
* Implementing **Object-Oriented Programming (OOP)** concepts.
* Implementing register and login functionalities.
<img width="1479" height="862" alt="image" src="https://github.com/user-attachments/assets/4ba5dfee-2e3c-4aff-aebd-3d2d1fd602ac" />
* Handling user input and output in a console environment.
* Live rendering information about the user(wizard)
<img width="1906" height="867" alt="image" src="https://github.com/user-attachments/assets/68248f9d-ff59-45c4-a8f2-244b556f691d" />
* Data the insertion from yaml with data about spells
* Creating Profile managing page
<img width="1312" height="806" alt="image" src="https://github.com/user-attachments/assets/9486fd93-46d1-4a4e-aa56-895024f86ab6" />
* Creating Arena listing all avalilable wizards by House(Gryffindor, Slythering, etc.)
<img width="1452" height="863" alt="image" src="https://github.com/user-attachments/assets/7e942e0f-d7d1-49cb-82ed-1230f52fe2d7" />

  ## ✨ Features
* **Character Creation:** Players can choose a house (Gryffindor, Slytherin, etc.) and customize their wizard/witch.
* **Spell System:** Implements a variety of offensive, defensive, and utility spells (e.g., *Stupefy*, *Protego*, *Expelliarmus*).

  ## ⚙️ Technologies Used
* **Language:** Spring MVC 3.3.5
* **Libraries/Modules:**
    * `random` (for random adding one spell when wizard is created)
    * `thymeleaf (for the fron-end rendering)
    * `spring crypto (for password encryption)
    * `etc...
* **Development Tools:** Git, GitHub

* ## 🚀 Installation and Setup

### Prerequisites

To run this project locally, you will need:
* **Java Development Kit (JDK) 17+** (Ensure the `JAVA_HOME` environment variable is set).
* **Maven** or **Gradle** (if you prefer running it without an IDE).

### Steps to Run

1.  **Clone the repository:**
    ```bash
    git clone [https://github.com/LPanov/Hogwarts-Arena.git](https://github.com/LPanov/Hogwarts-Arena.git)
    cd Hogwarts-Arena
    ```

2.  **Build the Project:**

    Use your preferred build tool to compile the code and package it into a single executable JAR file.

    **Option A: Using Maven**
    ```bash
    mvn clean install

3.  **Run the Application:**

    Navigate to the `target/` directory and run the generated JAR file.

    java -jar target/Hogwarts-Arena.jar


### Running via IDE (Alternative)

Alternatively, you can import the project directly into an IDE like **IntelliJ IDEA**, **Eclipse**, or **VS Code** with Java extensions and run the main class (which will likely have the `@SpringBootApplication` annotation).
