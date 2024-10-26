# Rule Engine Application

## Overview

The Rule Engine Application is a simple 3-tier rule engine that determines user eligibility based on attributes like age, department, income, and experience. It uses an Abstract Syntax Tree (AST) to represent conditional rules and allows for dynamic creation, combination, and modification of these rules.

## Features

- Create and evaluate rules stored in MongoDB.
- Abstract Syntax Tree (AST) representation of rules.
- Modify existing rules in the database.
- User-defined functions for advanced conditions.
- Error handling for invalid rule strings and data formats.

## Technologies Used

- Java 8+
- Maven
- MongoDB
- JUnit (for testing)
- JSON library

## Prerequisites

- Java Development Kit (JDK) 8 or higher.
- Apache Maven 3.x.
- MongoDB installed and running on `localhost:27017`.

## Getting Started

### Clone the Repository

```bash
git clone https://github.com/kalyaniDhapodkar/RuleEngine
cd rule-engine
