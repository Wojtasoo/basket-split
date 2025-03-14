# Basket Splitter

## Overview
Basket Splitter is a Java-based utility that organizes a list of purchased items into different delivery options based on a predefined configuration. It reads a configuration file that defines the available delivery methods for each product and then optimally distributes the items into respective delivery groups.

## Features
- Reads product delivery options from a JSON configuration file.
- Splits a given list of items into optimized delivery groups.
- Supports serialization of output into JSON format.
- Includes unit tests for functionality validation.

## Requirements
- Java 8 or later
- Jackson library for JSON processing
- JUnit 5 for testing

## Installation
1. Clone the repository:
   ```sh
   git clone https://github.com/your-repo/basket-splitter.git
   ```
2. Navigate to the project directory:
   ```sh
   cd basket-splitter
   ```
3. Ensure you have the required dependencies (Jackson and JUnit) installed.

## Usage
### Running the Program
To execute the BasketSplitter, provide the path to a JSON configuration file:
```sh
java -cp . com.ocado.basket.BasketSplitter /path/to/config.json
```
The program processes sample basket files and outputs the organized delivery groups in JSON format.

### Example
#### Configuration File (config.json)
```json
{
  "Coffee - Colombian, Whole Bean": ["Next day shipping", "Same day delivery"],
  "Chocolate - Dark, 70% Cacao": ["Same day delivery", "Courier"],
  "Apples - Granny Smith, Organic": ["Next day shipping", "In-store pick-up"]
}
```

#### Input Basket
```json
["Coffee - Colombian, Whole Bean", "Chocolate - Dark, 70% Cacao", "Apples - Granny Smith, Organic"]
```

#### Expected Output
```json
{
  "Next day shipping": ["Coffee - Colombian, Whole Bean", "Apples - Granny Smith, Organic"],
  "Same day delivery": ["Chocolate - Dark, 70% Cacao"]
}
```

## Testing
The repository includes JUnit test cases in `BasketSplitterTest.java`. To run the tests:
```sh
mvn test  # If using Maven
# OR
javac -cp .:junit-5.jar com/ocado/basket/BasketSplitterTest.java
java -cp .:junit-5.jar org.junit.runner.JUnitCore com.ocado.basket.BasketSplitterTest
```

## Contributing
Contributions are welcome! Please follow these steps:
1. Fork the repository.
2. Create a new branch (`git checkout -b feature-name`).
3. Make your changes and commit (`git commit -m "Description of changes"`).
4. Push to the branch (`git push origin feature-name`).
5. Create a Pull Request.

## License
This project is licensed under the MIT License.

