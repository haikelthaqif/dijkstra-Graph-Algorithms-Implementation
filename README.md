# Dijkstra-Graph-Algorithms-Implementation

This repository contains a Java implementation of Dijkstra's algorithm for finding the shortest path in a weighted graph. The program reads data about Liverpool pubs from three input files and answers various questions related to finding the shortest paths between pubs.

## Project Description

In this project, we use Dijkstra's algorithm to find the shortest paths between Liverpool pubs. The input data is stored in three files:

- `pubs.csv`: Contains information about the pubs, including their IDs and names.
- `randomGraph.csv`: Contains information about the connections between pubs, including the source pub ID, destination pub ID, and the cost of taking that route.
- `pubs_lon_lat.csv`: Contains information about the longitude and latitude of each pub.

The main objectives of this project are:

1. Calculate the number of shortest paths (minimal number of vertices) between two given pubs.
2. Determine which pair of pubs has the highest number of shortest paths between them.
3. Find out how many shortest paths exist between the pubs with the highest number of paths.
4. Calculate the length of each of these shortest paths.
5. Identify the set of pubs furthest away from a specific pub in terms of the number of stops.
6. Determine the length of the shortest path between two specific pubs using Dijkstra's algorithm.
7. Calculate the length of the shortest path between two pubs in kilometres based on their latitude and longitude.

The provided Java code includes implementations for reading the input data, running Dijkstra's algorithm, and answering these questions.

## Usage

To run the program, you need to provide three input files as command-line arguments in the following order: `pubs`, `pubs_lon_lat`, and `randomGraph`. For example:

```bash
java Ass2226200553917 pubs.csv pubs_lon_lat.csv randomGraph.csv
```

The program will then calculate and display the answers to the questions mentioned in the project description.

## Output Format

The program provides answers to the questions in the following format:

```
Question 1: [Number of shortest paths between pubs]
Question 2: [Pair of pubs with the highest number of shortest paths]
Question 3: [Number of shortest paths for the pair in Question 2]
Question 4: [Length of each of the shortest paths in Question 2]
Question 5: [Set of pubs furthest away from a specific pub]
Question 6: [Length of the shortest path between two specific pubs]
Question 7: [Length of the shortest path (in km) between two pubs based on latitude and longitude]

Execution Time: [Execution time in milliseconds]
```

Please note that the actual answers will be provided based on the input data provided to the program.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
